package masterchef;
import java.awt.*;

public class Chef {
    private Image sprite;
    public void setAppearance(String country) {
        // หน้าแผนที่ใช้ chef_no_hat.png หน้าครัวใช้ chef_thailand.png ฯลฯ
        String fileName = (country == null) ? "chef_no_hat.png" : "chef_" + country.toLowerCase() + ".png";
        this.sprite = AssetLoader.loadImage(fileName);
    }
    public void draw(Graphics2D g2, int x, int y, int w, int h) {
        if(sprite != null) g2.drawImage(sprite, x, y, w, h, null);
        else {
            g2.setColor(Color.MAGENTA); g2.drawRect(x, y, w, h);
            g2.drawString("Chef Missing", x, y);
        }
    }
}