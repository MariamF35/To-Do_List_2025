import javax.swing.*;
import java.awt.*;
import java.awt.RenderingHints;
import java.time.*;
import java.time.format.*;

public class ToDoFrame extends JFrame {
    private TaskManager manager = new TaskManager();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(listModel);
    private String currentUsername;

    public ToDoFrame(String username) {
        this.currentUsername = username;
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
        JButton logoutBtn = new JButton("Logout");

        p.add(addBtn);
        p.add(compBtn);
        p.add(dashBtn);
        p.add(logoutBtn);

        add(p, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addTask());
        compBtn.addActionListener(e -> completeTask());
        dashBtn.addActionListener(e -> showDashboard());
        logoutBtn.addActionListener(e -> {
            saveTasks();
            dispose();
            new LoginFrame();
        });

        // Load tasks from file
        manager.setTasks(TaskFileHandler.loadTasks(username));
        refresh();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private int themeIndex = 0; // 0: Light, 1: Dark, 2: Purple-Cyan

    private void switchTheme() {
        themeIndex = (themeIndex + 1) % 3;
        applyTheme();
    }

    private void applyTheme() {
        Color bg, fg;
        switch (themeIndex) {
            case 0: // Light theme
                bg = Color.WHITE;
                fg = Color.BLACK;
                break;
            case 1: // Dark theme
                bg = Color.BLACK;
                fg = Color.WHITE;
                break;
            case 2: // Purple-Cyan theme
                bg = new Color(128, 0, 128); // Purple
                fg = new Color(0, 255, 255); // Cyan
                break;
            default:
                bg = Color.WHITE;
                fg = Color.BLACK;
        }

        getContentPane().setBackground(bg);
        taskList.setBackground(bg);
        taskList.setForeground(fg);
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog("Task title:");
        if (title == null || title.trim().isEmpty()) return;

        // Ask for deadline
        LocalDateTime deadline = null;
        try {
            String deadlineStr = JOptionPane.showInputDialog("Enter deadline (yyyy-MM-dd HH:mm):", 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            if (deadlineStr == null || deadlineStr.trim().isEmpty()) return;
            deadline = LocalDateTime.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd HH:mm");
            return;
        }

        int p = 3;
        try {
            String pin = JOptionPane.showInputDialog("Priority (1-5):", "3");
            if (pin == null) return;
            p = Integer.parseInt(pin);
            if (p < 1) p = 1;
            if (p > 5) p = 5;
        } catch (Exception ex) {
            p = 3;
        }

        manager.addTask(new Task(title, deadline, p));
        saveTasks();
        refresh();
    }

    private void completeTask() {
        int i = taskList.getSelectedIndex();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Select a task to complete.");
            return;
        }
        manager.completeTask(i);
        saveTasks();
        refresh();
    }

    private void showDashboard() {
        long onTime = manager.getCompletedOnTimeCount();
        long late = manager.getCompletedLateCount();
        long incomplete = manager.getIncompleteCount();
        int points = manager.getPoints();

        // Create pie chart frame
        JFrame chartFrame = new JFrame("Dashboard");
        chartFrame.setSize(500, 400);
        chartFrame.setLocationRelativeTo(this);

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPieChart(g, getWidth(), getHeight(), onTime, late, incomplete);
            }
        };

        chartFrame.add(chartPanel);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("On Time: " + onTime + " | Late: " + late + " | Incomplete: " + incomplete + " | Total Points: " + points));
        chartFrame.add(infoPanel, BorderLayout.SOUTH);

        chartFrame.setVisible(true);
    }

    private void drawPieChart(Graphics g, int width, int height, long onTime, long late, long incomplete) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        long total = onTime + late + incomplete;
        if (total == 0) {
            g2d.drawString("No tasks to display", width / 2 - 50, height / 2);
            return;
        }

        int centerX = width / 2;
        int centerY = height / 2 - 30;
        int radius = Math.min(width, height) / 3;

        // Calculate angles
        double onTimeAngle = (onTime * 360.0) / total;
        double lateAngle = (late * 360.0) / total;
        double incompleteAngle = (incomplete * 360.0) / total;

        // Draw pie slices
        g2d.setColor(new Color(76, 175, 80)); // Green for on-time
        g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 0, (int) onTimeAngle);

        g2d.setColor(new Color(255, 152, 0)); // Orange for late
        g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) onTimeAngle, (int) lateAngle);

        g2d.setColor(new Color(244, 67, 54)); // Red for incomplete
        g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 
            (int) (onTimeAngle + lateAngle), (int) incompleteAngle);

        // Draw legend
        int legendX = centerX - radius - 50;
        int legendY = centerY + radius + 30;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        g2d.setColor(new Color(76, 175, 80));
        g2d.fillRect(legendX, legendY, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("On Time: " + onTime, legendX + 20, legendY + 12);

        g2d.setColor(new Color(255, 152, 0));
        g2d.fillRect(legendX, legendY + 20, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Late: " + late, legendX + 20, legendY + 32);

        g2d.setColor(new Color(244, 67, 54));
        g2d.fillRect(legendX, legendY + 40, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Incomplete: " + incomplete, legendX + 20, legendY + 52);
    }

    private void saveTasks() {
        TaskFileHandler.saveTasks(currentUsername, manager.getTasks());
    }

    private void refresh() {
        listModel.clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Task t : manager.getTasksSorted()) {
            String due = t.getDeadline() != null ? t.getDeadline().format(fmt) : "-";
            String status = t.isCompleted() ? " ✔" : "";
            listModel.addElement(t.getTitle() + " | P:" + t.getPriority() + " | Due:" + due + status);
        }
    }
}
