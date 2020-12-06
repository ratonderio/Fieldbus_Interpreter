package schenck;

//TODO Decide if actually useful for GUI; might be useful for labels
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
