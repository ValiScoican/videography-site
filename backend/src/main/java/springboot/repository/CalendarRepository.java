package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.domain.entity.CalendarEntry;
import springboot.domain.entity.User;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntry, Long> {
}
