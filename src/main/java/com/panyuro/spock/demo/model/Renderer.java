package com.panyuro.spock.demo.model;

import java.awt.*;

public class Renderer {
  private Palette palette;

  public Renderer(Palette palette) {
    this.palette = palette;
  }

  public void drawLine(){

  }

  public Color getForeGroundColor() {
    return palette.getPrimaryColor();
  }
}
