package src.com.terminalroot.game.engine;

import java.net.Socket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.terminalroot.game.models.ActionsModel;
import com.terminalroot.game.models.AllElements;
import com.terminalroot.game.models.StatusGameModel;

public class ClientPlayer{
  
  SpriteBatch batch;
  private AllElements allElements;
  private final String SERVER_ADDRESS_CONNECT;
  private final int SERVER_PORT;
  private ClientSocket clientSocket;
  private ClientPlayerObservable playerObservable;

  public ClientPlayer(String address, int port) {
    allElements = new AllElements();
    this.allElements.initialize();
    
    this.SERVER_ADDRESS_CONNECT = address;
    this.SERVER_PORT = port;
    this.startConnection();
    this.createTextures();
  }

  public void closeConnection() {
    this.clientSocket.sendAction(ActionPlayerEnum.EXIT);
    this.clientSocket.close();
    this.allElements.dispose();
  }

  public void checkKeyBoard() {
    if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			this.clientSocket.sendAction(ActionPlayerEnum.ENTER);
      this.startGame();
		}
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			this.clientSocket.sendAction(ActionPlayerEnum.RIGHT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			this.clientSocket.sendAction(ActionPlayerEnum.LEFT);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			this.clientSocket.sendAction(ActionPlayerEnum.UP);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			this.clientSocket.sendAction(ActionPlayerEnum.DOWN);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			this.clientSocket.sendAction(ActionPlayerEnum.SPACE);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			this.clientSocket.sendAction(ActionPlayerEnum.EXIT);
		}
  }

  public AllElements getAllElements() {
    return this.allElements;
  }

  // public Element getNave(){
  //   return this.allElements.getNave();
  // }

  // public List<Element> getEminies(){
  //   return this.allElements.getEminies();
  // }


  // @Override
  // public void run() {
  //     String msg;
  //     while((msg = clientSocket.getMessage())!=null) {
  //         System.out.println(msg);
  //     }
  // }

  private void startConnection() {
    try {
      final Socket socket = new Socket(this.SERVER_ADDRESS_CONNECT, SERVER_PORT);
      this.clientSocket = new ClientSocket(socket);
      this.playerObservable = new ClientPlayerObservable(this.clientSocket, this.allElements);
      
      System.out.println(
        "Cliente conectado ao servidor no endereço " + SERVER_ADDRESS_CONNECT +
        " e porta " + SERVER_PORT);
      this.allElements.setStatus(StatusGameEnum.WAITING);
    }catch(Exception e){
      System.out.println("Error ao tentar se conectar ao servidor "+SERVER_ADDRESS_CONNECT+ " e porta " + SERVER_PORT);
      this.allElements.setStatus(StatusGameEnum.ERROR);
    }
  }

  private void createTextures() {
    
  }

  private void startGame(){
    if(this.allElements.checkStatus(StatusGameEnum.WAITING)){
      new Thread(this.playerObservable).start();
    }
  }
}
