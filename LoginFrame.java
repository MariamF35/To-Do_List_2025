import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login / Register");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Sign Up");
        JButton forgotBtn = new JButton("Forgot Password");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginBtn);
        JPanel bottom = new JPanel();
        bottom.add(registerBtn);
        bottom.add(forgotBtn);
        add(bottom);

        // LOGIN BUTTON
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter username and password.");
                return;
            }

            // Read all users from CSV file
            List<String[]> rows = FileHandler.readAll();

            boolean found = false;
            boolean passMatch = false;
            for (String[] row : rows) {
                if (row.length >= 2 && row[0].equals(username)) {
                    found = true;
                    if (row[1].equals(password)) passMatch = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "User not found!");
                return;
            }

            if (!passMatch) {
                JOptionPane.showMessageDialog(this, "Incorrect password!");
                return;
            }

            JOptionPane.showMessageDialog(this, "Login successful!");
            new ToDoFrame(username);
            dispose();
        });

        // REGISTER BUTTON opens SignupFrame
        registerBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });

        // FORGOT PASSWORD
        forgotBtn.addActionListener(e -> {
            dispose();
            new ForgotPasswordFrame();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
