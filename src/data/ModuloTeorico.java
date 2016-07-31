package data;

public class ModuloTeorico {
	
	private int id_modulo;
	private int nivel;
	private byte[] pdf;
	
	public ModuloTeorico(int id, int l, byte[] p){
		this.id_modulo=id;
		this.nivel=l;
		this.pdf=p;
	}
	
	public ModuloTeorico(int id, int l){
		this.id_modulo=id;
		this.nivel=l;
		this.pdf=null;
	}

	public int getId_modulo() {
		return id_modulo;
	}

	public void setId_modulo(int id_modulo) {
		this.id_modulo = id_modulo;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	

}
