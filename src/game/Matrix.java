package game;

import java.util.Scanner;

public class Matrix {

    private int[][] matrix;
    private int columns;
    private int rows;

    public Matrix(int r, int c) {
        rows = r;
        columns = c;
        Scanner scanner = new Scanner(System.in);
        matrix = new int[rows][columns];
        System.out.print("matrix size is " + rows + " x " + columns);
        for (int rn = 0; rn < rows; rn++) {

            for (int col = 0; col < columns; col++) {
                System.out.println("Enter for spot: [" + rn + "][" + col + "]");
                int add = scanner.nextInt();
                matrix[rn][col] = add;
            }
        }

    }


    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int rn = 0; rn < rows; rn++) {
            
            for (int col = 0; col < columns; col++) {
                b.append(matrix[rn][col] + "\t");
            }
            b.append("\n");
        }
        return b.toString();
    }
        
    
}
