import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

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
    TOMORROW: Why do the clients disconnect when connecting to the server? Resets name, color, and entire program
     */
    public static void playOnline(Graphics window, Socket socket) throws IOException, InterruptedException {
        Scanner input = new Scanner(socket.getInputStream());
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        //Creates Board
        Board localBoard = new Board();
        window.initLocalBoard();

        //Sends name/chip color to server
        output.println(window.getP1Name());
        output.println(window.getLocalPlayerColor());


        //Handles messages from the server
        while(input.hasNextLine()) {
            String response = input.nextLine();
            System.out.println(response);
            if (response.startsWith("MESSAGE")) {
                window.displayMessage(response.substring(8));
            } else if (response.startsWith("YOUR_MOVE")) {
                window.displayTurn(window.p1Name);
                //Updates opponent move
                int col = Integer.parseInt(response.substring(10));
                if(col != -1) {
                    localBoard.update(col, 2);
                    window.addChip(col, localBoard.getAvailRow(col), 2);
                }
                while (true) {
                    col = window.getCol();
                    window.createButtons();

                    while (col == -1) {
                        col = window.getCol();
                        Thread.sleep(25);
                    }
                    //Checks if chip can be placed in desired column
                    if (!localBoard.update(col, 1))
                        window.colFull(window.getP1Name());
                    else {
                        window.addChip(col, localBoard.getAvailRow(col), 1);
                        break;
                    }
                }
                output.println("UPDATE_BOARD " + col);
            }
            else if (response.equals("OTHER_PLAYER_LEFT")) {
                window.clearChips();
                localBoard.clearBoard();
                window.displayMessage("Opponent Disconnected");
            }
            else if(response.startsWith("CHIP_COLOR"))
            {
                //Receives chip color from server
                String color = response.substring(11);
                if(color.equals(window.playerColor))
                    window.setP2Color("Orange");
                else {
                    window.setP2Color(color);
                }
            }
            else if(response.startsWith("VICTORY"))
            {
                window.displayMessage("You won!");
                Thread.sleep(2000);
            }
            else if(response.startsWith("DEFEAT"))
            {
                localBoard.update(Integer.parseInt(response.substring(7)), 2);
                window.addChip(Integer.parseInt(response.substring(7)), localBoard.getAvailRow(Integer.parseInt(response.substring(7))), 2);
                window.displayMessage("You lost!");
                Thread.sleep(2000);
            }
            else if(response.startsWith("TIE"))
            {
                try {
                    localBoard.update(Integer.parseInt(response.substring(4)), 2);
                    window.addChip(Integer.parseInt(response.substring(4)), localBoard.getAvailRow(Integer.parseInt(response.substring(4))), 2);
                    window.displayMessage("Tie!");
                    Thread.sleep(2000);
                } catch(Exception e) {
                    window.displayMessage("Tie!");
                    Thread.sleep(2000);
                }
            }
            Thread.sleep(500);
        }
    }

    /*
    The method main runs the entire program, calling the playGame method and initializing the Graphics
    window.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Graphics window = new Graphics();
        while(true) {
            if(!window.isOnline()) {
                do {
                    //Play Game
                    playGame(window);
                    //Handles Post-Game
                    window.gameOver();
                    window.clearChips();
                } while (window.getPlayAgain());
                window.clearScore();
                window.menuScreen();
            } else {
                try {
                    Socket socket = new Socket(window.getIPAddress(), 25565);
                    window.displayMessage("");
                    playOnline(window, socket);
                } catch (ConnectException e) {
                    window.displayMessage("Cannot connect to server...");
                }

            }
        }
    }
}
