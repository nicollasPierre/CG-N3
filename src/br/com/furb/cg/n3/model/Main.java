package br.com.furb.cg.n3.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	// private ObjetoGrafico objeto = new ObjetoGrafico();
	private ArrayList<ObjetoGrafico> objetos = new ArrayList<>();

	private Mundo mundo = new Mundo();

	// Váriaveis do mouse
	private double valorX = 0.0, valorY = 0.0;
	private int antigoX, antigoY = 0;

	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		for (ObjetoGrafico objeto : objetos) {
			objeto.atribuirGL(gl);
		}
		// objeto.atribuirGL(gl);
	}

	// metodo definido na interface GLEventListener.
	// "render" feito pelo cliente OpenGL.
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		glu.gluOrtho2D(-30.0f, 30.0f, -30.0f, 30.0f);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);
		
		/*gl.glBegin(GL.GL_LINE_LOOP);
		double testeX = 1.0+20, testeY = 1.0+20;
			gl.glVertex2d(testeX, testeY);
			gl.glVertex2d(testeX-5, testeY);
			gl.glVertex2d(testeX, testeY-5);
		gl.glEnd();*/
		desenhaSRU();
		for (ObjetoGrafico objeto : objetos) {
			objeto.desenha();
		}

		// objeto.desenha();

		gl.glFlush();
	}

	public void desenhaSRU() {
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-20.0f, 0.0f);
		gl.glVertex2f(20.0f, 0.0f);
		gl.glEnd();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -20.0f);
		gl.glVertex2f(0.0f, 20.0f);
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
//		case KeyEvent.VK_P:
//			objetos[0].exibeVertices();
//			break;
//		case KeyEvent.VK_M:
//			objetos[0].exibeMatriz();
//			break;
//
//		case KeyEvent.VK_R:
//			objetos[0].atribuirIdentidade();
//			break;
//
//		case KeyEvent.VK_RIGHT:
//			objetos[0].translacaoXYZ(2.0, 0.0, 0.0);
//			break;
//		case KeyEvent.VK_LEFT:
//			objetos[0].translacaoXYZ(-2.0, 0.0, 0.0);
//			break;
//		case KeyEvent.VK_UP:
//			objetos[0].translacaoXYZ(0.0, 2.0, 0.0);
//			break;
//		case KeyEvent.VK_DOWN:
//			objetos[0].translacaoXYZ(0.0, -2.0, 0.0);
//			break;
//
//		case KeyEvent.VK_PAGE_UP:
//			objetos[0].escalaXYZ(2.0, 2.0);
//			break;
//		case KeyEvent.VK_PAGE_DOWN:
//			objetos[0].escalaXYZ(0.5, 0.5);
//			break;
//
//		case KeyEvent.VK_HOME:
//			// objetos[0].RoracaoZ();
//			break;
//
//		case KeyEvent.VK_1:
//			objetos[0].escalaXYZPtoFixo(0.5, new Ponto4D(-15.0, -15.0, 0.0, 0.0));
//			break;
//
//		case KeyEvent.VK_2:
//			objetos[0].escalaXYZPtoFixo(2.0, new Ponto4D(-15.0, -15.0, 0.0, 0.0));
//			break;
//
//		case KeyEvent.VK_3:
//			objetos[0].rotacaoZPtoFixo(10.0, new Ponto4D(-15.0, -15.0, 0.0, 0.0));
//			break;
			
		case KeyEvent.VK_SPACE:
			if(mundo.getPoligonoSelecionado() != null){	
				mundo.getPoligonoSelecionado().exibeVertices();
				mundo.getPoligonoSelecionado().atribuirGL(gl);
				objetos.add(mundo.getPoligonoSelecionado());
				mundo.setPoligonoSelecionado(null);
			}
			break;
		}

		glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		// System.out.println(" --- reshape ---");
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		// System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		// System.out.println(" --- keyTyped ---");
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//mouseMoviment(e.getX(), e.getY());

		glDrawable.display();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//mouseMoviment(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseUnitToGlUnit(e.getX(), e.getY());
		System.out.println("X: " + e.getX() + "\nY: " + e.getY());
		if (mundo.getPoligonoSelecionado() != null) {
//			mundo.getPoligonoSelecionado().getVertices().add(new Ponto4D(e.getX() -161.5, e.getY() -161.5, 0, 0));
			mundo.getPoligonoSelecionado().getVertices().add(new Ponto4D(valorX, valorY, 0, 0));
		} else {
			ObjetoGrafico poligno = new ObjetoGrafico();
//			poligno.getVertices().add(new Ponto4D(e.getX() -161.5, e.getY() -161.5, 0, 0));
			poligno.getVertices().add(new Ponto4D(valorX, valorY, 0, 0));
			mundo.setPoligonoSelecionado(poligno);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		antigoX = e.getX();
		antigoY = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	/*public void mouseMoviment(int x, int y){
		int movtoX = x - antigoX;
		int movtoY = y - antigoY;
		valorX += movtoX;
		valorY += movtoY;

		// Dump ...
		System.out.println("posMouse: " + movtoX + " / " + movtoY);

		antigoX = x;
		antigoY = y;
	}*/
	/**
	  * Converte a poisão da unidade do mouse (Tamanho da tela em pixel) para unidades do OpenGl (definida pelo Ortho2D)
	  * O valor convertido é colocado na variável valorX e valorY;
	  * 
	  * @param x   valor X do mouse
	  * @param y   valor Y do mouse
	  */
	public void mouseUnitToGlUnit(int x, int y){
		/*valorX = x*(((60.0*100.0)/400.0)/100.0)-30.0;
		valorY = (y*(((60.0*100.0)/422.0)/100.0)-30.0)*-1;*/
//		valorX = x+(-422.0+30.0);
//		valorY = (y+(-422.0+30.0))*-1;
		valorX = (30.0 - x/400.0 * 60.0) * -1.0;
		valorY = 30.0 - y/400.0 * 60.0;
		
		System.out.println("X = " + valorX);
		System.out.println("Y = " + valorY);
		//O valor 0.15 sai da formula de (distânciaXOrtho2D*100)/TamanhoXDaJanela
		// sendo DistânciaXOrtho2D = 60 e TamanhoXDaJanela = 400
		//ai converte o resultando em porcentagem dividindo por 100;
	}
}
