package schenck;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

//TODO Clean up GUI
class GUI extends JFrame {

  public static final Font TITLEFONT = new Font("TitleFont", Font.BOLD, 20);

  DataLoader dataLoader = new DataLoader();
  JPanel optionsPanel = new JPanel(new GridBagLayout()), dataDisplayPanel = new JPanel(
      new GridBagLayout()), optionsSubPanel = new JPanel(new GridBagLayout());
  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel,
      dataDisplayPanel);
  JButton selectFile = new JButton("Select File");
  JTree fieldbusTree = new JTree(new DefaultMutableTreeNode("Fieldbus Capture"));
  String[] disoTypeChoices = {"Disocont Tersus (VCU)",
      "Disocont Classic (VSE)"}, wordSequenceChoices = {"I:std/L:std", "I:swp/L:std", "I:std/L:swp",
      "I:swp/L:swp"}, byteSequenceChoices = {"High - Low", "Low - High"}, softwareTypeChoices = {
      "VBW", "VDB", "VDD", "VKD", "VLW", "VMD"};

  JComboBox<String> disoType = new JComboBox<>(disoTypeChoices), wordSequenceType = new JComboBox<>(
      wordSequenceChoices), byteSequenceType = new JComboBox<>(
      byteSequenceChoices), softwareType = new JComboBox<>(softwareTypeChoices);

  GUI() {

    {
      setTitle("Fieldbus Interpreter");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationByPlatform(true);
      setSize(new Dimension(1280, 720));
      setMinimumSize(new Dimension(800, 600));
      setResizable(true);
    }

    optionsPanel.setBorder(new TitledBorder("Fieldbus Capture"));
    selectFile.addActionListener(EventListener -> chooseFile());

    fieldbusTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    fieldbusTree.setCellRenderer(new SchenckTreeCellRenderer());
    fieldbusTree.addTreeSelectionListener(this::updateDataPanel);

    JScrollPane listScrollPane = new JScrollPane(fieldbusTree);

    addItem(optionsSubPanel, new JLabel("Controller:"), 0, 0, 1, 1, GridBagConstraints.WEST,
        GridBagConstraints.HORIZONTAL);
    disoType.addActionListener(EventListener -> toggleVisibility());
    addItem(optionsSubPanel, disoType, 1, 0, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL);
    addItem(optionsSubPanel, softwareType, 2, 0, 1, 1, GridBagConstraints.EAST,
        GridBagConstraints.HORIZONTAL);
    softwareType.setVisible(false);

    addItem(optionsSubPanel, new JLabel("Word Sequence:"), 0, 1, 1, 1, GridBagConstraints.WEST,
        GridBagConstraints.HORIZONTAL);
    addItem(optionsSubPanel, wordSequenceType, 1, 1, 1, 1, GridBagConstraints.EAST,
        GridBagConstraints.HORIZONTAL);

    addItem(optionsSubPanel, new JLabel("Byte Sequence:"), 0, 2, 1, 1, GridBagConstraints.WEST,
        GridBagConstraints.HORIZONTAL);
    addItem(optionsSubPanel, byteSequenceType, 1, 2, 1, 1, GridBagConstraints.EAST,
        GridBagConstraints.HORIZONTAL);

    addItem(optionsSubPanel, selectFile, 1, 3, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL);

    addItem(optionsPanel, optionsSubPanel, 0, 0);

    addItem(optionsPanel, listScrollPane, 0, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH);

    splitPane.setDividerLocation(400);
    add(splitPane);

    setVisible(true);
  }

  public static void addItem(Container mainPanel, JComponent component, int gridx, int gridy) {
    int width = 1;
    int height = 1;
    addItem(mainPanel, component, gridx, gridy, width, height);
  }

  public static void addItem(Container mainPanel, JComponent component, int gridx, int gridy,
      int width, int height) {
    int align = GridBagConstraints.CENTER;
    int fill = GridBagConstraints.BOTH;
    addItem(mainPanel, component, gridx, gridy, width, height, align, fill);
  }

  public static void addItem(Container mainPanel, JComponent component, int gridx, int gridy,
      int width, int height, int align, int fill) {
    Insets insets = new Insets(5, 5, 5, 5);
    addItem(mainPanel, component, gridx, gridy, width, height, align, fill, insets);
  }

  public static void addItem(Container mainPanel, JComponent component, int gridx, int gridy,
      int width, int height, int align, int fill, Insets insets) {

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    constraints.gridwidth = width;
    constraints.gridheight = height;
    constraints.weightx = 100.0;
    constraints.weighty = 100.0;
    constraints.anchor = align;
    constraints.fill = fill;
    constraints.insets = insets;
    mainPanel.add(component, constraints);
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
      boolean littleEndian = Objects.equals(byteSequenceType.getSelectedItem(), "Low - High");
      boolean wordSwapped =
          Objects.equals(wordSequenceType.getSelectedItem(), "I:swp/L:std") || Objects
              .equals(wordSequenceType.getSelectedItem(), "I:swp/L:swp");
      while (scanner.hasNextLine()) {
        String scanline = scanner.nextLine();
        if (Objects.equals(disoType.getSelectedItem(), "Disocont Classic (VSE)")) {
          FieldbusObject fieldbusObject = FieldbusParser.parseDisocontVSE(scanline, dataLoader,
              String.valueOf(softwareType.getSelectedItem()), littleEndian, wordSwapped);
          if (fieldbusObject != null) {
            fieldbusObjects.add(fieldbusObject);
          }
        } else if (Objects.equals(disoType.getSelectedItem(), "Disocont Tersus (VCU)")) {
          FieldbusObject fieldbusObject = FieldbusParser
              .parseDisocontVCU(scanline, dataLoader, littleEndian, wordSwapped);
          if (fieldbusObject != null) {
            fieldbusObjects.add(fieldbusObject);
          }
        }
      }
      scanner.close();

      DefaultMutableTreeNode root = (DefaultMutableTreeNode) fieldbusTree.getModel().getRoot();
      buildTree(root, fieldbusObjects);

    } catch (IOException ex) {
      JOptionPane
          .showMessageDialog(this, "No file was selected or the selected file was not found.",
              "No File Selected", JOptionPane.INFORMATION_MESSAGE);
    } catch (NullPointerException ex) {
      JOptionPane.showMessageDialog(this, "Database selection cancelled.", "Cancel",
          JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void toggleVisibility() {
    softwareType.setVisible(Objects.equals(disoType.getSelectedItem(), "Disocont Classic (VSE)"));
  }

  void buildTree(DefaultMutableTreeNode root, ArrayList<FieldbusObject> fieldbusObjects) {
    root.removeAllChildren();
    DefaultTreeModel model = (DefaultTreeModel) fieldbusTree.getModel();
    DefaultMutableTreeNode inputsNode = new DefaultMutableTreeNode("Inputs");
    DefaultMutableTreeNode outputsNode = new DefaultMutableTreeNode("Outputs");
    root.add(inputsNode);
    root.add(outputsNode);
    for (FieldbusObject fbObject : fieldbusObjects) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(fbObject);
      if (fbObject.getClass() == FieldbusInput.class) {
        inputsNode.add(node);
      } else if (fbObject.getClass() == FieldbusOutput.class) {
        outputsNode.add(node);
      }
    }
    model.reload();
  }

  void updateDataPanel(TreeSelectionEvent e) {

    DefaultMutableTreeNode node = (DefaultMutableTreeNode) fieldbusTree
        .getLastSelectedPathComponent();

    if (node != null && node.getUserObject() != null) {
      Object fieldbusObject = node.getUserObject();

      if (fieldbusObject.getClass() == FieldbusInput.class
          || fieldbusObject.getClass() == FieldbusOutput.class) {
        dataDisplayPanel.removeAll();
        addItem(dataDisplayPanel, ((FieldbusObject) fieldbusObject).getDataPanel(), 0, 0, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH);
      }
    }
    this.repaint();
    this.revalidate();
  }
}
