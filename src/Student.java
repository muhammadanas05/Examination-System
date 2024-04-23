//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.HashSet;
//
//class StudentWindow extends JFrame {
//    private JComboBox<String> subjectComboBox;
//    private JButton submitButton;
//    private JButton displayResultButton;
//    String username;
//
//    public StudentWindow(String username) {
//        setTitle("Student Window");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(300, 200);
//        setLocationRelativeTo(null);
//        setLayout(new FlowLayout());
//        this.username = username;
//
//        JLabel subjectLabel = new JLabel("Select Subject:");
//        subjectComboBox = new JComboBox<>(new String[] { "Math", "Science", "English" });
//        submitButton = new JButton("Submit");
//
//        add(subjectLabel);
//        add(subjectComboBox);
//        add(submitButton);
//        // pack();
//
//        submitButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if (checkExistingCSVFile(username)) {
//                    JOptionPane.showMessageDialog(StudentWindow.this, username + " Has Already Submitted The Quiz.",
//                            "Error",
//                            JOptionPane.ERROR_MESSAGE);
//
//                    // Create the "Display Result" button
//                    displayResultButton = new JButton("Display Result");
//
//                    // Add action listener to the "Display Result" button
//                    displayResultButton.addActionListener(new ActionListener() {
//                        public void actionPerformed(ActionEvent e) {
//                            displayResult(username);
//                        }
//                    });
//
//                    // Add the "Display Result" button to the frame
//                    add(displayResultButton);
//                    remove(submitButton);
//                    pack(); // Resize the frame to accommodate the new button
//                    return;
//                } else {
//                    openQuizWindow();
//                }
//
//            }
//        });
//    }
//
//    public void displayResult(String targetUsername) {
//        JFrame resultFrame = new JFrame("Result Window");
//        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        resultFrame.setSize(600, 500);
//        resultFrame.setLocationRelativeTo(null);
//
//        DefaultTableModel model = new DefaultTableModel();
//        JTable resultTable = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(resultTable);
//
//        resultFrame.getContentPane().add(scrollPane);
//
//        // Set table headers
//        model.addColumn("Info");
//        model.addColumn("Status");
//
//        String resultFile = "result.csv";
//        try (BufferedReader br = new BufferedReader(new FileReader(resultFile))) {
//            String line;
//            boolean found = false; // Flag to indicate if the target username was found
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 2 && parts[0].equals(targetUsername)) {
//                    found = true;
//                    for (int i = 0; i < parts.length; i++) {
//                        Object[] rowData = new Object[2];
//                        if (i == 0) {
//                            rowData[0] = "Username";
//                        } else if (i == 4) {
//                            rowData[0] = "Result";
//                        } else {
//                            rowData[0] = "Q" + (i);
//                        }
//                        rowData[1] = parts[i];
//                        model.addRow(rowData);
//                    }
//                    break; // Exit the loop after adding the rows for the target username
//                }
//            }
//            if (!found) {
//                JOptionPane.showMessageDialog(StudentWindow.this, "No result found for " + targetUsername, "Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Set column widths
//        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Username column
//        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Result column
//        int rowHeight = 50; // Adjust the value to change the row height
//        resultTable.setRowHeight(rowHeight);
//
//        // Set font for table data
//        Font font = new Font("Arial", Font.BOLD, 16);
//        resultTable.setFont(font);
//
//        resultFrame.setVisible(true);
//    }
//
//    private void openQuizWindow() {
//        String subject = (String) subjectComboBox.getSelectedItem();
//        QuizWindow quizWindow = new QuizWindow(subject, this.username);
//        quizWindow.setVisible(true);
//    }
//
//    private boolean checkExistingCSVFile(String username) {
//        Path path = Paths.get("students/" + username + ".csv");
//        return Files.exists(path);
//    }
//}
//
//class QuizWindow extends JFrame {
//    private JComboBox<String> questionComboBox;
//    private ButtonGroup radioButtonGroup;
//    private JPanel radioPanel;
//    private JTextArea questionArea;
//    private JComboBox<String> subjectComboBox;
//    private JFrame optionsFrame;
//    JButton submitButton = new JButton("Submit");
//    JButton finishButton = new JButton("Finish");
//    private String username;
//    private String sub;
//    // private List<String> selectedQuestions = new ArrayList<>();
//    // private List<String> selectedOptions = new ArrayList<>();
//    private Map<String, String> selectedOptions;
//    private Set<String> answeredQuestions;
//    private String[][] radioButtonOptions = {
//            { "A", "B", "C", "D" }, };
//
//    public QuizWindow(String subject, String username) {
//        setTitle("Quiz Window");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(600, 300);
//        setLocationRelativeTo(null);
//        setLayout(new GridLayout(1, 2));
//        this.username = username;
//        this.sub = subject;
//        selectedOptions = new HashMap<>();
//        answeredQuestions = new HashSet<>();
//
//        subjectComboBox = new JComboBox<>(new String[] { "Math", "Science", "English" });
//        submitButton.setPreferredSize(new Dimension(50, 30));
//        finishButton.setPreferredSize(new Dimension(50, 30));
//
//        JPanel leftPanel = new JPanel(new BorderLayout());
//        JPanel rightPanel = new JPanel(new GridLayout(5, 1));
//
//        questionComboBox = new JComboBox<>();
//        radioButtonGroup = new ButtonGroup();
//        questionArea = new JTextArea();
//
//        leftPanel.add(questionComboBox, BorderLayout.NORTH);
//        leftPanel.add(questionArea, BorderLayout.CENTER);
//
//        radioPanel = new JPanel(new FlowLayout());
//        for (String[] options : radioButtonOptions) {
//            JRadioButton[] radioButtons = new JRadioButton[options.length];
//            for (int i = 0; i < options.length; i++) {
//                radioButtons[i] = new JRadioButton(options[i]);
//                radioButtonGroup.add(radioButtons[i]);
//                radioPanel.add(radioButtons[i]);
//            }
//        }
//
//        rightPanel.add(radioPanel);
//        rightPanel.add(submitButton);
//        rightPanel.add(finishButton);
//
//        add(leftPanel);
//        add(rightPanel);
//
//        loadQuiz(subject);
//
//        questionComboBox.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int index = questionComboBox.getSelectedIndex();
//                displayQuestion(index);
//            }
//        });
//        submitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String selectedOption = (String) questionComboBox.getSelectedItem();
//                String selectedRadioButton = getSelectedRadioButton();
//                // Check if a radio button is selected
//                if (selectedRadioButton.isEmpty()) {
//                    JOptionPane.showMessageDialog(optionsFrame, "Please select an option!", "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                // Check if the same question has already been answered
//                if (answeredQuestions.contains(selectedOption)) {
//                    JOptionPane.showMessageDialog(optionsFrame, "Question already answered!", "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                // Add the answered question and selected options to the lists
//                answeredQuestions.add(selectedOption);
//                selectedOptions.put(selectedOption, selectedRadioButton);
//
//                // Clear radio button selection
//                radioButtonGroup.clearSelection();
//
//                // Show next question in dropdown box
//                // showNextQuestion();
//            }
//        });
//
//        finishButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                updateCSVFile(username);
//                new Comparator(username);
//                displayResult(username);
//                // System.exit(1);
//                // optionsFrame.dispose();
//                QuizWindow.this.dispose();
//            }
//        });
//    }
//
//    public void displayResult(String targetUsername) {
//        JFrame resultFrame = new JFrame("Result Window");
//        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        resultFrame.setSize(600, 500);
//        resultFrame.setLocationRelativeTo(null);
//
//        DefaultTableModel model = new DefaultTableModel();
//        JTable resultTable = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(resultTable);
//
//        resultFrame.getContentPane().add(scrollPane);
//
//        // Set table headers
//        model.addColumn("Info");
//        model.addColumn("Status");
//
//        String resultFile = "result.csv";
//        try (BufferedReader br = new BufferedReader(new FileReader(resultFile))) {
//            String line;
//            boolean found = false; // Flag to indicate if the target username was found
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 2 && parts[0].equals(targetUsername)) {
//                    found = true;
//                    for (int i = 0; i < parts.length; i++) {
//                        Object[] rowData = new Object[2];
//                        if (i == 0) {
//                            rowData[0] = "Username";
//                        } else if (i == 4) {
//                            rowData[0] = "Result";
//                        } else {
//                            rowData[0] = "Q" + (i);
//                        }
//                        rowData[1] = parts[i];
//                        model.addRow(rowData);
//                    }
//                    break; // Exit the loop after adding the rows for the target username
//                }
//            }
//            if (!found) {
//                JOptionPane.showMessageDialog(QuizWindow.this, "No result found for " + targetUsername, "Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Set column widths
//        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Username column
//        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Result column
//        int rowHeight = 50; // Adjust the value to change the row height
//        resultTable.setRowHeight(rowHeight);
//
//        // Set font for table data
//        Font font = new Font("Arial", Font.BOLD, 16);
//        resultTable.setFont(font);
//
//        resultFrame.setVisible(true);
//    }
//
//    private String getSelectedRadioButton() {
//        for (Component component : radioPanel.getComponents()) {
//            if (component instanceof JRadioButton) {
//                JRadioButton radioButton = (JRadioButton) component;
//                if (radioButton.isSelected()) {
//                    return radioButton.getText();
//                }
//            }
//        }
//        return "";
//    }
//
//    public int countQuestionsInFile(String filePath) {
//        int questionCount = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.contains("?")) {
//                    questionCount++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return questionCount;
//    }
//
//    private void loadQuiz(String subject) {
//        try {
//            String filename = subject.toLowerCase() + "_quiz.txt";
//            BufferedReader reader = new BufferedReader(new FileReader(filename));
//            String line;
//            int questionCount = this.countQuestionsInFile(filename);
//            int questionNumber = 1;
//            while ((line = reader.readLine()) != null && questionNumber <= questionCount) {
//                questionComboBox.addItem("Q" + questionNumber);
//                questionNumber++;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void displayQuestion(int index) {
//        try {
//            String subject = (String) subjectComboBox.getSelectedItem();
//            String filename = subject.toLowerCase() + "_quiz.txt";
//            BufferedReader reader = new BufferedReader(new FileReader(filename));
//            String line;
//            int lineNumber = 0;
//            StringBuilder questionText = new StringBuilder();
//            while ((line = reader.readLine()) != null) {
//                lineNumber++;
//                if (lineNumber == (index * 6) + 1) {
//                    questionText.append(line).append("\n"); // Append the question
//                    // Append options a, b, c, d
//                    questionText.append(reader.readLine()).append("\n");
//                    questionText.append(reader.readLine()).append("\n");
//                    questionText.append(reader.readLine()).append("\n");
//                    questionText.append(reader.readLine()).append("\n");
//                    break;
//                }
//            }
//            reader.close();
//            questionArea.setText(questionText.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateCSVFile(String username) {
//        try {
//            if (!selectedOptions.isEmpty()) {
//                FileWriter fileWriter = new FileWriter("students/" + username + ".csv");
//                PrintWriter printWriter = new PrintWriter(fileWriter);
//
//                for (String question : selectedOptions.keySet()) {
//                    String selectedOption = question;
//                    String selectedRadioButton = selectedOptions.get(question);
//                    printWriter.println(selectedOption + "," + selectedRadioButton);
//                }
//
//                printWriter.close();
//                System.out.println("CSV file updated.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//public class Student {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                StudentWindow studentWindow = new StudentWindow("Osama");
//                studentWindow.setVisible(true);
//            }
//        });
//    }
//}
