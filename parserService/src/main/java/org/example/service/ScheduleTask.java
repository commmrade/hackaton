package org.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
    @Autowired
    private PdfService pdfService;

    @Scheduled(cron = "0 15 0 * * ?")
    public void parse() {
        pdfService.parsePdf();
    }

}
