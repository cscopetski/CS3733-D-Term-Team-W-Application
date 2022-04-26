package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import javafx.scene.Node;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ButtonEmpID extends Button {

  // This class just exists to function as a button but it holds an integer as information
  private Integer empID;

  public ButtonEmpID() {
    super();
  }

  /**
   * Creates a button with the specified text as its label.
   *
   * @param text A text string for its label.
   */
  public ButtonEmpID(String text) {
    super(text);
  }

  /**
   * Creates a button with the specified text and icon for its label.
   *
   * @param text A text string for its label.
   * @param graphic the icon for its label.
   */
  public ButtonEmpID(String text, Node graphic) {
    super(text, graphic);
  }
}
