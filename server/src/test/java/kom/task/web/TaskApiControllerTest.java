package kom.task.web;

import kom.task.domain.daydo.Daydo;
import kom.task.domain.daydo.DaydoRepository;
import kom.task.domain.todo.Todo;
import kom.task.domain.todo.TodoRepository;
import kom.task.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.web.dto.todo.TodoSaveRequestDto;
import kom.task.web.dto.todo.TodoUpdateRequestDto;
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
    private TodoRepository todoRepository;

    @Autowired
    private DaydoRepository daydoRepository;

    @After
    public void cleanUp() throws Exception {
        todoRepository.deleteAll();
        daydoRepository.deleteAll();
    }

    /*** To do REST TEST ***/
    @Test
    public void create_todo_item() throws Exception {
        //given
        String content = "test";
        Boolean isDone = true;

        TodoSaveRequestDto requestDto = TodoSaveRequestDto.builder()
                .content(content)
                .isDone(isDone)
                .build();

        String url = "http://localhost:" + port + "/api/todo";

        //when
        ResponseEntity responseEntity = testRestTemplate.postForEntity(url, requestDto, null);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Todo> all = todoRepository.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getIsDone()).isEqualTo(isDone);
    }

    @Test
    public void update_todo_item() throws Exception {
        //given
        Todo todo = todoRepository.save(Todo.builder()
                .content("todo #1")
                .isDone(true)
                .build());

        Long updateId = todo.getId();
        String expectedContent = "todo #2";
        Boolean expectedIsDone = true;

        TodoUpdateRequestDto requestDto = TodoUpdateRequestDto.builder()
                .content(expectedContent)
                .isDone(expectedIsDone)
                .build();

        String url = "http://localhost:" + port + "/api/todo/" + updateId;

        HttpEntity<TodoUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Todo> all = todoRepository.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
        assertThat(all.get(0).getIsDone()).isEqualTo(expectedIsDone);
    }


    /*** Day do REST TEST ***/
    @Test
    public void create_daydo_item() throws Exception {
        //given
        Integer day = 0;
        String content = "Sunday Todo";

        DaydoSaveRequestDto requestDto = DaydoSaveRequestDto.builder()
                .day(day)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/daydo";

        //when
        ResponseEntity responseEntity = testRestTemplate.postForEntity(url, requestDto, null);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Daydo> all = daydoRepository.findAll();
        assertThat(all.get(0).getDay()).isEqualTo(day);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void update_daydo_item() throws Exception {
        //given
        Daydo daydo = daydoRepository.save(Daydo.builder()
                .day(0)
                .content("Sunday Todo #1")
                .build());

        Long updateId = daydo.getId();
        String expectedContent = "Sunday Todo #2";

        DaydoUpdateRequestDto requestDto = DaydoUpdateRequestDto.builder()
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/daydo/" + updateId;

        HttpEntity<DaydoUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Daydo> all = daydoRepository.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
