package br.furb.sp.main;

import br.furb.sp.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Informacoes extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Setar Orientacao da Tela Portrait, Modo Full Screen e nao permite desligar a tela
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.informacoes);
		
		init();
	}
	
	public void init(){
		TextView email = (TextView) findViewById(R.id.textVEmail);
		
		email.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "vitorbq@gmail.com");
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contato via App TCC");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Enviando via android...");
				emailIntent.setType("text/plain");
				startActivity(Intent.createChooser(emailIntent, "Enviar E-mail"));
				finish();		
			}
		});
		
	}
	
	

}
