package bezier.kurven;

import javax.swing.JApplet;

/**
 * 
 * @author Elias
 */
public class BezierKurvenApplet extends JApplet {

    BezierKurven appletData;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        appletData = new BezierKurven(this);
    }
}
