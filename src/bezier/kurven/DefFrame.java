package bezier.kurven;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * creates an JFrame with the given height, width and titel. The Frame is not
 * Resizable, it has an battlefield icon and it disposes on close.
 *
 * @author Elias
 */
public class DefFrame extends javax.swing.JFrame {

    /**
     * creates an new JFrame with the given dates
     *
     * @param width
     * @param height
     * @param titel
     */
    public DefFrame(int width, int height, String titel) {
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setResizable(false);
        setTitle(titel);
        Image icon = new ImageIcon(this.getClass().getResource("battlefield.png")).getImage();
        setIconImage(icon);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}
