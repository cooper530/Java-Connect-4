import java.io.IOException;
import java.util.Random;

/*
The Main Class handles all the game mechanics and all the other classes in the program. The game is launched
and run in this class.
 */
public class Main {

    /*
    The playGame method handles the entire game and is the only major method in the Main Class
     */
    public static void playGame(Graphics window) throws IOException, InterruptedException {
        //Creates the board, the random turn selector, and the Players
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

            //Player 1 Instance
            if (turn == 0) {
                int col;
                while (true) {
                    col = window.getCol();
                    window.createButtons();

                    while (col == -1) {
                        col = window.getCol();
                        Thread.sleep(25);
                    }
                    //Checks if chip can be placed in desired column
                    if (!board.update(col, player1.getMarker()))
                        window.colFull(player1.getName());
                    else {
                        //System.out.println("col: " + col);
                        window.addChip(col, board.getAvailRow(col), 1);
                        break;
                    }
                }
            }

            //Player 2 Instance
            else {
                //Singleplayer Mode
                if (player2.isComputer()) {
                    int compCol = -1;
                    while (!board.update(compCol, player2.getMarker())) {
                        compCol = rand.nextInt(board.getColSize());
                    }
                    Thread.sleep(1000);
                    window.addChip(compCol, board.getAvailRow(compCol), 2);
                }

                //Multiplayer Mode
                else {
                    int col;
                    while (true) {
                        col = window.getCol();
                        window.createButtons();

                        while (col == -1) {
                            col = window.getCol();
                            Thread.sleep(25);
                        }
                        if (!board.update(col, player2.getMarker()))
                            window.colFull(player2.getName());
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
        }

        //Determines who the winner is based on what turn it is
        if(turn == 0) {
            window.displayWinner(player1.getName());
            window.updateWins(1);
        }
        else {
            window.displayWinner(player2.getName());
            window.updateWins(2);
        }
    }

    /*
    The method main runs the entire program, calling the playGame method and initializing the Graphics
    window.
     */
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
            window.clearScore();
            window.menuScreen();
        }
    }
}
