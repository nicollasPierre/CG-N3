package br.com.furb.cg.n3.model;

public class BBox3D {
	private double xMin;
	private double yMin;
	private double zMin;
	private double xMax;
	private double yMax;
	private double zMax;

	public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getyMin() {
		return yMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public double getzMin() {
		return zMin;
	}

	public void setzMin(double zMin) {
		this.zMin = zMin;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getyMax() {
		return yMax;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public double getzMax() {
		return zMax;
	}

	public void setzMax(double zMax) {
		this.zMax = zMax;
	}

	public boolean isInsideBBox(Ponto4D ponto){
		
		return (xMin <= ponto.obterX() && ponto.obterX() <= xMax  && yMin <= ponto.obterX() && ponto.obterX() <= yMax);
	}

}
