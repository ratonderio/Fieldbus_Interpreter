package schenck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class FieldbusDataPanel extends JPanel {

  FieldbusDataPanel(ArrayList<SchenckDataType> schenckDataTypes, boolean inputBorder) {
    setLayout(new GridBagLayout());
    setBorder(new TitledBorder(inputBorder ? "Fieldbus Input" : "Fieldbus Output"));
    JLabel nameLabel = new JLabel("Name"), valueLabel = new JLabel("Value");
    nameLabel.setFont(GUI.TITLEFONT);
    valueLabel.setFont(GUI.TITLEFONT);
    GUI.addItem(this, nameLabel, 0, 0, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, valueLabel, 1, 0, 1, 1, GridBagConstraints.CENTER, 2);
    int i = 1;
    for (SchenckDataType dataType : schenckDataTypes) {
      JButton jButton = new JButton(dataType.getValue());
      jButton.addActionListener(
          e -> JOptionPane
              .showMessageDialog(this, new DataTypeValuePanel(dataType), "Interpreted Value",
                  JOptionPane.INFORMATION_MESSAGE));
      GUI.addItem(this, new JLabel(dataType.getName()), 0, i, 1, 1, GridBagConstraints.CENTER, 2);
      GUI.addItem(this, jButton, 1, i, 1, 1, GridBagConstraints.CENTER, 2);
      i++;

    }
  }
}
