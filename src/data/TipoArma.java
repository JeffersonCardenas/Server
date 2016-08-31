package data;

public class TipoArma {
	
	private int id_arma;
	private String descripcion;
	
	public TipoArma(){}
	
	public TipoArma(int id, String desc){
		this.id_arma=id;
		this.descripcion=desc;
	}

	/**
	 * @return the id_arma
	 */
	public int getId_arma() {
		return id_arma;
	}

	/**
	 * @param id_arma the id_arma to set
	 */
	public void setId_arma(int id_arma) {
		this.id_arma = id_arma;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
