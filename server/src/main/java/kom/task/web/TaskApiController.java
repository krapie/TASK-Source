package kom.task.web;

import kom.task.domain.task.Task;
import kom.task.service.TaskService;
import kom.task.web.dto.TaskResponseDto;
import kom.task.web.dto.TaskSaveRequestDto;
import kom.task.web.dto.TaskUpdateRequestDto;
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

    // Create
    @PostMapping("/api/task")
    public ResponseEntity<?> save(@RequestBody TaskSaveRequestDto requestDto) {
        Task newTask = taskService.save(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(newTask);
    }

    // Read
    @GetMapping("/api/task")
    public ResponseEntity<?> fetch() {
        List<TaskResponseDto> responseDto = taskService.fetchAll();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/task/{id}")
    public Long update(@PathVariable Long id, @RequestBody TaskUpdateRequestDto requestDto) {
        return taskService.update(id, requestDto);
    }

    // Delete
    @DeleteMapping("/api/task/{id}")
    public Long delete(@PathVariable Long id) {
        return taskService.delete(id);
    }
}
