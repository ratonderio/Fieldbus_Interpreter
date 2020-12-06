package schenck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;

//TODO Liberal with the getters to begin with;remove extraneous when done
public class BitEncoded implements SchenckDataType {

  private String name;
  private String value;
  private HashMap<String, String> internalBitNames = new HashMap<>();
  private JPanel valuePanel;
  private ArrayList<BitEncoded> byteList = new ArrayList<>();

  BitEncoded(String name, List<String> bitEncodedList) {
    this.name = name;
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

  public JPanel getValuePanel() {
    return valuePanel;
  }

  public void setValuePanel(JPanel valuePanel) {
    this.valuePanel = valuePanel;
  }

  public ArrayList<BitEncoded> getByteList() {
    return byteList;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  public HashMap<String, String> getInternalBitNames() {
    return internalBitNames;
  }


}
