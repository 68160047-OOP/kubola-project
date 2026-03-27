package masterchef;
import javax.swing.*;
import java.awt.*;

public class MapScreen extends JPanel {
    private static final String[] NATIONS = {"Thailand", "Japan", "Italy", "France", "China"};
    private Chef chef = new Chef();
    private Image mapBg = AssetLoader.loadImage("bg_map.png");

    public MapScreen(java.util.function.Consumer<String> onSelect) {
        setLayout(new BorderLayout());
        chef.setAppearance(null);

        JLabel title = new JLabel("เลือกประเทศ", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        title.setOpaque(false);
        add(title, BorderLayout.NORTH);

        JPanel p = new JPanel(new GridLayout(1, 5, 20, 20));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(20, 40, 80, 40));

        for (String n : NATIONS) {
            Image img = AssetLoader.loadImage("landmark_" + n.toLowerCase() + ".png");
            JButton b = new JButton("<html><center>" + n + "</center></html>");
            b.setFont(new Font("Tahoma", Font.BOLD, 16));
            if (img != null) {
                b.setIcon(new ImageIcon(img.getScaledInstance(160, 180, Image.SCALE_SMOOTH)));
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
            } else {
                b.setBackground(new Color(52, 73, 94));
            }
            b.setVerticalTextPosition(SwingConstants.BOTTOM);
            b.setHorizontalTextPosition(SwingConstants.CENTER);
            b.setForeground(Color.WHITE);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addActionListener(e -> onSelect.accept(n));
            p.add(b);
        }
        add(p, BorderLayout.CENTER);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapBg != null) {
            g.drawImage(mapBg, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(30, 39, 46));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        chef.draw((Graphics2D) g, 20, 480, 110, 200);
    }
}