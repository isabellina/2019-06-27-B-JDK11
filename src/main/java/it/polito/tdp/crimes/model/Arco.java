package it.polito.tdp.crimes.model;

public class Arco {
	
	private String reato;
	private String reato2;
	private int peso;
	
	
	
	public Arco(String reato, String reato2, int peso) {
		super();
		this.reato = reato;
		this.reato2 = reato2;
		this.peso = peso;
	}



	public String getReato() {
		return reato;
	}



	public void setReato(String reato) {
		this.reato = reato;
	}



	public String getReato2() {
		return reato2;
	}



	public void setReato2(String reato2) {
		this.reato2 = reato2;
	}



	public int getPeso() {
		return peso;
	}



	public void setPeso(int peso) {
		this.peso = peso;
	}



	@Override
	public String toString() {
		return "Arco [reato=" + reato + ", reato2=" + reato2 + ", peso=" + peso + "]";
	}
	
	

}
