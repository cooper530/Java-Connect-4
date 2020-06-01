import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Board board = new Board();
        Player player = new Player("Cooper", 1);
        Player computer = new Player("Computer", 2);
        Random rand = new Random();
        Graphics window = new Graphics();

        //Selects who starts randomly (0 = P1 Player, 1 = P2 Computer)
        int turn = rand.nextInt(2);

        //Game Loop
        while(true)
        {
            //Turn handling
            if(turn == 0) window.displayTurn(player.getName());
            else window.displayTurn(computer.getName());
            //System.out.println(board);

            //Player Instance
            if(turn == 0)
            {
                int col;
                while(true) {
                    col = window.getCol();
                    window.createButtons();

                    while(col == -1) {
                        col = window.getCol();
                        Thread.sleep(25);
                    }
                    if (!board.update(col, player.getMarker()))
                        System.out.println("That column is full! Please try again");
                    else {
                        //System.out.println("col: " + col);
                        window.addChip(col, board.getAvailRow(col), 1);
                        break;
                    }
                }
            }
            //Computer Instance (Random Turn)

            else
            {
                int compCol = -1;
                while (!board.update(compCol, computer.getMarker())){
                    compCol = rand.nextInt(board.getColSize());
                }
                Thread.sleep(1000);
                window.addChip(compCol, board.getAvailRow(compCol), 2);
            }

            //Check if winner
            if(board.checkBoard())
                break;

            //Changes turn
            if (turn == 0) turn = 1;
            else turn = 0;

            //System.out.println("\n");
        }

        //System.out.println(board + "\n");
        if(turn == 0) window.displayWinner(player.getName());
        else window.displayWinner(computer.getName());
    }
}
