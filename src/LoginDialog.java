import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String role;

    public LoginDialog(JFrame parentFrame) {
        super(parentFrame, "Login", true);
        setSize(300, 200);
        setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        add(panel);

        JLabel roleLabel = new JLabel("Role:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JComboBox<String> roleComboBox = new JComboBox<>(new String[] { "Student", "Teacher" });
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                role = (String) roleComboBox.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Perform login
                if (!username.isEmpty() && !password.isEmpty()) {
                    boolean isLoggedIn = loginUser(role.toLowerCase(), username, password);
                    if (isLoggedIn) {
                        JOptionPane.showMessageDialog(LoginDialog.this, "Login successful!");
                        dispose();
                        if (role.equalsIgnoreCase("Teacher")) {
                            openTeacherWindow(username);
                        } else if (role.equalsIgnoreCase("Student")) {
                            openStudentWindow(username);
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginDialog.this,
                                "Please Register Yourself!\nOR\nInsert Correct Username or Password!");
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Please enter username and password.");
                }
            }
        });

        setVisible(true);
    }

    private boolean loginUser(String role, String username, String password) {
        try {
            String filename = role + "s.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedUsername = parts[0];
                String savedPassword = parts[1];

                if (savedUsername.equals(username) && savedPassword.equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openTeacherWindow(String username) {
        TeacherWindow teacherWindow = new TeacherWindow(username);
        teacherWindow.setVisible(true);
    }

    private void openStudentWindow(String username) {
        StudentWindow studentWindow = new StudentWindow(username);
        studentWindow.setVisible(true);
        this.dispose();
    }
}