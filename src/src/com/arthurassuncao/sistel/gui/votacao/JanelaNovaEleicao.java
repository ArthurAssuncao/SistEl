package com.arthurassuncao.sistel.gui.votacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.eventos.votacao.TratadorEventoMouseNovaEleicao;
import com.arthurassuncao.sistel.gui.Calendario;
import com.arthurassuncao.sistel.gui.Fonte;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;

/** Cria uma interface grafica para o usuario cadastrar novas votacoes
 * @author Arthur Assunção
 * 
 * @see Janela
 */
public class JanelaNovaEleicao extends Janela {

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 7260342749786391100L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  	= 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 	= 600;
	
	private JList<String> listaCargos;
	private JList<String> listaCargosUsados;
	
	private JScrollPane barraRolagemListaCargos;
	private JScrollPane barraRolagemListaCargosUsados;
	
	private Painel painelTotal;
	private Painel painelBotoesDireitaEsquerda;
	private Painel painelBotoesSul;
	
	private LabelRotulo labelCargosDisponiveis;
	private LabelRotulo labelCargosUsados;
	private LabelRotulo labelDataVotacao;
	
	private Calendario campoDataVotacao;

	private JButton botaoAdiciona;
	private JButton botaoRemove;
	
	private JButton botaoLimpar;
	private JButton botaoSalvar;
	
	GridBagConstraints gridBagConstraints;
	
	/** Cria uma instancia da janela de cadastro de votacao
	 * 
	 */
	public JanelaNovaEleicao(){
		super("Nova Eleição", LARGURA, ALTURA);
		
		this.iniciaElementos();
		
		this.addDadosListas();
		
		this.addEventos();
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos e adiciona dados aos <code>JList</code>, alem de setar algumas propriedades de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		//inicia listas
		listaCargos = new JList<String>(new DefaultListModel<String>());
		listaCargosUsados = new JList<String>(new DefaultListModel<String>());
				
		//inicia o campo data
		campoDataVotacao = new Calendario(Calendario.CALENDARIO_WEBDATEFIELD);

		//inica barras de rolagem
		barraRolagemListaCargos = new JScrollPane(listaCargos);
		barraRolagemListaCargosUsados = new JScrollPane(listaCargosUsados);
		
		barraRolagemListaCargos.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		barraRolagemListaCargosUsados.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		Dimension tamanhoListas = new Dimension(200, 400); 
		barraRolagemListaCargos.setPreferredSize(tamanhoListas);
		barraRolagemListaCargosUsados.setPreferredSize(tamanhoListas);
		Color corFundoListas = new Color(240, 240, 240);
		listaCargos.setBackground(corFundoListas);
		listaCargosUsados.setBackground(corFundoListas);
		
		//inicia paineis
		painelTotal = new Painel(new GridBagLayout());
		painelBotoesDireitaEsquerda = new Painel(new GridBagLayout());
		painelBotoesSul = new Painel(new GridBagLayout());
		
		//inicia os labels
		labelCargosDisponiveis = new LabelRotulo("Cargos Disponiveis");
		labelCargosUsados = new LabelRotulo("Cargos Selecionados");
		labelDataVotacao = new LabelRotulo("Data da Votação: ");
		
		Font fonteLabels = new Fonte(14.0F).getFont();
		labelCargosUsados.setFont(fonteLabels);
		labelCargosDisponiveis.setFont(fonteLabels);
		
		//inicia botoes
		botaoAdiciona = new JButton(">");
		botaoRemove = new JButton("<");
		
		botaoLimpar = new JButton("Limpar");
		botaoSalvar = new JButton("Salvar");
		
		botaoAdiciona.setFocusable(false);
		botaoRemove.setFocusable(false);
		botaoLimpar.setFocusable(false);
		botaoSalvar.setFocusable(false);
		
		//inicia grid
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	public void addElementos(){
		int linha = 0;
		
		//adiciona ao painelBotoes
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = linha++;
		painelBotoesDireitaEsquerda.add(botaoAdiciona, gridBagConstraints);
		gridBagConstraints.gridy = linha++;
		painelBotoesDireitaEsquerda.add(botaoRemove, gridBagConstraints);
		
		linha = 0;
		Insets insetsPadrao = gridBagConstraints.insets;
		Insets insetsEsquerda = new Insets(20, 20, 0, 5);
		Insets insetsDireita = new Insets(20, 0, 0, 20);
		gridBagConstraints.insets = insetsEsquerda;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = linha++;
		painelTotal.add(labelCargosDisponiveis, gridBagConstraints);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.insets = insetsDireita;
		painelTotal.add(labelCargosUsados, gridBagConstraints);
		
		gridBagConstraints.insets = insetsEsquerda;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = linha++;
		painelTotal.add(barraRolagemListaCargos, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.insets = insetsPadrao;
		painelTotal.add(painelBotoesDireitaEsquerda, gridBagConstraints);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.insets = insetsDireita;
		painelTotal.add(barraRolagemListaCargosUsados, gridBagConstraints);
		
		Painel painelData = new Painel(new GridBagLayout());
		GridBagConstraints gridData = new GridBagConstraints();
		gridData.gridy = 0;
		gridData.insets = new Insets(5, 0, 5, 5);
		painelData.add(labelDataVotacao, gridData);
		painelData.add(campoDataVotacao, gridData);
		
		/*gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = linha++;*/
		
		//painelTotal.add(painelData, gridBagConstraints);
		
		//adiciona ao painelBotoesSul
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 20);
		painelBotoesSul.add(botaoLimpar, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		painelBotoesSul.add(botaoSalvar, gridBagConstraints);
		
		gridBagConstraints.gridy = linha++;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = new Insets(5, 20, 5, 5);
		painelTotal.add(painelData, gridBagConstraints);
		gridBagConstraints.gridx = 2;
		//gridBagConstraints.insets = new Insets(15, 5, 10, 5);
		painelTotal.add(painelBotoesSul, gridBagConstraints);
		
		
		this.add(painelTotal);
	}
	
	/** Adiciona eventos aos elementos
	 * 
	 */
	public void addEventos(){
		this.botaoAdiciona.addMouseListener(new TratadorEventoMouseNovaEleicao(this));
		this.botaoRemove.addMouseListener(new TratadorEventoMouseNovaEleicao(this));
		
		this.botaoLimpar.addMouseListener(new TratadorEventoMouseNovaEleicao(this));
		this.botaoSalvar.addMouseListener(new TratadorEventoMouseNovaEleicao(this));
	}
	
	/** Adiciona dados aos <code>JList</code>
	 * @see DefaultListModel
	 */
	public void addDadosListas(){
		DefaultListModel<String> modeloListaCargos = (DefaultListModel<String>)listaCargos.getModel();
		List<Cargo> cargos = Cargo.getAll();
		for(Cargo cargo : cargos){
			modeloListaCargos.addElement(cargo.getNome());
		}
		listaCargos.setModel(modeloListaCargos);
		listaCargos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ordenaLista(listaCargos);
		listaCargosUsados.setModel(new DefaultListModel<String>());
		listaCargosUsados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/** Ordena os elementos dos <code>JList</code>
	 * @param lista <code>JList</code> com a lista que será ordenada
	 * @see DefaultListModel
	 */
	public void ordenaLista(JList<String> lista){
		DefaultListModel<String> modeloLista = (DefaultListModel<String>)lista.getModel();
		String[] items = new String[modeloLista.size()];
		for(int i = 0; i < items.length; i++){
			items[i] = modeloLista.getElementAt(i); //(String)
		}

		Arrays.sort(items);
		
		modeloLista.removeAllElements();
		for (int i = 0; i < items.length; ++i){
			modeloLista.addElement(items[i]);
		} 
	}
	
	/** Limpa os campos da janela, deixando o campo de data vazio, os <code>JList</code> com o valorpadrao
	 * @see DefaultListModel
	 */
	public void limparCamposJanela(){
		DefaultListModel<String> modeloListaCargosUsados = (DefaultListModel<String>)this.listaCargosUsados.getModel();
		DefaultListModel<String> modeloListaCargos = (DefaultListModel<String>)this.listaCargos.getModel();
		
		if(modeloListaCargosUsados.size() > 0){
			String cargo;
			for(int i=0; i < modeloListaCargosUsados.size(); i++){
				cargo = modeloListaCargosUsados.getElementAt(i); //String
				modeloListaCargos.addElement(cargo);
			}
			modeloListaCargosUsados.removeAllElements();
		}
		this.campoDataVotacao.setData(null);
	}
	
	/** Retorna um <code>JButton</code> com o botaoLimpar
	 * @return um <code>JButton</code> com o botaoLimpar
	 */
	public JButton getBotaoLimpar() {
		return botaoLimpar;
	}

	/** Retorna um <code>JButton</code> com o botaoSalvar
	 * @return um <code>JButton</code> com o botaoSalvar
	 */
	public JButton getBotaoSalvar() {
		return botaoSalvar;
	}

	/** Retorna um <code>JList</code> com o listaCargos
	 * @return um <code>JList</code> com o listaCargos
	 */
	public JList<String> getListaCargos() {
		return listaCargos;
	}

	/** Retorna um <code>JList</code> com o listaCargosUsados
	 * @return um <code>JList</code> com o listaCargosUsados
	 */
	public JList<String> getListaCargosUsados() {
		return listaCargosUsados;
	}

	/** Retorna um <code>JButton</code> com o botaoAdiciona
	 * @return um <code>JButton</code> com o botaoAdiciona
	 */
	public JButton getBotaoAdiciona() {
		return botaoAdiciona;
	}

	/** Retorna um <code>JButton</code> com o botaoRemove
	 * @return um <code>JButton</code> com o botaoRemove
	 */
	public JButton getBotaoRemove() {
		return botaoRemove;
	}
	
	/** Retorna <code>Data</code> com a data da votacao
	 * @return <code>Data</code> com a data da votacao
	 */
	public Data getDataVotacao(){
		return new Data(this.campoDataVotacao.getData());
	}

	/** Verifica se os campos são validos.<br>
	 * O <code>JList</code> com os cargos usados na votacao e a data não podem ficar vazios, a data da votacao não pode ser anterior a data atual.
	 * 
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 * @see DefaultListModel
	 * @see Data
	 */
	@Override
	public boolean verificaCampos() {
		DefaultListModel<String> modeloListaCargos = (DefaultListModel<String>)this.listaCargosUsados.getModel();
		if(modeloListaCargos.isEmpty()){
			this.addError("Nenhum cargo foi selecionado");
		}
		if(campoDataVotacao.getText().isEmpty()){
			this.addError("Forneça a data da votação");
		}
		else if(new Data(campoDataVotacao.getData()).compareTo(new Data()) < 0){ //data da votacao é anterior a data atual
			this.addError("A data da votação deve igual ou posterior a data atual");
		}
		if(!this.getErros().isEmpty()){
			return false;
		}
		else{
			return true;
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
