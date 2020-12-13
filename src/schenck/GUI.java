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
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
      new GridBagLayout());
  JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel,
      dataDisplayPanel);
  JButton selectFile = new JButton("Select File");

  JTree fieldbusTree = new JTree(new DefaultMutableTreeNode("Fieldbus Capture"));

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

    addItem(optionsPanel, selectFile, 0, 0, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL);
    addItem(optionsPanel, listScrollPane, 0, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH);

    splitPane.setDividerLocation(300);
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
          FieldbusDataPanel fieldbusDataPanel = new FieldbusDataPanel(input.getFieldbusData(),
              true);
          input.setDataPanel(fieldbusDataPanel);
          fieldbusObjects.add(input);
        } else if (scanline.contains("}")) {
          FieldbusOutput output = new FieldbusOutput(scanline, dataLoader);
          FieldbusDataPanel fieldbusDataPanel = new FieldbusDataPanel(output.getFieldbusData(),
              false);
          output.setDataPanel(fieldbusDataPanel);
          fieldbusObjects.add(output);
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
