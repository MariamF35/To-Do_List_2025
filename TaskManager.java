import java.util.*;
import java.time.LocalDateTime;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void completeTask(int i) {
        tasks.get(i).complete();
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

    public int getPoints() {
        int total = 0;
        for (Task t : tasks) {
            total += t.calculatePoints();
        }
        return total;
    }

    public long getCompletedOnTimeCount() {
        return tasks.stream().filter(t -> t.isCompleted() && t.isCompletedOnTime()).count();
    }

    public long getCompletedLateCount() {
        return tasks.stream().filter(t -> t.isCompleted() && !t.isCompletedOnTime()).count();
    }

    public long getIncompleteCount() {
        return tasks.stream().filter(t -> !t.isCompleted()).count();
    }

    public void setTasks(List<Task> taskList) {
        this.tasks = taskList;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
