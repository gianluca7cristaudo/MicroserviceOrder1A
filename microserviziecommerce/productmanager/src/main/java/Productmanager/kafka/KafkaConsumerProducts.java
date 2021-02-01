package Productmanager.kafka;

import Productmanager.service.ProductService;
import com.google.gson.Gson;
import Productmanager.jsonModel.OrderCompleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerProducts {

    @Autowired
    ProductService service;
    @KafkaListener(topics = "${kafkaTopicOrders}", groupId = "${kafkaGroup}" )
    public void listenProductTopic(@Payload String message,@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){

        if(message!=null && key.compareTo("order_validation")!=0){
            OrderCompleted order = new Gson().fromJson(message, OrderCompleted.class);
            service.updateProductByOrder(order);
        }
    }
}
