package br.com.furb.cg.n3.model;

public class ObjetoGrafico {
	private int[] cor;
	private Ponto4D[] listaPto;
	private BBox3D bBox;
	private int primitiva;
	public int[] getCor() {
		return cor;
	}
	public void setCor(int[] cor) {
		this.cor = cor;
	}
	public Ponto4D[] getListaPto() {
		return listaPto;
	}
	public void setListaPto(Ponto4D[] listaPto) {
		this.listaPto = listaPto;
	}
	public BBox3D getbBox() {
		return bBox;
	}
	public void setbBox(BBox3D bBox) {
		this.bBox = bBox;
	}
	public int getPrimitiva() {
		return primitiva;
	}
	public void setPrimitiva(int primitiva) {
		this.primitiva = primitiva;
	}
	public ObjetoGrafico(int[] cor, Ponto4D[] listaPto, BBox3D bBox, int primitiva) {
		super();
		this.cor = cor;
		this.listaPto = listaPto;
		this.bBox = bBox;
		this.primitiva = primitiva;
	}
	
	public void desenhar(){
		
	}
	
	
	
}
