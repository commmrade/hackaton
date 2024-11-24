package com.example.eventService.service;

import com.example.eventService.model.SubscribedEventDTO;
import com.example.eventService.repository.SubscribedEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribedEventService {
    private final SubscribedEventRepository repository;

    public SubscribedEventService(SubscribedEventRepository repo) {
        this.repository = repo;
    }

    @Transactional
    public void addSubscribedEvent(Long userId, Long eventId) {
        repository.addSubscribedEvent(userId, eventId);
    }

    public boolean exists(Long userId, Long eventId) {
        return repository.existsByUserIdAndEventId(userId, eventId) == 1;
    }

    public List<SubscribedEventDTO> getSubEventsByUserId(Long userID) {
        return repository.findAllByUserId(userID);
    }

    @Transactional
    public void deleteByUseridAndTaskId(Long user_id, Long event_id) {
        repository.deleteByUserIdAndEventId(user_id, event_id);
    }
}
