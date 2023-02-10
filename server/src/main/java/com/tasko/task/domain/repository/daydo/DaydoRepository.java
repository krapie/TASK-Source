package com.tasko.task.domain.repository.daydo;

import com.tasko.task.domain.model.dailydo.Daydo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DaydoRepository extends JpaRepository<Daydo, Long> {
    List<Daydo> findAllByUserId(String userId);
}
