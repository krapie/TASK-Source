package kom.task.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String pictureUrl;

    @Column
    private LocalDate todoUpdatedDate;

    @Column
    private LocalDate pomodoroUpdatedDate;

    @Builder
    public User(String userId, String name, String email, String pictureUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.todoUpdatedDate = LocalDate.of(2021,5,5);
        this.pomodoroUpdatedDate = LocalDate.of(2021,5,5);
    }

    public User update(String name, String pictureUrl) {
        this.name = name;
        this.pictureUrl = pictureUrl;

        return this;
    }

    public void updateTodoUpdatedDate(LocalDate newDate) {
        this.todoUpdatedDate = newDate;
    }

    public void updatePomodoroUpdatedDate(LocalDate newDate) {
        this.pomodoroUpdatedDate = newDate;
    }
}
