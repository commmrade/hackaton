package com.example.eventService.repository;

import com.example.eventService.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    @Query("SELECT e, c.name FROM Event e LEFT JOIN Category c ON e.categoryId = c.id " +
            "WHERE e.startDate >= CURRENT_TIMESTAMP AND e.startDate < :nextWeek")
    List<Object[]> findEventsWeek(@Param("nextWeek") LocalDateTime nextWeek);

    @Query("SELECT e, c.name FROM Event e LEFT JOIN Category c ON e.categoryId = c.id")
    List<Object[]> findAllWithCategoryName();

    @Query(value = "SELECT e, c.name FROM Event e LEFT JOIN Category c ON e.categoryId = c.id " +
            "WHERE e.startDate >= CURRENT_TIMESTAMP " +
            "ORDER BY RAND() LIMIT 3")
    List<Object[]> findRandom3Events();

}

