package com.arthurassuncao.sistel.gui.candidato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.eventos.EventoMenuPopUp;
import com.arthurassuncao.sistel.eventos.FixedLengthDocument;
import com.arthurassuncao.sistel.eventos.FixedLengthWithNumerosPositivosDocument;
import com.arthurassuncao.sistel.eventos.candidato.TratadorEventosMousePesquisarCandidato;
import com.arthurassuncao.sistel.eventos.candidato.TratadorEventosTecladoPesquisarCandidato;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;
import com.arthurassuncao.sistel.persistencia.BDEleicoes;

/** A classe <code>JanelaPesquisarCandidato</code> cria uma GUI para pesquisa por candidatos, alem de permitir que se selecione candidatos para exibir seus dados,
 * excluir e alterar. 
 * @author Arthur Assunção
 * 
 *
 * @see Janela
 * @see JanelaCadastrarCandidato
 * @see JanelaExibirDadosCandidato
 * @see JanelaExcluirCandidato
 *
 */
public class JanelaPesquisarCandidato extends Janela {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -841255591886073738L;
	/** <code>int</code> com o numero de colunas da tabela com os dados dos candidatos pesquisados */
	public final int NUMERO_COLUNAS_TABELA = 4;
	
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  = 450;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 = 310;
	/*public static final int LARGURA_FOTO = 150;
	public static final int ALTURA_FOTO  = 170;*/
	
	private int numeroColunasCamposTexto = 15;
	//private static final String FOTO_PADRAO = "/too/trabalho/gui/imagens/semFoto.jpg"; //imagem para candidato sem foto
	
	private final String COLUNA_NOME_CANDIDATO = "Nome";
	private final String COLUNA_NUMERO_CANDIDATO = "Numero";
	private final String COLUNA_CARGO_CANDIDATO = "Cargo";
	private final String COLUNA_PARTIDO_CANDIDATO = "Partido";
	private final String[] COLUNAS_CANDIDATO = {COLUNA_NOME_CANDIDATO, COLUNA_NUMERO_CANDIDATO, COLUNA_CARGO_CANDIDATO, COLUNA_PARTIDO_CANDIDATO};
	private String[][] linhasTabela = new String[0][NUMERO_COLUNAS_TABELA];
	
	//Paineis
	private Painel painelTotal;
	private Painel painelLabelsCampos;
	private Painel painelTabelaResultado;
	
	//Labels
	private LabelRotulo labelNome;
	private LabelRotulo labelNumero;
	
	//campos
	private JTextField campoNome;
	private JTextField campoNumero;
	
	//tabela
	private JTable tabelaResultado;
	
	//Barra de rolagem
	private JScrollPane barraRolagem;

	//Botoes
	private JButton botaoPesquisar;
	
	//textDocument
	private FixedLengthWithNumerosPositivosDocument documentCampoNumero = new FixedLengthWithNumerosPositivosDocument(BDEleicoes.TAMANHO_CANDIDATO_NUMERO);
	
	/** Cria uma instancia da jenela de pesquisar candidato
	 */
	public JanelaPesquisarCandidato() {
		super("Pesquisar Candidato", LARGURA, ALTURA);
		
		this.iniciaElementos();
		
		this.addElementos();
		
		//this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		//repaint();
	}
	
	/** Instancia os componentes da janela, alem de setar algumas propriedades de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		//inicia os paineis
		painelTotal 			= new Painel();
		painelLabelsCampos = new Painel(new GridBagLayout());
		painelTabelaResultado = new Painel();
		
		//inicia os labels
		labelNome = new LabelRotulo("Nome: ");
		labelNumero = new LabelRotulo("Numero: ");
		
		//inicia os campos
		campoNome = new JTextField(numeroColunasCamposTexto);
		campoNumero = new JTextField(6);
		
		//eventos dos campos de texto
		campoNome.addKeyListener(new TratadorEventosTecladoPesquisarCandidato(this));
		campoNome.setDocument(new FixedLengthDocument(BDEleicoes.TAMANHO_CANDIDATO_NOME));
		campoNumero.setDocument(documentCampoNumero);
		
		//inicia os botoes
		botaoPesquisar = new JButton("Pesquisar");
		botaoPesquisar.addMouseListener(new TratadorEventosMousePesquisarCandidato(this));
		
		botaoPesquisar.setFocusable(false);
				
		//inicia a tabela
		tabelaResultado = new JTable(new DefaultTableModel(this.linhasTabela, this.COLUNAS_CANDIDATO)){
			private static final long serialVersionUID = 5727320816550514927L;
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //evita a edicao das celulas
			}
		};
		int larguraTabela = LARGURA-40;
		tabelaResultado.setPreferredScrollableViewportSize(new Dimension(larguraTabela, 200));
		int larguraNumero = 60;
		tabelaResultado.getColumn(COLUNA_NUMERO_CANDIDATO).setPreferredWidth(larguraNumero);
		int larguraCargo = 90;
		tabelaResultado.getColumn(COLUNA_CARGO_CANDIDATO).setPreferredWidth(larguraCargo);
		int larguraPartido = 50;
		tabelaResultado.getColumn(COLUNA_PARTIDO_CANDIDATO).setPreferredWidth(larguraPartido);
		int larguraNome = larguraTabela - larguraCargo - larguraNumero - larguraPartido;
		tabelaResultado.getColumn(COLUNA_NOME_CANDIDATO).setPreferredWidth(larguraNome);
		//tabelaResultado.setSelectionBackground(Color.LIGHT_GRAY);
		tabelaResultado.addMouseListener(new TratadorEventosMousePesquisarCandidato(this));
		tabelaResultado.addMouseListener(new EventoMenuPopUp(this.tabelaResultado, new MenuPopUp(this.tabelaResultado, this)));
		tabelaResultado.setBackground(Color.WHITE);
		tabelaResultado.setRowSelectionAllowed(true); //selecao de linha
		//tabelaResultado.setCellSelectionEnabled(false); //selecao de celula
        tabelaResultado.setColumnSelectionAllowed(false); //selecao de coluna
		
		//inicia barra de rolagem
		barraRolagem = new JScrollPane(tabelaResultado);
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		//Adiciona componentes ao painelLabelCampos
		painelLabelsCampos.add(labelNome);
		painelLabelsCampos.add(campoNome);
		painelLabelsCampos.add(new Painel());
		painelLabelsCampos.add(labelNumero);
		painelLabelsCampos.add(campoNumero);
		painelLabelsCampos.add(new Painel());
		painelLabelsCampos.add(botaoPesquisar);

		//Adiciona componentes ao painelTabelaResultado
		painelTabelaResultado.add(barraRolagem);
		
		//Adiciona componentes ao painelTotal
		painelTotal.add(painelLabelsCampos);
		painelTotal.add(painelTabelaResultado);
				
		this.add(painelTotal);
	}

	/** Retorna um <code>JButton</code> com uma referencia para o botaoPesquisar
	 * @return <code>JButton</code> com uma referencia para o botaoPesquisar
	 */
	public JButton getBotaoPesquisar() {
		return botaoPesquisar;
	}

	/** Retorna um <code>JTextField</code> com uma referencia para o campoNome
	 * @return <code>JTextField</code> com uma referencia para o campoNome
	 */
	public JTextField getCampoNome() {
		return campoNome;
	}

	/** Retorna um <code>JTextField</code> com uma referencia para o campoNumero
	 * @return <code>JTextField</code> com uma referencia para o campoNumero
	 */
	public JTextField getCampoNumero() {
		return campoNumero;
	}
	
	/** Adiciona linhas à tabela, são inseridos os dados dos candidatos que foram passados por parametro no <code>List</code>
	 * @param candidatos {@code List<Candidato>} com os dados dos candidatos que serão inseridos na tabela
	 */
	public void addLinhasTabela(List<Candidato> candidatos){
		DefaultTableModel model = limpaTabela();
		if (candidatos.size() > 0){
			Object[] linha = new Object[NUMERO_COLUNAS_TABELA];
			for(int i = 0; i < candidatos.size(); i++){
				//JanelaMensagem.mostraMensagem(null, "teste", ""+candidatos.get(0).getNumeroCandidato());
				linha[0] = candidatos.get(i).getNome();
				linha[1] = String.valueOf(candidatos.get(i).getNumeroCandidato());
				linha[2] = candidatos.get(i).getCargo();
				linha[3] = candidatos.get(i).getPartidoPolitico().getSigla();
				//model.addRow(new String[] {candidatos.get(0).getNome(), String.valueOf(candidatos.get(0).getNumeroCandidato()), candidatos.get(0).getCargo(), candidatos.get(0).getPartidoPolitico().getSigla()});
				model.addRow(linha);
			}
		}
		else{
			//JanelaMensagem.mostraMensagemErro(null, "Nenhum candidato encontrado");
		}
	}
	
	/** Limpa a tabela da pesquisa apando os dados contidos nela
	 * @return <code>DefaultTableModel</code> com o modelo da tabela
	 */
	public DefaultTableModel limpaTabela(){
		DefaultTableModel model = ((DefaultTableModel)(this.tabelaResultado.getModel()));
		model.setNumRows(0);
		return model;
	}
	
	/** Atualiza a tabela fazendo uma pesquisa usando o campo nome, se este nao estiver vazio, ou o campo numero. 
	 *  Caso os dois campos estejam vazios, os dados da tabela sao apagados 
	 */
	public void atualizaTabela(){
		if (!this.getCampoNome().getText().isEmpty()){
			String nome = this.getCampoNome().getText();
			List<Candidato> candidatos = Candidato.pesquisar(nome);
			this.addLinhasTabela(candidatos);
		}
		else if (!this.getCampoNumero().getText().isEmpty()){
				String numero = this.getCampoNumero().getText();
				List<Candidato> candidatos = Candidato.pesquisar(Integer.parseInt(numero), null);
				this.addLinhasTabela(candidatos);
			}
			else{
				this.limpaTabela();
			}
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
