import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User {
    private final String username;

    private static Scanner scanner;
    private static final String fileName = "Text Files/Users.txt";
    private static FileWriter fileWriter;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public static boolean checkForUsername(String username) {
        try {
            scanner = new Scanner(new File(fileName)).useDelimiter(",");
        } catch (FileNotFoundException e) {
            System.out.println("User file could not be found");
        }
        while (scanner.hasNext()) {
            if (scanner.next().equals(username)) {
                return true;
            }
        }
        scanner.close();
        return false;
    }

    public static void signUp(String username) {
        try {
            fileWriter = new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(fileName);
        try {
            if (file.length() == 0) {
                fileWriter.write(username);
            } else {
                fileWriter.write("\n" + username);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
