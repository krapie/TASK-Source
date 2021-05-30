package kom.task.web.dto.user;

import kom.task.domain.user.User;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private String userId;

    public UserLoginResponseDto(String userId) {
        this.userId = userId;
    }
}
