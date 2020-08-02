import java.awt.*;

public class BlockT extends Shape{
    public BlockT(Point main_) {
        super(main_);
        int x = main_.x;
        int y = main_.y;

        top = new Point(x, y);
        below = new Point(x, y+1);
        lelf = new Point(x-1, y);
        right = new Point(x+1, y);
        color = Color.BLUE;
    }
}
