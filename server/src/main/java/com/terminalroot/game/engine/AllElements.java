package com.terminalroot.game.models;

import java.util.ArrayList;
import java.util.List;

public class AllElements extends AllElementsModel {
  protected static final long serialVersionUID = 1L;
  
  public AllElements(ScreenGame screenGame) {
    this.screen = screenGame;
  }

  public void initialize() {
    this.eminies = new ArrayList<>();
    this.status = StatusGameModel.WAITING;
    
    this.nave = new Element();
    // this.nave.initialize(0,0,10,110,110,this.screen);
    
    this.missible = new Element();
    this.nave.initialize(0,0,10,110,110,this.screen);
  }

  public void startOrUnPauseGame(){
    if(this.status == StatusGameModel.WAITING){
      this.startGame();
    }else if(this.status == StatusGameModel.PAUSE){
      this.unPauseGame();
    }
    this.status = StatusGameModel.IN_PROGRESS;
  }

  public void moveNave(DirectionModel direction) {
    this.nave.moveElement(direction);
    System.out.println(this.nave.toString());
  }

  public void lounchMissible() {
    // this.nave.moveElement(direction);
  }

  public Object getNewFrame() {
    return this.nave;
    // return null;
  }

  public Element getNave() {
    return this.nave;
  }

  public Element getMissible() {
    return this.missible;
  }

  public ScreenGame getScreen() {
    return this.screen;
  }

  public List<Element> getEminies() {
    return this.eminies;
  }

  public void printPositions () {
    System.out.println("Nave: " + this.nave.printObject());
    System.out.println("Missible: " + this.missible.printObject());
  }

  public StatusGameModel getStatus() {
    return this.status;
  }

  public void setStatus(StatusGameModel newStatus) {
    this.status = newStatus;
  }

  public boolean checkStatus(StatusGameModel newStatus) {
    return this.status == newStatus;
  }

  public void editStatus(StatusGameModel newStatus) {
    if(newStatus == StatusGameModel.FINISHED){
      this.status = StatusGameModel.FINISHED;
    }
  }

  private void startGame(){

  }

  private void unPauseGame() {

  }

  // @Override
  // public String toString() {
  //   return "Nave={X="+this.nave.getX()+",Y="+this.nave.getY()+",width="+this.nave.getWidth()+"}";
  // }
}
