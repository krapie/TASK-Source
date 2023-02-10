package com.tasko.task.domain.repository.pomodoro;

import com.tasko.task.domain.model.pomodoro.Pomodoro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PomodoroRepository extends JpaRepository<Pomodoro, String> {
    Optional<Pomodoro> findByUserId(String userId);
}
