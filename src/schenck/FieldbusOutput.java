package schenck;

import java.util.ArrayList;

public class FieldbusOutput implements FieldbusObject {

  private SchenckDateTime dateTime;
  private ArrayList<SchenckDataType> disoOutputs;
  private FieldbusDataPanel fieldbusDataPanel;

  FieldbusOutput(String data, DataLoader dataLoader) {
    dateTime = new SchenckDateTime(data.substring(0, data.indexOf("}")).strip(),
        data.substring(data.indexOf("H ") + 1, data.indexOf("ms")).strip());
    disoOutputs = new ArrayList<>();

    String dataString = data.substring(data.indexOf("ms") + 2).strip();
    String[] dataSplit = dataString.split("[()]");

    for (int i = 0; i < dataSplit.length; i++) {
      dataSplit[i] = dataSplit[i].strip();
    }
    for (int i = 1; i < dataSplit.length; i += 2) {
      SchenckDataType schenckDataType = dataLoader.getSchenckDataTypeHashMap()
          .get(dataSplit[i].toLowerCase());
      SchenckDataType schenckDataTypeCopy = null;
      if (BitEncoded.class.equals(schenckDataType.getClass())) {
        schenckDataTypeCopy = new BitEncoded((BitEncoded) schenckDataType);
      } else if (IEEE754.class.equals(schenckDataType.getClass())) {
        schenckDataTypeCopy = new IEEE754((IEEE754) schenckDataType);
      } else if (EncodedInteger.class.equals(schenckDataType.getClass())) {
        schenckDataTypeCopy = new EncodedInteger((EncodedInteger) schenckDataType);
      }
      assert schenckDataTypeCopy != null;
      schenckDataTypeCopy.setValue(dataSplit[i + 1]);
      disoOutputs.add(schenckDataTypeCopy);
    }
  }

  @Override
  public String toString() {
    return "FieldbusOutput{" +
        "dateTime=" + dateTime +
        ", disoOutputs=" + disoOutputs +
        '}';
  }

  public SchenckDateTime getDateTime() {
    return dateTime;
  }

  public ArrayList<SchenckDataType> getFieldbusData() {
    return disoOutputs;
  }

  @Override
  public FieldbusDataPanel getDataPanel() {
    return fieldbusDataPanel;
  }

  public void setDataPanel(FieldbusDataPanel fieldbusDataPanel) {
    this.fieldbusDataPanel = fieldbusDataPanel;
  }
}
