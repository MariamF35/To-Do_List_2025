import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");
        JButton forgotBtn = new JButton("Forgot Password");

        add(loginBtn);
        add(signupBtn);
        add(forgotBtn);

        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> new SignupFrame());
        forgotBtn.addActionListener(e -> new ForgotPasswordFrame());

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void login() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (FileHandler.validateLogin(user, pass)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            new ToDoFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid login!");
        }
    }
}
