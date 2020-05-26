import java.awt.*;

public class MouseTester {
    public static void main(String[] args)
    {
        while(true) {
            double x = MouseInfo.getPointerInfo().getLocation().getX();
            double y = MouseInfo.getPointerInfo().getLocation().getY();
            System.out.println("y coord: " + y);
            System.out.println("X coord: " + x);
            System.out.println(" ");
        }
    }
}
