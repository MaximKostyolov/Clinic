package ru.ktelabs.clinic.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.ktelabs.clinic.model.schedule.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, QuerydslPredicateExecutor<Schedule> {
}
