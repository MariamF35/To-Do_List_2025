import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskFileHandler {

    private static final String TASK_DIR = "tasks";
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void ensureTaskDirExists() {
        File dir = new File(TASK_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private static String getTaskFile(String username) {
        return TASK_DIR + File.separator + username + "_tasks.csv";
    }

    public static void saveTasks(String username, List<Task> tasks) {
        ensureTaskDirExists();
        String file = getTaskFile(username);

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("title,deadline,priority,completed,completedAt,createdAt\n");

            for (Task t : tasks) {
                String title = t.getTitle().replace(",", "|");
                String deadline = t.getDeadline() != null ? t.getDeadline().format(fmt) : "";
                String priority = String.valueOf(t.getPriority());
                String completed = String.valueOf(t.isCompleted());
                String completedAt = t.getCompletedAt() != null ? t.getCompletedAt().format(fmt) : "";
                String createdAt = t.getCreatedAt() != null ? t.getCreatedAt().format(fmt) : "";

                fw.write(title + "," + deadline + "," + priority + "," + completed + "," + completedAt + "," + createdAt + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks(String username) {
        ensureTaskDirExists();
        String file = getTaskFile(username);
        List<Task> tasks = new ArrayList<>();

        File f = new File(file);
        if (!f.exists()) {
            return tasks;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 6) continue;

                    String title = parts[0].replace("|", ",");
                    LocalDateTime deadline = parts[1].isEmpty() ? null : LocalDateTime.parse(parts[1], fmt);
                    int priority = Integer.parseInt(parts[2]);
                    boolean completed = Boolean.parseBoolean(parts[3]);
                    LocalDateTime completedAt = parts[4].isEmpty() ? null : LocalDateTime.parse(parts[4], fmt);
                    LocalDateTime createdAt = parts[5].isEmpty() ? null : LocalDateTime.parse(parts[5], fmt);

                    Task t = new Task(title, deadline, priority);
                    if (completed) {
                        t.complete();
                    }

                    // Restore timestamps via reflection if needed, or create a setter
                    t.setCompletedAt(completedAt);
                    t.setCreatedAt(createdAt);

                    tasks.add(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
