package kom.task.web.dto.user;

import kom.task.domain.user.User;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private String name;

    public UserLoginResponseDto(String name) {
        this.name = name;
    }
}
