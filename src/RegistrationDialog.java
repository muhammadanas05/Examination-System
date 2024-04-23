import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class RegistrationDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegistrationDialog(JFrame parentFrame) {
        super(parentFrame, "Registration", true);
        setSize(300, 250);
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
        JButton registerButton = new JButton("Register");

        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleComboBox.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Perform registration
                if (!username.isEmpty() && !password.isEmpty()) {
                    boolean isRegistered = registerUser(role.toLowerCase(), username, password);
                    if (isRegistered) {
                        JOptionPane.showMessageDialog(RegistrationDialog.this, "Registration successful!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(RegistrationDialog.this, "Username already exist!");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegistrationDialog.this, "Please enter username and password.");
                }
            }
        });

        setVisible(true);
    }

    private boolean registerUser(String role, String username, String password) {
        try {
            String filename = role + "s.txt";
            File file = new File(filename);

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String existingUsername = parts[0];
                if (existingUsername.equals(username)) {
                    reader.close();
                    return false; // Username already exists
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(username + "," + password);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}