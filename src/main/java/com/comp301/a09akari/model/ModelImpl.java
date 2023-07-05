package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private PuzzleLibrary library;
  private int puzzleIndex;
  private boolean[][] lamps;
  private List<ModelObserver> observerList;
  private Puzzle puzzle;
  private int height;

  private int width;

  public ModelImpl(PuzzleLibrary library) {
    if (library != null) {
      this.library = library;
      this.puzzleIndex = 0;
      this.puzzle = library.getPuzzle(puzzleIndex);
      this.height = puzzle.getHeight();
      this.width = puzzle.getWidth();
      this.lamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
      this.observerList = new ArrayList<>();
    }
  }

  @Override
  public void addLamp(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else if (r < 0 || c < 0 || r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    } else if (lamps[r][c]) {
    } else {
      lamps[r][c] = true;
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else if (r < 0 || c < 0 || r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    } else {
      lamps[r][c] = false;
      notifyObservers();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else if (r < 0 || c < 0 || r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }

    if (lamps[r][c]) {
      return true;
    }

    for (int row = r + 1; (row < height); row++) {
      if (puzzle.getCellType(row, c) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(row, c)) {
          return true;
        }
      }
    }

    for (int row = r - 1; row >= 0; row--) {
      if (puzzle.getCellType(row, c) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(row, c)) {
          return true;
        }
      }
    }

    for (int column = c + 1; column < width; column++) {
      if (puzzle.getCellType(r, column) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(r, column)) {
          return true;
        }
      }
    }

    for (int column = c - 1; column >= 0; column--) {
      if (puzzle.getCellType(r, column) != CellType.CORRIDOR) {
        break;
      } else {
        if (isLamp(r, column)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else if (r < 0 || c < 0 || r > lamps.length || c > lamps[0].length) {
      throw new IndexOutOfBoundsException();
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    } else if (r >= height || c >= width) {
      throw new IndexOutOfBoundsException();
    }
    if (!lamps[r][c]) {
      return false;
    }

    for (int row = r + 1; row < height; row++) {
      if (puzzle.getCellType(row, c)
          != CellType.CORRIDOR) { // checking elements south of the current cell for lit lamps
        break;
      } else {
        if (isLamp(row, c)) {
          return true;
        }
      }
    }

    for (int row = r - 1; row >= 0; row--) {
      if (puzzle.getCellType(row, c)
          != CellType.CORRIDOR) { // checking elements north of the current cell for lit lamps
        break;
      } else {
        if (isLamp(row, c)) {
          return true;
        }
      }
    }

    for (int column = c + 1; column < width; column++) {
      if (puzzle.getCellType(r, column)
          != CellType.CORRIDOR) { // checking elements east of the current cell for lit lamps
        break;
      } else {
        if (isLamp(r, column)) {
          return true;
        }
      }
    }

    for (int column = c - 1; column >= 0; column--) {
      if (puzzle.getCellType(r, column)
          != CellType.CORRIDOR) { // checking elements west of the current cell for lit lamps
        break;
      } else {
        if (isLamp(r, column)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return puzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException();
    }
    this.puzzleIndex = index;
    this.puzzle = library.getPuzzle(puzzleIndex);
    this.lamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
    this.height = puzzle.getHeight();
    this.width = puzzle.getWidth();
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    this.lamps = new boolean[height][width];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    Puzzle tempPuzzle = getActivePuzzle();
    for (int r = 0; r < tempPuzzle.getHeight(); r++) {
      for (int c = 0; c < tempPuzzle.getWidth(); c++) {
        if (tempPuzzle.getCellType(r, c) == CellType.CLUE && !isClueSatisfied(r, c)) {
          return false;
        }
        if (tempPuzzle.getCellType(r, c) == CellType.CORRIDOR) {
          if ((isLamp(r, c)) && isLampIllegal(r, c) || !isLit(r, c)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    } else if (r < 0 || c < 0 || r >= puzzle.getHeight() || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    int count = 0;
    int numLamps = puzzle.getClue(r, c);

    if (puzzle.getCellType(r, c) == CellType.CLUE) {
      if (c > 0 && puzzle.getCellType(r, c - 1) == CellType.CORRIDOR) {
        if (isLamp(r, c - 1)) {
          count++;
        }
      }
      if (c < width - 1 && puzzle.getCellType(r, c + 1) == CellType.CORRIDOR) {
        if (isLamp(r, c + 1)) {
          count++;
        }
      }
      if (r > 0 && puzzle.getCellType(r - 1, c) == CellType.CORRIDOR) {
        if (isLamp(r - 1, c)) {
          count++;
        }
      }
      if (r < height - 1 && puzzle.getCellType(r + 1, c) == CellType.CORRIDOR) {
        if (isLamp(r + 1, c)) {
          count++;
        }
      }
    }
    return count == numLamps;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observerList.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observerList.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver observer : observerList) {
      observer.update(this);
    }
  }
}
