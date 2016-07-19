package data;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

public class User {
	
	private String DNI;
	private String fecha;
	private String nombre;
	private String pass;
	private int tipo;
	
	public User(){}
	
	public User(String dniP, String p, String name, String f, int t) {
		this.DNI=dniP;
		this.pass=p;
		this.nombre=name;
		this.fecha=f;
		this.tipo=t;
	}

	public String getDNI() {
		return DNI;
	}
	
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public String toString(){
		return "Soy "+this.nombre+"con DNI "+this.DNI;		
	}
	
	public String toXML() {
        String xml = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(out);
            encoder.writeObject(this); // serialize to XML
            encoder.close();
            xml = out.toString(); // stringify
        }
        catch(Exception e) { }
        return xml;
    }

}
