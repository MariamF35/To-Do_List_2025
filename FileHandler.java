import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String FILE = "login.csv";

    public static void ensureFileExists() {
        try {
            File f = new File(FILE);
            if (!f.exists()) {
                FileWriter fw = new FileWriter(f);
                fw.write("username,password,email\n");
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readAll() {
        ensureFileExists();
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static void append(String username, String password, String email) {
        ensureFileExists();
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(username + "," + password + "," + email + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(String email, String newPass) {
        List<String[]> rows = readAll();
        try {
            FileWriter fw = new FileWriter(FILE);
            fw.write("username,password,email\n");

            for (String[] r : rows) {
                if (r[2].equals(email)) {
                    fw.write(r[0] + "," + newPass + "," + r[2] + "\n");
                } else {
                    fw.write(r[0] + "," + r[1] + "," + r[2] + "\n");
                }
            }

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
