/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DummyTest;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 *
 * @author 48261881
 */

public class DummyTienda {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/example");
        context.setHandler(DummyTienda::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        
        String response = "This is the response at "+ InetAddress.getLocalHost() + requestURI + ": ";
        
        InputStream inputStream = exchange.getRequestBody();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            sb.append(new String(res, 0, i));
        }
        inputStream.close();
        response=response+sb;
        System.out.println(response);
        
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    
}