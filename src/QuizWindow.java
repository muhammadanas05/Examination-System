import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

class QuizWindow extends JFrame {
    private JComboBox<String> questionComboBox;
    private ButtonGroup radioButtonGroup;
    private JPanel radioPanel;
    private JTextArea questionArea;
    private JComboBox<String> subjectComboBox;
    public static int TIME;
    JButton submitButton = new JButton("Submit");
    JButton finishButton = new JButton("Finish");
    private String username;
    private String sub;
    private Timer timer;
    private JLabel timerLabel;
    private Map<String, String> selectedOptions;
    private Set<String> answeredQuestions;
    private String[][] radioButtonOptions = {
            {"A", "B", "C", "D"},
    };

    public QuizWindow(String subject, String username) {
        setTitle("Quiz Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.username = username;
        this.sub = subject;
        selectedOptions = new HashMap<>();
        answeredQuestions = new HashSet<>();

        subjectComboBox = new JComboBox<>(new String[]{
                "Database Management System Theory (DBT-2001)",
                "Database Management System Lab (DBL-2001)",
                "Object Oriented Programming Theory (OOPT-2002)",
                "Object Oriented Programming Lab (OOPL-2002)",
                "Operating System Lab (OSL-2003)",
                "Operating System Theory(OSL-2003)",
                "Software Design and Architecture (SDA-2004)"
        });

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new GridLayout(5, 1));

        questionComboBox = new JComboBox<>();
        radioButtonGroup = new ButtonGroup();
        questionArea = new JTextArea();

        leftPanel.add(questionComboBox, BorderLayout.NORTH);
        leftPanel.add(questionArea, BorderLayout.CENTER);

        radioPanel = new JPanel(new FlowLayout());
        for (String[] options : radioButtonOptions) {
            JRadioButton[] radioButtons = new JRadioButton[options.length];
            for (int i = 0; i < options.length; i++) {
                radioButtons[i] = new JRadioButton(options[i]);
                radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
                radioButtonGroup.add(radioButtons[i]);
                radioPanel.add(radioButtons[i]);
            }
        }

        rightPanel.add(radioPanel);
        rightPanel.add(submitButton);
        rightPanel.add(finishButton);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        loadQuiz(subject);

        questionComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = questionComboBox.getSelectedIndex();
                displayQuestion(index);
            }
        });

        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        finishButton.setFont(new Font("Arial", Font.BOLD, 14));

        submitButton.setForeground(Color.WHITE);
        finishButton.setForeground(Color.WHITE);

        submitButton.setBackground(new Color(52, 152, 219));
        finishButton.setBackground(new Color(52, 152, 219));

        submitButton.setFocusPainted(false);
        finishButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) questionComboBox.getSelectedItem();
                String selectedRadioButton = getSelectedRadioButton();
                // Check if a radio button is selected
                if (selectedRadioButton.isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "Please select an option!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the same question has already been answered
                if (answeredQuestions.contains(selectedOption)) {
                    JOptionPane.showMessageDialog(getParent(), "Question already answered!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add the answered question and selected options to the lists
                answeredQuestions.add(selectedOption);
                selectedOptions.put(selectedOption, selectedRadioButton);
                questionComboBox.setSelectedIndex(questionComboBox.getSelectedIndex() + 1); 
                // Clear radio button selection
                radioButtonGroup.clearSelection();

                // Show next question in dropdown box
                // showNextQuestion();
            }
        });
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCSVFile(username);
                System.out.println("-- Updated CSV");
                Comparator cmop = new Comparator(username);
                cmop.checkAnswersAndSave(username);
                System.out.println("Comparaing...");
                displayResult(username);
                System.out.println("Displaying..." + cmop.returnAnswer());
                // System.exit(1);
                // optionsFrame.dispose();
                QuizWindow.this.dispose();
            }
        });


        // Timer setup
        timerLabel = new JLabel("Time Remaining: 00:00");
        rightPanel.add(timerLabel);

//        int quizDurationMinutes = TIME;
        int quizDurationMinutes = 2;
        int quizDurationMilliseconds = quizDurationMinutes * 60 * 1000;

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long remainingTime = quizDurationMilliseconds - elapsedTime;

                if (remainingTime <= 0) {
                    timer.cancel();
                    timerLabel.setText("Time's up!");
                    submitButton.setEnabled(false);
                    finishButton.setEnabled(true);
                } else {
                    int minutes = (int) (remainingTime / 1000) / 60;
                    int seconds = (int) (remainingTime / 1000) % 60;
                    timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void displayResult(String targetUsername) {
        JFrame resultFrame = new JFrame("Result Window");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(600, 500);
        resultFrame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable resultTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        resultFrame.getContentPane().add(scrollPane);

        // Set table headers
        model.addColumn("Info");
        model.addColumn("Status");

        Comparator temp = new Comparator(username);
        
        String resultFile = "result.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(resultFile))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(targetUsername)) {
                    found = true;
                    for (int i = 0; i < parts.length; i++) {
                        Object[] rowData = new Object[2];
                        if (i == 0) {
                            rowData[0] = "Username";
                        } else if (i == 11) {
                            rowData[0] = "Result";
                        } else {
                            
                            if(i == parts.length)
                            {
                                rowData[0] = "Total";
                            }
                            else{
                                rowData[0] = "Q" + (i);
                            }
                        }
                        rowData[1] = parts[i];
                        model.addRow(rowData);
                    }
                    // Object[] tb = new Object[1];
                    // tb[1] = temp.RealCorrectCount;
                    // model.addRow(tb);
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(QuizWindow.this, "No result found for " + targetUsername, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set column widths
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Username column
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Result column
        int rowHeight = 50; // Adjust the value to change the row height
        resultTable.setRowHeight(rowHeight);

        // Set font for table data
        Font font = new Font("Arial", Font.BOLD, 16);
        resultTable.setFont(font);

        resultFrame.setVisible(true);
    }

    private String getSelectedRadioButton() {
        for (Component component : radioPanel.getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                    return radioButton.getText();
                }
            }
        }
        return "";
    }

    public int countQuestionsInFile(String filePath) {
        int questionCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("?")) {
                    questionCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionCount;
    }

    private void loadQuiz(String subject) {
        try {
            String filename = subject.toLowerCase() + "_quiz.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int questionCount = this.countQuestionsInFile(filename);
            int questionNumber = 1;
            // Skip the first 7 lines
            for (int i = 0; i < 7; i++) {
                reader.readLine();
            }
            while ((line = reader.readLine()) != null && questionNumber <= questionCount) {
                questionComboBox.addItem("Q" + questionNumber);
                questionNumber++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void displayQuestion(int index) {
        try {
            String subject = (String) subjectComboBox.getSelectedItem();
            String filename = subject.toLowerCase() + "_quiz.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            // Skip the first 5 lines
            for (int i = 0; i < 7; i++) {
                reader.readLine();
            }
            String line;
            int lineNumber = 0;
            StringBuilder questionText = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == (index * 6) + 1) {
                    questionText.append(line).append("\n"); // Append the question
                    // Append options a, b, c, d
                    questionText.append(reader.readLine()).append("\n");
                    questionText.append(reader.readLine()).append("\n");
                    questionText.append(reader.readLine()).append("\n");
                    questionText.append(reader.readLine()).append("\n");
                    break;
                }
            }
            reader.close();
            questionArea.setText(questionText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSVFile(String username) {
        try {
            if (!selectedOptions.isEmpty()) {
                FileWriter fileWriter = new FileWriter("students/" + username + ".csv");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                for (String question : selectedOptions.keySet()) {
                    String selectedOption = question;
                    String selectedRadioButton = selectedOptions.get(question);
                    printWriter.println(selectedOption + "," + selectedRadioButton);
                }

                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
