/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author bitde
 */
public class Cliente {
    
    private final int id_interno;                   // ID interno del Cliente (el número de cliente en el main)
    private final int id;                           // ID del Cliente
    private final String ipMonitor;                 // IP del Monitor
    private final HashSet<Tienda> tConocidas;       // Tiendas conocidas por el Cliente
    private LinkedList<Tienda> tNoVisitadas;        // Tiendas visitadas por el Cliente
    private LinkedList<Tienda> tVisitadas;          // Tiendas visitadas por el Cliente
    private final Productos productos;              // Productos a comprar
    private int nVueltas;                           // Veces que hemos recorrido el array de Tiendas
    private final int MAXVUELTAS;                   // Máximo de vueltas que vamos a dar al array de Tiendas
    public final FileWriter fichero;                // FileWriter para escribir los logs
    public final PrintWriter pw;                    // PrintWriter para escribir los logs
    
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
        this.fichero = new FileWriter(".\\logs\\cliente" + id_interno + ".txt", true);
        this.pw = new PrintWriter(fichero);
        this.nVueltas = 0;
        this.MAXVUELTAS = 5;

        /* Se pide al monitor el ID, la Lista de Productos y las Tienda
         * conocidas mediante el mensaje "mensajeAltaMonitor()".
        */
        Object[] respuesta = mensajeAltaMonitor();
        id = (Integer) respuesta[0];
        productos = (Productos) respuesta[1];
        tConocidas = (HashSet) respuesta[2];
        tNoVisitadas = new LinkedList(tConocidas);
        tVisitadas = new LinkedList();

        pw.println(" ID ASIGNADO: " + id + " a las " + LocalTime.now() + "\n"
                 + "\n TIENDAS CONOCIDAS: " + tConocidas.toString() + "\n"
                 + "\n PRODUCTOS A COMPRAR: " + productos.toString()+ "\n");
    }
    
    public void funcionDelCliente() throws IOException{
        while(!productos.finalizado() && nVueltas < MAXVUELTAS){
            // Obtenemos la primera tienda de la lista de no visitadas
            if (tNoVisitadas.isEmpty()){
                tNoVisitadas = tVisitadas;
                tVisitadas = new LinkedList<>();
                nVueltas ++;
                pw.println("\n\n\n  DAMOS OTRA VUELTA A LAS TIENDAS\n\n");
            }
            Tienda tienda = tNoVisitadas.poll();
            
            //Me doy de alta en la tienda
            String respuestaAltaTienda = mensajeAltaTienda(tienda);
            pw.println("\n  ALTA en la tienda: " + tienda.toString());
            //Pido catalogo
            Productos catalogo = mensajeConsultaProductos(tienda);
            pw.println("    Obtengo productos de la tienda.");
            
            // Compro productos del catalogo
            for(Map.Entry<Integer, Integer> par : catalogo.getProductos().entrySet()){
                // Si el producto esta en la lista de la compra
                if(productos.getProductos().containsKey(par.getKey())){
                    int cant = productos.getProductos().get(par.getKey()); //par.getValue();
                    // Si quiero 1 o mas unidades del producto lo compro
                    if (cant > 0){
                        // Compro un producto
                        int respuestaCompraProducto = mensajeCompraProductos(tienda, par.getKey(), cant);
                        // Resto cuanto he comprado
                        if (productos.menosProducto(par.getKey(), respuestaCompraProducto) == null)
                            pw.println("\n\n     ---  ERROR AL RESTAR PRODUCTO --- \n\n");
                        pw.println("      Compro " + respuestaCompraProducto + " unidades del producto " 
                                + par.getKey() + ". Faltan " + productos.nProducto(par.getKey()));
                        
                    }
                }
                // Si no continuamos al siguiente producto
            }
            
            //Pedimos la lista de tiendas conocidas a la tienda
            ArrayList<Tienda> respuestaConsultaTiendas = mensajeConsultaTiendas(tienda);
            pw.println("    Consultamos las tiendas conocidas.");
            // Para cada tienda recibida
            for (Tienda respuesta : respuestaConsultaTiendas) {
                // La intentamos añadir a nuestro HashMap de tiendas conocidas
                if (tConocidas.add(respuesta)) {
                    // Y si se ha añadido (no estaba), también a no visitadas
                    tNoVisitadas.add(respuesta);
                    pw.println("      Nueva tienda: " + respuesta);
                }
            }
      
            // Nos damos de baja en la tienda
            String respuestaBajaTienda = mensajeBajaTiendas();
            pw.println("  BAJA en la tienda " + tienda);
            
            // Añadimos la tienda visitada al final de la lista de tiendas visitadas.
            tVisitadas.add(tienda);
        }
        pw.println("\n\n COMPRA FINALIZADA. PRODUCTOS RESTANTES: " + productos.toString()+ "\n");
        fichero.close();
    }
    
    
    // como aún no podemos recibir mensajes de la tienda, leemos de un fichero de texto
    // mensajes que hemos escrito
    
    private Object[] mensajeAltaMonitor(){
        Object[] array = new Object[3];
        
        // Añadimos el ID
        array[0] = (int) (Math.random() * 10000);
        
        // Añadimos los Productos
        Integer [][] prod = new Integer[3][2];
        for (int i = 0; i < prod.length; i++) {
            prod[i][0] = i;
            prod[i][1] = (int) (1 + (Math.random() * 100));
        }
        
        array[1] = new Productos(prod);
        
        // Añadimos las Tiendas
        HashSet tiendas = new HashSet();
        tiendas.add(new Tienda(0, "1.1.1.1", 62, "Mercadona"));
        tiendas.add(new Tienda(1, "2.2.2.2", 62, "El Corte Inglés"));
        tiendas.add(new Tienda(1, "3.3.3.3", 62, "LIDL"));
        
        array[2] = tiendas;
        
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
        Integer[][] prod = new Integer[2][2];
        prod[0][0] = 0;
        prod[0][1] = (int) (Math.random() * 40);
        if ("Mercadona".equals(tienda.nombre)) {
            prod[1][0] = 1;
            prod[1][1] = (int) (Math.random() * 80);
        } else if ("El Corte Inglés".equals(tienda.nombre)) {
            prod[1][0] = 2;
            prod[1][1] = (int) (Math.random() * 60);
        } else if ("LIDLl".equals(tienda.nombre)) {
            prod[0][0] = 1;
            prod[1][0] = 2;
            prod[1][1] = (int) (Math.random() * 70);
        }
        return productos;
    }
    
    private int mensajeCompraProductos(Tienda tienda, int id, int cant){
        int rand = Integer.MAX_VALUE;
        while (rand>cant){
            rand = (int) (Math.random()*20 + 1);
        }
        
        return rand;
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
    
    /*
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
    */

    /**
     * @return the id_interno
     */
    public int getId_interno() {
        return id_interno;
    }
}
