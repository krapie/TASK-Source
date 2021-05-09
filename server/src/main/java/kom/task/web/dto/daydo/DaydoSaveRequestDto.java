package kom.task.web.dto.daydo;

import kom.task.domain.dailydo.daydo.Daydo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaydoSaveRequestDto {

    private Integer day;
    private String content;

    @Builder
    public DaydoSaveRequestDto(Integer day, String content) {
        this.day = day;
        this.content = content;
    }

    public Daydo toEntity() {
        return Daydo.builder()
                .day(day)
                .content(content)
                .build();
    }
}
