package com.arthurassuncao.sistel.gui.partido;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.arthurassuncao.sistel.eventos.FixedLengthDocument;
import com.arthurassuncao.sistel.eventos.FixedLengthWithNumerosPositivosDocument;
import com.arthurassuncao.sistel.eventos.partido.TratadorEventoMouseCadastrarPartido;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;
import com.arthurassuncao.sistel.persistencia.BDEleicoes;

/** Classe com a interface grafica para cadastro de partidos
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 */
public class JanelaCadastrarPartido extends Janela {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -813075064973795070L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA 	 = 200;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 		 = 200;
	private int numeroColunasCamposTexto = 10;
	
	//Paineis
	private Painel painelTotal;
	private Painel painelNorte;
	private Painel painelSul;
	private Painel painelDados;
	
	//Labels
	private LabelRotulo labelNome;
	private LabelRotulo labelSigla;
	private LabelRotulo labelNumero;
	
	//Campos
	private JTextField campoNome;
	private JTextField campoSigla;
	private JTextField campoNumero;
	
	//text documents
	private FixedLengthDocument documentCampoNome 		= new FixedLengthDocument(BDEleicoes.TAMANHO_PARTIDO_NOME);
	private FixedLengthDocument documentCampoSigla 		= new FixedLengthDocument(BDEleicoes.TAMANHO_PARTIDO_SIGLA);
	private FixedLengthWithNumerosPositivosDocument documentCampoNumero   = new FixedLengthWithNumerosPositivosDocument(BDEleicoes.TAMANHO_PARTIDO_NUMERO);
	
	//Botoes
	private JButton botaoLimparCampos;
	private JButton botaoSalvar;
	
	//Grid
    private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
    /** Cria uma instancia da janela de cadastro de partidos
   	 * 
   	 */
	public JanelaCadastrarPartido(){
		super("Cadastrar Partido", LARGURA, ALTURA);
		
		this.iniciaElementos();
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos, alem de setar algumas propriedades e eventos de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		
		//inicia Paineis
		painelTotal = new Painel(new GridBagLayout());
		painelNorte = new Painel();
		painelSul   = new Painel();
		painelDados = new Painel(new GridBagLayout());
		
		//Grid
		gridBagConstraint.insets = new Insets(5, 10, 10, 10); //espacos pro GridBadLayout 
		gridBagConstraint.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		//inicia labels 
		labelNome 	= new LabelRotulo("Partido: ");
		labelSigla  = new LabelRotulo("Sigla: ");
		labelNumero  = new LabelRotulo("Numero: ");
		
		//inicia campos
		campoNome = new JTextField(numeroColunasCamposTexto);
		campoNome.setDocument(documentCampoNome);
		campoNome.setToolTipText("Digite o partido");
		campoSigla  = new JTextField(numeroColunasCamposTexto);
		campoSigla.setDocument(documentCampoSigla);
		campoSigla.setToolTipText("Digite a sigla");
		campoNumero = new JTextField();
		campoNumero.setToolTipText("Digite o numero do partido");
		campoNumero.setDocument(documentCampoNumero);
		
		//Inicializa os Botoes
		botaoLimparCampos = new JButton("Limpar");
		botaoLimparCampos.addMouseListener(new TratadorEventoMouseCadastrarPartido(this));
		botaoSalvar 	  = new JButton("Salvar");
		botaoSalvar.addMouseListener(new TratadorEventoMouseCadastrarPartido(this));
		
		botaoLimparCampos.setFocusable(false);
		botaoSalvar.setFocusable(false);
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		//Adiciona ao painelDados
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		painelDados.add(labelNome, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNome, gridBagConstraint);
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 1;
		painelDados.add(labelSigla, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoSigla, gridBagConstraint);
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 2;
		painelDados.add(labelNumero, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNumero, gridBagConstraint);
		
		//adiciona ao painelSul
		painelSul.add(botaoLimparCampos);
		painelSul.add(new Painel());
		painelSul.add(botaoSalvar);
		
		//Adiciona ao painelTotal
		gridBagConstraint.insets = new Insets(0, 0, 0, 0); //espacos pro GridBadLayout 
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		painelTotal.add(painelNorte, gridBagConstraint);
		gridBagConstraint.gridy = 1;
		painelTotal.add(painelDados, gridBagConstraint);
		gridBagConstraint.gridy = 2;
		painelTotal.add(painelSul, gridBagConstraint);
		
		this.add(painelTotal);
	}
	/** Limpa os campos da janela. Os campos de texto ficam vazios.
	 * 
	 */
	public void limpaCamposJanela(){
		this.campoNome.setText("");
		this.campoSigla.setText("");
		this.campoNumero.setText("");
	}
	
	/** Retorna um <code>String</code> com o valor do campo Sigla
	 * @return um <code>String</code> com o valor do campo Sigla
	 */
	public String getValorCampoSigla() {
		return campoSigla.getText();
	}

	/** Retorna um <code>String</code> com o valor do campo Nome
	 * @return um <code>String</code> com o valor do campo Nome
	 */
	public String getValorCampoNome() {
		return campoNome.getText();
	}

	/** Retorna um <code>String</code> com o valor do campo Numero
	 * @return um <code>String</code> com o valor do campo Numero
	 */
	public String getValorCampoNumero() {
		return campoNumero.getText();
	}

	/** Retorna um <code>JButton</code> com o botaoLimparCampos
	 * @return um <code>JButton</code> com o botaoLimparCampos
	 */
	public JButton getBotaoLimparCampos() {
		return botaoLimparCampos;
	}

	/** Retorna um <code>JButton</code> com o botaoSalvar
	 * @return um <code>JButton</code> com o botaoSalvar
	 */
	public JButton getBotaoSalvar() {
		return botaoSalvar;
	}
	
	/** Verifica se os campos são validos. <br>
	 * Os campos nome, sigla e numero não pode ficar vazios e o numero de digitos no campo numero deve ser 2.
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		String nome = campoNome.getText().trim();
		String sigla = campoSigla.getText().trim();
		String numero = campoNumero.getText().trim();
		if(nome.isEmpty()){
			this.addError("Campo nome está vazio");
		}
		if(sigla.isEmpty()){
			this.addError("Campo sigla está vazio");
		}
		if(numero.length() != 2){
			this.addError("Campo número deve ter 2 digitos");
		}
		if(!this.getErros().isEmpty()){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.Janela#addItensPopupMenu()
	 */
	@Override
	protected void addItensPopupMenu() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.arthurassuncao.sistel.gui.Janela#addEventoItens()
	 */
	@Override
	protected void addEventoItens() {
		// TODO Auto-generated method stub
		
	}
	
}
