public class Collision {
    public static boolean detectCollision(Paddle p, Ball b) {
        if(p.x + p.WIDTH > b.x && p.x < b.x + b.WIDTH && p.y + p.HEIGHT > b.y && p.y < b.y + b.HEIGHT ) {
            return true;
        }
        return false;
    }
}
