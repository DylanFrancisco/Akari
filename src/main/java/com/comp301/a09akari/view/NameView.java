package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NameView implements FXComponent {
  private final AlternateMvcController controller;

  public NameView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();

    Text title = new Text(controller.getName());
    Text outOf = new Text(" out of " + controller.getPuzzleLibrarySize());
    title.setFont(Font.font("Verdana", 10));
    outOf.setFont(Font.font("Verdana", 10));

    layout.getChildren().add(title);
    layout.getChildren().add(outOf);
    return layout;
  }
}
