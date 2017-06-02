package other.atom;

import java.util.concurrent.atomic.AtomicLong;

import org.testng.annotations.Test;

public class NumAtom {
    
  private int num =0;
  
  private AtomicLong numAtom = new AtomicLong(0l);
  
  @Test
  public void numTest() {
     for(int ii=0;ii<5;ii++){
	 new Thread() {    //A
	          public void run() {
	              int i = 0;
	              while (i++ < 1000) {
	        	  num++;
	        	  numAtom.incrementAndGet();
	              }
	          }
	      }.start();
     }
      try {
	Thread.sleep(5000);
    } catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
    }
      System.err.println(num);
      System.err.println(numAtom.get());
  }
}
