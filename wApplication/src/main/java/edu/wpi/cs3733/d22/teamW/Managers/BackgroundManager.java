package edu.wpi.cs3733.d22.teamW.Managers;

import javafx.animation.Transition;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    parent.getChildren().remove(pane);
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

    parent.getChildren().add(pane);
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

  public static class DefaultBackgrounds {
    public static Pane HospitalImage;
    public static Pane White;

    static {
      HospitalImage = new Pane();
      HospitalImage.getStylesheets()
          .add("/edu/wpi/cs3733/d22/teamW/wApp/CSS/UniversalCSS/paneHospital.css");
      HospitalImage.getStyleClass().add("image");
      White = new Pane();
      White.setStyle("-fx-background-color: white");
    }
  }
}
