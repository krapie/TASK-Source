package kom.task.domain.repository.pomdoro;

import kom.task.domain.repository.pomodoro.Pomodoro;
import kom.task.domain.repository.pomodoro.PomodoroRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PomodoroRepositoryTest {

    @Autowired
    PomodoroRepository pomodoroRepository;

    @After
    public void cleanup() {
        pomodoroRepository.deleteAll();
    }

    @Test
    public void pomodoro_repository_save_test() {
        //given
        String userId = "1234";
        Integer timerSet = 25*60;
        Integer pomo = 0;

        pomodoroRepository.save(Pomodoro.builder()
                .userId(userId)
                .timerSet(timerSet)
                .pomo(pomo)
                .build());

        //when
        List<Pomodoro> pomodoroList = pomodoroRepository.findAll();

        //then
        Pomodoro pomodoro = pomodoroList.get(0);
        assertThat(pomodoro.getUserId()).isEqualTo(userId);
        assertThat(pomodoro.getTimerSet()).isEqualTo(timerSet);
        assertThat(pomodoro.getPomo()).isEqualTo(pomo);
    }
}
