package com.terminalroot.game.models;

public class ScreenGame {
  private final int width;
  private final int height;

  public ScreenGame(int maxWidth, int maxHeight) {
    width = maxWidth;
    height = maxHeight;
  }

  public int getWidth(){
    return width;
  }

  public int getHeight() {
    return height;
  }
}
