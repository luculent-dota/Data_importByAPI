package other.enumm;

import org.testng.annotations.Test;

public class EnumTest {
  @Test
  public void testName() {
      System.out.println(EnumSmipleTest.FRI.name());
  }
  
  private void switchEnum(EnumSmipleTest test){
      switch(test){
      case MON:
	  System.out.println(EnumSmipleTest.MON.name());
	  break;
      case FRI:
	  System.out.println(EnumSmipleTest.FRI.name());
	  break;
    default:
	System.out.println("other");
	break;
     
      }
  }
  
  @Test
  public void testSwitch(){
      switchEnum(EnumSmipleTest.MON);
  }
  
  @Test
  public void testName2() {
      System.out.println(EnumCompTest.MAN.name());
      System.out.println(EnumCompTest.WOMAN.getVal());
  }
}
