import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class ToDoFrame extends JFrame {
    private TaskManager manager = new TaskManager();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(listModel);
    private boolean dark = false;

    public ToDoFrame(String username) {
        setTitle("To-Do List - " + username);
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton toggleTheme = new JButton("Toggle Theme");
        toggleTheme.addActionListener(e -> switchTheme());
        add(toggleTheme, BorderLayout.NORTH);

        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add Task");
        JButton completeBtn = new JButton("Mark Completed");
        JButton dashBtn = new JButton("Show Dashboard");

        addBtn.addActionListener(e -> addTask());
        completeBtn.addActionListener(e -> completeTask());
        dashBtn.addActionListener(e -> showDashboard());

        panel.add(addBtn);
        panel.add(completeBtn);
        panel.add(dashBtn);

        add(panel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

        new ReminderService(manager);
    }

    private void switchTheme() {
        dark = !dark;
        Color bg = dark ? Color.BLACK : Color.WHITE;
        Color fg = dark ? Color.WHITE : Color.BLACK;

        getContentPane().setBackground(bg);
        taskList.setBackground(bg);
        taskList.setForeground(fg);
    }

    private LocalDateTime parseDate(String input) {
        String[] patterns = {
            "dd-MM-yyyy HH:mm",
            "dd-MM-yyyy",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "dd/MM/yyyy HH:mm",
            "dd MMM yyyy",
            "dd MMM yyyy HH:mm"
        };

        for (String pattern : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
                if (pattern.endsWith("HH:mm"))
                    return LocalDateTime.parse(input, f);
                else
                    return LocalDate.parse(input, f).atTime(23, 59);
            } catch (Exception ignored) {}
        }

        return null;
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog(this, "Task name:");
        if (title == null || title.isEmpty()) return;

        String deadlineStr = JOptionPane.showInputDialog(this, "Enter deadline:");
        LocalDateTime dl = parseDate(deadlineStr);

        if (dl == null) {
            JOptionPane.showMessageDialog(this, "Invalid date!");
            return;
        }

        String priorityStr = JOptionPane.showInputDialog(this, "Priority (1-5):");
        int pr = Integer.parseInt(priorityStr);

        Task t = new Task(title, dl, pr);
        manager.addTask(t);
        refreshList();
    }

    private void completeTask() {
        int idx = taskList.getSelectedIndex();
        if (idx == -1) return;

        manager.completeTask(idx);
        refreshList();
    }

    private void showDashboard() {
        double percent = manager.getCompletionPercentage();
        JOptionPane.showMessageDialog(this,
            "Completed: " + percent + "%\n" +
            "Points: " + manager.getPoints());
    }

    private void refreshList() {
        listModel.clear();
        for (Task t : manager.getTasksSorted()) {
            listModel.addElement(
                t.getTitle() + " | P:" + t.getPriority() + " | Due:" + t.getDeadline() +
                (t.isCompleted() ? " ✔" : "")
            );
        }
    }
}
