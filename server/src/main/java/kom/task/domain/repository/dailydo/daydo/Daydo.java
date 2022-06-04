package kom.task.domain.repository.dailydo.daydo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Daydo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private Integer day; // Monday - Sunday : 1 ~ 7

    @Column
    private String content;


    @Builder
    public Daydo(String userId, Integer day, String content) {
        this.userId = userId;
        this.day = day;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
