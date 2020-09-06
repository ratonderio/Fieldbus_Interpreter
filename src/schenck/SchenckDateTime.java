package schenck;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class SchenckDateTime {
  TemporalAccessor currentTime;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:m:s a");
  String currentMillis;

  SchenckDateTime(String currentTime, String currentMillis){
    this.currentTime = formatter.parse(currentTime);
    this.currentMillis = currentMillis;
  }

}
