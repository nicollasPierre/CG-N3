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

	
	
	public BBox3D getBbox() {
		bbox.setBBox3D(this);
		return bbox;
	}

	public void setBbox(BBox3D bbox) {
		this.bbox = bbox;
	}

	public Ponto4D getVerticeSelecionado() {
		return verticeSelecionado;
	}

	public void setVerticeSelecionado(Ponto4D verticeSelecionado) {
		this.verticeSelecionado = verticeSelecionado;
	}

	public void desenha() {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(tamanho);
		gl.glPointSize(tamanho);

		gl.glPushMatrix();
			gl.glMultMatrixd(matrizObjeto.GetDate(), 0);
			gl.glBegin(primitiva);
				for (Ponto4D vertice : vertices) {
					gl.glVertex2d(vertice.obterX(), vertice.obterY());
				}
			gl.glEnd();

			//////////// ATENCAO: chamar desenho dos filhos... 

		gl.glPopMatrix();
	}

	public void translacaoXYZ(double tx, double ty, double tz) {
		Transformacao4D matrizTranslate = new Transformacao4D();
		matrizTranslate.atribuirTranslacao(tx,ty,tz);
		matrizObjeto = matrizTranslate.transformMatrix(matrizObjeto);		
	}

	public void escalaXYZ(double Sx,double Sy) {
		Transformacao4D matrizScale = new Transformacao4D();		
		matrizScale.atribuirEscala(Sx,Sy,1.0);
		matrizObjeto = matrizScale.transformMatrix(matrizObjeto);
	}

	///TODO: erro na rotacao
	public void rotacaoZ(double angulo) {
//		anguloGlobal += 10.0; // rotacao em 10 graus
//		Transformacao4D matrizRotacaoZ = new Transformacao4D();		
//		matrizRotacaoZ.atribuirRotacaoZ(Transformacao4D.DEG_TO_RAD * angulo);
//		matrizObjeto = matrizRotacaoZ.transformMatrix(matrizObjeto);
	}
	
	public void atribuirIdentidade() {
//		anguloGlobal = 0.0;
		matrizObjeto.atribuirIdentidade();
	}

	public void escalaXYZPtoFixo(double escala, Ponto4D ptoFixo) {
		matrizGlobal.atribuirIdentidade();

		matrizTmpTranslacao.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacao.transformMatrix(matrizGlobal);

		matrizTmpEscala.atribuirEscala(escala, escala, 1.0);
		matrizGlobal = matrizTmpEscala.transformMatrix(matrizGlobal);

		ptoFixo.inverterSinal(ptoFixo);
		matrizTmpTranslacaoInversa.atribuirTranslacao(ptoFixo.obterX(),ptoFixo.obterY(),ptoFixo.obterZ());
		matrizGlobal = matrizTmpTranslacaoInversa.transformMatrix(matrizGlobal);

		matrizObjeto = matrizObjeto.transformMatrix(matrizGlobal);
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

				if()

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
			System.out.println("Click inside BBox? " + isOdd(nrIntersections) + ". Amount of intersections: "+nrIntersections);
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

	
}

