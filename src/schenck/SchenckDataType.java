package schenck;

import javax.swing.JPanel;

public interface SchenckDataType {

  String getName();

  void setValue(String value);

  String getValue();

  void setValuePanel(JPanel valuePanel);

}
