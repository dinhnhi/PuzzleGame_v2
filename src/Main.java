import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.*;

public class Main extends JPanel {
    private JFrame frame;

    public static void main(String args[]){
        Main main = new Main();
        main.play();
    }

    void play(){
        frame = new JFrame();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setSize(200, 200);


        ScreenDrawBlock draw = new ScreenDrawBlock();
        frame.add(draw, BorderLayout.CENTER);
        frame.addKeyListener(draw);

        //frame.repaint();

        frame.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
    protected  void veMotKhoiVuong (Graphics graphics, Point newPoint){
        graphics.setColor(Color.black);
        graphics.drawRect(newPoint.x*10, newPoint.y*10, 10, 10);
    }
}
