package com.tasko.task.interfaces.web.user;

import com.tasko.task.domain.model.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private String name;
    private String pictureUrl;

    public UserResponseDto(User entity) {
        this.name = entity.getName();
        this.pictureUrl = entity.getPictureUrl();
    }
}
