/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
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
    
    private static int id;                      // ID del Cliente
    private static String ipMonitor;            // IP del Monitor
    private static HashSet<Tienda> tiendas;     // Tiendas conocidas por el Cliente
    private static Productos productos;         // Productos a comprar
    
    
    /**
     * Constructor para el cliente. Inicializa las variables y obtiene del Monitor
     * el ID del Cliente, la Lista de Productos y 2 Tienda conocidas.
     * @param ip IP del Monitor
    */
    public Cliente(String ip){
        // Inicializaciones
        ipMonitor = ip;
        tiendas = new HashSet<>();
	
        /* Se pide al monitor el ID, la Lista de Productos y las Tienda
         * conocidas mediante el mensaje "mensajeAltaMonitor()". */
        String respuesta = mensajeAltaMonitor();
        id = respuesta[0];
        productos = new Productos(respuesta[1]);
        tiendas = respuesta[2];
    }
    
    public void funcionDelCliente(){
        while(!productos.isEmpty()){
            //Me doy de alta en la tienda
            Document respuestaAltaTienda = mensajeAltaTienda();
            System.out.println(respuestaAltaTienda);
            
            //Pido catalogo
            Document respuestaConsultaProductos = mensajeConsultaProductos();
            System.out.println(respuestaConsultaProductos);
            Productos catalogo = new Productos(respuestaConsultaProductos);
            
            //Compro productos del catalogo
            for(Map.Entry<Integer, Integer> par : catalogo.getProductos().entrySet()){
                //si el producto esta en la lista de la compra
                if(productos.getProductos().containsKey(par.getKey())){
                    //Compro un producto
                    Document respuestaCompraProducto = mensajeCompraProductos();
                    System.out.println(respuestaCompraProducto);
                    //Resto cuanto he comprado
                    //Quito producto de la lista si ya he comprado todo
                }
                //si no continuamos al siguiente producto
            }
            
            //Pedimos la lista de clientes que hay en la tienda
            Document respuestaConsultaClientes = mensajeConsultaClientes();
            System.out.println(respuestaConsultaClientes);
            ArrayList<Integer> clientes = new ArrayList<>();
                ///leer la respuesta y escribirla en la lista
            for(int cliente: clientes){
                Document respuestaConsultaTiendas = mensajeConsultaTiendas();
                System.out.println(respuestaConsultaTiendas);
                //añadimos tiendas a la lista
            }
            
            //Nos damos de baja en la tienda y vamos a la sigiente
            Document respuestaBajaTienda = mensajeBajaTiendas();
            System.out.println(respuestaBajaTienda);
        }
    }
    
    
    // como aún no podemos recibir mensajes de la tienda, leemos de un fichero de texto
    // mensajes que hemos escrito
    
    private Document mensajeAltaMonitor() throws FileNotFoundException{
        

        // Creamos un string con la respuesta que recibimos
        // Creamos un string con la url del cliente
        // Creamos un cliente de la clase HttpCliente
        // Creamos un objeto de la clase GetMethod con la url de antes
        // Ejecutamos el método en el cliente y guardamos la respuesta que obtengamos
        
        // Para hacer una prueba leemos de un fichero que hemos creado
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaAltaMonitor.txt");
        return xmlToDom(fichero);
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
