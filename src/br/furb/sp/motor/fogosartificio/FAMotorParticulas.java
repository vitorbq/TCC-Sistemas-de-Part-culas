package br.furb.sp.motor.fogosartificio;

import android.util.Log;
import br.furb.sp.motor.MotorParticulas;
import br.furb.sp.motor.Particula;
import br.furb.sp.util.Constants;
import br.furb.sp.util.Preferencias;
import javax.microedition.khronos.opengles.GL10;

public class FAMotorParticulas extends MotorParticulas {

	/**
	 * Construtor do Motor de Particulas
	 * 
	 * @param qtdParticulas
	 *            Quantidade de Partículas
	 */
	public FAMotorParticulas(Preferencias preferencias) {

		super(preferencias);
		// Cria Array
		this.particulasARRAY = new FAParticula[this.QTDparticulas];

		// Outros Parametros das partículas
		this.resistenciaAR = 1.0f;
		this.limiteChao = -1.5f;
		this.tamanhoPart = 0.05f;

		iniciaParticulas();

		this.estadoSistema = true;
		
	}
	
	/**
	 * Desenha as particulas
	 * 
	 * @param gl
	 *            Elemento Visao
	 */
	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pVertexBuffer);

		for (int i = 0; i < QTDparticulas; i++) {
			// somente desenha quando estiver ativa
			if (particulasARRAY[i].ativa) {
				// Desenha a Particula, caso ela nao tenha explodido
				if (!particulasARRAY[i].efeitoAtivo) {
					gl.glPushMatrix();
					gl.glColor4f(particulasARRAY[i].vermelho,
							particulasARRAY[i].verde, particulasARRAY[i].azul,
							1f);
					gl.glTranslatef(particulasARRAY[i].x, particulasARRAY[i].y,
							particulasARRAY[i].z);
					gl.glDrawElements(GL10.GL_TRIANGLES, 3,
							GL10.GL_UNSIGNED_SHORT, pIndiceBuffer);
					gl.glPopMatrix();
					// desenha a particula explodindo
				} else {
					float alphaN = 1.0f; // REVER esta configuração do ALPHA
					for (int j = 0; j < particulasARRAY[i].subParticulas.length; j++) {
						// Log.v("TCC", " draw explode" );
						gl.glPushMatrix();
						gl.glColor4f(
								particulasARRAY[i].subParticulas[j].vermelho,
								particulasARRAY[i].subParticulas[j].verde,
								particulasARRAY[i].subParticulas[j].azul,
								alphaN);
						gl.glTranslatef(particulasARRAY[i].subParticulas[j].x,
								particulasARRAY[i].subParticulas[j].y,
								particulasARRAY[i].subParticulas[j].z);
						gl.glDrawElements(GL10.GL_TRIANGLES, 3,
								GL10.GL_UNSIGNED_SHORT, pIndiceBuffer);
						gl.glPopMatrix();
						alphaN = alphaN - 0.1f;
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

		// Calcula tempo dos frames para definir velocidade
		long tempoAtual = System.currentTimeMillis();

		if (this.estadoSistema) {
			// Frame Rate é a velocidade
			float frameRate = (float) (tempoAtual - this.ultimoTempo)
					/ this.velocTempo;
			this.ultimoTempo = tempoAtual;
			// Log.i(TelaPrincipal.CATEGORIA, "framerate: " + frameRate);

			// Movimento das partículas
			// Percorre todas as partículas do sistema
			for (int i = 0; i < this.QTDparticulas; i++) {

				if (particulasARRAY[i].ativa) {

					// Aplica Resistencia do AR
					particulasARRAY[i].vx = particulasARRAY[i].vx
							- (particulasARRAY[i].vx * resistenciaAR * frameRate);
					particulasARRAY[i].vy = particulasARRAY[i].vy
							- (particulasARRAY[i].vy * resistenciaAR * frameRate);
					particulasARRAY[i].vz = particulasARRAY[i].vz
							- (particulasARRAY[i].vz * resistenciaAR * frameRate);

					// O Movimento será a Velocidade Aplicada a Gravidade
					particulasARRAY[i].vy = particulasARRAY[i].vy
							- (gravidade * frameRate);

					// Calular o Y anteriormente pra poder detectar quando
					// estiver
					// atingido o ponto máximo em Y
					float novoY = particulasARRAY[i].y
							+ (particulasARRAY[i].vy * frameRate);

					// Quando For partícula de explosão deve atualizar explosao
					if (particulasARRAY[i].efeitoAtivo) {

						updateEfeito(
								(FAParticula[]) particulasARRAY[i].subParticulas,
								frameRate, i);

					} else {

						// Verifica quando a partícula atingiu o ponto máximo e
						// começa a descer
						if (novoY < particulasARRAY[i].y) {

							// Explode Partícula
							particulasARRAY[i].subParticulas = new FAParticula[gerador
									.nextInt(20) + this.QTDsubParticulas];
							particulasARRAY[i].efeitoAtivo = true;

							// Executa criação das partículas de explosão
							criaSubParticulas(
									i,
									(FAParticula[]) particulasARRAY[i].subParticulas,
									particulasARRAY[i].x, particulasARRAY[i].y,
									particulasARRAY[i].z);

						} else {
							// movimento conforme a velocidade aplicada a
							// gravidade
							particulasARRAY[i].x = particulasARRAY[i].x
									+ (particulasARRAY[i].vx * frameRate);
							particulasARRAY[i].z = particulasARRAY[i].z
									+ (particulasARRAY[i].vz * frameRate);
							// Altura
							particulasARRAY[i].y = novoY;

						}
					} // if explode

				}
			}
		} else {
			this.ultimoTempo = tempoAtual;
		}

	}

	public void updateEfeito(FAParticula[] parExplode, float frameRate, int ind) {

		for (int i = 0; i < parExplode.length; i++) {
			// Aplica Resistencia do AR
			parExplode[i].vx = parExplode[i].vx
					- (parExplode[i].vx * resistenciaAR * frameRate);
			parExplode[i].vy = parExplode[i].vy
					- (parExplode[i].vy * resistenciaAR * frameRate);
			parExplode[i].vz = parExplode[i].vz
					- (parExplode[i].vz * resistenciaAR * frameRate);

			// Move a Particula de acordo com a velocidade
			parExplode[i].x = parExplode[i].x + (parExplode[i].vx * frameRate);
			parExplode[i].y = parExplode[i].y + (parExplode[i].vy * frameRate);
			parExplode[i].z = parExplode[i].z + (parExplode[i].vz * frameRate);

			// Efeito de Gravidade // diminui a gravidade
			parExplode[i].y = parExplode[i].y - ((gravidade - 7f) * frameRate);
			// parExplode[i].y = parExplode[i].y - ((gravidade)*frameRate);

			// Diminui Tempo de Vida
			parExplode[i].tempoVida = parExplode[i].tempoVida - frameRate;

			// Se a partícula atingiu seu tempo limite desativa
			if (parExplode[i].tempoVida < 0f) {
				particulasARRAY[ind].efeitoAtivo = false;
				particulasARRAY[ind].ativa = false;
				criaParticula(ind);
				// Log.v("TCC", " explode e ativa = false");
			}
		}
	}

	/**
	 * Cria Partículas da explosao
	 * 
	 * @param partExp
	 *            ParticulasARRAY Explode
	 */
	public void criaSubParticulas(int j, FAParticula[] partExp, float x,
			float y, float z) {
		for (int i = 0; i < partExp.length; i++) {
			partExp[i] = new FAParticula();

			// Posição Inicial
			partExp[i].x = x;
			partExp[i].y = y;
			partExp[i].z = z;

			// Aplica Velocidade Extra para quando explode
			float velocidadeExtra = (this.velocidade * 1.5f); // 10f;
			partExp[i].vx = (gerador.nextFloat() * velocidadeExtra)
					- (velocidadeExtra / 2f);
			partExp[i].vy = (gerador.nextFloat() * velocidadeExtra)
					- (velocidadeExtra / 2f);
			partExp[i].vz = (gerador.nextFloat() * velocidadeExtra)
					- (velocidadeExtra / 2f);

			// Atribui as cores das partículas, igual a particula origem
			partExp[i].azul = particulasARRAY[j].azul;
			partExp[i].vermelho = particulasARRAY[j].vermelho;
			partExp[i].verde = particulasARRAY[j].verde;

			// Atribui um tempo de vida para a partícula
			partExp[i].tempoVida = (gerador.nextFloat() * 1.5f) + 0.5f;

		}

	}

	public void criaParticulas() {
		// Percorre todas as partículas e cria instancias de cada uma
		for (int i = 0; i < this.QTDparticulas; i++) {
			particulasARRAY[i] = new FAParticula();
			criaParticula(i);
		}
	}

	public void criaParticula(int i) {

		particulasARRAY[i].ativa = true;
		
		Log.v("TCC", " aqui " + particulasARRAY[i]);

		// Criação das Novas instancias das Particulas
		particulasARRAY[i].x = 0f;
		particulasARRAY[i].y = this.limiteChao;
		particulasARRAY[i].z = 0f;

		// X e Z velocidades aleatorias entre -1.0 e 1.0
		particulasARRAY[i].vx = (gerador.nextFloat() * 4f) - 2f;
		particulasARRAY[i].vz = (gerador.nextFloat() * 2f) - 2f;
		// Y velocidade aleatoria entre 3.0 e 7.0
		particulasARRAY[i].vy = (gerador.nextFloat() * 2) + this.velocidade;// 6f;
		// Log.v(Constants.LOG,
		// "vel x " + particulasARRAY[i].vx +
		// "vel y " + particulasARRAY[i].vy +
		// "vel z " + particulasARRAY[i].vz
		// );

		// Colorido, Fogos de Artifífico
		particulasARRAY[i].azul = (gerador.nextFloat() * .5f) + .5f;
		particulasARRAY[i].vermelho = (gerador.nextFloat() * .5f) + .5f;
		particulasARRAY[i].verde = (gerador.nextFloat() * .5f) + .5f;

	}

	/**
	 * Carregar Preferencias
	 */
	public void carregarPref() {
		this.QTDparticulas = this.preferencias.getInt("qtdparticulas", 15);
		this.QTDsubParticulas = this.preferencias.getInt("qtdpartexplode", 20);
		this.velocTempo = this.preferencias.getInt("velocTempo", 8000);
		this.gravidade = this.preferencias.getFloat("gravidade", 9.8f);
		this.velocidade = this.preferencias.getFloat("velocidade", 6f);

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
		this.preferencias.putInteger("qtdpartexplode", this.QTDsubParticulas);
		this.preferencias.putInteger("velocTempo", this.velocTempo);
		this.preferencias.putFloat("gravidade", this.gravidade);
		this.preferencias.putFloat("velocidade", this.velocidade);

		Log.i(Constants.LOG, "Gravou Preferencias com sucesso");

	}

}
