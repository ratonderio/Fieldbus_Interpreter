package schenck;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class SchenckListCellRenderer extends JLabel implements ListCellRenderer<Object> {

  public SchenckListCellRenderer() {
    setOpaque(true);
  }

  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {

    FieldbusObject fieldbusObject = (FieldbusObject) value;

    setText(fieldbusObject.getDateTime().toString());

    Color background;
    Color foreground;

    JList.DropLocation dropLocation = list.getDropLocation();
    if (dropLocation != null
        && !dropLocation.isInsert()
        && dropLocation.getIndex() == index) {

      background = Color.BLUE;
      foreground = Color.WHITE;

    } else if (isSelected) {
      background = Color.BLUE;
      foreground = Color.WHITE;

    } else {
      background = new Color(255, 255, 255, 0);
      foreground = Color.BLACK;
    }

    setBackground(background);
    setForeground(foreground);

    return this;
  }
}
