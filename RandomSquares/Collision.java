
/**
 * Write a description of class Collision here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Collision {
    
    public static boolean detected(GameObj a, GameObj b) {
        if(a.x + a.width > b.x && a.x < b.x + b.width && a.y + a.height > b.y && a.y < b.y + b.height) {
            return true;
        }
        return false;
    }
    
}
