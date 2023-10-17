package br.furb.sp.motor.gotaagua;

import br.furb.sp.motor.SurfaceView;
import br.furb.sp.util.Preferencias;
import android.content.Context;

class GAGLSurfaceView extends SurfaceView {
		
	public GAGLSurfaceView(Context context,Preferencias pref, final int width,
			final int height) {
		super(context, pref, width, height);

		mRenderer = new GARendererView(pref, width, height);
		setRenderer(mRenderer);
	}
	
}