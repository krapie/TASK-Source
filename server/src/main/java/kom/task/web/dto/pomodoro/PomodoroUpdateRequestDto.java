package kom.task.web.dto.pomodoro;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PomodoroUpdateRequestDto {
    private String tokenId;
    private Integer timerSet;
    private Integer pomo;

    PomodoroUpdateRequestDto(String tokenId, Integer timerSet, Integer pomo) {
        this.tokenId = tokenId;
        this.timerSet = timerSet;
        this.pomo = pomo;
    }
}
