package com.panyuro.spock.demo.model;

public class ShapeFactory {
  private Renderer renderer;

  public ShapeFactory(Renderer renderer) {
    this.renderer = renderer;
  }

  public Polygon createDefaultPolygon() {
    return  new Polygon(4, renderer);
  }
}
