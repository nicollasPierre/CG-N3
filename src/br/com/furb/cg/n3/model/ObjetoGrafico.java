package br.com.furb.cg.n3.model;

import java.util.ArrayList;

import javax.media.opengl.GL;
public final class ObjetoGrafico {
	GL gl;
	private float tamanho = 2.0f;
	private Ponto4D verticeSelecionado;
	private int primitiva = GL.GL_LINE_LOOP;
	private ArrayList<Ponto4D> vertices = new ArrayList<>();
	private BBox3D bbox = new BBox3D();
	private ArrayList<ObjetoGrafico> childrenObjects = new ArrayList<>();
	private boolean isChild = false;
	private int[] cor = { 0, 0, 0 };
//	private int primitiva = GL.GL_POINTS;
//	private Ponto4D[] vertices = { new Ponto4D(10.0, 10.0, 0.0, 1.0) };	

	private Transformacao4D matrizObjeto = new Transformacao4D();

	/// Matrizes temporarias que sempre sao inicializadas com matriz Identidade entao podem ser "static".
	private static Transformacao4D matrizTmpTranslacao = new Transformacao4D();
	private static Transformacao4D matrizTmpTranslacaoInversa = new Transformacao4D();
	private static Transformacao4D matrizTmpEscala = new Transformacao4D();		
//	private static Transformacao4D matrizTmpRotacaoZ = new Transformacao4D();
	private static Transformacao4D matrizGlobal = new Transformacao4D();
//	private double anguloGlobal = 0.0;
	
	public ObjetoGrafico() {
		
	}

	public void atribuirGL(GL gl) {
		this.gl = gl;
	}

	public double obterTamanho() {
		return tamanho;
	}

	public double obterPrimitava() {
		return primitiva;
	}

	public void setCor(int[] cor){
		this.cor = cor;
	}
	
	public int[] getCor(){
		return this.cor;
	}
	
	public BBox3D getBbox() {
		//bbox.setBBox3D(this);
		return bbox;
	}

	public void setBbox() {
		this.bbox.setBBox3D(this);
	}

	public Ponto4D getVerticeSelecionado() {
		return verticeSelecionado;
	}

	public void setVerticeSelecionado(Ponto4D verticeSelecionado) {
		this.verticeSelecionado = verticeSelecionado;
	}

	public void desenha() {
		//gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glColor3f(cor[0], cor[1], cor[2]);
		gl.glLineWidth(tamanho);
		gl.glPointSize(tamanho);

		gl.glPushMatrix();
			gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
			gl.glBegin(primitiva);
				for (Ponto4D vertice : vertices) {
					gl.glVertex2d(vertice.obterX(), vertice.obterY());
				}
			gl.glEnd();

		gl.glPopMatrix();

		for (ObjetoGrafico child : childrenObjects) {
			child.desenha();
		}

	}

	public void desenhaBBox(){
		gl.glBegin(primitiva);
			gl.glColor3f(255,0,0);
			gl.glLineWidth(1.0f);
			gl.glPointSize(tamanho);
			gl.glVertex2d(this.getBbox().getxMax(), this.getBbox().getyMax());
			gl.glVertex2d(this.getBbox().getxMax(), this.getBbox().getyMin());
			gl.glVertex2d(this.getBbox().getxMin(), this.getBbox().getyMin());
			gl.glVertex2d(this.getBbox().getxMin(), this.getBbox().getyMax());
		gl.glEnd();
	}

	public void translacaoXYZ(double tx, double ty, double tz) {
		Transformacao4D matrizTranslate = new Transformacao4D();
		matrizTranslate.atribuirTranslacao(tx,ty,tz);
		matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);

		for (ObjetoGrafico child : childrenObjects) {
			child.translacaoXYZ(tx, ty, tz);
		}
	}

	public void escalaXYZ(double Sx,double Sy) {
		Transformacao4D matrizScale = new Transformacao4D();		
		//matrizScale.atribuirEscala(Sx,Sy,1.0);
		matrizObjeto = matrizScale.transformMatrix(matrizObjeto);
		Ponto4D ponto = this.getBbox().getCenterPoint();
		ponto = ponto.inverterSinal(ponto);
		this.escalaXYZPtoFixo(Sx, Sy, ponto);

		for (ObjetoGrafico child : childrenObjects) {
			child.escalaXYZ(Sx, Sy);
		}
	}

	public double obterXMinimo(){
		double menor = Double.MAX_VALUE;
		for (int i = 0; i < getVertices().size(); i++) {
			if(getVertices().get(i).obterX() < menor){
				menor = getVertices().get(i).obterX();
			}
		}
		return menor;
	}
	
	public double obterYMinimo(){
		double menor = Double.MAX_VALUE;
		for (int i = 0; i < getVertices().size(); i++) {
			if(getVertices().get(i).obterY() < menor){
				menor = getVertices().get(i).obterY();
			}
		}
		return menor;
	}
	
	public double obterXMaximo(){
		double maior = Double.MIN_VALUE;
		for (int i = 0; i < getVertices().size(); i++) {
			if(getVertices().get(i).obterX() > maior){
				maior = getVertices().get(i).obterX();
			}
		}
		return maior;
	}
	
	public double obterYMaximo(){
		double maior = Double.MIN_VALUE;
		for (int i = 0; i < getVertices().size(); i++) {
			if(getVertices().get(i).obterY() > maior){
				maior = getVertices().get(i).obterY();
			}
		}
		return maior;
	}
	
	public Ponto4D obterPontoCentral(){
		double x = (this.obterXMinimo() + this.obterXMaximo()) / 2d;
		double y = (this.obterYMinimo() + this.obterYMaximo()) / 2d;
		return new Ponto4D(x, y, 0.0, 0.0);
	}
	
	///TODO: erro na rotacao
	public void rotacaoZ(double angulo) {
		Transformacao4D matrizRotacao = new Transformacao4D();
		
		matrizRotacao.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		Ponto4D ponto = this.getBbox().getCenterPoint();
		
		this.rotacaoZPtoFixo(angulo, ponto.inverterSinal(ponto));

		for (ObjetoGrafico child : childrenObjects) {
			child.rotacaoZ(angulo);
		}

	}
	
	public void atribuirIdentidade() {
//		anguloGlobal = 0.0;
		matrizObjeto.atribuirIdentidade();
	}

	public void escalaXYZPtoFixo(double Sx, double Sy, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirEscala(Sx, Sy, 1.0);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);

		matrizObjeto.exibeMatriz();
		this.exibeVertices();
	}
	
	public void rotacaoZPtoFixo(double angulo, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
	}

	public void exibeMatriz() {
		matrizObjeto.exibeMatriz();
	}

	public void exibeVertices() {
		for (Ponto4D vertice : vertices) {

			System.out.println("P0[" + vertice.obterX() + "," + vertice.obterY() + "," + vertice.obterZ() + "," + vertice.obterW() + "]");	
		}
//		System.out.println("anguloGlobal:" + anguloGlobal);
	}
	
	public void setVertice(ArrayList<Ponto4D> vertices){
		this.vertices = vertices;
	}

	public boolean isClickInside(Ponto4D clickPoint) {
		if(getBbox().isInsideBBox(clickPoint)) {
			int nrIntersections = 0;

			for (int i=0; i < vertices.size(); i++) {

				Ponto4D pi = vertices.get(i);

				Ponto4D pP = vertices.get(0);
				if(i < vertices.size()-1) {
					pP = vertices.get(i+1);
				}

				if(pi.obterY() != pP.obterY()) {
					Ponto4D intersectionPoint = discoverIntersection(pi, pP, clickPoint.obterY());
					if(intersectionPoint.obterX() == clickPoint.obterX()) {
						return true; //Está sobre o lado
					} else {
						if(intersectionPoint.obterX() > clickPoint.obterX()
								&& clickPoint.obterY() > getSmaller(pi.obterY(),pP.obterY())
								&& clickPoint.obterY() <=  getBigger(pi.obterY(),pP.obterY())) {
							nrIntersections++;
						}
					}
				} else {
					if(clickPoint.obterY() == pi.obterY()
							&& clickPoint.obterX() >= getSmaller(pi.obterX(),pP.obterX())
							&& clickPoint.obterX() <=  getBigger(pi.obterX(),pP.obterX())) {
						return true; // Está sobre o lado horizontal
					}
				}
			}
			System.out.println("Click object? " + isOdd(nrIntersections) + ". Amount of intersections: "+nrIntersections);
			return isOdd(nrIntersections);
		} else {
			System.out.println("Click not inside BBox");
			return false;
		}
	}

	public Ponto4D discoverIntersection(Ponto4D p1, Ponto4D p2, double y) {
		double t = (y - p1.obterY()) / (p2.obterY() - p1.obterY());
		double x = p1.obterX() + (p2.obterX() - p1.obterX())*t;
		return new Ponto4D(x, y,0,0);
	}

	private double getSmaller(double val1, double val2){
		return val1 < val2 ? val1 : val2;
	}

	private double getBigger(double val1, double val2) {
		return val1 > val2 ? val1 : val2;
	}

	private boolean isOdd(int number){
		return number % 2 != 0;
	}
	
	public ArrayList<Ponto4D> getVertices(){
		return vertices;
	}

	public Transformacao4D getMatrizObjeto() {
		return matrizObjeto;
	}

	public void setMatrizObjeto(Transformacao4D matrizObjeto) {
		this.matrizObjeto = matrizObjeto;
	}
	
	public double getMatrizObjeto(int pos){
		return matrizObjeto.getMatriz()[pos];
	}

	public void addChild(ObjetoGrafico child) {
		this.childrenObjects.add(child);
	}

	public ArrayList<ObjetoGrafico> getChildren() {
		return this.childrenObjects;
	}

	public boolean isChild() {
		return this.isChild;
	}

	public void setIsChild(boolean isChild) {
		this.isChild = isChild;
	}
}

