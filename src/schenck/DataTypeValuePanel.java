package schenck;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DataTypeValuePanel extends JPanel {

  DataTypeValuePanel(SchenckDataType schenckDataType) {
    String dataTypeName = schenckDataType.getName();

    setLayout(new GridBagLayout());
    setBorder(new TitledBorder(dataTypeName));

    if (schenckDataType.getClass() == IEEE754.class) {
      String IEEE754Value = DataHelper.toLittleEndian(schenckDataType);
      GUI.addItem(this, new JLabel(IEEE754Value), 0, 0, 1, 1,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    } else if (schenckDataType.getClass() == EncodedInteger.class) {
      EncodedInteger encodedInteger = (EncodedInteger) schenckDataType;

      ArrayList<String> intNamesList = new ArrayList<>(
          encodedInteger.getOrderedIntegerNames());
      intNamesList.addAll(encodedInteger.getByteList().get(0).getOrderedIntegerNames());

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

      String bitEncodedValue = DataHelper.toLittleEndian(bitEncoded);
      String[] bitEncodedArray = bitEncodedValue.split("");

      int i = 0;
      for (int k = -1; k <= 2; k++) {
        int j = 0;
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(LineBorder.createGrayLineBorder());
        if (k < 0) {
          for (String bitName : bitEncoded.getOrderedBitNames()) {
            JLabel nameLabel = new JLabel(bitName);
            JLabel valueLabel = new JLabel(bitEncodedArray[i]);
            if (bitEncodedArray[i].equals("1")) {
              nameLabel.setForeground(Color.GREEN);
              valueLabel.setForeground(Color.GREEN);
            }
            GUI.addItem(panel, nameLabel, 0, j);
            GUI.addItem(panel, valueLabel, 1, j);
            i++;
            j++;
          }
        } else {
          for (String bitName : bitEncoded.getByteList().get(k).getOrderedBitNames()) {
            JLabel nameLabel = new JLabel(bitName);
            JLabel valueLabel = new JLabel(bitEncodedArray[i]);
            if (bitEncodedArray[i].equals("1")) {
              nameLabel.setForeground(Color.GREEN);
              valueLabel.setForeground(Color.GREEN);
            }
            GUI.addItem(panel, nameLabel, 0, j);
            GUI.addItem(panel, valueLabel, 1, j);
            i++;
            j++;
          }
        }
        GUI.addItem(this, panel, k + 1, 0);
      }
    }
  }

//  void createCommand1011(BitEncoded bitEncoded) {
//
//    ArrayList<String> firstBitNamesList = new ArrayList<>(
//        bitEncoded.getInternalBitNames().values());
//    firstBitNamesList.addAll(bitEncoded.getByteList().get(0).getInternalBitNames().values());
//
//    ArrayList<String> secondBitNamesList = new ArrayList<>(
//        bitEncoded.getByteList().get(1).getInternalBitNames().values());
//    secondBitNamesList.addAll(bitEncoded.getByteList().get(2).getInternalBitNames().values());
//
//    Collections.sort(firstBitNamesList);
//    Collections.sort(secondBitNamesList);
//
//    String bitEncodedValue = DataHelper.toLittleEndian(bitEncoded);
//    String[] bitEncodedArray = bitEncodedValue.split("");
//
//    int i = 0;
//    for (String bitName : firstBitNamesList) {
//      GUI.addItem(this, new JLabel(bitName), 0, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      GUI.addItem(this, new JLabel(bitEncodedArray[i]), 1, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      i++;
//    }
//
//    for (String bitName : secondBitNamesList) {
//      GUI.addItem(this, new JLabel(bitName), 0, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      GUI.addItem(this, new JLabel(bitEncodedArray[i]), 1, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      i++;
//    }
//  }
//
//  void createStatus5253(BitEncoded bitEncoded) {
//
//    ArrayList<String> firstBitNamesList = new ArrayList<>(
//        bitEncoded.getInternalBitNames().values());
//    firstBitNamesList.addAll(bitEncoded.getByteList().get(0).getInternalBitNames().values());
//
//    ArrayList<String> secondBitNamesList = new ArrayList<>(
//        bitEncoded.getByteList().get(1).getInternalBitNames().values());
//    secondBitNamesList.addAll(bitEncoded.getByteList().get(2).getInternalBitNames().values());
//
//    Collections.sort(firstBitNamesList);
//    Collections.sort(secondBitNamesList);
//
//    String bitEncodedValue = DataHelper.toLittleEndian(bitEncoded);
//    String[] bitEncodedArray = bitEncodedValue.split("");
//
//    int i = 0;
//    for (String bitName : firstBitNamesList) {
//      GUI.addItem(this, new JLabel(bitName), 0, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      GUI.addItem(this, new JLabel(bitEncodedArray[i]), 1, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      i++;
//    }
//
//    for (String bitName : secondBitNamesList) {
//      GUI.addItem(this, new JLabel(bitName), 0, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      GUI.addItem(this, new JLabel(bitEncodedArray[i]), 1, i, 1, 1,
//          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
//      i++;
//    }
//  }
}
