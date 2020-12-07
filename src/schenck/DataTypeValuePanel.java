package schenck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DataTypeValuePanel extends JPanel {

  DataTypeValuePanel(SchenckDataType schenckDataType) {
    setLayout(new GridBagLayout());
    setBorder(new TitledBorder(schenckDataType.getName()));
    if (schenckDataType.getClass() == IEEE754.class) {
      String IEEE754Value = DataHelper.toLittleEndian(schenckDataType);
      GUI.addItem(this, new JLabel(IEEE754Value), 0, 0, 1, 1,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    } else if (schenckDataType.getClass() == EncodedInteger.class) {
      EncodedInteger encodedInteger = (EncodedInteger) schenckDataType;

      ArrayList<String> intNamesList = new ArrayList<>(
          encodedInteger.getInternalIntegerNames().values());
      intNamesList.addAll(encodedInteger.getByteList().get(0).getInternalIntegerNames().values());

      String encodedIntegerValue = schenckDataType.getValue();
      String[] encodedIntegerSplit = encodedIntegerValue.split("(?<=\\G.{2})");

      int i = 0;
      for (String integerName : intNamesList) {
        GUI.addItem(this, new JLabel(integerName), 0, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        GUI.addItem(this, new JLabel(String.valueOf(Integer.parseInt(encodedIntegerSplit[i], 16))),
            1, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        i++;
      }
    } else if (schenckDataType.getClass() == BitEncoded.class) {
      BitEncoded bitEncoded = (BitEncoded) schenckDataType;

      ArrayList<String> firstBitNamesList = new ArrayList<>(
          bitEncoded.getInternalBitNames().values());
      firstBitNamesList.addAll(bitEncoded.getByteList().get(0).getInternalBitNames().values());

      ArrayList<String> secondBitNamesList = new ArrayList<>(
          bitEncoded.getByteList().get(1).getInternalBitNames().values());
      secondBitNamesList.addAll(bitEncoded.getByteList().get(2).getInternalBitNames().values());

      Collections.sort(firstBitNamesList);
      Collections.sort(secondBitNamesList);

      String bitEncodedValue = DataHelper.toLittleEndian(bitEncoded);
      String[] bitEncodedArray = bitEncodedValue.split("");

      int i = 0;
      for (String bitName : firstBitNamesList) {
        GUI.addItem(this, new JLabel(bitName), 0, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        GUI.addItem(this, new JLabel(bitEncodedArray[i]), 1, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        i++;
      }

      for (String bitName : secondBitNamesList) {
        GUI.addItem(this, new JLabel(bitName), 0, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        GUI.addItem(this, new JLabel(bitEncodedArray[i]), 1, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        i++;
      }
    }
  }
}
