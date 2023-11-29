package com.terminalroot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import com.terminalroot.game.classes.Player;
import com.terminalroot.game.models.ScreenGame;

public class ServerTCP {

    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private ScreenGame screen;

    private final List<Player> playerList;

    public ServerTCP() {
        playerList = new LinkedList<>();
        this.initialElements();
        
    }

    private void initialElements(){
      screen = new ScreenGame(1280, 720);
	  }

    public static void main(String[] args) {
        final ServerTCP server = new ServerTCP();
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }

    private void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println(
                "Servidor de chat bloqueante iniciado no endereço " + serverSocket.getInetAddress().getHostAddress() +
                " e porta " + PORT);
        clientConnectionLoop();
    }

    /*
     * Create loop accept players
     */
    private void clientConnectionLoop() throws IOException {
        try {
            while (true) {
                System.out.println("Aguardando conexão de novo jogador");
                final Player player;
                
                try {
                    player = new Player(serverSocket.accept(), screen);
                    System.out.println("Jogador " + player.getClientSocket().getRemoteSocketAddress() + " conectado");
                }catch(SocketException e){
                    System.err.println("Erro ao aceitar conexão do jogador. O servidor possivelmente está sobrecarregado:");
                    System.err.println(e.getMessage());
                    continue;
                }

                try {
                    new Thread(() -> player.startPlayer()).start();
                    playerList.add(player);
                }catch(OutOfMemoryError ex){
                    System.err.println(
                            "Não foi possível criar thread para novo jogador. O servidor possivelmente está sobrecarregdo. Conexão será fechada: ");
                    System.err.println(ex.getMessage());
                    player.getClientSocket().close();
                }
            }
        } finally{
            stop();
        }
    }

    // private void checkActions(final ClientSocket sender, String action) {
    //     if(action.equals("RIGHT") && this.todosElementos.nave.X < widthScreen + todosElementos.nave.width ){
    //         this.todosElementos.nave.X += this.todosElementos.nave.velocity;
    //     }
    //     if(action.equals("LEFT") && this.todosElementos.nave.X > 0){
    //         this.todosElementos.nave.X -= this.todosElementos.nave.velocity;
    //     }
    //     if(action.equals("UP") && this.todosElementos.nave.Y < heigthScreen - todosElementos.nave.height ){
    //         this.todosElementos.nave.Y += this.todosElementos.nave.velocity;
    //     }
    //     if(action.equals("DOWN") && this.todosElementos.nave.Y > 0){
    //         this.todosElementos.nave.Y -= this.todosElementos.nave.velocity;
    //     }
    //     this.checkMissible(action);
        
    //     try{
    //         ObjectMapper mapper = new ObjectMapper();
    //         String objectSend = mapper.writeValueAsString(this.todosElementos);
    //         sendMsgToAll(sender, objectSend);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void checkMissible(String action){
    //     if(action.equals("SPACE") && !this.todosElementos.nave.attack){
    //         new Thread(() -> loopMissibleAttack()).start();
    //     }else{
    //         this.todosElementos.missible.attack = false;
    //     }
    // }

    // private void loopMissibleAttack(){
    //     // try {
    //     //     while(this.todosElementos.nave.attack){
    //     //         // final SocketAddress clientIP = clientSocket.getRemoteSocketAddress();
    //     //         // if("sair".equalsIgnoreCase(msg)){
    //     //         //     return;
    //     //         // }

    //     //         // System.out.println("Mensagem recebida de "+ clientSocket.getUserName() +": " + msg);
    //     //         // msg = " diz: " + msg;

    //     //         // this.checkActions(clientSocket, msg);
    //     //         // sendMsgToAll(clientSocket, msg);
    //     //     }
    //     // } finally {
    //     //     // clientSocket.close();
    //     // }
    // }
    

    // private void sendMsgToAll(final ClientSocket sender, final String msg) {
    //     final Iterator<ClientSocket> iterator = clientSocketList.iterator();
    //     int count = 0;
        
    //     while (iterator.hasNext()) {
    //         final ClientSocket client = iterator.next();
    //         if(client.sendMsg(msg))
    //             count++;
    //         else iterator.remove();
    //     }
    // }

    private void stop()  {
        try {
            System.out.println("Finalizando servidor");
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar socket do servidor: " + e.getMessage());
        }
    }

}
