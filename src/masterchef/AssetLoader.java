package masterchef;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

public class AssetLoader {
    public static Image loadImage(String path) {
        String projectDir = System.getProperty("user.dir");
        String name = path.toLowerCase().trim();
        
        // รายการพิกัดที่โปรแกรมจะวิ่งไปหา
        String[] locations = {
            projectDir + File.separator + "resources" + File.separator + "assets" + File.separator + name,
            projectDir + File.separator + "src" + File.separator + "masterchef" + File.separator + "assets" + File.separator + name,
            projectDir + File.separator + "bin" + File.separator + "masterchef" + File.separator + "assets" + File.separator + name,
            projectDir + File.separator + "assets" + File.separator + name,
        };

        for (String loc : locations) {
            File f = new File(loc);
            if (f.exists() && !f.isDirectory()) {
                return new ImageIcon(loc).getImage();
            }
        }
        System.err.println("❌ Missing: " + name);
        return null;
    }
}