package data;

public class ExamenTeorico {
	
	private int idTeorico;
	private String nombre;
	private String descripcion;
	private int tiempo;
	private int numPreguntas;
	private int nivel;
	
	public ExamenTeorico(){	}
	
	public ExamenTeorico(int id, String name, String d, int t, int num, int l){
		this.idTeorico=id;
		this.nombre=name;
		this.descripcion=d;
		this.tiempo=t;
		this.numPreguntas=num;
		this.nivel=l;
	}

	public int getIdTeorico() {
		return idTeorico;
	}

	public void setIdTeorico(int idTeorico) {
		this.idTeorico = idTeorico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getNumPreguntas() {
		return numPreguntas;
	}

	public void setNumPreguntas(int numPreguntas) {
		this.numPreguntas = numPreguntas;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}	

}
