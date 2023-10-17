package br.furb.sp.motor.fogosartificio;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import br.furb.sp.motor.RendererView;
import br.furb.sp.util.Preferencias;

class FARendererView extends RendererView {

	public FARendererView(Preferencias preferencias, final int widht,
			final int height) {
		super(preferencias, widht, height);

		// Variável que diz a quantidade de particulas a iniciar
		mSistemaParticula = new FAMotorParticulas(preferencias);

	}

	@Override
	public void drawMotorParticulas(GL10 gl) {
		// TODO Auto-generated method stub

		((FAMotorParticulas) mSistemaParticula).draw(gl);
		
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mSistemaParticula.pVertexBuffer);
//
//		if (mSistemaParticula.isAtivo()) {
//			for (int i = 0; i < mSistemaParticula.QTDparticulas; i++) {
//
//				Log.v("TCC", " aqui2 " + "_"
//						+ mSistemaParticula.particulasARRAY[i] + "_" + i);
//				// somente desenha quando estiver ativa
//				if (mSistemaParticula.particulasARRAY[i].ativa) {
//					// Desenha a Particula, caso ela nao tenha explodido
//					if (!mSistemaParticula.particulasARRAY[i].efeitoAtivo) {
//						gl.glPushMatrix();
//						gl.glColor4f(
//								mSistemaParticula.particulasARRAY[i].vermelho,
//								mSistemaParticula.particulasARRAY[i].verde,
//								mSistemaParticula.particulasARRAY[i].azul, 1f);
//						gl.glTranslatef(mSistemaParticula.particulasARRAY[i].x,
//								mSistemaParticula.particulasARRAY[i].y,
//								mSistemaParticula.particulasARRAY[i].z);
//						gl.glDrawElements(GL10.GL_TRIANGLES, 3,
//								GL10.GL_UNSIGNED_SHORT,
//								mSistemaParticula.pIndiceBuffer);
//						gl.glPopMatrix();
//						// desenha a particula explodindo
//					} else {
//
//						float alphaN = 1.0f;
//						for (int j = 0; j < mSistemaParticula.particulasARRAY[i].subParticulas.length; j++) {
//							gl.glPushMatrix();
//							gl.glColor4f(
//									mSistemaParticula.particulasARRAY[i].subParticulas[j].vermelho,
//									mSistemaParticula.particulasARRAY[i].subParticulas[j].verde,
//									mSistemaParticula.particulasARRAY[i].subParticulas[j].azul,
//									alphaN);
//							gl.glTranslatef(
//									mSistemaParticula.particulasARRAY[i].subParticulas[j].x,
//									mSistemaParticula.particulasARRAY[i].subParticulas[j].y,
//									mSistemaParticula.particulasARRAY[i].subParticulas[j].z);
//							gl.glDrawElements(GL10.GL_TRIANGLES, 3,
//									GL10.GL_UNSIGNED_SHORT,
//									mSistemaParticula.pIndiceBuffer);
//							gl.glPopMatrix();
//							alphaN = alphaN - 0.1f;
//						}
//					}
//				}
//			}
//		}
		

	}

}
