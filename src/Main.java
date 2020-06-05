import java.io.IOException;
import java.util.Random;

public class Main {

    public static void playGame(Graphics window) throws IOException, InterruptedException {
        Board board = new Board();
        Random rand = new Random();
        Player player1 = new Player(window.getP1Name(), 1, false);
        Player player2 = new Player(window.getP2Name(), 2, window.isComputer());

        //Play Loop
        //Selects who starts randomly (0 = P1 Player, 1 = P2 Computer)
        int turn = rand.nextInt(2);
        board.clearBoard();

        //Game Loop
        while (true) {
            //Turn handling
            if (turn == 0) window.displayTurn(player1.getName());
            else window.displayTurn(player2.getName());
            //System.out.println(board);

            //Player Instance
            if (turn == 0) {
                int col;
                while (true) {
                    col = window.getCol();
                    window.createButtons();

                    while (col == -1) {
                        col = window.getCol();
                        Thread.sleep(25);
                    }
                    if (!board.update(col, player1.getMarker()))
                        System.out.println("That column is full! Please try again");
                    else {
                        //System.out.println("col: " + col);
                        window.addChip(col, board.getAvailRow(col), 1);
                        break;
                    }
                }
            }
            //Computer Instance (Random Turn)

            else {
                if (player2.isComputer()) {
                    int compCol = -1;
                    while (!board.update(compCol, player2.getMarker())) {
                        compCol = rand.nextInt(board.getColSize());
                    }
                    Thread.sleep(1000);
                    window.addChip(compCol, board.getAvailRow(compCol), 2);
                } else {
                    int col;
                    while (true) {
                        col = window.getCol();
                        window.createButtons();

                        while (col == -1) {
                            col = window.getCol();
                            Thread.sleep(25);
                        }
                        if (!board.update(col, player2.getMarker()))
                            System.out.println("That column is full! Please try again");
                        else {
                            window.addChip(col, board.getAvailRow(col), 2);
                            break;
                        }
                    }
                }
            }

            //Check if winner
            if (board.checkBoard())
                break;

            //Changes turn
            if (turn == 0) turn = 1;
            else turn = 0;

            //System.out.println("\n");
        }
        //System.out.println(board + "\n");
        if(turn == 0) window.displayWinner(player1.getName());
        else window.displayWinner(player2.getName());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Graphics window = new Graphics();
        while(true) {
            do {
                //Play Game
                playGame(window);
                //Handles Post-Game
                window.gameOver();
                window.clearChips();
            } while (window.getPlayAgain());
            window.menuScreen();
        }
    }
}
