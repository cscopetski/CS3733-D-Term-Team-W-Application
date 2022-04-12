package edu.wpi.cs3733.d22.teamW.wApp.controllers.Snake;

import edu.wpi.cs3733.d22.teamW.wApp.controllers.LoadableController;
import edu.wpi.cs3733.d22.teamW.wMid.SceneManager;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SnakeController extends LoadableController {

  // A snake body part is 50x50
  private final Double snakeSize = 50.;
  // The head of the snake is created, at position (250,250)
  private Rectangle snakeHead;
  private ImageView image;
  // x and y position of the snake head different from starting position
  double xPos;
  double yPos;

  public final double borderSize = 500;
  public final double center = 200;
  public final double xOffset = 210;
  public final double Min = center - (borderSize / 2);
  public final double Max = center + (borderSize / 2);

  // Food
  Food food;

  // Direction snake is moving at start
  private Position.Direction direction;

  // List of all position of thew snake head
  private final List<Position> positions = new ArrayList<>();

  // List of all snake body parts
  private final ArrayList<Rectangle> snakeBody = new ArrayList<>();

  // Game ticks is how many times the snake have moved
  private int gameTicks;

  @FXML private AnchorPane anchorPane;
  @FXML private AnchorPane gameBorder;
  @FXML private Label score;
  @FXML private Label loss;

  private int counter = 0;

  Timeline timeline;

  private boolean canChangeDirection;

  @FXML
  void start(ActionEvent event) {

    loss.setVisible(false);
    counter = 0;
    score.setText("Score: " + counter);

    for (Rectangle snake : snakeBody) {
      anchorPane.getChildren().remove(snake);
    }

    gameTicks = 0;
    positions.clear();
    snakeBody.clear();
    snakeHead = new Rectangle(center + xOffset, center, snakeSize, snakeSize);
    // First snake tail created behind the head of the snake
    Rectangle snakeTail =
        new Rectangle(snakeHead.getX() - snakeSize, snakeHead.getY(), snakeSize, snakeSize);
    xPos = snakeHead.getLayoutX();
    yPos = snakeHead.getLayoutY();
    direction = Position.Direction.RIGHT;
    canChangeDirection = true;
    food.moveFood();

    snakeBody.add(snakeHead);
    // snakeHead.setFill(Color.TRANSPARENT);
    snakeHead.setFill(Color.RED);
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();

    snakeBody.add(snakeTail);

    anchorPane.getChildren().addAll(snakeHead, snakeTail);
  }

  // Change position with key pressed
  @FXML
  void moveSquareKeyPressed(KeyEvent event) {
    if (canChangeDirection) {
      if (event.getCode().equals(KeyCode.W) && direction != Position.Direction.DOWN) {
        direction = Position.Direction.UP;
      } else if (event.getCode().equals(KeyCode.S) && direction != Position.Direction.UP) {
        direction = Position.Direction.DOWN;
      } else if (event.getCode().equals(KeyCode.A) && direction != Position.Direction.RIGHT) {
        direction = Position.Direction.LEFT;
      } else if (event.getCode().equals(KeyCode.D) && direction != Position.Direction.LEFT) {
        direction = Position.Direction.RIGHT;
      }
      canChangeDirection = false;
    }
  }

  // Snake head is moved in the direction specified
  private void moveSnakeHead(Rectangle snakeHead) {
    if (direction.equals(Position.Direction.RIGHT)) {
      xPos = xPos + snakeSize;
      snakeHead.setTranslateX(xPos);
    } else if (direction.equals(Position.Direction.LEFT)) {
      xPos = xPos - snakeSize;
      snakeHead.setTranslateX(xPos);
    } else if (direction.equals(Position.Direction.UP)) {
      yPos = yPos - snakeSize;
      snakeHead.setTranslateY(yPos);
    } else if (direction.equals(Position.Direction.DOWN)) {
      yPos = yPos + snakeSize;
      snakeHead.setTranslateY(yPos);
    }

    // image.setX(xPos);
    // image.setY(yPos);
  }

  // A specific tail is moved to the position of the head x game ticks after the head was there
  private void moveSnakeTail(Rectangle snakeTail, int tailNumber) {
    double yPos = positions.get(gameTicks - tailNumber + 1).getYPos() - snakeTail.getY();
    double xPos = positions.get(gameTicks - tailNumber + 1).getXPos() - snakeTail.getX();
    snakeTail.setTranslateX(xPos);
    snakeTail.setTranslateY(yPos);
  }

  // New snake tail is created and added to the snake and the anchor pane
  private void addSnakeTail() {
    Rectangle snakeTail =
        new Rectangle(
            snakeBody.get(1).getX() + xPos + snakeSize,
            snakeBody.get(1).getY() + yPos,
            snakeSize,
            snakeSize);
    snakeBody.add(snakeTail);
    anchorPane.getChildren().add(snakeTail);
  }

  public boolean checkIfGameIsOver(Rectangle snakeHead) {
    if (xPos <= Min || xPos > Max || yPos <= Min || yPos > Max) {
      System.out.println("Game_over");
      loss.setVisible(true);
      return true;
    } else if (snakeHitItSelf()) {
      loss.setVisible(true);
      return true;
    }
    return false;
  }

  public boolean snakeHitItSelf() {
    int size = positions.size() - 1;
    if (size > 2) {
      for (int i = size - snakeBody.size(); i < size; i++) {
        if (positions.get(size).getXPos() == (positions.get(i).getXPos())
            && positions.get(size).getYPos() == (positions.get(i).getYPos())) {
          System.out.println("Hit");
          return true;
        }
      }
    }
    return false;
  }

  private void eatFood() {
    if (xPos + snakeHead.getX() == food.getPosition().getXPos()
        && yPos + snakeHead.getY() == food.getPosition().getYPos()) {
      System.out.println("Eat food");
      foodCantSpawnInsideSnake();
      addSnakeTail();
      counter++;
      score.setText("Score: " + counter);
    }
  }

  private void foodCantSpawnInsideSnake() {
    food.moveFood();
    while (isFoodInsideSnake()) {
      food.moveFood();
    }
  }

  private boolean isFoodInsideSnake() {
    int size = positions.size();
    if (size > 2) {
      for (int i = size - snakeBody.size(); i < size; i++) {
        if (food.getPosition().getXPos() == (positions.get(i).getXPos())
            && food.getPosition().getYPos() == (positions.get(i).getYPos())) {
          System.out.println("Inside");
          return true;
        }
      }
    }
    return false;
  }

  @Override
  protected SceneManager.Scenes GetSceneType() {
    return SceneManager.Scenes.Snake;
  }

  @Override
  public void onLoad() {
    gameBorder.setLayoutX(center + xOffset);
    gameBorder.setLayoutY(center);
    gameBorder.setPrefWidth(borderSize);
    gameBorder.setPrefHeight(borderSize);
    food = new Food(0, 0, anchorPane, snakeSize);
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.seconds(0.2),
                e -> {
                  positions.add(new Position(snakeHead.getX() + xPos, snakeHead.getY() + yPos));
                  moveSnakeHead(snakeHead);
                  for (int i = 1; i < snakeBody.size(); i++) {
                    moveSnakeTail(snakeBody.get(i), i);
                  }
                  canChangeDirection = true;
                  eatFood();
                  gameTicks++;
                  if (checkIfGameIsOver(snakeHead)) {
                    timeline.stop();
                  }
                }));
    // image =  new ImageView(new
    // Image(getClass().getResourceAsStream("edu/wpi/cs3733/d22/teamW/wApp/assets/Icons/wong.png")));
  }

  @Override
  public void onUnload() {
    timeline = null;
    food = null;
    anchorPane.getChildren().clear();
  }
}
