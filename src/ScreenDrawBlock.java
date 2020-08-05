import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class ScreenDrawBlock extends JPanel implements KeyListener {

    private Shape running;
    int ROW = 20;
    int COL = 20;
    Color[][] gridColor;
    int sizeUnit;

    Point location;

    public ScreenDrawBlock(Point location_, int row_, int col_, int size_){
        ROW = row_;
        COL = col_;
        sizeUnit = size_;
        location = location_;

        gridColor = new Color[ROW][COL];
        fillGridColor();
    }

    public void play(){
        /*Runnable runnable = new myRun();
        Thread thread = new Thread(runnable);
        thread.start();*/

        running = createRandomShape();
        addKeyListener(running);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!isStop(running)) {
                    running.moveDown();
                    repaint();
                }
                else{
                    // tắt điều khiển với running và đưa các khói của nó vào gridColor
                    removeKeyListener(running);
                    Point[] all_point = running.getAll();
                    for (int i = 0; i < all_point.length; ++i) {
                        int row = all_point[i].y;
                        int collunm = all_point[i].x;

                        if (row < 0) continue;
                        gridColor[row][collunm] = running.color;
                    }

                    // xóa những hàng full
                    int row = gridColor.length;
                    for (int i = row - 1; i >= 0; )
                        if (isFullLine(i))
                            deleteLine(gridColor, i);
                        else --i;

                    if(!isFullGrid()) {
                        running = createRandomShape();
                        addKeyListener(running);
                    }else{
                        gameOver("GAME OVER", 50);
                        ((Timer)e.getSource()).stop();
                    }
                }
            }
        });
        timer.start();
    }

    /*public class myRun implements Runnable{
        @Override
        public void run() {
            while (!isFullGrid()) {
                running = createRandomShape();
                addKeyListener(running);

                while (!isStop(running)) {

                    running.moveDown();
                    try {
                        Thread.sleep(400);
                        repaint();
                    } catch (Exception ex) {
                    }
                }

                // tắt điều khiển với running và đưa các khói của nó vào gridColor
                removeKeyListener(running);
                Point[] all_point = running.getAll();
                for (int i = 0; i < all_point.length; ++i) {
                    int row = all_point[i].y;
                    int collunm = all_point[i].x;

                    if (row < 0) continue;
                    gridColor[row][collunm] = running.color;
                }

                // xóa những hàng full
                int row = gridColor.length;
                for (int i = row - 1; i >= 0; )
                    if (isFullLine(i))
                        deleteLine(gridColor, i);
                    else --i;
            }

            gameOver("GAME OVER", 50);
        }

    }*/

    void fillGridColor(){

        for(int i=0; i<ROW; ++i)
            for(int j=0; j<COL; ++j)
                gridColor[i][j] = Color.WHITE;
    }

    Shape createRandomShape(){
        Point def = new Point(COL/2, -1);
        switch ((int)(Math.random()*4)){
            case 0: return new BlockL(def);
            case 1: return new BlockI(def);
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
        if(collum_index < gridColor[0].length){
            for(int i =0; i<ROW; ++i)
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
            int row = all_point[i].y;

            if(row < 0) continue;
            if(row == ROW - 1) return true;
            if(gridColor[row + 1][colunm] != Color.WHITE) return true;

        }
        return false;
    }

    boolean isOverEdge(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i<all_point.length; ++i){
            int x = all_point[i].x;

            if(x > COL -1) return true;
            if(x < 0) return true;
        }

        return false;
    }
    boolean isCollapseShape(Shape shape){
        Point[] all_point = shape.getAll();
        for(int i=0; i<all_point.length; ++i){
            int x = all_point[i].x;
            int y = all_point[i].y;

            if(x < 0 || x > COL -1) continue;
            if(y < 0 || y > ROW - 1) continue;
            if(gridColor[y][x] != Color.WHITE) return true;
        }

        return false;
    }
    boolean isFailed(Shape shape){
        return (isOverEdge(shape) || isCollapseShape(shape));
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

    void gameOver(String str, int fontSize){

        JLabel label = new JLabel(str);

        int width = (int)(label.getText().length() * fontSize *0.75); // tỉ lệ giữa size và width: 0.
        int heigth = fontSize;
        label.setSize(width, heigth);

        int x = (COL*sizeUnit - width)/2 + location.x;
        int y = (ROW*sizeUnit - heigth)/2 + location.y;
        label.setLocation(x, y);
        label.setFont(new Font("Consolas", Font.BOLD, fontSize ));
        this.add(label);

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gridColor != null){

            for(int i=0; i<ROW; ++i)
                for(int j=0; j<COL; ++j) {

                    g.setColor(Color.lightGray);
                    g.drawRect(location.x + j*sizeUnit, location.y + i*sizeUnit, sizeUnit, sizeUnit);

                    if (gridColor[i][j] != Color.WHITE)
                        veMotKhoiVuong(g, new Point(j, i), gridColor[i][j]);
                }
        }

        if(running != null)
            drawShape(g, running);

    }
    protected void veMotKhoiVuong (Graphics graphics, Point newPoint, Color color){
        if(newPoint.x <0 || newPoint.x > COL - 1)
            return;
        if(newPoint.y<0 || newPoint.y > ROW-1)
            return;

        graphics.setColor(color);
        graphics.fillRect(location.x + newPoint.x*sizeUnit, location.y + newPoint.y*sizeUnit, sizeUnit, sizeUnit);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(location.x + newPoint.x*sizeUnit, location.y + newPoint.y*sizeUnit, sizeUnit, sizeUnit);
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
                running.moveLelf();
                if(isFailed(running))
                    running.moveRight();
                break;

            case KeyEvent.VK_RIGHT:
                running.moveRight();
                if(isFailed(running))
                    running.moveLelf();
                break;

            case KeyEvent.VK_UP:
                running.turnRight();
                if(isFailed(running))
                    running.turnLelf();
                break;

            case KeyEvent.VK_DOWN:
                running.turnLelf();
                if(isFailed(running))
                    running.turnRight();
                break;

            case KeyEvent.VK_SPACE:
                if(!isStop(running))
                    running.moveDown();
                break;
        }

        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

}
