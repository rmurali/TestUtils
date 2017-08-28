
 
import java.util.ArrayList;
import java.util.List;
 

import java.util.concurrent.CountDownLatch;
 

public class ThreadedTestHarness {
 
       private static class InvokerThread extends Thread {
 
              private CountDownLatch startSignal = null;
              private CountDownLatch stopSignal = null;
              private int numIterations = 0;
              private String identifier = null;

             
              public InvokerThread(CountDownLatch startSignal, CountDownLatch stopSignal, String identifier)
              {
                  this.startSignal = startSignal;
                  this.stopSignal = stopSignal;
                  this.identifier = identifier;
                  this.setName("Thread " + identifier);
                 
              }
             
              public void run()
              {
                     try {
                           startSignal.await();
                          
                           //do whatever individual worker thread needs to do here
                           System.out.println("Thread " + identifier + " counting down");
                          
                           stopSignal.countDown();
                          
                          
                     } catch (Exception e) {
                           e.printStackTrace();
                     }
                    
              }
             
       }
 
      
      
     
      
       
       public static void runSimulation() throws Exception
       {
             
          
           CountDownLatch stopSignal = new CountDownLatch(50);
           CountDownLatch startSignal = new CountDownLatch(1); 
      
 
           for(int i=1; i <= 50; i++) {
               new ThreadedTestHarness.InvokerThread(startSignal, stopSignal,  new Integer(i).toString()).start();
           }
 
           startSignal.countDown(); 
            stopSignal.await();  // wait until all threads are finished
         
             
       }
       
       public static void main(String[] args)
       {
       
          try
          { 
             ThreadedTestHarness testHarness = new ThreadedTestHarness();
             testHarness.runSimulation();
          }
          catch (Exception e)
          {
             e.printStackTrace(); 
            
          }   
           
       }
      
      
      
 
}