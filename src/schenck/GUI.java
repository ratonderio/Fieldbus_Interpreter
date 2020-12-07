package schenck;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

//TODO Clean up GUI
class GUI extends JFrame {

  DataLoader dataLoader = new DataLoader();
  JPanel optionsPanel = new JPanel(new GridBagLayout()), dataDisplayPanel = new JPanel(
      new GridBagLayout());
  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel,
      dataDisplayPanel);
  JButton selectFile = new JButton("Select File");
  JList<FieldbusObject> list;
  DefaultListModel<FieldbusObject> listModel = new DefaultListModel<>();

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

    list = new JList<>(listModel);
    list.setCellRenderer(new SchenckListCellRenderer());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setSelectedIndex(0);
    list.setVisibleRowCount(20);
    list.addListSelectionListener(this::updateDataPanel);

    JScrollPane listScrollPane = new JScrollPane(list);

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

      Scanner scanner = new Scanner(file);
      ArrayList<FieldbusObject> fieldbusObjects = new ArrayList<>();
      while (scanner.hasNextLine()) {
        String scanline = scanner.nextLine();
        if (scanline.contains("{")) {
          FieldbusInput input = new FieldbusInput(scanline, dataLoader);
          FieldbusDataPanel fieldbusDataPanel = new FieldbusDataPanel(input.getFieldbusData());
          input.setDataPanel(fieldbusDataPanel);
          fieldbusObjects.add(input);
        } else if (scanline.contains("}")) {
          FieldbusOutput output = new FieldbusOutput(scanline, dataLoader);
          FieldbusDataPanel fieldbusDataPanel = new FieldbusDataPanel(output.getFieldbusData());
          output.setDataPanel(fieldbusDataPanel);
          fieldbusObjects.add(output);
        }
      }
      scanner.close();

      listModel.clear();
      listModel.addAll(fieldbusObjects);

    } catch (IOException ex) {
      JOptionPane
          .showMessageDialog(this, "No file was selected or the selected file was not found.",
              "No File Selected", JOptionPane.INFORMATION_MESSAGE);
    } catch (NullPointerException ex) {
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


  void updateDataPanel(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      dataDisplayPanel.removeAll();
      FieldbusObject fieldbusObject = list.getSelectedValue();
      addItem(dataDisplayPanel, fieldbusObject.getDataPanel(), 0, 0, 1, 1,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH);
      this.repaint();
      this.revalidate();
    }
  }
}
