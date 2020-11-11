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
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            crearFicheroLog(i);
            Cliente comprador = new Cliente(ip,i); //Pasar IP al constructor 
            compradores.add(comprador);
        }
        
        for(Cliente comprador : compradores){
            new Thread(){
                @Override
                public void run(){
                    try {
                        comprador.funcionDelCliente();
                    } catch (Exception ex) {
                        try {
                            System.out.println("  EXCEPCIÓN EN CLIENTE " + comprador.getId_interno());
                            comprador.pw.println("\n\n\n  EXCEPCIÓN EN CLIENTE " + comprador.getId_interno());
                            comprador.fichero.close();
                            throw ex;
                        } catch (IOException ex1) { }
                    }
                }
            }.start();
        }
    }
    
    public static void crearFicheroLog(int n) throws IOException{
        FileWriter fichero = new FileWriter(".\\logs\\cliente" + n + ".txt");
        PrintWriter pw = new PrintWriter(fichero);
        pw.println("\n COMPRADOR " + n + " creado a las " + LocalTime.now() + "\n");
        fichero.close();
    }
    
}
