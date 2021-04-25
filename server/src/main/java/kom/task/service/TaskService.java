package kom.task.service;

import kom.task.domain.task.Task;
import kom.task.domain.task.TaskRepository;
import kom.task.web.dto.TaskResponseDto;
import kom.task.web.dto.TaskSaveRequestDto;
import kom.task.web.dto.TaskUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task save(TaskSaveRequestDto requestDto) {
        return taskRepository.save(requestDto.toEntity());
    }

    @Transactional
    public List<TaskResponseDto> fetchAll() {
        List<Task> taskEntities = taskRepository.findAll();
        List<TaskResponseDto> responseDtoList = new ArrayList<>();

        for(Task taskEntity : taskEntities) {
            responseDtoList.add(new TaskResponseDto(taskEntity));
        }

        return responseDtoList;
    }

    @Transactional
    public Long update(Long id, TaskUpdateRequestDto requestDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        task.update(requestDto.getContent(),requestDto.getIsDone());

        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Task todo = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        taskRepository.delete(todo);

        return id;
    }
}
