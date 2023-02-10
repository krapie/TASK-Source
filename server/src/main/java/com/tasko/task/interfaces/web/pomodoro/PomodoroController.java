package com.tasko.task.interfaces.web.pomodoro;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tasko.task.domain.service.pomodoro.impl.PomodoroServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://www.tasko.link","http://localhost:3000","http://localhost:3001","http://localhost:3002"})
public class PomodoroController {
    private final PomodoroServiceImpl pomodoroService;

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
