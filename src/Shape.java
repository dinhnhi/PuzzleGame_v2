import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Shape extends JPanel implements KeyListener {
    private Point main;
    protected Point top, below;
    protected Point lelf, right;
    Color color;

/*    public Shape(){

    }*/
    public Shape(Point main_){
        main = main_;

    }

    void setTop(Point top_){
        top = top_;
    }
    void setBelow(Point below_){
        below = below_;
    }
    void setLelf(Point lelf_){
        lelf = lelf_;
    }
    void setRight(Point right_){
        right = right_;
    }
    void setColor(Color color_){color = color_;}
    void setAll(Point top_, Point below_,
                     Point lelf_, Point right_){

        top = top_;
        below = below_;
        lelf = lelf_;
        right = right_;
    }
    void setMain(Point main_){
        main = main_;
    }

    Point getTop(){
        return (Point)top.clone();
    }
    Point getBelow(){
        return (Point)below.clone();
    }
    Point getLelf(){
        return (Point)lelf.clone();
    }
    Point getRight(){
        return (Point)right.clone();
    }
    Color getColor(){return color;}
    Point[] getAll(){
        return new Point[]{(Point)top.clone(), (Point)below.clone(),
                (Point)lelf.clone(), (Point)right.clone()};
    }


    void turnLelf(){ // - 90
        Point[] arr = new Point[]{top, below, lelf, right};
        for(int i=0; i<arr.length; ++i){

            int old_x = arr[i].x;
            int old_y = arr[i].y;

            // ap dung cong thuc quay quanh 1 diem bat ki
            arr[i].x = old_y + main.x - main.y;
            arr[i].y = -old_x + main.x + main.y;
        }
    }
    void turnRight(){ // + 90
        Point[] arr = new Point[]{top, below, lelf, right};
        for(int i=0; i<arr.length; ++i){
            int old_x = arr[i].x;
            int old_y = arr[i].y;

            // ap dung cong thuc quay quanh 1 diem bat ki
            arr[i].x = -old_y + main.x + main.y;
            arr[i].y = old_x - main.x + main.y;
        }
    }

    void moveLelf(){
        top.x -= 1;
        below.x -= 1;
        lelf.x -= 1;
        right.x -= 1;
        main.x -= 1;
    }
    void moveRight(){
        top.x += 1;
        below.x += 1;
        lelf.x += 1;
        right.x += 1;
        main.x += 1;
    }
    void moveDown(){
        top.y += 1;
        below.y += 1;
        lelf.y += 1;
        right.y += 1;
        main.y += 1;
    }
    void moveUp(){
        top.y -= 1;
        below.y -= 1;
        lelf.y -= 1;
        right.y -= 1;
        main.y -= 1;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                turnRight();
                break;
            case KeyEvent.VK_DOWN:
                turnLelf();
                break;
            case KeyEvent.VK_LEFT:
                moveLelf();
                break;
            case KeyEvent.VK_RIGHT:
                moveRight();
                break;
        }
        this.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Point[] arr = this.getAll();
        for(int i =0; i<arr.length; ++i)
            veMotKhoiVuong(g, arr[i]);
    }
    protected  void veMotKhoiVuong (Graphics graphics, Point newPoint){
        graphics.setColor(Color.BLACK);
        graphics.drawRect(newPoint.x*10, newPoint.y*10, 10, 10);
        graphics.setColor(color);
        graphics.fillRect(newPoint.x*10, newPoint.y*10, 10, 10);
    }
}
