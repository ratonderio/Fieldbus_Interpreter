package schenck;

import javax.swing.JPanel;

public interface SchenckDataType {

  String getName();

  String getValue();

  void setValue(String value);

  void setValuePanel(JPanel valuePanel);

}
