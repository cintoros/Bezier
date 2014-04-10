/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bezier.kurven;

/**
 * Class for starting the Programm
 * @author Elias
 */
public class start {

    /**
     * starts the Programm
     * @param args
     */
    public static void main(String[] args) {
        DefFrame d1 = new DefFrame(1000, 600, "BezierKurven");
        BezierKurven b1 = new BezierKurven(d1);
        d1.setResizable(true);
    }
}
