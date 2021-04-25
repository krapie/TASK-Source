package kom.task.web.dto;

import kom.task.domain.task.Task;
import lombok.Getter;

@Getter
public class TaskResponseDto {

    private Long id;
    private String content;
    private Boolean isDone;

    public TaskResponseDto(Task entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.isDone = entity.getIsDone();
    }
}
