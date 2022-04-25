package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatCardHBox extends HBox {
  private boolean unread;
  private Integer chatID;

  /** Creates an {@code HBox} layout with {@code spacing = 0}. */
  public ChatCardHBox() {
    super();
  }

  /**
   * Creates an {@code HBox} layout with the specified spacing between children.
   *
   * @param spacing the amount of horizontal space between each child
   */
  public ChatCardHBox(double spacing) {
    super(spacing);
  }

  /**
   * Creates an {@code HBox} layout with {@code spacing = 0}.
   *
   * @param children the initial set of children for this pane
   * @since JavaFX 8.0
   */
  public ChatCardHBox(Node... children) {
    super(children);
  }

  /**
   * Creates an {@code HBox} layout with the specified spacing between children.
   *
   * @param spacing the amount of horizontal space between each child
   * @param children the initial set of children for this pane
   * @since JavaFX 8.0
   */
  public ChatCardHBox(double spacing, Node... children) {
    super(spacing, children);
  }
}
