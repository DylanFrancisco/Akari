package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

public class ControllerImpl implements AlternateMvcController {

  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (model.getActivePuzzleIndex() == model.getPuzzleLibrarySize() - 1) {
      model.setActivePuzzleIndex(0);
    } else {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() == 0) {
      model.setActivePuzzleIndex(model.getPuzzleLibrarySize() - 1);
    } else {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int random = model.getActivePuzzleIndex();
    while (random == model.getActivePuzzleIndex()) {
      random = (int) (Math.random() * model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(random);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  @Override
  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return model.getPuzzleLibrarySize();
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  @Override
  public int getPuzzleHeight() {
    return model.getActivePuzzle().getHeight();
  }

  @Override
  public int getPuzzleWidth() {
    return model.getActivePuzzle().getWidth();
  }

  @Override
  public int getClue(int r, int c) {
    return model.getActivePuzzle().getClue(r, c);
  }

  @Override
  public CellType getCellType(int r, int c) {
    return model.getActivePuzzle().getCellType(r, c);
  }

  @Override
  public String getName() {
    int index = model.getActivePuzzleIndex();
    switch (index) {
      case 0:
        return "Puzzle 1";
      case 1:
        return "Puzzle 2";
      case 2:
        return "Puzzle 3";
      case 3:
        return "Puzzle 4";
      case 4:
        return "Puzzle 5";
    }
    return "Puzzles!";
  }
}
