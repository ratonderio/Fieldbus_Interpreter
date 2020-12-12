package schenck;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class BitEncoded implements SchenckDataType {

  private String name, value;
  private JPanel valuePanel;
  private ArrayList<BitEncoded> byteList = new ArrayList<>();
  private ArrayList<String> orderedBitNames = new ArrayList<>();

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
      orderedBitNames.add(rest);
    }
  }

  @Override
  public String toString() {
    return "BitEncoded{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", byteList=" + byteList +
        ", orderedBitNames=" + orderedBitNames +
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

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  public ArrayList<String> getOrderedBitNames() {
    return orderedBitNames;
  }
}
