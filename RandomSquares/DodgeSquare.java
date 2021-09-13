import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Try to dodge all the sqaures and make it to the green area
 *
 * @author Abdul Asif && Zohaib Asif
 * @version 4/10/20
 */

public class DodgeSquare extends KeyAdapter implements Runnable {
    
    private JFrame window;
    private JPanel panel;
    private final int WINDOW_WIDTH = 700;
    private final int WINDOW_HEIGHT = 500;
    private final int DELAY = 33;
    
    private Player player;
    private final int START_X = 20;
    private final int START_Y = WINDOW_HEIGHT/2 - 30;
    
    private Particle deathMin;
    private Particle deathMax;
    private ExplosionEffect deathAnimation;
    
    public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame window = new JFrame("Dodge Square");
        window.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(new Color(245,237,122));
                g.fillRect(0,0,100,WINDOW_HEIGHT);

                g.setColor(new Color(156,255,64));
                g.fillRect(600,0,WINDOW_WIDTH,WINDOW_HEIGHT);

                nextLevel();
                checkCollisions();
                
                if(deathAnimation != null) deathAnimation.draw(g);
                EnemyGenerator.draw(g);
                player.draw(g);
            }
        };

        window.add(panel);
        window.addKeyListener(this);

        window.pack();
        window.setVisible(true);

        player = new Player(START_X,START_Y,15,15,0,0,Color.BLUE,panel);
        player.start();
        EnemyGenerator.generate(2,panel);
        deathMin = new Particle(player.x,player.y,3,3,-4,-4,10,Color.BLUE,panel);
        deathMax = new Particle(player.x + player.width,player.y + player.height,6,6,4,4,30,Color.BLUE,panel);
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 38 && player.speedY != 4) {
            
            player.speedY = -4;
        }

        if(e.getKeyCode() == 40 && player.speedY != -4) {
            player.speedY = 4;
        }

        if(e.getKeyCode() == 37 && player.speedX != 4) {
            player.speedX = -4;
        }

        if(e.getKeyCode() == 39 && player.speedX != -4) {
            player.speedX = 4;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 38 && player.speedY == -4) {
            player.speedY = 0;
        }

        if(e.getKeyCode() == 40 && player.speedY == 4) {
            player.speedY = 0;
        }

        if(e.getKeyCode() == 37 && player.speedX == -4) {
            player.speedX = 0;
        }

        if(e.getKeyCode() == 39 && player.speedX == 4) {
            player.speedX = 0;
        }
    }
    
    public void checkCollisions() {
        ArrayList<Enemy> enemies = EnemyGenerator.getEnemies();
        for(Enemy enemy : enemies) {
            if(Collision.detected(player,enemy)) {
                deathMin.x = player.x;
                deathMin.y = player.y;
                deathMax.x = player.x + player.width;
                deathMax.y = player.y + player.height;
                deathAnimation = new ExplosionEffect(deathMin,deathMax,50);
                player.x = START_X;
                player.y = START_Y;
            }
        }
    }
    
    public void nextLevel() {
        if(player.x > WINDOW_WIDTH - 50) {
            int amount = EnemyGenerator.getAmount()+2;
            EnemyGenerator.degenerate();
            EnemyGenerator.generate(amount,panel);
            player.x = START_X;
            player.y = START_Y;
        }
    }
    
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new DodgeSquare());
    }

}
