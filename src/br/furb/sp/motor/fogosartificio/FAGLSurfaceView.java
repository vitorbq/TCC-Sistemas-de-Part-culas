package br.furb.sp.motor.fogosartificio;

import br.furb.sp.motor.SurfaceView;
import br.furb.sp.util.Preferencias;
import android.content.Context;


class FAGLSurfaceView extends SurfaceView {

	public FAGLSurfaceView(Context context, Preferencias pref, final int width,
			final int height) {
		super(context, pref, width, height);
		mRenderer = new FARendererView(pref, width, height);
		setRenderer(mRenderer);
	}

}