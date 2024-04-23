import java.io.*;
import java.util.*;

public class Comparator {
    public static String username;

    public Comparator(String username) {
        Comparator.username = username;
        String questionsFile = "students/" + username + ".csv";
        String answerKeyFile = "answerkey.csv";

        Map<String, String> questionAnswers = readCSVFile(questionsFile);
        Map<String, String> answerKey = readCSVFile(answerKeyFile);

        Map<String, String> userAnswers = new HashMap<>();
        userAnswers.put("username", username); // Replace with the actual username

        compareAnswers(questionAnswers, answerKey, userAnswers);
        writeResultToCSV(userAnswers);
    }

//    public static void main(String[] args) {
//        Comparator comp = new Comparator("ANAS");

        // String questionsFile = "students/" + username + ".csv";
        // String answerKeyFile = "answerkey.csv";

        // Map<String, String> questionAnswers = readCSVFile(questionsFile);
        // Map<String, String> answerKey = readCSVFile(answerKeyFile);

        // Map<String, String> userAnswers = new HashMap<>();
        // userAnswers.put("username", username); // Replace with the actual username

        // compareAnswers(questionAnswers, answerKey, userAnswers);
        // writeResultToCSV(userAnswers);
//    }

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

    private static void writeResultToCSV(Map<String, String> userAnswers) {
        String resultFile = "result.csv";
        String username = userAnswers.get("username");
        try (PrintWriter writer = new PrintWriter(new FileWriter(resultFile, true))) {
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
