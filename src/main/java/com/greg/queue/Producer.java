package com.greg.queue;

import java.io.IOException;  
import java.util.HashMap;  
  
import org.apache.commons.lang3.SerializationUtils;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class Producer extends MessageQueueEndPoint {  
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);  
      
    public Producer(String queueName) {  
        super(queueName);  
    }  
      
    public void publishMessage(HashMap<String, Integer> msgMap) {  
        try {  
            channel.basicPublish("", endPointName, null, SerializationUtils.serialize(msgMap));  
        } catch (IOException e) {  
            logger.error("Error connecting to Queue." , e);  
        }  
    }  
}