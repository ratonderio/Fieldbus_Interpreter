package schenck;

public class SchenckDateTime {

  String currentTime;
  String currentMillis;

  SchenckDateTime(String currentTime, String currentMillis) {
    this.currentTime = currentTime;
    this.currentMillis = currentMillis + " ms";
  }

  @Override
  public String toString() {
    return currentTime + " " + currentMillis;
  }
}
