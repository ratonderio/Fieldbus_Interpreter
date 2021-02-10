package schenck;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

//TODO Liberal with the getters to begin with;remove extraneous when done
public class EncodedInteger implements SchenckDataType {

  private String name;
  private String value;
  private JPanel valuePanel;
  private ArrayList<EncodedInteger> byteList = new ArrayList<>();
  private ArrayList<String> orderedIntegerNames = new ArrayList<>();

  EncodedInteger(EncodedInteger encodedInteger) {
    this.name = encodedInteger.name;
    this.value = encodedInteger.value;
    this.valuePanel = encodedInteger.valuePanel;
    this.byteList = encodedInteger.byteList;
    this.orderedIntegerNames = encodedInteger.orderedIntegerNames;
  }

  EncodedInteger(String name, List<String> encodedIntegerList) {
    this.name = name;
    int first = 0;
    String hexCode, rest;
    for (String intVals : encodedIntegerList) {
      hexCode = intVals.substring(0, 4);
      rest = intVals.substring(4).strip();
      if (first < 1) {
        first++;
        value = hexCode;
      }
      orderedIntegerNames.add(rest);
    }
  }

  @Override
  public String toString() {
    return "EncodedInteger{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", byteList=" + byteList +
        ", orderedIntegerNames=" + orderedIntegerNames +
        '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  public JPanel getValuePanel() {
    return valuePanel;
  }

  public void setValuePanel(JPanel valuePanel) {
    this.valuePanel = valuePanel;
  }

  public ArrayList<EncodedInteger> getByteList() {
    return byteList;
  }

  public ArrayList<String> getOrderedIntegerNames() {
    return orderedIntegerNames;
  }
}
