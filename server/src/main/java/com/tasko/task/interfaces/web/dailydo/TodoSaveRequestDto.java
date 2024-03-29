package com.tasko.task.interfaces.web.dailydo;

import com.tasko.task.domain.model.dailydo.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoSaveRequestDto {

    private String userId;
    private String content;
    private Boolean isDone;

    @Builder
    public TodoSaveRequestDto(String userId, String content, Boolean isDone) {
        this.userId = userId;
        this.content = content;
        this.isDone = isDone;
    }

    public Todo toEntity() {
        return Todo.builder()
                .userId(userId)
                .content(content)
                .isDone(isDone)
                .build();
    }
}
