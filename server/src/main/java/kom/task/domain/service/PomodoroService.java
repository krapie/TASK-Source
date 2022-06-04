package kom.task.domain.service;

import kom.task.domain.repository.pomodoro.Pomodoro;
import kom.task.domain.repository.pomodoro.PomodoroRepository;
import kom.task.domain.web.dto.pomodoro.PomodoroResponseDto;
import kom.task.domain.web.dto.pomodoro.PomodoroUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PomodoroService {
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
