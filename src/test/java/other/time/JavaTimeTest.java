package other.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.testng.annotations.Test;
/**
 * 
 *@Description:java8新增time包
 *包内类型为不可变的 线程安全
 *@Author:zhangyang
 *@Since:2017年6月2日上午11:19:25
 */
public class JavaTimeTest {
    
  @Test(description="本地日期")
  public void testLocalDate() {
      LocalDate today = LocalDate.now();
      LocalDate alozon = LocalDate.of(1922, 6, 14);
      alozon = LocalDate.of(2007, Month.JANUARY, 11);
      String formatter = DateTimeFormatter.ISO_LOCAL_DATE.format(alozon);
      System.out.println(formatter);
  }
  
  @Test(description="日期调整")
  public void testTemporalAdjusters(){
      LocalDate firstTuesday = LocalDate.of(1922, 6, 14);
      
      String formatter = DateTimeFormatter.ISO_LOCAL_DATE.format(firstTuesday);
      System.out.println(formatter);
      LocalDate nextTuesday = firstTuesday.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
      formatter = DateTimeFormatter.ISO_LOCAL_DATE.format(nextTuesday);
      System.out.println(formatter);
  }
  
  @Test(description="本地时间")
  public void testLocalTime(){
      LocalTime bedtime = LocalTime.of(22, 30);
      LocalTime wakeup = bedtime.plusHours(8);
      String formatter = DateTimeFormatter.ISO_LOCAL_TIME.format(wakeup);
      System.out.println(formatter);
     
      
  }
  
  @Test(description="时区时间")
  public void testZoneTime(){
      ZonedDateTime apollolllaunch = ZonedDateTime.of(1969,7,16,9,32,0,0, ZoneId.of("America/New_York"));
      String formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(apollolllaunch);
      System.out.println(formatter);
  }
  
  @Test(description="本地日期+时间")
  public void testLocalDateTime(){
      LocalDateTime nows = LocalDateTime.now();
      String formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(nows);
      System.out.println(formatter);
      try {
		Thread.sleep(5000);
	        } catch (InterruptedException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	        }
      LocalDateTime nextTime = LocalDateTime.now();
      formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(nows);
      System.out.println(formatter);
      //时间差
      System.out.println( Duration.between(nows, nextTime).toMillis());
      
      
  }
  
  @Test(description="日期格式化")
  public void testFormatter(){
      LocalDateTime nows = LocalDateTime.now();
      String formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(nows);
      System.out.println(formatter);
      nows = LocalDateTime.parse("2017-06-05 10:21:32",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(nows);
      System.out.println(formatter);
  }
  
  @Test(description="与Date转换")
  public void testToDate(){
      Instant now = Instant.now();
      Date nowDate = Date.from(now);
      Instant now2 = nowDate.toInstant();
  }
  
  
  
  
}
