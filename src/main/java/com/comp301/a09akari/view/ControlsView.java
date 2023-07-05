package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlsView implements FXComponent {
  private final AlternateMvcController controller;
  private HBox layout;

  public ControlsView(AlternateMvcController controller) {
    if (controller != null) {
      this.controller = controller;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();

    // Previous Button
    Button previousButton = new Button("Previous");
    previousButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });

    // Next Button
    Button nextButton = new Button("Next");
    nextButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });

    // Reset Button
    Button resetButton = new Button("Reset");
    resetButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });

    // Random Button
    Button randomButton = new Button("Random");
    randomButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });

    layout.getChildren().add(previousButton);
    layout.getChildren().add(nextButton);
    layout.getChildren().add(resetButton);
    layout.getChildren().add(randomButton);

    return layout;
  }
}
