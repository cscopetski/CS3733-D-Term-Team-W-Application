package edu.wpi.cs3733.d22.teamW.wApp.controllers.Snake;

import java.util.Random;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food {
  private Position position;
  private Rectangle rectangle;
  private Color color = Color.GREEN;
  private AnchorPane pane;
  private Random random = new Random();
  private int size;

  public Food(double xPos, double yPos, AnchorPane pane, double size) {
    System.out.println(rectangle);
    this.pane = pane;
    this.size = (int) size;
    position = new Position(xPos, yPos);
    if (rectangle != null) {
      pane.getChildren().remove(rectangle);
    }
    rectangle = new Rectangle(position.getXPos(), position.getYPos(), size, size);
    rectangle.setFill(color);
    pane.getChildren().add(rectangle);
    System.out.println(rectangle);
  }

  public Position getPosition() {
    return position;
  }

  public void moveFood() {
    getRandomSpotForFood();
  }
  // xPos > 500 || xPos < -50 || yPos < -100 || yPos > 400
  public void getRandomSpotForFood() {
    int positionX = random.nextInt(19 - 7) + 7;
    int positionY = random.nextInt(14 - 2) + 2;
    rectangle.setX(positionX * size);
    rectangle.setY(positionY * size);

    position.setXPos(positionX * size);
    position.setYPos(positionY * size);
    System.out.println((positionX * size) + "---FOOD---" + (positionY * size));
  }
}
