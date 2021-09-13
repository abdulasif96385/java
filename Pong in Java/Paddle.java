import java.awt.Graphics;
import java.awt.Color;

public class Paddle {

  public final int WIDTH = 10;
  public final int HEIGHT = 60;
  public final int MAX_SPEED = 10;
  private final Color COLOR = Color.WHITE;

  public int x;
  public int y;
  public int acc;
  public int speed;
  public int score;

  public Paddle(int x,int y) {
    this.x = x;
    this.y = y;
    this.acc = 1;
    this.speed = 0;
    this.score = 0;
  }

  public void draw(Graphics g) {
    g.setColor(COLOR);
    g.fillRect(x,y,WIDTH,HEIGHT);
  }

  public void contain(int h) {
    if(y < 0) {
      y = 0;
      speed = 0;
    }
    else if(y > h - HEIGHT) {
      y = h - HEIGHT;
      speed = 0;
    }
  }

  public void displayScore(Graphics g,int x,int y) {
    g.setColor(COLOR);
    g.drawString("Score: " + score,x,y);
  }

}
