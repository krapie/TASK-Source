package com.tasko.task.domain.service.todo.impl;

import com.tasko.task.domain.repository.daydo.DaydoRepository;
import com.tasko.task.domain.service.todo.DailydoService;
import com.tasko.task.interfaces.web.dailydo.DaydoResponseDto;
import com.tasko.task.interfaces.web.dailydo.DaydoSaveRequestDto;
import com.tasko.task.interfaces.web.dailydo.DaydoUpdateRequestDto;
import com.tasko.task.interfaces.web.dailydo.TodoResponseDto;
import com.tasko.task.interfaces.web.dailydo.TodoSaveRequestDto;
import com.tasko.task.interfaces.web.dailydo.TodoUpdateRequestDto;
import com.tasko.task.domain.model.dailydo.Daydo;
import com.tasko.task.domain.model.dailydo.Todo;
import com.tasko.task.domain.repository.daydo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DailydoServiceImpl implements DailydoService {
    private final TodoRepository todoRepository;
    private final DaydoRepository daydoRepository;

    @Transactional
    public TodoResponseDto saveTodoItem(TodoSaveRequestDto requestDto) {
        Todo createdTodo = todoRepository.save(requestDto.toEntity());

        return new TodoResponseDto(createdTodo);
    }

    @Transactional
    public List<TodoResponseDto> fetchAllTodoItems(String userId) {
        List<TodoResponseDto> responseDtoList = new ArrayList<>();
        List<Todo> todoEntities = todoRepository.findAllByUserId(userId);

        for(Todo todoEntity : todoEntities) {
            responseDtoList.add(new TodoResponseDto(todoEntity));
        }

        return responseDtoList;
    }

    @Transactional
    public Long updateTodoItem(Long id, TodoUpdateRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        todo.update(requestDto.getContent(),requestDto.getIsDone());

        return id;
    }

    @Transactional
    public Long deleteTodoItem(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        todoRepository.delete(todo);

        return id;
    }

    /*** DAY DO REST SERVICE ***/
    @Transactional
    public DaydoResponseDto saveDaydoItem(DaydoSaveRequestDto requestDto) {
        Daydo createdDaydo = daydoRepository.save(requestDto.toEntity());

        return new DaydoResponseDto(createdDaydo);
    }

    @Transactional
    public List<DaydoResponseDto> fetchAllDaydoItems(String userId) {
        List<DaydoResponseDto> responseDtoList = new ArrayList<>();
        List<Daydo> daydoEntities = daydoRepository.findAllByUserId(userId);

        for(Daydo daydoEntity : daydoEntities) {
            responseDtoList.add(new DaydoResponseDto(daydoEntity));
        }

        return responseDtoList;
    }

    @Transactional
    public Long updateDaydoItem(Long id, DaydoUpdateRequestDto requestDto) {
        Daydo daydo = daydoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        daydo.update(requestDto.getContent());

        return id;
    }

    @Transactional
    public Long deleteDaydoItem(Long id) {
        Daydo daydo = daydoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        daydoRepository.delete(daydo);

        return id;
    }
}
