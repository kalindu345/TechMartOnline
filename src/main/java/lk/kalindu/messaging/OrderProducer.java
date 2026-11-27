package lk.kalindu.messaging;


import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless
public class OrderProducer {

    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/OrderQueue")
    private Queue queue;

    public void sendMessage(String message){

        context.createProducer()
                .send(queue,message);

        System.out.println("Message sent : "+message);

    }
}
