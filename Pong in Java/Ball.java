import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Ball {

    public final int WIDTH = 10;
    public final int HEIGHT = 10;
    private final Color COLOR = Color.RED;

    public int x;
    public int y;
    public int speedX;
    public int speedY;
    private Random r;

    public Ball(int x,int y) {
      this.x = x;
      this.y = y;
      r = new Random();
      setSpeed();
    }

    public void draw(Graphics g) {
      g.setColor(COLOR);
      g.fillRect(x,y,WIDTH,HEIGHT);
    }

    public void setSpeed() {
        int degree = r.nextInt(360);
        while(degree == 0 || degree == 180) {
            degree = r.nextInt(360);
        }
        int radians = (int) (degree * (Math.PI/180));
        speedX = (int) (Math.cos(radians) * 8);
        speedY = (int) (Math.sin(radians) * 8);
    }

    public void contain(int w,int h,Paddle p1,Paddle p2) {
        if(x < 0) {
            x = w/2 - WIDTH/2;
            y = h/2 - HEIGHT/2;
            setSpeed();
            p2.score++;
        }
        else if(x > w - WIDTH) {
            x =  w/2 - WIDTH/2;
            y = h/2 - HEIGHT/2;
            setSpeed();
            p1.score++;
        }
        if(y < 0) {
            y = 0;
            speedY *= -1;
        }
        else if(y > h - HEIGHT) {
            y = h - HEIGHT;
            speedY *= -1;
        }
    }

}
