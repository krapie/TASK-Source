package kom.task.web.dto.todo;

import kom.task.domain.todo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoSaveRequestDto {

    private String content;
    private Boolean isDone;

    @Builder
    public TodoSaveRequestDto(String content, Boolean isDone) {
        this.content = content;
        this.isDone = isDone;
    }

    public Todo toEntity() {
        return Todo.builder()
                .content(content)
                .isDone(isDone)
                .build();
    }
}
