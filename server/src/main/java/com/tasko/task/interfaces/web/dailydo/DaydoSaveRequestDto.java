package com.tasko.task.interfaces.web.dailydo;

import com.tasko.task.domain.model.dailydo.Daydo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaydoSaveRequestDto {

    private String userId;
    private Integer day;
    private String content;

    @Builder
    public DaydoSaveRequestDto(String userId, Integer day, String content) {
        this.userId = userId;
        this.day = day;
        this.content = content;
    }

    public Daydo toEntity() {
        return Daydo.builder()
                .userId(userId)
                .day(day)
                .content(content)
                .build();
    }
}
