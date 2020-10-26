package schenck;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

//TODO Make an actual GUI
class GUI extends JFrame {

  JPanel optionsPanel = new JPanel(new GridBagLayout()), dataPanel = new JPanel(
      new GridBagLayout()), testPanel = new TestPanel();
  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, dataPanel);
  JButton selectFile = new JButton("Select File");
  JList list;
  DefaultListModel listModel;

  GUI() {

    {
      setTitle("Fieldbus Interpreter");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationByPlatform(true);
      setSize(new Dimension(1280, 720));
      setMinimumSize(new Dimension(800, 600));
      setResizable(true);
    }

    optionsPanel.setBorder(new TitledBorder("FBFB"));

    selectFile.addActionListener(EventListener -> chooseFile());

    listModel = new DefaultListModel();
    listModel.addElement("Jane Doe");
    listModel.addElement("John Smith");
    listModel.addElement("Kathy Green");
    listModel.addElement("Test");
    listModel.addElement("Test");
    listModel.addElement("Test");
    listModel.addElement("Test");

    list = new JList(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    list.setVisibleRowCount(20);
    JScrollPane listScrollPane = new JScrollPane(list);

    addItem(dataPanel, testPanel, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    addItem(optionsPanel, selectFile, 0, 0, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL);
    addItem(optionsPanel, listScrollPane, 0, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH);

    add(splitPane);

    setVisible(true);
  }

  private void chooseFile() {
    JFileChooser fileChooser = new JFileChooser(".");
    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Document", "txt");
    fileChooser.setFileFilter(fileFilter);
    fileChooser.showOpenDialog(this);

    try {
      File file = fileChooser.getSelectedFile();
      String fileName = fileChooser.getName(file);
      Scanner scanner = new Scanner(file);
      scanner.useDelimiter(" ");

      scanner.close();
      this.repaint();
      this.revalidate();
    } catch (IOException ex) {
      JOptionPane
          .showMessageDialog(this, "No file was selected or the selected file was not found.",
              "No File Selected", JOptionPane.INFORMATION_MESSAGE);
      chooseFile();
    } catch (NullPointerException ex) {
      System.out.println(Arrays.toString(ex.getStackTrace()));
      JOptionPane.showMessageDialog(this, "Database selection cancelled.", "Cancel",
          JOptionPane.INFORMATION_MESSAGE);
    }

  }

  public static void addItem(Container mainPanel, JComponent component, int gridx, int gridy,
      int width,
      int height, int align, int fill) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    constraints.gridwidth = width;
    constraints.gridheight = height;
    constraints.weightx = 100.0;
    constraints.weighty = 100.0;
    constraints.insets = new Insets(5, 5, 5, 5);
    constraints.anchor = align;
    constraints.fill = fill;
    mainPanel.add(component, constraints);
  }

}
