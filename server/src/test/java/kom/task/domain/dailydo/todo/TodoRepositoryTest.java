package kom.task.domain.dailydo.todo;

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
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @After
    public void cleanup() {
        todoRepository.deleteAll();
    }

    @Test
    public void todo_repository_save_test() {
        //given
        String content = "todo";
        Boolean isDone = true;

        todoRepository.save(Todo.builder()
                .content(content)
                .isDone(isDone)
                .build());

        //when
        List<Todo> todoList = todoRepository.findAll();

        //then
        Todo todo = todoList.get(0);
        assertThat(todo.getContent()).isEqualTo(content);
        assertThat(todo.getIsDone()).isEqualTo(isDone);
    }
}
