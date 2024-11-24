package com.example.webservice.controller;

import com.example.webservice.model.Event;
import com.example.webservice.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private RestService restService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/filter")
    public String filterPage(@RequestParam String filterType, Model page) {

        List<Event> events = restService.getEvents(filterType.trim());
        page.addAttribute("events", events);
        return "filter";
    }

    @GetMapping("/mainpage")
    public String mainPage(Model page) {

        List<Event> events = restService.getSubEvents();
        List<Event> recs = restService.getRecs();
        page.addAttribute("events", events);
        page.addAttribute("recs", recs);
        return "mainpage";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


    @GetMapping("/search")
    public String search() {
        return "search";
    }

}
