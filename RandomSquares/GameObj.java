import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * 
 *
 * @author Abdul Asif, Zohaib Asif
 */
abstract public class GameObj extends Thread {
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speedX;
    protected int speedY;
    protected Color color;
    protected JPanel container;
   
    private boolean running;
    
    public GameObj(int x, int y, int width, int height, int speedX, int speedY,  Color color, JPanel container) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.container = container;
        
        this.running = true;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void stopRunning() {
        running = false;
    }

    public void contain(){}

    public void run(){}
    
}


