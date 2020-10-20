/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

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
    
    private String mensajeAltaMonitor(){
        

        // Creamos un string con la respuesta que recibimos
        // Creamos un string con la url del cliente
        // Creamos un cliente de la clase HttpCliente
        // Creamos un objeto de la clase GetMethod con la url de antes
        // Ejecutamos el método en el cliente
        // Guardamos en respuesta la respuesta que obtengamos
        
        // Para hacer una prueba leemos de un fichero que hemos creado
        
        return "a";
        
        
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
