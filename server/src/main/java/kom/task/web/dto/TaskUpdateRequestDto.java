package kom.task.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskUpdateRequestDto {

    private String content;
    private Boolean isDone;

    @Builder
    public TaskUpdateRequestDto(String content, Boolean isDone) {
        this.content = content;
        this.isDone = isDone;
    }
}
