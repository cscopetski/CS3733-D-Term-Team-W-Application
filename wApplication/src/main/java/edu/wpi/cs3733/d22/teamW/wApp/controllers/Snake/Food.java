package edu.wpi.cs3733.d22.teamW.wApp.controllers.Snake;

import java.util.Random;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food {
  private Position position;
  private Rectangle rectangle;
  private final Random random = new Random();
  private int size;

  public final double borderSize = 500;
  public final double center = 200;
  public final int Min = (int) (((borderSize / 2) - 50) / 50);
  public final int Max = (int) ((2 * center + (borderSize / 2) + 50) / 50);

  public Food(double xPos, double yPos, AnchorPane pane, double size) {
    System.out.println(rectangle);
    this.size = (int) size;

    position = new Position(xPos, yPos);
    if (rectangle != null) {
      pane.getChildren().remove(rectangle);
    }
    rectangle = new Rectangle(position.getXPos(), position.getYPos(), size, size);
    rectangle.setFill(Color.rgb(0, 139, 176, 1.0));
    pane.getChildren().add(rectangle);
    System.out.println(rectangle);
  }

  public Position getPosition() {
    return position;
  }

  public void moveFood() {
    int pos1 = size * (random.nextInt(Max - Min) + Min);
    int pos2 = size * (random.nextInt(Max - Min) + Min);
    rectangle.setX(pos1);
    rectangle.setY(pos2);

    position.setXPos(pos1);
    position.setYPos(pos2);
  }
}
