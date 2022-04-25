package edu.wpi.cs3733.d22.teamW.Managers;

import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Random;

public class BackgroundManager {

  private AnchorPane parent;

  private static class Instance {
    public static final BackgroundManager instance = new BackgroundManager();
  }

  private BackgroundManager() {}

  public static BackgroundManager getInstance() {
    return Instance.instance;
  }

  public void initialize(AnchorPane pane) {
    parent = pane;
    parent.toBack();
  }

  public void setContent(Pane pane) {
    if (parent.getChildren().size() > 0 && parent.getChildren().get(parent.getChildren().size() - 1).equals(pane)) {
      System.out.println("No Background Changed");
      return;
    }

    Object bn = pane.getProperties().get("BackgroundName");
    if (bn == null) {
      bn = "No Name Set";
    }
    System.out.println("Background Set: " + bn);

    AnchorPane.setTopAnchor(pane, 0.0);
    AnchorPane.setBottomAnchor(pane, 0.0);
    AnchorPane.setLeftAnchor(pane, 0.0);
    AnchorPane.setRightAnchor(pane, 0.0);

    if (!parent.getChildren().isEmpty()) {
      final Pane prevF = (Pane) parent.getChildren().get(parent.getChildren().size() - 1);
      Transition tOut =
          TransitionManager.createTransition(
                  TransitionManager.Transitions.FadeOut,
                  prevF,
                  () -> parent.getChildren().remove(prevF));
      tOut.play();
    }
    Transition tIn = TransitionManager.createTransition(TransitionManager.Transitions.FadeIn, pane);
    tIn.play();

    if (!parent.getChildren().contains(pane)){
      parent.getChildren().add(pane);
    }
  }

  public Pane getContent() {
    if (parent.getChildren().isEmpty()) {
      throw new UnsupportedOperationException();
    }
    return (Pane) parent.getChildren().get(parent.getChildren().size() - 1);
  }

  public void requestFocus() {
    getContent().requestFocus();
  }

  public void blur() {
    parent.setEffect(new GaussianBlur(7.5));
  }

  public void unBlur() {
    parent.setEffect(null);
  }

  public enum DefaultBackgrounds {
    HospitalImage,
    White,
    Shapes;

    public Pane content = null;

    public Pane getContent() {
      if (content == null) {
        switch (this) {
          case HospitalImage:
            content = new Pane();
            content.getStylesheets()
                    .add("/edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/paneHospital.css");
            content.getStyleClass().add("image");
            content.getProperties().put("BackgroundName", "Hospital Image");
            break;
          case White:
            content = new Pane();
            content.setStyle("-fx-background-color: white");
            content.getProperties().put("BackgroundName", "White");
            break;
          case Shapes:
            createShapesBG(WindowManager.getInstance().getPrimaryStage().getWidth(), WindowManager.getInstance().getPrimaryStage().getHeight());
        }
      }
      return content;
    }

    private static void createShapesBG(double width, double height) {
      Shapes.content = new AnchorPane();
      Shapes.content.getProperties().put("BackgroundName", "Shapes");

      Random r = new Random();

      for (int i = 0; i < 5; i++) {
        Shape s = null;
        switch(r.nextInt() % 2) {
          case 0:
            double x = r.nextDouble() * width;
            double y = r.nextDouble() * height;

            Circle c = new Circle(x, y, r.nextDouble() * 25 + 50);
            s = c;
            break;
          case 1:
            Point2D first = new Point2D(0, 0);
            Point2D second = new Point2D(r.nextDouble() * 100 + 50, r.nextDouble() * 100 + 50);
            Point2D third = first.midpoint(second).add(new Point2D(-second.getY(), second.getX()));
            Polygon p = new Polygon(first.getX(), first.getY(), second.getX(), second.getY(), third.getX(), third.getY());
            p.setLayoutX(r.nextDouble() * width);
            p.setLayoutY(r.nextDouble() * height);
            s = p;
            break;
        }

        s.setFill(Color.TRANSPARENT);
        s.setStroke(Color.web("#009ca8"));
        s.setStrokeWidth(10);
        AnchorPane.setTopAnchor(s, s.getBoundsInParent().getMinY());
        AnchorPane.setLeftAnchor(s, s.getBoundsInParent().getMinX());
        AnchorPane.setBottomAnchor(s, height - s.getBoundsInParent().getMaxY());
        AnchorPane.setRightAnchor(s, width - s.getBoundsInParent().getMaxX());
        Shapes.content.getChildren().add(0, s);
     }
    }

  }
}
