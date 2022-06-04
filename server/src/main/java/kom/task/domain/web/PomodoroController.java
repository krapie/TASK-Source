package kom.task.domain.web;

import com.fasterxml.jackson.databind.node.TextNode;
import kom.task.domain.service.PomodoroService;
import kom.task.domain.web.dto.pomodoro.PomodoroResponseDto;
import kom.task.domain.web.dto.pomodoro.PomodoroUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://www.tasko.link","http://localhost:3000","http://localhost:3001","http://localhost:3002"})
public class PomodoroController {
    private final PomodoroService pomodoroService;

    // Read
    @PostMapping("/api/pomodoro")
    public ResponseEntity<?> fetchPomodoroItem(@RequestBody TextNode userIdString) {
        PomodoroResponseDto responseDto = pomodoroService.fetchPomodoroItem(userIdString.asText());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update
    @PutMapping("/api/pomodoro/update")
    public ResponseEntity<?> updatePomodoroItem(@RequestBody PomodoroUpdateRequestDto requestDto) {

        PomodoroResponseDto updatedDto = pomodoroService.updatePomodoroItem(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
}
