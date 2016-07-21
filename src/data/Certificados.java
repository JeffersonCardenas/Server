package data;

public class Certificados {
	
	private int nivel;
	private int limite;
	
	public Certificados(){
		
	}
	
	public Certificados(int n,int l){
		this.nivel=n;
		this.limite=l;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}
	
	

}
