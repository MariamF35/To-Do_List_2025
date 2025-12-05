import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public SignupFrame() {
        setTitle("Signup");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("New Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("New Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton signupBtn = new JButton("Create Account");
        add(signupBtn);

        signupBtn.addActionListener(e -> signup());

        setVisible(true);
    }

    void signup() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (FileHandler.checkUserExists(user)) {
            JOptionPane.showMessageDialog(this, "User already exists!");
            return;
        }

        FileHandler.signup(user, pass);
        JOptionPane.showMessageDialog(this, "Account created!");
        dispose();
    }
}
