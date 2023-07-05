package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {

  private final AlternateMvcController controller;

  public View(AlternateMvcController controller) {
    if (controller == null) {
      throw new IllegalArgumentException();
    }
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox format = new VBox();

    ControlsView controlsView = new ControlsView(controller);
    format.getChildren().add(controlsView.render());

    PuzzleView puzzleView = new PuzzleView(controller);
    format.getChildren().add(puzzleView.render());

    NameView nameView = new NameView(controller);
    format.getChildren().add(nameView.render());

    return format;
  }
}
