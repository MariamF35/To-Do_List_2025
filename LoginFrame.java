import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login / Register");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        add(userLabel);
        add(userField);
        add(loginBtn);
        add(registerBtn);

        // LOGIN BUTTON
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a username.");
                return;
            }

            // Read all users from CSV file
            List<String[]> rows = FileHandler.readAll();

            boolean exists = false;
            for (String[] row : rows) {
                if (row[0].equals(username)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new ToDoFrame(username);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User not found!");
            }
        });

        // REGISTER BUTTON
        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a username.");
                return;
            }

            List<String[]> rows = FileHandler.readAll();

            boolean exists = false;
            for (String[] row : rows) {
                if (row[0].equals(username)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                JOptionPane.showMessageDialog(this, "User already exists!");
            } else {
                FileHandler.append(username, "0", "0");
                JOptionPane.showMessageDialog(this, "Registration successful!");
                new ToDoFrame(username);
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
