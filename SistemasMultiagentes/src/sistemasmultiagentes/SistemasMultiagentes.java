/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pablo
 */
public class SistemasMultiagentes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<Cliente> compradores = new ArrayList<>();
        int i = 0;
        String ip;
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Introduce el ip del monitor:");
        ip = teclado.nextLine();
        
        while(i<100){
            Cliente comprador = new Cliente(ip,i); //Pasar IP al constructor 
            compradores.add(comprador);
            i++;
        }
        
        for(Cliente comprador : compradores){
            final Cliente aux = comprador;
            new Thread(){
                @Override
                public void run(){
                    System.out.println("Se ha creado un nuevo comprador");
                    aux.funcionDelCliente();
                }
            }.start();
        }
    }
    
    public static void crearFicheroLog(){
        
    }
    
}
