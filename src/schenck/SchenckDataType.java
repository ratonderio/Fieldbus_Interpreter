package schenck;

//TODO Interface, only reason it is an interface is to put it in a map...is there a better way?

import javax.swing.JPanel;

public interface SchenckDataType {
  String getName();
  void setValue(String value);
  String getValue();
  void setValuePanel(JPanel valuePanel);

}
