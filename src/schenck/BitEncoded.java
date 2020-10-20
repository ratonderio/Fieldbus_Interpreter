package schenck;

import java.util.HashMap;
import java.util.List;

//TODO Liberal with the getters to begin with;remove extraneous when done
public class BitEncoded implements SchenckDataType {

  private String name;
  private String value;
  private List<String> bitEncodedList;
  private HashMap<String, String> internalBitNames = new HashMap<>();

  BitEncoded(String name, List<String> bitEncodedList) {
    this.name = name;
    this.bitEncodedList = bitEncodedList;
    int first = 0;
    String hexCode, rest;
    for (String bitVals : bitEncodedList) {
      hexCode = bitVals.substring(0, 4);
      rest = bitVals.substring(4).strip();
      if (first < 1) {
        first++;
        value = hexCode;
      }
      internalBitNames.put(hexCode, rest);
    }
  }

  @Override
  public String toString() {
    return "BitEncoded{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", internalBitNames=" + internalBitNames +
        '}';
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public List<String> getBitEncodedList() {
    return bitEncodedList;
  }

  public HashMap<String, String> getInternalBitNames() {
    return internalBitNames;
  }
}
