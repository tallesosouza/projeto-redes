package com.terminalroot.game.classes;

import java.io.IOException;
import java.net.Socket;

import com.terminalroot.game.models.ActionPlayerEnum;
import com.terminalroot.game.models.AllElements;
import com.terminalroot.game.models.DirectionEnum;
import com.terminalroot.game.models.ScreenGame;
import com.terminalroot.game.models.StatusGameEnum;
import com.terminalroot.game.socket.ServerSocket;

public class Player {

  private final ServerSocket playerSocket;
  private final ScreenGame screen;
  private AllElements allElementGame;

  public Player(final Socket socket, final ScreenGame screenGame) throws IOException {
    this.playerSocket = new ServerSocket(socket);
    this.screen = screenGame;
  }


  public void startPlayer(){
    this.allElementGame = new AllElements(screen);
    this.allElementGame.initialize();
    this.loopActionPlayer();
  }

  public void loopActionPlayer() {
    String actionClient;
    try {
      while((actionClient = this.playerSocket.getAction()) != null){
        // System.out.println(actionClient);
        if(ActionPlayerEnum.EXIT.name().equals(actionClient)){
          this.allElementGame.editStatus(StatusGameEnum.FINISHED);
          return;
        }else{
          this.checkActionPlayer(actionClient);
        }
      }
    } finally {
      System.out.println("Jogador: " + this.playerSocket.getRemoteSocketAddress() + " Desconectou-se");
      this.playerSocket.close();
    }
  }

  public ScreenGame getScreen() {
    return screen;
  }

  public ServerSocket getClientSocket() {
    return this.playerSocket;
  }

  private void checkActionPlayer(String action){

    if(ActionPlayerEnum.ENTER.name().equals(action)){
      if(this.allElementGame.checkStatus(StatusGameEnum.WAITING)){
        new Thread(() -> this.checkNewFrame()).start(); //Start new Thread by eminies and missible frame
      }
      this.allElementGame.startOrUnPauseGame();
    }else if(this.allElementGame.checkStatus(StatusGameEnum.IN_PROGRESS)){

      if(ActionPlayerEnum.RIGHT.name().equals(action)){
        this.allElementGame.moveNave(DirectionEnum.RIGHT);

      }else if(ActionPlayerEnum.LEFT.name().equals(action)){
        this.allElementGame.moveNave(DirectionEnum.LEFT);

      }else if(ActionPlayerEnum.UP.name().equals(action)){
        this.allElementGame.moveNave(DirectionEnum.UP);

      }else if(ActionPlayerEnum.DOWN.name().equals(action)){
        this.allElementGame.moveNave(DirectionEnum.DOWN);

      }else if(ActionPlayerEnum.SPACE.name().equals(action)){
        this.allElementGame.lounchMissible();

      }else {
        System.err.println("Usuário: "+this.playerSocket.getRemoteSocketAddress()+" tentou enviar um comando incorreto: "+action);
      }

      // AllElements frame = this.allElementGame;
      // Random random = new Random();
      // Product product = new Product("Teste", random.nextInt());
      // ScreenGame screen = new ScreenGame(120, 100);
      // Element element = new Element(random.nextInt(),random.nextInt(),120,120,150, screen);
      // Element element = new Element(random.nextInt(),random.nextInt(),120,120,150, screen);
      this.playerSocket.sendFrame(this.allElementGame);
    } 
  }

  private void checkNewFrame(){
    Object frame;
    this.allElementGame.setStatus(StatusGameEnum.IN_PROGRESS);
    while(this.allElementGame.getStatus() != StatusGameEnum.FINISHED) {
      // frame = this.allElementGame.getNewFrame();
      // this.playerSocket.sendFrame(frame);
    }
  }

}
