package maco.habit_backend.schedulers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.repositories.HabitRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Component
public class DailyLogScheduler {

    private DailyLogRepo dailyLogRepository;

    private HabitRepo habitRepository;

    private static final int BATCH_SIZE = 5; // Define your batch size here

    // Schedule to run every day at 00:00
    @Scheduled(cron = "0 0 0 * * *") // Every day at midnight
    @Transactional // Ensure the operation is within a single transaction
    public void createDailyLogs() {
        LocalDate today = LocalDate.now();

        // Retrieve all habits that require daily logging
        List<Habit> dailyHabits = habitRepository.findAllByOccurrence(Occurrence.DAILY);

        // Prepare to batch save DailyLogs
        List<DailyLog> dailyLogs = new ArrayList<>();

        // Loop through each daily habit and create a new DailyLog
        for (Habit habit : dailyHabits) {
            DailyLog dailyLog = new DailyLog();
            dailyLog.setHabit(habit);
            dailyLog.setDate(today);
            dailyLog.setCompleted(false); // Default initial state

            // Add to the list for batch saving
            dailyLogs.add(dailyLog);

            // If the list reaches the defined batch size, save it
            if (dailyLogs.size() == BATCH_SIZE) {
                dailyLogRepository.saveAll(dailyLogs);
                dailyLogs.clear(); // Clear the list for the next batch
            }
        }

        // Save any remaining logs that didn't fill the last batch
        if (!dailyLogs.isEmpty()) {
            dailyLogRepository.saveAll(dailyLogs);
        }

        System.out.println("Daily logs created for each habit on " + today);
    }
}
