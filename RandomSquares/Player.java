import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player extends GameObj {
    
    public Player(int x, int y, int width, int height, int speedX, int speedY, Color color, JPanel container) {
        super(x,y,width,height,speedX,speedY,color,container);
    }

    @Override
    public void contain() {
        if(x < 0) {
            x = 0;
        }
        else if(x > container.getWidth() - width) {
            x = container.getWidth() - width;
        }

        if(y < 0) {
            y = 0;
        }
        else if(y > container.getHeight() - height) {
            y = container.getHeight() - height;
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
            
            container.repaint();
        }
    }
}
