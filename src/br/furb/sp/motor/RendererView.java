package br.furb.sp.motor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import br.furb.sp.string.DrawSTR;
import br.furb.sp.util.CalcFPS;
import br.furb.sp.util.Preferencias;

public abstract class RendererView implements GLSurfaceView.Renderer {

	// Cria variavel para guardar o sistema de particula
	protected MotorParticulas mSistemaParticula;

	// Desenho de Texto
	protected DrawSTR drawSTR;

	// FPS
	protected CalcFPS fps;
	
	/**
	 * Construtor da Surface View
	 */
	public RendererView(Preferencias preferencias, final int widht,
			final int height) {

		// Inicializa Desenho do texto
		this.drawSTR = new DrawSTR(widht, height);

		// Iniciliaza FPS
		this.fps = new CalcFPS();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
	 * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// Define a perspective e camera
		GLU.gluPerspective(gl, 15f, 50.0f / 50.0f, 1, 50);
		GLU.gluLookAt(gl, 0f, 0f, -20f, 0.0f, 0.0f, 1f, 0.0f, 1.0f, 0.0f);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// inicializa classe drawFPS
		this.drawSTR.initFPS(gl, new Paint());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
	 * .khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
	}

	/**
	 * Define a Sequencia das rotinas, Calcula e Desenha
	 * 
	 * @param gl
	 */
	public void onDrawFrame(GL10 gl) {
		
		// Cor do Fundo
		gl.glClearColor(0, 0, 0f, 1.0f); // Preto
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Atualiza Movimentos das Partículas
		mSistemaParticula.update();
		
		// Pinta a tela Com as Mudanças
//		mSistemaParticula.draw(gl);
		drawMotorParticulas(gl);
		
		// Calcula e desenha FPS
		if (mSistemaParticula.isAtivo())
			this.fps.calculaFPS();
		
		// Desenha o FPS Passando os valores como String
		this.drawSTR.drawFPS(gl, this.fps.toString());

	}
	
	
	public void drawMotorParticulas(GL10 gl){
		// Todo desenho das partículas fica nesse trecho de código
	}
	
	public void pausarSimulacao() {
		mSistemaParticula.pausarSimulacao();
	}
	
	public void continuarSimulacao() {
		mSistemaParticula.continuarSimulacao();
	}

}
