package lk.kalindu.messaging;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import lk.kalindu.service.StatisticsService;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationLookup",
                        propertyValue = "jms/OrderQueue"
                ),

                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"
                )
        }
)

public class OrderMessageBean implements MessageListener {


    @PostConstruct
    public void init() {
        System.out.println("MDB Created");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("MDB Destroyed");
    }

    @Inject
    private StatisticsService statisticsService;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String order = textMessage.getText();
            System.out.println("Order received : " + order);
            statisticsService.incrementMessagesProcessed();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
