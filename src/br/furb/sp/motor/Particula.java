package br.furb.sp.motor;

public abstract class Particula {

	public float 	x, y, z; 	// posicao
	public float 	vx, vy, vz; // velocidade
	public float 	tempoVida; 	// tempo de vida
	public float 	vermelho, 	// Cores RGB
					verde, 
					azul; 
	public boolean 	ativa = false,
					efeitoAtivo = false;
	public float 	tamanhoPart;
	
	public Particula[]
	                 subParticulas;	// Particulas

	/**
	 * Construtor da Particula
	 */
	public Particula() {
		this.ativa 			= true;
		this.efeitoAtivo	= false;
	}

	/**
	 * Construtor da Particula com parametros de posicao
	 * 
	 * @param novaPosX
	 *            Posicao do eixo X
	 * @param novaPosY
	 *            Posicao do eixo Y
	 * @param novaPosZ
	 *            Posicao do eixo Z
	 */
	public Particula(float novaPosX, float novaPosY, float novaPosZ) {
		this.ativa 		= false;
		this.efeitoAtivo= false;
		this.x 			= novaPosX;
		this.y 			= novaPosY;
		this.z 			= novaPosZ;
	}

	/**
	 * Construtor da Particula com parametros de posicao
	 * 
	 * @param novaPosX
	 *            Posicao do eixo X
	 * @param novaPosY
	 *            Posicao do eixo Y
	 * @param novaPosZ
	 *            Posicao do eixo Z
	 * @param velX
	 *            velocidade X
	 * @param velY
	 *            velocidade Y
	 * @param velZ
	 *            velocidade Z
	 * @param vermelho
	 *            Cor Vermelha
	 * @param verde
	 *            Cor Verde
	 * @param azul
	 *            Cor Azul
	 */
	public Particula(float novaPosX, float novaPosY, float novaPosZ,
			float velX, float velY, float velZ, float vermelho, float verde,
			float azul) {
		this.ativa 		= false;
		this.efeitoAtivo= false;
		this.x = novaPosX;
		this.y = novaPosY;
		this.z = novaPosZ;
		this.vx = velX;
		this.vy = velY;
		this.vz = velZ;
		this.vermelho = vermelho;
		this.verde = verde;
		this.azul = azul;
	}
	
	/**
	 * Verifica se existe pelo menos uma sub particula ativa
	 * @return
	 */
	public boolean subParAtiva(){
		boolean retorno = false;
		for (int i = 0; i < subParticulas.length; i++) {
			if (subParticulas[i].ativa)
				return true;
		}
		return retorno;
	}

}
