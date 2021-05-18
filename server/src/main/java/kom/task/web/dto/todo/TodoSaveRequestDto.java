package kom.task.web.dto.todo;

import kom.task.domain.dailydo.todo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoSaveRequestDto {

    private String token;
    private String content;
    private Boolean isDone;

    @Builder
    public TodoSaveRequestDto(String token, String content, Boolean isDone) {
        this.token = token;
        this.content = content;
        this.isDone = isDone;
    }

    public Todo toEntity(String userId) {
        return Todo.builder()
                .userId(userId)
                .content(content)
                .isDone(isDone)
                .build();
    }
}
