package data;

public class ExamenPractico {
	
	private int idPractico;
	private int nivel;
	private int numImagenes;
	
	public ExamenPractico(){}
	
	public ExamenPractico(int id, int l, int n){
		this.idPractico=id;
		this.nivel=l;
		this.numImagenes=n;
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
	
	

}
