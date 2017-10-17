package br.com.furb.cg.n3.model;

public class Camera2D {
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	public static final int ESQUERDA = 1;
	public static final int DIREITA = 2;
	public static final int CIMA = 3;
	public static final int BAIXO = 4;
	
	
	
	
	public Camera2D(double minX, double maxX, double minY, double maxY) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public void zoomIn(int quantidade) {
		maxY -= quantidade;
		maxX -= quantidade;
		minY += quantidade;
		minX += quantidade;
	}

	public void zoomOut(int quantidade) {
		maxY += quantidade;
		maxX += quantidade;
		minY -= quantidade;
		minX -= quantidade;
	}

	public void pan(int direcao, int quantidade) {
		switch (direcao) {
		case ESQUERDA:
			minX -= quantidade;
			maxX -= quantidade;
			break;
		case DIREITA:
			minX += quantidade;
			maxX += quantidade;
			break;
		case CIMA:
			maxY += quantidade;
			minY += quantidade;
			break;
		case BAIXO:
			minY -= quantidade;
			maxY -= quantidade;
			break;
		}
	}
	
	public String toString(){
		
		return "Camera:{minX: "+minX+", maxX: "+maxX+", minY: "+minY+", maxY: "+maxY+"}";
	}
}
