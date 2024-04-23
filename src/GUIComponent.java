import javax.swing.*;
import java.awt.*;

abstract class GUIComponent extends JPanel {
    protected void setLayout(int rows, int cols, int hgap, int vgap) {
        setLayout(new GridLayout(rows, cols, hgap, vgap));
    }

    protected void addComponent(Component component) {
        add(component);
    }
}