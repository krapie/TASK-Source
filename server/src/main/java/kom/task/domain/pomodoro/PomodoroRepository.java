package kom.task.domain.pomodoro;

import kom.task.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PomodoroRepository extends JpaRepository<Pomodoro, String> {
    Optional<Pomodoro> findByUserId(String userId);
}
