import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ScreenDrawBlock extends JPanel implements KeyListener {

    private Shape running;
    private ArrayList<Shape> shapesStoped;
    private boolean[][] grid = new boolean[20][20];


    public ScreenDrawBlock(){
        shapesStoped = new ArrayList<Shape>();
        fillGrid();
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
                    // tao 1 thread moi de roi khoi nay
                    try{
                        Thread.sleep(1000);
                    }catch (Exception ex){}
                    running.moveDown();
                    repaint();
                    // ..... .....
                }

                removeKeyListener(running);
                shapesStoped.add(running);
                Point[] all_point = running.getAll();
                for(int i=0; i< all_point.length; ++i){
                    int row = all_point[i].y;
                    int collunm = all_point[i].x;

                    grid[row][collunm]=true;
                }
            }
        }
    }

    void fillGrid(){
        int row = grid.length;
        int collunm = grid[0].length;

        for(int i=0; i<row; ++i)
            for(int j=0; j<collunm; ++j)
                grid[i][j] = false;
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
        int collum = grid[0].length;

        for(int i=0; i<collum; ++i)
            if(isFullCollumn(i))
                return true;
        return false;
    }
    boolean isFullCollumn(int collum_index){
        int row = grid.length;

        if(collum_index < grid[0].length){
            for(int i =0; i<row; ++i)
                if(!grid[i][collum_index])
                    return false;
            return true;
        }
        return false;
    }
    boolean isStop(Shape shape){
        // check dung cac khoi truoc do
            // check cac point trong cung 1 khoi khong chong len nhau
        Point[] all_point = shape.getAll();
        for(int i=0; i< all_point.length; ++i){
            int colunm = all_point[i].x;
            int row = all_point[i].y + 1;

            if(grid[row][colunm]) return true;
            if(row == grid.length - 1) return true;
        }

        return false;
    }

    boolean isTouchLelfEdge(Shape shape){
        Point lelf = shape.getLelf();

        if(lelf.x==0) return true;
        return false;
  }
    boolean isTouchRigthEdge(Shape shape){
        Point rigth = shape.getRight();

        if(rigth.x == grid[0].length - 1)
            return true;
        return false;
  }
    boolean isTouchLelfShape(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i<all_point.length; ++i){
            int x = all_point[i].x - 1;
            int y = all_point[i].y;

            if(grid[y][x]) return true;
        }

        return false;
    }
    boolean isTouchRigthShape(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i<all_point.length; ++i){
            int x = all_point[i].x + 1;
            int y = all_point[i].y;

            if(grid[y][x]) return true;
        }

        return false;
    }

    boolean isFullLine(int line_index){
        int collunm = grid[0].length;

        for(int c=0; c<collunm; ++c)
            if(!grid[line_index][c]) return false;
        return true;
    }
    void deleteLine(Boolean[][] source, int line_index){
        
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(running != null)
            drawShape(g, running);

        if(shapesStoped != null)
            for(Shape shape : shapesStoped)
                drawShape(g, shape);

        /*if(grid != null){
            int row = grid.length;
            int collumn = grid[0].length;

            for(int i=0; i<row; ++i)
                for(int j=0; j<collumn; ++j)
                    if(grid[i][j])
                        veMotKhoiVuong(g, new Point(j, i), );
        }*/
    }
    protected void veMotKhoiVuong (Graphics graphics, Point newPoint, Color color){
        graphics.setColor(Color.black);
        graphics.drawRect(newPoint.x*10, newPoint.y*10, 10, 10);
        graphics.setColor(color);
        graphics.fillRect(newPoint.x*10, newPoint.y*10, 10, 10);
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
                if(isTouchRigthEdge(running) || isTouchRigthEdge(running))
                    break;

                running.keyPressed(e);
                break;

            default:
                running.keyPressed(e);
                break;
        }

        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /*void printGrid(){
        int row = grid.length;
        int collunm = grid[0].length;

        for(int i=0; i<row; ++i) {
            for (int j = 0; j < collunm; ++j)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
        System.out.println("\n");
    }*/
}
