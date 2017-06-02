package other.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

public class MapTest {
  
   private final AtomicInteger num=new AtomicInteger(0);
  
  @Test(threadPoolSize=1,invocationCount=5)
  public void hashMapTest() {
      Map<String,String> hashMap = new HashMap<String,String>();
      
      for (int s = 0;s<10;s++){
	  new Thread() {    //A
	          public void run() {
	              int i = 0;
	              while (i++ < 80) {
	        	  int current = num.incrementAndGet();
	        	  hashMap.put("thead"+current, "ssss");
	              }
	          }
	      }.start();
      }
      try {
	Thread.sleep(6000);
    } catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
      System.err.println(hashMap.size());
      
  }
  
  
  @Test(threadPoolSize=1,invocationCount=5)
  public void concurrenthashMapTest() {
      Map<String,String> hashMap = new ConcurrentHashMap<String,String>();
      
      for (int s = 0;s<10;s++){
	  new Thread() {    //A
	          public void run() {
	              int i = 0;
	              while (i++ < 80) {
	        	  int current = num.incrementAndGet();
	        	  hashMap.put("thead"+current, "ssss");
	              }
	          }
	      }.start();
      }
      try {
	Thread.sleep(6000);
    } catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
      System.err.println(hashMap.size());
      
  }
}
