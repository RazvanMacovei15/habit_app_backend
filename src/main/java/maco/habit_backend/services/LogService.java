package maco.habit_backend.services;

import maco.habit_backend.entities.Log;

public interface LogService {
    Log addUpdate(Log log);
    Log decrementUpdate(Log log);
}