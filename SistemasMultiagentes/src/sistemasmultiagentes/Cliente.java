/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import sun.net.www.http.HttpClient;

/**
 *
 * @author bitde
 */
public class Cliente {
    
    private static int id;                      // ID del Cliente
    private static String ipMonitor;            // IP del Monitor
    private static ArrayList<String> tiendas;   // Tiendas conocidas por el Cliente
    private static Productos productos;         // Productos a comprar
    
    
    /**
     * Constructor para el cliente. Inicializa las variables y obtiene del Monitor
     * el ID del Cliente, la Lista de Productos y 2 Tiendas conocidas.
     * @param ip IP del Monitor
    */
    public Cliente(String ip){
        // Inicializaciones
        ipMonitor = ip;
	tiendas = new ArrayList<>();
	
        /* Se pide al monitor el ID, la Lista de Productos y las Tiendas
         * conocidas mediante el mensaje "mensajeAltaMonitor()". */
        String respuesta = mensajeAltaMonitor();
        id = respuesta[0];
        productos = new Productos(respuesta[1]);
        tiendas = respuesta[2];
    }
    
    public void funcionDelCliente(){
        while(!productos.isEmpty()){
            //Me doy de alta en la tienda
            Document respuestaAltaTienda = xmlToDom(mensajeAltaTienda());
            System.out.println(respuestaAltaTienda);
            
            //Pido catalogo
            Document respuestaConsultaProductos = xmlToDom(mensajeConsultaProductos());
            System.out.println(respuestaConsultaProductos);
            Productos catalogo = new Productos(respuestaConsultaProductos);
            
            //Compro productos del catalogo
            for(Map.Entry<Integer, Integer> par : catalogo.getProductos().entrySet()){
                //si el producto esta en la lista de la compra
                if(productos.getProductos().containsKey(par.getKey())){
                    //Compro un producto
                    Document respuestaCompraProducto = xmlToDom(mensajeCompraProductos());
                    System.out.println(respuestaCompraProducto);
                    //Resto cuanto he comprado
                    //Quito producto de la lista si ya he comprado todo
                }
                //si no continuamos al siguiente producto
            }
            
            //Pedimos la lista de clientes que hay en la tienda
            Document respuestaConsultaClientes = xmlToDom(mensajeConsultaClientes());
            System.out.println(respuestaConsultaClientes);
            ArrayList<Integer> clientes = new ArrayList<>();
                ///leer la respuesta y escribirla en la lista
            for(int cliente: clientes){
                Document respuestaConsultaTiendas = xmlToDom(mensajeConsultaTiendas());
                System.out.println(respuestaConsultaTiendas);
                //añadimos tiendas a la lista
            }
            
            //Nos damos de baja en la tienda y vamos a la sigiente
            Document respuestaBajaTienda = xmlToDom(mensajeBajaTiendas());
            System.out.println(respuestaBajaTienda);
        }
    }
    
    
    // como aún no podemos recibir mensajes de la tienda, leemos de un fichero de texto
    // mensajes que hemos escrito
    
    private File mensajeAltaMonitor() throws FileNotFoundException{
        

        // Creamos un string con la respuesta que recibimos
        // Creamos un string con la url del cliente
        // Creamos un cliente de la clase HttpCliente
        // Creamos un objeto de la clase GetMethod con la url de antes
        // Ejecutamos el método en el cliente
        // Guardamos en respuesta la respuesta que obtengamos
        
        // Para hacer una prueba leemos de un fichero que hemos creado
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaAltaMonitor.txt");
        return fichero;
    }
    
    private File mensajeAltaTienda(){
        
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaAltaTienda.txt");
        return fichero;
        
    }
    
    private File mensajeConsultaProductos(){
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaProductos.txt");
        return fichero;
    }
    
    private File mensajeCompraProductos(){
        File fichero = new File("./project/nbproject/respuestas/RespuestaComprarProductos.txt");
        return fichero;
    }
    
    private File mensajeConsultaClientes(){
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaClientes.txt");
        return fichero;
    }
    
    private File mensajeConsultaTiendas(){
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaTiendas.txt");
        return fichero;
    }
    
    private File mensajeBajaTiendas(){
        File fichero = new File("./project/nbproject/respuestas/RespuestaBajaTiendas.txt");
        return fichero;
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
