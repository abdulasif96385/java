import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * Write a description of class EnemyGenerator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyGenerator {
    
    private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private static Random random = new Random();
    
    public static void generate(int amount, JPanel container) {
        for(int i = 0; i < amount; i++) {
            int side = random.nextInt(30) + 20;
            int x = random.nextInt(501 - side) + 100;
            int y = random.nextInt(501 - side);
            int speedX = random.nextInt(10) + 1;
            int speedY = random.nextInt(10) + 1;
            
            Enemy enemy = new Enemy(x,y,side,side,speedX,speedY,container);
            enemies.add(enemy);
            enemy.start();
        }
    }
    
    public static void degenerate(){
        enemies.clear();
    }
    
    public static int getAmount() {
        return enemies.size();
    }
    
    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    
    public static void draw(Graphics g) {
        for(Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }
    
}
