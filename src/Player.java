public class Player {
    private final int marker;
    private final String name;
    private final String chipColor;

    public Player(String name, int marker)
    {
        this.name = name;
        this.marker = marker;
        this.chipColor = "";
    }

    public String getName()
    {
        return this.name;
    }

    public int getMarker()
    {
        return this.marker;
    }

    public String getChipColor()
    {
        return this.chipColor;
    }

}
