public class Player {
    private final int marker;
    private final String name;

    public Player(String name, int marker)
    {
        this.name = name;
        this.marker = marker;
    }

    public String getName()
    {
        return name;
    }

    public int getMarker()
    {
        return marker;
    }

}
