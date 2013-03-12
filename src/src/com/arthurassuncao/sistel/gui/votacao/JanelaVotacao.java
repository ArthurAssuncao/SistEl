package com.arthurassuncao.sistel.gui.votacao;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.classes.partido.PartidoPolitico;
import com.arthurassuncao.sistel.classes.votacao.Votacao;
import com.arthurassuncao.sistel.eventos.FixedLengthWithNumerosPositivosDocument;
import com.arthurassuncao.sistel.eventos.votacao.TratadorEventoMouseVotacao;
import com.arthurassuncao.sistel.eventos.votacao.TratadorEventoTecladoVotacao;
import com.arthurassuncao.sistel.gui.Fonte;
import com.arthurassuncao.sistel.gui.Imagem;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;
import com.arthurassuncao.sistel.recursos.Recursos;

/** A classe <code>JanelaVotacao</code> cria uma urna para a votacao
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 */
public class JanelaVotacao extends Janela {

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 8374417712791791834L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  	= 800;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 	= 600;

	private final String LOGO_JUSTICA_ELEITORAL = "imagens/tse.jpg";
	private final String FOTO_CANDIDATO_TRANSPARENTE = "imagens/foto_candidato_transparente.png";

	private int numeroDigitosCargo = 0;
	private int larguraTelaUrna = 420;
	private int alturaTelaUrna = 300;
	private final double escalaFotoCandidato = 0.8;

	private Votacao votacao;
	private List<Cargo> cargos;
	private int cargoPosicaoAtual = -1;
	private boolean votoNulo;

	//painéis
	private Painel painelTotal;
	private JLayeredPane painelTela;

	private Painel painelDadosPrincipais; //cargo e numero, apenas
	private Painel painelNome;
	private Painel painelPartido;
	private Painel painelNumeroErrado;
	private Painel painelMensagem;
	private Painel painelSeuVotoPara;
	private Painel painelNorte;
	private Painel painelFimVotacao;

	private Painel painelLogo;
	private Painel painelBotoes;
	private Painel painelBotoesNumericos;
	private Painel painelBotoesBCC; //branco, corrige, confirma

	//Campos
	//private JTextField campoCargoCandidato;
	private LabelRotulo campoNumeroCandidato;
	//private JTextField campoNomeCandidato;
	//private JTextField campoPartidoCandidato;

	//Botoes
	private JButton botaoIniciarVotacao;
	private JButton[] botoesTecladoNumerico;
	private JButton botaoConfirmar;
	private JButton botaoBranco;
	private JButton botaoCorrigir;
	private JButton botaoEncerrarVotacao;

	//label
	//private LabelRotulo labelCargoCandidato;
	private LabelRotulo labelNomeCandidato;
	private LabelRotulo labelPartidoCandidato;

	private LabelRotulo labelCargo; //label Cargo
	private LabelRotulo labelNumero;
	private LabelRotulo labelNome;
	private LabelRotulo labelPartido;
	private LabelRotulo labelFotoCandidato;

	private LabelRotulo labelSeuVotoPara;
	private LabelRotulo labelCandidatoInexistente;
	private LabelRotulo labelVotoBranco;

	//text documents
	//private NumerosPositivosDocument documentCampoNumero = new NumerosPositivosDocument();
	private FixedLengthWithNumerosPositivosDocument documentCampoNumero = new FixedLengthWithNumerosPositivosDocument(0);

	//Grid
	private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	Insets insetsPadrao = new Insets(5, 5, 5, 5);

	/** Cria uma instancia da janela de votacao
	 * @param votacao <code>Votacao</code> com a votacao que será apurada
	 * 
	 */
	public JanelaVotacao(Votacao votacao){
		super("Votação", LARGURA, ALTURA);

		this.votacao = votacao;

		this.cargos = votacao.getAllCargos();
		Collections.sort(this.cargos); //ordena, quanto maior o numero de digitos menor("menos" importante) é o cargo

		this.iniciaElementos();

		this.proximoCargo();

		this.limpaCampos();

		this.setaPropriedades();

		this.addElementos();

		this.pack();

		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}

	/** Instancia os elementos e alem de setar algumas propriedades de alguns componentes e adicionar eventos aos elementos
	 * 
	 */
	private void iniciaElementos()
	{
		//inicia painéis
		painelTotal = new Painel(new GridBagLayout());
		painelLogo = new Painel(new GridBagLayout());
		//painelTela = new Painel(new BorderLayout());
		painelTela = new JLayeredPane();
		painelDadosPrincipais = new Painel(new GridBagLayout());
		painelNome = new Painel(new GridBagLayout());
		painelPartido = new Painel(new GridBagLayout());
		painelNumeroErrado = new Painel(new GridBagLayout());
		painelMensagem = new Painel(new GridBagLayout());
		painelNorte = new Painel(new GridBagLayout());
		painelFimVotacao = new Painel(new GridBagLayout());
		painelSeuVotoPara = new Painel(new GridBagLayout());
		painelBotoes = new Painel(new GridBagLayout());
		painelBotoesNumericos = new Painel(new GridBagLayout());
		painelBotoesBCC = new Painel(new GridBagLayout());
		//painelTela.setPreferredSize(new Dimension(217, 285));

		//inicia os campos
		//campoNumeroCandidato = new JTextField(10);
		campoNumeroCandidato = new LabelRotulo();	
		//inicia os botões
		botoesTecladoNumerico = new JButton[10];
		for(int i=0; i < botoesTecladoNumerico.length; i++){
			//botoesTecladoNumerico[i] = new JButton(String.valueOf(i));
			botoesTecladoNumerico[i] = new BotaoEleicao(String.valueOf(i), BotaoEleicao.BOTAO_NUMERO);
			botoesTecladoNumerico[i].addMouseListener(new TratadorEventoMouseVotacao(this));
			botoesTecladoNumerico[i].setFocusable(false);
			//botoesTecladoNumerico[i].setBackground(Color.WHITE);
			botoesTecladoNumerico[i].setForeground(Color.BLACK);
		}
		botaoIniciarVotacao = new JButton("Iniciar Votação");
		/*botaoConfirmar = new JXButton("Confirma");
		botaoBranco = new JXButton("Branco");
		botaoCorrigir = new JXButton("Corrigir");*/
		botaoConfirmar = new BotaoEleicao("Confirmar", BotaoEleicao.BOTAO_CONFIRMAR);
		botaoBranco = new BotaoEleicao("Branco", BotaoEleicao.BOTAO_BRANCO);
		botaoCorrigir = new BotaoEleicao("Corrige", BotaoEleicao.BOTAO_CORRIGE);
		botaoEncerrarVotacao = new JButton("Encerrar");

		//adiciona tratador de eventos aos botoes que nao sao numericos
		botaoConfirmar.addMouseListener(new TratadorEventoMouseVotacao(this));
		botaoBranco.addMouseListener(new TratadorEventoMouseVotacao(this));
		botaoCorrigir.addMouseListener(new TratadorEventoMouseVotacao(this));

		//adiciona tratador de eventos aos botoes que nao sao numericos
		campoNumeroCandidato.addKeyListener(new TratadorEventoTecladoVotacao(this));

		//inicia os labels		
		labelSeuVotoPara = new LabelRotulo("SEU VOTO PARA");
		labelSeuVotoPara.setFont(new Fonte(12F, Fonte.ESTILO_NEGRITO).getFont());
		labelCandidatoInexistente = new LabelRotulo("CANDIDATO INEXISTENTE");
		labelVotoBranco = new LabelRotulo("VOTO EM BRANCO");
		labelVotoBranco.setFont(new Fonte(22F).getFont());

		Font fonteLabelsDados = new Fonte(12F).getFont();

		labelCargo = new LabelRotulo("Cargo Teste");
		labelCargo.setFont(new Fonte(21.F).getFont());
		labelNome = new LabelRotulo("Nome: ");
		labelNumero = new LabelRotulo("Número: ");
		labelPartido = new LabelRotulo("Partido: ");

		labelNome.setFont(fonteLabelsDados);
		labelNumero.setFont(fonteLabelsDados);
		labelPartido.setFont(fonteLabelsDados);

		//labelCargoCandidato = new LabelRotulo();
		labelNomeCandidato = new LabelRotulo();
		labelPartidoCandidato = new LabelRotulo();

		labelNomeCandidato.setFont(fonteLabelsDados);
		labelPartidoCandidato.setFont(fonteLabelsDados);

		labelFotoCandidato = new LabelRotulo(new ImageIcon(Recursos.getResource(Candidato.FOTO_PADRAO)));
		//labelFotoCandidato.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		//inicia o grid
		gridBagConstraint = new GridBagConstraints();
		gridBagConstraint.insets = insetsPadrao;
	}

	private void setaPropriedades(){
		//seta cor de fundo dos paineis e outros atributos
		Color corFundoBotoes = new Color(107, 105, 108);
		painelBotoes.setBackground(corFundoBotoes);
		painelBotoesNumericos.setBackground(corFundoBotoes);
		painelBotoesBCC.setBackground(corFundoBotoes);
		painelBotoes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		painelLogo.setBackground(Color.LIGHT_GRAY);
		painelLogo.setPreferredSize(new Dimension(267, 60));
		//painelLogo.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		//painelLogo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		ImageIcon logoJusticaEleitoral = new ImageIcon(Recursos.getResource(LOGO_JUSTICA_ELEITORAL));
		logoJusticaEleitoral.setImage(logoJusticaEleitoral.getImage().getScaledInstance(267, 60, 100));
		LabelRotulo labelLogoJusticaEleitoral = new LabelRotulo(logoJusticaEleitoral);
		labelLogoJusticaEleitoral.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		painelLogo.add(labelLogoJusticaEleitoral);

		Color corTela = new Color(228, 228, 228);
		//painelTela.setLayout(new GridBagLayout()); //por padrao JLayeredPane nao tem layout
		painelTela.setLayout(this.getLayeredPane().getLayout()); //por padrao JLayeredPane nao tem layout
		painelTela.setBackground(corTela);
		painelTela.setOpaque(true); //coloca o componente como opaco, assim nao fica com transparencia, mantendo a cor de fundo por todo ele
		painelTela.setPreferredSize(new Dimension(larguraTelaUrna, alturaTelaUrna));
		painelTela.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		painelNumeroErrado.setBackground(corTela);
		//painelNumeroErrado.setVisible(false);

		painelNorte.setBackground(corTela);

		painelNome.setBackground(corTela);
		painelNome.setPreferredSize(new Dimension(larguraTelaUrna, 25));
		painelNome.setMinimumSize(new Dimension(larguraTelaUrna, 25));
		painelPartido.setBackground(corTela);
		painelPartido.setPreferredSize(new Dimension(larguraTelaUrna, 25));
		painelPartido.setMinimumSize(new Dimension(larguraTelaUrna, 25));
		painelDadosPrincipais.setBackground(corTela);

		painelSeuVotoPara.setBackground(corTela);

		painelMensagem.setBackground(corTela);
		painelMensagem.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		painelMensagem.setPreferredSize(new Dimension(larguraTelaUrna, 100));
		
		painelFimVotacao.setBackground(corTela);

		painelTotal.setBackground(new Color(224, 219, 223));

		labelNomeCandidato.setPreferredSize(new Dimension(larguraTelaUrna - 50, 25));
		labelNomeCandidato.setMinimumSize(new Dimension(larguraTelaUrna - 50, 25));

		labelNome.setPreferredSize(new Dimension(50, 25));
		labelNome.setMinimumSize(new Dimension(50, 25));
		
		labelPartido.setPreferredSize(new Dimension(50, 25));
		labelPartido.setMinimumSize(new Dimension(50, 25));
		
		labelPartidoCandidato.setPreferredSize(new Dimension(50, 25));
		labelPartidoCandidato.setMinimumSize(new Dimension(50, 25));

		campoNumeroCandidato.setPreferredSize(new Dimension(100, 30));
		campoNumeroCandidato.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		//campoNumeroCandidato.setDocument(documentCampoNumero);
		//campoNumeroCandidato.setPreferredSize(new Dimension(100, 50));
		campoNumeroCandidato.setFont(new Fonte(20F, Fonte.ESTILO_NEGRITO).getFont());

		botaoIniciarVotacao.setFocusable(false);
		botaoConfirmar.setFocusable(false);
		botaoBranco.setFocusable(false);
		botaoCorrigir.setFocusable(false);
		botaoEncerrarVotacao.setFocusable(false);

		botaoConfirmar.setBackground(Color.GREEN);
		botaoBranco.setBackground(Color.WHITE);
		botaoCorrigir.setBackground(Color.ORANGE);
	}

	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		//adiciona ao painelBotoes
		GridBagConstraints gridBotoes;
		gridBotoes = new GridBagConstraints();
		gridBotoes.insets = insetsPadrao;
		gridBotoes.gridx = 0;
		gridBotoes.gridy = 0;		
		painelBotoesNumericos.add(botoesTecladoNumerico[1], gridBotoes);
		gridBotoes.gridx = 1;
		painelBotoesNumericos.add(botoesTecladoNumerico[2], gridBotoes);
		gridBotoes.gridx = 2;
		painelBotoesNumericos.add(botoesTecladoNumerico[3], gridBotoes);
		gridBotoes.gridx = 0;
		gridBotoes.gridy = 1;
		painelBotoesNumericos.add(botoesTecladoNumerico[4], gridBotoes);
		gridBotoes.gridx = 1;
		painelBotoesNumericos.add(botoesTecladoNumerico[5], gridBotoes);
		gridBotoes.gridx = 2;
		painelBotoesNumericos.add(botoesTecladoNumerico[6], gridBotoes);
		gridBotoes.gridx = 0;
		gridBotoes.gridy = 2;
		painelBotoesNumericos.add(botoesTecladoNumerico[7], gridBotoes);
		gridBotoes.gridx = 1;
		painelBotoesNumericos.add(botoesTecladoNumerico[8], gridBotoes);
		gridBotoes.gridx = 2;
		painelBotoesNumericos.add(botoesTecladoNumerico[9], gridBotoes);
		gridBotoes.gridx = 1;
		gridBotoes.gridy = 3;
		painelBotoesNumericos.add(botoesTecladoNumerico[0], gridBotoes);

		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.weightx = 1.0;
		gridBagConstraint.weighty = 1.0;

		gridBagConstraint.anchor = GridBagConstraints.SOUTH;
		painelBotoesBCC.add(botaoBranco, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelBotoesBCC.add(botaoCorrigir, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelBotoesBCC.add(botaoConfirmar, gridBagConstraint);
		gridBagConstraint.anchor = GridBagConstraints.CENTER;

		//adiciona ao painelBotoes
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.insets = new Insets(20, 5, 0, 5);
		painelBotoes.add(painelBotoesNumericos, gridBagConstraint);
		gridBagConstraint.gridy = 1;
		gridBagConstraint.insets = new Insets(0, 5, 20, 5);
		painelBotoes.add(painelBotoesBCC, gridBagConstraint);

		gridBagConstraint.insets = insetsPadrao;

		//Adiciona aos paineis que comporao a tela
		//adiciona ao painelMensagem
		int linha = 0;
		GridBagConstraints gridMensagem = new GridBagConstraints();
		gridMensagem.gridx = 0;
		gridMensagem.gridy = 0;
		gridMensagem.fill = GridBagConstraints.HORIZONTAL;
		gridMensagem.insets = new Insets(0, 0, 0, 200);
		gridMensagem.anchor = GridBagConstraints.LINE_START;
		LabelRotulo labelMensagemParte1 = new LabelRotulo("Aperte a Tecla: ");
		painelMensagem.add(labelMensagemParte1, gridMensagem);
		gridMensagem.gridx = 0;
		gridMensagem.gridy = 1;
		LabelRotulo labelMensagemParte2 = new LabelRotulo("       VERDE para CONFIRMAR este voto");
		painelMensagem.add(labelMensagemParte2, gridMensagem);
		gridMensagem.gridy = 2;
		LabelRotulo labelMensagemParte3 = new LabelRotulo("   LARANJA para REINICAR este voto");
		painelMensagem.add(labelMensagemParte3, gridMensagem);

		gridMensagem.gridx = 0;
		gridMensagem.gridy = 0;
		gridMensagem.fill = GridBagConstraints.HORIZONTAL;
		gridMensagem.insets = new Insets(0, 0, 0, 200);
		painelSeuVotoPara.add(labelSeuVotoPara, gridMensagem);

		//adiciona ao painelNumeroErrado
		GridBagConstraints gridNumeroErrado = new GridBagConstraints();

		gridNumeroErrado.gridx = 0;
		gridNumeroErrado.gridy = 1;
		gridNumeroErrado.fill = GridBagConstraints.HORIZONTAL;
		gridNumeroErrado.anchor = GridBagConstraints.LINE_START;
		LabelRotulo labelNumeroErradoParte1 = new LabelRotulo("NÚMERO ERRADO");
		painelNumeroErrado.add(labelNumeroErradoParte1, gridNumeroErrado);
		gridNumeroErrado.anchor = GridBagConstraints.CENTER;
		LabelRotulo labelNumeroErradoParte2 = new LabelRotulo("VOTO NULO");
		labelNumeroErradoParte2.setFont(new Fonte(18F, Fonte.ESTILO_NEGRITO).getFont());
		gridNumeroErrado.gridy = 3;
		gridNumeroErrado.insets = new Insets(30, 150, 0, 0);
		painelNumeroErrado.add(labelNumeroErradoParte2, gridNumeroErrado);

		gridBagConstraint.insets = insetsPadrao;
		gridBagConstraint.anchor = GridBagConstraints.LINE_START;
		gridBagConstraint.gridheight = 1;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;

		LabelRotulo labelTituloVotacao = new LabelRotulo("VOTAÇÃO");
		labelTituloVotacao.setFont(new Fonte(12F, Fonte.ESTILO_NORMAL).getFont());
		painelNorte.add(labelTituloVotacao, gridBagConstraint);

		gridBagConstraint.insets = insetsPadrao;
		gridBagConstraint.gridheight = 1;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.anchor = GridBagConstraints.WEST;
		painelDadosPrincipais.add(labelCargo, gridBagConstraint);
		gridBagConstraint.gridy = 1;
		gridBagConstraint.insets = new Insets(20, insetsPadrao.left, insetsPadrao.bottom, insetsPadrao.right);
		gridBagConstraint.anchor = GridBagConstraints.WEST;
		painelDadosPrincipais.add(campoNumeroCandidato, gridBagConstraint);
		gridBagConstraint.gridy = 2;
		gridBagConstraint.insets = new Insets(insetsPadrao.top, 50, insetsPadrao.bottom, insetsPadrao.right);
		painelDadosPrincipais.add(labelVotoBranco, gridBagConstraint);
		

		//dados
		//Adiciona ao painelNome
		linha = 0;
		gridBagConstraint.anchor = GridBagConstraints.WEST;
		gridBagConstraint.insets = insetsPadrao;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelNome.add(labelNome, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelNome.add(labelNomeCandidato, gridBagConstraint);

		//Adiciona ao painelPartido
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelPartido.add(labelPartido, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelPartido.add(labelPartidoCandidato, gridBagConstraint);
		
		//seta sem opacidade
		painelNorte.setOpaque(false);
		painelDadosPrincipais.setOpaque(false);
		painelMensagem.setOpaque(false);
		painelSeuVotoPara.setOpaque(false);
		painelNome.setOpaque(false);
		painelNumeroErrado.setOpaque(false);
		painelPartido.setOpaque(false);

		//Adiciona ao painelTela
		int camada = 0; //camada mais superior
		painelNorte.setBounds(0, 0, 70, 20);
		painelTela.add(painelNorte, new Integer(camada++));
		//labelCargo.getText().length() * 8; //= 128
		/*painelDadosPrincipais.setBounds(200 - 128, 50, labelCargo.getText().length() * 14, 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight()); //centraliza o cargo, 6 centraliza
		painelTela.add(painelDadosPrincipais, new Integer(camada++));*/
		painelDadosPrincipais.setBounds(52, 50, larguraTelaUrna - 55, 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight()); //centraliza o cargo, 6 centraliza
		painelTela.add(painelDadosPrincipais, new Integer(camada++));

		int alturaMensagem = 55;
		painelMensagem.setBounds(4 / 2, alturaTelaUrna - alturaMensagem, larguraTelaUrna - 4, alturaMensagem);
		painelTela.add(painelMensagem, new Integer(camada++));

		painelSeuVotoPara.setBounds(0, 10, 310, 25);
		painelTela.add(painelSeuVotoPara, new Integer(camada++));

		labelNumero.setBounds(5, 23 + 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight(), 50, 25);
		painelTela.add(labelNumero, new Integer(camada++));

		painelNome.setBounds(0, 25 + 23 + 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight(), 420, 25);
		painelTela.add(painelNome, new Integer(camada++));

		painelNumeroErrado.setBounds(5, 25 + 23 + 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight(), 260, 80);
		painelTela.add(painelNumeroErrado, new Integer(camada++));

		painelPartido.setBounds(0, 25 + 25 + 23 + 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight(), 110, 25);
		painelTela.add(painelPartido, new Integer(camada++));
		
		labelFotoCandidato.setBounds(420 - (int)(Candidato.LARGURA_FOTO * escalaFotoCandidato) - 5, 5, (int)(Candidato.LARGURA_FOTO * escalaFotoCandidato), (int)(Candidato.ALTURA_FOTO * escalaFotoCandidato));
		painelTela.add(labelFotoCandidato, new Integer(camada++));
		
		labelCandidatoInexistente.setBounds(5, 25 + 23 + 70 + (int)campoNumeroCandidato.getPreferredSize().getHeight(), 420, 25);
		painelTela.add(labelCandidatoInexistente, new Integer(camada++));

		//adiciona ao painelTudo
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.gridheight = 2;
		gridBagConstraint.insets = new Insets(20, 60, 5, 60);
		painelTotal.add(painelTela, gridBagConstraint);
		gridBagConstraint.insets = insetsPadrao;
		gridBagConstraint.gridheight = 1;
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = 0;
		gridBagConstraint.insets = new Insets(20, 5, 0, 10);
		painelTotal.add(painelLogo, gridBagConstraint);
		gridBagConstraint.gridy = 1;
		gridBagConstraint.gridx = 1;
		gridBagConstraint.insets = new Insets(2, 5, 10, 10);
		painelTotal.add(painelBotoes, gridBagConstraint);

		//adiciona na janela
		this.add(painelTotal);		
	}

	/** Verifica se um <code>JButton</code> é uma referencia a um botao do teclado numerico ou nao
	 * @param botao <code>JButton</code> com o botao
	 * @return <code>boolean</code> com <code>true</code> se a referencia é igual a de um botao do teclado numerico e <code>false</code> senão
	 */
	public boolean isBotaoTecladoNumerico(JButton botao){
		for(int i=0; i < botoesTecladoNumerico.length; i++){
			if(botoesTecladoNumerico[i] == botao){
				return true; //é um botao do teclado numerico
			}
		}
		return false;
	}

	/** Retorna um <code>boolean</code> informando se o voto é branco ou não
	 * @return <code>boolean</code> com <code>true</code> se o voto é um voto em branco e <code>false</code> senão
	 */
	public boolean isVotoBranco(){
		return this.votoNulo;
	}

	/** Informa se o voto é nulo ou nao
	 * @param votoNulo <code>boolean</code> com <code>true</code> se o voto é nulo e <code>false</code> senão
	 */
	public void setVotoNulo(boolean votoNulo){
		this.votoNulo = votoNulo;
	}

	//modifica a tela com os dados para a escolha do proximo candidato(de outro cargo)
	/** Muda a tela(urna) para a escolha do proximo cargo, se não houver proximo, é mostrada a mensagem de fim
	 * 
	 */
	public void proximoCargo(){
		if(this.cargoPosicaoAtual < cargos.size()){
			this.cargoPosicaoAtual++;
			if(this.cargoPosicaoAtual < cargos.size()){
				this.numeroDigitosCargo = cargos.get(this.cargoPosicaoAtual).getNumeroDigitos();
				this.labelCargo.setText(cargos.get(this.cargoPosicaoAtual).getNome().toUpperCase());
				this.documentCampoNumero.setMaxLength(this.numeroDigitosCargo);
				this.limpaCampos();
				//System.out.println("Comentei a linha 466 abaixo de 'this.documentCampoNumero.setMaxLength(this.numeroDigitosCargo);'");
			}
			else{
				this.fimVotacao(); //fim da votacao para este usuario
			}
		}
		else{
			this.fimVotacao(); //fim da votacao para este usuario
		}
	}

	/** Mostra a mensagem de fim da votacao para o eleitor
	 * 
	 */
	public void fimVotacao(){
		//JanelaMensagem.mostraMensagem(this, "Votacao Encerrada", "Este usuario votou em todos candidatos necessarios");
		AudioClip somCliqueConfirmaFim = null;
		somCliqueConfirmaFim = Applet.newAudioClip(Recursos.getResource("sons/somUrnaConfirmaFim.wav"));
		somCliqueConfirmaFim.play();
		
		this.painelTela.removeAll();

		//adiciona ao painelFimVotacao
		int camada = 0;
		painelTela.add(painelNorte, new Integer(camada++));

		LabelRotulo labelFim = new LabelRotulo("FIM");
		labelFim.setFont(new Fonte(120F, Fonte.ESTILO_NEGRITO).getFont());
		labelFim.setBounds(100, 50, 300, 150);
		painelTela.add(labelFim, new Integer(camada++));

		LabelRotulo labelVotou = new LabelRotulo("VOTOU");
		labelVotou.setFont(new Fonte(20F, Fonte.ESTILO_NEGRITO).getFont());
		labelVotou.setForeground(Color.GRAY);
		labelVotou.setBounds(larguraTelaUrna - 80, alturaTelaUrna - 40, 80, 40);
		painelTela.add(labelVotou, new Integer(camada++));
		
		painelTela.repaint();
		painelTela.revalidate();
	}

	/** Muda a foto do candidato
	 * @param foto <code>String</code> com o endereco da foto do candidato
	 */
	public void setFotoCandidato(String foto){
		if (foto != null){
			ImageIcon fotoCandidato = null;
			//fotoCandidato = new ImageIcon(foto);
			try{
				if(!foto.equalsIgnoreCase(FOTO_CANDIDATO_TRANSPARENTE)){
					if(foto.equalsIgnoreCase(Candidato.FOTO_PADRAO)){
						//fotoCandidato = Imagem.imagemToEscalaCinza(foto);
						fotoCandidato = Imagem.imagemToEscalaCinza(Recursos.getResource(foto), Recursos.getResourceAsStream(foto));
					}
					else{
						fotoCandidato = Imagem.imagemToEscalaCinza(foto);
					}
				}
				else{
					fotoCandidato = new ImageIcon(Recursos.getResource(foto));
				}
			}
			catch(IOException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemErro(this, "Erro ao abrir foto do candidato");
			}
			if(fotoCandidato != null){
				fotoCandidato.setImage(fotoCandidato.getImage().getScaledInstance((int)(Candidato.LARGURA_FOTO * escalaFotoCandidato), (int)(Candidato.ALTURA_FOTO * escalaFotoCandidato), 100));
				this.labelFotoCandidato.setIcon(fotoCandidato);
				this.labelFotoCandidato.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
		}
	}

	/** Mostra o nome do candidato e partido se o candidato é valido e a mensagem de numero errado se o candidato nao existe
	 * @param valido <code>boolean</code> com <code>true</code> se o numero candidato é valido e <code>false</code> senão
	 * @param partidoExiste <code>boolean</code> com <code>true</code> se o partido existe e <code>false</code> senão
	 */
	public void setNumeroCandidatoValido(boolean valido, boolean partidoExiste){
		if(valido){
			this.setNomeCandidatoVisivel(true);
			this.setPartidoVisivel(true);
			//this.labelFotoCandidato.setVisible(true);

			this.painelMensagem.setVisible(true);
			this.labelSeuVotoPara.setVisible(true);
			this.painelNumeroErrado.setVisible(!partidoExiste);
		}
		else{
			this.setNomeCandidatoVisivel(false);
			this.setPartidoVisivel(partidoExiste);

			this.painelMensagem.setVisible(true);
			this.labelSeuVotoPara.setVisible(false);
			this.painelNumeroErrado.setVisible(!partidoExiste);

			this.labelCandidatoInexistente.setVisible(partidoExiste);

		}
	}

	/** Mostra ou escode o <code>LabelRotulo</code> com o texto "Partido:" e com o partido correspondente ao numero do candidato.
	 * @param visivel <code>boolean</code> com <code>true</code> se deve mostrar e <code>false</code> se deve esconder
	 */
	public void setPartidoVisivel(boolean visivel){
		this.labelPartido.setVisible(visivel);
		this.labelPartidoCandidato.setVisible(visivel);
	}

	/** Mostra ou escode o <code>LabelRotulo</code> com o texto "Nome:" e com o nome correspondente ao numero do candidato.
	 * @param visivel <code>boolean</code> com <code>true</code> se deve mostrar e <code>false</code> se deve esconder
	 */
	public void setNomeCandidatoVisivel(boolean visivel){
		this.labelNome.setVisible(visivel);
		this.labelNomeCandidato.setVisible(visivel);
	}

	/** Mostra ou escode o <code>LabelRotulo</code> com o texto "Voto em branco"
	 * @param visivel <code>boolean</code> com <code>true</code> se deve mostrar e <code>false</code> se deve esconder
	 */
	public void setLabelVotoBrancoVisivel(boolean visivel){
		this.labelVotoBranco.setVisible(visivel);
		this.labelNumero.setVisible(!visivel);
		this.campoNumeroCandidato.setVisible(!visivel);
		this.painelMensagem.setVisible(visivel);
		this.labelSeuVotoPara.setVisible(visivel);
	}

	/** Verifica se o partido corresponde aos digitos do candidato existe, se o numero tem apenas um digito, esconde o partido e retorn <code>false</code>
	 * se tem dois digitos e existe mostra o partido, se tem dois digitos e nao existe, mostra a mensagem de numero errado
	 * @return <code>boolean</code> com <code>true</code> se o partido existe e <code>false</code> senão
	 */
	public boolean mudaPartido(){
		boolean partidoExiste = false;
		if(this.campoNumeroCandidato.getText().length() <= 1){
			labelPartidoCandidato.setText("");
			this.setPartidoVisivel(false);
			this.painelMensagem.setVisible(false);
		}
		else if(this.campoNumeroCandidato.getText().length() >= 2){
			PartidoPolitico partido;
			int numeroPartido = Integer.parseInt(this.campoNumeroCandidato.getText().substring(0, 2));
			partido = PartidoPolitico.pesquisa(numeroPartido);
			if(partido != null){
				labelPartidoCandidato.setText(partido.getSigla());
				this.setPartidoVisivel(true);
				this.labelNumero.setVisible(true);
				this.painelMensagem.setVisible(false); //na urna real, essa mensagem aparece, mas a minha nao computa voto de legenda
				partidoExiste = true;
			}
			else{
				this.painelNumeroErrado.setVisible(true);
				this.painelMensagem.setVisible(true);
				this.labelNumero.setVisible(true);
			}
		}
		return partidoExiste;
	}
	
	/** Adiciona um numero ao campo numero
	 * @param numero <code>int</code> com o numero que sera adicionado, se o numero for menor que 0 ou maior que 9, não é adicionado
	 * @return <code>boolean</code> com <code>true</code> se adicionou o numero e <code>false</code> se nao adicionou
	 */
	public boolean addNumeroCampoNumero(int numero){
		String textoCampoNumero = this.campoNumeroCandidato.getText();
		if(textoCampoNumero.length() < numeroDigitosCargo && (numero < 10 && numero >= 0)){
			this.campoNumeroCandidato.setText(textoCampoNumero + numero);
			return true;
		}
		return false;
	}

	/** Limpa os campos, deixando apenas o titulo, cargo e o campo numero visiveis
	 * 
	 */
	public void limpaCampos(){
		this.campoNumeroCandidato.setText("");
		//this.labelFotoCandidato.setVisible(false);
		this.setFotoCandidato(FOTO_CANDIDATO_TRANSPARENTE);
		this.labelFotoCandidato.setBorder(null);
		this.setLabelVotoBrancoVisivel(false);
		this.labelNumero.setVisible(false);
		this.setNumeroCandidatoValido(false, false);
		this.painelNumeroErrado.setVisible(false);
		this.labelCandidatoInexistente.setVisible(false);
		this.labelVotoBranco.setVisible(false);
		this.votoNulo = false;
		this.mudaPartido();
	}

	/** Retorna a votacao atual
	 * @return um <code>Votacao</code> com a votacao em andamento
	 */
	public Votacao getVotacao() {
		return votacao;
	}

	/** Retorna a referencia do botao de confirmar
	 * @return um <code>JButton</code> com o botaoConfirmar
	 */
	public JButton getBotaoConfirmar() {
		return botaoConfirmar;
	}

	/** Retorna a referencia do botao branco
	 * @return um <code>JButton</code> com o botaoBranco
	 */
	public JButton getBotaoBranco() {
		return botaoBranco;
	}

	/** Retorna a referencia do botao corrigir
	 * @return um <code>JButton</code> com o botaoCorrigir
	 */
	public JButton getBotaoCorrigir() {
		return botaoCorrigir;
	}

	/** Retorna a referencia do label de voto em branco
	 * @return um <code>LabelRotulo</code> com o labelVotoBranco
	 */
	public LabelRotulo getLabelVotoBranco() {
		return labelVotoBranco;
	}

	/** Retorna a referencia do label do nome do candidato
	 * @return um <code>LabelRotulo</code> com o labelNomeCandidato
	 */
	public LabelRotulo getLabelNomeCandidato() {
		return labelNomeCandidato;
	}

	/** Retorna a referencia do label de candidato inexistente
	 * @return um <code>LabelRotulo</code> com o labelCandidatoInexistente
	 */
	public LabelRotulo getLabelCandidatoInexistente() {
		return labelCandidatoInexistente;
	}

	/** Retorna a referencia do label do cargo
	 * @return um <code>LabelRotulo</code> com o labelCargo
	 */
	public LabelRotulo getLabelCargo() {
		return labelCargo;
	}

	/** Retorna o numero de digitos do cargo
	 * @return <code>int</code> com o numero de digitos do cargo atual
	 */
	public int getNumeroDigitosCargo() {
		return numeroDigitosCargo;
	}

	//retorna o cargo atual
	/** Retorna o cargo em votacao
	 * @return <code>Cargo</code> com o cargo em que o eleitor esta votando
	 * @see Cargo
	 */
	public Cargo getCargoAtual(){
		if(this.cargoPosicaoAtual < cargos.size())
			return cargos.get(this.cargoPosicaoAtual);
		return null;
	}

	/** Retorna a referencia do label do do campo numero
	 * @return um <code>LabelRotulo</code> com o campoNumero
	 */
	public LabelRotulo getCampoNumero() {
		return campoNumeroCandidato;
	}

	/** Retorna a referencia do label do partido do candidato
	 * @return um <code>LabelRotulo</code> com o labelPartidoCandidato
	 */
	public LabelRotulo getLabelPartidoCandidato() {
		return labelPartidoCandidato;
	}

	/** Implementa esse metodo porque a <code>InterfaceJanela</code> obriga, desta forma o metodo apenas retorna <code>true</code>.
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.Janela#addItensPopupMenu()
	 */
	@Override
	protected void addItensPopupMenu() {
	}

	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.Janela#addEventoItens()
	 */
	@Override
	protected void addEventoItens() {
	}
}