package kom.task.domain.task;

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
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @After
    public void cleanup() {
        taskRepository.deleteAll();
    }

    @Test
    public void task_repository_save_test() {
        //given
        String content = "test";
        Boolean isDone = true;

        taskRepository.save(Task.builder()
                .content(content)
                .isDone(isDone)
                .build());

        //when
        List<Task> taskList = taskRepository.findAll();

        //then
        Task task = taskList.get(0);
        assertThat(task.getContent()).isEqualTo(content);
        assertThat(task.getIsDone()).isEqualTo(isDone);
    }

}
