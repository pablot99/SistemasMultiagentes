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
import java.util.ArrayList;
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
            testEscribeAltaTienda(interprete);
            testLeeCompra(interprete);
            testEscribeConsultaTiendas(interprete);
            testLeeTiendasConocidas(interprete);
            testEscribeBajaTienda(interprete);
        } catch (IOException ex) {
            Logger.getLogger(TestInterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static void testLeeAltaMonitor(InterpreteXML interprete) throws FileNotFoundException, IOException{
        System.out.println("-----------    TEST LEE ALTA MONITOR    -----------\n");
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
    
    static void testEscribeAltaTienda(InterpreteXML interprete){
        System.out.println("-----------    TEST ESCRIBE ALTA TIENDA    -----------\n");
        int id_c=1;
        Tienda t = new Tienda(0, "192.168.1.1", 8080);
        HashMap<Integer, Producto> p = new HashMap<>();
        Producto p1 = new Producto(1, 50);
        p.put(1, p1);
        Producto p2 = new Producto(2,100);
        p.put(2, p2);
        String result = interprete.escribeAltaTienda(id_c, t, p);
        System.out.println(result);
        if(interprete.validateSchema(result))
            System.out.println("ESQUEMA VALIDADO");
        else
            System.out.println("EL ESQUEMA NO VALIDA");
    }
    
    static void testLeeCompra(InterpreteXML interprete) throws FileNotFoundException, IOException{
        System.out.println("-----------    TEST LEE COMPRA    -----------\n");
        String cadena=fileToString(".\\test\\XMLinput\\confirmacion_compra.xml");
        System.out.println("Mensaje: " + cadena);
        ArrayList<Producto> productos= interprete.leeCompra(cadena);
        //imprimimos los productos
        System.out.println("Productos: \n");
        System.out.println(productos);
    }
    
    static void testEscribeConsultaTiendas(InterpreteXML interprete){
        System.out.println("-----------    TEST ESCRIBE CONSULTA TIENDAS    -----------\n");
        HashSet<Tienda> tiendas= new HashSet<>();
        Tienda t = new Tienda(0, "192.168.1.0", 8080);
        tiendas.add(t);
        Tienda t1 = new Tienda(1, "192.168.1.1", 8080);
        tiendas.add(t1);
        Tienda t2 = new Tienda(2, "192.168.1.2", 8080);
        tiendas.add(t2);
        
        String result = interprete.escribeConsultaTiendas(0, t, tiendas);
        System.out.println(result);
        if(interprete.validateSchema(result))
            System.out.println("ESQUEMA VALIDADO");
        else
            System.out.println("EL ESQUEMA NO VALIDA");
    }
    
    static void testLeeTiendasConocidas(InterpreteXML interprete) throws IOException{
        System.out.println("-----------    TEST LEE TIENDAS CONOCIDAS    -----------\n");
        String cadena=fileToString(".\\test\\XMLinput\\respuesta_tiendas_conocidas.xml");
        System.out.println("Mensaje: " + cadena);
        ArrayList<Tienda> tiendas= interprete.leeTiendasConocidas(cadena);
        //imprimimos los productos
        System.out.println("Tiendas: \n");
        System.out.println(tiendas);
    }
    static void testEscribeBajaTienda(InterpreteXML interprete){
        System.out.println("-----------    TEST ESCRIBE BAJA TIENDA    -----------\n");
        Tienda t = new Tienda(0, "192.168.1.0", 8080);       
        
        String result = interprete.escribeBajaTienda(0, t);
        System.out.println(result);
        if(interprete.validateSchema(result))
            System.out.println("ESQUEMA VALIDADO");
        else
            System.out.println("EL ESQUEMA NO VALIDA");
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
