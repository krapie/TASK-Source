package kom.task.web.dto.todo;

import kom.task.domain.todo.Todo;
import lombok.Getter;

@Getter
public class TodoResponseDto {

    private Long id;
    private String content;
    private Boolean isDone;

    public TodoResponseDto(Todo entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.isDone = entity.getIsDone();
    }
}
