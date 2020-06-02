import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Graphics extends JFrame {

    //Gives it a title
    private final JFrame frame = new JFrame("Java Connect 4");
    private final JLayeredPane layeredPane;
    private final JLabel message = new JLabel("");
    private final JLabel imageLabel;
    //First in list
    String playerColor = "Red";
    String computerColor = "Red";
    private int col = -1;
    String p1Name = "Player 1";
    String p2Name = "Computer";
    AtomicReference<String> type = new AtomicReference<>("Singleplayer");

    JButton zero;
    JButton one;
    JButton two;
    JButton three;
    JButton four;
    JButton five;
    JButton six;

    public Graphics() throws IOException {
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
        BufferedImage titleImage = ImageIO.read(getClass().getResource("/res/GameLogo.png"));
        imageLabel = new JLabel(new ImageIcon(titleImage));
        imageLabel.setBounds(55, 0, 389, 170);
        layeredPane.add(imageLabel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //Menu Screen
        menuScreen();

    }

    public void menuScreen()
    {
        String[] modes = {"Singeplayer", "Multiplayer"};
        JComboBox<String> gameType = new JComboBox<>(modes);
        gameType.addActionListener(e -> type.set((String) gameType.getSelectedItem()));
        gameType.setBounds(200, 250, 100, 20);
        JLabel selectGame = new JLabel("Gamemode");
        selectGame.setBounds(100, 250, 100, 20);
        addInstance(layeredPane, selectGame, 0);
        addInstance(layeredPane, gameType, 0);

        JLabel pName = new JLabel("Player 1 Name");
        JLabel cName = new JLabel("Computer Name");
        pName.setBounds(40, 300, 100, 20);
        cName.setBounds(40, 350, 100, 20);

        JTextField pNameText = new JTextField("Player 1");
        JTextField cNameText = new JTextField("Player 2");
        pNameText.setBounds(140, 300, 100, 20);
        cNameText.setBounds(140, 350, 100, 20);

        addInstance(layeredPane, cNameText, 0);
        addInstance(layeredPane, pNameText, 0);
        addInstance(layeredPane, cName, 0);
        addInstance(layeredPane, pName, 0);

        AtomicBoolean submitted = new AtomicBoolean(false);
        JButton submit = new JButton("Submit");
        submit.setBounds(200, 430, 100, 20);

        String[] colors = {"Red", "Orange", "Yellow", "Green", "Blue", "Pink", "Purple"};
        JComboBox<String> colorPList = new JComboBox<>(colors);
        colorPList.addActionListener(e -> playerColor = (String) colorPList.getSelectedItem());
        colorPList.setBounds(390, 300, 70, 20);

        JComboBox<String> colorCList = new JComboBox<>(colors);
        colorCList.addActionListener(e -> computerColor = (String) colorCList.getSelectedItem());
        colorCList.setBounds(390, 350, 70, 20);

        JLabel pColor = new JLabel("Player 1 Color");
        JLabel cColor = new JLabel("Computer Color");
        cColor.setBounds(290, 350, 100, 20);
        pColor.setBounds(290, 300, 100, 20);

        addInstance(layeredPane, cColor, 0);
        addInstance(layeredPane, pColor, 0);
        addInstance(layeredPane, colorCList, 0);
        addInstance(layeredPane, colorPList, 0);
        addInstance(layeredPane, submit, 0);
        submit.addActionListener(arg0 -> {
            try {
                if (!playerColor.equals(computerColor)) {
                    removeInstance(layeredPane, cColor);
                    removeInstance(layeredPane, pColor);
                    removeInstance(layeredPane, colorCList);
                    removeInstance(layeredPane, colorPList);
                    removeInstance(layeredPane, submit);
                    removeInstance(layeredPane, imageLabel);
                    removeInstance(layeredPane, submit);
                    removeInstance(layeredPane, cNameText);
                    removeInstance(layeredPane, pNameText);
                    removeInstance(layeredPane, cName);
                    removeInstance(layeredPane, pName);
                    removeInstance(layeredPane, gameType);
                    removeInstance(layeredPane, selectGame);
                    p1Name = pNameText.getText();
                    p2Name = cNameText.getText();
                    submitted.set(true);
                    //Adds Board Image
                    initializeBoard();
                }
            }catch (NullPointerException | IOException ignored){}
        });
        while(!submitted.get())
        {
            if(type.get().equals("Multiplayer"))
            {
                cColor.setText("Player 2 Color");
                cName.setText("Player 2 Name");
                removeInstance(layeredPane, selectGame);
                removeInstance(layeredPane, gameType);
            }
        }
    }

    public void addChip(int col, int row, int player) throws IOException {
        if(player == 1) {
            BufferedImage pChipImage = ImageIO.read(getClass().getResource("/res/" + playerColor + "_Chip.png"));
            JLabel pChip = new JLabel(new ImageIcon(pChipImage));
            pChip.setBounds(113 + 40*col, 20 + 40*row, 32, 32);
            addInstance(layeredPane, pChip, 0);
        }
        else {
            BufferedImage cChipImage = ImageIO.read(getClass().getResource("/res/" + computerColor + "_Chip.png"));
            JLabel cChip = new JLabel(new ImageIcon(cChipImage));
            cChip.setBounds(113 + 40*col, 20 + 40*row, 32, 32);
            addInstance(layeredPane, cChip, 0);
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
    public void initializeBoard() throws IOException {
        BufferedImage boardImage = ImageIO.read(getClass().getResource("/res/6x7_Board.jpg"));
        JLabel board = new JLabel(new ImageIcon(boardImage));
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

    public int getCol()
    {
        int tempCol = col;
        col = -1;
        return tempCol;
    }

    public String getP1Name()
    {
        return p1Name;
    }

    public String getP2Name()
    {
        return p2Name;
    }

    public boolean isComputer()
    {
        return type.get().equals("Singleplayer");
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