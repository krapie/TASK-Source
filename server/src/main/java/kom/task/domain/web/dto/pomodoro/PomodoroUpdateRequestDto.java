package kom.task.repository.web.dto.pomodoro;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PomodoroUpdateRequestDto {
    private String userId;
    private Integer timerSet;
    private Integer pomo;

    PomodoroUpdateRequestDto(String userId, Integer timerSet, Integer pomo) {
        this.userId = userId;
        this.timerSet = timerSet;
        this.pomo = pomo;
    }
}
