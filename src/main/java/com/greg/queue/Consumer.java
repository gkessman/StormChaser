package com.greg.queue;

import java.io.IOException;
import java.util.HashMap;  

import org.apache.commons.lang3.SerializationUtils;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.QueueingConsumer;  
  
public class Consumer extends MessageQueueEndPoint implements Runnable {  
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);  
      
    public Consumer(String queueName) {  
        super(queueName);  
    }  
      
    public void run() {  
       QueueingConsumer consumer = new QueueingConsumer(channel);  
       //start consuming messages. Auto acknowledge messages.  
       channel.basicConsume(endPointName, true, consumer);  
    
       while (true) { //keep listening for the message  
          try {  
             HashMap<String, Integer> msgMap = consumeMessage(consumer);     
             logger.info("Message #" + msgMap.get("My Message") + " received from Queue.");  
          } catch (IOException e) {  
             logger.error("Error connecting to Queue." , e);  
          }  
       }  
    }  
  
    /** 
     * Blocking method, return only when something is available from the Queue  
     * @return 
     * @throws Exception 
     */  
    private HashMap<String, Integer> consumeMessage(QueueingConsumer consumer) throws IOException {  
        QueueingConsumer.Delivery delivery = consumer.nextDelivery(); //blocking call  
        return (HashMap<String, Integer>) SerializationUtils.deserialize(delivery.getBody());  
    }  
}  