import javax.swing.*;
import java.awt.*;
class BottomPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        Color color1 = new Color(204, 204, 204);
        Color color2 = new Color(42, 46, 45);
        GradientPaint gradientPaint = new GradientPaint(0, height, color1, 0, 0, color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
    }

    public BottomPanel(JButton... buttons) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBackground(Color.WHITE);

        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(new Color(128, 203, 196));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            add(button);
        }
    }
}