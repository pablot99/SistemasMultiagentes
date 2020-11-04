/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.Objects;

/**
 *
 * @author pablo
 */
public class Tienda {
    
    int id;
    String ip;
    int puerto;
    String nombre;
    
    /**
     * Constructor para una Tienda. Inicializa los valores de id, ip y puerto.
     * @param id ID de la tienda.
     * @param ip IP de la tienda.
     * @param puerto Puerto de la tienda.
    */
    public Tienda(int id, String ip, int puerto, String nombre){
        this.id = id;
        this.ip = ip;
        this.puerto = puerto;
        this.nombre = nombre;
    }
    
    /**
     *
     * @param o Objeto a comparar.
     * @return True si los objetos son iguales y False si no lo son.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Tienda))
            return false;

        Tienda that = (Tienda) o;
        return super.equals(that)
            && Objects.equals(this.id, that.id)
            && Objects.equals(this.puerto, that.puerto)
            && Objects.equals(this.ip, that.ip)
            && Objects.equals(this.nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.ip);
        hash = 37 * hash + this.puerto;
        hash = 37 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
}
