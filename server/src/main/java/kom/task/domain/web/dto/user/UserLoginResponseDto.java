package kom.task.domain.web.dto.user;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private String userId;

    public UserLoginResponseDto(String userId) {
        this.userId = userId;
    }
}
