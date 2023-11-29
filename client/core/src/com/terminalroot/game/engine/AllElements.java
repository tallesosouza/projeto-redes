package src.com.terminalroot.game.engine;

import java.util.ArrayList;
import java.util.List;

public class AllElements extends AllElementsModel {
  protected static final long serialVersionUID = 1L;

  public AllElements() {
  }
  
  public void initialize () {
    this.nave = new Element();
    this.nave.initialize(0,0,10,110,110);
    this.missible = new Element();
    this.missible.initialize(0,0,10,110,110);
    this.eminies = new ArrayList<>();
    this.status = StatusGameEnum.CONNECTING;
    // this.tNave = new Texture("spaceship.png");
    // this.tMissible = new Texture("missile.png");
    // this.tEminy = new Texture("enemy.png");
    // this.sNave = new Sprite(tNave);
    // this.sMissible = new Sprite(tMissible);
  }

  public void dispose () {
    // tNave.dispose();
    // tMissible.dispose();
    // tEminy.dispose();
  }

  public boolean checkStatus(StatusGameEnum newStatus) {
    return this.status == newStatus;
  }

  public StatusGameEnum getStatus() {
    return this.status;
  }

  public void setStatus(StatusGameEnum newStatus) {
    this.status = newStatus;
  }

  public void setAllElements(AllElements elements) {
    this.nave = elements.nave;
    this.eminies = elements.eminies;
    this.missible = elements.missible;
  }

  public Element getNave() {
    return this.nave;
  }

  public List<Element> getEminies() {
    return this.eminies;
  }

  public Element getMissible() {
    return this.missible;
  }

  public void setNave(Element newNave) {
    this.nave = newNave;
  }

  @Override
  public String toString() {
    return "Nave={X="+this.nave.getX()+",Y="+this.nave.getY()+",width="+this.nave.getWidth()+"}";
  }

}
