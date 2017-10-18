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
	private ArrayList<Ponto4D> objetoTempVertices;
	private Ponto4D pontoMouse;
	private ObjetoGrafico objetoTemporario;

	private float red = 0;
	private float green = 0;
	private float blue = 0;
	
	private ArrayList<int[]> cores = new ArrayList<>();
	private int indiceCores = 0;
	
	private boolean nextIsChild = false;
	
	// private ObjetoGrafico objeto = new ObjetoGrafico();
	// private ArrayList<ObjetoGrafico> objetos = new ArrayList<>();

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

		for (ObjetoGrafico objeto : mundo.getListaObjetos()) {
			objeto.atribuirGL(gl);
		}
		// objeto.atribuirGL(gl);
		
		int[] cor0 = {0, 0, 0};
		int[] cor1 = {255, 0, 0};
		int[] cor2 = {0, 255, 0};
		cores.add(cor0);
		cores.add(cor1);
		cores.add(cor2);
	}

	// metodo definido na interface GLEventListener.
	// "render" feito pelo cliente OpenGL.
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		glu.gluOrtho2D(mundo.getCamera().getMinX(),
				mundo.getCamera().getMaxX(),
				mundo.getCamera().getMinY(),
				mundo.getCamera().getMaxY());
		System.out.println(mundo.getCamera().toString());
		
		System.out.println(mundo.getCamera().getMaxY());

		
		gl.glLineWidth(1.0f);
		gl.glPointSize(1.0f);

		/*
		 * gl.glBegin(GL.GL_LINE_LOOP); double testeX = 1.0+20, testeY = 1.0+20;
		 * gl.glVertex2d(testeX, testeY); gl.glVertex2d(testeX-5, testeY);
		 * gl.glVertex2d(testeX, testeY-5); gl.glEnd();
		 */
		desenhaSRU();

		for (ObjetoGrafico objeto : mundo.getListaObjetos()) {

		    if(!objeto.isChild()) {
                objeto.desenha(objeto.equals(mundo.getPoligonoSelecionado()));
            }
		}
		
		if (objetoTempVertices != null && objetoTempVertices.size() > 0) {
			desenhaTemp();
		}


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
	
	public void desenhaTemp() {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
		for (int i = 0; i < objetoTempVertices.size(); i++) {
			gl.glVertex2d(objetoTempVertices.get(i).obterX(), objetoTempVertices.get(i).obterY());
		}
		if (pontoMouse != null) {
			gl.glVertex2d(pontoMouse.obterX(), pontoMouse.obterY());
			System.out.println("valorX: " + pontoMouse.obterX() + "  valorY: " + pontoMouse.obterY());
		}
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_SPACE:
			if (objetoTemporario != null) {

                objetoTemporario.exibeVertices();
				objetoTemporario.atribuirGL(gl);
				mundo.getListaObjetos().add(objetoTemporario);

                objetoTemporario.setBbox();

                if(nextIsChild) {
                    objetoTemporario.setIsChild(true);
                    mundo.getPoligonoSelecionado().addChild(objetoTemporario);
                    nextIsChild = false;
                } else {
                    mundo.setPoligonoSelecionado(objetoTemporario);
                }

				objetoTemporario = null;
				objetoTempVertices.clear();
				pontoMouse = null;
			} else {
				System.out.println("Sem polignos na lista");
			}
			break;
		case KeyEvent.VK_K:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().rotacaoZ(0.5);
			}
			break;
		case KeyEvent.VK_DELETE:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getListaObjetos().remove(mundo.getPoligonoSelecionado());
				mundo.setPoligonoSelecionado(null);
			}
			break;
		case KeyEvent.VK_E:
			if (mundo.getPoligonoSelecionado() != null
					&& mundo.getPoligonoSelecionado().getVerticeSelecionado() != null) {
				mundo.getPoligonoSelecionado().getVertices()
						.remove(mundo.getPoligonoSelecionado().getVerticeSelecionado());
				mundo.getPoligonoSelecionado().setVerticeSelecionado(null);
				mundo.getPoligonoSelecionado().setBbox();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().translacaoXYZ(2.0, 0.0, 0.0);
				mundo.getPoligonoSelecionado().exibeVertices();
			}
			break;
		case KeyEvent.VK_LEFT:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().translacaoXYZ(-2.0, 0.0, 0.0);
				mundo.getPoligonoSelecionado().exibeVertices();
			}
			break;
		case KeyEvent.VK_UP:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().translacaoXYZ(0.0, 2.0, 0.0);
				mundo.getPoligonoSelecionado().exibeVertices();
			}
			break;
		case KeyEvent.VK_DOWN:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().translacaoXYZ(0.0, -2.0, 0.0);
				mundo.getPoligonoSelecionado().exibeVertices();
			}
			break;
		case KeyEvent.VK_R://Reseta matriz
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().atribuirIdentidade();
			}
			break;
        case KeyEvent.VK_P:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().escalaXYZ(1.05, 1.05);
				mundo.getPoligonoSelecionado().exibeVertices();
			}
            break;
        case KeyEvent.VK_O:
			if (mundo.getPoligonoSelecionado() != null) {
				mundo.getPoligonoSelecionado().escalaXYZ(0.95, 0.95);
				mundo.getPoligonoSelecionado().exibeVertices();
			}
            break;
        case KeyEvent.VK_F:
            if (objetoTemporario == null) {
                nextIsChild = true;
            }
            break;
        case KeyEvent.VK_C:

			if (mundo.getPoligonoSelecionado() != null) {
				if (indiceCores == 2) {
					indiceCores = 0;
				} else {
					indiceCores++;
				}

				//mundo.getPoligonoSelecionado().setCor(cores.get(indiceCores));
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
			}
			break;
        case KeyEvent.VK_M:
        	mundo.getCamera().zoomOut(1);
        	break;
        case KeyEvent.VK_N:
        	mundo.getCamera().zoomIn(1);
        	break;
        case KeyEvent.VK_NUMPAD8:
        	mundo.getCamera().pan(Camera2D.CIMA, 1);
        	break;
        case KeyEvent.VK_NUMPAD4:
        	mundo.getCamera().pan(Camera2D.ESQUERDA, 1);
        	break;
        case KeyEvent.VK_NUMPAD6:
        	mundo.getCamera().pan(Camera2D.DIREITA, 1);
        	break;
        case KeyEvent.VK_NUMPAD2:
        	mundo.getCamera().pan(Camera2D.BAIXO, 1);
        	break;
		case KeyEvent.VK_T:
			if(red < 1.0f) {
				red += 0.1;
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
			}
			break;
		case KeyEvent.VK_Y:
			if(green < 1.0f) {
				green += 0.1;
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
			}
			break;
		case KeyEvent.VK_U:
			if(blue < 1.0f) {
				blue += 0.1;
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
			}
			break;
		case KeyEvent.VK_G:
			if(red > 0.0f) {
				red -= 0.1;
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
			}
			break;
		case KeyEvent.VK_H:
			if(green > 0.0f) {
				green -= 0.1;
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
			}
			break;
		case KeyEvent.VK_J:
			if(blue > 0.0f) {
				blue -= 0.1;
				mundo.getPoligonoSelecionado().setRGB(red, green, blue);
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
		if (mundo.getPoligonoSelecionado() != null && mundo.getPoligonoSelecionado().getVerticeSelecionado() != null) {
			mouseUnitToGlUnit(e.getX(), e.getY());
			mundo.getPoligonoSelecionado().getVerticeSelecionado().atribuirX((valorX-mundo.getPoligonoSelecionado().getMatrizObjeto().getMatriz()[12])/mundo.getPoligonoSelecionado().getMatrizObjeto().getMatriz()[0]);
			mundo.getPoligonoSelecionado().getVerticeSelecionado().atribuirY((valorY-mundo.getPoligonoSelecionado().getMatrizObjeto().getMatriz()[13])/mundo.getPoligonoSelecionado().getMatrizObjeto().getMatriz()[5]);
		} else {
			System.out.println("mouse arrastando sem ação");
		}

		glDrawable.display();

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (objetoTempVertices != null && objetoTempVertices.size() > 0){
			mouseUnitToGlUnit(e.getX(), e.getY());
			pontoMouse = new Ponto4D(valorX, valorY, 0, 0);
			System.out.println("valorX: " + valorX + "  valorY: " + valorY);
			glDrawable.display();
		}
		// mouseMoviment(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:// caso botão direito do mouse...
			System.out.println("Botão direito apertado");
			mouseUnitToGlUnit(e.getX(), e.getY());
			if (objetoTemporario != null) {
				objetoTemporario.getVertices().add(new Ponto4D(valorX, valorY, 0, 0));

				objetoTempVertices.add(new Ponto4D(valorX, valorY, 0, 0));
			} else {
				objetoTemporario = new ObjetoGrafico();
				objetoTemporario.getVertices().add(new Ponto4D(valorX, valorY, 0, 0));

				objetoTempVertices = new ArrayList<Ponto4D>();
				objetoTempVertices.add(new Ponto4D(valorX, valorY, 0, 0));
			}
			glDrawable.display();
			break;
		case MouseEvent.BUTTON1: // caso botão esquerdo do mouse...
			/*mouseUnitToGlUnit(e.getX(), e.getY());
			for (ObjetoGrafico objeto : mundo.getListaObjetos()) {
                if(objeto.isClickInside(new Ponto4D(e.getX(), e.getY(), 0,0))) {
                    mundo.setPoligonoSelecionado(objeto);
                    break;
                }
			}*/
		default:
			break;
		}
		glDrawable.display();

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
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:// caso botão esquerdo do mouse...
			System.out.println("Botão esquerdo apertado");
			mundo.lookIfInside(valorX, valorY);
			if(mundo.getPoligonoSelecionado() != null){
				mundo.getPoligonoSelecionado().setVerticeSelecionado(achaVertice(e.getX(), e.getY()));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:// caso botão esquerdo do mouse...
			if(mundo.getPoligonoSelecionado() != null){
				mundo.getPoligonoSelecionado().setBbox();
			}
			break;
		default:
			break;
		}

	}

	/**
	 * Converte a posição da unidade do mouse (Tamanho da tela em pixel) para
	 * unidades do OpenGl (definida pelo Ortho2D) O valor convertido é colocado
	 * na variável valorX e valorY;
	 * 
	 * @param x
	 *            Posição X do mouse
	 * @param y
	 *            Posição Y do mouse
	 */
	public void mouseUnitToGlUnit(int x, int y) {
		valorX = ((30.0 - x / 400.0 * 60.0) * -1.0) + 1;
		valorY = (30.0 - y / 400.0 * 60.0) - 1;
	}

	/**
	 * Procura o vertice mais perto da area selecionada do poligno selecionado
	 *
	 * @param x posição X do mouse
	 * @param y posição Y do mouse
	 * @return O vértice mais próximo do poligno selecionado
	 */
	private Ponto4D achaVertice(int x, int y) {
		mouseUnitToGlUnit(x, y);
		double menorDistancia = Double.MAX_VALUE;
		Ponto4D verticeMaisPerto = null;
		for (Ponto4D vertice : mundo.getPoligonoSelecionado().getVertices()) {
			double distanciaAtual = vertice.distanciaDeOutroPonto2D(valorX, valorY, mundo.getPoligonoSelecionado().getMatrizObjeto());
			if (distanciaAtual < menorDistancia) {
				menorDistancia = distanciaAtual;
				verticeMaisPerto = vertice;
			}
		}
		return verticeMaisPerto;

	}

}
