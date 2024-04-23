import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class AnswerKeyCreationDialog extends JDialog {
    private JTextArea answerArea;

    public AnswerKeyCreationDialog(JFrame parentFrame) {
        super(parentFrame, "Create Answer Key", true);
        setSize(400, 200);
        setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JLabel answerLabel = new JLabel("Answer:");
        answerArea = new JTextArea();
        JScrollPane answerScrollPane = new JScrollPane(answerArea);
        panel.add(answerLabel, BorderLayout.NORTH);
        panel.add(answerScrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        panel.add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAnswerKey();
                dispose();
            }
        });

        setVisible(true);
    }

    private void saveAnswerKey() {
        try {
            String filename = "answerkey.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(answerArea.getText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}