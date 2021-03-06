package schenck;

import javax.swing.JPanel;

public class IEEE754 implements SchenckDataType {

  private String name;
  private String value;
  private JPanel valuePanel;

  IEEE754(IEEE754 ieee754) {
    this.name = ieee754.name;
    this.value = ieee754.value;
    this.valuePanel = ieee754.valuePanel;
  }

  IEEE754(String collatedName) {
    value = collatedName.substring(0, 4);
    name = collatedName.substring(4).strip();
  }

  @Override
  public String toString() {
    return "IEEE754{" +
        "name='" + name + '\'' +
        ", value='" + value + '\'' +
        '}';
  }

  public String getName() {
    return name;
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
}
