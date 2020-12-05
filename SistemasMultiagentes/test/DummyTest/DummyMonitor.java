/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DummyTest;

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
public class DummyMonitor {
    static HttpServer server;
    static int nVisitas;
    
    DummyMonitor() throws IOException{
        DummyMonitor.nVisitas=0;
        DummyMonitor.server = HttpServer.create(new InetSocketAddress(3000), 0);
        HttpContext context = DummyMonitor.server.createContext("/");
        context.setHandler(DummyMonitor::handleRequest);
        DummyMonitor.server.start();
    }
    
    private static void handleRequest(HttpExchange exchange) throws IOException {
        DummyMonitor.nVisitas++; 
        switch(nVisitas){
            case 1:
                System.out.println("MONITOR VISITADO");
                String response=fileToString(".\\test\\XMLinput\\Dummy_reg_cliente.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
            case 2:
                System.out.println("DADO BAJA DE MONITOR");
                response=fileToString(".\\test\\XMLinput\\ACK.xml");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                server.stop(0);
                break;
            default:
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