/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pablo
 */
public class SistemasMultiagentes {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        int nCompradores = 10;
        
        ArrayList<Cliente> compradores = new ArrayList<>();
        String ip;
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Introduce el ip del monitor:");
        ip = teclado.nextLine();
        
        for (int i = 0; i < nCompradores; i++) {
            Cliente comprador = new Cliente(ip,i); //Pasar IP al constructor 
            compradores.add(comprador);
            crearFicheroLog(comprador, i);
        }
        /*
        for(Cliente comprador : compradores){
            new Thread(){
                @Override
                public void run(){
                    comprador.funcionDelCliente();
                }
            }.start();
        }*/
    }
    
    public static void crearFicheroLog(Cliente comprador, int n) throws IOException{
        FileWriter fichero = new FileWriter(".\\logs\\cliente" + n + ".txt");
        PrintWriter pw = new PrintWriter(fichero);
        fichero.close();
    }
    
}
