package br.furb.sp.motor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import br.furb.sp.util.Preferencias;

public abstract class MotorParticulas {

	// Array para armazenar as partículas
	public Particula[] particulasARRAY;

	// Buffers para desenhar as particulas
	public FloatBuffer pVertexBuffer;
	public ShortBuffer pIndiceBuffer;

	// gerador para posicao e cor das particulas
	protected Random gerador;
	// variavel auxiliar para calcular o framerate
	protected long ultimoTempo;

	// Quantidade de partículas e subparticulas
	public int QTDparticulas;
	public int QTDsubParticulas;

	// Velocidade Tempo
	protected int velocTempo;// = 8000f;
	// Gravidade 9,80665 m/s²
	protected float gravidade;// = 9.8f;
	// Velocidade das Particuals
	protected float velocidade;// = 9.8f;
	// Resistencia do Ar
	protected float resistenciaAR;// = 1.0f; // 20

	// Tamanho da Particula
	protected float tamanhoPart;
	// Chão
	protected float limiteChao;

	// Estado Em execução ou Pausado
	protected boolean estadoSistema;
	// Preferencias
	protected Preferencias preferencias;
	protected int contFrame = 0;

	/**
	 * Construtor do Motor de Particulas
	 * 
	 * @param qtdParticulas
	 *            Quantidade de Partículas
	 */
	public MotorParticulas(Preferencias preferencias) {

		this.estadoSistema = false;
		
		this.preferencias = preferencias;

		carregarPref();

	}
	
	public void iniciaParticulas(){

		// Random para gerador de partículas
		gerador = new Random(System.currentTimeMillis());

		// Inicializa Particulas
		criaParticulas();

		// cria triangulo para servir de particula
		float[] coord = { -tamanhoPart, 0.0f, 0.0f, tamanhoPart, 0.0f, 0.0f,
				0.0f, tamanhoPart, 0.0f };

		// coordenadas indices
		short[] icoord = { 0, 1, 2 };

		// Constroi buffers para os pontos das partículas
		pVertexBuffer = ConstroiFloatBuffer(coord);
		pIndiceBuffer = ConstroiShortBuffer(icoord);

		this.ultimoTempo = System.currentTimeMillis();
	}
	
	// Utilizado para criar Buffer de ordem nativa
	private FloatBuffer ConstroiFloatBuffer(float[] coordenadas) {
		ByteBuffer byteBuffer = ByteBuffer
				.allocateDirect(coordenadas.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder()); // Esta versão do SDK exige
													// ordem nativa
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(coordenadas); // empilha
		floatBuffer.position(0);
		return floatBuffer;
	}

	// Utilizado para criar Buffer de ordem nativa
	private ShortBuffer ConstroiShortBuffer(short[] icoordenadas) {
		ByteBuffer byteBuffer = ByteBuffer
				.allocateDirect(icoordenadas.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		shortBuffer.put(icoordenadas);
		shortBuffer.position(0);
		return shortBuffer;
	}

	/**
	 * Atualiza o Comportamento da Particula
	 */
	public void update() {

	}

	/**
	 * Atualiza o Comportamento da SubParticula
	 */
	public void updateEfeito() {

	}
	
	/**
	 * Desenha as particulas
	 * 
	 * @param gl
	 *            Elemento Visao
	 */
//	public void draw(GL10 gl) {
//	}
	

	/**
	 * Destroi Particula informada
	 * 
	 * @param i
	 *            Indice da Particula no array
	 */
	public void destroiParticula(int i) {
		// Zera posicoes
		particulasARRAY[i].x = 0f;
		particulasARRAY[i].y = 0f;
		particulasARRAY[i].z = 0f;
		// Zera Velocidades
		particulasARRAY[i].vx = 0f;
		particulasARRAY[i].vy = 0f;
		particulasARRAY[i].vz = 0f;
	}

	/**
	 * Particula e SubParticulas dentro da Gota
	 */
	public void criaParticula(int partInd){

	}

	/**
	 * Percorre numero de particulas e cria cada uma
	 */
	public void criaParticulas(){
		
	}

	/**
	 * Carregar Preferencias
	 */
	public void carregarPref() {

	}
	
	
	/**
	 * Gravar Preferencias
	 */
	public void gravarPref() {
	
	}

	/**
	 * Retorna se o Motor de Partículas está em execução ou Pausado
	 * 
	 * @return
	 */
	public boolean isAtivo() {
		return this.estadoSistema;
	}
	
	/**
	 * Continua Aplicação
	 */
	public void continuarSimulacao() {
		this.estadoSistema = true;
	}
	
	/**
	 * Pausa a Simulação
	 */
	public void pausarSimulacao() {
		this.estadoSistema = false;		
	}

}
