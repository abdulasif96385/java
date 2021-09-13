import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Write a description of class Particle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Particle extends GameObj {

    public int lifeSpan;
    
    private int life;
     
    public Particle(int x, int y, int width, int height, int speedX, int speedY, int lifeSpan, Color color, JPanel container) {
        super(x,y,width,height,speedX,speedY,color,container);
        
        this.life = 0;
        this.lifeSpan = lifeSpan;
    }

    public boolean living() {
        if(life >= lifeSpan) {
            stopRunning();
            return false;
        }
        return true;
    }
    
    @Override
    public void run() {
        while(isRunning()) {

            try {
                sleep(33);
            }
            catch(InterruptedException e){};
            
            living();
            life++;
            x += speedX;
            y += speedY;
        }
    }
    
}
