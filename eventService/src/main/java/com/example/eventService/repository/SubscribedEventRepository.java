package com.example.eventService.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.eventService.model.SubscribedEventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribedEventRepository extends JpaRepository<SubscribedEventDTO, Long> {
    @Query(value = "SELECT id, user_id, event_id FROM subscribed_events", nativeQuery = true)
    List<Object[]> findAllSubscribedEvents();

    @Modifying
    @Query(value = "INSERT INTO subscribed_events (user_id, event_id) VALUES (:userId, :eventId)", nativeQuery = true)
    void addSubscribedEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

    @Query(value = "SELECT * FROM subscribed_events WHERE user_id = :userId", nativeQuery = true)
    List<SubscribedEventDTO> findAllByUserId(@Param("userId") Long userId);


    @Query(value = "SELECT COUNT(*) > 0 FROM subscribed_events WHERE user_id = :userId AND event_id = :eventId", nativeQuery = true)
    Integer existsByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);


    @Modifying
    @Query(value = "DELETE FROM subscribed_events WHERE user_id = :userId AND event_id = :eventId", nativeQuery = true)
    void deleteByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
