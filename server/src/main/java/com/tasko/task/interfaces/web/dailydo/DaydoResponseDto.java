package com.tasko.task.interfaces.web.dailydo;

import com.tasko.task.domain.model.dailydo.Daydo;
import lombok.Getter;

@Getter
public class DaydoResponseDto {

    private Long id;
    private Integer day;
    private String content;

    public DaydoResponseDto(Daydo entity) {
        this.id = entity.getId();
        this.day = entity.getDay();
        this.content = entity.getContent();
    }
}
