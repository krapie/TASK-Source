package kom.task.repository.web.dto.daydo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaydoUpdateRequestDto {

    private Integer day;
    private String content;

    @Builder
    public DaydoUpdateRequestDto(Integer day, String content) {
        this.day = day;
        this.content = content;
    }
}
