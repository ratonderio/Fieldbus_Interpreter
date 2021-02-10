package schenck;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DataTypeValuePanel extends JPanel {

  DataTypeValuePanel(SchenckDataType schenckDataType, boolean littleEndian, boolean wordSwapped) {
    String dataTypeName = schenckDataType.getName();

    setLayout(new GridBagLayout());
    setBorder(new TitledBorder(dataTypeName));

    if (schenckDataType.getClass() == IEEE754.class) {
      String IEEE754Value = DataHelper.formatData(schenckDataType, littleEndian, wordSwapped);
      GUI.addItem(this, new JLabel(IEEE754Value), 0, 0);
    } else if (schenckDataType.getClass() == EncodedInteger.class) {
      EncodedInteger encodedInteger = (EncodedInteger) schenckDataType;

      ArrayList<String> intNamesList = new ArrayList<>(
          encodedInteger.getOrderedIntegerNames());
      intNamesList.addAll(encodedInteger.getByteList().get(0).getOrderedIntegerNames());

      String encodedIntegerValue = schenckDataType.getValue();
      String[] encodedIntegerSplit = encodedIntegerValue.split("(?<=\\G.{2})");
      if (!littleEndian) {
        encodedIntegerSplit = new String[]{
            encodedIntegerSplit[1],
            encodedIntegerSplit[0],
            encodedIntegerSplit[3],
            encodedIntegerSplit[2]
        };
      }
      int i = 0;
      for (String integerName : intNamesList) {
        GUI.addItem(this, new JLabel(integerName), 0, i, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        GUI.addItem(this, new JLabel(String.valueOf(Integer.parseInt(encodedIntegerSplit[i], 16))),
            1, i);
        i++;
      }

    } else if (schenckDataType.getClass() == BitEncoded.class) {
      BitEncoded bitEncoded = (BitEncoded) schenckDataType;

      if (bitEncoded.getName().contains("Status 52")) {
        createStatus5253(bitEncoded, littleEndian);
        return;
      }

      String bitEncodedValue = DataHelper.formatData(bitEncoded, littleEndian, wordSwapped);
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
      if (bitEncoded.getName().contains("Command 10")) {
        JLabel label = new JLabel(
            "Ingredient No. " + Integer.parseInt(bitEncodedValue.substring(16, 24), 2));
        label.setForeground(Color.GREEN);
        GUI.addItem((Container) getComponent(2), label, 0, 8);
      }
    }
  }

  void createStatus5253(BitEncoded bitEncoded, boolean littleEndian) {

    String[] statusNames = {"Parameter Number", "Parameter Block Number", "Event Number Offset",
        "Unused", "Acknowledged", "Event Class", "Event Group", "Event Number"};
    ArrayList<String> bitEncodedArray = DataHelper.toStatus5253(bitEncoded, littleEndian);

    int i = 0;

    for (int k = 7; k >= 0; k--) {
      GUI.addItem(this, new JLabel(statusNames[k]), 0, i);
      GUI.addItem(this, new JLabel(bitEncodedArray.get(k)), 1, i);
      i++;
    }
    if (bitEncodedArray.get(2).equals("1")) {
      bitEncodedArray.set(7, String.valueOf(Integer.parseInt(bitEncodedArray.get(7) + 16)));
    }
    String eventString =
        bitEncodedArray.get(5) + " - " + bitEncodedArray.get(6) + bitEncodedArray.get(7);
    String paramString = "P" + bitEncodedArray.get(1) + "." + bitEncodedArray.get(0);

    GUI.addItem(this, new JLabel("Event:"), 0, 8);
    GUI.addItem(this, new JLabel(eventString), 1, 8);
    GUI.addItem(this, new JLabel("Parameter:"), 0, 9);
    GUI.addItem(this, new JLabel(paramString), 1, 9);
  }
}
