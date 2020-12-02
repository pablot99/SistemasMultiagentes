/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 *
 * @author bitde
 */
public class BasicHttpServerExample {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/example");
        context.setHandler(BasicHttpServerExample::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        
        printRequestInfo(exchange);
        String response = "This is the response at " + requestURI + ": ";
        
        InputStream inputStream = exchange.getRequestBody();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            sb.append(new String(res, 0, i));
        }
        inputStream.close();
        System.out.println("caca");
        System.out.println("caca"+sb);
        response=response+sb;
        System.out.println(response);
        
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void printRequestInfo(HttpExchange exchange) throws IOException {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);

        System.out.println("-- principle --");
        HttpPrincipal principal = exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
        /* No se debe abrir 2 veces el getRequestBody
        System.out.println("-- InputStream --");
        InputStream inputStream = exchange.getRequestBody();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            sb.append(new String(res, 0, i));
        }
        inputStream.close();
        System.out.println(sb);*/
    }
}
