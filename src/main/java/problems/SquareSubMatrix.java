package problems;

public class SquareSubMatrix {

  /**
   * Given a 2D boolean array, find the largest square subarray of true values. You should return
   * the side length of the largest square subarray.
   */
  public static void main(String[] args) {
    boolean[][] matrix = new boolean[3][4];

    // first row
    matrix[0][0] = false;
    matrix[0][1] = true;
    matrix[0][2] = false;
    matrix[0][3] = false;

    // second row
    matrix[1][0] = true;
    matrix[1][1] = true;
    matrix[1][2] = true;
    matrix[1][3] = true;

    // third row
    matrix[2][0] = false;
    matrix[2][1] = true;
    matrix[2][2] = true;
    matrix[2][3] = false;

    System.out.println(largestSquareSubMatrixIteratively(matrix));
  }

  // top-down solution w/o memoization
  private static int largestSquareSubMatrix(boolean[][] matrix) {
    int largestSide = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        largestSide = Math.max(largestSquareSubMatrix(matrix, i, j), largestSide);
      }
    }
    return largestSide;
  }

  private static int largestSquareSubMatrix(boolean[][] matrix, int row, int col) {
    if (row == matrix.length || col == matrix[0].length) {
      return 0; // gone over the edge
    }

    if (!matrix[row][col]) {
      return 0; // found a cell that is set to false
    }

    // now look to my right, below me and to my diagonal
    return 1 + Math.min(Math.min(largestSquareSubMatrix(matrix, row, col + 1),
        largestSquareSubMatrix(matrix, row + 1, col + 1)),
        largestSquareSubMatrix(matrix, row + 1, col));
  }

  // top down solution with memoization
  private static int largestSquareSubMatrixWithCache(boolean[][] matrix) {
    int[][] cache = new int[matrix.length][matrix[0].length];
    int largestSide = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        largestSide = Math.max(largestSquareSubMatrix(matrix, i, j), largestSide);
      }
    }
    return largestSide;
  }

  private static int largestSquareSubMatrixWithCache(boolean[][] matrix, int[][] cache, int
      row, int col) {

    if (cache[row][col] == 0) {
      if (row == matrix.length || col == matrix[0].length) {
        return 0; // gone over the edge
      }

      if (!matrix[row][col]) {
        return 0; // found a cell that is set to false
      }

      // now look to my right, below me and to my diagonal
      cache[row][col] = 1 + Math.min(Math.min(largestSquareSubMatrix(matrix, row, col + 1),
          largestSquareSubMatrix(matrix, row + 1, col + 1)),
          largestSquareSubMatrix(matrix, row + 1, col));
    }

    return cache[row][col];
  }

  // bottom-up solution that uses iteration
  private static int largestSquareSubMatrixIteratively(boolean[][] matrix) {

    int largestSide = Integer.MIN_VALUE;
    int[][] dpTable = new int[matrix.length][matrix[0].length];

    // first fill in the last row and column of dpTable
    fillLastRow(matrix, dpTable);
    fillLastColumn(matrix, dpTable);

    // then fill in the rest starting from the bottom of dpTable
    for (int i = matrix.length - 2; i >= 0; i--) {
      for (int j = matrix[i].length - 2; j >= 0; j--) {
        if (matrix[i][j]) {
          dpTable[i][j] =
              1 + (Math.min(Math.min(dpTable[i][j + 1], dpTable[i + 1][j]), dpTable[i + 1][j + 1]));
        }
        largestSide = Math.max(dpTable[i][j], largestSide);
      }
    }

    return largestSide;
  }

  private static void fillLastRow(final boolean[][] matrix, final int[][] dpTable) {
    boolean[] lastRow = matrix[matrix.length - 1];
    for (int i = 0; i < lastRow.length; i++) {
      if (lastRow[i]) {
        dpTable[dpTable.length - 1][i] = 1;
      }
    }
  }

  private static void fillLastColumn(final boolean[][] matrix, final int[][] dpTable) {
    int colIndex = matrix[0].length - 1;
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i][colIndex]) {
        dpTable[i][colIndex] = 1;
      }
    }
  }

}


