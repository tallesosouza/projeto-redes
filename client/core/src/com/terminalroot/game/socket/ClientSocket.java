package com.terminalroot.game.socket;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

import com.terminalroot.game.models.ActionsModel;

public class ClientSocket implements Closeable{
  private final Socket socket;
  private final ObjectInputStream input;
  private final PrintWriter output;

  public ClientSocket(final Socket socket) throws IOException {
    this.socket = socket;
    this.input = new ObjectInputStream(socket.getInputStream());
    this.output = new PrintWriter(socket.getOutputStream(), true);
  }

  public void sendAction(ActionPlayerEnum msg) {
    output.println(msg);
  }

  public String getObject() {
    try {
      return ( String ) input.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void close() {
      try {
          input.close();
          output.close();
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

}
