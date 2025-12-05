import java.time.LocalDateTime;

public class Task {
    private String title;
    private LocalDateTime deadline;
    private int priority; // 1 = high
    private boolean completed;
    private boolean onTime;

    public Task(String title, LocalDateTime deadline, int priority) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
        this.onTime = false;
    }

    public String getTitle() { return title; }
    public LocalDateTime getDeadline() { return deadline; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public boolean isOnTime() { return onTime; }

    public void markCompleted() {
        this.completed = true;
        this.onTime = LocalDateTime.now().isBefore(deadline);
    }
}
