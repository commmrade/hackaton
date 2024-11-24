package com.example.eventService.controller;


import com.example.eventService.model.Event;
import com.example.eventService.model.SearchFilters;
import com.example.eventService.model.SubscribedEventDTO;
import com.example.eventService.model.User;
import com.example.eventService.service.EventService;
import com.example.eventService.service.SubscribedEventService;
import com.example.eventService.service.UserService;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EventController {

    @Autowired
    public EventService eventService;

    @Autowired
    public UserService userService;

    @Autowired
    private SubscribedEventService subscribedEventService;

    @GetMapping("/filter")
    public List<Event> eventsFilter(@RequestParam String filterType, Model page) {
        System.out.println("Request incoming");
        if (filterType.trim().equals("close")) {
            List<Event> events = eventService.getEvents(3);
            return events;
        } else if (filterType.trim().equals("weekly")) {
            List<Event> events = eventService.getEvents(7);
            return events;
        } else if (filterType.trim().equals("monthly")) {
            List<Event> events = eventService.getEvents(30);
            return events;
        } else if (filterType.trim().equals("quarterly")) {
            List<Event> events = eventService.getEvents(90);
            return events;
        } else if (filterType.trim().equals("half")) {
            List<Event> events = eventService.getEvents(180);
            return events;
        }

        List<Event> events = eventService.getEvents(3);
        return events;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Event>> search(@RequestBody SearchFilters searchFilters) {
        List<Event> allEvents = eventService.getAllEvents();
        double threshold = 0.5;

        List<Event> filteredEvents = allEvents.stream()
                .filter(event -> {
                    return List.of(
                            searchFilters.getCountry() == null || isSimilar(searchFilters.getCountry(), event.getCountry(), threshold),
                            searchFilters.getRegion() == null || isSimilar(searchFilters.getRegion(), event.getRegion(), threshold),
                            searchFilters.getSportType() == null || isSimilar(searchFilters.getSportType(), event.getCategoryName(), threshold),
                            searchFilters.getStartDate() == null || !event.getStartDate().before(searchFilters.getStartDate()),
                            searchFilters.getEndDate() == null || !event.getEndDate().after(searchFilters.getEndDate()),
                            searchFilters.getAgeAndSex() == null || isSimilar(searchFilters.getAgeAndSex(), event.getSexAndAge(), threshold),
                            searchFilters.getCompType() == null || isSimilar(searchFilters.getCompType(), event.getType(), threshold)
                    ).stream().allMatch(Boolean::booleanValue); // Проверяем все условия
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredEvents);
    }

    private boolean isSimilar(String input, String target, double threshold) {
        input = input.toLowerCase().trim();
        target = target.toLowerCase().trim();

        int lcsLength = longestCommonSubstring(input, target);


        int inputLength = input.length();
        int targetLength = target.length();

        double ratioToMax = (double) lcsLength / Math.max(inputLength, targetLength);
        double ratioToMin = (double) lcsLength / Math.min(inputLength, targetLength);
        double similarity = 0.5 * ratioToMax + 0.5 * ratioToMin; // Вес 50% на каждое соотношение

        return similarity >= threshold;
    }

    private int longestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    maxLength = Math.max(maxLength, dp[i][j]);
                }
            }
        }
        return maxLength;
    }

    @GetMapping("/rec-events")
    public ResponseEntity<?> recEvents() {
        List<Event> randEvents = eventService.randomEvents();
        return ResponseEntity.ok(randEvents);
    }



    @PutMapping("/subscribe-event")
    public ResponseEntity<?> subscribeToEvent(@RequestParam long eventid) {
        Optional<User> userOpt = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userOpt.isPresent() && !subscribedEventService.exists(userOpt.get().getId(), eventid)) {
            subscribedEventService.addSubscribedEvent(userOpt.get().getId(), eventid);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscribed-events")
    public ResponseEntity<?> subscribedEvents() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            List<SubscribedEventDTO> subEvents = subscribedEventService.getSubEventsByUserId(userOpt.get().getId());

            List<Event> events = new ArrayList<>();

            for (var subEvent : subEvents) {
                Optional<Event> eventOpt = eventService.getById(subEvent.getEventId());
                if (eventOpt.isPresent()) {
                    events.add(eventOpt.get());
                }

            }
            return ResponseEntity.ok(events);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete-event")
    public ResponseEntity<?> deleteSubEvent(@RequestParam long event_id) {
        Optional<User> userOpt = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userOpt.isPresent()) {
            //Delete by task_id, user_id
            subscribedEventService.deleteByUseridAndTaskId(userOpt.get().getId(), event_id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
