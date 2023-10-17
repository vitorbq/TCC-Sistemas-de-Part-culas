package br.furb.sp.motor;

import br.furb.sp.util.Constants;
import br.furb.sp.util.Preferencias;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public abstract class SurfaceView extends GLSurfaceView {

	// Armazena Renderer
	protected RendererView mRenderer;
	int cont1 = 0, cont2 = 0;
	float x, y;
	protected Preferencias preferencias;

	public SurfaceView(Context context, Preferencias pref, final int width,
			final int height) {
		super(context);
		this.preferencias = pref;
//		mRenderer = new RendererView(pref, width, height);
//		setRenderer(mRenderer);
	}
	

	/**
	 * Pausar
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.v(Constants.LOG, "Pause");
		mRenderer.pausarSimulacao();
		// super.onPause();
	}

	/**
	 * Continuar
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		Log.v(Constants.LOG, "Play");
		mRenderer.continuarSimulacao();
		// super.onResume();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();

		// TODO Auto-generated method stub
		switch (action) {
		// Pressiona a tela
		case MotionEvent.ACTION_DOWN: {
			Log.v(Constants.LOG, "DOWN");
			x = event.getX();
			y = event.getY();
			cont1 = 0;
			return true;
		}
			// Solta a tela
		case MotionEvent.ACTION_UP: {
			Log.v(Constants.LOG, "UP");
			cont2 = 0;
			return true;
		}
			// Pressionado, movimenta na tela
		case MotionEvent.ACTION_MOVE: {
			float variacaoX, variacaoY;
			cont1++;
			if (cont1 > 10) {
//				Log.v("TCC", "MOVE " + event.getX() + " - " + event.getY());
				variacaoX = x - event.getX();
				variacaoY = y - event.getY();
//				Log.v("TCC", "MOVEv " + variacaoX + " - " + variacaoY);
				// Converte as 2 variacoes para positiva, e compara qual 
				// teve a maior variacao no intervalo de 10 eventos de
				// touch

				Log.v(Constants.LOG, "1 variacaoX " + variacaoX + " ... " + (variacaoX < 0 ? variacaoX * -1 : variacaoX) );
				Log.v(Constants.LOG, "1 variacaoY " + variacaoY + " ... " + (variacaoY < 0 ? variacaoY * -1	: variacaoY) );
				if ((variacaoX < 0 ? variacaoX * -1 : variacaoX) > 
					(variacaoY < 0 ? variacaoY * -1	: variacaoY)) {
					// Se o X for maior então foi um Movimento Horizontal
					// negativo é para direita
					// positivo para esquerda

					Log.v(Constants.LOG, "2 variacaoX " + variacaoX );
					
					int velocTempo = this.preferencias.getInt("velocTempo", 8000);
					if (variacaoX > 0){
						// Aumenta o valor
						velocTempo = velocTempo + 750;
						// Limite Alto 8000
						if(velocTempo < 15000){
							this.preferencias.putInteger("velocTempo", velocTempo);
							this.preferencias.setAtualizou(true);
							Log.v(Constants.LOG, " velocTempo + nova " + velocTempo );
						}
					} else {
						// senão Diminui o valor
						velocTempo = velocTempo - 750;
						// o Limite Baixo 0 (Zero)
						if(velocTempo > 0){
							this.preferencias.putInteger("velocTempo", velocTempo);
							this.preferencias.setAtualizou(true);
							Log.v(Constants.LOG, " velocTempo - nova " + velocTempo );
						}
					}
//					Log.v("TCC", "MOVE X " + variacaoX + " - " + variacaoY + " neg " + (-variacaoX));
				} else {
					
					Log.v(Constants.LOG, "2 variacaoY " + variacaoY );
					// Caso o Y seja maior, então o movimento é Vertical
					// negativo é para cima
					// positivo é para baixo
					float gravidade = this.preferencias.getFloat("gravidade", 9.8f);
					if (variacaoY > 0f){
						// Aumenta o valor
						gravidade = gravidade - 0.5f;
						// Limite Alto 15
						if(gravidade > -5f){
							this.preferencias.putFloat("gravidade", gravidade);
							this.preferencias.setAtualizou(true);
							Log.v(Constants.LOG, " gravidade + nova " + gravidade );
						}
					} else {
						// senão Diminui o valor
						gravidade = gravidade + 0.5f;
						// o Limite Alto 15f
						if(gravidade < 15f){
							this.preferencias.putFloat("gravidade", gravidade);
							this.preferencias.setAtualizou(true);
							Log.v(Constants.LOG, " gravidade - nova " + gravidade );
						}
					}

//					Log.v("TCC", "MOVE Y " + variacaoX + " - " + variacaoY + " neg " + (-variacaoY));
				}
				cont1 = 0;

			}

			return true;
		}
		}
		return false;
	}

}