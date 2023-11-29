package src.com.terminalroot.game.engine.models;

import java.io.Serializable;

public class ElementModel implements Serializable {
  protected int X;  
  protected int Y;
  protected int velocity;
  protected int width;
  protected int height;
  protected boolean attack;
  private static final long serialVersionUID = 1L;

  @Override
  public String toString() {
    return  "{" + 
            "X="+X+","+
            "Y="+Y+","+
            "width="+width+","+
            "height="+height+","+
            "attack="+attack+"}";
  }

}
