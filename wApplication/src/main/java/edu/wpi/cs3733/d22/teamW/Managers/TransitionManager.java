package edu.wpi.cs3733.d22.teamW.Managers;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TransitionManager {
  public interface OnCompleteFunction {
    void onComplete();
  }

  public enum Transitions {
    TranslateYIn,
    TranslateYOut,
    TranslateY,
    FadeIn,
    FadeOut,
    Fade
  }

  public static Transition createTransition(Transitions transition, Pane pane) {
    return createTransition(transition, pane, 250);
  }

  public static Transition createTransition(Transitions transition, Pane pane, double duration) {
    return createTransition(transition, pane, duration, null);
  }

  public static Transition createTransition(
      Transitions transition, Pane pane, OnCompleteFunction onCompleteFunction) {
    return createTransition(transition, pane, 250, onCompleteFunction);
  }

  public static Transition createTransition(
      Transitions transition, Pane pane, double duration, OnCompleteFunction onCompleteFunction) {
    Transition t = null;
    switch (transition) {
      case TranslateY:
      case TranslateYIn:
        t = new TranslateTransition(Duration.millis(duration), pane);
        ((TranslateTransition) t).setFromX(pane.getHeight());
        ((TranslateTransition) t).setByY(-pane.getHeight());
        break;
      case TranslateYOut:
        t = new TranslateTransition(Duration.millis(duration), pane);
        ((TranslateTransition) t).setFromX(0);
        ((TranslateTransition) t).setByY(-pane.getHeight());
        break;
      case Fade:
      case FadeIn:
        t = new FadeTransition(Duration.millis(duration), pane);
        ((FadeTransition) t).setFromValue(0);
        ((FadeTransition) t).setToValue(1);
        break;
      case FadeOut:
        t = new FadeTransition(Duration.millis(duration), pane);
        ((FadeTransition) t).setFromValue(1);
        ((FadeTransition) t).setToValue(0);
        break;
    }
    if (onCompleteFunction != null) {
      t.setOnFinished(e -> onCompleteFunction.onComplete());
    }
    return t;
  }
}
