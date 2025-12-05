import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String FILE_NAME = "login.csv";

    public static boolean validateLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    if (data[0].equals(username) && data[1].equals(password)) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading login.csv: " + e.getMessage());
        }
        return false;
    }

    public static boolean checkUserExists(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 1 && data[0].equals(username)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error checking user: " + e.getMessage());
        }
        return false;
    }

    public static void signup(String username, String password) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(username + "," + password + "\n");
        } catch (Exception e) {
            System.out.println("Error writing login.csv: " + e.getMessage());
        }
    }

    public static boolean resetPassword(String username, String newPassword) {
        try {
            List<String> lines = new ArrayList<>();
            boolean updated = false;

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    lines.add(username + "," + newPassword);
                    updated = true;
                } else {
                    lines.add(line);
                }
            }
            br.close();

            FileWriter fw = new FileWriter(FILE_NAME);
            for (String l : lines) fw.write(l + "\n");
            fw.close();

            return updated;

        } catch (Exception e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
        return false;
    }
}
