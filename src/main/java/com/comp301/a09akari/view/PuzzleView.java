package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PuzzleView implements FXComponent {
  private final AlternateMvcController controller;

  public PuzzleView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane gridPane = new GridPane();
    gridPane.gridLinesVisibleProperty();
    Puzzle puzzle = controller.getActivePuzzle();
    int height = puzzle.getHeight();
    int width = puzzle.getWidth();

    for (int i = 0; i < height; i++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setMaxHeight(50);
      rowConstraints.setMinHeight(50);
      gridPane.getRowConstraints().add(rowConstraints);
    }

    for (int j = 0; j < width; j++) {
      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setMaxWidth(50);
      columnConstraints.setMinWidth(50);
      gridPane.getColumnConstraints().add(columnConstraints);
    }

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("YOU WON!");
        alert.setHeaderText("YOU WON!");
        if (puzzle.getCellType(r, c) == CellType.CLUE) {
          StackPane clue = new StackPane();
          Label clueLabel = new Label(Integer.toString(puzzle.getClue(r, c)));
          clueLabel.setTextFill(Color.WHITE);
          clue.getChildren().add(clueLabel);
          gridPane.add(clue, c, r);
          if (controller.isClueSatisfied(r, c)) {
            clue.getStyleClass().add("clueSolved");
          } else {
            clue.getStyleClass().add("clue");
          }
        } else if (puzzle.getCellType(r, c) == CellType.WALL) {
          StackPane wall = new StackPane();
          wall.getStyleClass().add("wall");
          gridPane.add(wall, c, r);
        } else {
          StackPane tile = new StackPane();
          tile.setMinWidth(50);
          tile.setMinHeight(50);
          tile.setMaxWidth(50);
          tile.setMaxHeight(50);
          //          Button btn = new Button();
          //          btn.setMinWidth(50);
          //          btn.setMinHeight(50);
          //          btn.setMaxWidth(50);
          //          btn.setMaxHeight(50);
          Image image = new Image("light-bulb.png");
          ImageView light = new ImageView(image);
          light.setFitHeight(50);
          light.setFitWidth(50);
          //          tile.getChildren().add(btn);
          gridPane.add(tile, c, r);

          int r2 = r;
          int c2 = c;
          tile.setOnMouseClicked(actionEvent -> controller.clickCell(r2, c2));
          //          btn.setOnAction(actionEvent -> controller.clickCell(r2, c2));

          if (controller.isLit(r, c)) {
            //            btn.getStyleClass().add("tile");
            tile.getStyleClass().add("lit");
            //            if (controller.isLamp(r, c)) {
            //              btn.getStyleClass().add("lamp");
            //            }
            if (controller.isSolved()) {
              alert.show();
              alert.close();
            }
          }
          if (controller.isLamp(r, c)) {
            tile.getStyleClass().add("lamp");
            tile.getChildren().add(light);
            if (controller.isLampIllegal(r, c)) {
              tile.getStyleClass().add("illegalLamp");
            }
            if (controller.isSolved()) {
              alert.show();
              alert.close();
            }
          }

          /*if (controller.isLamp(r, c)) {
          //            tile.getStyleClass().add("tile");
          //            tile.getChildren().add(light);
          //            btn.getStyleClass().add("tile");
                        btn.getStyleClass().add("lamp");
                      if (controller.isLampIllegal(r, c)) {
                        btn.getStyleClass().add("illegalLamp");
                      }

                    } else if (controller.isLit(r, c)) {
                      tile.getStyleClass().add("tile");
                      tile.getStyleClass().add("lit");
                    }*/
        }
      }
    }
    gridPane.setGridLinesVisible(true);
    gridPane.setAlignment(Pos.BOTTOM_CENTER);
    return gridPane;
  }
}
