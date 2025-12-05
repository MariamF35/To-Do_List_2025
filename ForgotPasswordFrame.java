import javax.swing.*;
import java.awt.*;

public class ForgotPasswordFrame extends JFrame {

    JTextField usernameField;
    JPasswordField newPasswordField;

    public ForgotPasswordFrame() {
        setTitle("Reset Password");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        add(newPasswordField);

        JButton resetBtn = new JButton("Reset");
        add(resetBtn);

        resetBtn.addActionListener(e -> reset());

        setVisible(true);
    }

    void reset() {
        String user = usernameField.getText();
        String pass = new String(newPasswordField.getPassword());

        if (FileHandler.resetPassword(user, pass)) {
            JOptionPane.showMessageDialog(this, "Password Reset!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "User not found!");
        }
    }
}
