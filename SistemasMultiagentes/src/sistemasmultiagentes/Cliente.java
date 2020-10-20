/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import sun.net.www.http.HttpClient;

/**
 *
 * @author bitde
 */
public class Cliente {
    
    public Cliente(){
        
    }
    
    public void funcionDelCliente(){
        
    }
    
    
    // como aún no podemos recibir mensajes de la tienda, leemos de un fichero de texto
    // mensajes que hemos escrito
    
    private String mensajeAltaMonitor() throws FileNotFoundException{
        

        // Creamos un string con la respuesta que recibimos
        // Creamos un string con la url del cliente
        // Creamos un cliente de la clase HttpCliente
        // Creamos un objeto de la clase GetMethod con la url de antes
        // Ejecutamos el método en el cliente
        // Guardamos en respuesta la respuesta que obtengamos
        
        // Para hacer una prueba leemos de un fichero que hemos creado
        
        BufferedReader respuesta = new BufferedReader(new FileReader("./project/nbproject/respuestas/RespuestaAltaMonitor.txt"));
        
    }
    
    private String mensajeAltaTienda(){
        
        
        return "hola";
        
    }
    
    private String mensajeConsultaProductos(){
        return "por hacer";
    }
    
    private String mensajeCompraProductos(){
        return "por hacer";
    }
    
    private String mensajeConsultaClientes(){
        return "por hacer";
    }
    
    private String mensajeConsultaTiendas(){
        return "por hacer";
    }
    
    private String mensajeBajaTiendas(){
        return "por hacer";
    }
    
    
}
