package schenck;

import java.util.ArrayList;

public class FieldbusInput implements FieldbusObject {

  private SchenckDateTime dateTime;
  private ArrayList<SchenckDataType> disoInputs;
  private FieldbusDataPanel fieldbusDataPanel;

  FieldbusInput(String data, DataLoader dataLoader) {
    dateTime = new SchenckDateTime(data.substring(0, 21), data.substring(30, 35));
    disoInputs = new ArrayList<>();

    String dataString = data.substring(40);
    String[] dataSplit = dataString.split("[()]");

    for (int i = 0; i < dataSplit.length; i++) {
      dataSplit[i] = dataSplit[i].strip();
    }
    for (int i = 1; i < dataSplit.length; i += 2) {
      SchenckDataType schenckDataType = dataLoader.getSchenckDataTypeHashMap()
          .get(dataSplit[i].toLowerCase());
      schenckDataType.setValue(dataSplit[i + 1]);
      disoInputs.add(schenckDataType);
    }
  }

  @Override
  public String toString() {
    return "FieldbusInput{" +
        "dateTime=" + dateTime +
        ", disoInputs=" + disoInputs +
        '}';
  }

  public SchenckDateTime getDateTime() {
    return dateTime;
  }

  public ArrayList<SchenckDataType> getFieldbusData() {
    return disoInputs;
  }

  public FieldbusDataPanel getDataPanel() {
    return fieldbusDataPanel;
  }

  public void setDataPanel(FieldbusDataPanel fieldbusDataPanel) {
    this.fieldbusDataPanel = fieldbusDataPanel;
  }
}
