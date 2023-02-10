package com.tasko.task.domain.service.pomodoro.impl;

import com.tasko.task.domain.service.pomodoro.PomodoroService;
import com.tasko.task.interfaces.web.pomodoro.PomodoroResponseDto;
import com.tasko.task.interfaces.web.pomodoro.PomodoroUpdateRequestDto;
import com.tasko.task.domain.model.pomodoro.Pomodoro;
import com.tasko.task.domain.repository.pomodoro.PomodoroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PomodoroServiceImpl implements PomodoroService {
    private final PomodoroRepository pomodoroRepository;

    @Transactional
    public PomodoroResponseDto fetchPomodoroItem(String userId) {
        Pomodoro pomodoro = pomodoroRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("id not found"));

        return new PomodoroResponseDto(pomodoro);
    }

    @Transactional
    public PomodoroResponseDto updatePomodoroItem(PomodoroUpdateRequestDto requestDto) {
        Pomodoro pomodoro = pomodoroRepository.findById(requestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("id not found"));
        pomodoro.update(requestDto.getTimerSet(),requestDto.getPomo());

        return new PomodoroResponseDto(pomodoro);
    }
}
