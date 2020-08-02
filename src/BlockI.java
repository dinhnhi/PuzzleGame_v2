import java.awt.*;

public class BlockI extends Shape{
    public BlockI(Point main_) {
        super(main_);

        int x = main_.x;
        int y = main_.y;

        top = new Point(x, y+1);
        below = new Point(x, y-2);
        lelf = new Point(x, y);
        right = new Point(x, y-1);
        color = Color.RED;
    }
}
