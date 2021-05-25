package kom.task.web.dto.pomodoro;

import kom.task.domain.pomodoro.Pomodoro;
import lombok.Getter;

@Getter
public class PomodoroResponseDto {
    private Integer timerSet;
    private Integer pomo;
    private Integer maxPomo;
    private Integer totalPomo;

    public PomodoroResponseDto(Pomodoro entity) {
        this.timerSet = entity.getTimerSet();
        this.pomo = entity.getPomo();
        this.maxPomo = entity.getMaxPomo();
        this.totalPomo = entity.getTotalPomo();
    }
}
