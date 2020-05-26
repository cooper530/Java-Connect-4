import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Board board = new Board();
        Player player = new Player("Cooper", 1);
        Player computer = new Player("Joel", 2);
        Random rand = new Random();
        Scanner in = new Scanner(System.in);
        Graphics window = new Graphics();

        //Selects who starts randomly (0 = P1 Player, 1 = P2 Computer)
        int turn = rand.nextInt(2);

        //Game Loop
        while(true)
        {
            //Turn handling
            if(turn == 0) window.displayTurn(player.getName());//System.out.println("It is " + player.getName() + "'s turn" + "\n");
            else window.displayTurn(computer.getName());//System.out.println("It is " + computer.getName() + "'s turn" + "\n");

            System.out.println(board);

            //Player Instance
            if(turn == 0)
            {
                while(true) {
                    System.out.print("Enter col: ");
                    int col = in.nextInt();

                    if (!board.update(col, player.getMarker()))
                        System.out.println("That column is full! Please try again");
                    else
                        break;
                }
            }
            //Computer Instance (Random Turn)
            else
                do {
                    Thread.sleep(1000);
                } while (!board.update(rand.nextInt(board.getColSize()), computer.getMarker()));



            //Check if winner
            if(board.checkBoard())
                break;

            //Changes turn
            if (turn == 0) turn = 1;
            else turn = 0;

            System.out.println("\n");
        }

        System.out.println(board + "\n");
        System.out.println("Game over!");
        if(turn == 0) System.out.println(player.getName() + " won!");
        else System.out.println(computer.getName() + " won!");
    }
}
