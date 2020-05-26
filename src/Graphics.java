import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Graphics extends JFrame {

    //Gives it a title
    private final JFrame frame = new JFrame("Java Connect 4 - Cooper");
    private final JPanel panel = new JPanel() {
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        };
    };
    private final JLabel message = new JLabel("");
    private int col = -1;

    public Graphics() throws InterruptedException {
        //Sets closing operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Sets size of screen
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setContentPane(panel);
        panel.setBackground(new Color(255, 255,255));
        //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setLayout(null);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                checkClickPos(e);
            }
        });

        //Loading Screen
        JLabel imageLabel = new JLabel(new ImageIcon("res/GameLogo.png"));
        imageLabel.setBounds(55, 55, 389, 308);
        panel.add(imageLabel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Thread.sleep(2000);
        //Once removing something, the frame must be re-validated and repainted.
        removeInstance(panel, imageLabel);
        //Adds Board Image
        initializeBoard();
    }

    public void checkClickPos(MouseEvent e)
    {
        int x = e.getX(), y = e.getY(), boardBoundY = 235;
        //col = -1;
        //Col 0
        if((x > 109 && x < 143) && (y < boardBoundY)) col = 0;
        //Col 1
        else if((x > 143 && x < 186) && (y < boardBoundY)) col = 1;
        //Col 2
        else if((x > 186 && x < 227) && (y < boardBoundY)) col = 2;
        //Col 3
        else if((x > 227 && x < 269) && (y < boardBoundY)) col = 3;
        //Col 4
        else if((x > 269 && x < 308) && (y < boardBoundY)) col = 4;
        //Col 5
        else if((x > 308 && x < 347) && (y < boardBoundY)) col = 5;
        //Col 6
        else if((x > 347 && x < 386) && (y < boardBoundY)) col = 6;
        System.out.println(col);
    }

    public int getCol()
    {
        //System.out.println(col);
        int tempCol = col;
        col = -1;
        return tempCol;
    }

    public void addChip(int col, int player)
    {
        JLabel chip;
        //chip.setBounds();
        if(player == 1)/*
            chip = new JLabel(new ImageIcon("res/Red_Chip.png"));
            if(col == 0)
            else if(col == 1)
            else if(col == 2)
            else if(col == 3)
            else if(col == 4)
            else if(col == 5)
            else if(col == 6)*/
            System.out.println(1);
        else if(player == 2)
            /*
            chip = new JLabel(new ImageIcon("res/Yellow_Chip.png"));
            if(col == 0)
            else if(col == 1)
            else if(col == 2)
            else if(col == 3)
            else if(col == 4)
            else if(col == 5)
            else if(col == 6)*/
            System.out.println(2);
    }

    /*
    Displays the turn of each player
     */
    public void displayTurn(String player) throws InterruptedException {
        //panel.setLayout(null);
        removeInstance(panel, this.message);
        this.message.setText("It is " + player + "'s turn");
        this.message.setBounds(170, 290, 500, 30);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(frame, this.message);
    }

    /*
    Creates the board in the beginning of the game
     */
    public void initializeBoard()
    {
        JLabel board = new JLabel(new ImageIcon("res/6x7_Board.jpg"));
        board.setBounds(109, 0, 279, 275);
        board.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(panel, board);
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
    public static void addInstance(Container instance, JComponent type)
    {
        instance.add(type);
        instance.revalidate();
        instance.repaint();
    }
}

/* CREATE A BUTTON AND HAVE CLICK ACTION
JButton yes = new JButton("Yes");
yes.setBounds(200,400,100,30);
addInstance(panel, yes);
yes.addActionListener(arg0 -> {
    confirmBuy = 1;
    removeInstance(panel,yes);
});
 */

//Set location: button.setBounds(182,232,132,35);