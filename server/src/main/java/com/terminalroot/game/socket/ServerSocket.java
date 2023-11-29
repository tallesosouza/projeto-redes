package com.terminalroot.game.socket;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terminalroot.game.models.AllElementsModel;

public class ServerSocket implements Closeable{
  private final Socket socket;
  private final BufferedReader in;
  private final ObjectOutputStream out;
  private ObjectMapper objectMapper;

  public ServerSocket(final Socket socket) throws IOException {
    this.socket = socket;
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.out = new ObjectOutputStream(socket.getOutputStream());
    this.objectMapper = new ObjectMapper();
  }

  public boolean sendFrame(AllElementsModel frame) {
    try {
      String enviar = convertObjectToString(frame);
      System.out.println(enviar);
      out.writeObject(enviar);
      return true;
    } catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }

  public String getAction() {
    try {
      String objetoRecebido =  in.readLine();
      return objetoRecebido;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }


  @Override
  public void close() {
      try {
          in.close();
          out.close();
          socket.close();
      } catch(IOException e){
          System.err.println("Erro ao fechar socket: " + e.getMessage());
      }
  }

  public SocketAddress getRemoteSocketAddress(){
      return socket.getRemoteSocketAddress();
  }

  public boolean isOpen(){
      return !socket.isClosed();
  }

  private String convertObjectToString(AllElementsModel elements) throws Exception {
    return objectMapper.writeValueAsString(elements);
  }
}
