/*
This class handles the two players in the game, storing the names, player marker, and whether they are
a computer or not. This information is supplied to the playGame method in the Main Class from instances
of this class.
 */
public class Player {
    //Field Variables
    private final int marker;
    private final String name;
    private final String chipColor;
    private final boolean isComputer;

    /*
    The constructor initializes the Player Class with their name, marker type, and whether they are a computer
    or not.
     */
    public Player(String name, int marker, boolean isComputer)
    {
        this.name = name;
        this.marker = marker;
        this.chipColor = "";
        this.isComputer = isComputer;
    }

    /*
    Returns the name of the player
     */
    public String getName()
    {
        return this.name;
    }

    /*
    Returns the marker type of the player (as an int)
     */
    public int getMarker()
    {
        return this.marker;
    }

    /*
    Returns the chip color of the player
     */
    public String getChipColor()
    {
        return this.chipColor;
    }

    /*
    Returns whether the player is a computer or not
     */
    public boolean isComputer() {
        return this.isComputer;
    }
}
