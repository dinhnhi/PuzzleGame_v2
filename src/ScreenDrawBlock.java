import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ScreenDrawBlock extends JPanel implements KeyListener {

    private Shape running;
   // private boolean[][] grid = new boolean[20][20];
    Color[][] gridColor = new Color[20][20];


    public ScreenDrawBlock(){
        fillGridColor();
        this.setSize(20, 20);

        Runnable runnable = new myRun();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public class myRun implements Runnable{

        @Override
        public void run() {
            while(!isFullGrid()){
                revalidate();
                running = createRandomShape();
                addKeyListener(running);

                while(!isStop(running)) {
                    running.moveDown();

                    try{
                        Thread.sleep(500);
                        repaint();
                    }catch (Exception ex){}
                }

                removeKeyListener(running);
                Point[] all_point = running.getAll();
                for(int i=0; i< all_point.length; ++i){
                    int row = all_point[i].y;
                    int collunm = all_point[i].x;

                    gridColor[row][collunm] = running.color;
                }

                int row = gridColor.length;
                for(int i=row-1; i>=0;)
                    if(isFullLine(i))
                        deleteLine(gridColor, i);
                    else --i;
            }
        }
    }

    void fillGridColor(){
        int row = gridColor.length;
        int collunm = gridColor[0].length;

        for(int i=0; i<row; ++i)
            for(int j=0; j<collunm; ++j)
                gridColor[i][j] = Color.WHITE;
    }

    Shape createRandomShape(){
        Point def = new Point(10, 0);
        switch ((int)(Math.random()*4)){
            case 0: return new BlockL(def);
            //case 1: return new BlockI(def);
            case 2: return new BlockO(def);
            case 3: return new BlockT(def);
            case 4: return new BlockZ(def);
            default:
                return new BlockZ(def);
        }
    }

    boolean isFullGrid(){
        int collum = gridColor[0].length;

        for(int i=0; i<collum; ++i)
            if(isFullCollumn(i))
                return true;
        return false;
    }
    boolean isFullCollumn(int collum_index){
        int row = gridColor.length;

        if(collum_index < gridColor[0].length){
            for(int i =0; i<row; ++i)
                if(gridColor[i][collum_index] == Color.WHITE)
                    return false;
            return true;
        }
        return false;
    }
    boolean isStop(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i< all_point.length; ++i){
            int colunm = all_point[i].x;
            int row = all_point[i].y + 1;

            if(gridColor[row][colunm] != Color.WHITE) return true;
            if(row == gridColor.length - 1) return true;
        }
        return false;
    }

    boolean isTouchLelfEdge(Shape shape){
        Point[] all_point = shape.getAll();

        for(int i=0; i<all_point.length; ++i)
            if(all_point[i].x == 0) return true;

        return false;
  }
    boolean isTouchRigthEdge(Shape shape){
        Point[] all_point = shape.getAll();

        for(int i=0; i<all_point.length; ++i)
            if(all_point[i].x == gridColor[0].length-1) return true;

        return false;
  }
    boolean isTouchLelfShape(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i<all_point.length; ++i){
            int x = all_point[i].x - 1;
            int y = all_point[i].y;

            if(gridColor[y][x] != Color.WHITE) return true;
        }

        return false;
    }
    boolean isTouchRigthShape(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i<all_point.length; ++i){
            int x = all_point[i].x + 1;
            int y = all_point[i].y;

            if(gridColor[y][x] != Color.WHITE) return true;
        }

        return false;
    }

    boolean isFullLine(int line_index){
        int collunm = gridColor[0].length;

        for(int c=0; c<collunm; ++c)
            if(gridColor[line_index][c] == Color.WHITE) return false;
        return true;
    }
    void deleteLine(Color[][] source, int line_index){
        int row = source.length;
        int collunm = source[0].length;

        for(int i=line_index-1; i>=0; --i)
            for(int j=0; j<collunm; ++j)
                gridColor[i+1][j] = gridColor[i][j];

            for(int j=0; j<collunm; ++j)
                gridColor[0][j] = Color.WHITE;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(0, 0, 200, 200);

        if(gridColor != null){
            int row = gridColor.length;
            int collunm = gridColor[0].length;

            for(int i=0; i<row; ++i)
                for(int j=0; j<collunm; ++j) {

                    g.setColor(Color.lightGray);
                    g.drawRect(i*10, j*10, 10, 10);

                    if (gridColor[i][j] != Color.WHITE)
                        veMotKhoiVuong(g, new Point(j, i), gridColor[i][j]);
                }
        }

        if(running != null)
            drawShape(g, running);

    }
    protected void veMotKhoiVuong (Graphics graphics, Point newPoint, Color color){
        graphics.setColor(color);
        graphics.fillRect(newPoint.x*10, newPoint.y*10, 10, 10);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(newPoint.x*10, newPoint.y*10, 10, 10);
    }
    void drawShape(Graphics graphics, Shape shape){
        if(shape != null){
            Point[] arr = shape.getAll();
            for(int i =0; i<arr.length; ++i)
                veMotKhoiVuong(graphics, arr[i], shape.getColor());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(isTouchLelfEdge(running) || isTouchLelfShape(running))
                    break;

                running.keyPressed(e);
                break;

            case KeyEvent.VK_RIGHT:
                if(isTouchRigthEdge(running) || isTouchRigthShape(running))
                    break;

                running.keyPressed(e);
                break;

            case KeyEvent.VK_SPACE:
                if(isStop(running))
                    break;

                running.moveDown();
            default:
                running.keyPressed(e);
                break;
        }

        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

}
