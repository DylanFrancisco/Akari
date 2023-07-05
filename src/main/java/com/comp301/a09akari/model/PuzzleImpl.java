package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {

  private int[][] puzzleBoard;
  private int height;
  private int width;

  public PuzzleImpl(int[][] board) {
    if (board != null) {
      this.puzzleBoard = board;
      this.height = board.length;
      this.width = board[0].length;
    }
  }

  private boolean cellInPuzzle(int r, int c) {
    return r <= width && c <= height && r >= 0 && c >= 0;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (cellInPuzzle(r, c)) {
      int cell = puzzleBoard[r][c];
      if (cell <= 4) {
        return CellType.CLUE;
      } else if (cell == 5) {
        return CellType.WALL;
      } else {
        return CellType.CORRIDOR;
      }
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (getCellType(r, c) == CellType.CLUE) {
      return puzzleBoard[r][c];
    } else {
      throw new IllegalArgumentException();
    }
  }
}
