import java.util.*;
import java.time.LocalDateTime;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();
    private int points = 0;

    public void addTask(Task t) { tasks.add(t); }

    public void completeTask(int index) {
        Task t = tasks.get(index);
        t.markCompleted();

        if (t.isOnTime()) points += 5;
        else points += 2;
    }

    public int getPoints() { return points; }

    public List<Task> getTasksSorted() {
        tasks.sort(Comparator
            .comparing(Task::isCompleted)
            .thenComparing(Task::getPriority)
            .thenComparing(Task::getDeadline));
        return tasks;
    }

    public double getCompletionPercentage() {
        if (tasks.size() == 0) return 0;
        long completed = tasks.stream().filter(Task::isCompleted).count();
        return (completed * 100.0) / tasks.size();
    }
}
