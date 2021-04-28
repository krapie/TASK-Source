package kom.task.web;

import kom.task.domain.daydo.Daydo;
import kom.task.domain.todo.Todo;
import kom.task.service.TaskService;
import kom.task.web.dto.daydo.DaydoResponseDto;
import kom.task.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.web.dto.todo.TodoResponseDto;
import kom.task.web.dto.todo.TodoSaveRequestDto;
import kom.task.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TaskApiController {

    private final TaskService taskService;

    /*** To Do REST CONTROLLER ***/

    // Create
    @PostMapping("/api/todo")
    public ResponseEntity<?> saveTodoItem(@RequestBody TodoSaveRequestDto requestDto) {
        Todo newTodo = taskService.saveTodoItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(newTodo);
    }

    // Read
    @GetMapping("/api/todo")
    public ResponseEntity<?> fetchAllTodoItems() {
        List<TodoResponseDto> responseDto = taskService.fetchAllTodoItems();

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
        Daydo newDaydo = taskService.saveDaydoItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(newDaydo);
    }

    // Read
    @GetMapping("/api/daydo")
    public ResponseEntity<?> fetchAllDaydoItems() {
        List<DaydoResponseDto> responseDto = taskService.fetchAllDaydoItems();

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
}
