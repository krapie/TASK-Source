package kom.task.web.dto;

import kom.task.domain.task.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskSaveRequestDto {

    private String content;
    private Boolean isDone;

    @Builder
    public TaskSaveRequestDto(String content, Boolean isDone) {
        this.content = content;
        this.isDone = isDone;
    }

    public Task toEntity() {
        return Task.builder()
                .content(content)
                .isDone(isDone)
                .build();
    }
}
