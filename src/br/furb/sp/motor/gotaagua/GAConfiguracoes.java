package br.furb.sp.motor.gotaagua;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import br.furb.sp.R;
import br.furb.sp.util.Constants;
import br.furb.sp.util.Preferencias;

public class GAConfiguracoes extends Activity {

	SeekBar seekBGravidade, seekBResistAr, seekBVelocTempo;

	TextView textVGravidade, textVResistAr, textVVelocTempo;

	EditText editTQTDParticulas, editTQTDSubParticulas;

	Preferencias preferencias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.configgotaagua);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Componentes
		initComponentes();

		// Preferencias
		carregarPref();

	}
	
	/**
	 * Carrega Preferencias
	 */
	public void carregarPref() {

		SharedPreferences pref = getSharedPreferences(Constants.GA_PREF,
				MODE_PRIVATE);
		this.preferencias = new Preferencias(Constants.GA_PREF, pref);

		this.editTQTDParticulas.setText(this.preferencias.getInt(
				"qtdparticulas", 15) + "");
		this.editTQTDSubParticulas.setText(this.preferencias.getInt(
				"qtdsubparticulas", 32) + "");
		this.seekBVelocTempo.setProgress(this.preferencias.getInt("velocTempo",
				8000));

		float grav = this.preferencias.getFloat("gravidade", 9.8f);

		// Formula para transformar Indice em Float na seekBar
		String floatToTXT = ((grav * 1500) / 15) + 500 + "";
		floatToTXT = floatToTXT.substring(0, floatToTXT.indexOf(".")).trim(); 
		int txtToInt = Integer.parseInt(floatToTXT);
		this.seekBGravidade.setProgress(txtToInt);

		// Formula para transformar Indice em outra escala Float na seekBar
		floatToTXT = this.preferencias.getFloat("resistenciaar", 1.0f) + "";
		floatToTXT = floatToTXT.substring(0, floatToTXT.indexOf(".")).trim(); 
		txtToInt = Integer.parseInt(floatToTXT);
		txtToInt = txtToInt * 10;
		this.seekBResistAr.setProgress(txtToInt);

		Log.i(Constants.LOG, "Carregou Preferencias com sucesso");
	}

	/**
	 * Grava Preferencias
	 */
	public void gravarPref() {

		int integer = Integer.parseInt(0 + (this.editTQTDParticulas.getText() + "") );
		if ( integer < 1 ) integer = 1;
		this.preferencias.putInteger("qtdparticulas", integer);

		integer = Integer.parseInt(0 + (this.editTQTDSubParticulas.getText() + "") );
		if ( integer < 1) integer = 1;
		this.preferencias.putInteger("qtdsubparticulas", integer);

		integer = Integer.parseInt(this.seekBVelocTempo.getProgress() + "");
		this.preferencias.putInteger("velocTempo", integer);

		this.preferencias.putFloat("gravidade",
				((this.seekBGravidade.getProgress() - 500) / 100.f));

		this.preferencias.putFloat("resistenciaar",
				(float) ((this.seekBResistAr.getProgress()) / 10.f));

		Log.i(Constants.LOG, "Gravou Preferencias com sucesso");
	}

	public void initComponentes() {
		// Inicializa componentes
		this.seekBGravidade = (SeekBar) findViewById(R.id.GAseekBGravidade);
		this.textVGravidade = (TextView) findViewById(R.id.GAtextVGravidade);
		this.seekBResistAr = (SeekBar) findViewById(R.id.GAseekBResistAr);
		this.textVResistAr = (TextView) findViewById(R.id.GAtextVResistAr);
		this.seekBVelocTempo = (SeekBar) findViewById(R.id.GAseekBVelocTempo);
		this.textVVelocTempo = (TextView) findViewById(R.id.GAtextVVelocTempo);
		this.editTQTDParticulas = (EditText) findViewById(R.id.GAeditTQTDParticulas);
		this.editTQTDSubParticulas = (EditText) findViewById(R.id.GAeditTQTDSubParticulas);

		// Valor da Barra de Gravidade
		this.seekBGravidade
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						textVGravidade.setText(
						// String.valueOf(progress)
						// + " - " +
						Float.toString((float) ((progress - 500) / 100.f)));
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});
		// Fim Valor Barra Gravidade

		// Valor da Barra da Resistencia do Ar
		this.seekBResistAr
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						textVResistAr.setText(
						// String.valueOf(progress)
						// + " - " +
								Float.toString((float) ((progress) / 10.f)));
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});
		// Fim Valor Barra Resistencia do Ar

		// Valor da Barra de Fator do Tempo
		this.seekBVelocTempo
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						textVVelocTempo.setText(String.valueOf(progress));
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});
		// Fim Valor Barra Fator de Tempo

	}

	@Override
	protected void onStop() {
		gravarPref();
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Reset Configurações");
		return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0: // Restart Configuracoes
			resetConfig();
			return true;
		}
		return false;
	}

	public void resetConfig() {

		this.preferencias.putInteger("qtdparticulas", 6);
		this.preferencias.putInteger("qtdsubparticulas", 32);
		this.preferencias.putInteger("velocTempo", 8000);
		this.preferencias.putFloat("gravidade", 9.8f);
		this.preferencias.putFloat("resistenciaar", 1.0f);

		Log.i(Constants.LOG, "Reset Preferencias com sucesso");

		carregarPref();
	}

}
