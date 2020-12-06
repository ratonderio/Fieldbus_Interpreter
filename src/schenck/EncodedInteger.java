package schenck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;

//TODO Liberal with the getters to begin with;remove extraneous when done
public class EncodedInteger implements SchenckDataType {

  private String name;
  private String value;
  private HashMap<String, String> internalIntegerNames = new HashMap<>();
  private JPanel valuePanel;
  private ArrayList<EncodedInteger> byteList = new ArrayList<>();

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
      internalIntegerNames.put(hexCode, rest);
    }
  }

  @Override
  public String toString() {
    return "EncodedInteger{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", internalIntegerNames=" + internalIntegerNames +
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

  public ArrayList<EncodedInteger> getByteList() {
    return byteList;
  }

  public void setValuePanel(JPanel valuePanel) {
    this.valuePanel = valuePanel;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  public HashMap<String, String> getInternalIntegerNames() {
    return internalIntegerNames;
  }
}
