package bezier.kurven;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Elias Schorr
 */
public class BezierKurven {

    //variables
    private int bezierKurvenzaehler = 0; //zählt die erstellten Punkte
    private final BezierKurvenBerechnung bezierKurven[];
    private JPanel hintergrund;
    private MouseEvent klickEvent; //zum spiechern des Mouse events
    protected DefFrame parentFrame;
    protected BezierKurvenApplet parentApplet;

    /**
     * Initialization method that will be called after the class is loaded into
     * the Frame.
     *
     * @param parentFrame
     */
    public BezierKurven(DefFrame parentFrame) {
        this.bezierKurven = new BezierKurvenBerechnung[30];
        this.parentFrame = parentFrame;
        init();
    }

    /**
     * Initialization method that will be called after the class is loaded into
     * the Applet.
     *
     * @param parentApplet
     */
    public BezierKurven(BezierKurvenApplet parentApplet) {
        this.bezierKurven = new BezierKurvenBerechnung[30];
        this.parentApplet = parentApplet;
        init();
    }

    private void init() {
        initObjekte();//erstellen der Objekte
        createPopupMenu();//ertellen eines Mausklick Menüs
//        this.setSize(1000, 600);//setzten der Grösse
        //estellen eriner neuen BezierKurve
        bezierKurven[bezierKurvenzaehler] = new BezierKurvenBerechnung(true, null, this);
    }

    public void start() {
        this.refreshPanels();
    }

    private void initObjekte() {
        Container contentPane;
        if (parentFrame != null) {
            contentPane = parentFrame.getContentPane();//Container erstellen
        } else {
            contentPane = parentApplet.getContentPane();//Container erstellen
        }
        contentPane.setLayout(new BorderLayout());//Layout des Containers bestimmen
        //Erstellung eines Hintergrund und Positionierung
        hintergrund = new DrawPanel();
        contentPane.add(hintergrund, BorderLayout.CENTER);
        //Eigenschaften des Hintergrundes
        hintergrund.setBackground(Color.lightGray);
        //InfoLabel +"sollten die Kordinatenpunkte sich einmal verschoben haben können sie <Panels refresh> auswählen"
        JLabel info = new JLabel("Pro Kurve k\u00F6nnen maximal 30 Punkte \u00FCbergeben werden. Es k\u00F6nnen max 30 Kurven erstellt werden. Die Punkte k\u00F6nnen mit der Maus verschoben werden. ");
        hintergrund.add(info);
    }

    private void createPopupMenu() {
        JMenuItem menuNeuerPunkt, menuReset, menuKurveerweitern, menuPanelRefresh;//Die Menü-unter-Punkte
        //Create the popup menu.
        JPopupMenu popup = new JPopupMenu();
        menuNeuerPunkt = new JMenuItem("Einen neuen Punkt erzeugen");
        menuNeuerPunkt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                //auslesen der Position der Maus
                Point p = klickEvent.getPoint();
                //übergeben der Daten
                bezierKurven[bezierKurvenzaehler].setPunkt(p);
                resetKordinatenPunkte();
            }
        });
        popup.add(menuNeuerPunkt);
        menuKurveerweitern = new JMenuItem("B\u00E9zier Kurve erweitern");
        menuKurveerweitern.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                erweiternKurve();
            }
        });
        popup.add(menuKurveerweitern);
        menuPanelRefresh = new JMenuItem("Panels refresh");
        menuPanelRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                refreshPanels();
            }
        });
        popup.add(menuPanelRefresh);

        menuReset = new JMenuItem("Reset");
        menuReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                resetAll();
            }
        });
        popup.add(menuReset);
        //Add listener to the text area so the popup menu can come up.
        MouseListener popupListener = new PopupListener(popup);
        hintergrund.addMouseListener(popupListener);
    }

    private void resetAll() {
        for (int i = 0; i <= bezierKurvenzaehler; i++) {
            try {
                bezierKurven[i].removeHintergrundPunkte();
                bezierKurven[i] = null;
            } catch (Exception ex) {
                break;
            }
        }
        bezierKurvenzaehler = 0;
        hintergrund.repaint();
        bezierKurven[bezierKurvenzaehler] = new BezierKurvenBerechnung(true, null, this);

    }

    private void resetKordinatenPunkte() {
        bezierKurven[bezierKurvenzaehler].resetKordinatenPunkte();
    }

    public void refreshPanels() {
        for (int i = 0; i <= bezierKurvenzaehler; i++) {
            bezierKurven[i].refreshPanels();
        }
    }

    private void erweiternKurve() {
        if (bezierKurven[bezierKurvenzaehler].getPunktezaehler() < 4) {
            JOptionPane.showMessageDialog(null, "Sie m\u00FCssen zuerst weitere Punkte erstellen um eine neue Kurve zu erstellen. ", "B\u00E9zier Kurven", JOptionPane.WARNING_MESSAGE);
        } else {
            bezierKurvenzaehler++;//Erstellen einer neuen Kurve
            bezierKurven[bezierKurvenzaehler] = new BezierKurvenBerechnung(false, bezierKurven[bezierKurvenzaehler - 1], this);
            bezierKurven[bezierKurvenzaehler - 1].setChildKlasse(bezierKurven[bezierKurvenzaehler]);
        }
    }

    public void addPunkt(JPanel addObject) {
        hintergrund.add(addObject);
    }

    public void removePunkt(JPanel removeObject) {
        hintergrund.remove(removeObject);
    }

    public void repaintHintergrund() {
        hintergrund.repaint();
    }

//ersteellen der Popup Klasse
    private class PopupListener extends MouseAdapter {

        JPopupMenu popup;//PopupMenü Variable

        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
            klickEvent = e;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

    //Paint Klasse
    private class DrawPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //für alle Abschnitte
            for (int pz = 0; pz <= bezierKurvenzaehler; pz++) {
                if (bezierKurven[pz] == null) {
                    break;
                }
                //zeichnen der Normalen Punkte und dessen verbindne durch Linien.
                g.setColor(Color.red);
                Point punkte[] = bezierKurven[pz].getEingabePunkte();
                for (int i = 0; i < punkte.length - 1; i++) {
                    if (punkte[i + 1] == null) {
                        break;
                    }
                    g.drawLine(punkte[i].x, punkte[i].y, punkte[i + 1].x, punkte[i + 1].y);
                }
                //zeichnen der Bésier-Kurve mit den errechneten Punkten.
                Point kurve[] = bezierKurven[pz].getkurvenPunkte();
                g.setColor(Color.blue);
                for (int a = 0; a < kurve.length - 1; a++) {
                    if (kurve[a] == null) {
                        break;
                    }
                    g.drawLine(kurve[a].x, kurve[a].y, kurve[a + 1].x, kurve[a + 1].y);
                }
            }
        }
    }
}
