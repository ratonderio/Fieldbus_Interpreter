package schenck;

import java.util.HashMap;
import java.util.List;

//TODO Liberal with the getters to begin with;remove extraneous when done
public class EncodedInteger implements SchenckDataType {

  private final String name;
  private String value;
  private final List<String> encodedIntegerList;
  private final HashMap<String, String> internalIntegerNames = new HashMap<>();

  EncodedInteger(String name, List<String> encodedIntegerList) {
    this.name = name;
    this.encodedIntegerList = encodedIntegerList;
    int first = 0;
    String hexCode, rest;
    for (String intVals : encodedIntegerList) {
      hexCode = intVals.substring(0, 4);
      rest = intVals.substring(4).strip();
      if (first < 1) {
        first++;
        value = hexCode;
      }
      internalIntegerNames.put(hexCode, rest);
    }
  }

  @Override
  public String toString() {
    return "EncodedInteger{" +
        "name='" + name + '\'' +
        ", encodedIntegerList=" + encodedIntegerList +
        '}';
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public List<String> getEncodedIntegerList() {
    return encodedIntegerList;
  }

  public HashMap<String, String> getInternalIntegerNames() {
    return internalIntegerNames;
  }
}
