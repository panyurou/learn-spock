package com.panyuro.spock.demo.model;

import com.panyuro.spock.demo.exception.TooFewSidesException;


public class Polygon {
  private final int numberOfSides;
  private Renderer renderer;
  
  public Polygon(int numberOfSides) throws TooFewSidesException {
    if (numberOfSides <= 2){
      throw new TooFewSidesException("You can not have fewer than 3 sides for a polygon");
    }
    this.numberOfSides = numberOfSides;
  }

  public Polygon(int numberOfSides, Renderer renderer) {
    this.numberOfSides = numberOfSides;
    this.renderer = renderer;
  }

  public int getNumberOfSides() {
    return numberOfSides;
  }

  public void draw() {
    for (int i = 0; i < numberOfSides; i++) {
      renderer.drawLine();
    }
  }
}
