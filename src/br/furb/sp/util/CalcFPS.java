package br.furb.sp.util;

public class CalcFPS {

	// Variaveis de calculo do FPS
	private long ultimoTempoDesenho;
	private int desenhoFrameColetado;
	private int desenhoFrameTempo;
	private int fps;
	
	/**
	 * Inicializa FPS
	 */
	public CalcFPS() {
		this.ultimoTempoDesenho = 0;
		this.desenhoFrameColetado = 0;
		this.desenhoFrameTempo = 0;
		this.fps = 0;
	}
	
	/**
	 * Calclula/Atualiza FPS
	 */
	public void calculaFPS() {
		long now = System.currentTimeMillis();
		if (this.ultimoTempoDesenho != 0) {
			int time = (int) (now - this.ultimoTempoDesenho);
			this.desenhoFrameTempo += time;
			this.desenhoFrameColetado++;
			if (this.desenhoFrameColetado == 10) {
				this.fps = (int) (10000 / this.desenhoFrameTempo);
				this.desenhoFrameTempo = 0;
				this.desenhoFrameColetado = 0;
			}
			
		}
		this.ultimoTempoDesenho = now;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.fps);
	}
	
	
	
}
