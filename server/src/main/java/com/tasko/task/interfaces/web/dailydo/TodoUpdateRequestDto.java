package com.tasko.task.interfaces.web.dailydo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoUpdateRequestDto {

    private String content;
    private Boolean isDone;

    @Builder
    public TodoUpdateRequestDto(String content, Boolean isDone) {
        this.content = content;
        this.isDone = isDone;
    }
}
