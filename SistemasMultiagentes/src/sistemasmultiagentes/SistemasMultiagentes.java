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

/**
 *
 * @author Grupo 2
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
        int puerto;
        //Scanner teclado = new Scanner(System.in);
        
        /*System.out.println("Introduce el ip del monitor:");
        ip = teclado.nextLine();
        System.out.println("Introduce el puerto del monitor:");
        puerto = teclado.nextInt();*/
        
        //ip = "172.19.164.14";    //Pablo
        ip = "172.19.164.151";     //David
        //ip = "localhost";
        puerto = 3000;
        
        for (int i = 0; i < nCompradores; i++) {
            crearFicheroLog(i);
            Cliente comprador = new Cliente(ip,puerto,i); //Pasar IP al constructor 
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
                            System.out.println(ex.toString());
                            System.out.println("  EXCEPCIÓN EN CLIENTE " + comprador.getId_interno());
                            comprador.pw.println("\n\n\n  EXCEPCIÓN EN CLIENTE " + comprador.getId_interno());
                            comprador.pw.println(ex.toString());
                            comprador.fichero.close();
                            throw ex;
                        } catch (IOException ex1) { }
                    }
                }
            }.start();
        }
    }
    
    public static void crearFicheroLog(int n) throws IOException{
        String ruta= ".\\logs";
        File carpeta=new File(ruta);
        carpeta.mkdir();
        File f = new File(ruta+"\\cliente" + n + ".txt");
        FileWriter fichero = new FileWriter(ruta+"\\cliente" + n + ".txt");
        PrintWriter pw = new PrintWriter(fichero);
        pw.println("\n COMPRADOR " + n + " creado a las " + LocalTime.now() + "\n");
        fichero.close();
    }
    
}
