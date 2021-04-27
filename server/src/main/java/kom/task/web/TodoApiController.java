package kom.task.web;

import kom.task.domain.todo.Todo;
import kom.task.service.TodoService;
import kom.task.web.dto.TodoResponseDto;
import kom.task.web.dto.TodoSaveRequestDto;
import kom.task.web.dto.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoApiController {

    private final TodoService todoService;

    // Create
    @PostMapping("/api/todo")
    public ResponseEntity<?> save(@RequestBody TodoSaveRequestDto requestDto) {
        Todo newTodo = todoService.save(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(newTodo);
    }

    // Read
    @GetMapping("/api/todo")
    public ResponseEntity<?> fetch() {
        List<TodoResponseDto> responseDto = todoService.fetchAll();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/todo/{id}")
    public Long update(@PathVariable Long id, @RequestBody TodoUpdateRequestDto requestDto) {
        return todoService.update(id, requestDto);
    }

    // Delete
    @DeleteMapping("/api/todo/{id}")
    public Long delete(@PathVariable Long id) {
        return todoService.delete(id);
    }
}
