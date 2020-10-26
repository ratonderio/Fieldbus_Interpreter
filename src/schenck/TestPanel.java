package schenck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TestPanel extends JPanel {

  JButton button1 = new JButton("Test"), button2 = new JButton("Test"), button3 = new JButton(
      "Test"), button4 = new JButton("Test");

  TestPanel() {
    setLayout(new GridBagLayout());
    setBorder(new TitledBorder("FBFB"));
    GUI.addItem(this, button1, 0, 0, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button2, 0, 1, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button3, 0, 2, 1, 1, GridBagConstraints.CENTER, 2);
    GUI.addItem(this, button4, 0, 3, 1, 1, GridBagConstraints.CENTER, 2);
  }

}
