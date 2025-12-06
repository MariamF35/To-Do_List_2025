import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ForgotPasswordFrame extends JFrame {

    public ForgotPasswordFrame() {
        setTitle("Reset Password");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        JTextField email = new JTextField();
        JPasswordField newPass = new JPasswordField();

        JButton resetBtn = new JButton("Reset");
        JButton backBtn = new JButton("Back");

        add(new JLabel("Enter registered email:"));
        add(email);
        add(new JLabel("Enter new password:"));
        add(newPass);

        JPanel p = new JPanel();
        p.add(resetBtn);
        p.add(backBtn);
        add(p);

        resetBtn.addActionListener(e -> {
            String em = email.getText();
            String np = new String(newPass.getPassword());

            boolean exists = false;

            for (String[] r : FileHandler.readAll()) {
                if (r[2].equals(em)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                JOptionPane.showMessageDialog(this, "Email not found!");
                return;
            }

            FileHandler.updatePassword(em, np);

            JOptionPane.showMessageDialog(this, "Password updated!");
            dispose();
            new LoginFrame();
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
