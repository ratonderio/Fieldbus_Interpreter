package schenck;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

class GUI extends JFrame {
  private JPanel mainPanel = new JPanel(new GridBagLayout());



  GUI(){
    setTitle("Fieldbus Interpreter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationByPlatform(true);
    setMinimumSize(new Dimension(600,800));
    setResizable(true);

    mainPanel.setBorder(new TitledBorder("Poop"));
    add(mainPanel);
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
          .showMessageDialog(this, "No file was selected or the selected file was not found.", "No File Selected", JOptionPane.INFORMATION_MESSAGE);
      chooseFile();
    } catch (NullPointerException ex) {
      System.out.println(Arrays.toString(ex.getStackTrace()));
      JOptionPane.showMessageDialog(this, "Database selection cancelled.", "Cancel", JOptionPane.INFORMATION_MESSAGE);
    }

  }

}
