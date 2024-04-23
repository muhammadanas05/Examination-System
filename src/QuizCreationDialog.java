//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Date;
//
//class QuizCreationDialog extends JDialog {
//    private JTextArea questionArea;
//    private JTextField[] optionFields;
//    private JComboBox<String> topicsComboBox;
//    private JSpinner numQuestionsSpinner;
//    private JSpinner marksSpinner;
//    private JSpinner timerSpinner;
//    private JSpinner dateTimeSpinner;
//    private JSpinner timeLimitSpinner;
//    private JPanel questionPanel;
//    private String courseName;
//
//    public QuizCreationDialog(JFrame parentFrame) {
//        super(parentFrame, "Create Quiz", true);
//        setSize(400, 500);
//        setLocationRelativeTo(parentFrame);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//        add(panel);
//
//        // Topic selection
//        JPanel topicPanel = new JPanel(new GridLayout(4, 2));
//        JLabel topicsLabel = new JLabel("Courses:");
//        String[] topics = {"Database Management System Theory (DBT-2001)",
//                "Database Management System Lab (DBL-2001)",
//                "Object Oriented Programming Theory (OOPT-2002)",
//                "Object Oriented Programming Lab (OOPL-2002)",
//                "Operating System Lab (OSL-2003)",
//                "Operating System Theory(OSL-2003)",
//                "Software Design and Architecture (SDA-2004)"};
//        topicsComboBox = new JComboBox<>(topics);
//        topicPanel.add(topicsLabel);
//        topicPanel.add(topicsComboBox);
//
//        JLabel numQuestionsLabel = new JLabel("No. of Questions:");
//        SpinnerModel numQuestionsModel = new SpinnerNumberModel(1, 1, 40, 1);
//        numQuestionsSpinner = new JSpinner(numQuestionsModel);
//        topicPanel.add(numQuestionsLabel);
//        topicPanel.add(numQuestionsSpinner);
//
//        JLabel marksLabel = new JLabel("Marks per Question:");
//        SpinnerModel marksModel = new SpinnerNumberModel(1, 1, 10, 1);
//        marksSpinner = new JSpinner(marksModel);
//        topicPanel.add(marksLabel);
//        topicPanel.add(marksSpinner);
//
//        JLabel timerLabel = new JLabel("Time Limit(in Min):");
//        SpinnerModel timerModel = new SpinnerNumberModel(1, 1, 10, 1);
//        timerSpinner = new JSpinner(timerModel);
//        topicPanel.add(timerLabel);
//        topicPanel.add(timerSpinner);
//
//        JLabel dateTimeLabel = new JLabel("Quiz Date/Time:");
//        SpinnerModel dateTimeModel = new SpinnerDateModel();
//        dateTimeSpinner = new JSpinner(dateTimeModel);
//        JSpinner.DateEditor dateTimeEditor = new JSpinner.DateEditor(dateTimeSpinner, "dd MMM yyyy HH:mm");
//        dateTimeSpinner.setEditor(dateTimeEditor);
//        topicPanel.add(dateTimeLabel);
//        topicPanel.add(dateTimeSpinner);
//
//        panel.add(topicPanel, BorderLayout.NORTH);
//
//        // Question panel
//        questionPanel = new JPanel(new BorderLayout());
//        JLabel questionLabel = new JLabel("Question:");
//        questionPanel.add(questionLabel, BorderLayout.NORTH);
//
//        JScrollPane questionScrollPane = new JScrollPane();
//        questionArea = new JTextArea();
//        questionScrollPane.setViewportView(questionArea);
//
//        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
//        panel.add(questionPanel, BorderLayout.CENTER);
//
//        // Options
//        optionFields = new JTextField[4];
//        for (int i = 0; i < 4; i++) {
//            optionFields[i] = new JTextField();
//        }
//        JPanel optionsPanel = new JPanel(new GridLayout(1, 4));
//        for (JTextField optionField : optionFields) {
//            optionsPanel.add(optionField);
//        }
//        panel.add(optionsPanel, BorderLayout.SOUTH);
//
//        // Time limit
//        JPanel timeLimitPanel = new JPanel(new GridLayout(1, 2));
//        JLabel timeLimitLabel = new JLabel("Time Limit (in minutes):");
//        SpinnerModel timeLimitModel = new SpinnerNumberModel(1, 1, 60, 1);
//        timeLimitSpinner = new JSpinner(timeLimitModel);
//        timeLimitPanel.add(timeLimitLabel);
//        timeLimitPanel.add(timeLimitSpinner);
//
//        panel.add(timeLimitPanel, BorderLayout.SOUTH);
//
//        topicsComboBox.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                courseName = topicsComboBox.getSelectedItem().toString();
//            }
//        });
//
//        JButton saveButton = new JButton("Save");
//        panel.add(saveButton, BorderLayout.SOUTH);
//
//        saveButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                saveQuiz();
//                dispose();
//            }
//        });
//
//        setVisible(true);
//    }
//
//    private void saveQuiz() {
//        // Get the selected date and time from the dateTimeSpinner
//        Date selectedDate = (Date) dateTimeSpinner.getValue();
//
//        // Get the current date and time
//        Date currentDate = new Date();
//
//        // Compare the selected date and time with the current date and time
//        if (selectedDate.before(currentDate)) {
//            JOptionPane.showMessageDialog(this, "Invalid date and time. Please select a future date and time.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Check if the questionArea is empty
//        String question = questionArea.getText().trim();
//        if (question.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please enter a question.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Get other data
//        int numQuestions = (int) numQuestionsSpinner.getValue();
//        int marksPerQuestion = (int) marksSpinner.getValue();
//
//        try {
//            String filename = courseName + "_quiz.txt";
//            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
//
//            // Save all the data in the file
//            writer.write("Course Name: " + courseName);
//            writer.newLine();
//            writer.write("No. of Questions: " + numQuestions);
//            writer.newLine();
//            writer.write("Marks per Question: " + marksPerQuestion);
//            writer.newLine();
//            writer.write("Date & Time: " + selectedDate);
//            writer.newLine();
//            writer.write("Time Limit(in min): " + selectedTime);
//            writer.newLine();
//            writer.write("Question: ");
//            writer.newLine();
//            writer.write(question);
//            writer.newLine();
//            for (JTextField optionField : optionFields) {
//                writer.write(optionField.getText());
//                writer.newLine();
//            }
//
//            writer.close();
//
//            JOptionPane.showMessageDialog(this, "Quiz saved successfully!");
//        }
//        catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error saving quiz: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JFrame frame = new JFrame("Quiz App");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(800, 600);
//                frame.setLocationRelativeTo(null);
//
//                JButton createQuizButton = new JButton("Create Quiz");
//                createQuizButton.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//                        QuizCreationDialog dialog = new QuizCreationDialog(frame);
//                    }
//                });
//
//                frame.getContentPane().setLayout(new FlowLayout());
//                frame.getContentPane().add(createQuizButton);
//
//                frame.setVisible(true);
//            }
//        });
//    }
//}



//SECOND ATTEMPT

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

class QuizCreationDialog extends JDialog {
    private JTextArea questionArea;
    private JTextField[] optionFields;
    private JComboBox<String> topicsComboBox;
    private JSpinner numQuestionsSpinner;
    private JSpinner marksSpinner;
    private JSpinner timerSpinner;
    private JSpinner dateTimeSpinner;
    private JSpinner timeLimitSpinner;
    private JPanel questionPanel;
    private String courseName;
    public static String USERNAME;

    public QuizCreationDialog(JFrame parentFrame) {
        super(parentFrame, "Create Quiz", true);
        setSize(400, 500);
        setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        // Topic selection
        JPanel topicPanel = new JPanel(new GridLayout(5, 2));
        JLabel topicsLabel = new JLabel("Courses:");
        String[] topics = {"Database Management System Theory (DBT-2001)",
                "Database Management System Lab (DBL-2001)",
                "Object Oriented Programming Theory (OOPT-2002)",
                "Object Oriented Programming Lab (OOPL-2002)",
                "Operating System Lab (OSL-2003)",
                "Operating System Theory(OSL-2003)",
                "Software Design and Architecture (SDA-2004)"};
        topicsComboBox = new JComboBox<>(topics);
        topicPanel.add(topicsLabel);
        topicPanel.add(topicsComboBox);

        JLabel numQuestionsLabel = new JLabel("No. of Questions:");
        SpinnerModel numQuestionsModel = new SpinnerNumberModel(1, 1, 40, 1);
        numQuestionsSpinner = new JSpinner(numQuestionsModel);
        topicPanel.add(numQuestionsLabel);
        topicPanel.add(numQuestionsSpinner);

        JLabel marksLabel = new JLabel("Marks per Question:");
        SpinnerModel marksModel = new SpinnerNumberModel(1, 1, 10, 1);
        marksSpinner = new JSpinner(marksModel);
        topicPanel.add(marksLabel);
        topicPanel.add(marksSpinner);

        JLabel timerLabel = new JLabel("Time Limit (in Min):");
        SpinnerModel timerModel = new SpinnerNumberModel(1, 1, 10, 1);
        timerSpinner = new JSpinner(timerModel);
        topicPanel.add(timerLabel);
        topicPanel.add(timerSpinner);

        JLabel dateTimeLabel = new JLabel("Quiz Date/Time:");
        SpinnerModel dateTimeModel = new SpinnerDateModel();
        dateTimeSpinner = new JSpinner(dateTimeModel);
        JSpinner.DateEditor dateTimeEditor = new JSpinner.DateEditor(dateTimeSpinner, "dd MMM yyyy HH:mm");
        dateTimeSpinner.setEditor(dateTimeEditor);
        topicPanel.add(dateTimeLabel);
        topicPanel.add(dateTimeSpinner);

        panel.add(topicPanel, BorderLayout.NORTH);

        // Question panel
        questionPanel = new JPanel(new BorderLayout());
        JLabel questionLabel = new JLabel("Question:");
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        JScrollPane questionScrollPane = new JScrollPane();
        questionArea = new JTextArea();
        questionScrollPane.setViewportView(questionArea);

        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
        panel.add(questionPanel, BorderLayout.CENTER);

        // Options
        optionFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            optionFields[i] = new JTextField();
        }
        JPanel optionsPanel = new JPanel(new GridLayout(1, 4));
        for (JTextField optionField : optionFields) {
            optionsPanel.add(optionField);
        }
        panel.add(optionsPanel, BorderLayout.SOUTH);

        // Save button
        JButton saveButton = new JButton("Save");
        panel.add(saveButton, BorderLayout.SOUTH);

        topicsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                courseName = topicsComboBox.getSelectedItem().toString();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveQuiz();
                dispose();
            }
        });

        setVisible(true);
    }

    private void saveQuiz() {
        // Get the selected date and time from the dateTimeSpinner
        Date selectedDate = (Date) dateTimeSpinner.getValue();

        // Get the current date and time
        Date currentDate = new Date();

        // Compare the selected date and time with the current date and time
        if (selectedDate.before(currentDate)) {
            JOptionPane.showMessageDialog(this, "Invalid date and time. Please select a future date and time.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the questionArea is empty
        String question = questionArea.getText().trim();
        if (question.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a question.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get other data
        int numQuestions = (int) numQuestionsSpinner.getValue();
        int marksPerQuestion = (int) marksSpinner.getValue();
        int selectedTime = (int) timerSpinner.getValue();

        try {
            String filename = courseName + "_quiz.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Save all the data in the file
            writer.write("Teacher Name: " + USERNAME);
            writer.newLine();
            writer.write("Course Name: " + courseName);
            writer.newLine();
            writer.write("No. of Questions: " + numQuestions);
            writer.newLine();
            writer.write("Marks per Question: " + marksPerQuestion);
            writer.newLine();
            writer.write("Date & Time: " + selectedDate);
            writer.newLine();
            writer.write("Time Limit: " + selectedTime + " Minutes");
            writer.newLine();
            writer.write("Question: ");
            writer.newLine();
            writer.write(question);
            writer.newLine();
            QuizWindow.TIME = selectedTime;
            for (JTextField optionField : optionFields) {
                writer.write(optionField.getText());
                writer.newLine();
            }

            writer.close();

            JOptionPane.showMessageDialog(this, "Quiz saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving quiz: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Quiz App");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);

                JButton createQuizButton = new JButton("Create Quiz");
                createQuizButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        QuizCreationDialog dialog = new QuizCreationDialog(frame);
                    }
                });

                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(createQuizButton);

                frame.setVisible(true);
            }
        });
    }
}
