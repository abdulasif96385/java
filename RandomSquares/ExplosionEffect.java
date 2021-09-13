import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 * Write a description of class ExplosionEffect here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExplosionEffect{
    
    private static Random random = new Random();
    
    private ArrayList<Particle> particles;
    private Particle minParticle;
    private Particle maxParticle;
    private int size;
    
    public ExplosionEffect(Particle minParticle, Particle maxParticle, int size) {
        this.minParticle = minParticle;
        this.maxParticle = maxParticle;
        this.size = size;
        this.particles = new ArrayList<Particle>();

        for(int i = 0; i < size; i++) {
            int x = random.nextInt(maxParticle.x - minParticle.x) + minParticle.x;
            int y = random.nextInt(maxParticle.y - minParticle.y) + minParticle.y;
            int width = random.nextInt(maxParticle.width - minParticle.width) + minParticle.width;
            int height = random.nextInt(maxParticle.height - minParticle.height) + minParticle.height;
            int speedX = random.nextInt(maxParticle.speedX - minParticle.speedX) + minParticle.speedX;
            int speedY = random.nextInt(maxParticle.speedY - minParticle.speedY) + minParticle.speedY;
            int lifeSpan = random.nextInt(maxParticle.lifeSpan - minParticle.lifeSpan) + minParticle.lifeSpan;
            Color color;
            if(random.nextInt(2) == 0) {
                color = minParticle.color;
            }
            else {
                color = maxParticle.color;
            }
            Particle particle = new Particle(x,y,width,height,speedX,speedY,lifeSpan,color,minParticle.container);
            particle.start();
            particles.add(particle);
        }
    }

    public void draw(Graphics g) {
        if(!particles.isEmpty()) {
            for(Particle particle : particles) {
                particle.draw(g);
            }
            for(int i = 0; i < particles.size(); i++) {
                if(!particles.get(i).isRunning()) {
                    particles.remove(i);
                }
            }
        }
    }
}
