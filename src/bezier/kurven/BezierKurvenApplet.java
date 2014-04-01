package bezier.kurven;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JApplet;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
//import javax.swing.JTextField;

/**
 *
 * @author Elias Schorr
 */
public class BezierKurvenApplet extends JApplet {

    //Variablen
    private int bezierKurvenzaehler = 0; //zählt die erstellten Punkte
//    private JPanel  menu; //zum anzeigen der Punkte auf dem Bildschirm
    //speichern der unterschiedlichen Kurven
    private BezierKurvenBerechnung bezierKurven[] = new BezierKurvenBerechnung[10];
//    JComboBox PunktAuswahl;
//    JButton verschieben;
//    JLabel lP, lXKord, lYKord;
//    JTextField eingabeX, eingabeY;
    //Objekte
    private JPanel hintergrund;
    private MouseEvent event; //zum spiechern des Mouse events

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        initObjekte();//erstellen der Objekte
//        initListener();//ertellen der Listener
        createPopupMenu();//ertellen eines Mausklick Menüs
        this.setSize(1000, 600);//setzten der Grösse
        //estellen eriner neuen BezierKurve
        bezierKurven[bezierKurvenzaehler] = new BezierKurvenBerechnung();
    }

    private void initListener() {
//        PunktAuswahl.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent arg0) {
//                int index = PunktAuswahl.getSelectedIndex();
//                try {
//                    eingabeX.setText("" + punkte[0][index].x);
//                    eingabeY.setText("" + punkte[0][index].y);
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, "Error ", "Bézier Kurven", JOptionPane.WARNING_MESSAGE);
//                }
//            }
//        });
//        verschieben.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int index = PunktAuswahl.getSelectedIndex();
//                punkte[0][index].x = Integer.parseInt(eingabeX.getText());
//                punkte[0][index].y = Integer.parseInt(eingabeY.getText());
//                hintergrund.repaint();
//                resetKordinatenPunkte();
//                try {
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, "Error ", "Bézier Kurven", JOptionPane.WARNING_MESSAGE);
//                }
//
//            }
//        });
    }

    private void initObjekte() {
        Container contentPane = getContentPane();//Container erstellen
        contentPane.setLayout(new BorderLayout());//Layout des Containers bestimmen
//        //Objekterstellung
//        menu = new JPanel();
//        lP = new JLabel("Punkt:");
//        PunktAuswahl = new JComboBox();
//        lXKord = new JLabel("X-Kord.");
//        eingabeX = new JTextField();
//        eingabeX.setPreferredSize(new Dimension(40, 20));
//        lYKord = new JLabel("Y-Kord.");
//        eingabeY = new JTextField();
//        eingabeY.setPreferredSize(new Dimension(40, 20));
//        verschieben = new JButton("Punkt verschieben");
//        //Objekte zum menu hinzufügen
//        contentPane.add(menu,BorderLayout.NORTH);
//        menu.add(lP);
//        menu.add(PunktAuswahl);
//        menu.add(lXKord);
//        menu.add(eingabeX);
//        menu.add(lYKord);
//        menu.add(eingabeY);
//        menu.add(verschieben);
        //Erstellung eines Hintergrund und Positionierung
        hintergrund = new DrawPanel();
        contentPane.add(hintergrund, BorderLayout.CENTER);
        //Eigenschaften des Hintergrundes
        hintergrund.setBackground(Color.white);
        //InfoLabel
        JLabel info = new JLabel("Pro Kurve können maximal 30 Punkte übergeben werden. Es können max 10 Kurven erstellt werden. Die Punkte können mit der Maus verschobenwerden");
        hintergrund.add(info);
    }

    private void createPopupMenu() {
        JMenuItem menuNeuerPunkt, menuKurve, menuReset, menuKurveerweitern;//Die Menü-unter-Punkte
        //Create the popup menu.
        JPopupMenu popup = new JPopupMenu();
        menuNeuerPunkt = new JMenuItem("Einen neuen Punkt erzeugen");
        menuNeuerPunkt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                //auslesen der Position der Maus
                Point p = event.getPoint();
                //übergeben der Daten
                bezierKurven[bezierKurvenzaehler].setPunkt(p);
                ResetKordinatenPunkte();
            }
        });
        popup.add(menuNeuerPunkt);
        menuKurve = new JMenuItem("Bézier Kurve zeichnen");
        menuKurve.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                bezierKurven[bezierKurvenzaehler].Punkterechnen();
            }
        });
        popup.add(menuKurve);
        menuKurveerweitern = new JMenuItem("Bézier Kurve erweitern");
        menuKurveerweitern.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (bezierKurven[bezierKurvenzaehler].getPunktezaehler() < 2) {
                    JOptionPane.showMessageDialog(null, "Sie müssen zuerst min. 3 Punkte erstellen um eine neue Kurve zu erstellen. ", "Bézier Kurven", JOptionPane.WARNING_MESSAGE);
                } else {
                    bezierKurvenzaehler++;//Erstellen einer neuen Kurve
                    bezierKurven[bezierKurvenzaehler] = new BezierKurvenBerechnung();
                    Point punkte[]=bezierKurven[bezierKurvenzaehler-1].getEingabePunkte();
                    int zaehler=bezierKurven[bezierKurvenzaehler-1].getPunktezaehler();
                    bezierKurven[bezierKurvenzaehler].setPunkt(punkte[zaehler-1]);
                }
            }
        });
        popup.add(menuKurveerweitern);
        menuReset = new JMenuItem("Reset");
        menuReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                ResetHintergrundPunkte();
            }
        });
        popup.add(menuReset);
        //Add listener to the text area so the popup menu can come up.
        MouseListener popupListener = new PopupListener(popup);
        hintergrund.addMouseListener(popupListener);
    }

    private void ResetHintergrundPunkte() {
        bezierKurven[bezierKurvenzaehler].removeHintergrundPunkte();
    }

    private void ResetBersetKurve() {
        bezierKurven[bezierKurvenzaehler].resetBersetKurve();
    }

    private void ResetKordinatenPunkte() {
        bezierKurven[bezierKurvenzaehler].removeHintergrundPunkte();
    }

    //erstellen eines neuen Punktes auf Knopfdruck 
    private void getMouseLocation() {
        //auslesen der Position der Maus
        Point p = event.getPoint();
        //übergeben der Daten
        bezierKurven[bezierKurvenzaehler].setPunkt(p);
        ResetKordinatenPunkte();
    }

    private class BezierKurvenBerechnung {

        //Variablen
        private int punkteZaehler = 0;
        private Point eingabePunkte[] = new Point[30];
        private Point[] kurvenPunkte = new Point[30]; //speichert die Punkte der Bérset Kurve
        private JPanel kordPunkt[] = new JPanel[30];

        //setzten der Punkte
        public void setPunkt(Point p) {
            //es werden die Punkte mit den Mauskordinaten gesetzt, sofern es noch Platz hat
            try {
                eingabePunkte[punkteZaehler] = new Point(p);
                kordPunkt[punkteZaehler] = new JPanel();
                kordPunkt[punkteZaehler].setSize(10, 10);
                kordPunkt[punkteZaehler].setBackground(Color.red);
                kordPunkt[punkteZaehler].setName("" + punkteZaehler);
                hintergrund.add(kordPunkt[punkteZaehler]);
                hintergrund.repaint();
                kordPunkt[punkteZaehler].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent event) {
                    }
                });
                kordPunkt[punkteZaehler].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent event) {
                        //beim Losslassen der  Maus wird die aktuelle Mausposition in die Liste und das Panel geschrieben, anschliessend wird der Hintergrund neu ausgegeben.
                        JPanel h = (JPanel) event.getSource();
                        h.setLocation(getMousePosition());
                        int a = Integer.parseInt(h.getName());
                        eingabePunkte[a] = h.getLocation();
                        hintergrund.repaint();
                    }
                });
//                PunktAuswahl.addItem("" + punkteZaehler);
                kordPunkt[punkteZaehler].setLocation(p);
                punkteZaehler++;//bei einem erfolgreichen durchgang wird der punkteZaehler erhöht
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Es können keine weiteren Punkte erstellt werden ", "Bézier Kurven", JOptionPane.WARNING_MESSAGE);
            }
        }

        //Methode zum berechnen der Bésier Punkte
        public void Punkterechnen() {
            //Kurve wird zurückgessezt
            resetBersetKurve();
            //punkte werden in ein neues Arry uebertragen
            Point punkte[][] = new Point[30][30];//speichert die Punkte
            for (int i = 0; i < eingabePunkte.length; i++) {
                punkte[0][i] = eingabePunkte[i];
            }
            //Startpunkt der Besier Kurvee wird gesetzt
            kurvenPunkte[0] = punkte[0][0];
            //Schleife die verschiedene t für die Berechnung setzt
            for (double t = 0.05; t < 1; t += 0.05) {
                //schleifen die alle Punkte ausrechnet
                for (int index = 0; index < punkte.length; index++) {
                    for (int i = 0; i < punkte.length - 1; i++) {
                        //wenn der nachfolgenende Punkt leer ist wird abgebrochen
                        if (punkte[index][i + 1] == null) {
                            break;
                        }
                        //Berechnung des Punktes mit der Hilfe von t Speicherung in dem nächsten punkte Array
                        double x1 = ((1 - t) * punkte[index][i].x) + (t * punkte[index][i + 1].x);
                        double y1 = ((1 - t) * punkte[index][i].y) + (t * punkte[index][i + 1].y);
                        int x2 = (int) x1;
                        int y2 = (int) y1;
                        punkte[index + 1][i] = new Point(x2, y2);
                    }
                    //sobald der letze Punkt für t errechnet wurde wird dieser in das nächste freie Feld von kurvenPunkte geschrieben
                    if (punkte[index][1] == null) {
                        for (int a = 0; a < kurvenPunkte.length; a++) {
                            if (kurvenPunkte[a] == null) {
                                kurvenPunkte[a] = punkte[index][0];
                                break;
                            }
                        }
                    }
                }
            }
            //Endpunkt der Bésier Kurve wird in das letzte freie Feld geseetzt
            for (int a = 0; a < kurvenPunkte.length; a++) {
                if (kurvenPunkte[a] == null) {
                    kurvenPunkte[a] = punkte[0][punkteZaehler - 1];
                }
            }
            hintergrund.repaint();
        }

        public Point[] getEingabePunkte() {
            return eingabePunkte;
        }

        public int getPunktezaehler() {
            return punkteZaehler;
        }

        public JPanel[] getKordPunkte() {
            return kordPunkt;
        }

        public void resetBersetKurve() {
            //Reset der Bersét-Kurve
            for (int index = 0; index < kurvenPunkte.length; index++) {
                kurvenPunkte[index] = null;
            }
        }

        public void removeHintergrundPunkte() {
            //Reset der Variablen und des Hintergrundes
            for (int index = 0; index < 30; index++) {
                try {
                    hintergrund.remove(kordPunkt[index]);
                } catch (Exception ex) {
                    break;
                }
            }
            hintergrund.repaint();
        }

        private void resetKordinatenPunkte() {
            for (int index = 0; index < kordPunkt.length; index++) {
                try {
                    kordPunkt[index].setLocation(eingabePunkte[index]);
                } catch (Exception ex) {
                    break;
                }
            }
        }
    }

//ersteellen der Popup Klasse
    class PopupListener extends MouseAdapter {

        JPopupMenu popup;//PopupMenü Variable

        PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
            event = e;
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
    class DrawPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int index = 0;
            //zeichnen der Normalen Punkte und dessen verbindne durch Linien.
            g.setColor(Color.red);
            for (int i = 0; i < punkte.length - 1; i++) {
                if (punkte[index][i + 1] == null) {
                    break;
                }
                g.drawLine(punkte[index][i].x, punkte[index][i].y, punkte[index][i + 1].x, punkte[index][i + 1].y);
            }
            //zeichnen der Bésier-Kurve mit dne errechneten Punkten.
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
