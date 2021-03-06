/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import XML.InterpreteXML;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Grupo 2
 */
public class Cliente {

    private int id_interno;                         // ID interno del Cliente (el número de cliente en el main)
    private int id;                                 // ID del Cliente
    private String ipMonitor;                       // IP del Monitor
    private int puertoMonitor;                      // Puerto del Monitor
    private HashSet<Tienda> tConocidas;             // Tiendas conocidas por el Cliente
    private LinkedList<Tienda> tNoVisitadas;        // Tiendas visitadas por el Cliente
    private LinkedList<Tienda> tVisitadas;          // Tiendas visitadas por el Cliente
    private HashMap<Integer, Producto> productos;   // Productos a comprar
    private int nVueltas;                           // Veces que hemos recorrido el array de Tiendas
    private int MAXVUELTAS;                         // Máximo de vueltas que vamos a dar al array de Tiendas
    public FileWriter fichero;                      // FileWriter para escribir los logs
    public PrintWriter pw;                          // PrintWriter para escribir los logs
    public InterpreteXML XML;                       // Intérprete XML

    /**
     * Constructor para el cliente. Inicializa las variables.
     * @param ip IP del Monitor
     * @param puerto Puerto del Monitor
     * @param id_interno ID interno que utiliza el main para distinguir a cada
     * Cliente
     * @throws java.io.IOException
     */
    public Cliente(String ip, int puerto, int id_interno) throws IOException {
        // Inicializaciones
        this.id_interno = id_interno;
        this.ipMonitor = ip;
        this.puertoMonitor = puerto;
        this.fichero = new FileWriter(".\\logs\\cliente" + id_interno + ".txt", true);
        this.pw = new PrintWriter(fichero);
        this.nVueltas = 0;
        this.MAXVUELTAS = 5;
        this.XML = new InterpreteXML();
    }

    public void funcionDelCliente() throws IOException {
        /* Se pide al monitor el ID, la Lista de Productos y las Tienda
         * conocidas mediante el mensaje "mensajeAltaMonitor()".
         */
        Object[] resp = mensajeAltaMonitor(this.ipMonitor, this.puertoMonitor);
        id = (Integer) resp[0];
        productos = (HashMap) resp[1];
        tConocidas = (HashSet) resp[2];
        tNoVisitadas = new LinkedList(tConocidas);
        tVisitadas = new LinkedList();

        pw.println(" ID ASIGNADO: " + id + " a las " + LocalTime.now() + "\n"
                + "\n TIENDAS CONOCIDAS: " + tConocidas.toString() + "\n"
                + "\n PRODUCTOS A COMPRAR: " + productos.values().toString() + "\n");

        while (!finalizado() && nVueltas < MAXVUELTAS) {
            // Obtenemos la primera tienda de la lista de no visitadas
            if (tNoVisitadas.isEmpty()) {
                tNoVisitadas = tVisitadas;
                tVisitadas = new LinkedList<>();
                nVueltas++;
                pw.println("\n\n\n  DAMOS OTRA VUELTA A LAS TIENDAS\n\n");
            }
            Tienda tienda = tNoVisitadas.poll();

            // Me doy de alta en la tienda (realizando la compra al mismo tiempo)
            HashMap<Integer, Producto> mTienda = mensajeAltaTienda(tienda);

            pw.println("\n  Compra en la tienda: " + tienda.toString2() + " realizada.");

            // Vemos qué productos hemos comprado
            if (mTienda != null) {
                mTienda.forEach((Integer id_producto, Producto prod) -> {
                    productos.get(id_producto).restaCantidad(prod.getCantidad());
                    pw.println("      Compro " +  prod.getCantidad()
                            + " unidades del producto " + id_producto + ". Faltan " + productos.get(id_producto).getCantidad());
                });
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
            mensajeBajaTiendas(tienda);
            pw.println("  BAJA en la tienda: " + tienda.toString2());

            // Añadimos la tienda visitada al final de la lista de tiendas visitadas.
            tVisitadas.add(tienda);
        }
        pw.println("\n\n COMPRA FINALIZADA. PRODUCTOS RESTANTES: " + productos.values().toString() + "\n");
        mensajeBajaMonitor();
        fichero.close();
    }

    private Object[] mensajeAltaMonitor(String ip, int puerto) throws IOException {
        return this.XML.leeAltaMonitor(getHTTP(("http://" + ip + ":" + puerto), "crearCliente=True"));
    }

    private HashMap<Integer, Producto> mensajeAltaTienda(Tienda tienda) throws IOException {
        String confirmacion = XML.escribeAltaTienda(this.id, tienda, this.productos);
        String respuestaPost = postHTTP(confirmacion, "http://" + tienda.ip + ":" + tienda.puerto + "/", true);
        return XML.leeCompra(respuestaPost);
    }

    private ArrayList<Tienda> mensajeConsultaTiendas(Tienda tienda) throws IOException {
        String confirmacion = XML.escribeConsultaTiendas(this.id, tienda, this.tConocidas);
        String respuestaPost = postHTTP(confirmacion, "http://" + tienda.ip + ":" + tienda.puerto + "/", true);
        return XML.leeTiendasConocidas(respuestaPost);
    }

    private void mensajeBajaTiendas(Tienda tienda) throws IOException {
        String confirmacion = XML.escribeBajaTienda(this.id, tienda);
        postHTTP(confirmacion, "http://" + tienda.ip + ":" + tienda.puerto + "/", true);
    }

    private void mensajeBajaMonitor() throws IOException {
        String confirmacion = XML.escribeBajaMonitor(this.id, this.ipMonitor,
                this.puertoMonitor, this.productos);
        postHTTP(confirmacion, "http://" + ipMonitor + ":" + puertoMonitor + "/", false);
    }

    public int getId_interno() {
        return id_interno;
    }

    /**
     * Devuelve si hay algún producto en el HashSet con cantidad distinta de 0.
     *
     * @return Si quedan productos por comprar o no.
     */
    public boolean finalizado() {
        for (Producto prod : productos.values()) {
            if (prod.getCantidad() != 0) {
                return false;
            }
        }
        return true;
    }

    private String getHTTP(String URL, String query) throws MalformedURLException, IOException {
        URL url = new URL(URL + "/?" + query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set timeout as per needs
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(200000);

        // Set DoOutput to true if you want to use URLConnection for output.
        // Default is false
        connection.setDoOutput(true);

        connection.setUseCaches(true);
        connection.setRequestMethod("GET");

        // Set Headers
        connection.setRequestProperty("Content-Type", "application/xml");

        // Read XML
        InputStream inputStream = connection.getInputStream();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        System.out.println("Response= " + response.toString());
        return response.toString();
    }

    private String postHTTP(String mensaje, String URL, Boolean respuesta) throws MalformedURLException, IOException {
        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String respuestaS = " ";
        // Set timeout as per needs
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(200000);

        // Set DoOutput to true if you want to use URLConnection for output.
        // Default is false
        connection.setDoOutput(true);

        connection.setUseCaches(true);
        connection.setRequestMethod("POST");

        // Set Headers
        connection.setRequestProperty("Accept", "application/xml");
        connection.setRequestProperty("Content-Type", "application/xml");

        // Write XML
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = mensaje.getBytes("UTF-8");
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();

        // Read XML
        if (respuesta) {
            InputStream inputStream = connection.getInputStream();
            byte[] res = new byte[2048];
            int i = 0;
            StringBuilder response = new StringBuilder();
            while ((i = inputStream.read(res)) != -1) {
                response.append(new String(res, 0, i));
            }
            inputStream.close();
        
        System.out.println("Response= " + response.toString());
        return response.toString();
        }
        return respuestaS;
    }
}
