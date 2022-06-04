package kom.task.domain.web;

import com.fasterxml.jackson.databind.node.TextNode;
import kom.task.domain.service.DailydoService;
import kom.task.domain.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.domain.web.dto.daydo.DaydoResponseDto;
import kom.task.domain.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.domain.web.dto.todo.TodoResponseDto;
import kom.task.domain.web.dto.todo.TodoSaveRequestDto;
import kom.task.domain.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://www.tasko.link","http://localhost:3000","http://localhost:3001","http://localhost:3002"})
public class DailydoController {
    private final DailydoService dailydoService;

    // Create
    @PostMapping("/api/todo")
    public ResponseEntity<?> saveTodoItem(@RequestBody TodoSaveRequestDto requestDto) {
        TodoResponseDto responseDto = dailydoService.saveTodoItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Read
    @PostMapping("/api/todos")
    public ResponseEntity<?> fetchAllTodoItems(@RequestBody TextNode userIdString) {
        List<TodoResponseDto> responseDto = dailydoService.fetchAllTodoItems(userIdString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/todo/{id}")
    public Long updateTodoItem(@PathVariable Long id, @RequestBody TodoUpdateRequestDto requestDto) {
        return dailydoService.updateTodoItem(id, requestDto);
    }

    // Delete
    @DeleteMapping("/api/todo/{id}")
    public Long deleteTodoItem(@PathVariable Long id) {
        return dailydoService.deleteTodoItem(id);
    }


    /*** Day Do REST CONTROLLER ***/
    // Create
    @PostMapping("/api/daydo")
    public ResponseEntity<?> saveDaydoItem(@RequestBody DaydoSaveRequestDto requestDto) {
        DaydoResponseDto responseDto = dailydoService.saveDaydoItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Read
    @PostMapping("/api/daydos")
    public ResponseEntity<?> fetchAllDaydoItems(@RequestBody TextNode userIdString) {
        List<DaydoResponseDto> responseDto = dailydoService.fetchAllDaydoItems(userIdString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/daydo/{id}")
    public Long updateDaydoItem(@PathVariable Long id, @RequestBody DaydoUpdateRequestDto requestDto) {
        return dailydoService.updateDaydoItem(id, requestDto);
    }

    // Delete
    @DeleteMapping("/api/daydo/{id}")
    public Long deleteDaydoItem(@PathVariable Long id) {
        return dailydoService.deleteDaydoItem(id);
    }
}
