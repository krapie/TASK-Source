package com.tasko.task.domain.service.user;

import com.tasko.task.interfaces.web.user.UserLoginResponseDto;
import com.tasko.task.interfaces.web.user.UserResponseDto;

public interface UserService {

    UserLoginResponseDto googleTokenLogin(String tokenDtoString);
    UserResponseDto fetchUserInfo(String userId);
}
