package data;

public class Imagen {
	
	private int id_imagen;
	private String normal;
	private String organico;
	private String inorganico;
	private String bn;
	private float alto;
	private float ancho;
	private int id_objeto;
	private int id_examen;
	
	public Imagen(){}
	
	public Imagen(int id, String n, String org, String ino, String blanco, float al, float anc,
			int objeto, int examen){
		this.id_imagen=id;
		this.normal=n;
		this.organico=org;
		this.inorganico=ino;
		this.bn=blanco;
		this.alto=al;
		this.ancho=anc;
		this.id_objeto=objeto;
		this.id_examen=examen;
	}

	/**
	 * @return the id_imagen
	 */
	public int getId_imagen() {
		return id_imagen;
	}

	/**
	 * @param id_imagen the id_imagen to set
	 */
	public void setId_imagen(int id_imagen) {
		this.id_imagen = id_imagen;
	}

	/**
	 * @return the normal
	 */
	public String getNormal() {
		return normal;
	}

	/**
	 * @param normal the normal to set
	 */
	public void setNormal(String normal) {
		this.normal = normal;
	}

	/**
	 * @return the organico
	 */
	public String getOrganico() {
		return organico;
	}

	/**
	 * @param organico the organico to set
	 */
	public void setOrganico(String organico) {
		this.organico = organico;
	}

	/**
	 * @return the inorganico
	 */
	public String getInorganico() {
		return inorganico;
	}

	/**
	 * @param inorganico the inorganico to set
	 */
	public void setInorganico(String inorganico) {
		this.inorganico = inorganico;
	}

	/**
	 * @return the bn
	 */
	public String getBn() {
		return bn;
	}

	/**
	 * @param bn the bn to set
	 */
	public void setBn(String bn) {
		this.bn = bn;
	}

	/**
	 * @return the alto
	 */
	public float getAlto() {
		return alto;
	}

	/**
	 * @param alto the alto to set
	 */
	public void setAlto(float alto) {
		this.alto = alto;
	}

	/**
	 * @return the ancho
	 */
	public float getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(float ancho) {
		this.ancho = ancho;
	}

	/**
	 * @return the id_objeto
	 */
	public int getId_objeto() {
		return id_objeto;
	}

	/**
	 * @param id_objeto the id_objeto to set
	 */
	public void setId_objeto(int id_objeto) {
		this.id_objeto = id_objeto;
	}

	/**
	 * @return the id_examen
	 */
	public int getId_examen() {
		return id_examen;
	}

	/**
	 * @param id_examen the id_examen to set
	 */
	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}

}
