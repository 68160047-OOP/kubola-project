package masterchef;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KitchenPanel extends JPanel {
    private Image bg, counter, potNormal, potFire, orderImg;
    private Chef chef;
    private BaseRecipe recipe;
    private Ingredient activeObj;
    private int timeLeft;
    private Timer timer;
    private boolean isPotPlaced = false;
    private boolean isDragging = false;
    private Rectangle targetZone = new Rectangle(500, 380, 220, 200);
    private Runnable onWin, onLose;
    private int stageIdx;

    // Feedback
    private String feedbackMsg = "";
    private Color feedbackColor = Color.GREEN;
    private int feedbackAlpha = 0;
    private Timer feedbackTimer;

    // Ingredient slots (แสดงวัตถุดิบที่ต้องใส่)
    private String[] allIngredients;
    private int currentStep = 0;

    public KitchenPanel(BaseRecipe r, int stageIdx, Runnable win, Runnable lose) {
        this.recipe = r;
        this.stageIdx = stageIdx;
        this.onWin = win;
        this.onLose = lose;
        this.timeLeft = r.getTimeLimit();
        this.allIngredients = r.getIngredients();

        this.bg       = AssetLoader.loadImage("bg_kitchen.png");
        this.counter  = AssetLoader.loadImage("counter.png");
        this.potNormal = AssetLoader.loadImage("pot.png");
        this.potFire  = AssetLoader.loadImage("pot_fire.png");
        this.orderImg = AssetLoader.loadImage("dish_" + r.getCountry().toLowerCase() + "_" + stageIdx + ".png");

        this.chef = new Chef();
        this.chef.setAppearance(r.getCountry());

        // เริ่มต้นด้วยหม้อ
        this.activeObj = new Ingredient("Pot", 150, 620, potNormal);

        // ปุ่มกลับ
        setLayout(null);
        JButton backBtn = new JButton("← เมนู");
        backBtn.setBounds(10, 10, 90, 30);
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
        backBtn.setBackground(new Color(52, 73, 94));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> { timer.stop(); lose.run(); });
        add(backBtn);

        // Timer นับถอยหลัง
        timer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                timer.stop();
                showResult(false);
            }
            repaint();
        });
        timer.start();

        // Feedback fade timer
        feedbackTimer = new Timer(50, e -> {
            feedbackAlpha -= 8;
            if (feedbackAlpha <= 0) { feedbackAlpha = 0; ((Timer)e.getSource()).stop(); }
            repaint();
        });

        setupMouse();
    }

    private void setupMouse() {
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (activeObj != null && activeObj.contains(e.getPoint())) {
                    isDragging = true;
                }
            }

            @Override public void mouseReleased(MouseEvent e) {
                isDragging = false;
                if (activeObj == null) return;

                if (activeObj.getBounds().intersects(targetZone)) {
                    handleDrop();
                } else {
                    activeObj.reset();
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) {
                if (isDragging && activeObj != null) {
                    activeObj.move(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    private void handleDrop() {
        if (!isPotPlaced) {
            // วางหม้อสำเร็จ
            isPotPlaced = true;
            showFeedback("หม้อพร้อมแล้ว!", Color.CYAN);
            recipe.next();
            if (recipe.isFinished()) {
                timer.stop(); showResult(true);
            } else {
                activeObj = new Ingredient(recipe.getTarget(), 150, 620);
            }
        } else {
            // วางวัตถุดิบ
            recipe.next();
            showFeedback("ใส่แล้ว!", new Color(39, 174, 96));
            currentStep++;
            if (recipe.isFinished()) {
                timer.stop(); showResult(true);
            } else {
                activeObj = new Ingredient(recipe.getTarget(), 150, 620);
            }
        }
    }

    private void showFeedback(String msg, Color c) {
        feedbackMsg = msg;
        feedbackColor = c;
        feedbackAlpha = 255;
        feedbackTimer.restart();
    }

    private void showResult(boolean win) {
        int score = win ? timeLeft * 10 : 0;
        if (win) GameStatus.addScore(score);

        // สร้าง overlay panel
        JPanel overlay = new JPanel(new GridBagLayout());
        overlay.setBackground(new Color(0, 0, 0, 180));
        overlay.setOpaque(true);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(win ? new Color(39, 174, 96) : new Color(192, 57, 43));
        card.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel resultLabel = new JLabel(win ? "🎉 สำเร็จ!" : "⏰ หมดเวลา!");
        resultLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dishLabel = new JLabel(recipe.getDishName());
        dishLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        dishLabel.setForeground(Color.WHITE);
        dishLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel(win ? "คะแนน: +" + score : "ไม่ได้คะแนน");
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn = new JButton(win ? "ด่านต่อไป →" : "ลองใหม่");
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
            if (win) onWin.run();
            else onLose.run();
        });

        card.add(resultLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(dishLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(btn);

        overlay.add(card);
        setLayout(new OverlayLayout(this));
        add(overlay, 0);
        revalidate(); repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        if (bg != null) g2.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        else { g2.setColor(new Color(200, 180, 140)); g2.fillRect(0, 0, getWidth(), getHeight()); }

        // Counter image removed - bg_kitchen already contains counter

        // Target zone (หม้อบนเตา)
        if (isPotPlaced) {
            Image pot = potFire != null ? potFire : potNormal;
            if (pot != null) g2.drawImage(pot, targetZone.x, targetZone.y, targetZone.width, targetZone.height, null);
        } else {
            // แสดง drop zone
            g2.setColor(new Color(255, 255, 255, 60));
            g2.fillRoundRect(targetZone.x, targetZone.y, targetZone.width, targetZone.height, 20, 20);
            g2.setColor(new Color(255, 255, 255, 120));
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{10}, 0));
            g2.drawRoundRect(targetZone.x, targetZone.y, targetZone.width, targetZone.height, 20, 20);
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Tahoma", Font.BOLD, 16));
            g2.drawString("วางหม้อที่นี่", targetZone.x + 55, targetZone.y + 110);
        }

        // Chef
        chef.draw(g2, 850, 200, 280, 450);

        // Active draggable object
        if (activeObj != null) activeObj.draw(g2);

        // Timer bar
        drawTimerBar(g2);

        // Order image (รูปเมนูที่ต้องทำ)
        drawOrderPanel(g2);

        // Ingredient progress
        drawIngredientProgress(g2);

        // Feedback
        if (feedbackAlpha > 0) {
            g2.setFont(new Font("Tahoma", Font.BOLD, 28));
            g2.setColor(new Color(feedbackColor.getRed(), feedbackColor.getGreen(), feedbackColor.getBlue(), feedbackAlpha));
            g2.drawString(feedbackMsg, targetZone.x + 20, targetZone.y - 20);
        }
    }

    private void drawTimerBar(Graphics2D g2) {
        int barX = 350, barY = 20, barW = 500, barH = 24;
        double ratio = timeLeft / (double) recipe.getTimeLimit();
        Color barColor = ratio > 0.5 ? new Color(39, 174, 96) : ratio > 0.25 ? new Color(243, 156, 18) : new Color(192, 57, 43);

        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(barX, barY, barW, barH, 12, 12);
        g2.setColor(barColor);
        g2.fillRoundRect(barX, barY, (int)(barW * ratio), barH, 12, 12);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.BOLD, 14));
        g2.drawString("⏱ " + timeLeft + "s", barX + barW + 10, barY + 17);
    }

    private void drawOrderPanel(Graphics2D g2) {
        // กล่องแสดงเมนูที่ต้องทำ
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(20, 20, 180, 200, 15, 15);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.BOLD, 13));
        g2.drawString("เมนูที่ต้องทำ:", 30, 42);

        if (orderImg != null) {
            g2.drawImage(orderImg, 35, 50, 130, 120, null);
        } else {
            g2.setColor(new Color(255, 200, 100));
            g2.fillRoundRect(35, 50, 130, 120, 10, 10);
        }

        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Tahoma", Font.BOLD, 14));
        String name = recipe.getDishName();
        g2.drawString(name, 30 + (120 - g2.getFontMetrics().stringWidth(name)) / 2, 190);

        // คะแนนรวม
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        g2.drawString("Score: " + GameStatus.totalScore, 30, 215);
    }

    private void drawIngredientProgress(Graphics2D g2) {
        if (!isPotPlaced || allIngredients == null) return;
        int x = 220, y = 30;
        g2.setFont(new Font("Tahoma", Font.BOLD, 13));
        g2.setColor(Color.WHITE);
        g2.drawString("วัตถุดิบ:", x, y);
        for (int i = 0; i < allIngredients.length; i++) {
            boolean done = i < currentStep;
            g2.setColor(done ? new Color(39, 174, 96) : new Color(200, 200, 200));
            g2.drawString((done ? "✓ " : "○ ") + allIngredients[i], x, y + 18 * (i + 1));
        }
    }
}
