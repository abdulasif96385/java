import java.awt.*;

public class Button {

    public String string;
    public int x;
    public int y;
    public Color color;
    public Font font;

    public Button(String string,int x, int y) {
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.font = new Font("Times New Roman",Font.PLAIN,12);
    }

    public Button(String string,int x, int y,Color color,Font font) {
        this.string = string;
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(string,x,y);
    }

}
