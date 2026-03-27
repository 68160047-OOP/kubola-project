package masterchef;
import java.awt.Point;

public interface Draggable {
    void move(int x, int y);
    boolean contains(Point p);
    void reset();
}