package az.hrportal.hrportalapi.repository;

import az.hrportal.hrportalapi.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Integer> {
    Optional<Day> findByDay(LocalDate day);

    @Query("select d from Day d where d.jobDay=true and month(d.day) = :month order by d.day desc")
    List<Day> findLastJobDayOfMonth(@Param("month") Integer month);
}
