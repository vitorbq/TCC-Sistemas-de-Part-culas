package br.furb.sp.motor.gotaagua;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import br.furb.sp.util.Constants;
import br.furb.sp.util.Preferencias;

public class GATelaActivity extends Activity {

	// Cria a View onde vamos desenhar
	private GLSurfaceView pGLView;
	private int telaWidth, telaHeight;
	private Preferencias preferencias;

	/**
	 * Metodo principal quando a Actibity inicia
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Setar Orientacao da Tela Portrait, Modo Full Screen e nao permite
		// desligar a tela
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Busca a dimensao da tela
		Display display = ((android.view.WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		this.telaWidth = display.getWidth();
		this.telaHeight = display.getHeight();

		// Faz carga das preferencias
		CarregaPref();

		// Criar nova instancia da View e seta como View prim�ria
		pGLView = new GAGLSurfaceView(this, this.preferencias, this.telaWidth,
				this.telaHeight);

		setContentView(pGLView);
	}

	/**
	 * Reiniciar
	 */
	@Override
	protected void onRestart() {
		onPause();
		pGLView = null;
		pGLView = new GAGLSurfaceView(this, this.preferencias, this.telaWidth,
				this.telaHeight);
		setContentView(pGLView);
		onResume();
	}

	/**
	 * Pausar
	 */
	@Override
	protected void onPause() {
		super.onPause();
		pGLView.onPause();
		playpause = false;

	}

	/**
	 * Continuar
	 */
	@Override
	protected void onResume() {
		super.onResume();
		pGLView.onResume();
		playpause = true;
	}

	public boolean playpause = true;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Constants.PAUSE, 0, "Pause/Play");
		menu.add(0, Constants.REINICIAR, 1, "Reiniciar");
		menu.add(0, Constants.CONFIGURAR, 2, "Configurar");
		return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Constants.PAUSE: // Play / Pause
			if (playpause)
				onPause();
			else
				onResume();
			return true;
		case Constants.REINICIAR: // Reiniciar
			onRestart();
			return true;
		case Constants.CONFIGURAR: // Configura��es
			Intent intent = new Intent(this, GAConfiguracoes.class);
			startActivity(intent);

			return true;
		}
		return false;
	}

	public void CarregaPref() {

		// Preferencias para Gotas de Agua
		SharedPreferences pref = getSharedPreferences(Constants.GA_PREF,
				Activity.MODE_PRIVATE);
		this.preferencias = new Preferencias(Constants.GA_PREF, pref);

	}

}
