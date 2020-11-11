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
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
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
    
    private final int id_interno;                   // ID interno del Cliente (el número de cliente en el main)
    private final int id;                           // ID del Cliente
    private final String ipMonitor;                 // IP del Monitor
    private final HashSet<Tienda> tConocidas;       // Tiendas conocidas por el Cliente
    private final LinkedList<Tienda> tNoVisitadas;  // Tiendas visitadas por el Cliente
    private final LinkedList<Tienda> tVisitadas;    // Tiendas visitadas por el Cliente
    private final Productos productos;              // Productos a comprar
    private final FileWriter fichero;               // FileWriter para escribir los logs
    private final PrintWriter pw;                   // PrintWriter para escribir los logs
    
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
        this.fichero = new FileWriter(".\\logs\\cliente" + id_interno + ".txt",true);
        this.pw = new PrintWriter(fichero);

        /* Se pide al monitor el ID, la Lista de Productos y las Tienda
         * conocidas mediante el mensaje "mensajeAltaMonitor()".
        */
        Object[] respuesta = mensajeAltaMonitor();
        id = (Integer) respuesta[0];
        productos = (Productos) respuesta[1];
        tConocidas = (HashSet) respuesta[2];
        tNoVisitadas = new LinkedList(tConocidas);
        tVisitadas = new LinkedList();

        pw.println("ID ASIGNADO: " + id + " a las " + LocalTime.now() + "\n"
                 + "TIENDAS CONOCIDAS: " + tConocidas.toString() + "\n"
                 + "PRODUCTOS A COMPRAR: " + productos.toString() + "\n");

    }
    
    public void funcionDelCliente() throws IOException{
        while(!productos.isEmpty()){
            // Obtenemos la primera tienda de la lista de no visitadas
            Tienda tienda = tNoVisitadas.poll();
            
            //Me doy de alta en la tienda
            String respuestaAltaTienda = mensajeAltaTienda(tienda);
            pw.println("\nALTA en la tienda: " + tienda.toString());
            //Pido catalogo
            Productos catalogo = mensajeConsultaProductos(tienda);
            pw.println(" Obtengo productos de la tienda.");
            
            // Compro productos del catalogo
            for(Map.Entry<Integer, Integer> par : catalogo.getProductos().entrySet()){
                // Si el producto esta en la lista de la compra
                if(productos.getProductos().containsKey(par.getKey())){
                    int cant = productos.getProductos().get(par.getKey()); //par.getValue();
                    // Si quiero 1 o mas unidades del producto lo compro
                    if (cant > 0){
                        // Compro un producto
                        int respuestaCompraProducto = mensajeCompraProductos(par.getKey());
                        // Resto cuanto he comprado
                        if (productos.menosProducto(par.getKey(), respuestaCompraProducto) == null)
                            pw.println("\n\n  ---  ERROR AL RESTAR PRODUCTO --- \n\n");
                        pw.println("   Compro " + respuestaCompraProducto + "unidades del producto " 
                                + par.getKey() + ". Faltan " + productos.nProducto(par.getKey()));
                        
                    }
                }
                // Si no continuamos al siguiente producto
            }
            
            //Pedimos la lista de tiendas conocidas a la tienda
            ArrayList<Tienda> respuestaConsultaTiendas = mensajeConsultaTiendas(tienda);
            pw.println(" Consultamos las tiendas conocidas.");
            // Para cada tienda recibida
            for (Tienda respuesta : respuestaConsultaTiendas) {
                // La intentamos añadir a nuestro HashMap de tiendas conocidas
                if (tConocidas.add(respuesta)) {
                    // Y si se ha añadido (no estaba), también a no visitadas
                    tNoVisitadas.add(respuesta);
                    pw.println("   Nueva tienda: " + respuesta);
                }
            }
      
            // Nos damos de baja en la tienda
            String respuestaBajaTienda = mensajeBajaTiendas();
            pw.println("BAJA en la tienda " + tienda);
            
            // Añadimos la tienda visitada al final de la lista de tiendas visitadas.
            tVisitadas.add(tienda);
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
    
    private String mensajeAltaTienda(Tienda tienda){
        
        // Mismos pasos iniciales
        // Para darnos de alta en la tienda necesitamos su IP y pedir permiso al monitor
        // Recibiremos un mensaje de la tienda que nos indique que nos hemos dado de alta correctamente
        
        String respuesta = "Se ha dado de alta correctamente";
        return respuesta;
        
    }
    
    private Productos mensajeConsultaProductos(Tienda tienda){
        
        // Mismos pasos iniciales de antes
        // Consultamos a la tienda la lista de todos sus productos
        File fichero = new File("./project/nbproject/respuestas/RespuestaBajaTiendas.txt");
        Productos productos = new Productos(xmlToDom(fichero));
        return productos;
    }
    
    private int mensajeCompraProductos(int par){
        int cantidad = 0;
        // Mismos pasos iniciales de antes
        // Cuando hayamos consultado los productos que tiene la tienda, le indicamos cuales queremos comprar
        // (todos los que tengan de nuestra lista)
        
        
        return cantidad;
    }
    
    private Document mensajeConsultaClientes(){
        
        // Mismos pasos iniciales de antes
        // Pedimos a la tienda los Id de todos los clientes que están en la tienda
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaClientes.txt");
        return xmlToDom(fichero);
    }
    
    private ArrayList<Tienda> mensajeConsultaTiendas(Tienda tienda){
        ArrayList<Tienda> tiendasSiguientes = new ArrayList<Tienda>();
        // Mismos pasos iniciales de antes
        // Pedimos a la tienda las tiendas que conozca un cliente conociendo su Id
        
        return tiendasSiguientes;
    }
    
    private String mensajeBajaTiendas(){
        String respuesta = "Se ha dado de baja correctamente";
        // Mismos pasos iniciales
        // Para darnos de baja en la tienda necesitamos su IP y pedir permiso al monitor
        // Recibiremos un mensaje de la tienda que nos indique que nos hemos dado de baja correctamente
        
        return respuesta;
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
