package com.arthurassuncao.sistel.gui.pesquisa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa.IdCandidatoVotos;
import com.arthurassuncao.sistel.eventos.NumerosPositivosDocument;
import com.arthurassuncao.sistel.eventos.pesquisa.TratadorEventoItemCadastrarPesquisa;
import com.arthurassuncao.sistel.eventos.pesquisa.TratadorEventoMouseCadastrarPesquisa;
import com.arthurassuncao.sistel.eventos.pesquisa.TratadorEventoMouseCadastrarPesquisa.TratadorEventosMouseOutCadastrarPesquisa;
import com.arthurassuncao.sistel.gui.Calendario;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;

/** A classe <code>JanelaCadastrarPesquisa</code> cria uma GUI para cadastrar pesquisas eleitorais 
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 *
 */
public class JanelaCadastrarPesquisa extends Janela {

	/**@serial
	 * 
	 */
	private static final long serialVersionUID = -3529917864365622239L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  				= 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 				= 300;
	/** <code>int</code> com o numero de colunas da tabela de candidatos */
	public final int NUMERO_COLUNAS_TABELA = 3;
	
	//Tabela
	private final String COLUNA_NUMERO_CANDIDATO = "Numero";
	private final String COLUNA_NOME = "Nome";
	private final String COLUNA_INTENCOES_VOTO = "Intenções de voto";
	private final String[] COLUNAS_CANDIDATO = {COLUNA_NUMERO_CANDIDATO, COLUNA_NOME, COLUNA_INTENCOES_VOTO};
	private String[][] linhasTabela = new String[0][NUMERO_COLUNAS_TABELA];
	
	private int numeroColunasCamposTexto 	= 10;
	private ArrayList<Cargo> cargos 	= new ArrayList<Cargo>();
	private List<Candidato> candidatos = new ArrayList<Candidato>();
	
	//paineis
	private Painel painelTotal;
	private Painel painelDados;
	private Painel painelCandidatos;
	
	//Labels
	//private LabelRotulo labelTitulo;
	private LabelRotulo labelCargo; //Cargo
	private LabelRotulo labelDataInicio; //Data de inicio da pesquisa
	private LabelRotulo labelDataFim; //data de fim da pesquisa
	//private LabelRotulo labelNumeroIntencoesVoto; //numero de intencoes de voto para cada candidato
	private LabelRotulo labelNumeroVotosBrancosNulos; //numero de votos brancos e nulos
	private LabelRotulo labelNumeroVotosIndecisos; //numero de votos de indecisos, nao quiseram opinar ou nao souberam
	private LabelRotulo labelNumeroPessoasEntrevistadas; //numero de pessoas entrevistadas
	private LabelRotulo labelNumeroMunicipiosPesquisados; //numero de municipios pesquisados
	
	//Campos
	private JComboBox<String> campoSelecionarCargo;
	private Calendario campoDataInicio;
	private Calendario campoDataFim;
	//private JTextField campoNumeroIntencoesVoto;
	private JTextField campoNumeroVotosBrancosNulos;
	private JTextField campoNumeroVotosIndecisos;
	private JTextField campoNumeroPessoasEntrevistadas;
	private JTextField campoNumeroMunicipiosPesquisados;
	
	//tabela
	private JTable tabelaCandidatos;
	
	//Barra de rolagem
	private JScrollPane barraRolagem;
	
	//Botoes
	JButton botaoLimparCampos;
	JButton botaoSalvar;
	
	//Grid
	private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
	/** Cria uma instancia da janela de cadastro de pesquisa
	 * 
	 */
	public JanelaCadastrarPesquisa(){
		super("Cadastrar Pesquisa", LARGURA, ALTURA);
		
		this.iniciaElementos();
		
		//Deixa os campos com o valor padrao
		this.limpaCamposJanela();
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos e adiciona dados aos <code>JComboBox</code>, alem de setar algumas propriedades de alguns componentes e adicionar eventos aos elementos
	 * 
	 */
	private void iniciaElementos(){
		//inicia os labels
		//labelTitulo = new LabelRotulo("Cadastrar Pesquisa");
		labelCargo = new LabelRotulo("Cargo: ");
		labelDataInicio = new LabelRotulo("Data de Início");
		labelDataFim = new LabelRotulo("Data de fim");
		//labelNumeroIntencoesVoto = new LabelRotulo("Intenções de voto: ");
		labelNumeroVotosBrancosNulos = new LabelRotulo("Nulos/brancos: ");
		labelNumeroVotosIndecisos = new LabelRotulo("Indecios: ");
		labelNumeroPessoasEntrevistadas = new LabelRotulo("Pessoas entrevistadas: ");
		labelNumeroMunicipiosPesquisados = new LabelRotulo("Municipios pesquisados: ");
		
		//inicia os campos
		campoSelecionarCargo 	= new JComboBox<String>();
		campoDataInicio 			= new Calendario(Calendario.CALENDARIO_WEBDATEFIELD);
		campoDataFim 				= new Calendario(Calendario.CALENDARIO_WEBDATEFIELD);
		//campoNumeroIntencoesVoto 				= new JTextField(numeroColunasCamposTexto);
		campoNumeroVotosBrancosNulos 		= new JTextField(numeroColunasCamposTexto);
		campoNumeroVotosIndecisos 				= new JTextField(numeroColunasCamposTexto);
		campoNumeroPessoasEntrevistadas 	= new JTextField(numeroColunasCamposTexto);
		campoNumeroMunicipiosPesquisados 	= new JTextField(numeroColunasCamposTexto);
		
		//pega os cagos que estao no banco de dados
		List<Cargo> listaCargos = Cargo.getAll();
		//adiciona as siglas dos partidos ao comboBox
		if (listaCargos != null){
			for(Cargo cargo : listaCargos){
				cargos.add(new Cargo(cargo.getNome(), cargo.getNumeroDigitos()));
			}
		}
		//Adiciona os valores do vetor(ArrayList) partidos ao JComboBox
		campoSelecionarCargo.addItem(Janela.COMBO_BOX_TEXTO_SELECIONE);
		for(Cargo itemCargo : this.cargos){
			campoSelecionarCargo.addItem(itemCargo.getNome());
		}
		
		//Inicializa os Botoes
		botaoLimparCampos = new JButton("Limpar");
		botaoSalvar = new JButton("Salvar");
		
		botaoLimparCampos.setFocusable(false);
		botaoSalvar.setFocusable(false);
		
		//inicia a tabela
		tabelaCandidatos = new JTable(new DefaultTableModel(this.linhasTabela, this.COLUNAS_CANDIDATO)){
			private static final long serialVersionUID = 5727320816550514929L;
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if (colIndex == getColumn(COLUNA_NUMERO_CANDIDATO).getModelIndex() || colIndex == getColumn(COLUNA_NOME).getModelIndex()){
				  return false; //evita a edicao das celulas
				}
				else
					return true;
			}
		};
		int larguraScroll = 310;
		int larguraNumeroCandidato = 50;
		int larguraColunaIntencoesVoto = 110;
		tabelaCandidatos.getColumn(COLUNA_NUMERO_CANDIDATO).setPreferredWidth(larguraNumeroCandidato);
		tabelaCandidatos.getColumn(COLUNA_INTENCOES_VOTO).setPreferredWidth(larguraColunaIntencoesVoto);
		tabelaCandidatos.getColumn(COLUNA_NOME).setPreferredWidth(larguraScroll - larguraColunaIntencoesVoto - larguraNumeroCandidato);
		tabelaCandidatos.setPreferredScrollableViewportSize(new Dimension(larguraScroll, 100));
		tabelaCandidatos.setBackground(Color.WHITE);
		tabelaCandidatos.setRowSelectionAllowed(true); //selecao de linha
		//tabelaResultado.setCellSelectionEnabled(false); //selecao de celula
		tabelaCandidatos.setColumnSelectionAllowed(false); //selecao de coluna
		
		int indexColunaIntencoesVotos = tabelaCandidatos.getColumnModel().getColumnIndex(COLUNA_INTENCOES_VOTO);
		TableColumn colunaIntencoesVotos = tabelaCandidatos.getColumnModel().getColumn(indexColunaIntencoesVotos);
		tabelaCandidatos.setRowHeight(20);
		colunaIntencoesVotos.setCellEditor(new EditorTabelaColunaNumerica());
		
		
		//inicia barra de rolagem
		barraRolagem = new JScrollPane(tabelaCandidatos);
		
		//inicia o Grid
		//Grid
		gridBagConstraint.insets = new Insets(5, 1, 5, 1); //espacos pro GridBadLayout
		gridBagConstraint.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		//inicia os Paineis
		painelTotal 			= new Painel(new GridBagLayout());
		painelDados 			= new Painel(new GridBagLayout());
		painelCandidatos 	= new Painel(new GridBagLayout());
		
		//Adiciona tooltip aos campos
		campoDataInicio.setToolTipText("Digite a data de inicio da pesquisa");
		campoDataFim.setToolTipText("Digite a data de fim da pesquisa");
		//campoNumeroIntencoesVoto.setToolTipText("Digite o numero de votos");
		campoNumeroVotosBrancosNulos.setToolTipText("Digite o numero de votos nulos e/ou brancos");
		campoNumeroVotosIndecisos.setToolTipText("Digite o numero de indecisos, não souberam ou não quiseram opinar");
		campoNumeroPessoasEntrevistadas.setToolTipText("Digite o numero de pessoas entrevistadas");
		campoNumeroMunicipiosPesquisados.setToolTipText("Digite o numero de municipios pesquisados");
		
		//Adiciona os tratadores de eventos aos campos
		campoSelecionarCargo.addItemListener(new TratadorEventoItemCadastrarPesquisa(this));
		campoSelecionarCargo.setMaximumRowCount(7);
		botaoLimparCampos.addMouseListener(new TratadorEventoMouseCadastrarPesquisa(this));
		botaoSalvar.addMouseListener(new TratadorEventoMouseCadastrarPesquisa(this));
		
		campoNumeroVotosBrancosNulos.addMouseListener(new TratadorEventoMouseCadastrarPesquisa(this));
		campoNumeroVotosIndecisos.addMouseListener(new TratadorEventoMouseCadastrarPesquisa(this));
		campoNumeroPessoasEntrevistadas.addMouseListener(new TratadorEventoMouseCadastrarPesquisa(this));
		campoNumeroMunicipiosPesquisados.addMouseListener(new TratadorEventoMouseCadastrarPesquisa(this));
		
		campoNumeroVotosBrancosNulos.addFocusListener(new TratadorEventosMouseOutCadastrarPesquisa());
		campoNumeroVotosIndecisos.addFocusListener(new TratadorEventosMouseOutCadastrarPesquisa());
		campoNumeroPessoasEntrevistadas.addFocusListener(new TratadorEventosMouseOutCadastrarPesquisa());
		campoNumeroMunicipiosPesquisados.addFocusListener(new TratadorEventosMouseOutCadastrarPesquisa());
		
		//Adiciona os documentos de entrada aos campos		
		campoNumeroVotosBrancosNulos.setDocument(new NumerosPositivosDocument());
		campoNumeroVotosIndecisos.setDocument(new NumerosPositivosDocument());
		campoNumeroPessoasEntrevistadas.setDocument(new NumerosPositivosDocument());
		campoNumeroMunicipiosPesquisados.setDocument(new NumerosPositivosDocument());
		
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		//Adiciona ao painelTotal
		int linha = 0;
		//Cargo
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelCargo, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoSelecionarCargo, gridBagConstraint);
		
		//Data inicio/fim
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelDataInicio, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoDataInicio, gridBagConstraint);
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelDataFim, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoDataFim, gridBagConstraint);
		
		//Numero intencoes de voto
		GridBagConstraints gridBagConstraintCandidatos = new GridBagConstraints();
		gridBagConstraintCandidatos.insets = new Insets(5, 1, 5, 1); //espacos pro GridBadLayout
		gridBagConstraintCandidatos.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		
		int gridwidthTemp = gridBagConstraint.gridwidth;
		double weightxTemp = gridBagConstraint.weightx;
		
		gridBagConstraint.gridwidth = GridBagConstraints.REMAINDER; //faz o elemento ocupar quantas colunas necessitar
		gridBagConstraint.weightx = 1;
		
		painelDados.add(painelCandidatos, gridBagConstraint);
		
		gridBagConstraint.gridwidth = gridwidthTemp;
		gridBagConstraint.weightx = weightxTemp;;
		
		gridBagConstraintCandidatos.gridx = 0;
		gridBagConstraintCandidatos.gridy = 0;
		/*painelCandidatos.add(labelNumeroIntencoesVoto, gridBagConstraintCandidatos);
		gridBagConstraintCandidatos.gridx = 1;
		painelCandidatos.add(campoNumeroIntencoesVoto, gridBagConstraintCandidatos);
		gridBagConstraintCandidatos.gridx = 0;
		gridBagConstraintCandidatos.gridy = 1;*/
		gridBagConstraintCandidatos.gridwidth = GridBagConstraints.REMAINDER; //faz o elemento ocupar quantas colunas necessitar
		gridBagConstraintCandidatos.weightx = 1;
		gridBagConstraintCandidatos.gridheight = GridBagConstraints.REMAINDER; //permite q o elemento ocupe quantas linhas abaixo necessitar
		painelCandidatos.add(barraRolagem, gridBagConstraintCandidatos);
		
		//Numero votos nulos
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelNumeroVotosBrancosNulos, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNumeroVotosBrancosNulos, gridBagConstraint);
		
		//Numero indecisos/nao souberam/nao quiseram opinar
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelNumeroVotosIndecisos, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNumeroVotosIndecisos, gridBagConstraint);
		
		//Numero pessoas entrevistadas
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelNumeroPessoasEntrevistadas, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNumeroPessoasEntrevistadas, gridBagConstraint);
		
		//Numero de municipios
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelNumeroMunicipiosPesquisados, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNumeroMunicipiosPesquisados, gridBagConstraint);
		
		//Adiciona os botoes
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		gridBagConstraint.fill = GridBagConstraints.NONE;
		painelDados.add(botaoLimparCampos, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(botaoSalvar, gridBagConstraint);
		
		//Adiciona o painelDados ao painelTotal
		gridBagConstraint.insets = new Insets(10, 20, 10, 20); //espacos pro GridBadLayout
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		painelTotal.add(painelDados, gridBagConstraint);
		
		this.add(painelTotal);
		
	}
	
	/** Adiciona linhas à tabela, são inseridos os dados dos candidatos que foram passados por parametro no <code>List</code>
	 * @param candidatos {@code List<Candidato>} com os dados dos candidatos que serão inseridos na tabela
	 */
	public void addLinhasTabela(List<Candidato> candidatos){
		this.candidatos = candidatos;
		DefaultTableModel model = limpaTabela();
		if (candidatos.size() > 0){
			Object[] linha = new Object[NUMERO_COLUNAS_TABELA];
			for(int i = 0; i < candidatos.size(); i++){
				linha[0] = candidatos.get(i).getNumeroCandidato();
				linha[1] = candidatos.get(i).getNome();
				linha[2] = "0"; //inicia com 0 votos
				model.addRow(linha);
			}
		}
		else{
			//Nada a fazer
		}
	}
	
	/** Limpa a tabela da pesquisa apando os dados contidos nela
	 * @return <code>DefaultTableModel</code> com o modelo da tabela
	 */
	public DefaultTableModel limpaTabela(){
		DefaultTableModel model = ((DefaultTableModel)(this.tabelaCandidatos.getModel()));
		model.setNumRows(0);
		return model;
	}
	
	/** Retorna um {@code JComboBox<String>} com o campoSelecionarCargo
	 * @return um {@code JComboBox<String>} com o campoSelecionarCargo
	 */
	public JComboBox<String> getCampoSelecionarCargo() {
		return campoSelecionarCargo;
	}
	
	/** Retorna um <code>Data</code> com a data de inicio selecionada
	 * @return um <code>Data</code> com a data de inicio selecionada
	 */
	public Data getDataCampoDataInicio(){
		return new Data(this.campoDataInicio.getDate());
	}
	
	/** Retorna um <code>Data</code> com a data de fim selecionada
	 * @return um <code>Data</code> com a data de fim selecionada
	 */
	public Data getDataCampoDataFim(){
		return new Data(this.campoDataFim.getDate());
	}
	
	/** Retorna um <code>int</code> com o numero de votos nulos e brancos
	 * @return um <code>int</code> com o numero de votos nulos e brancos
	 */
	public int getVotosNulosBrancos(){
		int votosNulos = 0;
		try{
			votosNulos = Integer.parseInt(this.campoNumeroVotosBrancosNulos.getText());
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		return votosNulos;
	}
	
	/** Retorna um <code>int</code> com o numero de indecisos, nao souberam ou quiseram opinar
	 * @return um <code>int</code> com o numero de indecisos, nao souberam ou quiseram opinar
	 */
	public int getVotosIndecisos(){
		int votosIndecisos = 0;
		try{
			votosIndecisos = Integer.parseInt(this.campoNumeroVotosIndecisos.getText());
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		return votosIndecisos;
	}
	
	/** Retorna um <code>int</code> com o numero de pessoas entrevistadas
	 * @return um <code>int</code> com o numero de pessoas entrevistadas
	 */
	public int getNumeroPessoasEntrevistadas(){
		int numeroPessoasEntrevistadas = 0;
		try{
			numeroPessoasEntrevistadas = Integer.parseInt(this.campoNumeroPessoasEntrevistadas.getText());
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		return numeroPessoasEntrevistadas;
	}
	
	/** Retorna um <code>int</code> com o numero de municipios pesquisados
	 * @return um <code>int</code> com o numero de municipios pesquisados
	 */
	public int getNumeroMunicipiosEntrevistados(){
		int numeroMunicipiosEntrevistados = 0;
		try{
			numeroMunicipiosEntrevistados = Integer.parseInt(this.campoNumeroMunicipiosPesquisados.getText());
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		return numeroMunicipiosEntrevistados;
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
	
	/** Limpa os campos da janela, deixando os campos de data vazios, os de texto(numero) com valor 0, o <code>JComboBox</code> com o valor
	 * {@link Janela#COMBO_BOX_TEXTO_SELECIONE} e limpa a tabela.
	 * 
	 */
	public void limpaCamposJanela(){
		campoDataInicio.setDate(null);
		campoDataFim.setDate(null);
		campoNumeroVotosBrancosNulos.setText("0");
		campoNumeroVotosIndecisos.setText("0");
		campoNumeroPessoasEntrevistadas.setText("0");
		campoNumeroMunicipiosPesquisados.setText("0");
		campoSelecionarCargo.setSelectedItem(Janela.COMBO_BOX_TEXTO_SELECIONE);
		this.limpaTabela();
	}
	
	//soma todos os votos validos que estao na tabela
	/** Retorna um <code>int</code> com o total de votos validos, sem votos brancos, nulos e indecisos.
	 * @return um <code>int</code> com o total de votos validos, sem votos brancos, nulos e indecisos.
	 */
	private int totalVotosValidos(){
		int totalVotos = 0;
		int votos;
		DefaultTableModel model = ((DefaultTableModel)(this.tabelaCandidatos.getModel()));
		this.tabelaCandidatos.clearSelection();
		for(int i = 0; i < model.getRowCount(); i++){
			votos = 0;
			try{
				votos = Integer.parseInt((String)model.getValueAt(i, this.tabelaCandidatos.getColumn(COLUNA_INTENCOES_VOTO).getModelIndex()));
			}
			catch(NumberFormatException e){
				//e.printStackTrace();
				this.addError("A coluna " + COLUNA_INTENCOES_VOTO + " deve ter apenas caracteres numericos. Ver candidato: " + model.getValueAt(i, this.tabelaCandidatos.getColumn(COLUNA_NOME).getModelIndex()));
			}
			if (votos >= 0){
				totalVotos += votos;
			}
			else{
				this.addError("O candidato " + model.getValueAt(i, this.tabelaCandidatos.getColumn(COLUNA_NOME).getModelIndex()) + " está com um numero de votos invalido(negativo)" );
			}
		}
		return totalVotos;
	}
	
	/** Retorna um <code>int</code> com o total de votos, considerando todos os votos, ate indecisos, nao souberam ou quiseram opinar. 
	 * @return um <code>int</code> com o total de votos, considerando todos os votos, ate indecisos, nao souberam ou quiseram opinar.
	 */
	private int getTotalVotos(){
		//soma de todos os votos(candidatos + indecisos + nulos)
		int totalVotos = 0;
		int votosBrancosNulos = 0;
		int votosIndecisos = 0;
		try{
			votosBrancosNulos = Integer.parseInt(campoNumeroVotosBrancosNulos.getText());
			if(votosBrancosNulos < 0){
				this.addError("Número de votos nulos e brancos deve ser maior ou igual a zero");
			}
		}
		catch(NumberFormatException e){
			//e.printStackTrace();
			this.addError("Campo Numero votos branco deve conter apenas numeros");
		}
		try{
			votosIndecisos = Integer.parseInt(campoNumeroVotosIndecisos.getText());
			if(votosBrancosNulos < 0){
				this.addError("Número de indecisos deve ser maior ou igual a zero");
			}
		}
		catch(NumberFormatException e){
			//e.printStackTrace();
			this.addError("Campo Numero votos indecisos deve conter apenas numeros");
		}
		
		totalVotos = totalVotosValidos() + votosBrancosNulos + votosIndecisos;
		return totalVotos;
	}
	
	/** Retorna um {@code List<IdCandidatoVotos>} com os ids dos candidatos e o numero de votos na pesquisa
	 * @return um {@code List<IdCandidatoVotos>} com os ids dos candidatos e o numero de votos na pesquisa
	 */
	public List<IdCandidatoVotos> getCandidatosVotos(){
		List<IdCandidatoVotos> candidatos = new ArrayList<IdCandidatoVotos>();
		Candidato candidato = null;
		int votos = 0;
		DefaultTableModel model = ((DefaultTableModel)(this.tabelaCandidatos.getModel()));
		int numeroCandidatoTabela = -1;
		for(int i = 0; i < model.getRowCount(); i++){
			votos = 0;
			try{
				votos = Integer.parseInt((String)model.getValueAt(i, this.tabelaCandidatos.getColumn(COLUNA_INTENCOES_VOTO).getModelIndex()));
			}
			catch(NumberFormatException e){
				//e.printStackTrace();
				votos = 0;
			}
			if (votos < 0){
				votos = 0;
			}
			candidato = this.getCandidatos().get(i);
			try{
				int indiceColunaNumeroCandidato = this.tabelaCandidatos.getColumn(COLUNA_NUMERO_CANDIDATO).getModelIndex();
				numeroCandidatoTabela = (Integer)model.getValueAt( i, indiceColunaNumeroCandidato);
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemErro(this, "Erro ao pegar numero do candidato " + (String)model.getValueAt(i, this.tabelaCandidatos.getColumn(COLUNA_NOME).getModelIndex()) + " da tabela");
			}
			if ( numeroCandidatoTabela == candidato.getNumeroCandidato() ){
				candidatos.add(new IdCandidatoVotos(candidato.getId(), votos));
			}
		}
		return candidatos;
	}

	/** Retorna um {@code List<Candidato>} the candidatos
	 * @return um {@code List<Candidato>} the candidatos
	 */
	public List<Candidato> getCandidatos() {
		return candidatos;
	}
	
	/** Retorna um <code>JTextField</code> com o campoNumeroVotosBrancosNulos
	 * @return um <code>JTextField</code> com o campoNumeroVotosBrancosNulos
	 */
	public JTextField getCampoNumeroVotosBrancosNulos() {
		return campoNumeroVotosBrancosNulos;
	}

	/** Retorna um <code>JTextField</code> com o campoNumeroVotosIndecisos
	 * @return um <code>JTextField</code> com o campoNumeroVotosIndecisos
	 */
	public JTextField getCampoNumeroVotosIndecisos() {
		return campoNumeroVotosIndecisos;
	}

	/** Retorna um <code>JTextField</code> com o campoNumeroPessoasEntrevistadas
	 * @return um <code>JTextField</code> com o campoNumeroPessoasEntrevistadas
	 */
	public JTextField getCampoNumeroPessoasEntrevistadas() {
		return campoNumeroPessoasEntrevistadas;
	}

	/** Retorna um <code>JTextField</code> com o campoNumeroMunicipiosPesquisados
	 * @return um <code>JTextField</code> com o campoNumeroMunicipiosPesquisados
	 */
	public JTextField getCampoNumeroMunicipiosPesquisados() {
		return campoNumeroMunicipiosPesquisados;
	}

	/** Verifica se os campos são validos.<br>
	 * Os <code>JComboBox</code> não podem estar com o valor {@link Janela#COMBO_BOX_TEXTO_SELECIONE}. A data de inicio não pode ser posterior a data de fim da pesquisa, 
	 * os campos data de inicio e data de fim não podem estar vazios, a data de fim não pode ser posterior a data atual.
	 * Os campos Nulos/Brancos, Indecisos, Pessoas entrevistadas, Municipios pesquisados não podem estar vazios, devem ter valores numericos e não podem ser menores que 0. 
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 * @see Data
	 */
	@Override
	public boolean verificaCampos() {
		boolean temCamposNumeroVotosEmBranco = false; //verifica se ha campos com numero de votos em branco ou o se o campo numeroPessoas esta em branco
		boolean dataInicioValida = false;
		boolean dataFimValida = false;
		Data dataInicio = null;
		Data dataFim = null;
		//Verifica campos vazios
		if(campoSelecionarCargo.getSelectedItem().equals(Janela.COMBO_BOX_TEXTO_SELECIONE)){
			this.addError("Selecione um cargo");
		}
		if(campoDataInicio.getText().isEmpty()){
			this.addError("Campo Data Inicio está vazio");
			dataInicioValida = false;
		}
		else{ //verifica data
			dataInicio = new Data(campoDataInicio.getDate());
			if (dataInicio.getAno() < 1995){
				this.addError("O ano da data de inicio da pesquisa deve ser maior ou igual a 1995");
			}
			else{
				dataInicioValida = true;
			}
		}
		if(campoDataFim.getText().isEmpty()){
			this.addError("Campo Data Fim está vazio");
			dataFimValida = false;
		}
		else{ //verifica data
			dataFim = new Data(campoDataFim.getDate());
			if (dataFim.getAno() < 1995){
				this.addError("O ano da data de fim da pesquisa deve ser maior ou igual a 1995");
			}
			else{
				dataFimValida = true;
			}
		}
		if (dataInicioValida && dataFimValida){
			if (dataInicio.compareTo(dataFim) <= 0){ //data inicio é menor q data fim
				
			}
			else{
				this.addError("A data de inicio deve ser menor ou igual a data de fim da pesquisa");
			}
			if ( dataFim.compareTo(new Data()) > 0){
				this.addError("A data de fim deve ser menor ou igual a data atual");
			}
		}
		if(campoNumeroVotosBrancosNulos.getText().trim().isEmpty()){
			this.addError("Campo Votos Brancos está vazio");
			temCamposNumeroVotosEmBranco = true;
		}
		if(campoNumeroVotosIndecisos.getText().trim().isEmpty()){
			this.addError("Campo Indecisos está vazio");
			temCamposNumeroVotosEmBranco = true;
		}
		if(campoNumeroPessoasEntrevistadas.getText().trim().isEmpty()){
			this.addError("Campo Pessoas Entrevistadas está vazio");
			temCamposNumeroVotosEmBranco = true;
		}
		if(campoNumeroMunicipiosPesquisados.getText().trim().isEmpty()){
			this.addError("Campo Municipios Pesquisados está vazio");
		}
		try{
			if(Integer.parseInt(campoNumeroMunicipiosPesquisados.getText().trim()) < 0 ){
				this.addError("Número de Municipios Pesquisados deve ser maior que zero");
			}
		}
		catch(NumberFormatException e){
				//e.printStackTrace();
				this.addError("Número de Municipios Pesquisados deve conter apenas numeros");
		}
		
		//Verifica votos
		if (!temCamposNumeroVotosEmBranco){
			//verifica se total de votos é igual ao numero de pessoas
			int numeroPessoasEntrevistadas = 0;
			try{
				numeroPessoasEntrevistadas = Integer.parseInt(campoNumeroPessoasEntrevistadas.getText());
				if(numeroPessoasEntrevistadas < 0){
					this.addError("Número de Pessoas entrevistadas deve ser maior que zero");
				}
			}
			catch(NumberFormatException e){
				//e.printStackTrace();
				this.addError("Campo Numero de pessoas entrevistadas deve conter apenas numeros");
			}
			if(this.getErros().isEmpty()){
				int numeroTotalVotos = getTotalVotos();
				if(numeroTotalVotos != numeroPessoasEntrevistadas){
					this.addError(String.format("Numero de pessoas(%d) é diferente do numero do total de votos(%d)", numeroPessoasEntrevistadas, numeroTotalVotos));
				}
			}
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
