/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author bitde
 */
public class Cliente {
    
    private final int id_interno;              // ID interno del Cliente (el número de cliente en el main)
    private final int id;                      // ID del Cliente
    private final String ipMonitor;            // IP del Monitor
    private final HashSet<Tienda> tiendas;     // Tiendas conocidas por el Cliente
    private final Productos productos;         // Productos a comprar
    private final FileWriter fichero;          // FileWriter para escribir los logs
    private final PrintWriter pw;              // PrintWriter para escribir los logs
    
    /**
     * Constructor para el cliente. Inicializa las variables y obtiene del Monitor
     * el ID del Cliente, la Lista de Productos y 2 Tienda conocidas.
     * @param ip IP del Monitor
     * @param id_interno ID interno que utiliza el main para distinguir a cada Cliente
     * @throws java.io.IOException
    */
    public Cliente(String ip, int id_interno) throws IOException{
        // Inicializaciones
        this.id_interno = id_interno;
        this.ipMonitor = ip;

        /* Se pide al monitor el ID, la Lista de Productos y las Tienda
         * conocidas mediante el mensaje "mensajeAltaMonitor()".
        */
        Object[] respuesta = mensajeAltaMonitor();
        id = (Integer) respuesta[0];
        productos = (Productos) respuesta[1];
        tiendas = (HashSet) respuesta[2];

        this.fichero = new FileWriter(".\\logs\\cliente" + id_interno + ".txt",true);
        this.pw = new PrintWriter(fichero);
    }
    
    public void funcionDelCliente() throws IOException{
        while(!productos.isEmpty()){
            //Me doy de alta en la tienda
            String respuestaAltaTienda = mensajeAltaTienda();
            System.out.println(respuestaAltaTienda);
            
            //Pido catalogo
            //Document respuestaConsultaProductos = mensajeConsultaProductos();
            //System.out.println(respuestaConsultaProductos);
            Productos catalogo = mensajeConsultaProductos();
            
            //Compro productos del catalogo
            for(Map.Entry<Integer, Integer> par : catalogo.getProductos().entrySet()){
                //si el producto esta en la lista de la compra
                if(productos.getProductos().containsKey(par.getKey())){
                    int cant = productos.getProductos().get(par.getKey()); //par.getValue();
                    //Si quiero 1 o mas unidades del producto lo compro
                    if (cant > 0){
                        //Compro un producto
                        int respuestaCompraProducto = mensajeCompraProductos(par.getKey());
                        System.out.println(respuestaCompraProducto);
                        //Resto cuanto he comprado
                        productos.menosProducto(par.getKey(), cant);
                    }
                    
                }
                //si no continuamos al siguiente producto
            }
            
            //Pedimos la lista de tiendas conocidas a la tienda
            HashSet<Tienda> respuestaConsultaTiendas = mensajeConsultaTiendas();
            System.out.println(respuestaConsultaTiendas);
            //añadimos tiendas a la lista
            for (Tienda t : respuestaConsultaTiendas){
                tiendas.add(t);
            }            
            //Nos damos de baja en la tienda y vamos a la sigiente
            String respuestaBajaTienda = mensajeBajaTiendas();
            System.out.println(respuestaBajaTienda);
        }
        fichero.close();
    }
    
    
    // como aún no podemos recibir mensajes de la tienda, leemos de un fichero de texto
    // mensajes que hemos escrito
    
    private Object[] mensajeAltaMonitor(){
        

        // Creamos un string con la respuesta que recibimos
        // Creamos un string con la url del cliente
        // Creamos un cliente de la clase HttpCliente
        // Creamos un objeto de la clase GetMethod con la url de antes
        // Ejecutamos el método en el cliente y guardamos la respuesta que obtengamos
        
        // Para hacer una prueba leemos de un fichero que hemos creado

        Object[] array = new Object[3];
        return array;
    }
    
    private Document mensajeAltaTienda(){
        
        // Mismos pasos iniciales
        // Para darnos de alta en la tienda necesitamos su IP y pedir permiso al monitor
        // Recibiremos un mensaje de la tienda que nos indique que nos hemos dado de alta correctamente
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaAltaTienda.txt");
        return xmlToDom(fichero);
        
    }
    
    private Document mensajeConsultaProductos(){
        
        // Mismos pasos iniciales de antes
        // Consultamos a la tienda la lista de todos sus productos
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaProductos.txt");
        return xmlToDom(fichero);
    }
    
    private Document mensajeCompraProductos(){
        
        // Mismos pasos iniciales de antes
        // Cuando hayamos consultado los productos que tiene la tienda, le indicamos cuales queremos comprar
        // (todos los que tengan de nuestra lista)
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaComprarProductos.txt");
        return xmlToDom(fichero);
    }
    
    private Document mensajeConsultaClientes(){
        
        // Mismos pasos iniciales de antes
        // Pedimos a la tienda los Id de todos los clientes que están en la tienda
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaClientes.txt");
        return xmlToDom(fichero);
    }
    
    private Document mensajeConsultaTiendas(){
        
        // Mismos pasos iniciales de antes
        // Pedimos a la tienda las tiendas que conozca un cliente conociendo su Id
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaTiendas.txt");
        return xmlToDom(fichero);
    }
    
    private Document mensajeBajaTiendas(){
        
        // Mismos pasos iniciales
        // Para darnos de baja en la tienda necesitamos su IP y pedir permiso al monitor
        // Recibiremos un mensaje de la tienda que nos indique que nos hemos dado de baja correctamente
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaBajaTiendas.txt");
        return xmlToDom(fichero);
    }
    
    private Document xmlToDom(File f){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
