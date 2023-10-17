package br.furb.sp.motor.gotaagua;

import android.util.Log;
import br.furb.sp.motor.MotorParticulas;
import br.furb.sp.util.Constants;
import br.furb.sp.util.Preferencias;
import javax.microedition.khronos.opengles.GL10;

public class GAMotorParticulas extends MotorParticulas {

	/**
	 * Construtor do Motor de Particulas
	 * 
	 * @param qtdParticulas
	 *            Quantidade de Partículas
	 */
	public GAMotorParticulas(Preferencias preferencias) {

		super(preferencias);
		// Cria Array
		this.particulasARRAY = new GAParticula[this.QTDparticulas];
		
		// Outros Parametros das partículas
		this.resistenciaAR 	=  1.0f;
		this.limiteChao		= -2.5f;
		this.tamanhoPart	=  0.05f; 
		
		iniciaParticulas();

	}

	/**
	 * Desenha as particulas
	 * 
	 * @param gl
	 *            Elemento Visao
	 */
	public void draw(GL10 gl) {

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pVertexBuffer);

		// for (int i = 0; i < QTDparticulas; i++) {
		// Percorre Todas as Partículas
		for (int ind = 0; ind < this.particulasARRAY.length; ind++) {

			// somente desenha quando estiver ativa
			if (this.particulasARRAY[ind].ativa) {

				// Percorre Todas as partículas que compõe a partícula da gota
				for (int i = 0; i < this.particulasARRAY[ind].subParticulas.length; i++) {

					// somente desenha quando estiver ativa
					if (this.particulasARRAY[ind].subParticulas[i].ativa) {

						gl.glPushMatrix();
						gl.glColor4f(
								this.particulasARRAY[ind].subParticulas[i].vermelho,
								this.particulasARRAY[ind].subParticulas[i].verde,
								this.particulasARRAY[ind].subParticulas[i].azul,
								1f);
						gl.glTranslatef(
								this.particulasARRAY[ind].subParticulas[i].x,
								this.particulasARRAY[ind].subParticulas[i].y,
								this.particulasARRAY[ind].subParticulas[i].z);
						// gl.glDrawElements(GL10.GL_TRIANGLES,
						// 6, GL10.GL_UNSIGNED_SHORT,
						// pIndiceBuffer);

						// gl.glPointSize(1);
						gl.glDrawElements(GL10.GL_TRIANGLES, 3,
								GL10.GL_UNSIGNED_SHORT, pIndiceBuffer);
						gl.glPopMatrix();

					}
				}
			}
		}

	}

	// Processamento das particulas no espaço
	public void update() {

		// On-Line a cada 5 updates, mas somente se foi atualizado
		this.contFrame++;
		if (this.contFrame > 5) {
			this.contFrame = 0;
			if (preferencias.isAtualizou())
				carregarPref2();
		}

		long tempoAtual = System.currentTimeMillis();

		if (this.estadoSistema) {

			// Calcula tempo dos frames para definir velocidade
			// Frame Rate é a velocidade
			float frameRate = (float) (tempoAtual - this.ultimoTempo)
					/ this.velocTempo;
			this.ultimoTempo = tempoAtual;

			// Percorre Todas as Partículas
			for (int partInd = 0; partInd < this.particulasARRAY.length; partInd++) {

				// Percorre Todas as partículas que compõe a partícula da gota
				for (int subInd = 0; subInd < this.particulasARRAY[partInd].subParticulas.length; subInd++) {

					// somente atualiza quando elas tiverem ativas
					if (this.particulasARRAY[partInd].subParticulas[subInd].ativa) {

						// Aplica Resistencia do AR
						this.particulasARRAY[partInd].subParticulas[subInd].vx = this.particulasARRAY[partInd].subParticulas[subInd].vx
								- (particulasARRAY[partInd].subParticulas[subInd].vx
										* resistenciaAR * frameRate);
						this.particulasARRAY[partInd].subParticulas[subInd].vy = this.particulasARRAY[partInd].subParticulas[subInd].vy
								- (particulasARRAY[partInd].subParticulas[subInd].vy
										* resistenciaAR * frameRate);
						this.particulasARRAY[partInd].subParticulas[subInd].vz = this.particulasARRAY[partInd].subParticulas[subInd].vz
								- (particulasARRAY[partInd].subParticulas[subInd].vz
										* resistenciaAR * frameRate);

						// Move a Particula de acordo com a velocidade
						this.particulasARRAY[partInd].subParticulas[subInd].x = this.particulasARRAY[partInd].subParticulas[subInd].x
								+ (particulasARRAY[partInd].subParticulas[subInd].vx * frameRate);
						this.particulasARRAY[partInd].subParticulas[subInd].y = this.particulasARRAY[partInd].subParticulas[subInd].y
								+ (particulasARRAY[partInd].subParticulas[subInd].vy * frameRate);
						this.particulasARRAY[partInd].subParticulas[subInd].z = this.particulasARRAY[partInd].subParticulas[subInd].z
								+ (particulasARRAY[partInd].subParticulas[subInd].vz * frameRate);

						// Aplica Formula para Gravidade
						this.particulasARRAY[partInd].subParticulas[subInd].y = this.particulasARRAY[partInd].subParticulas[subInd].y
								- ((gravidade - 7f) * frameRate);

						// Colisao com o Chao
						if (this.particulasARRAY[partInd].subParticulas[subInd].y <= this.limiteChao) {
							// Inverte as Velocidades
							this.particulasARRAY[partInd].subParticulas[subInd].y = this.limiteChao;
							this.particulasARRAY[partInd].subParticulas[subInd].vy = this.particulasARRAY[partInd].subParticulas[subInd].vy
									+ 1f + (gerador.nextFloat() * 1.1f) * -.5f;
							this.particulasARRAY[partInd].subParticulas[subInd].vx = this.particulasARRAY[partInd].subParticulas[subInd].vx
									+ (gerador.nextFloat() * 6f) - 3f;
							this.particulasARRAY[partInd].efeitoAtivo= true;
						}

						// depois de colidir com o chão, diminui o tempo de vida
						if (this.particulasARRAY[partInd].efeitoAtivo) {
							// Diminui Tempo de Vida
							this.particulasARRAY[partInd].subParticulas[subInd].tempoVida = this.particulasARRAY[partInd].subParticulas[subInd].tempoVida
									- frameRate;
						}

						// // Se a partícula atingiu seu tempo limite desativa
						if (this.particulasARRAY[partInd].subParticulas[subInd].tempoVida < 0f) {

							this.particulasARRAY[partInd].subParticulas[subInd].ativa = false;

							// Verifica se todas as Partículas morrreram, e cria
							// uma nova
							if (this.particulasARRAY[partInd].subParAtiva()) {
								this.particulasARRAY[partInd].ativa = false;
								criaParticula(partInd);

								Log.v("TCC", "new particle= " + partInd);
							}

						}
					}
				} // subPart = Ativa
			}

		} else {

			// Calcula tempo dos frames para definir velocidade
			this.ultimoTempo = tempoAtual;

		}

	}

	/**
	 * Percorre numero de particulas e cria cada uma
	 */
	public void criaParticulas() {
		// Percorre todas as partículas e cria instancias de cada uma
		for (int i = 0; i < this.QTDparticulas; i++) {

			criaParticula(i);
		}
	}

	/**
	 * Particula e SubParticulas dentro da Gota
	 */
	public void criaParticula(int partInd) {

		// Cria Partícula Gota
		particulasARRAY[partInd] = new GAParticula();
		particulasARRAY[partInd].ativa = true;
		particulasARRAY[partInd].efeitoAtivo = false;

		// // Posicoes iniciais da gota
		float x, y, z;

		// Posição X é Aleatória
		if (gerador.nextBoolean())
			x = (this.gerador.nextFloat() * (-3f + this.gerador.nextFloat()));
		else if (gerador.nextBoolean())
			x = (this.gerador.nextFloat() * (3f  + this.gerador.nextFloat()));
		else if (gerador.nextBoolean())
			x = (this.gerador.nextFloat());
		else
			x = (-this.gerador.nextFloat());

		// Posição Y é Aleatória mais uma posição 1.9 fixado para altura
		y = this.gerador.nextFloat() + 1.9f;
		z = 0f;

		String raizSTR = Math.sqrt(this.QTDsubParticulas) + "";
		// Log.v("TCC", "Raiz str 1 = " + raizSTR);
		raizSTR = raizSTR.substring(0, raizSTR.indexOf("."));
		// Log.v("TCC", "Raiz str 2 = " + raizSTR);
		// Divide a raiz quadrada das particulas em 4 partes
		float raizQtdPart = Float.parseFloat(raizSTR) / 4;
		// Log.v("TCC", "Raiz str 3 = " + raizQtdPart);

		// Variaveis temporarias para o posicionamento da gota
		int j = 0, q = 1, qx = 0;
		float xTemp = x, yTemp = y; // guardar a referencia do X,Y
		boolean volta1 = true, volta2 = true;

		// Cria array das subpartículas
		particulasARRAY[partInd].subParticulas = new GAParticula[this.QTDsubParticulas];

		// Percorre todas as partículas e cria instancias de cada uma
		for (int subInd = 0; subInd < particulasARRAY[partInd].subParticulas.length; subInd++) {
			// particulasARRAY[i].subParticulas[i] = new Particula();

			// Cria particula e codigo abaixa ja prepara a proxima posicao
			criaParticula(partInd, subInd, x, y, z);

			// Rotina para saber a posição que pintar a próxima subparticula

			j++; // Marca que pintou um elemento
			x = x - 0.1f; // diminui 1 da posicao x da proxima particula

			// Regra do pula linha para desenhar as particulas
			if (q == j && q <= (raizQtdPart * 3 + raizQtdPart / 2) && volta1) {
				xTemp = xTemp + this.tamanhoPart; // Posiciona Coluna
				yTemp = yTemp - this.tamanhoPart; // Posiciona Linha
				x = xTemp;
				y = yTemp;
				q++;
				j = 0;
			} else if (q == j && qx <= raizQtdPart + raizQtdPart / 2 && volta2) {
				volta1 = false;
				yTemp = yTemp - this.tamanhoPart; // Posiciona Linha
				x = xTemp;
				y = yTemp;
				qx++; // indica quantos espaços da parte igual vai pintar
				j = 0;
			} else if (q == j) {
				volta2 = false;
				xTemp = xTemp - this.tamanhoPart; // Posiciona Coluna
				yTemp = yTemp - this.tamanhoPart; // Posiciona Linha
				x = xTemp;
				y = yTemp;
				q--;
				j = 0;
			}

		}
	}

	public void criaParticula(int partInd, int subInd, float x, float y, float z) {

		this.particulasARRAY[partInd].subParticulas[subInd] = new GAParticula();

		// Marca como Particula Ativa
		this.particulasARRAY[partInd].subParticulas[subInd].ativa = true;

		// Criação das Novas instancias das Particulas
		this.particulasARRAY[partInd].subParticulas[subInd].x = x;// (0f + i) /
																	// 10;
		this.particulasARRAY[partInd].subParticulas[subInd].y = y; // (0f + i) /
																	// 10 ;
		// //this.limiteChao;
		this.particulasARRAY[partInd].subParticulas[subInd].z = z;// (0f + i) /
																	// 10;

		// X e Z velocidades aleatorias entre -1.0 e 1.0
		this.particulasARRAY[partInd].subParticulas[subInd].vx = (gerador
				.nextFloat() * 0.1f);
		this.particulasARRAY[partInd].subParticulas[subInd].vz = (gerador
				.nextFloat() * 0.1f);
		// Y velocidade aleatoria entre 3.0 e 7.0
		this.particulasARRAY[partInd].subParticulas[subInd].vy = 0;// (gerador.nextFloat()
		// *
		// 2) + 6f;

		// Somente Escala de Azul para representar a água
		this.particulasARRAY[partInd].subParticulas[subInd].azul = (gerador
				.nextFloat() + 1f) / 2f;
		this.particulasARRAY[partInd].subParticulas[subInd].vermelho = this.particulasARRAY[partInd].subParticulas[subInd].azul * .8f;
		this.particulasARRAY[partInd].subParticulas[subInd].verde = this.particulasARRAY[partInd].subParticulas[subInd].azul * .8f;

		this.particulasARRAY[partInd].subParticulas[subInd].tempoVida = gerador
				.nextFloat() * 5f + 1.0f;
		// Log.v(Constants.LOG, "pas 1.0 ind i " + ind + "_" + i +
		// " - lifetime "
		// + this.particulasARRAY[ind].subParticulas[i].tempoVida);

	}

	/**
	 * Carregar Preferencias
	 */
	public void carregarPref() {
		this.QTDparticulas = this.preferencias.getInt("qtdparticulas", 7);// 219);
		this.QTDsubParticulas = this.preferencias
				.getInt("qtdsubparticulas", 32);// 219);
		this.velocTempo = this.preferencias.getInt("velocTempo", 5000);
		this.gravidade = this.preferencias.getFloat("gravidade", 9.8f);
		this.resistenciaAR = this.preferencias.getFloat("resistenciaar", 1.0f);

		Log.i(Constants.LOG, "Carregou Preferencias com sucesso");
	}

	/**
	 * Carregar Preferencias OnLine Somente Gravidade e Tempo entre Frames
	 */
	public void carregarPref2() {
		this.velocTempo = this.preferencias.getInt("velocTempo", 8000);
		this.gravidade = this.preferencias.getFloat("gravidade", 9.8f);

		this.preferencias.setAtualizou(false);
		Log.i(Constants.LOG, "Carregou Preferencias online com sucesso");
	}

	/**
	 * Gravar Preferencias
	 */
	public void gravarPref() {

		this.preferencias.putInteger("qtdparticulas", this.QTDparticulas);
		this.preferencias.putInteger("qtdsubparticulas", this.QTDsubParticulas);
		this.preferencias.putInteger("velocTempo", this.velocTempo);
		this.preferencias.putFloat("gravidade", this.gravidade);
		this.preferencias.putFloat("resistenciaar", this.resistenciaAR);

		Log.i(Constants.LOG, "Gravou Preferencias com sucesso");

	}


}
