package data;

public class Pregunta {
	
	private int id_pregunta;
	private String enunciado;
	private int id_examen;
	
	public Pregunta(){}
	
	public Pregunta(int id, String e, int exa){
		this.id_pregunta=id;
		this.enunciado=e;
		this.id_examen=exa;
	}

	public int getId_pregunta() {
		return id_pregunta;
	}

	public void setId_pregunta(int id_pregunta) {
		this.id_pregunta = id_pregunta;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public int getId_examen() {
		return id_examen;
	}

	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}

}
