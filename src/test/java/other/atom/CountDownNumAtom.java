package other.atom;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.testng.annotations.Test;

public class CountDownNumAtom {
    private int num =0;
    
    private AtomicLong numAtom = new AtomicLong(0l);
    
    @Test
    public void numTest() {
	CountDownLatch cdl = new CountDownLatch(5);
       for(int ii=0;ii<5;ii++){
  	 new Thread() {    //A
  	          public void run() {
  	              int i = 0;
  	              while (i++ < 1000) {
  	        	  num++;
  	        	  numAtom.incrementAndGet();
  	              }
  	            cdl.countDown();
  	          }
  	      }.start();
       }
       try {
	cdl.await();
    } catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
        System.err.println(num);
        System.err.println(numAtom.get());
    }
}
