package masterchef;
import java.awt.*;

public class Ingredient implements Draggable {
    private Image img;
    private Rectangle rect;
    private String name;
    private int startX, startY;

    public Ingredient(String name, int x, int y, Image customImg) {
        this.name = name; this.startX = x; this.startY = y;
        this.rect = new Rectangle(x, y, 110, 110);
        this.img = customImg;
    }

    public Ingredient(String name, int x, int y) {
        this.name = name; this.startX = x; this.startY = y;
        this.rect = new Rectangle(x, y, 110, 110);
        
        // ถ้าเป็น Pot ให้ดึง pot.png ถ้าเป็นอย่างอื่นให้เติม ing_ นำหน้า
        String fileName = (name.equalsIgnoreCase("Pot")) ? "pot.png" : "ing_" + name.toLowerCase() + ".png";
        this.img = AssetLoader.loadImage(fileName);
    }

    @Override public void move(int x, int y) { rect.x = x - 55; rect.y = y - 55; }
    @Override public boolean contains(Point p) { return rect.contains(p); }
    @Override public void reset() { rect.x = startX; rect.y = startY; }
    public void draw(Graphics2D g2) {
        if(img != null) g2.drawImage(img, rect.x, rect.y, 110, 110, null);
        else {
            g2.setColor(Color.RED); g2.drawRect(rect.x, rect.y, 110, 110);
            g2.drawString(name, rect.x, rect.y + 130);
        }
    }
    public Rectangle getBounds() { return rect; }
}