package kom.task.service;

import kom.task.domain.dailydo.daydo.Daydo;
import kom.task.domain.dailydo.daydo.DaydoRepository;
import kom.task.domain.dailydo.todo.Todo;
import kom.task.domain.dailydo.todo.TodoRepository;
import kom.task.domain.pomodoro.Pomodoro;
import kom.task.web.dto.daydo.DaydoResponseDto;
import kom.task.web.dto.daydo.DaydoSaveRequestDto;
import kom.task.web.dto.daydo.DaydoUpdateRequestDto;
import kom.task.web.dto.todo.TodoResponseDto;
import kom.task.web.dto.todo.TodoSaveRequestDto;
import kom.task.web.dto.todo.TodoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TodoRepository todoRepository;
    private final DaydoRepository daydoRepository;
    private final Pomodoro pomodoro;

    /*** TO DO REST SERVICE ***/
    @Transactional
    public Todo saveTodoItem(TodoSaveRequestDto requestDto) {
        return todoRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<TodoResponseDto> fetchAllTodoItems() {
        todayFetch();

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


    /*** FETCH LOGICS ***/
    public void todayFetch() {
        // 이미 To-do 아이템이 존재하면 (이미 요일별 할 일이 저장되어 있으면)
        if(todoRepository.count() != 0) {
            return;
        }

        // 호출된 시점의 요일을 구해서
        LocalDate localDate = LocalDate.now();
        int todayDay =  localDate.getDayOfWeek().getValue(); // Monday - Sunday : 1 ~ 7

        // 해당 요일에 해당하는 튜플들을 DaydoRepository에서 추출 후
        List<Daydo> daydoList = daydoRepository.findAll();
        List<Daydo> todayDaydoList = daydoList.stream().filter(daydo -> daydo.getDay() == todayDay).collect(Collectors.toList());

        // To-do 아이템으로 변환 후 TodoRepository에 저장
        for(Daydo daydo : todayDaydoList ) {
            Todo todoEntity = Todo.builder()
                    .content(daydo.getContent())
                    .isDone(false)
                    .build();

            todoRepository.save(todoEntity);
        }
    }

    /*** Pomodoro Service ***/
    public Pomodoro fetchPomodoroItem() {
        return pomodoro;
    }

    public Pomodoro updatePomdoroItem(Pomodoro updateDto) {
        pomodoro.update(updateDto.getTimerSet());

        return pomodoro; // SUCCESS
    }
}