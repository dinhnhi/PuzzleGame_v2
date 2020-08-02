import java.awt.*;

public class BlockZ extends Shape {

    public BlockZ(Point main_) {
        super(main_);
        int x = main_.x;
        int y = main_.y;

        top = new Point(x, y-1);
        below = new Point(x, y);
        lelf = new Point(x-1, y-1);
        right = new Point(x+1, y);
        color = Color.ORANGE;
    }
}
