package data;

public class ExamenPractico {
	
	private int idPractico;
	private int nivel;
	private int numImagenes;
	private int tiempo_examen;
	
	public ExamenPractico(){}
	
	public ExamenPractico(int id, int l, int n, int t){
		this.idPractico=id;
		this.nivel=l;
		this.numImagenes=n;
		this.tiempo_examen=t;
	}

	public int getIdPractico() {
		return idPractico;
	}

	public void setIdPractico(int idPractico) {
		this.idPractico = idPractico;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getNumImagenes() {
		return numImagenes;
	}

	public void setNumImagenes(int numImagenes) {
		this.numImagenes = numImagenes;
	}

	/**
	 * @return the tiempo_examen
	 */
	public int getTiempo_examen() {
		return tiempo_examen;
	}

	/**
	 * @param tiempo_examen the tiempo_examen to set
	 */
	public void setTiempo_examen(int tiempo_examen) {
		this.tiempo_examen = tiempo_examen;
	}	

}
