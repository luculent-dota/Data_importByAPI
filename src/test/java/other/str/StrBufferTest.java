package other.str;

import org.testng.annotations.Test;

public class StrBufferTest {
    
  private StringBuffer strB = new StringBuffer();
  //线程不安全 但单例下 效率更高
  private StringBuilder strU = new StringBuilder();
  
  @Test
  public void testStringBuffer() {
      new Thread() {    //A
          public void run() {
              int i = 0;
              while (i++ < 20) {
        	  strB.append("aaaaa");
                  System.out.println(strB);
              }
          }
      }.start();
      new Thread() {    //B
          public void run() {
              int i = 0;
              while (i++ < 20) {
        	  strB.append("bbbbb");
                  System.out.println(strB);
              }
          }
      }.start();
  }
  
  @Test
  public void testStringBuilder() {
      System.err.println("--------------------");
      new Thread() {    //A
          public void run() {
              int i = 0;
              while (i++ < 20) {
        	  strU.append("aaaaa");
                  System.out.println(strU);
              }
          }
      }.start();
      new Thread() {    //B
          public void run() {
              int i = 0;
              while (i++ < 20) {
        	  strU.append("bbbbb");
                  System.out.println(strU);
              }
          }
      }.start();
  }
}
