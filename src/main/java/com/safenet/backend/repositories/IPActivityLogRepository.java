package com.safenet.backend.repositories;

import com.safenet.backend.entities.IPActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPActivityLogRepository extends JpaRepository<IPActivityLog, Long> {
}
