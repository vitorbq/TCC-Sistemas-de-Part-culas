package br.furb.sp.util;

import android.content.SharedPreferences;

public class Preferencias {
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	private boolean atualizou = false;
	
	private String MODO_PREF;

	public Preferencias(String MODO, SharedPreferences preferencias) {
		this.MODO_PREF = MODO;
		this.preferences = preferencias;
	}

	public void putString(String item, String pref){
		this.editor = preferences.edit();
		editor.putString(MODO_PREF + item, pref);
//		editor.putString(item, pref);
		commit();
	}

	public void putInteger(String item, int pref){
		this.editor = preferences.edit();
		editor.putInt(MODO_PREF + item, pref);
//		editor.putInt(item, pref);
		commit();
	}
	
	public void putFloat(String item, float pref){
		this.editor = preferences.edit();
		editor.putFloat(MODO_PREF + item, pref);
//		editor.putFloat(item, pref);
		commit();
	}
	
	public String getString(String item, String defValue){
		return preferences.getString(MODO_PREF + item, defValue);
	}
	
	public int getInt(String item, int defValue){
		return preferences.getInt(MODO_PREF + item, defValue);
	}
	
	public float getFloat(String item, float defValue){
		return preferences.getFloat(MODO_PREF + item, defValue);
	}
	
	public boolean isAtualizou() {
		return atualizou;
	}
	
	public void setAtualizou(boolean atualizou) {
		this.atualizou = atualizou;
	}
	
	/**
	 * Salva preferencias
	 */
	public void commit(){
		editor.commit();
	}

}
