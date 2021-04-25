package kom.task.web;

import kom.task.domain.task.Task;
import kom.task.domain.task.TaskRepository;
import kom.task.web.dto.TaskSaveRequestDto;
import kom.task.web.dto.TaskUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @After
    public void cleanUp() throws Exception {
        taskRepository.deleteAll();
    }

    @Test
    public void create_task() throws Exception {
        //given
        String content = "task";
        Boolean isDone = true;

        TaskSaveRequestDto requestDto = TaskSaveRequestDto.builder()
                .content(content)
                .isDone(isDone)
                .build();

        String url = "http://localhost:" + port + "/api/task";

        //when
        ResponseEntity responseEntity = testRestTemplate.postForEntity(url, requestDto, null);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Task> all = taskRepository.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getIsDone()).isEqualTo(isDone);
    }

    @Test
    public void update_task() throws Exception {
        //given
        Task task = taskRepository.save(Task.builder()
                .content("task #1")
                .isDone(true)
                .build());

        Long updateId = task.getId();
        String expectedContent = "task #2";
        Boolean expectedIsDone = true;

        TaskUpdateRequestDto requestDto = TaskUpdateRequestDto.builder()
                .content(expectedContent)
                .isDone(expectedIsDone)
                .build();

        String url = "http://localhost:" + port + "/api/task/" + updateId;

        HttpEntity<TaskUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Task> all = taskRepository.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
        assertThat(all.get(0).getIsDone()).isEqualTo(expectedIsDone);
    }
}
