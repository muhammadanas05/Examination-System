import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComparatorGUI {
    private JFrame frame;
    private JTable table;

    // public static void main(String[] args) {
    // EventQueue.invokeLater(new Runnable() {
    // public void run() {
    // try {
    // ComparatorGUI window = new ComparatorGUI();
    // window.frame.setVisible(true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // });
    // }

    public ComparatorGUI() {
        initialize();
        centerWindow(frame);
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        // Set table headers
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Username");

        String answerKeyFile = "answerkey.csv";
        int numQuestions = getNumQuestions(answerKeyFile);

        for (int i = 1; i <= numQuestions; i++) {
            model.addColumn("Q" + i);
        }

        model.addColumn("Result");

        table.setModel(model);

        List<String> usernames = readUsernamesFromCSV("result.csv");
        for (String username : usernames) {
            String questionsFile = "students/" + username + ".csv";

            Map<String, String> questionAnswers = readCSVFile(questionsFile);
            Map<String, String> answerKey = readCSVFile(answerKeyFile);

            Map<String, String> userAnswers = new HashMap<>();
            userAnswers.put("username", username);

            compareAnswers(questionAnswers, answerKey, userAnswers);
            writeResultToTable(userAnswers, model);
        }

        int rowHeight = 50;
        table.setRowHeight(rowHeight);

        // Set font for table data
        Font font = new Font("Arial", Font.BOLD, 16);
        table.setFont(font);

        frame.pack();
    }

    private static int getNumQuestions(String answerKeyFile) {
        int numQuestions = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(answerKeyFile))) {
            while (br.readLine() != null) {
                numQuestions++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numQuestions;
    }

    private static List<String> readUsernamesFromCSV(String filePath) {
        List<String> usernames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    String username = parts[0].trim(); // Extract the username
                    usernames.add(username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    private static void writeResultToTable(Map<String, String> userAnswers, DefaultTableModel model) {
        // Extract the required values from the userAnswers map
        String username = userAnswers.get("username");
        String result = userAnswers.get("Result");

        // Add a new row to the table with the extracted values
        Object[] rowData = new Object[model.getColumnCount()];
        rowData[0] = username;

        for (int i = 1; i < model.getColumnCount() - 1; i++) {
            String question = "Q" + i;
            rowData[i] = userAnswers.getOrDefault(question, "");
        }

        rowData[model.getColumnCount() - 1] = result;

        model.addRow(rowData);
    }

    private static Map<String, String> readCSVFile(String filePath) {
        Map<String, String> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String question = parts[0];
                    String answer = parts[1];
                    data.put(question, answer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void compareAnswers(Map<String, String> questionAnswers, Map<String, String> answerKey,
                                       Map<String, String> userAnswers) {
        int correctCount = 0;
        for (String question : questionAnswers.keySet()) {
            String userAnswer = questionAnswers.get(question);
            String correctAnswer = answerKey.get(question);
            if (correctAnswer != null) {
                boolean isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
                userAnswers.put(question, isCorrect ? "Correct" : "Incorrect");
                if (isCorrect) {
                    correctCount++;
                }
            } else {
                System.out.println("Answer key does not contain a correct answer for question: " + question);
            }
        }
        int totalQuestions = questionAnswers.size();
        userAnswers.put("Result", correctCount + "/" + totalQuestions);
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
