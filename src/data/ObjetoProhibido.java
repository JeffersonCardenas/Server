package data;

public class ObjetoProhibido {
	
	private int id_objeto;
	private String nombre;
	private int posx;
	private int posy;
	private int alto;
	private int ancho;
	private int id_arma;
	
	public ObjetoProhibido(){}
	
	public ObjetoProhibido(int id, String name, int x, int y, int height, int width, int arma){
		this.id_objeto=id;
		this.nombre=name;
		this.posx=x;
		this.posy=y;
		this.alto=height;
		this.ancho=width;
		this.id_arma=arma;
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the posx
	 */
	public int getPosx() {
		return posx;
	}

	/**
	 * @param posx the posx to set
	 */
	public void setPosx(int posx) {
		this.posx = posx;
	}

	/**
	 * @return the posy
	 */
	public int getPosy() {
		return posy;
	}

	/**
	 * @param posy the posy to set
	 */
	public void setPosy(int posy) {
		this.posy = posy;
	}

	/**
	 * @return the alto
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * @param alto the alto to set
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}

	/**
	 * @return the ancho
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
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

}
