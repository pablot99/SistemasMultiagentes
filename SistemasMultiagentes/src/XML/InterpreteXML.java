/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sistemasmultiagentes.Producto;
import sistemasmultiagentes.Tienda;

/**
 *
 * @author bitde
 */
public class InterpreteXML {
    String schemaPath = ".//src//XML//TodosMensajes.xsd";
    public InterpreteXML(){
        
    }
    
    public Object[] leeAltaMonitor(String XML){
        Object[] r = null;
        if(validateSchema(XML)){
            //convertimos string a DOM
            Document doc = convertStringToXMLDocument(XML);
            //Obtenemos nuestro id
            String s_id= doc.getElementsByTagName("id_receptor").item(0).getTextContent();
            int id = Integer.parseInt(s_id);
            r[0]=id;
            //Obtenemos la lista de productos
            NodeList p = doc.getElementsByTagName("producto");
            HashMap<Integer, Producto> productos = new HashMap<>();
            for(int i=0; i<p.getLength();i++){
                Node iNode = p.item(i);
                if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element iElement = (Element) iNode;
                    int id_p= Integer.parseInt(iElement.getElementsByTagName("id_producto").item(0).getTextContent());
                    int cant_p= Integer.parseInt(iElement.getElementsByTagName("cantidad").item(0).getTextContent());
                    productos.put(id_p, new Producto(id_p, cant_p));
                } 
            }
            r[1]=productos;
            //Obtenemos la lista de tiendas
            HashSet<Tienda> tiendas = new HashSet<>();
            NodeList t = doc.getElementsByTagName("tienda");
            for(int i=0; i<p.getLength();i++){
                Node iNode = p.item(i);
                if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element iElement = (Element) iNode;
                    int id_t= Integer.parseInt(iElement.getElementsByTagName("id_tienda").item(0).getTextContent());
                    String ip_t= iElement.getElementsByTagName("ip_tienda").item(0).getTextContent();
                    int puerto_t= Integer.parseInt(iElement.getElementsByTagName("puerto").item(0).getTextContent());
                    tiendas.add(new Tienda(id_t, ip_t, puerto_t));
                } 
            }
            r[2]=tiendas;
        }
        else{
            r=null;
        }
        
        return r;
    }
    
    public String escribeAltaTienda(int id_c, Tienda t, HashMap<Integer, Producto> p){
        return null;
    }
    
    public ArrayList<Producto> leeCompra(String XML){
        if(validateSchema(XML)){
            
        }
        else{
            
        }
        return null;
    }
    
    public String escribeBajaTienda(int id_c, Tienda t){
        return null;
    }
    
    public ArrayList<Tienda> leeTiendasConocidas(String XML){
        if(validateSchema(XML)){
            
        }
        else{
            
        }
        return null;
    }
    
    private boolean validateSchema(String XML){
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(this.schemaPath));
            Validator validator = schema.newValidator();
            Reader reader = new StringReader(XML);
            validator.validate(new StreamSource(reader));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
    
    private static Document convertStringToXMLDocument(String xmlString) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}
