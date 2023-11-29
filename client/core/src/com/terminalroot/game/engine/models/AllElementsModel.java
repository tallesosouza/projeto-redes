package src.com.terminalroot.game.engine.models;

import java.io.Serializable;
import java.util.List;

import src.com.terminalroot.game.engine.Element;

public class AllElementsModel implements Serializable {

  public List<Element> eminies;
  public Element nave;
  public Element missible;
  public StatusGameEnum status;
  protected static final long serialVersionUID = 1L;

  public AllElementsModel() { }

  public Element getNave() {
    return this.nave;
  }

  public Element getMissible() {
    return this.missible;
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
