package com.tasko.task.domain.service.todo;

import com.tasko.task.interfaces.web.dailydo.*;

import java.util.List;

public interface DailydoService {

    TodoResponseDto saveTodoItem(TodoSaveRequestDto requestDto);
    List<TodoResponseDto> fetchAllTodoItems(String userId);
    Long updateTodoItem(Long id, TodoUpdateRequestDto requestDto);
    Long deleteTodoItem(Long id);
    DaydoResponseDto saveDaydoItem(DaydoSaveRequestDto requestDto);
    List<DaydoResponseDto> fetchAllDaydoItems(String userId);
    Long updateDaydoItem(Long id, DaydoUpdateRequestDto requestDto);
    Long deleteDaydoItem(Long id);
}
