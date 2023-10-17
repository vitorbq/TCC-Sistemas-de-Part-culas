package br.furb.sp.main;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuInicial extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Cria as String para compor o Menu
		String[] menuI = new String[] { 
				"Simulação Fogos de Artifício",
				"Simulação Gota de Água", 
				"TCC - Angel Vitor Lopes" };
		// Adiciona o Menu
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuI));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (position) {
		case 0: // Simulação Fogos de Artifício
			intent = new Intent(this,
					br.furb.sp.motor.fogosartificio.FATelaActivity.class);
			startActivity(intent);
			break;

		case 1: // Simulação Queda de Agua
			intent = new Intent(this,
					br.furb.sp.motor.gotaagua.GATelaActivity.class);
			startActivity(intent);
			break;

		case 2: // Tela de Informacoes
			intent = new Intent(this, br.furb.sp.main.Informacoes.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
}
