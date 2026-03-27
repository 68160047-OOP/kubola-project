package masterchef;
import javax.swing.*;
import java.awt.*;

public class StartScreen extends JPanel {
    private Image cover = AssetLoader.loadImage("cover.png");

    public StartScreen(Runnable start) {
        setLayout(new BorderLayout());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottom.setOpaque(false);

        JButton b = new JButton("▶  START GAME");
        b.setFont(new Font("Tahoma", Font.BOLD, 24));
        b.setBackground(new Color(255, 107, 107));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> start.run());

        bottom.add(b);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cover != null) {
            g.drawImage(cover, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Fallback background
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(30, 39, 46));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Tahoma", Font.BOLD, 60));
            g2.drawString("World Chef Saga", 200, 300);
            g2.setFont(new Font("Tahoma", Font.PLAIN, 28));
            g2.setColor(new Color(255, 107, 107));
            g2.drawString("Perfect 15", 460, 370);
        }
    }
}