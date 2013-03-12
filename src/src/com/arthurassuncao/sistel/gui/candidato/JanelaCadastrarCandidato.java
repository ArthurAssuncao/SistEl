package com.arthurassuncao.sistel.gui.candidato;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.classes.partido.PartidoPolitico;
import com.arthurassuncao.sistel.eventos.FixedLengthDocument;
import com.arthurassuncao.sistel.eventos.FixedLengthWithNumerosPositivosDocument;
import com.arthurassuncao.sistel.eventos.candidato.TratadorEventoItemCandidato;
import com.arthurassuncao.sistel.eventos.candidato.TratadorEventosMouseCadastrarCandidato;
import com.arthurassuncao.sistel.eventos.candidato.TratadorEventosTecladoCadastrarCandidato;
import com.arthurassuncao.sistel.gui.Fonte;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;
import com.arthurassuncao.sistel.persistencia.BDEleicoes;
import com.arthurassuncao.sistel.recursos.Recursos;

/** A classe <code>JanelaCadastrarCandidato</code> cria uma GUI para cadastrar candidatos 
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 *
 */
public class JanelaCadastrarCandidato extends Janela{
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -6494834015463393908L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  				= 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 				= 300;
	/** <code>String</code> com o texto do botão salvar */
	public static final String TEXTO_BOTAO_SALVAR 		= "Salvar";
	/** <code>String</code> com o texto do botão atualizar */
	public static final String TEXTO_BOTAO_ATUALIZAR  = "Atualizar";
	
	private int numeroColunasCamposTexto 	= 15;
	private String enderecoFotoCandidato 		= Candidato.FOTO_PADRAO; //imagem para candidato sem foto

	private ArrayList<PartidoPoliticoSiglaNumero> partidos = new ArrayList<PartidoPoliticoSiglaNumero>();
	private ArrayList<Cargo> cargos 	= new ArrayList<Cargo>();

	//paineis
	private Painel painelTotal;
	private Painel painelNorte;
	private Painel painelSul;
	private Painel painelDados;
	private Painel painelImagem;
	private Painel painelCentro;
	
	//Labels
	private LabelRotulo labelId;
	private LabelRotulo labelTitulo;
	private LabelRotulo labelNome;
	private LabelRotulo labelCargo;
	private LabelRotulo labelPartido;
	private LabelRotulo labelNumero;
	private LabelRotulo labelFotoCandidato;
	
	//Campos
	private JTextField campoId;
	private JTextField campoNome;
	private JComboBox<String> campoSelecionarPartido;
	private JComboBox<String> campoSelecionarCargo;
	private JFormattedTextField campoNumero;
	
	//text documents
	private FixedLengthDocument documentCampoNome 	 = new FixedLengthDocument(BDEleicoes.TAMANHO_CANDIDATO_NOME);
	private FixedLengthWithNumerosPositivosDocument documentCampoNumero = new FixedLengthWithNumerosPositivosDocument(BDEleicoes.TAMANHO_CANDIDATO_NUMERO);
	
	//mascara
	//private Mascara mascaraNumero = new Mascara("#####", ' ');
	
	//Botoes
	private JButton botaoLimparCampos;
	private JButton botaoSalvar;
	
	//Imagens
	private ImageIcon fotoCandidato;
	
	//private JPanel painelTotal;
	
	//Grid
    private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
	/** Cria uma instancia da janela de cadastro de candidato
	 * 
	 */
	public JanelaCadastrarCandidato(){
		super("Cadastrar Candidato", LARGURA, ALTURA);
		this.iniciaElementos();

		//Modifica alguns campos
		labelTitulo.setEnabled(false);
		labelTitulo.setVisible(false);
		
		botaoSalvar.setText(TEXTO_BOTAO_SALVAR);

		//Adiciona evento botaoSalvar
		botaoSalvar.addMouseListener(new TratadorEventosMouseCadastrarCandidato(this));
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	//Alterar Candidato
	
	/** Cria uma instancia da janela de alteração de candidato
	 * @param candidato <code>Candidato</code> que poderá ser alterado
	 * @param janelaPesquisar <code>JanelaPesquisarCandidato</code> janela de pesquisa de onde o candidato foi selecionado
	 */
	public JanelaCadastrarCandidato(Candidato candidato, JanelaPesquisarCandidato janelaPesquisar){
		super("Alterar Candidato", LARGURA, ALTURA);
		this.iniciaElementos();
		
		//Faz alguns campos aparecerem na GUI Alterar
		labelTitulo.setVisible(true);
		labelTitulo.setEnabled(true);
		
		botaoSalvar.setText(TEXTO_BOTAO_ATUALIZAR);
		
		this.addDadosCandidato(candidato);
		
		//Adiciona evento botaoSalvar(Atualizar)
		int idCandidato = Integer.parseInt(campoId.getText());
		botaoSalvar.addMouseListener(new TratadorEventosMouseCadastrarCandidato(this, janelaPesquisar, idCandidato));
		
		this.addElementos();
		
		this.pack();
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos e adiciona dados aos <code>JComboBox</code>, alem de setar algumas propriedades de alguns componentes  e adicionar eventos aos elementos
	 * 
	 */
	private void iniciaElementos(){
	
		painelTotal 	= new Painel(new GridBagLayout());
		painelNorte 	= new Painel();
		painelSul 		= new Painel(new GridBagLayout());
		painelDados 	= new Painel(new GridBagLayout());
		painelImagem	= new Painel();
		painelCentro 	= new Painel(new GridBagLayout());
		
		//Grid
		//gridBagConstraint.insets = new Insets(5, 15, 10, 10); //espacos pro GridBadLayout 
		gridBagConstraint.insets = new Insets(3, 0, 3, 0); //espacos pro GridBadLayout
		gridBagConstraint.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		//Inicializa os partidos
		//partidos.add(Janela.COMBO_BOX_TEXTO_SELECIONE);
		//pega os partidos que estao no banco de dados
		List<PartidoPolitico> listaPartidos = PartidoPolitico.getAll();
		//adiciona as siglas dos partidos ao comboBox
		if (listaPartidos != null){
			for(PartidoPolitico partido : listaPartidos){
				partidos.add(new PartidoPoliticoSiglaNumero(partido.getSigla(), partido.getNumeroPartido()));
			}
		}
		
		//Inicializa os cargos
		//cargos.add(Janela.COMBO_BOX_TEXTO_SELECIONE);
		//pega os cagos que estao no banco de dados
		List<Cargo> listaCargos = Cargo.getAll();
		//adiciona as siglas dos partidos ao comboBox
		if (listaCargos != null){
			for(Cargo cargo : listaCargos){
				cargos.add(new Cargo(cargo.getNome(), cargo.getNumeroDigitos()));
			}
		}
		
		
		//inicializa os labels
		labelTitulo 		= new LabelRotulo();
		labelTitulo.setFont(Fonte.FONTE_TITULO);
		labelId 			= new LabelRotulo(String.format("%-20s%-15s", "","ID: "));
		labelId.setVisible(false);
		labelId.setEnabled(false);
		labelNome 			= new LabelRotulo(String.format("%-20s%-15s", "","Nome: "));
		//labelNome.setHorizontalAlignment(JLabel.RIGHT);
		labelCargo 			= new LabelRotulo(String.format("%-20s%-15s", "","Cargo: "));
		//labelCargo.setHorizontalAlignment(JLabel.RIGHT);
		labelPartido 		= new LabelRotulo(String.format("%-20s%-15s", "", "Partido: "));
		//labelPartido.setHorizontalAlignment(JLabel.RIGHT);
		labelNumero 		= new LabelRotulo(String.format("%-20s%-15s", "", "Numero: "));
		//labelNumero.setHorizontalAlignment(JLabel.RIGHT);
		labelFotoCandidato 	= new LabelRotulo(fotoCandidato);
		labelFotoCandidato.addMouseListener(new TratadorEventosMouseCadastrarCandidato(this));
		labelFotoCandidato.setToolTipText("Click para mudar a foto");
		
		//Inicializa imagens
		this.setFotoCandidato(Candidato.FOTO_PADRAO);
		
		//inicializa os campos
		campoId 					= new JTextField(5);
		campoId.setEnabled(false);
		campoId.setEditable(false);
		campoId.setVisible(false);
		campoNome 				= new JTextField(numeroColunasCamposTexto);
		campoNome.setToolTipText("Digite o nome do candidato");
		campoNome.setDocument(documentCampoNome);
		//campoSelecionarPartido 	= new JComboBox<String>();
		campoSelecionarPartido 	= new JComboBox<String>();
		campoSelecionarPartido.setMaximumRowCount(7);
		campoSelecionarPartido.addItemListener(new TratadorEventoItemCandidato(this));
		//Adiciona os valores do vetor(ArrayList) partidos ao JComboBox
		campoSelecionarPartido.addItem(JanelaCadastrarCandidato.COMBO_BOX_TEXTO_SELECIONE);
		for(PartidoPoliticoSiglaNumero itemPartido : this.partidos){
			campoSelecionarPartido.addItem(itemPartido.getSigla());
		}
		campoSelecionarCargo 	= new JComboBox<String>();
		campoSelecionarCargo.setMaximumRowCount(7);
		campoSelecionarCargo.addItemListener(new TratadorEventoItemCandidato(this));
		//Adiciona os valores do vetor(ArrayList) partidos ao JComboBox
		campoSelecionarCargo.addItem(Janela.COMBO_BOX_TEXTO_SELECIONE);
		for(Cargo itemCargo : this.cargos){
			campoSelecionarCargo.addItem(itemCargo.getNome());
		}
		//campoNumero = new JFormattedTextField(mascaraNumero);
		campoNumero = new JFormattedTextField();
		campoNumero.setToolTipText("Digite o numero do candidato");
		campoNumero.setDocument(documentCampoNumero);
		campoNumero.addKeyListener(new TratadorEventosTecladoCadastrarCandidato(this));
		
		//Inicializa os Botoes
		botaoLimparCampos = new JButton("Limpar");
		botaoSalvar = new JButton();
		
		botaoLimparCampos.setFocusable(false);
		botaoSalvar.setFocusable(false);
		
		//Adiciona eventos aos botoes
		botaoLimparCampos.addMouseListener(new TratadorEventosMouseCadastrarCandidato(this));
		//botaoSalvar.addMouseListener(new TratadorEventosMouseCadastrarCandidato(this));
	}
	
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		
		//Adiciona ao painel Norte
		painelNorte.add(labelTitulo);
		
		//Aciciona ao painel Imagem
		painelImagem.add(labelFotoCandidato);
		
		//Adiciona ao painelDados
		int linha = 0;
		//Nome
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelNome, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelDados.add(campoNome, gridBagConstraint);
		
		//Cargo
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelCargo, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelDados.add(campoSelecionarCargo, gridBagConstraint);
		
		//Partido
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelPartido, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelDados.add(campoSelecionarPartido, gridBagConstraint);
		
		//Numero
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelNumero, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelDados.add(campoNumero, gridBagConstraint);
		
		//Adiciona ao painelCentro
		gridBagConstraint.gridy = 0;
		gridBagConstraint.gridx = 0;
		painelCentro.add(painelImagem, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelCentro.add(painelDados, gridBagConstraint);
				
		//Adiciona ao painel Sul
		gridBagConstraint.gridy = 0;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.insets = new Insets(0, 20, 5, 20); //espacos pro GridBadLayout 
		painelSul.add(botaoLimparCampos, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelSul.add(botaoSalvar, gridBagConstraint);
		
		//Adiciona ao painelTotal
		linha = 0;
		gridBagConstraint.insets = new Insets(0, 5, 0, 5); //espacos pro GridBadLayout 
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelTotal.add(painelNorte, gridBagConstraint);
		gridBagConstraint.gridy = linha++;
		painelTotal.add(painelCentro, gridBagConstraint);
		gridBagConstraint.gridy = linha++;
		painelTotal.add(painelSul, gridBagConstraint);
		
		this.add(painelTotal);
	}
	
	
	/** Coloca os dados do candidato nos campos da janela
	 * @param candidato <code>Candidato</code> que poderá ser modificado
	 */
	private void addDadosCandidato(Candidato candidato){
		this.campoId.setText(String.valueOf(candidato.getId()));
		this.campoNome.setText(candidato.getNome());
		this.campoSelecionarCargo.setSelectedItem(candidato.getCargo());
		this.campoSelecionarPartido.setSelectedItem(candidato.getPartidoPolitico().getSigla());
		this.campoNumero.setText(String.valueOf(candidato.getNumeroCandidato()));
		this.setFotoCandidato(candidato.getEnderecoFoto());
		this.labelTitulo.setText(candidato.getNome());
	}
	
	/** Retorna um <code>String</code> com o endereço da foto do candidato
	 * @return <code>String</code> com o endereço da foto do candidato
	 */
	public String getEnderecoFotoCandidato() {
		return enderecoFotoCandidato;
	}

	/** Seta o endereco da foto do candidato 
	 * @param enderecoFotoCandidato <code>String</code> com o endereco da nova foto do candidato
	 */
	public void setEnderecoFotoCandidato(String enderecoFotoCandidato) {
		this.enderecoFotoCandidato = enderecoFotoCandidato;
	}

	/** Retorna um <code>String</code> com o endereco da foto padrão dos candidatos
	 * @return <code>String</code> com o endereco da foto padrão dos candidatos
	 */
	public static String getFotoPadrao() {
		return Candidato.FOTO_PADRAO;
	}
	
	/** Retorna um <code>int</code> com o numero do partido referente a sigla passada como parametro
	 * @param sigla <code>String</code> com a sigla do partido
	 * @return <code>int</code> com o numero do partido referente a sigla passada como parametro
	 */
	public int getNumeroPartidoBySigla(String sigla){
		for (Iterator<PartidoPoliticoSiglaNumero> iterador = partidos.iterator(); iterador.hasNext(); ) {
		    PartidoPoliticoSiglaNumero partido = iterador.next(); //PartidoPoliticoSiglaNumero
		    if (partido.getSigla().equals(sigla)){
		    	return partido.getNumero();
		    }
		}
		return 0;
	}
	
	
	/** Retorna um <code>String</code> com a sigla do partido referente ao numero passado como parametro
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return <code>String</code> com a sigla do partido referente ao numero passado como parametro
	 */
	public String getSiglaPartidoByNumero(int numeroPartido){
		for (Iterator<PartidoPoliticoSiglaNumero> iterador = partidos.iterator(); iterador.hasNext(); ) {
		    PartidoPoliticoSiglaNumero partido = iterador.next(); //PartidoPoliticoSiglaNumero
		    if (partido.getNumero() == numeroPartido){
		    	return partido.getSigla();
		    }
		}
		return Janela.COMBO_BOX_TEXTO_SELECIONE;
	}
	
	/** Retorna um <code>int</code> com o numero de digitos que o numero do candidato deste cargo deve ter
	 * @param nomeCargo <code>String</code> com o cargo
	 * @return <code>int</code> com o numero de digitos que o numero do candidato deste cargo deve ter
	 */
	public int getNumeroDigitosByCargo(String nomeCargo){
		for (Iterator<Cargo> iterador = cargos.iterator(); iterador.hasNext(); ){
		    Cargo cargo = iterador.next(); //Cargo
		    if (cargo.getNome().equals(nomeCargo)){
		    	return cargo.getNumeroDigitos();
		    }
		}
		return BDEleicoes.TAMANHO_CANDIDATO_NUMERO; //se nao achar retorna o maximo possivel
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
	
	/** Retorna um <code>JTextField</code> com o campoId
	 * @return um <code>JTextField</code> com o campoId
	 */
	public JTextField getCampoId() {
		return campoId;
	}

	/** Retorna um <code>JTextField</code> com o campoNome
	 * @return um <code>JTextField</code> com o campoNome
	 */
	public JTextField getCampoNome() {
		return campoNome;
	}

	/** Retorna um {@code JComboBox<String>} com o campoSelecionarPartido
	 * @return um {@code JComboBox<String>} com o campoSelecionarPartido
	 */
	public JComboBox<String> getCampoSelecionarPartido() {
		return campoSelecionarPartido;
	}

	/** Retorna um {@code JComboBox<String>} com o campoSelecionarCargo
	 * @return um {@code JComboBox<String>} com o campoSelecionarCargo
	 */
	public JComboBox<String> getCampoSelecionarCargo() {
		return campoSelecionarCargo;
	}

	/** Retorna um <code>JTextField</code> com o campoNumero
	 * @return um <code>JTextField</code> com o campoNumero
	 */
	public JTextField getCampoNumero() {
		return campoNumero;
	}
	
	/** Retorna um <code>JTextField</code> com o labelFotoCandidato
	 * @return um <code>JTextField</code> com o labelFotoCandidato
	 */
	public LabelRotulo getLabelFotoCandidato() {
		return labelFotoCandidato;
	}
	
	/** Sera o maximo de caracteres do documento do campo numero
	 * @param maxlength <code>int</code> com o maximo de caracteres para o campo Numero
	 */
	public void setMaxlengthCampoNumero(int maxlength){
		this.documentCampoNumero.setMaxLength(maxlength);
	}
	
	/** Remove o excesso de caracteres do campo numero. Caso haja mais de <code>maxlength</code> caracteres no campo numero, 
	 * esta função remove os caracteres a mais.
	 * 
	 */
	public void removeExcessoCaracteresCampoNumero(){
		documentCampoNumero.removeOverage();
	}

	/** Limpa os campos da janela. Os campos de texto ficam vazios, os <code>JComboBox</code> fica com a primeira opção selecionada e a imagem do candidato recebe a imagem padrão dos candidatos.
	 * 
	 */
	public void limpaCamposJanela(){
		this.campoNome.setText("");
		this.campoNumero.setText("");
		this.campoSelecionarCargo.setSelectedIndex(0);
		this.campoSelecionarPartido.setSelectedIndex(0);
		this.setFotoCandidato(Candidato.FOTO_PADRAO);
	}
	
	/** Seta a foto do candidato
	 * @param foto <String> com o endereço da foto do candidato
	 */
	public void setFotoCandidato(String foto){
		if (foto != null){
			ImageIcon fotoCandidato = null;
			if(foto.equals(Candidato.FOTO_PADRAO)){ //é a imagem padrao, entao pega do jar
				fotoCandidato = new ImageIcon(Recursos.getResource(foto));
			}
			else{
				fotoCandidato = new ImageIcon(foto);
			}
			fotoCandidato.setImage(fotoCandidato.getImage().getScaledInstance(Candidato.LARGURA_FOTO, Candidato.ALTURA_FOTO, 100));
			this.labelFotoCandidato.setIcon(fotoCandidato);
			this.setEnderecoFotoCandidato(foto);
		}
	}
	
	/** Seta a foto do candidato
	 * @param foto <code>URL</code> com a <code>URL</code> da foto do candidato
	 */
	public void setFotoCandidato(URL foto){
		if (foto != null){
			fotoCandidato = new ImageIcon(foto);
			fotoCandidato.setImage(fotoCandidato.getImage().getScaledInstance(Candidato.LARGURA_FOTO, Candidato.ALTURA_FOTO, 100));
			this.labelFotoCandidato.setIcon(fotoCandidato);
			this.setEnderecoFotoCandidato(foto.getPath());
			//System.out.println(foto.getFile());
		}
	}

	/** Verifica se os campos são validos.<br>
	 * Os campos nome, numero não poder ficar vazios. O cargo e o partido devem ser selecionados.
	 * O numero de digitos do numero do campo numero deve ser igual ao numero de digitos do cargo selecionado.
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 * @see Cargo
	 */
	@Override
	public boolean verificaCampos() {
		String nome = campoNome.getText().trim();
		String numero = campoNumero.getText().trim();
		int numeroDigitosCargo = this.documentCampoNumero.getMaxLength();
		String cargo = (String)campoSelecionarCargo.getSelectedItem();
		String partido = (String )campoSelecionarPartido.getSelectedItem();
		
		if(nome.isEmpty()){
			this.addError("Campo nome está vazio");
		}
		if(numero.length() < numeroDigitosCargo && !cargo.equals(Janela.COMBO_BOX_TEXTO_SELECIONE)){
			this.addError("Número do candidato à " + cargo + 
					" deve ter " + numeroDigitosCargo + " digitos");
		}
		if(cargo.equals(Janela.COMBO_BOX_TEXTO_SELECIONE)){
			this.addError("Selecione um cargo");
		}
		if(partido.equals(Janela.COMBO_BOX_TEXTO_SELECIONE)){
			this.addError("Selecione um partido");
		}
		if(!this.getErros().isEmpty()){
			return false;
		}
		return true;
	}
	
	//criado apenas para armazenar as siglas e numeros dos partidos, evita o uso da classe PartidoPolitico
	/** Classe interna para manipular partidos armazenando apenas sigla e numero
	 * @author Arthur Assunção
	 * 
	 *
	 */
	private class PartidoPoliticoSiglaNumero{
		private String sigla;
		private int numero;
		
		/** Cria uma instancia da classe interna
		 * @param sigla <code>String</code> com a sigla do partido
		 * @param numero <code>int</code> com o numero do partido
		 */
		public PartidoPoliticoSiglaNumero(String sigla, int numero){
			this.sigla 	 = sigla;
			this.numero  = numero;
		}
		/** Retorna um <code>String</code> com a sigla do partido do candidato
		 * @return um <code>String</code> com a sigla do partido do candidato
		 */
		public String getSigla() {
			return sigla;
		}
		/** Retorna um <code>int</code> com o numero do candidato
		 * @return um <code>int</code> com o numero do candidato
		 */
		public int getNumero() {
			return numero;
		}
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
