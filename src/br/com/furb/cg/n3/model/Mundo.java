package br.com.furb.cg.n3.model;

import java.util.ArrayList;

public class Mundo {
	private Camera2D camera;
	private ObjetoGrafico poligonoSelecionado;
	private ArrayList<ObjetoGrafico> listaObjetos;
	private int[] corFundo = {0,0,0};
	
	
	public Mundo() {
		listaObjetos = new ArrayList<>();
		camera = new Camera2D(-30.0f, 30.0f, -30.0f, 30.0f);
	}
	
	public Camera2D getCamera() {
		return camera;
	}

	public void setCamera(Camera2D camera) {
		this.camera = camera;
	}

	public ObjetoGrafico getPoligonoSelecionado() {
		return poligonoSelecionado;
	}

	public void setPoligonoSelecionado(ObjetoGrafico poligonoSelecionado) {
		this.poligonoSelecionado = poligonoSelecionado;
	}

	public ArrayList<ObjetoGrafico> getListaObjetos() {
		return listaObjetos;
	}

	public void setListaObjetos(ArrayList<ObjetoGrafico> listaObjetos) {
		this.listaObjetos = listaObjetos;
	}
	
	public void addObjeto(ObjetoGrafico poligono){
		this.listaObjetos.add(poligono);
	}

	public int[] getCorFundo() {
		return corFundo;
	}
	
	public int getCorFundoR(){
		return corFundo[0];
	}
	
	public int getCorFundoG(){
		return corFundo[1];
	}
	
	public int getCorFundoB(){
		return corFundo[2];
	}
	
	public void setCorFundo(int R, int G, int B) {
		this.corFundo[0] = R;
		this.corFundo[1] = G;
		this.corFundo[2] = B;
	}
	
	
	/**
	 * Verifica a posição (x,y) está dentro da BBox de um objeto
	 * @param x
	 * @param y
	 * @return True se estiver, false se não
	 */
	public boolean lookIfInside(double x, double y){
		for (ObjetoGrafico objeto : getListaObjetos()) {
			if (objeto.isClickInside(new Ponto4D((x-objeto.getMatrizObjeto(12))/objeto.getMatrizObjeto(0), (y-objeto.getMatrizObjeto(13))/objeto.getMatrizObjeto(5), 0, 0))) {
				this.setPoligonoSelecionado(objeto);
				return true;
			}
		}
		return false;
	}
}
