/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DummyTest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bitde
 */
public class DummyMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            new DummyMonitor();
            new DummyTienda();
        } catch (IOException ex) {
            Logger.getLogger(DummyMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
