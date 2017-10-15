package br.com.furb.cg.n3.model;

import java.util.ArrayList;

public class BBox3D {
	private double xMin;
	private double yMin;
	private double zMin;
	private double xMax;
	private double yMax;
	private double zMax;

	
	public BBox3D() {
		super();
		xMin = Double.MAX_VALUE;
		yMin = Double.MAX_VALUE;
		zMin = Double.MAX_VALUE;
		xMax = Double.MIN_VALUE;
		yMax = Double.MIN_VALUE;
		zMax = Double.MIN_VALUE;
	}

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

	public boolean isInsideBBox(Ponto4D ponto) {

		return (xMin <= ponto.obterX() && ponto.obterX() <= xMax && yMin <= ponto.obterY() && ponto.obterY() <= yMax);
	}

	public void setBBox3D(ObjetoGrafico objeto) {
		xMin = Double.MAX_VALUE;
		yMin = Double.MAX_VALUE;
		zMin = Double.MAX_VALUE;
		xMax = Double.MIN_VALUE;
		yMax = Double.MIN_VALUE;
		zMax = Double.MIN_VALUE;
 		for (Ponto4D ponto4d : objeto.getVertices()) {
			if (ponto4d.obterX() < xMin) {
				xMin = ponto4d.obterX();
			}

			if (ponto4d.obterX() > xMax) {
				xMax = ponto4d.obterX();
			}

			if (ponto4d.obterY() < yMin) {
				yMin = ponto4d.obterY();
			}

			if (ponto4d.obterY() > yMax) {
				yMax = ponto4d.obterY();
			}

			if (ponto4d.obterZ() < zMin) {
				zMin = ponto4d.obterZ();
			}

			if (ponto4d.obterZ() > zMax) {
				zMax = ponto4d.obterZ();
			}
		}
	}
}
