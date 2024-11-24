package com.example.webservice.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        // Handle 404 (Not Found) error
        if (statusCode != null && statusCode == 404) {
            return "redirect:/web/mainpage";
        }
        System.out.println("here");
        // Handle other errors (e.g., 500, etc.)
        return "mainpage"; // Returns a generic error view (e.g., error.html)
    }
}
