package br.furb.sp.string;

import javax.microedition.khronos.opengles.GL10;
import android.graphics.Paint;

public class DrawSTR {

	// Tamanho da Label
	private int width,
				height;
	private int widthWorld,
				heightWorld;
	
	// Variável local que guarda referencia do laberMaker
	private LabelMaker labelMaker;
	
	// Referencias para os textos que irão ser desenhados
	private int idTexturaFPS[];
	private int idTexturaW[];
	private int idTexturaH[];

	
	public DrawSTR(final int width, final int height) {
		this.widthWorld = width;
		this.heightWorld = height;
	}
	
	/**
	 * Realiza a Inicializacao dos Caracteres que serão utilizados Por medidas
	 * de desempenho e menos consumo de memória, só iremos iniciar os caracteres
	 * que serão utilizados no aplicativo
	 * 
	 * @param gl10
	 * @param paint
	 */
	public void initFPS(final GL10 gl10, final Paint paint) {

		// Inicializa Variáveis Locais que são referencia dos Caracteres
		this.idTexturaFPS = new int[14];
		this.idTexturaW = new int[14];
		this.idTexturaH = new int[14];

		this.width = 512;//256;
		this.height = 128;//64;
		this.labelMaker = new LabelMaker(false, this.width, this.height);
		
		// Inicializa e configura LabelMaker para Receber os dados
		this.labelMaker.initialize(gl10);
		this.labelMaker.beginAdding(gl10);
		
		String caracter = "";
		int idLBSTR = 0;

		// Percorre os Decimais de 0 a 9 e adiciona no LabelMaker
		for (int cont = 0; cont < 10; cont++) {
			caracter = String.valueOf(cont);
			// Adiciona caracter referencia no laberMaker
			idLBSTR = this.labelMaker.add(gl10, caracter, paint);
			// Atualiza as referencias locais;
			this.idTexturaFPS[cont] = idLBSTR;
			this.idTexturaW[cont] = (int) Math.ceil(this.labelMaker
					.getWidth(idLBSTR));
			this.idTexturaH[cont] = (int) Math.ceil(this.labelMaker
					.getHeight(idLBSTR));
		}
		
		// Armazena também os caracteres F P S
		caracter = String.valueOf("F");
		idLBSTR = this.labelMaker.add(gl10, caracter, paint);
		this.idTexturaFPS[10] = idLBSTR;
		this.idTexturaW[10] = (int) Math.ceil(this.labelMaker
				.getWidth(idLBSTR));
		this.idTexturaH[10] = (int) Math.ceil(this.labelMaker
				.getHeight(idLBSTR));
		
		caracter = String.valueOf("P");
		idLBSTR = this.labelMaker.add(gl10, caracter, paint);
		this.idTexturaFPS[11] = idLBSTR;
		this.idTexturaW[11] = (int) Math.ceil(this.labelMaker
				.getWidth(idLBSTR));
		this.idTexturaH[11] = (int) Math.ceil(this.labelMaker
				.getHeight(idLBSTR));
		
		caracter = String.valueOf("S");
		idLBSTR = this.labelMaker.add(gl10, caracter, paint);
		this.idTexturaFPS[12] = idLBSTR;
		this.idTexturaW[12] = (int) Math.ceil(this.labelMaker
				.getWidth(idLBSTR));
		this.idTexturaH[12] = (int) Math.ceil(this.labelMaker
				.getHeight(idLBSTR));

		caracter = String.valueOf(" ");
		idLBSTR = this.labelMaker.add(gl10, caracter, paint);
		this.idTexturaFPS[13] = idLBSTR;
		this.idTexturaW[13] = (int) Math.ceil(this.labelMaker
				.getWidth(idLBSTR));
		this.idTexturaH[13] = (int) Math.ceil(this.labelMaker
				.getHeight(idLBSTR));

		// Configura o LaberMaker para encerrar a recepcao
		this.labelMaker.endAdding(gl10);
	}

	public void drawFPS(final GL10 gl, final String numFPS) {
		
		// Posicoes que serao desenhadas
		int x = this.widthWorld - 90,
			y = this.heightWorld - 75;//115;
		int codigoSTR = 10;
		
		// Desenha os caracteres
		this.labelMaker.beginDrawing(gl, this.width, this.height);
		
		// primeiro os caracteres F P S
		// F
		this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		x += this.idTexturaW[codigoSTR];
		codigoSTR++;
		
		// P
		this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		x += this.idTexturaW[codigoSTR];
		codigoSTR++;
		
		// S
		this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		x += this.idTexturaW[codigoSTR];
		codigoSTR++;
		
		// espaco vazio 2 vezes
		this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		x += this.idTexturaW[codigoSTR];
		this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		x += this.idTexturaW[codigoSTR];

		// Desenha o valor do FPS
		codigoSTR = Integer.parseInt((String) numFPS.subSequence(0, 1));
		this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		x += this.idTexturaW[codigoSTR];		
		// Se o valor for de 2 digitos
		if (numFPS.trim().length() > 1){
			codigoSTR = Integer.parseInt((String) numFPS.subSequence(1, 2));
			this.labelMaker.draw(gl, x, y, this.idTexturaFPS[codigoSTR]);
		}

		this.labelMaker.endDrawing(gl);
	}

}
