package kom.task.domain.pomodoro;

import kom.task.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Pomodoro {

    @Id
    private String userId;

    @Column
    private Integer timerSet;

    @Column
    private Integer pomo;

    public Pomodoro(String userId) {
        this.userId = userId;
        this.timerSet = 25*60;
        this.pomo = 0;
    }

    @Builder
    public Pomodoro(String userId, Integer timerSet, Integer pomo) {
        this.userId = userId;
        this.timerSet = timerSet;
        this.pomo = pomo;
    }

    public void update(Integer timerSet, Integer pomo) {
        this.timerSet = timerSet;
        this.pomo = pomo;
    }
}
