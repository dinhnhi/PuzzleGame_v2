import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.*;

public class Main extends JPanel {
    private JFrame frame;
    public static void main(String args[]){
        Main main = new Main();
        main.play();
        System.out.println("Hello");
    }

    void play(){
        frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScreenDrawBlock draw = new ScreenDrawBlock(new Point(10, 10), 10, 20, 20);
        frame.addKeyListener(draw);
        frame.add(draw);
        frame.setVisible(true);

        draw.play();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
    protected  void veMotKhoiVuong (Graphics graphics, Point newPoint){
        graphics.setColor(Color.black);
        graphics.drawRect(newPoint.x*10, newPoint.y*10, 10, 10);
    }
}
