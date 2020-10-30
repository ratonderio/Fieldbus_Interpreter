package schenck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DataPanel extends JPanel {

  String[] test = {"02F0","0310","0610","0750","0752","0760","0762","0766","0330","0754","075E","07A8","0768","076A","0650","0770"};

  //TODO This works with test string; GUI needs to pass the actual list in now
  DataPanel(HashMap<String,SchenckDataType> dataLoader) {
    setLayout(new GridBagLayout());
    setBorder(new TitledBorder("FBFB"));
    int i = 0;
    for(String name: test){
      SchenckDataType intermediate = dataLoader.get(name.toLowerCase());
      String fullName = intermediate.getName();
      GUI.addItem(this, new JButton(fullName),0,i,1,1, GridBagConstraints.CENTER,2);
      i++;
    }

/*    GUI.addItem(this, button1, 0, 0, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button2, 0, 1, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button3, 0, 2, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button4, 0, 3, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button5, 0, 4, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button6, 0, 5, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button7, 0, 6, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button8, 0, 7, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button01, 1, 0, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button02, 1, 1, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button03, 1, 2, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button04, 1, 3, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button05, 1, 4, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button06, 1, 5, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button07, 1, 6, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button08, 1, 7, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button09, 1, 8, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button10, 1, 9, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button11, 1, 10, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button12, 1, 11, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button13, 1, 12, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button14, 1, 13, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button15, 1, 14, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button16, 1, 15, 1, 1, GridBagConstraints.CENTER, 2);*/
  }

}
