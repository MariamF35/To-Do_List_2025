import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SignupFrame extends JFrame {

    public SignupFrame() {
        setTitle("Signup");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        JTextField user = new JTextField();
        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton signupBtn = new JButton("Create Account");
        JButton backBtn = new JButton("Back");

        add(new JLabel("Username:"));
        add(user);
        add(new JLabel("Email:"));
        add(email);
        add(new JLabel("Password:"));
        add(pass);

        JPanel p = new JPanel();
        p.add(signupBtn);
        p.add(backBtn);
        add(p);

        signupBtn.addActionListener(e -> {
            String u = user.getText();
            String em = email.getText();
            String pw = new String(pass.getPassword());

            if (u.isEmpty() || em.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields");
                return;
            }

            // check duplicate
            for (String[] r : FileHandler.readAll()) {
                if (r[0].equals(u)) {
                    JOptionPane.showMessageDialog(this, "Username already exists");
                    return;
                }
            }

            FileHandler.append(u, pw, em);
            JOptionPane.showMessageDialog(this, "Signup successful!");

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
