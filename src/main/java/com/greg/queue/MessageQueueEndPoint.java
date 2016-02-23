package com.greg.queue;

import java.io.IOException;  

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
  
/** 
 * Creates a connection to the Queue 
 *  
 */  
public abstract class MessageQueueEndPoint {  
    protected Connection connection;  
    protected Channel channel;  
    protected String endPointName;  
   
    private static final Logger logger = LoggerFactory.getLogger(MessageQueueEndPoint.class);  
  
    public MessageQueueEndPoint(String queueName) {  
        this.endPointName = queueName;  
    
        //Create a connection factory  
        ConnectionFactory factory = new ConnectionFactory();  
          
        //Replace with the correct connection uri  
        String uri = "amqp://userName:password@hostName:portNumber/virtualHost";   
        try {  
            factory.setUri(uri);  
          
            //getting a connection  
            connection = factory.newConnection();  
       
            //creating a channel  
            channel = connection.createChannel();  
        
            //declaring a queue for this channel. If queue does not exist, it will be created on the server.  
            //durability (second param) is also set as TRUE (the queue will survive a server restart)  
            channel.queueDeclare(queueName, true, false, false, null);  
        } catch (Exception e) {  
            logger.error("Error connecting to MQ Server.", e);  
        }  
    }  
   
   
    /** 
     * Closes the Queue Connection. This is not needed to be called explicitly as connection closure happens implicitly anyways. 
     * @throws IOException 
     */  
     public void close() throws IOException{  
         this.connection.close(); //closing connection, closes all the open channels  
     }  
   
     public int getCurrentMessageCount() throws IOException {  
         return getChannel().queueDeclarePassive(this.endPointName).getMessageCount();  
     }  
}  