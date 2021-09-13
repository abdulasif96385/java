import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy extends GameObj
{
    
    public Enemy(int x, int y, int width, int height, int speedX, int speedY, JPanel container) {
        super(x,y,width,height,speedX,speedY,Color.red,container);
    }

    @Override
    public void contain() {
        if(x < 100) {
            x = 100;
            speedX *= -1;
        }
        else if(x > container.getWidth() - 100 - width) {
            x = container.getWidth() - 100 - width;
            speedX *= -1;
        }
        
        if(y < 0) {
            y = 0;
            speedY *= -1;
        }
        else if(y > container.getHeight() - height) {
            y = container.getHeight() - height;
            speedY *= -1;
        }
    }
    
    @Override
    public void run() {
        while(isRunning()) {
            try {
                sleep(33);
            }
            catch(InterruptedException e){};
            
            x += speedX;
            y += speedY;
            contain();
        }
    }
    
}
