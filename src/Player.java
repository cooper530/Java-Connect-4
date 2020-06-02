public class Player {
    private final int marker;
    private final String name;
    private final String chipColor;
    private final boolean isComputer;

    public Player(String name, int marker, boolean isComputer)
    {
        this.name = name;
        this.marker = marker;
        this.chipColor = "";
        this.isComputer = isComputer;
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

    public boolean isComputer() {
        return this.isComputer;
    }
}
