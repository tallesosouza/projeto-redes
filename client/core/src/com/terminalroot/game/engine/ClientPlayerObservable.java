package src.com.terminalroot.game.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terminalroot.game.models.AllElements;
import com.terminalroot.game.models.AllElementsModel;
import com.terminalroot.game.models.StatusGameModel;


public class ClientPlayerObservable implements Runnable {
  private final ClientSocket clientSocket;
  private final AllElements allElements;

  public ClientPlayerObservable(ClientSocket socket, AllElements allElements) {
    this.clientSocket = socket;
    this.allElements = allElements;
  }

  @Override
	public void run() {
      String obj;
      this.allElements.setStatus(StatusGameEnum.IN_PROGRESS);

      ObjectMapper objectMapper = new ObjectMapper();
			while((obj = this.clientSocket.getObject()) != null) {
        System.out.println(obj);
        try {
            AllElementsModel yourObject = objectMapper.readValue(obj, AllElementsModel.class);
            System.out.println("Objeto resultante: " + yourObject.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
	}   

}
