/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasmultiagentes;

import java.util.Collection;
import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author pablo
 */
public class Productos {
    
    private final HashMap<Integer,Integer> productos;
    
    /**
     * Constructor para los Productos. Inicializa el HashMap de Productos 
     * con el ID y la cantidad de cada producto dados por el Monitor 
     * en el "mensajeAltaMonitor()".
     * @param prods Tabla con el ID y la cantidad para cada producto proporcionado.
    */
    public Productos(Integer[][] prods){
        productos = new HashMap<>();
        
        /* Se añade cada uno de los productos al HashMap, siendo
         * siendo prod[0] el ID del producto, y prod[1] la cantidad */
        for (Integer[] prod : prods) {
            this.productos.put(prod[0], prod[1]);
        }
    }
    
    //Crear una lista de productos a partir de un XML
    public Productos(Document DOM){
         productos = new HashMap<>();
    }
    
    /**
     * Añade cantidad a un Producto determinado con su ID o, en caso de no
     * existir dicho Producto, lo crea con la cantidad especificada.
     * @param id ID del Producto.
     * @param cantidad Cantidad del Producto.
     * @return La cantidad anterior del producto, o "null" si hay algún error.
    */
    public Integer masProducto(int id, int cantidad){
        if (getProductos().containsKey(id))
            return getProductos().put(id,getProductos().get(id) + cantidad);
        else return getProductos().put(id,cantidad);
    }
    
    /**
     * Resta cantidad a un Producto determinado con su ID.
     * @param id ID del Producto.
     * @param cantidad Cantidad del Producto.
     * @return La cantidad anterior del producto, o "null" si hay algún error o
     * la cantidad a restar es mayor que la que había previamente.
    */
    public Integer menosProducto(int id, int cantidad){
        if (getProductos().containsKey(id)){
            if (getProductos().get(id) >= cantidad){
                return getProductos().put(id,getProductos().get(id) - cantidad);
            }
        }
        return null;
    }
    
    /**
     * Devuelve la cantidad de un Producto.
     * @param id ID del Producto.
     * @return La cantidad del producto.
    */
    public Integer nProducto(int id){
        return getProductos().get(id);
    }

    @Override
    public String toString(){
        String aux = "\n";
        for (Integer id: productos.keySet()){
               aux += ("   Producto " + id + ".\tCantidad:" + productos.get(id) + "\n");  
        } 
        return aux;
    }
    
    /**
     * @return El mapa con los ID de los Productos y sus Cantidades.
     */
    public HashMap<Integer,Integer> getProductos() {
        return productos;
    }
    
    /**
     * Devuelve si hay algún producto en el HashMap con cantidad distinta de 0.
     * @return Si quedan productos por comprar o no.
     */
    public boolean finalizado(){
        Collection<Integer> valores = productos.values();
        for (Integer valor : valores) {
            if (valor != 0) return false;
        }
        return true;
    }
    
}
