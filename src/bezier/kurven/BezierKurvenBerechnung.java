package bezier.kurven;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Elias
 */
public class BezierKurvenBerechnung {

    //Variablen
    private int punkteZaehler = 0;
    private final Point eingabePunkte[];
    private final Point kurvenPunkte[]; //speichert die Punkte der Bérset Kurve
    private final JPanel kordPunkt[];
    private final BezierKurvenBerechnung parent;
    private BezierKurvenBerechnung child;
    private boolean erster;
    private final BezierKurven parentClass;

    public BezierKurvenBerechnung(boolean erster, BezierKurvenBerechnung parent, BezierKurven parentClass) {
        this.kordPunkt = new JPanel[30];
        this.eingabePunkte = new Point[30];
        this.kurvenPunkte = new Point[30];
        this.erster = erster;
        this.parent = parent;
        this.parentClass = parentClass;
        if (erster == false) {
            JPanel panels[] = parent.getKordPunkte();
            Point punkte[] = parent.getEingabePunkte();
            int zaehler = parent.getPunktezaehler();

            kordPunkt[punkteZaehler] = panels[zaehler - 1];
            setPunkt(punkte[zaehler - 1]);
            int xVersch = (punkte[zaehler - 1].x - punkte[zaehler - 2].x);
            int yVersch = (punkte[zaehler - 1].y - punkte[zaehler - 2].y);
            xVersch = punkte[zaehler - 1].x + xVersch;
            yVersch = punkte[zaehler - 1].y + yVersch;
            Point p = new Point(xVersch, yVersch);
            setPunkt(p);
            erster = true;
            this.parentClass.repaintHintergrund();
        }
    }

    public void setChildKlasse(BezierKurvenBerechnung child) {
        this.child = child;
    }

    //setzten der Punkte
    public final void setPunkt(Point p) {
        //es werden die Punkte mit den Mauskordinaten gesetzt, sofern es noch Platz hat
        try {
            eingabePunkte[punkteZaehler] = new Point(p);
            if (erster == true) {
                kordPunkt[punkteZaehler] = new JPanel();
                kordPunkt[punkteZaehler].setSize(10, 10);
                kordPunkt[punkteZaehler].setBackground(Color.red);
                kordPunkt[punkteZaehler].setName("" + punkteZaehler);
                parentClass.addPunkt(kordPunkt[punkteZaehler]);
                parentClass.repaintHintergrund();
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
                        if (parentClass.parentFrame != null) {
                            punktverschieben(h, parentClass.parentFrame.getMousePosition());
                        } else {
                            punktverschieben(h, parentClass.parentApplet.getMousePosition());
                        }
                    }
                });
                kordPunkt[punkteZaehler].setLocation(p);
            } else {
                erster = true;
            }
            punkteZaehler++;//bei einem erfolgreichen durchgang wird der punkteZaehler erhöht
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Es k\u00F6nnen keine weiteren Punkte erstellt werden.", "B\u00E9zier Kurven", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void punktverschieben(JPanel punkt, Point neuePosition) {
        Point pv = punkt.getLocation();
        if (parentClass.parentFrame != null) {
            punkt.setLocation(parentClass.parentFrame.getMousePosition());
        } else {
            punkt.setLocation(parentClass.parentApplet.getMousePosition());
        }
        Point pn = punkt.getLocation();
        int xVersch = (pv.x - pn.x);
        int yVersch = (pv.y - pn.y);
        int a = Integer.parseInt(punkt.getName());
        eingabePunkte[a] = punkt.getLocation();
        try {
            if (kordPunkt[1].equals(punkt) && parent != null) {
                Point punkte[] = parent.getEingabePunkte();
                int zaehler = parent.getPunktezaehler();
                Point p = new Point(punkte[zaehler - 2].x + xVersch, punkte[zaehler - 2].y + yVersch);
                parent.setKordPunkte(zaehler - 2, p);
            }
            if (kordPunkt[punkteZaehler - 2].equals(punkt) && child != null) {
                Point punkte[] = child.getEingabePunkte();
                Point p = new Point(punkte[1].x + xVersch, punkte[1].y + yVersch);
                child.setKordPunkte(1, p);
            }
            if (kordPunkt[punkteZaehler - 1].equals(punkt) && child != null) {
                Point p = new Point(eingabePunkte[punkteZaehler - 2].x - xVersch, eingabePunkte[punkteZaehler - 2].y - yVersch);
                this.setKordPunkte(punkteZaehler - 2, p);
                child.setPosition(neuePosition);
                Point punkte[] = child.getEingabePunkte();
                Point p2 = new Point(punkte[1].x - xVersch, punkte[1].y - yVersch);
                child.setKordPunkte(1, p2);
            }
        } catch (Exception ex) {
            //System.out.println("error");
        }
        parentClass.repaintHintergrund();
    }

    public void setKordPunkte(int zaehler, Point neuePosition) {
        eingabePunkte[zaehler] = neuePosition;
        kordPunkt[zaehler].setLocation(neuePosition);
        parentClass.repaintHintergrund();
    }

    public void setPosition(Point pn) {
        eingabePunkte[0] = pn;
    }

    public void setPanel(Point neuePosition) {

    }

    //Methode zum berechnen der Bésier Punkte
    public void Punkterechnen() {
        //Kurve wird zurückgessezt
        resetBersetKurve();
        //punkte werden in ein neues Arry uebertragen
        Point punkte[][] = new Point[30][30];//speichert die Punkte
        System.arraycopy(eingabePunkte, 0, punkte[0], 0, eingabePunkte.length);
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
        //Endpunkt der Bésier Kurve wird in das letzte freie Feld gesetzt
        for (int a = 0; a < kurvenPunkte.length; a++) {
            if (kurvenPunkte[a] == null) {
                kurvenPunkte[a] = punkte[0][punkteZaehler - 1];
            }
        }
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

    public Point[] getkurvenPunkte() {
        if (punkteZaehler > 1) {
            Punkterechnen();
        }
        return kurvenPunkte;
    }

    public void resetBersetKurve() {
        //Reset der Bersét-Kurve
        for (int index = 0; index < kurvenPunkte.length; index++) {
            kurvenPunkte[index] = null;
        }
    }

    public void refreshPanels() {
        for (int i = 0; i < punkteZaehler; i++) {
            kordPunkt[i].setLocation(eingabePunkte[i]);
        }
    }

    public void removeHintergrundPunkte() {
        //Reset der Variablen und des Hintergrundes
        for (int index = 0; index < 30; index++) {
            try {
                parentClass.removePunkt(kordPunkt[index]);
            } catch (Exception ex) {
                break;
            }
        }
        parentClass.repaintHintergrund();
    }

    public void removeLastKordPunkt() {
        kordPunkt[punkteZaehler - 1].removeMouseListener(null);
    }

    public void resetKordinatenPunkte() {
        for (int index = 0; index < kordPunkt.length; index++) {
            try {
                kordPunkt[index].setLocation(eingabePunkte[index]);
            } catch (Exception ex) {
                break;
            }
        }
    }
}
