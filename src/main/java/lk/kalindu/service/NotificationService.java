package lk.kalindu.service;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.AsyncResult;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.concurrent.Future;

@Stateless
public class NotificationService {
    @Inject
    private StatisticsService statisticsService;

    @PostConstruct
    public void init(){
        System.out.println("Notification Service Created");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Notification Service Destroyed");

    }

    @Asynchronous
    public Future<String> sendEmail(String email) {

        try {
            Thread.sleep(3000);

            statisticsService.incrementEmailsSent();
            System.out.println("Email Successfully sent to " + email);
            return new AsyncResult<>("SUCCESS");

        } catch (Exception e) {
            return new AsyncResult<>("FAILED");
        }


    }

}
