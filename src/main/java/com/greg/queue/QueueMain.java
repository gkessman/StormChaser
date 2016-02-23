package com.greg.queue;

import java.util.HashMap;  

public class QueueMain {  
      
    public static void main(String[] argv) throws Exception {  
      
        final String QUEUE_NAME = "MY_QUEUE";  
      
        //Spawn Consumer Thread, which will always listening for the messages to be processed  
        Consumer consumer = new Consumer(QUEUE_NAME);  
        Thread consumerThread = new Thread(consumer);  
        consumerThread.start();  
      
        //Publishes msg in the queue  
        Producer producer = new Producer(QUEUE_NAME);  
      
        //Produce 100 msgs  
        for (int i = 0; i < 100; i++) {  
           HashMap message = new HashMap();  
           message.put("My Message", i);  
           producer.publishMessage(message);  
           System.out.println("Message #"+ i +" sent to Queue.");  
        }  
    }  
}  