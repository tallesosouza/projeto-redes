package com.terminalroot.game.models;

import java.io.Serializable;

public class Element implements Serializable {
  protected int X;  
  protected int Y;
  protected int velocity;
  protected int width;
  protected int height;
  protected boolean attack;
  protected transient ScreenGame screen;
  private static final long serialVersionUID = 1L;

  public void initialize(int initX, int initY, int initVelocity, int initWidth, int iniHeight, ScreenGame screenGame){
    X = initX;
    Y = initY;
    this.velocity = initVelocity;
    this.width = initWidth;
    this.height = iniHeight;
    this.attack = false;
    this.screen = screenGame;
  }

  public boolean checkCollide(Element elementCheck){
    return this.collide(elementCheck.X, elementCheck.Y, elementCheck.width, elementCheck.height, X, Y, width, height);
  }

  public void moveElement(DirectionEnum direction){
    System.out.println(this.velocity);
    if(direction == DirectionEnum.RIGHT && this.X < this.screen.getWidth() - this.width){
      this.X += this.velocity;
    }
    if(direction == DirectionEnum.LEFT && this.X > 0){
      this.X -= this.velocity;
    }
    if(direction == DirectionEnum.UP && this.Y < this.screen.getHeight() - this.height){
      this.Y += this.velocity;
    }
    if(direction == DirectionEnum.DOWN && this.Y > 0){
      this.Y -= this.velocity;
    }
  }

  public int getX() {
    return X;
  }

  public int getY() {
    return Y;
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
    return ("X="+X+";Y="+Y+";width="+width+";height="+height+";velocity="+velocity);
  }

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
