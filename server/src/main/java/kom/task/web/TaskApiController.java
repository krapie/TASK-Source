package kom.task.web;

import com.fasterxml.jackson.databind.node.TextNode;
import kom.task.domain.dailydo.daydo.Daydo;
import kom.task.domain.dailydo.todo.Todo;
import kom.task.service.TaskService;
import kom.task.web.dto.daydo.DaydoResponseDto;
import kom.task.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.web.dto.pomodoro.PomodoroResponseDto;
import kom.task.web.dto.pomodoro.PomodoroUpdateRequestDto;
import kom.task.web.dto.todo.TodoResponseDto;
import kom.task.web.dto.todo.TodoSaveRequestDto;
import kom.task.web.dto.todo.TodoUpdateRequestDto;
import kom.task.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://komputer-task.ml"})
public class TaskApiController {

    private final TaskService taskService;

    /*** LOGIN REST CONTROLLER ***/
    // Login
    @PostMapping("/api/google/tokensignin")
    public String googleTokenLogin(@RequestBody TextNode tokenDtoString) {
        String userName = taskService.googleTokenLogin(tokenDtoString.asText());

        return userName;
    }

    // Get User Info
    @PostMapping("/api/user")
    public ResponseEntity<?> fetchUserData(@RequestBody TextNode tokenDtoString) {
        UserResponseDto responseDto = taskService.fetchUserInfo(tokenDtoString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    /*** To Do REST CONTROLLER ***/
    // Create
    @PostMapping("/api/todo")
    public ResponseEntity<?> saveTodoItem(@RequestBody TodoSaveRequestDto requestDto) {
        TodoResponseDto responseDto = taskService.saveTodoItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Read
    @PostMapping("/api/todos")
    public ResponseEntity<?> fetchAllTodoItems(@RequestBody TextNode tokenDtoString) {
        List<TodoResponseDto> responseDto = taskService.fetchAllTodoItems(tokenDtoString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/todo/{id}")
    public Long updateTodoItem(@PathVariable Long id, @RequestBody TodoUpdateRequestDto requestDto) {
        return taskService.updateTodoItem(id, requestDto);
    }

    // Delete
    @DeleteMapping("/api/todo/{id}")
    public Long deleteTodoItem(@PathVariable Long id) {
        return taskService.deleteTodoItem(id);
    }


    /*** Day Do REST CONTROLLER ***/
    // Create
    @PostMapping("/api/daydo")
    public ResponseEntity<?> saveDaydoItem(@RequestBody DaydoSaveRequestDto requestDto) {
        DaydoResponseDto responseDto = taskService.saveDaydoItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Read
    @PostMapping("/api/daydos")
    public ResponseEntity<?> fetchAllDaydoItems(@RequestBody TextNode tokenDtoString) {
        List<DaydoResponseDto> responseDto = taskService.fetchAllDaydoItems(tokenDtoString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/daydo/{id}")
    public Long updateDaydoItem(@PathVariable Long id, @RequestBody DaydoUpdateRequestDto requestDto) {
        return taskService.updateDaydoItem(id, requestDto);
    }

    // Delete
    @DeleteMapping("/api/daydo/{id}")
    public Long deleteDaydoItem(@PathVariable Long id) {
        return taskService.deleteDaydoItem(id);
    }


    /*** Pomodoro REST Controller ***/

     // Read
     @PostMapping("/api/pomodoro")
     public ResponseEntity<?> fetchPomodoroItem(@RequestBody TextNode tokenDtoString) {
         PomodoroResponseDto responseDto = taskService.fetchPomodoroItem(tokenDtoString.asText());

         return ResponseEntity.status(HttpStatus.OK).body(responseDto);
     }

     // Update
     @PutMapping("/api/pomodoro/update")
     public ResponseEntity<?> updatePomodoroItem(@RequestBody PomodoroUpdateRequestDto requestDto) {

         PomodoroResponseDto updatedDto = taskService.updatePomodoroItem(requestDto);

         return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
     }
}
