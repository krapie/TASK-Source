package kom.task.domain.repository.dailydo.daydo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DaydoRepository extends JpaRepository<Daydo, Long> {
    List<Daydo> findAllByUserId(String userId);
}
