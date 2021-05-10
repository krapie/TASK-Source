package kom.task.domain.pomodoro;

import lombok.Getter;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class Pomodoro {
    private Integer timerSet;
    private Integer pomo;

    public Pomodoro() {
        this.timerSet = 25*60;
        this.pomo = 0;
    }

    public Pomodoro(Integer timerSet, Integer pomo) {
        this.timerSet = timerSet;
        this.pomo = pomo;
    }

    public void update(Integer timerSet) {
        this.timerSet = timerSet;
    }
}
