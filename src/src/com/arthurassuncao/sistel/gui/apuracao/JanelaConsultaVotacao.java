package com.arthurassuncao.sistel.gui.apuracao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.arthurassuncao.sistel.classes.votacao.Votacao;
import com.arthurassuncao.sistel.eventos.EventoMenuPopUp;
import com.arthurassuncao.sistel.eventos.FixedLengthDocument;
import com.arthurassuncao.sistel.eventos.apuracao.TratadorEventosMouseConsultarVotacao;
import com.arthurassuncao.sistel.eventos.apuracao.TratadorEventosTecladoConsultarVotacao;
import com.arthurassuncao.sistel.gui.Calendario;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;
import com.arthurassuncao.sistel.persistencia.BDEleicoes;

/** Cria uma interface grafica para consultar as votacoes e exibir os graficos da apuracao
 * @author Arthur Assunção
 * 
 * @see Janela
 */
public class JanelaConsultaVotacao extends Janela {

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -1759686384122604243L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  	= 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 	= 500;
	
	/** <code>int</code> com o numero de colunas da tabela com os dados das votacoes pesquisados */
	public final int NUMERO_COLUNAS_TABELA = 2;

	private int numeroColunasCamposTexto = 13;
	
	private final String[] COLUNAS_VOTACAO = {"Data", "Cargos"};
	private String[][] linhasTabela = new String[0][NUMERO_COLUNAS_TABELA];
	
	//Paineis
	private Painel painelTotal;
	private Painel painelLabelsCampos;
	private Painel painelTabelaResultado;
	
	//Labels
	private LabelRotulo labelData;
	private LabelRotulo labelCargo;
	
	//campos
	private Calendario campoData;
	private JTextField campoCargo;
	
	//tabela
	private JTable tabelaResultado;
	
	//Barra de rolagem
	private JScrollPane barraRolagem;

	//Botoes
	private JButton botaoPesquisar;
	
	/** Cria uma instancia da jenela de pesquisar candidato
	 */
	public JanelaConsultaVotacao(){
		super("Consulta Votacao", LARGURA, ALTURA);
		
		this.iniciaElementos();
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);		
	}
	
	/** Instancia os componentes da janela, alem de setar algumas propriedades de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		//inicia os paineis
		painelTotal 			= new Painel(new GridBagLayout());
		painelLabelsCampos = new Painel(new GridBagLayout());
		painelTabelaResultado = new Painel();
		
		//inicia os labels
		labelData = new LabelRotulo("Data: ");
		labelCargo = new LabelRotulo("Cargo: ");
		
		//inicia os campos
		campoData = new Calendario(Calendario.CALENDARIO_WEBDATEFIELD);
		campoCargo = new JTextField(numeroColunasCamposTexto);
		
		//eventos dos campos
		campoCargo.addKeyListener(new TratadorEventosTecladoConsultarVotacao(this));
		campoCargo.setDocument(new FixedLengthDocument(BDEleicoes.TAMANHO_CANDIDATO_CARGO));
		
		//inicia os botoes
		botaoPesquisar = new JButton("Pesquisar");
		botaoPesquisar.addMouseListener(new TratadorEventosMouseConsultarVotacao(this));
		
		botaoPesquisar.setFocusable(false);

		//inicia a tabela
		tabelaResultado = new JTable(new DefaultTableModel(this.linhasTabela, this.COLUNAS_VOTACAO)){
			private static final long serialVersionUID = 5727320816550514927L;
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				  return false; //evita a edicao das celulas
			}
			
		};
		tabelaResultado.setPreferredScrollableViewportSize(new Dimension(LARGURA-40, 200));
		tabelaResultado.getColumn("Data").setPreferredWidth(100);
		tabelaResultado.getColumn("Cargos").setPreferredWidth(LARGURA-40-100);
		tabelaResultado.addMouseListener(new TratadorEventosMouseConsultarVotacao(this));
		tabelaResultado.addMouseListener(new EventoMenuPopUp(this.tabelaResultado, new MenuPopUp(tabelaResultado, this)));
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
		
		//grid
		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.NONE;
		
		//Adiciona componentes ao painelLabelCampos
		painelLabelsCampos.add(labelData);
		painelLabelsCampos.add(campoData);
		painelLabelsCampos.add(new Painel());
		painelLabelsCampos.add(labelCargo);
		painelLabelsCampos.add(campoCargo);
		painelLabelsCampos.add(new Painel());
		painelLabelsCampos.add(botaoPesquisar);

		//Adiciona componentes ao painelTabelaResultado
		painelTabelaResultado.add(barraRolagem);
		
		//Adiciona componentes ao painelTotal
		grid.gridx = 0;
		grid.gridy = 0;
		painelTotal.add(painelLabelsCampos, grid);
		grid.gridy = 1;
		painelTotal.add(painelTabelaResultado, grid);
				
		this.add(painelTotal);
	}
	
	/** Adiciona linhas à tabela, são inseridos os dados das votacoes que foram passados por parametro no <code>List</code>
	 * @param votacoes {@code List<Votacao>} com os dados das votacoes que serão inseridos na tabela
	 */
	public void addLinhasTabela(List<Votacao> votacoes){
		DefaultTableModel model = limpaTabela();

		if(votacoes != null){
			if (votacoes.size() > 0){
				Object[] linha = new Object[NUMERO_COLUNAS_TABELA];
				for(int i = 0; i < votacoes.size(); i++){
					linha[0] = votacoes.get(i).getData().getDataDDMMAAAA();
					if(votacoes.get(i).getCargos().size() > 0){
						StringBuilder cargos = new StringBuilder();
						cargos.append(votacoes.get(i).getCargos().get(0));
						for(int j = 1; j < votacoes.get(i).getCargos().size(); j++){
							cargos.append(" - " + votacoes.get(i).getCargos().get(j));
						}
						linha[1] = cargos.toString(); //ta juntando os cargos no formato: cargo - cargo - cargo
					}
					model.addRow(linha);
					
				}
			}
			else{
				//JanelaMensagem.mostraMensagemErro(null, "Nenhuma votacao encontrada");
			}
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
	
	/** Retorna um <code>JButton</code> com uma referencia para o botaoPesquisar
	 * @return <code>JButton</code> com uma referencia para o botaoPesquisar
	 */
	public JButton getBotaoPesquisar() {
		return botaoPesquisar;
	}

	/** Retorna um <code>Calendario</code> com uma referencia para o campoData
	 * @return <code>Calendario</code> com uma referencia para o campoData
	 */
	public Calendario getCampoData() {
		return campoData;
	}

	/** Retorna um <code>JTextField</code> com uma referencia para o campoCargo
	 * @return <code>JTextField</code> com uma referencia para o campoCargo
	 */
	public JTextField getCampoCargo() {
		return campoCargo;
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
