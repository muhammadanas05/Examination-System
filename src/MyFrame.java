import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame {
    public static void main(String[] args) {


    JFrame frame = new JFrame("Examination System");
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    centerWindow(frame);

    WelcomePanel welcomePanel = new WelcomePanel();
        frame.add(welcomePanel, BorderLayout.CENTER);


    JButton registerButton = new JButton("Register");
    JButton loginButton = new JButton("Login");
    BottomPanel bottomPanel = new BottomPanel(registerButton, loginButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            RegistrationDialog registrationDialog = new RegistrationDialog(frame);
        }
    });

        loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            LoginDialog loginDialog = new LoginDialog(frame);
            frame.dispose();
        }
    });

        frame.setVisible(true);
    }
    private static void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        frame.setLocation(x, y);
    }
}