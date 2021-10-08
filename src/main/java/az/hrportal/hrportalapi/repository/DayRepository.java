package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Integer> {
    Optional<Day> findByDay(LocalDate day);
}
