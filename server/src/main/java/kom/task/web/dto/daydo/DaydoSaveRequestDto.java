package kom.task.web.dto.daydo;

import kom.task.domain.dailydo.daydo.Daydo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaydoSaveRequestDto {

    private String token;
    private Integer day;
    private String content;

    @Builder
    public DaydoSaveRequestDto(String token, Integer day, String content) {
        this.token = token;
        this.day = day;
        this.content = content;
    }

    public Daydo toEntity(String userId) {
        return Daydo.builder()
                .userId(userId)
                .day(day)
                .content(content)
                .build();
    }
}
