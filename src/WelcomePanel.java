import javax.swing.*;
import java.awt.*;

class WelcomePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        Color color1 = new Color(84, 152, 241);
        Color color2 = new Color(128, 203, 196);
        GradientPaint gradientPaint = new GradientPaint(0, height, color1, 0, 0, color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
    }

    public WelcomePanel() {
        setBackground(new Color(84, 152, 241, 255));
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("WELCOME TO THE EXAMINATION SYSTEM");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel watermarkLabel = new JLabel("DESIGNED BY MUHAMMAD ANAS");
        watermarkLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        watermarkLabel.setForeground(Color.WHITE);
        watermarkLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel watermarkPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        watermarkPanel.setOpaque(false);
        watermarkPanel.add(watermarkLabel);

        add(welcomeLabel, BorderLayout.CENTER);
        add(watermarkPanel, BorderLayout.SOUTH);
    }


        // Create a GridBagLayout and set constraints to center the component
        // GridBagLayout layout = new GridBagLayout();
        // setLayout(layout);

        // GridBagConstraints constraints = new GridBagConstraints();
        // constraints.gridx = 0;
        // constraints.gridy = 0;
        // constraints.weightx = 1.0;
        // constraints.weighty = 1.0;
        // constraints.fill = GridBagConstraints.CENTER;

        // layout.setConstraints(welcomeLabel, constraints);

}