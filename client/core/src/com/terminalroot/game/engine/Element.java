package src.com.terminalroot.game.engine;

import java.io.Serializable;

public class Element implements Serializable {
  private int x;  
  private int y;
  private int velocity;
  private int width;
  private int height;
  private boolean attack;
  private transient ScreenGame screen;
  private static final long serialVersionUID = 1L;

  public Element() {
  }

  public void initialize(int initX, int initY, int initVelocity, int initWidth, int iniHeight) {
    x = initX;
    y = initY;
    this.velocity = initVelocity;
    this.width = initWidth;
    this.height = iniHeight;
    this.attack = false;
  }

  public boolean checkCollide(Element elementCheck){
    return this.collide(elementCheck.x, elementCheck.y, elementCheck.width, elementCheck.height, x, y, width, height);
  }

  public void moveElement(DirectionEnum direction){
    if(direction == DirectionEnum.RIGHT && this.x < this.screen.getWidth() - this.width){
      this.x += this.velocity;
    }
    if(direction == DirectionEnum.LEFT && this.x > 0){
      this.x -= this.velocity;
    }
    if(direction == DirectionEnum.UP && this.y < this.screen.getHeight() - this.height){
      this.y += this.velocity;
    }
    if(direction == DirectionEnum.DOWN && this.y > 0){
      this.y -= this.velocity;
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getVelocity() {
    return velocity;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public boolean getAttack() {
    return attack;
  }

  private boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
		return x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2;
	}

  public String printObject() {
    return ("X="+x+";Y="+y+";width="+width+";height="+height+";velocity="+velocity);
  }

  @Override
  public String toString() {
    return  "{" + 
            "X="+x+","+
            "Y="+y+","+
            "width="+width+","+
            "height="+height+","+
            "attack="+attack+"}";
  }

}
