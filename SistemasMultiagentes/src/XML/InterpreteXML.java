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
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        Object[] r = new Object[3];
        if(validateSchema(XML)){
            //convertimos string a DOM
            Document doc = convertStringToXMLDocument(XML);
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
            for(int i=0; i<t.getLength();i++){
                Node iNode = t.item(i);
                if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element iElement = (Element) iNode;
                    int id_t = Integer.parseInt(iElement.getElementsByTagName("id_tienda").item(0).getTextContent());
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
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //elemento root 
            Element root = document.createElement("root");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            document.appendChild(root);
                //elemento head
                creaHead(document, root, "tienda", "entrada_tienda", id_c, t);
                //elemento body
                Element body = document.createElement("body");
                body.setAttribute("xsi:type", "entrada_tienda");
                root.appendChild(body);
                    //elemento lista_productos
                    Element lP = document.createElement("lista_productos");
                    body.appendChild(lP);
                        //elementos producto
                        for (Map.Entry<Integer, Producto> e : p.entrySet()) {
                            Element prod = document.createElement("producto");
                            lP.appendChild(prod);
                            addNodoTexto(document, prod, "id_producto", Integer.toString(e.getValue().getId()));
                            addNodoTexto(document, prod, "cantidad", Integer.toString(e.getValue().getCantidad()));
                        }
            return getStringFromDocument(document);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(InterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public HashMap<Integer,Producto> leeCompra(String XML){
        HashMap<Integer,Producto> p;
        if(validateSchema(XML)){
            //convertimos string a DOM
            Document doc = convertStringToXMLDocument(XML);
            
            //Obtenemos la lista de productos
            NodeList productos = doc.getElementsByTagName("producto");
            p = new HashMap<>();
            for(int i = 0; i < productos.getLength();i++){
                Node iNode = productos.item(i);
                if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element iElement = (Element) iNode;
                    int id_p = Integer.parseInt(iElement.getElementsByTagName("id_producto").item(0).getTextContent());
                    int cant_p = Integer.parseInt(iElement.getElementsByTagName("cantidad").item(0).getTextContent());
                    p.put(id_p, new Producto(id_p, cant_p));
                } 
            }
        }
        else{
            p = null;
        }
        
        return p;
    }
    
    
    public String escribeConsultaTiendas(int id_c, Tienda t, HashSet<Tienda> tiendas){
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //elemento root 
            Element root = document.createElement("root");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            document.appendChild(root);
                //elemento head
                creaHead(document, root, "tienda", "solicitar_tiendas", id_c, t);
                //elemento body
                Element body = document.createElement("body");
                body.setAttribute("xsi:type", "solicitar_tiendas");
                root.appendChild(body);
                    //elemento lista_productos
                    Element lP = document.createElement("lista_tiendas");
                    body.appendChild(lP);
                        //elementos producto
                        for (Tienda i:tiendas) {
                            Element tiend = document.createElement("tienda");
                            lP.appendChild(tiend);
                            addNodoTexto(document, tiend, "id_tienda", Integer.toString(i.getId()));
                            addNodoTexto(document, tiend, "ip_tienda", i.getIp());
                            addNodoTexto(document, tiend, "puerto", Integer.toString(i.getPuerto()));
                        }
            return getStringFromDocument(document);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(InterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public ArrayList<Tienda> leeTiendasConocidas(String XML){
        ArrayList<Tienda> tiendas = new ArrayList<>();
        if(validateSchema(XML)){
            //convertimos string a DOM
            Document doc = convertStringToXMLDocument(XML);

            //Obtenemos la lista de tiendas
            //HashSet<Tienda> tiendas = new HashSet<>();
            NodeList t = doc.getElementsByTagName("tienda");
            for(int i=0; i<t.getLength();i++){
                Node iNode = t.item(i);
                if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element iElement = (Element) iNode;
                    int id_t= Integer.parseInt(iElement.getElementsByTagName("id_tienda").item(0).getTextContent());
                    String ip_t= iElement.getElementsByTagName("ip_tienda").item(0).getTextContent();
                    int puerto_t= Integer.parseInt(iElement.getElementsByTagName("puerto").item(0).getTextContent());
                    tiendas.add(new Tienda(id_t, ip_t, puerto_t));
                } 
            }
            //t = tiendas;
        }
        else{
            tiendas = null;
        }
        
        return tiendas;
    }


    public String escribeBajaTienda(int id_c, Tienda t){
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //elemento root 
            Element root = document.createElement("root");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            document.appendChild(root);
                //elemento head
                creaHead(document, root, "tienda", "salida_tienda", id_c, t);
                //elemento body
                Element body = document.createElement("body");
                body.setAttribute("xsi:type", "salida_tienda");
                root.appendChild(body);
                  
            return getStringFromDocument(document);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(InterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String escribeBajaMonitor(int id_c, String ipMonitor, int puertoMonitor, HashMap<Integer, Producto> comprados){
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //elemento root 
            Element root = document.createElement("root");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            document.appendChild(root);
                //elemento head
                Tienda t=new Tienda(-1, ipMonitor, puertoMonitor);
                creaHead(document, root, "monitor", "finalizacion_cliente", id_c, t);
                //elemento body
                Element body = document.createElement("body");
                body.setAttribute("xsi:type", "finalizacion_cliente");
                root.appendChild(body);
                //elemento lista_productos
                    Element lP = document.createElement("lista_productos");
                    body.appendChild(lP);
                        //elementos producto
                        for (Map.Entry<Integer, Producto> e : comprados.entrySet()) {
                            Element prod = document.createElement("producto");
                            lP.appendChild(prod);
                            addNodoTexto(document, prod, "id_producto", Integer.toString(e.getValue().getId()));
                            addNodoTexto(document, prod, "cantidad", Integer.toString(e.getValue().getCantidad()));
                        }
                  
            return getStringFromDocument(document);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(InterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
    //crea el elemento head con su formato correcto y lo anade al elemento root
    private void creaHead(Document document, Element root, String tipoReceptor, String tipo_mensaje, int id_c, Tienda t){
        //elemento head
        Element head = document.createElement("head");
        root.appendChild(head);
            //elemento tipo_mensaje
            addNodoTexto(document, head, "tipo_mensaje", tipo_mensaje);
            
            //elemento tipo_emisor
            addNodoTexto(document, head, "tipo_emisor", "comprador");
            //elemento id emisor
            addNodoTexto(document, head, "id_emisor", Integer.toString(id_c));
            //elemento ip emisor
            try {
                addNodoTexto(document, head, "ip_emisor", InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                addNodoTexto(document, head, "ip_emisor", "error: pregunta a grupo 2");
                Logger.getLogger(InterpreteXML.class.getName()).log(Level.SEVERE, null, ex);
            }
            //elemento puerto emisor
            addNodoTexto(document, head, "puerto_emisor", "-1");
            
            //elemento tipo_receptor
            addNodoTexto(document, head, "tipo_receptor", tipoReceptor);
            //elemento id receptor
            addNodoTexto(document, head, "id_receptor", Integer.toString(t.getId()));
            //elemento ip receptor
            addNodoTexto(document, head, "ip_receptor", t.getIp());
            //elemento puerto receptor
            addNodoTexto(document, head, "puerto_receptor", Integer.toString(t.getPuerto()));
            
            //elemento marca de tiempo
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now(); 
            addNodoTexto(document, head, "time_sent", dtf.format(now));
    }
    
    //crea un nodo de solo texto y lo añade a un nodo padre
    private void addNodoTexto(Document document, Element padre, String etiqueta, String contenido){
        Element e = document.createElement(etiqueta);
        e.appendChild(document.createTextNode(contenido));
        padre.appendChild(e);
    }
    
    public boolean validateSchema(String XML){
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(this.schemaPath));
            Validator validator = schema.newValidator();
            Reader reader = new StringReader(XML);
            validator.validate(new StreamSource(reader));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
    //https://howtodoinjava.com/java/xml/parse-string-to-xml-dom/
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
    
    //https://stackoverflow.com/questions/10356258/how-do-i-convert-a-org-w3c-dom-document-object-to-a-string
    private String getStringFromDocument(Document doc){
        try
        {
           DOMSource domSource = new DOMSource(doc);
           StringWriter writer = new StringWriter();
           StreamResult result = new StreamResult(writer);
           TransformerFactory tf = TransformerFactory.newInstance();
           Transformer transformer = tf.newTransformer();
           transformer.transform(domSource, result);
           return writer.toString();
        }
        catch(TransformerException ex)
        {
           ex.printStackTrace();
           return null;
        }
    } 
}
