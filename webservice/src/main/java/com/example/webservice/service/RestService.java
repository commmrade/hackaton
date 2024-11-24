package com.example.webservice.service;

import com.example.webservice.model.Event;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Security;
import java.util.Arrays;
import java.util.List;

@Service
public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Event> getEvents(String filterType) {
        String url = "http://194.87.201.253:8082/filter?filterType=" + filterType;


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "token=" + SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        Event[] events = this.restTemplate.exchange(url, HttpMethod.GET, entity, Event[].class).getBody();

        return Arrays.asList(events);
    }
    public List<Event> getSubEvents() {
        String url = "http://194.87.201.253:8082/subscribed-events";


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "token=" + SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        Event[] events = this.restTemplate.exchange(url, HttpMethod.GET, entity, Event[].class).getBody();

        return Arrays.asList(events);
    }

    public List<Event> getRecs() {
        String url = "http://194.87.201.253:8082/rec-events";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "token=" + SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        Event[] events = this.restTemplate.exchange(url, HttpMethod.GET, entity, Event[].class).getBody();

        return Arrays.asList(events);
    }

}
