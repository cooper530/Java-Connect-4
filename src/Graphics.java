import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/*
This class handles all of the JFrame Components of the game, including buttons, images, and click actions.
The Graphics class interacts with the Main class, where an instance of it has been created.
 */
public class Graphics extends JFrame {

    //Field variables for the class
    private final JFrame frame = new JFrame("Java Connect 4");
    private final JLayeredPane layeredPane;
    private final JLabel message = new JLabel("");
    private JLabel imageLabel, board, winCounter;
    //First in list
    String playerColor = "Red", computerColor = "Red";
    private int col = -1, p1Wins = 0, p2Wins = 0;
    String p1Name = "Player 1", p2Name = "Computer", serverAddress = "";
    AtomicReference<String> type = new AtomicReference<>("Singleplayer");
    private boolean playAgain = false;
    private final ArrayList<JLabel> chips = new ArrayList<>();

    JButton zero, one, two, three, four, five, six;

    /*
    Handles the creation of the window and initialization of the menu screen
     */
    public Graphics() throws IOException {
        //Sets closing operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(255, 255, 255));
        //Sets size of screen
        frame.setSize(500, 500);
        frame.setResizable(false);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 500));
        layeredPane.setLayout(null);
        frame.setContentPane(layeredPane);

        //Menu Screen
        menuScreen();

    }

    /*
    The first screen of the game, which allows the player to add names, mode, and chip colors
     */
    public void menuScreen() throws IOException {
        //Title
        ArrayList<JComponent> elements = new ArrayList<>();
        BufferedImage titleImage = ImageIO.read(getClass().getResource("/res/GameLogo.png"));
        imageLabel = new JLabel(new ImageIcon(titleImage));
        imageLabel.setBounds(55, 0, 389, 170);
        layeredPane.add(imageLabel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Mode Box
        String[] modes = {"Singleplayer", "Local Multiplayer", "Online Multiplayer"};
        JComboBox<String> gameType = new JComboBox<>(modes);
        gameType.addActionListener(e -> type.set((String) gameType.getSelectedItem()));
        gameType.setBounds(200, 250, 100, 20);
        JLabel selectGame = new JLabel("Gamemode");
        selectGame.setBounds(100, 250, 100, 20);
        addInstance(layeredPane, selectGame, 0);
        addInstance(layeredPane, gameType, 0);

        //Name Labels
        JLabel pName = new JLabel("Player 1 Name");
        JLabel cName = new JLabel("Computer Name");
        pName.setBounds(40, 300, 100, 20);
        cName.setBounds(40, 350, 100, 20);

        //Name Text Fields
        JTextField pNameText = new JTextField("Player 1");
        JTextField cNameText = new JTextField("Player 2");
        pNameText.setBounds(140, 300, 100, 20);
        cNameText.setBounds(140, 350, 100, 20);

        //Submit Button
        AtomicBoolean submitted = new AtomicBoolean(false);
        JButton submit = new JButton("Submit");
        submit.setBounds(200, 430, 100, 20);

        //Color Box
        String[] colors = {"Red", "Orange", "Yellow", "Green", "Blue", "Pink", "Purple"};
        JComboBox<String> colorPList = new JComboBox<>(colors);
        colorPList.addActionListener(e -> playerColor = (String) colorPList.getSelectedItem());
        colorPList.setBounds(390, 300, 70, 20);
        JComboBox<String> colorCList = new JComboBox<>(colors);
        colorCList.addActionListener(e -> computerColor = (String) colorCList.getSelectedItem());
        colorCList.setBounds(390, 350, 70, 20);

        //Color Labels
        JLabel pColor = new JLabel("Player 1 Color");
        JLabel cColor = new JLabel("Computer Color");
        cColor.setBounds(290, 350, 100, 20);
        pColor.setBounds(290, 300, 100, 20);

        //IP Address
        JLabel ipLabel = new JLabel("IP Address");
        ipLabel.setBounds(40, 350, 100, 20);
        JTextField ipText = new JTextField();
        ipText.setBounds(140, 350, 100, 20);

        //Join Game
        JButton joinGame = new JButton("Join Game");
        joinGame.setBounds(200, 430, 100, 20);

        //Adds to list
        elements.add(cNameText);
        elements.add(pNameText);
        elements.add(cName);
        elements.add(pName);
        elements.add(cColor);
        elements.add(pColor);
        elements.add(colorCList);
        elements.add(colorPList);
        elements.add(submit);
        elements.add(ipText);
        elements.add(ipLabel);
        elements.add(joinGame);
        elements.add(selectGame);
        elements.add(gameType);
        elements.add(imageLabel);

        //Adds to window
        addInstance(layeredPane, elements, 0);
        playAgain = true;
        joinGame.addActionListener(arg0 -> {
            try {
                removeInstance(layeredPane, elements);
                serverAddress = ipText.getText();
                p1Name = pNameText.getText();
                type.set((String) gameType.getSelectedItem());
                submitted.set(true);
            } catch (NullPointerException ignored) { }
        });
        submit.addActionListener(arg0 -> {
            try {
                if (!playerColor.equals(computerColor)) {
                    //Removes instances if colors are not equal, initializes board (beginning game)
                    removeInstance(layeredPane, elements);
                    p1Name = pNameText.getText();
                    type.set((String) gameType.getSelectedItem());
                    submitted.set(true);
                    p2Name = cNameText.getText();
                    if (p1Name.equals("memes"))
                        executeMemes();
                    initializeBoard();
                }
            } catch (NullPointerException | IOException ignored) { }
        });

        //While loop to handle menu screen toggle
        while (!submitted.get()) {
            if(type.get().equals("Local Multiplayer") || type.get().equals("Singleplayer"))
            {
                cColor.setVisible(true);
                cName.setVisible(true);
                colorCList.setVisible(true);
                cNameText.setVisible(true);
                ipLabel.setVisible(false);
                ipText.setVisible(false);
                submit.setVisible(true);
                joinGame.setVisible(false);
                if (type.get().equals("Local Multiplayer")) {
                    cColor.setText("Player 2 Color");
                    cName.setText("Player 2 Name");
                } else if (type.get().equals("Singleplayer")) {
                    cColor.setText("Computer Color");
                    cName.setText("Computer Name");
                }
            } else {
                cColor.setVisible(false);
                cName.setVisible(false);
                colorCList.setVisible(false);
                cNameText.setVisible(false);
                ipLabel.setVisible(true);
                ipText.setVisible(true);
                submit.setVisible(false);
                joinGame.setVisible(true);
            }
        }
    }


    /*
    Adds the chip image onto the window, with the correct column, row, and color
     */
    public void addChip(int col, int row, int player) throws IOException {
        //Player 1
        if (player == 1) {
            BufferedImage pChipImage = ImageIO.read(getClass().getResource("/res/" + playerColor + "_Chip.png"));
            JLabel pChip = new JLabel(new ImageIcon(pChipImage));
            pChip.setBounds(113 + 40 * col, 20 + 40 * row, 32, 32);
            addInstance(layeredPane, pChip, 0);
            chips.add(pChip);
        //Player 2
        } else {
            BufferedImage cChipImage = ImageIO.read(getClass().getResource("/res/" + computerColor + "_Chip.png"));
            JLabel cChip = new JLabel(new ImageIcon(cChipImage));
            cChip.setBounds(113 + 40 * col, 20 + 40 * row, 32, 32);
            addInstance(layeredPane, cChip, 0);
            chips.add(cChip);
        }
    }

    /*
    Method to handle the end of the game
     */
    public void gameOver()
    {
        AtomicBoolean loopVar = new AtomicBoolean(true);
        //Creates Play Again and Start Over buttons
        JButton playAgainButton = new JButton("Play Again");
        JButton startOver = new JButton("Start Over");
        playAgainButton.setBounds(145, 430, 100, 20);
        startOver.setBounds(255, 430, 100, 20);
        playAgainButton.addActionListener(arg0 -> {
            playAgain = true;
            removeInstance(layeredPane, playAgainButton);
            removeInstance(layeredPane, startOver);
            loopVar.set(false);
        });
        startOver.addActionListener(arg0 -> {
            playAgain = false;
            removeInstance(layeredPane, playAgainButton);
            removeInstance(layeredPane, startOver);
            removeInstance(layeredPane, message);
            removeInstance(layeredPane, board);
            removeInstance(layeredPane, winCounter);
            playerColor = "Red";
            computerColor = "Red";
            p1Name = "Player 1";
            p2Name = "Computer";
            loopVar.set(false);
        });
        //Adds buttons to window
        addInstance(layeredPane, playAgainButton, 0);
        addInstance(layeredPane, startOver, 0);
        while(loopVar.get()){}
    }

    public void setP2Color(String color)
    {
        computerColor = color;
    }

    /*
    Clears all the chips from the window
     */
    public void clearChips()
    {
        for(JLabel label : chips)
            removeInstance(layeredPane, label);
    }

    /*
    Returns the boolean var playAgain, which determines whether the game should be
    repeated or started over
     */
    public boolean getPlayAgain()
    {
        return playAgain;
    }

    public String getLocalPlayerColor()
    {
        return playerColor;
    }

    /*
    Displays the turn of each player
     */
    public void displayTurn(String player) {
        removeInstance(layeredPane, this.message);
        this.message.setText("It is " + player + "'s turn");
        this.message.setBounds(100, 300, 320, 30);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setHorizontalAlignment(JLabel.CENTER);
        addInstance(layeredPane, this.message, 0);
    }

    /*
    Displays the winner of the game
     */
    public void displayWinner(String player)
    {
        removeInstance(layeredPane, this.message);
        this.message.setText(player + " won!");
        this.message.setBounds(100, 300, 300, 30);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setHorizontalAlignment(JLabel.CENTER);
        addInstance(layeredPane, this.message, 0);
    }

    /*
    Displays a message on the window
     */
    public void displayMessage(String text) throws InterruptedException {
        removeInstance(layeredPane, this.message);
        this.message.setText(text);
        this.message.setBounds(100, 300, 300, 30);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setHorizontalAlignment(JLabel.CENTER);
        addInstance(layeredPane, this.message, 0);
    }

    /*
    Updates the win counter every game
     */
    public void updateWins(int player)
    {
        if(player == 1) p1Wins++;
        else if(player == 2) p2Wins++;
        winCounter.setText(p1Name + ": " + p1Wins + "     " + p2Name + ": " + p2Wins);
    }

    /*
    Clears the win counter for when the game starts over
     */
    public void clearScore()
    {
        p1Wins = 0;
        p2Wins = 0;
    }

    /*
    Creates the board and win counter in the beginning of the game
     */
    public void initializeBoard() throws IOException {
        BufferedImage boardImage = ImageIO.read(getClass().getResource("/res/6x7_Board.jpg"));
        board = new JLabel(new ImageIcon(boardImage));
        board.setBounds(109, 0, 279, 275);
        board.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(layeredPane, board, 0);

        winCounter = new JLabel(p1Name + ": " + p1Wins + "     " + p2Name + ": " + p2Wins);
        winCounter.setBounds(100, 350, 300, 30);
        winCounter.setFont(new Font("Serif", Font.BOLD, 20));
        winCounter.setHorizontalAlignment(JLabel.CENTER);
        addInstance(layeredPane, winCounter, 0);
    }

    public void initLocalBoard() throws IOException
    {
        BufferedImage boardImage = ImageIO.read(getClass().getResource("/res/6x7_Board.jpg"));
        board = new JLabel(new ImageIcon(boardImage));
        board.setBounds(109, 0, 279, 275);
        board.setAlignmentX(Component.CENTER_ALIGNMENT);
        addInstance(layeredPane, board, 0);
    }

    /*
    Displays a message if a column is full on the board (no more chips can be placed)
     */
    public void colFull(String player) throws InterruptedException {
        message.setText("That column is full! Please try again.");
        Thread.sleep(1500);
        displayTurn(player);
    }

    /*
    Creates the buttons that the user clicks to select a column
     */
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

    /*
    Removes the buttons that a user clicks to select a column
     */
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
    Returns the column selected to the playGame method in the Main Class
     */
    public int getCol()
    {
        int tempCol = col;
        col = -1;
        return tempCol;
    }

    /*
    Returns the name of Player 1
     */
    public String getP1Name()
    {
        return p1Name;
    }

    /*
    Returns the name of Player 2
     */
    public String getP2Name()
    {
        return p2Name;
    }

    /*
    Returns whether the game is in Singleplayer Mode or Local Multiplayer Mode
     */
    public boolean isComputer()
    {
        return type.get().equals("Singleplayer");
    }

    /*
    Returns whether the game is online or not
     */
    public boolean isOnline()
    {
        return type.get().equals("Online Multiplayer");
    }

    /*
    Returns the IP Address for online play
     */
    public String getIPAddress()
    {
        return serverAddress;
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

    public void removeInstance(Container instance, ArrayList<JComponent> elements) {
        for(JComponent element : elements) {
            instance.remove(element);
            instance.revalidate();
            instance.repaint();
        }
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

    public static void addInstance(Container instance, ArrayList<JComponent> elements, int level)
    {
        for(JComponent element : elements) {
            instance.add(element, level);
            instance.revalidate();
            instance.repaint();
        }
    }
    /*
    This method is not important
     */
    public void executeMemes() throws IOException {
        BufferedImage meme1 = ImageIO.read(getClass().getResource("/res/meme1.jpg"));
        JLabel meme1Label = new JLabel(new ImageIcon(meme1));
        meme1Label.setBounds(0, 0, 250, 250);
        addInstance(layeredPane, meme1Label, 0);
        BufferedImage meme2 = ImageIO.read(getClass().getResource("/res/meme2.jpg"));
        JLabel meme2Label = new JLabel(new ImageIcon(meme2));
        meme2Label.setBounds(250, 250, 250, 250);
        addInstance(layeredPane, meme2Label, 0);
        BufferedImage meme3 = ImageIO.read(getClass().getResource("/res/meme3.jpg"));
        JLabel meme3Label = new JLabel(new ImageIcon(meme3));
        meme3Label.setBounds(250, 0, 250, 250);
        addInstance(layeredPane, meme3Label, 0);
        BufferedImage meme4 = ImageIO.read(getClass().getResource("/res/meme4.jpg"));
        JLabel meme4Label = new JLabel(new ImageIcon(meme4));
        meme4Label.setBounds(0, 250, 250, 250);
        addInstance(layeredPane, meme4Label, 0);
    }
}