/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.Objects;

/**
 *
 * @author Grupo 2
 */
public class Producto {
    
    private int id;
    private int cantidad;

    public Producto(int id, int cantidad){
        this.id = id;
        this.cantidad = cantidad;
    }
    
    /**
     * Añade cantidad al Producto.
     * @param cantidad Cantidad del Producto.
     * @return La cantidad nueva del producto, o "null" si hay algún error.
    */
    public Integer sumaCantidad(int cantidad){
        this.cantidad += cantidad;
        return this.cantidad;
    }
    
    /**
     * Resta cantidad al Producto.
     * @param cantidad Cantidad del Producto.
     * @return La cantidad nueva del producto, o "null" si hay algún error o
     * la cantidad a restar es mayor que la que había previamente.
    */
    public Integer restaCantidad(int cantidad){
        if(cantidad < this.cantidad) {
            this.cantidad -= cantidad;
            return this.cantidad;
        }
        return null;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString(){
        return "\n   Producto " + id + ", cantidad " + cantidad;
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
        if (!(o instanceof Producto))
            return false;

        Producto that = (Producto) o;
        return super.equals(that)
            && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

}
