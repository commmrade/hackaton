package com.example.eventService.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subscribed_events")
public class SubscribedEventDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long eventId;


    public SubscribedEventDTO() {

    }

    public SubscribedEventDTO(Long id, Long userId, Long eventId) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
