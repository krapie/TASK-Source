package kom.task.domain.daydo;

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
    private Integer day; // Sunday - Saturday : 0 ~ 6

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
