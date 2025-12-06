import java.util.*;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int points = 0;

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void completeTask(int i) {
        tasks.get(i).complete();
        points += 10;
    }

    public List<Task> getTasksSorted() {
        tasks.sort(Comparator.comparing(Task::getPriority));
        return tasks;
    }

    public double getCompletionPercentage() {
        if (tasks.isEmpty()) return 0;
        long c = tasks.stream().filter(Task::isCompleted).count();
        return (c * 100.0) / tasks.size();
    }

    public int getPoints() { return points; }
}
