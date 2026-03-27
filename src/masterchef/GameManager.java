package masterchef;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameManager extends JFrame {
    private CardLayout cards = new CardLayout();
    private JPanel container = new JPanel(cards);
    private HashMap<String, Integer> progress = new HashMap<>();

    public GameManager() {
        String[] ns = {"Thailand", "Japan", "Italy", "France", "China"};
        for(String n : ns) progress.put(n, 0);
        setTitle("World Chef Saga: Perfect 15");
        setSize(1200, 800); setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        container.add(new StartScreen(() -> cards.show(container, "MAP")), "START");
        container.add(new MapScreen(this::showMenu), "MAP");
        add(container);
        cards.show(container, "START");
        setLocationRelativeTo(null); setVisible(true);
    }

    private void showMenu(String nation) {
        container.add(new MenuScreen(nation, progress.get(nation), i -> startLevel(nation, i), () -> cards.show(container, "MAP")), "MENU");
        cards.show(container, "MENU");
        container.revalidate(); container.repaint();
    }

    private void startLevel(String n, int i) {
        BaseRecipe r = new RecipeBook(n, i);
        container.add(new KitchenPanel(r, i, () -> { 
            if(i == progress.get(n) && i < 4) progress.put(n, i + 1);
            showMenu(n);
        }, () -> showMenu(n)), "GAME");
        cards.show(container, "GAME");
        container.revalidate(); container.repaint();
    }
}