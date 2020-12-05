/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DummyTest;

import static DummyTest.DummyMonitor.nVisitas;
import static DummyTest.DummyMonitor.server;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 *
 * @author bitde
 */
public class DummyTienda {
    static HttpServer server1;
    static HttpServer server2;

    static int nVisitas1;
    static int nVisitas2;
    
    DummyTienda() throws IOException{
        nVisitas1=0;
        server1 = HttpServer.create(new InetSocketAddress(8001), 0);
        HttpContext context = server1.createContext("/");
        context.setHandler(DummyTienda::handleRequest1);
        server1.start();
        
        nVisitas2=0;
        server2 = HttpServer.create(new InetSocketAddress(8002), 0);
        HttpContext context2 = server2.createContext("/");
        context2.setHandler(DummyTienda::handleRequest2);
        server2.start();
    }
    
    private static void handleRequest1(HttpExchange exchange) throws IOException {
        nVisitas1++;
        String response;
        OutputStream os;
        switch(nVisitas1){
            case 1:
                System.out.println("COMPRANDO EN TIENDA 1");
                response=fileToString(".\\test\\XMLinput\\Dummy_confirmacion_compra_1.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
            case 2:
                System.out.println("PREGUNTANDO A TIENDA 1");
                response=fileToString(".\\test\\XMLinput\\Dummy_respuesta_tiendas_conocidas.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
            case 3:
                System.out.println("SALIENDO DE TIENDA 1");
                response=fileToString(".\\test\\XMLinput\\ACK.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                //server1.stop(0);
                break;
            default:
                System.out.println("INTENTA ENTRAR OTRA VEZ EN TIENDA 1 DESPUES DE ACABAR");
                break;
        }
    }
    
    private static void handleRequest2(HttpExchange exchange) throws IOException {
        nVisitas2++; 
        String response;
        OutputStream os;
        switch(nVisitas2){
            case 1:
                System.out.println("COMPRANDO EN TIENDA 2");                
                response=fileToString(".\\test\\XMLinput\\Dummy_confirmacion_compra_2.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
            case 2:
                System.out.println("PREGUNTANDO A TIENDA 2");
                response=fileToString(".\\test\\XMLinput\\Dummy_respuesta_tiendas_conocidas.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
            case 3:
                System.out.println("SALIENDO DE TIENDA 2");
                response=fileToString(".\\test\\XMLinput\\ACK.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                //server2.stop(0);
                break;
            default:
                System.out.println("INTENTA ENTRAR OTRA VEZ EN TIENDA 2 DESPUES DE ACABAR");
                break;
        }
    }
    
    static String fileToString(String ruta) throws FileNotFoundException, IOException{
        BufferedReader lector = new BufferedReader(new FileReader(ruta));
        StringBuilder cadena = new StringBuilder();
        String line = null;
    
        while ((line = lector.readLine()) != null) {
          cadena.append(line);

        }
        lector.close();
        return cadena.toString();
    }
}
