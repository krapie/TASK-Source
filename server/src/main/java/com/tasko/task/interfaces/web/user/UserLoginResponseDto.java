package com.tasko.task.interfaces.web.user;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private String userId;

    public UserLoginResponseDto(String userId) {
        this.userId = userId;
    }
}
