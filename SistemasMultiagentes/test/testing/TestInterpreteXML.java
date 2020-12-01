/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import XML.InterpreteXML;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemasmultiagentes.Producto;
import sistemasmultiagentes.Tienda;

/**
 *
 * @author bitde
 */
public class TestInterpreteXML {
    public static void main(String[] args){
        InterpreteXML interprete= new InterpreteXML();
        
        try {
            testLeeAltaMonitor(interprete);
        } catch (IOException ex) {
            Logger.getLogger(TestInterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void testLeeAltaMonitor(InterpreteXML interprete) throws FileNotFoundException, IOException{
        
        String cadena=fileToString(".\\test\\XMLinput\\reg_cliente.xml");
        System.out.println("Mensaje: " + cadena);
        Object[] o;
        o=interprete.leeAltaMonitor(cadena);
        //Imprimimos el id
        System.out.println("Id: "+(int) o[0]+"\n");
        //imprimimos lista de productos
        HashMap<Integer, Producto> productos= (HashMap<Integer, Producto>) o[1];
        System.out.println("Productos:\n");
        System.out.println(productos);
        System.out.println("\n");
        //imprimimos tiendas
        HashSet<Tienda> tiendas= (HashSet<Tienda>) o[2];
        System.out.println("Tiendas:\n");
        System.out.println(tiendas);
        System.out.println("\n");
    }
    
    static String fileToString(String ruta) throws FileNotFoundException, IOException{
        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        StringBuilder cadena = new StringBuilder();
        String line = null;
    
        while ((line = lector.readLine()) != null) {
          cadena.append(line);

        }
        lector.close();
        return cadena.toString();
    }
}
