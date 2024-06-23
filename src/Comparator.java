import java.io.*;
import java.util.*;

public class Comparator {
    public String username;
    public int RealCorrectCount;
    Map<String, String> userAnswers = new HashMap<>();


    public Comparator(String username) {
        this.username = username;
        String questionsFile = "students/" + username + ".csv";
        String answerKeyFile = "answerkey.csv";

        Map<String, String> questionAnswers = readCSVFile(questionsFile);
        Map<String, String> answerKey = readCSVFile(answerKeyFile);

        Map<String, String> userAnswers = new HashMap<>();
        userAnswers.put("username", username); // Replace with the actual username

        compareAnswers(questionAnswers, answerKey, userAnswers);
        writeResultToCSV(userAnswers);
    }

    public void checkAnswers(String username) {
        this.username = username;
        String questionsFile = "students/" + username + ".csv";
        String answerKeyFile = "answerkey.csv";

        Map<String, String> questionAnswers = readCSVFile(questionsFile);
        Map<String, String> answerKey = readCSVFile(answerKeyFile);

        Map<String, String> userAnswers = new HashMap<>();
        userAnswers.put("username", username); // Replace with the actual username

        compareAnswers(questionAnswers, answerKey, userAnswers);
    }

    public void checkAnswersAndSave(String username) {
        checkAnswers(username);
        writeResultToCSV(userAnswers);

    }

    public int returnAnswer() {
        System.out.println("return fuction -- start");
        checkAnswers(username);
        System.out.println("return function -- CORRECT ANSWERS: " + RealCorrectCount);
        return RealCorrectCount;

    }
    // public static void main(String[] args) {
    // System.out.println("RUNNING FROM MAIN");
    // Comparator comp = new Comparator("Talha");
    // System.out.println("Object Created -- MAIN");
    // String questionsFile = "students/" + username + ".csv";
    // String answerKeyFile = "answerkey.csv";

    // Map<String, String> questionAnswers = readCSVFile(questionsFile);
    // Map<String, String> answerKey = readCSVFile(answerKeyFile);

    // Map<String, String> userAnswers = new HashMap<>();
    // userAnswers.put("username", username); // Replace with the actual username

    // compareAnswers(questionAnswers, answerKey, userAnswers);
    // // writeResultToCSV(userAnswers);
    // }

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

    private void compareAnswers(Map<String, String> questionAnswers, Map<String, String> answerKey,
            Map<String, String> userAnswers) {
        int correctCount = 0;
        for (String question : questionAnswers.keySet()) {
            String userAnswer = questionAnswers.get(question);
            String correctAnswer = answerKey.get(question);
            System.out.println("DEBUG: " + correctAnswer + " " + userAnswer);
            if (correctAnswer != null) {
                boolean isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
                System.out.println("Check Ran " + correctCount);
                userAnswers.put(question, isCorrect ? "Correct" : "Incorrect");
                if (isCorrect) {
                    correctCount++;
                }
            } else {
                System.out.println("Answer key does not contain a correct answer for question: " + question);
            }
        }
        int totalQuestions = questionAnswers.size();
        RealCorrectCount = correctCount;
        System.out.println("CORRECT ANSWERS: " + correctCount);
        userAnswers.put("Result", correctCount + "/" + totalQuestions);
    }

    private static void writeResultToCSV(Map<String, String> userAnswers) {
        System.out.println("Comparator -- Writing to CSV");
        String resultFile = "result.csv";
        String username = userAnswers.get("username");
        try (PrintWriter writer = new PrintWriter(new FileWriter(resultFile, true))) {
            System.out.println("Comparator -- Filling Data");
            if (!isUsernameExist(resultFile, username)) {
                StringBuilder sb = new StringBuilder();
                sb.append(username);
                for (String question : userAnswers.keySet()) {
                    if (!question.equals("username")) {
                        sb.append(",").append(userAnswers.get(question));
                    }
                }
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isUsernameExist(String resultFile, String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(resultFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
