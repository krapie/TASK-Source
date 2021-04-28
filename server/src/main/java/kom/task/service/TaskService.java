package kom.task.service;

import kom.task.domain.daydo.Daydo;
import kom.task.domain.daydo.DaydoRepository;
import kom.task.domain.todo.Todo;
import kom.task.domain.todo.TodoRepository;
import kom.task.web.dto.daydo.DaydoResponseDto;
import kom.task.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.web.dto.todo.TodoResponseDto;
import kom.task.web.dto.todo.TodoSaveRequestDto;
import kom.task.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TodoRepository todoRepository;
    private final DaydoRepository daydoRepository;

    /*** TO DO REST SERVICE ***/
    @Transactional
    public Todo saveTodoItem(TodoSaveRequestDto requestDto) {
        return todoRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<TodoResponseDto> fetchAllTodoItems() {
        List<Todo> todoEntities = todoRepository.findAll();
        List<TodoResponseDto> responseDtoList = new ArrayList<>();

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
    public Daydo saveDaydoItem(DaydoSaveRequestDto requestDto) {
        return daydoRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<DaydoResponseDto> fetchAllDaydoItems() {
        List<Daydo> daydoEntities = daydoRepository.findAll();
        List<DaydoResponseDto> responseDtoList = new ArrayList<>();

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
