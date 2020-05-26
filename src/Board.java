public class Board {

    int[][] board;

    public Board()
    {
        //[row][col]
        board = new int[6][7];
    }

    public boolean update(int col, int player)
    {
        for(int i=board.length - 1;i>=0;i--)
        {
            if(board[i][col] == 0)
            {
                board[i][col] = player;
                return true;
            }
        }
        return false;
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
        return false;
    }

    private boolean checkVert()
    {
        int regCounter = 0;
        int p1Counter = 0;
        int p2Counter = 0;
        for(int j=0;j<board[0].length;j++)
        {
            for(int i=0;i<board.length;i++) {
                if (board[i][j] == 1)
                    p1Counter++;
                else if(board[i][j] == 2)
                    p2Counter++;
                regCounter++;

                if (regCounter == 4) {
                    if (p1Counter == 4 || p2Counter == 4)
                        return true;
                    else
                        i -= 3;
                    regCounter = 0;
                    p1Counter = 0;
                    p2Counter = 0;
                }
            }
            regCounter = 0;
            p1Counter = 0;
            p2Counter = 0;
        }
        return false;
    }

    private boolean checkHoriz()
    {
        int regCounter = 0;
        int p1Counter = 0;
        int p2Counter = 0;
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++) {
                if (board[i][j] == 1)
                    p1Counter++;
                else if(board[i][j] == 2)
                    p2Counter++;
                regCounter++;

                if (regCounter == 4) {
                    if (p1Counter == 4 || p2Counter == 4)
                        return true;
                    else
                        j -= 3;
                    regCounter = 0;
                    p1Counter = 0;
                    p2Counter = 0;
                }
            }
            regCounter = 0;
            p1Counter = 0;
            p2Counter = 0;
        }
        return false;
    }

    public String toString()
    {
        //Starting rows
        for(int i=0;i<board[0].length;i++)
            System.out.print(i + " ");
        System.out.println(" ");
        for(int i=0;i<board[0].length;i++)
            System.out.print("- ");
        System.out.println(" ");

        for (int i=0;i<board.length;i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(" ");
        }
        return "";
    }
}
