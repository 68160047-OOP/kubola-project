package masterchef;
import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JPanel {
    public MenuScreen(String nation, int unlocked, java.util.function.Consumer<Integer> onSelect, Runnable onBack) {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 52, 54));

        // Top bar: ปุ่มกลับ + ชื่อ
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JButton backBtn = new JButton("← แผนที่");
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        backBtn.setBackground(new Color(52, 73, 94));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> onBack.run());

        JLabel title = new JLabel(nation + " Kitchen", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(1, 5, 15, 15));
        grid.setBackground(new Color(45, 52, 54));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        for (int i = 0; i < 5; i++) {
            BaseRecipe r = new RecipeBook(nation, i);
            JButton b = new JButton("<html><center>Stage " + (i + 1) + "<br><small>" + r.getDishName() + "</small></center></html>");
            b.setEnabled(i <= unlocked);
            b.setBackground(i <= unlocked ? new Color(39, 174, 96) : new Color(99, 110, 114));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Tahoma", Font.BOLD, 13));
            b.setFocusPainted(false);
            int idx = i;
            b.addActionListener(e -> onSelect.accept(idx));
            grid.add(b);
        }
        add(grid, BorderLayout.CENTER);
    }
}