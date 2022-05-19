package kom.task.repository.repository.pomodoro;

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
    
    @Column
    private Integer maxPomo;

    @Column Integer totalPomo;

    public Pomodoro(String userId) {
        this.userId = userId;
        this.timerSet = 25*60;
        this.pomo = 0;
        this.maxPomo = 0;
        this.totalPomo = 0;
    }

    @Builder
    public Pomodoro(String userId, Integer timerSet, Integer pomo) {
        this.userId = userId;
        this.timerSet = timerSet;
        this.pomo = pomo;
        this.maxPomo = 0;
        this.totalPomo = 0;
    }

    public void update(Integer timerSet, Integer pomo) {
        // 뽀모가 업데이트됬다면
        if(pomo == 1 + this.pomo) {
            this.totalPomo += 1;
        }

        this.timerSet = timerSet;
        this.pomo = pomo;
    }

    public void updateMaxPomo() {
        if(this.pomo > this.maxPomo) {
            this.maxPomo = this.pomo;
        }
        this.pomo = 0;
    }
}
