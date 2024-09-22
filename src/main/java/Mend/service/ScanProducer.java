package Mend.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScanProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendScanTask(String task) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE.PERSISTENT);

        Message message = new Message(task.getBytes(), messageProperties);
        rabbitTemplate.send("scanExchange", "scanQueue", message);
    }
}

