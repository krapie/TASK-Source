package kom.task.web.dto.user;

import kom.task.domain.user.User;
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
