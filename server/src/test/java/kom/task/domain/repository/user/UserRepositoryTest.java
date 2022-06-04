package kom.task.domain.repository.user;

import kom.task.domain.repository.user.User;
import kom.task.domain.repository.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void user_repository_save_test() {
        //given
        String userId = "1234";
        String name = "kevin";
        String email = "kevin@gmail.com";
        String pictureUrl = "kevinHandsome.png";

        userRepository.save(User.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .pictureUrl(pictureUrl)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPictureUrl()).isEqualTo(pictureUrl);
    }
}
