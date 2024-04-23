//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
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
//        setSize(500, 400);
//        setLocationRelativeTo(null);
//        setLayout(new FlowLayout());
//        this.username = username;
//
//        JLabel subjectLabel = new JLabel("Select Subject:");
//        subjectComboBox = new JComboBox<>(new String[] {  "Database Management System Theory (DBT-2001)",
//                "Database Management System Lab (DBL-2001)",
//                "Object Oriented Programming Theory (OOPT-2002)",
//                "Object Oriented Programming Lab (OOPL-2002)",
//                "Operating System Lab (OSL-2003)",
//                "Operating System Theory(OSL-2003)",
//                "Software Design and Architecture (SDA-2004)" });
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
//                        } else if (i == 11) {
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

//SECOND ATTEMPT WITH NEW DESIGN
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
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
//        setSize(500, 400);
//        setLocationRelativeTo(null);
//        setLayout(new FlowLayout());
//        this.username = username;
//
//        JLabel subjectLabel = new JLabel("Select Subject:");
//        subjectLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size and style
//
//        subjectComboBox = new JComboBox<>(new String[]{
//                "Database Management System Theory (DBT-2001)",
//                "Database Management System Lab (DBL-2001)",
//                "Object Oriented Programming Theory (OOPT-2002)",
//                "Object Oriented Programming Lab (OOPL-2002)",
//                "Operating System Lab (OSL-2003)",
//                "Operating System Theory(OSL-2003)",
//                "Software Design and Architecture (SDA-2004)"
//        });
//        subjectComboBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size
//
//        submitButton = new JButton("Submit");
//        submitButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font size and style
//        submitButton.setForeground(Color.WHITE); // Set font color
//        submitButton.setBackground(new Color(52, 152, 219)); // Set button background color
//        submitButton.setFocusPainted(false); // Remove focus border
//
//        add(subjectLabel);
//        add(subjectComboBox);
//        add(submitButton);
//
//        submitButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if (checkExistingCSVFile(username)) {
//                    JOptionPane.showMessageDialog(StudentWindow.this, username + " Has Already Submitted The Quiz.",
//                            "Error", JOptionPane.ERROR_MESSAGE);
//
//                    displayResultButton = new JButton("Display Result");
//                    displayResultButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font size and style
//                    displayResultButton.setForeground(Color.WHITE); // Set font color
//                    displayResultButton.setBackground(new Color(52, 152, 219)); // Set button background color
//                    displayResultButton.setFocusPainted(false); // Remove focus border
//
//                    displayResultButton.addActionListener(new ActionListener() {
//                        public void actionPerformed(ActionEvent e) {
//                            displayResult(username);
//                        }
//                    });
//
//                    add(displayResultButton);
//                    remove(submitButton);
//                    pack();
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
//            boolean found = false;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 2 && parts[0].equals(targetUsername)) {
//                    found = true;
//                    for (int i = 0; i < parts.length; i++) {
//                        Object[] rowData = new Object[2];
//                        if (i == 0) {
//                            rowData[0] = "Username";
//                        } else if (i == 11) {
//                            rowData[0] = "Result";
//                        } else {
//                            rowData[0] = "Q" + (i);
//                        }
//                        rowData[1] = parts[i];
//                        model.addRow(rowData);
//                    }
//                    break;
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
//        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100);
//        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);
//        int rowHeight = 50;
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


//Third ATTEMPT

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

class StudentWindow extends JFrame {
    private JComboBox<String> subjectComboBox;
    private JButton submitButton;
    private String username;
    private Timer timer;
    private int remainingTime;

    public StudentWindow(String username) {
        setTitle("Student Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        this.username = username;

        JLabel subjectLabel = new JLabel("Select Course:");
        subjectLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size and style

        subjectComboBox = new JComboBox<String>(new String[]{
                "Database Management System Theory (DBT-2001)",
                "Database Management System Lab (DBL-2001)",
                "Object Oriented Programming Theory (OOPT-2002)",
                "Object Oriented Programming Lab (OOPL-2002)",
                "Operating System Lab (OSL-2003)",
                "Operating System Theory(OSL-2003)",
                "Software Design and Architecture (SDA-2004)"
        });
        subjectComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setFocusPainted(false);

        add(subjectLabel);
        add(subjectComboBox);
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openStartQuizWindow();
            }
        });
    }

    private void openStartQuizWindow() {
        JFrame startQuizFrame = new JFrame("START QUIZ WINDOW");
        startQuizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startQuizFrame.setSize(400, 250);
        startQuizFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("WELCOME " + username);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startQuizFrame.add(welcomeLabel, BorderLayout.NORTH);

        StringBuilder instructionsText = new StringBuilder();
        String fileName = "Database Management System Theory (DBT-2001)_quiz.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null && lineCount < 6) {
                instructionsText.append(line).append("<br>");
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel fileInstructionsLabel = new JLabel("<html>" + instructionsText.toString() + "</html>");
        fileInstructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startQuizFrame.add(fileInstructionsLabel, BorderLayout.CENTER);

        JButton startQuizButton = new JButton("START QUIZ");
        startQuizButton.setPreferredSize(new Dimension(120, 30));
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkExistingCSVFile(username)) {
                    int option = JOptionPane.showConfirmDialog(StudentWindow.this,
                            "You have already attempted the quiz. Do you want to preview your result?",
                            "Quiz Attempted", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        displayResult(username);
                    }
                } else {
                    startQuizFrame.dispose(); // Close the start quiz window
                    openQuizWindow();
                }
            }
        });
        startQuizFrame.add(startQuizButton, BorderLayout.SOUTH);

        startQuizFrame.setVisible(true);
//        startQuizTimer();
    }

//    private void startQuizTimer() {
//        remainingTime = 600; // 10 minutes in seconds
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                if (remainingTime > 0) {
//                    updateTimerLabel();
//                    remainingTime--;
//                } else {
//                    timer.cancel();
//                    timerExpiredPrompt();
//                }
//            }
//        }, 0, 1000); // Update timer every 1 second
//    }

//    private void updateTimerLabel() {
//        setTitle("Student Window - Time Remaining: " + remainingTime + " seconds");
//    }
//
//    private void timerExpiredPrompt() {
//        JOptionPane.showMessageDialog(StudentWindow.this, "Time's up! Your quiz has ended.", "Quiz Ended",
//                JOptionPane.INFORMATION_MESSAGE);
//    }

    private void openQuizWindow() {
        String subject = (String) subjectComboBox.getSelectedItem();
        QuizWindow quizWindow = new QuizWindow(subject, this.username);
        quizWindow.setVisible(true);
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
                            rowData[0] = "Q" + (i);
                        }
                        rowData[1] = parts[i];
                        model.addRow(rowData);
                    }
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(StudentWindow.this, "No result found for " + targetUsername, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set column widths
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        int rowHeight = 50;
        resultTable.setRowHeight(rowHeight);

        // Set font for table data
        Font font = new Font("Arial", Font.BOLD, 16);
        resultTable.setFont(font);

        resultFrame.setVisible(true);
    }

    private boolean checkExistingCSVFile(String username) {
        Path path = Paths.get("students/" + username + ".csv");
        return Files.exists(path);
    }
}
