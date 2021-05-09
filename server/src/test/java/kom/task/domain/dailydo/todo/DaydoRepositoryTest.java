package kom.task.domain.dailydo.todo;

import kom.task.domain.dailydo.daydo.Daydo;
import kom.task.domain.dailydo.daydo.DaydoRepository;
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
public class DaydoRepositoryTest {

    @Autowired
    DaydoRepository daydoRepository;

    @After
    public void cleanup() {
        daydoRepository.deleteAll();
    }

    @Test
    public void daydo_repository_save_test() {
        //given
        Integer day = 0;
        String content = "day do";


        daydoRepository.save(Daydo.builder()
                .day(day)
                .content(content)
                .build());

        //when
        List<Daydo> dayDoList = daydoRepository.findAll();

        //then
        Daydo daydo = dayDoList.get(0);
        assertThat(daydo.getContent()).isEqualTo(content);
        assertThat(daydo.getDay()).isEqualTo(day);
    }
}
