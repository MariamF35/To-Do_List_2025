import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String FILE = "Login.csv";

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
            String line = br.readLine(); // possible header or first data line

            if (line == null) return rows;

            boolean hasHeader = line.toLowerCase().contains("username") && line.toLowerCase().contains("password");

            if (!hasHeader) {
                rows.add(line.split(","));
            }

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
                String user = r.length > 0 ? r[0] : "";
                String pass = r.length > 1 ? r[1] : "";
                String em = r.length > 2 ? r[2] : "";

                if (em.equals(email)) {
                    fw.write(user + "," + newPass + "," + em + "\n");
                } else {
                    fw.write(user + "," + pass + "," + em + "\n");
                }
            }

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
