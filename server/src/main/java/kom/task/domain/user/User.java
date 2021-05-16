package kom.task.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public User(String userId, String name, String email, String pictureUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    public User update(String name, String pictureUrl) {
        this.name = name;
        this.pictureUrl = pictureUrl;

        return this;
    }
}
