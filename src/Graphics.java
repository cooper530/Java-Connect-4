import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;

public class Graphics extends JFrame {

    //Gives it a title
    private final JFrame frame = new JFrame("Java Connect 4 - Cooper");
    private final JLayeredPane layeredPane;
    private final JLabel message = new JLabel("");
    private int col = -1;

    JButton zero;
    JButton one;
    JButton two;
    JButton three;
    JButton four;
    JButton five;
    JButton six;

    public Graphics() throws InterruptedException {
        //Sets closing operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(255, 255,255));
        //Sets size of screen
        frame.setSize(500, 500);
        frame.setResizable(false);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 500));
        layeredPane.setLayout(null);
        frame.setContentPane(layeredPane);

        //Loading Screen
        JLabel imageLabel = new JLabel(new ImageIcon("res/GameLogo.png"));
        imageLabel.setBounds(55, 55, 389, 308);
        layeredPane.add(imageLabel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Thread.sleep(2000);
        //Once removing something, the frame must be re-validated and repainted.
        removeInstance(layeredPane, imageLabel);
        //Adds Board Image
        initializeBoard();
    }

    public int getCol()
    {
        int tempCol = col;
        col = -1;
        return tempCol;
    }

    public void addChip(int col, int row, int player)
    {
        if(player == 1) {
            JLabel redChip = new JLabel(new ImageIcon("res/Red_Chip.png"));
            redChip.setBounds(113 + 40*col, 20 + 40*row, 32, 32);
            addInstance(layeredPane, redChip, 0);
        }
        else {
            JLabel yellowChip = new JLabel(new ImageIcon("res/Yellow_Chip.png"));
            yellowChip.setBounds(113 + 40*col, 20 + 40*row, 32, 32);
            addInstance(layeredPane, yellowChip, 0);
        }
    }

    /*
    Displays the turn of each player
     */
    public void displayTurn(String player) {
        removeInstance(layeredPane, this.message);
        this.message.setText("It is " + player + "'s turn");
        this.message.setBounds(170, 400, 500, 30);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(layeredPane, this.message, 0);
    }

    /*
    Displays the winner of the game
     */
    public void displayWinner(String player)
    {
        removeInstance(layeredPane, this.message);
        this.message.setText(player + " won!");
        this.message.setBounds(170, 400, 500, 30);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(layeredPane, this.message, 0);
    }

    /*
    Creates the board in the beginning of the game
     */
    public void initializeBoard() {
        JLabel board = new JLabel(new ImageIcon("res/6x7_Board.jpg"));
        board.setBounds(109, 0, 279, 275);
        board.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(layeredPane, board, 0);
    }

    public void createButtons()
    {
        zero = new JButton("0");
        zero.setBorder(null);
        zero.setBounds(109, 275, 40, 20);
        addInstance(layeredPane, zero, 0);
        zero.addActionListener(arg0 -> {
            col = 0;
            removeButtons();
        });

        one = new JButton("1");
        one.setBorder(null);
        one.setBounds(150, 275, 40, 20);
        addInstance(layeredPane, one, 0);
        one.addActionListener(arg0 -> {
            col = 1;
            removeButtons();
        });

        two = new JButton("2");
        two.setBorder(null);
        two.setBounds(190, 275, 40, 20);
        addInstance(layeredPane, two, 0);
        two.addActionListener(arg0 -> {
            col = 2;
            removeButtons();
        });

        three = new JButton("3");
        three.setBorder(null);
        three.setBounds(230, 275, 40, 20);
        addInstance(layeredPane, three, 0);
        three.addActionListener(arg0 -> {
            col = 3;
            removeButtons();
        });

        four = new JButton("4");
        four.setBorder(null);
        four.setBounds(270, 275, 40, 20);
        addInstance(layeredPane, four, 0);
        four.addActionListener(arg0 -> {
            col = 4;
            removeButtons();
        });

        five = new JButton("5");
        five.setBorder(null);
        five.setBounds(310, 275, 40, 20);
        addInstance(layeredPane, five, 0);
        five.addActionListener(arg0 -> {
            col = 5;
            removeButtons();
        });

        six = new JButton("6");
        six.setBorder(null);
        six.setBounds(350, 275, 40, 20);
        addInstance(layeredPane, six, 0);
        six.addActionListener(arg0 -> {
            col = 6;
            removeButtons();
        });
    }

    public void removeButtons()
    {
        removeInstance(layeredPane, zero);
        removeInstance(layeredPane, one);
        removeInstance(layeredPane, two);
        removeInstance(layeredPane, three);
        removeInstance(layeredPane, four);
        removeInstance(layeredPane, five);
        removeInstance(layeredPane, six);
    }

    /*
    Remove an instance on a frame
     */
    public static void removeInstance(Container instance, JComponent type)
    {
        instance.remove(type);
        instance.revalidate();
        instance.repaint();
    }

    /*
    Adds an instance on a panel
     */
    public static void addInstance(Container instance, JComponent type, int level)
    {
        instance.add(type, level);
        instance.revalidate();
        instance.repaint();
    }
}