package com.terminalroot.game.models;

import java.io.Serializable;
import java.util.List;

public class AllElementsModel implements Serializable {

  protected List<Element> eminies;
  protected Element nave;
  protected Element missible;
  protected transient ScreenGame screen;
  protected StatusGameEnum status;
  protected static final long serialVersionUID = 1L;

  public AllElementsModel() { }

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

  public StatusGameEnum getStatus() {
    return this.status;
  }

  @Override
  public String toString() {
    return "Nave={X="+this.nave.getX()+",Y="+this.nave.getY()+",width="+this.nave.getWidth()+"}";
  }
}
