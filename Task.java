import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Task {
    private String title;
    private LocalDateTime deadline;
    private int priority;
    private boolean completed;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;

    public Task(String title, LocalDateTime deadline, int priority) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.completedAt = null;
    }

    public String getTitle() { return title; }
    public LocalDateTime getDeadline() { return deadline; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void complete() {
        completed = true;
        completedAt = LocalDateTime.now();
    }

    public void setCompletedAt(LocalDateTime dt) {
        this.completedAt = dt;
    }

    public void setCreatedAt(LocalDateTime dt) {
        this.createdAt = dt;
    }

    public boolean isCompletedOnTime() {
        if (!completed || completedAt == null || deadline == null) return false;
        return !completedAt.isAfter(deadline);
    }

    public long getDaysLate() {
        if (!completed || completedAt == null || deadline == null) return 0;
        if (isCompletedOnTime()) return 0;
        return ChronoUnit.DAYS.between(deadline, completedAt);
    }

    public int calculatePoints() {
        if (!completed) return 0;
        int basePoints = 10;
        long daysLate = getDaysLate();
        return basePoints - (int) daysLate;
    }
}
