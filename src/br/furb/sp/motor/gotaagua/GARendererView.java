package br.furb.sp.motor.gotaagua;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;
import android.opengl.GLU;
import br.furb.sp.motor.RendererView;
import br.furb.sp.motor.fogosartificio.FAMotorParticulas;
import br.furb.sp.util.Preferencias;

class GARendererView extends RendererView {

	public GARendererView(Preferencias preferencias, final int widht,
			final int height) {
		
		super(preferencias, widht, height);

		// Variável que diz a quantidade de particulas a iniciar
		mSistemaParticula = new GAMotorParticulas(preferencias);

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		// Define a perspective e camera
		GLU.gluPerspective(gl, 15f, 50.0f / 50.0f, 1, 50);
		GLU.gluLookAt(gl, 0f, 0f, -30f, 0.0f, 0.0f, 1f, 0.0f, 1.0f, 0.0f);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// inicializa classe drawFPS
		this.drawSTR.initFPS(gl, new Paint());
	}
	
	public void drawMotorParticulas(GL10 gl) {
		// TODO Auto-generated method stub

		((GAMotorParticulas) mSistemaParticula).draw(gl);
	}
}
