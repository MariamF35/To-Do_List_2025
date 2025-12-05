import javax.swing.JOptionPane;
import java.time.*;
import java.util.*;

public class ReminderService {
    private Timer timer;

    public ReminderService(TaskManager manager) {
        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                for (Task t : manager.getTasksSorted()) {
                    if (!t.isCompleted()) {
                        long minutesLeft = Duration.between(LocalDateTime.now(), t.getDeadline()).toMinutes();
                        if (minutesLeft == 10) {
                            JOptionPane.showMessageDialog(null,
                                "Reminder: \"" + t.getTitle() + "\" is due in 10 minutes!");
                        }
                    }
                }
            }
        }, 0, 60000);
    }
}
