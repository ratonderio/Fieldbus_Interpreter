package schenck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class FieldbusDataPanel extends JPanel {

  FieldbusDataPanel(ArrayList<SchenckDataType> schenckDataTypes) {
    setLayout(new GridBagLayout());
    setBorder(new TitledBorder("FBFB"));
    int i = 0;
    for (SchenckDataType dataType : schenckDataTypes) {
      String fullName = dataType.getName();
      JButton jButton = new JButton(fullName);
      jButton.addActionListener(
          e -> JOptionPane.showMessageDialog(this, new DataTypeValuePanel(dataType)));
      GUI.addItem(this, jButton, 0, i, 1, 1, GridBagConstraints.CENTER, 2);
      i++;
    }
  }
}
