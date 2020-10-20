/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.ArrayList;

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
    
    private String mensajeAltaMonitor(){
        return "por hacer";
    }
    
    private String mensajeAltaTienda(){
        return "por hacer";
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
