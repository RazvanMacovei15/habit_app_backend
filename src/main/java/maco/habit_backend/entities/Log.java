package maco.habit_backend.entities;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
public interface Log {
    int getId();
    Habit getHabit();
    int getCurrentCount();
    void setCurrentCount(int currentCount);
    boolean isCompleted();
    void setCompleted(boolean completed);
    boolean isPreviousCompleted();
    void setPreviousCompleted(boolean previousCompleted);
    User getUser();

}
