package com.example.eventService.service;


import com.example.eventService.model.Event;
import com.example.eventService.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;


    public List<Event> getEvents(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusDays(days);
        List<Object[]> objs = eventRepository.findEventsWeek(nextWeek);
        List<Event> events = new ArrayList<>();

        for (Object[] result : objs) {
            Event event = (Event) result[0];
            String categoryName = (String) result[1];
            event.setCategoryName(categoryName); // Установить categoryName
            events.add(event);
        }

        return events;
    }


    public Optional<Event> getById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAllEvents() {
        List<Object[]> objs = eventRepository.findAllWithCategoryName();
        List<Event> events = new ArrayList<>();

        for (Object[] result : objs) {
            Event event = (Event) result[0];
            String categoryName = (String) result[1];
            event.setCategoryName(categoryName); // Установить categoryName
            events.add(event);
        }

        return events;
    }

    public List<Event> randomEvents() {
        List<Object[]> objs = eventRepository.findRandom3Events();
        List<Event> events = new ArrayList<>();

        for (Object[] result : objs) {
            Event event = (Event) result[0];
            String categoryName = (String) result[1];
            event.setCategoryName(categoryName); // Установить categoryName
            events.add(event);
        }
        return events;
    }

}
