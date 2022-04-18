package edu.wpi.cs3733.d22.teamW.wApp.controllers.PacMan;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class PowerPellet {
  private ImageView img;
  private Circle circ;

  public PowerPellet(ImageView img, Circle circ) {
    this.img = img;
    this.circ = circ;
  }

  public void setImg(ImageView img) {
    this.img = img;
  }

  public ImageView getImg() {
    return img;
  }

  public void setCirc(Circle circ) {
    this.circ = circ;
  }

  public Circle getCirc() {
    return circ;
  }
}
