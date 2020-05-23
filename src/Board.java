import java.util.*;
public class Board {

    int[][] board;

    public Board()
    {
        //[row][col]
        board = new int[6][7];
    }

    public void update(int row, int col, int player)
    {
        board[row][col] = player;
    }

    public int getRowSize()
    {
        return board.length;
    }

    public int getColSize()
    {
        return board[0].length;
    }

    public boolean checkBoard()
    {
        return checkDiag() || checkVert() || checkHoriz();
    }

    private boolean checkDiag()
    {
        return true;
    }

    private boolean checkVert()
    {
        return true;
    }

    private boolean checkHoriz()
    {
        return true;
    }

    public String toString()
    {
        //Starting rows
        System.out.print("    ");
        for(int i=0;i<board[0].length;i++)
            System.out.print(i + " ");
        System.out.println(" ");
        System.out.print("    ");
        for(int i=0;i<board[0].length;i++)
            System.out.print("- ");
        System.out.println(" ");

        for (int i=0;i<board.length;i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(" ");
        }
        return "";
    }
}
