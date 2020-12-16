package schenck;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

class SchenckTreeCellRenderer extends DefaultTreeCellRenderer {

  public SchenckTreeCellRenderer() {
  }

  public Component getTreeCellRendererComponent(
      JTree tree,
      Object value,
      boolean sel,
      boolean expanded,
      boolean leaf,
      int row,
      boolean hasFocus) {

    super.getTreeCellRendererComponent(
        tree, value, sel,
        expanded, leaf, row,
        hasFocus);

    if (leaf && value.getClass() == DefaultMutableTreeNode.class) {
      if (((DefaultMutableTreeNode) value).getUserObject().getClass() == FieldbusInput.class
          || ((DefaultMutableTreeNode) value).getUserObject().getClass() == FieldbusOutput.class) {
        FieldbusObject fieldbusObject = (FieldbusObject) ((DefaultMutableTreeNode) value)
            .getUserObject();
        setText(fieldbusObject.getDateTime().toString());
      }
    }

    return this;
  }
}
