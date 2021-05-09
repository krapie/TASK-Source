package kom.task.domain.dailydo.daydo;

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
    private Integer day; // Monday - Sunday : 1 ~ 7

    @Column
    private String content;


    @Builder
    public Daydo(Integer day, String content) {
        this.day = day;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
