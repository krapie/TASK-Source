package com.tasko.task.domain.service.pomodoro;

import com.tasko.task.interfaces.web.pomodoro.PomodoroResponseDto;
import com.tasko.task.interfaces.web.pomodoro.PomodoroUpdateRequestDto;

public interface PomodoroService {

    PomodoroResponseDto fetchPomodoroItem(String userId);
    PomodoroResponseDto updatePomodoroItem(PomodoroUpdateRequestDto pomodoro);
}

