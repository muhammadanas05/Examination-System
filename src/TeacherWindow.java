import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

class TeacherWindow extends JFrame {
    private JButton studentDetailButton;
    private JButton createQuizButton;
    private JButton createAnswerKeyButton;
    private JButton resultButton;
    private JButton showAttendanceButton;

    public TeacherWindow(String username) {
        setTitle("Teacher Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        // Customize button styles
        studentDetailButton = createButton("Student Detail");
        createQuizButton = createButton("Create Quiz");
        createAnswerKeyButton = createButton("Create Answer Key");
        resultButton = createButton("Result");
        showAttendanceButton = createButton("Show Attendance");

        buttonPanel.add(studentDetailButton);
        buttonPanel.add(createQuizButton);
        buttonPanel.add(createAnswerKeyButton);
        buttonPanel.add(resultButton);
        buttonPanel.add(showAttendanceButton);

        add(buttonPanel, BorderLayout.CENTER);

        studentDetailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStudentDetails();
            }
        });

        createQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createQuiz();
            }
        });

        createAnswerKeyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAnswerKey();
            }
        });

        resultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new Comparator("Anas");
                new ComparatorGUI();
            }
        });

        showAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAttendance();
            }
        });

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.DARK_GRAY);
        add(welcomeLabel, BorderLayout.NORTH);

        QuizCreationDialog.USERNAME = username;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private void showStudentDetails() {
        try {
            String filename = "students.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            StringBuilder students = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                students.append(username).append("\n");
            }
            reader.close();
            JOptionPane.showMessageDialog(this, students.toString(), "Student Details",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createQuiz() {
        QuizCreationDialog quizDialog = new QuizCreationDialog(this);
        quizDialog.setVisible(true);
    }

    private void createAnswerKey() {
        AnswerKeyCreationDialog answerKeyDialog = new AnswerKeyCreationDialog(this);
        answerKeyDialog.setVisible(true);
    }

    // private void showResult() {
    // try {
    // String filename = "responses.txt";
    // BufferedReader reader = new BufferedReader(new FileReader(filename));
    // String line;
    // StringBuilder result = new StringBuilder();
    // while ((line = reader.readLine()) != null) {
    // String[] parts = line.split(",");
    // String username = parts[0];
    // String response = parts[1];
    // result.append("Username: ").append(username).append("\nResponse:
    // ").append(response).append("\n\n");
    // }
    // reader.close();
    // JOptionPane.showMessageDialog(this, result.toString(), "Result",
    // JOptionPane.INFORMATION_MESSAGE);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    private void showAttendance() {
        try {
            String studentsFile = "students.txt";
            String resultsFile = "result.csv";

            // Read student usernames from students.txt
            Set<String> students = readStudentUsernames(studentsFile);

            // Read attempted students from result.csv
            Set<String> attemptedStudents = readAttemptedStudents(resultsFile);

            // Create attendance information
            StringBuilder attendance = new StringBuilder();
            for (String student : students) {
                String fontColor = attemptedStudents.contains(student) ? "green" : "red";
                attendance.append("<font color='" + fontColor + "'>" + student + "</font><br>");
            }

            JLabel attendanceLabel = new JLabel("<html><body>" + attendance.toString() + "</body></html>");
            attendanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JOptionPane.showMessageDialog(this, attendanceLabel, "Attendance", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> readStudentUsernames(String filename) throws IOException {
        Set<String> usernames = new HashSet<>();

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String username = parts[0];
            usernames.add(username);
        }
        reader.close();

        return usernames;
    }

    private Set<String> readAttemptedStudents(String filename) throws IOException {
        Set<String> attemptedStudents = new HashSet<>();

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) {
                String[] parts = trimmedLine.split(",");
                if (parts.length > 0 && !parts[0].isEmpty()) {
                    String username = parts[0].trim();
                    // Add additional logic here if required to determine quiz attempt status
                    attemptedStudents.add(username);
                }
            }
        }
        reader.close();

        return attemptedStudents;
    }
}
