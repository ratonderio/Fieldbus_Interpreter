package schenck;

import java.util.ArrayList;

public interface FieldbusObject {
  SchenckDateTime getDateTime();
  ArrayList<SchenckDataType> getFieldbusData();
  FieldbusDataPanel getDataPanel();
}
