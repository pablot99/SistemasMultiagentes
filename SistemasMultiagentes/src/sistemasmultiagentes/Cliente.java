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
        
    }
    
    
    // como aún no podemos recibir mensajes de la tienda, leemos de un fichero de texto
    // mensajes que hemos escrito
    
    private File mensajeAltaMonitor() throws FileNotFoundException{
        

        // Creamos un string con la respuesta que recibimos
        // Creamos un string con la url del cliente
        // Creamos un cliente de la clase HttpCliente
        // Creamos un objeto de la clase GetMethod con la url de antes
        // Ejecutamos el método en el cliente y guardamos la respuesta que obtengamos
        
        // Para hacer una prueba leemos de un fichero que hemos creado
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaAltaMonitor.txt");
        return fichero;
    }
    
    private File mensajeAltaTienda(){
        
        // Mismos pasos iniciales
        // Para darnos de alta en la tienda necesitamos su IP y pedir permiso al monitor
        // Recibiremos un mensaje de la tienda que nos indique que nos hemos dado de alta correctamente
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaAltaTienda.txt");
        return fichero;
        
    }
    
    private File mensajeConsultaProductos(){
        
        // Mismos pasos iniciales de antes
        // Consultamos a la tienda la lista de todos sus productos
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaProductos.txt");
        return fichero;
    }
    
    private File mensajeCompraProductos(){
        
        // Mismos pasos iniciales de antes
        // Cuando hayamos consultado los productos que tiene la tienda, le indicamos cuales queremos comprar
        // (todos los que tengan de nuestra lista)
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaComprarProductos.txt");
        return fichero;
    }
    
    private File mensajeConsultaClientes(){
        
        // Mismos pasos iniciales de antes
        // Pedimos a la tienda los Id de todos los clientes que están en la tienda
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaClientes.txt");
        return fichero;
    }
    
    private File mensajeConsultaTiendas(){
        
        // Mismos pasos iniciales de antes
        // Pedimos a la tienda las tiendas que conozca un cliente conociendo su Id
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaListaTiendas.txt");
        return fichero;
    }
    
    private File mensajeBajaTiendas(){
        
        // Mismos pasos iniciales
        // Para darnos de baja en la tienda necesitamos su IP y pedir permiso al monitor
        // Recibiremos un mensaje de la tienda que nos indique que nos hemos dado de baja correctamente
        
        File fichero = new File("./project/nbproject/respuestas/RespuestaBajaTiendas.txt");
        return fichero;
    }
    
    
}
