import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;

public class ToDoFrame extends JFrame {
    private TaskManager manager = new TaskManager();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(listModel);

    public ToDoFrame(String username) {
        setTitle("To-Do List - " + username);
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton toggleTheme = new JButton("Theme");
        toggleTheme.addActionListener(e -> switchTheme());
        add(toggleTheme, BorderLayout.NORTH);

        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel p = new JPanel();
        JButton addBtn = new JButton("Add Task");
        JButton compBtn = new JButton("Complete");
        JButton dashBtn = new JButton("Dashboard");

        p.add(addBtn);
        p.add(compBtn);
        p.add(dashBtn);

        add(p, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addTask());
        compBtn.addActionListener(e -> completeTask());
        dashBtn.addActionListener(e -> showDashboard());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean dark = false;

    private void switchTheme() {
        dark = !dark;
        Color bg = dark ? Color.BLACK : Color.WHITE;
        Color fg = dark ? Color.WHITE : Color.BLACK;

        getContentPane().setBackground(bg);
        taskList.setBackground(bg);
        taskList.setForeground(fg);
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog("Task title:");
        String deadline = JOptionPane.showInputDialog("Enter date (yyyy-MM-dd HH:mm):");

        LocalDateTime d = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        int p = Integer.parseInt(JOptionPane.showInputDialog("Priority (1-5):"));

        manager.addTask(new Task(title, d, p));
        refresh();
    }

    private void completeTask() {
        int i = taskList.getSelectedIndex();
        if (i == -1) return;
        manager.completeTask(i);
        refresh();
    }

    private void showDashboard() {
        JOptionPane.showMessageDialog(this, "Completed: " +
                manager.getCompletionPercentage() + "%\nPoints: " + manager.getPoints());
    }

    private void refresh() {
        listModel.clear();
        for (Task t : manager.getTasksSorted()) {
            listModel.addElement(t.getTitle() + " | P:" + t.getPriority() + " | Due:" + t.getDeadline() +
                    (t.isCompleted() ? " ✔" : ""));
        }
    }
}
