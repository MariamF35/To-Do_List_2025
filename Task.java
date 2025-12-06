import java.time.LocalDateTime;

public class Task {
    private String title;
    private LocalDateTime deadline;
    private int priority;
    private boolean completed;

    public Task(String title, LocalDateTime deadline, int priority) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
    }

    public String getTitle() { return title; }
    public LocalDateTime getDeadline() { return deadline; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public void complete() { completed = true; }
}
