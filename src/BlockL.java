import java.awt.*;

public class BlockL extends Shape {
    public BlockL(Point main_) {
        super(main_);
        int x = main_.x;
        int y = main_.y;

        top = new Point(x, y-1);
        below = new Point(x, y+1);
        lelf = new Point(x, y);
        right = new Point(x+1, y+1);
        color = Color.PINK;
    }
}
