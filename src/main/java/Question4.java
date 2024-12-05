import java.util.Scanner;
import java.util.Stack;

/**
 *  Name: Luke Clarke
 *  Class Group: SD2A
 */

public class Question4  // Flood Fill (Stack, 2D Array)
{
    public enum DIRECTION {NORTH, SOUTH, EAST, WEST}

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        int[][] arr = floodFillStart();
        Scanner kb = new Scanner(System.in);

        // Prompt user for starting row and column
        System.out.print("Enter the starting row (0-9): ");
        int startRow = kb.nextInt();
        System.out.print("Enter the starting column (0-9): ");
        int startCol = kb.nextInt();
        fill(startRow, startCol, arr);
        display(arr);
    }

    /*
        Starter function to create the 2D array and populate it with zeros
     */
    public static int[][] floodFillStart() {
        int[][] arr = new int[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                arr[x][y] = 0;
            }
        }
        return arr;
    }

    /*
        Helper function to display the 2D array
     */
    public static void display(int[][] arr) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.printf("%4d", arr[x][y]);
            }
            System.out.println();
        }
    }

    /*
        Flood fill implementation using a stack and DIRECTION enum
     */
    private static void fill(int r, int c, int[][] arr) {
        // Ensure starting point is within bounds
        if (r < 0 || r >= 10 || c < 0 || c >= 10) {
            System.out.println("Invalid starting position.");
            return;
        }

        Stack<Cell> stack = new Stack<>();
        stack.push(new Cell(r, c));

        int fillValue = 1;
        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int row = current.row;
            int col = current.col;

            if (row >= 0 && row < 10 && col >= 0 && col < 10 && arr[row][col] == 0) {
                arr[row][col] = fillValue++;
                for (DIRECTION dir : DIRECTION.values()) {
                    Cell neighbor = getNeighbor(row, col, dir);
                    stack.push(neighbor);
                }
            }
        }
    }

    /*
        Method to get the neighboring cell based on direction
     */
    private static Cell getNeighbor(int row, int col, DIRECTION direction) {
        switch (direction) {
            case NORTH: return new Cell(row - 1, col);
            case SOUTH: return new Cell(row + 1, col);
            case EAST:  return new Cell(row, col + 1);
            case WEST:  return new Cell(row, col - 1);
            default: throw new IllegalArgumentException("Unknown direction");
        }
    }

    /*
        Inner class to represent a cell (row, column pair)
     */
    private static class Cell {
        int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
