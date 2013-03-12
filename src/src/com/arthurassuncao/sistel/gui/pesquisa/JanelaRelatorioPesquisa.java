package com.arthurassuncao.sistel.gui.pesquisa;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa.NomeCandidatoVotos;
import com.arthurassuncao.sistel.eventos.pesquisa.TratadorEventoItemRelatorioPesquisa;
import com.arthurassuncao.sistel.eventos.pesquisa.TratadorEventoMouseRelatorioPesquisa;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.Painel;
import com.arthurassuncao.sistel.gui.graficos.Grafico;
import com.arthurassuncao.sistel.gui.graficos.Grafico.Graficos;
import com.arthurassuncao.sistel.gui.graficos.Grafico.NomeValor;
import com.arthurassuncao.sistel.gui.graficos.GraficoLinhaSerie;
import com.arthurassuncao.sistel.gui.graficos.GraficoPizza;
import com.arthurassuncao.sistel.gui.graficos.GraficoTempoSerie;
import com.arthurassuncao.sistel.gui.graficos.GraficoXYLinhaSerie;


/** A classe <code>JanelaRelatorioPesquisa</code> cria uma GUI para exibir os graficos das pesquisas eleitorais
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 *
 */
public class JanelaRelatorioPesquisa extends Janela {

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -953362972803206162L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  				= 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 				= 300;
	
	/** <code>String</code> com o texto de "selecione" do <code>JComboBox</code> cargo*/
	public static final String TEXTO_SELECIONE_CARGO = "--Cargo--";
	/** <code>String</code> com o texto de "selecione" do <code>JComboBox</code> data pesquisa*/
	public static final String TEXTO_SELECIONE_DATA_PESQUISA = "--Data Inicio - Data fim--";
	/** <code>String</code> com o texto de "selecione" do <code>JComboBox</code> grafico*/
	public static final String TEXTO_SELECIONE_GRAFICO = "--Grafico--";
	
	private Grafico grafico;
	
	//Paineis
	private Painel painelTotal;
	private Painel painelNorte;
	private Painel painelSul;
	private Painel painelGrafico;
	
	//Campos
	JComboBox<String> campoSelecionarCargo;
	JComboBox<String> campoSelecionarDataPesquisa;
	JComboBox<String> campoSelecionarGrafico;
	
	//Botoes
	JButton botaoGraficoAnterior;
	JButton botaoGraficoPosterior;
	
	//Grid
    private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
    /** Cria uma instancia da janela de relatorio de pesquisas
	 * 
	 */
	public JanelaRelatorioPesquisa(){
		super("Relatório Pesquisa", LARGURA, ALTURA);

		this.iniciaElementos();
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos e adiciona dados aos <code>JComboBox</code>, alem de setar algumas propriedades de alguns componentes e adicionar eventos aos elementos
	 * 
	 */
	private void iniciaElementos(){
		//inicia os paineis
		painelTotal = new Painel(new GridBagLayout());
		painelNorte = new Painel(new GridBagLayout());
		painelSul = new Painel(new GridBagLayout());
		painelGrafico = new Painel(new GridBagLayout());
		
		//inicia os campos
		campoSelecionarCargo 	= new JComboBox<String>();
		campoSelecionarCargo.setMaximumRowCount(7);
		campoSelecionarDataPesquisa 	= new JComboBox<String>();
		campoSelecionarDataPesquisa.setMaximumRowCount(7);
		campoSelecionarGrafico 	= new JComboBox<String>();
		campoSelecionarGrafico.setMaximumRowCount(7);
		campoSelecionarGrafico.setEnabled(false);
		
		//inicia os botoes
		botaoGraficoAnterior = new JButton("<");
		botaoGraficoPosterior = new JButton(">");
		
		botaoGraficoAnterior.setFocusable(false);
		botaoGraficoPosterior.setFocusable(false);
		
		//inicia o grafico
		this.grafico = new Grafico();
		
		//inicia o Grid
		gridBagConstraint.insets = new Insets(3, 0, 3, 0); //espacos pro GridBadLayout
		gridBagConstraint.fill = GridBagConstraints.NONE;  //preenche toda coluna
		
		//Adiciona dados nos campos selecionar
		campoSelecionarCargo.addItem(TEXTO_SELECIONE_CARGO);
		campoSelecionarDataPesquisa.addItem(TEXTO_SELECIONE_DATA_PESQUISA);
		campoSelecionarGrafico.addItem(TEXTO_SELECIONE_GRAFICO);
		
		//adiciona dados no campoSelecionarCargo
		List<String> cargos = Pesquisa.getAllCargos();
		for(String cargo : cargos){
			campoSelecionarCargo.addItem(cargo);
		}
		
		//adiciona dados no campoSelecionarGrafico
		setCampoSelecionarGrafico(null);
		
		//Adiciona ToolTipsText
		campoSelecionarCargo.setToolTipText("Selecione um cargo");
		campoSelecionarDataPesquisa.setToolTipText("Selecione a data da pesquisa");
		campoSelecionarGrafico.setToolTipText("Selecione um grafico");
		botaoGraficoAnterior.setToolTipText("Anterior");
		botaoGraficoPosterior.setToolTipText("Proximo");
		
		//Adiciona os tratadores de eventos
		campoSelecionarCargo.addItemListener(new TratadorEventoItemRelatorioPesquisa(this));
		campoSelecionarDataPesquisa.addItemListener(new TratadorEventoItemRelatorioPesquisa(this));
		campoSelecionarGrafico.addItemListener(new TratadorEventoItemRelatorioPesquisa(this));
		
		botaoGraficoAnterior.addMouseListener(new TratadorEventoMouseRelatorioPesquisa(this));
		botaoGraficoPosterior.addMouseListener(new TratadorEventoMouseRelatorioPesquisa(this));
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		int linha = 0;
		
		//Adiciona ao painelNorte
		linha = 0;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelNorte.add(campoSelecionarCargo, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelNorte.add(campoSelecionarDataPesquisa, gridBagConstraint);
		
		//Adiciona ao painelSul
		linha = 0;
		int coluna = 0;
		gridBagConstraint.gridx = coluna++;
		gridBagConstraint.gridy = linha;
		painelSul.add(botaoGraficoAnterior, gridBagConstraint);
		gridBagConstraint.gridx = coluna++;
		gridBagConstraint.gridy = linha;
		painelSul.add(campoSelecionarGrafico, gridBagConstraint);
		gridBagConstraint.gridx = coluna++;
		gridBagConstraint.gridy = linha;
		painelSul.add(botaoGraficoPosterior, gridBagConstraint);
		
		//Adiciona ao painelGrafico
		painelGrafico.add(this.grafico);
		
		//Adiciona ao painelTotal
		linha = 0;
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelTotal.add(painelNorte, gridBagConstraint);
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelTotal.add(painelGrafico, gridBagConstraint);
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = linha++;
		painelTotal.add(painelSul, gridBagConstraint);
		
		this.add(painelTotal);
		
	}
	
	/** Muda o grafico da janela, pelo grafico selecionado
	 * @param grafico <code>String</code> com o nome do grafico que será exibido
	 * @param listaNomesValores {@code List<NomeCandidatoVotos>} com os nomes dos candidatos e seus respectivos numeros de votos
	 * @param entrevistados <code>int</code> com o numero de pessoas entrevistadas 
	 * @param municipios <code>int</code> com o numero de municipios pesquisados
	 */
	public void setGrafico(String grafico, List<NomeCandidatoVotos> listaNomesValores, int entrevistados, int municipios){
		List<NomeValor> nomesValores = new ArrayList<NomeValor>();
		for(NomeCandidatoVotos nomesCandidatosVotos : listaNomesValores){
			nomesValores.add(new NomeValor(nomesCandidatosVotos.getNomeCandidato(), nomesCandidatosVotos.getNumeroVotos()));
		}
		String titulo;
		titulo = this.getTituloGrafico();
		String legendaEntrevistados = "Entrevistados: " + entrevistados;
		String legendaMuncipiosPesquisados = "Municipios Pesquisados: " + municipios;
		if (grafico.equalsIgnoreCase(Graficos.PIZZA.getNome())){
			this.grafico.setGrafico(new GraficoPizza(titulo, nomesValores, false, legendaEntrevistados, legendaMuncipiosPesquisados));
		}
		else if (grafico.equalsIgnoreCase(Graficos.PIZZA_3D.getNome())){
			this.grafico.setGrafico(new GraficoPizza(titulo, nomesValores, true, legendaEntrevistados, legendaMuncipiosPesquisados));
		}
	}
	
	/** Muda o grafico da janela, pelo grafico selecionado
	 * @param grafico <code>String</code> com o nome do grafico que será exibido
	 * @param nomes {@code List<String>} com os nomes dos candidatos
	 * @param listaValores {@code List<ArrayList<Integer>>}  com lista de lista com as "linhas" correspondendo aos dados de um candidato e cada coluna da linha sendo o numero de votos numa determinada pesquisa
	 * @param datasFim {@code List<Data>} com as datas das pesquisas, são consideradas as datas de fim como as datas das pesquisas
	 */
	public void setGrafico(String grafico, List<String> nomes, List<ArrayList<Integer>> listaValores, List<Data> datasFim){
		String titulo;
		titulo = this.getValorCampoSelecionarCargo();
		if (grafico.equalsIgnoreCase(Graficos.LINHA_SERIE.getNome())){
			this.grafico.setGrafico(new GraficoLinhaSerie(titulo, datasFim, nomes, listaValores, false));
		}
		else if (grafico.equalsIgnoreCase(Graficos.LINHA_SERIE_3D.getNome())){
			this.grafico.setGrafico(new GraficoLinhaSerie(titulo, datasFim, nomes, listaValores, true));
		}
		else if (grafico.equalsIgnoreCase(Graficos.LINHA_SERIE_XY.getNome())){
			this.grafico.setGrafico(new GraficoXYLinhaSerie(titulo, datasFim, nomes, listaValores));
		}
		else if (grafico.equalsIgnoreCase(Graficos.TEMPO_SERIE.getNome())){
			this.grafico.setGrafico(new GraficoTempoSerie(titulo, datasFim, nomes, listaValores));
		}
	}

	/** Retorna um <code>Data[]</code> com as datas de inicio e fim da pesquisa
	 * @return um <code>Data[]</code> com as datas de inicio e fim da pesquisa
	 */
	public Data[] getDatasCampoSelecionarDataPesquisa(){
		Data[] datas = new Data[2];
		String datasSeleciondas = (String)campoSelecionarDataPesquisa.getSelectedItem();
		String dataInicio;
		String dataFim;
		int posicaoTraco;
		posicaoTraco = datasSeleciondas.indexOf("-");
		dataInicio = datasSeleciondas.substring(0, posicaoTraco - 1);
		dataFim = datasSeleciondas.substring(posicaoTraco + 2, datasSeleciondas.length());
		
		datas[0] = new Data(dataInicio);
		datas[1] = new Data(dataFim);
		
		return datas;
	}

	/** Retorna um <code>String</code> com o titulo do grafico
	 * @return um <code>String</code> com o titulo do grafico, o titulo é gerado da seguinte forma:<br>
	 * {@code cargo selecionado + "(" + data de inicio + "-" + data de fim + ")"}
	 */
	private String getTituloGrafico(){
		return (String)this.campoSelecionarCargo.getSelectedItem() + " (" + ((String)this.campoSelecionarDataPesquisa.getSelectedItem()).replace('-', 'à') + ")";
	}
	
	/** Retorna um {@code JComboBox<String>} com o campoSelecionarCargo
	 * @return um {@code JComboBox<String>} com o campoSelecionarCargo
	 */
	public JComboBox<String> getCampoSelecionarCargo() {
		return campoSelecionarCargo;
	}
	
	/** Retorna um <code>String</code> com o cargo selecionado
	 * @return um <code>String</code> com o cargo selecionado
	 */
	public String getValorCampoSelecionarCargo(){
		return (String)campoSelecionarCargo.getSelectedItem();
	}
	
	/** Retorna um {@code JComboBox<String>} com o  campoSelecionarDataPesquisa
	 * @return um {@code JComboBox<String>} com o  campoSelecionarDataPesquisa
	 */
	public JComboBox<String> getCampoSelecionarDataPesquisa() {
		return campoSelecionarDataPesquisa;
	}

	/** Retorna um {@code JComboBox<String>} com o  campoSelecionarGrafico
	 * @return um {@code JComboBox<String>} com o  campoSelecionarGrafico
	 */
	public JComboBox<String> getCampoSelecionarGrafico() {
		return campoSelecionarGrafico;
	}

	/** Retorna um <code>JButton</code> com o  botaoGraficoAnterior
	 * @return um <code>JButton</code> com o  botaoGraficoAnterior
	 */
	public JButton getBotaoGraficoAnterior() {
		return botaoGraficoAnterior;
	}

	/** Retorna um <code>JButton</code> com o botaoGraficoPosterior
	 * @return um <code>JButton</code> com o botaoGraficoPosterior
	 */
	public JButton getBotaoGraficoPosterior() {
		return botaoGraficoPosterior;
	}
	
	/** Ativa e desativa o campo de seleção(<code>JComboBox</code>) Grafico. Se houver um cargo selecionado e houver alguma pesquisa o campo grafico é ativado.
	 * 
	 */
	public void changeEnabledCampoSelecionarGrafico(){
		if (this.campoSelecionarCargo.getSelectedIndex() > 0 && this.campoSelecionarDataPesquisa.getSelectedIndex() > 0){
			this.campoSelecionarGrafico.setEnabled(true);
			//this.setCampoSelecionarGrafico(false);
		}
		else if(this.campoSelecionarCargo.getSelectedIndex() > 0 && this.campoSelecionarDataPesquisa.getItemCount() > 1){ //tem alguma pesquisa cadastrada, permite que se acesse os graficos serie
			this.campoSelecionarGrafico.setEnabled(true);
			//this.setCampoSelecionarGrafico(true);
		}
		else{
			this.campoSelecionarGrafico.setEnabled(false);
		}
	}

	/** Seta as dadas no campo de seleção de datas
	 * @param datas {@code List<Data[]>} com as datas das pesquisas
	 */
	public void setValoresCampoSelecionarDataPesquisa(List<Data[]> datas){
		this.campoSelecionarDataPesquisa.removeAllItems();
		this.campoSelecionarDataPesquisa.addItem(TEXTO_SELECIONE_DATA_PESQUISA);
		if (datas != null){
			for(Data[] data : datas){
				this.campoSelecionarDataPesquisa.addItem(data[0].getDataDDMMAAAA() + " - " + data[1].getDataDDMMAAAA());
			}
		}
	}
	
	/** Seta os graficos no campo de selecao de grafico
	 * @param emSerie <code>boolean</code> informando se o grafico é seriado ou não, ou seja, se contem dados de varias pesquisas ou de apenas uma.
	 */
	public void setCampoSelecionarGrafico(Boolean emSerie){
		String itemSelecionado = (String)campoSelecionarGrafico.getSelectedItem();
		campoSelecionarGrafico.removeAllItems();
		campoSelecionarGrafico.addItem(TEXTO_SELECIONE_GRAFICO);
		if (emSerie == null){ //pega todos graficos
			for(Graficos grafico : Graficos.values()){
				if(!grafico.getNome().equalsIgnoreCase(Graficos.BARRA.getNome()) && !grafico.getNome().equalsIgnoreCase(Graficos.BARRA_3D.getNome())){
					campoSelecionarGrafico.addItem(grafico.getNome());
				}
			}
		}
		else{
			for(Graficos grafico : Graficos.values()){
				if (emSerie.equals(grafico.isEmSerie())){ //pega so grafico em serie(considera todas pesquisa) ou nao em serie(usa dados so de uma pesquisa)
					if(!grafico.getNome().equalsIgnoreCase(Graficos.BARRA.getNome()) && !grafico.getNome().equalsIgnoreCase(Graficos.BARRA_3D.getNome())){ //exclui os graficos de barra
						campoSelecionarGrafico.addItem(grafico.getNome());
					}
				}
			}
		}
		campoSelecionarGrafico.setSelectedItem(itemSelecionado);
	}
	
	/** Muda para o grafico anterior no <code>JComboBox</code> caso exista.
	 * 
	 */
	public void setGraficoAnterior(){
		int indiceGraficoSelecionado;
		int indiceMinimo = 0;
		if (this.campoSelecionarGrafico.isEnabled()){
			indiceGraficoSelecionado = this.campoSelecionarGrafico.getSelectedIndex();
			if (indiceGraficoSelecionado == -1){
				indiceGraficoSelecionado = 0;
			}
			if (indiceGraficoSelecionado - 1 >= indiceMinimo){
				this.campoSelecionarGrafico.setSelectedIndex(indiceGraficoSelecionado - 1);
			}
		}
	}
	
	/** Muda para o grafico posterior no <code>JComboBox</code> caso exista.
	 * 
	 */
	public void setGraficoPosterior(){
		int indiceGraficoSelecionado;
		int indiceMaximo;
		if (this.campoSelecionarGrafico.isEnabled()){
			indiceGraficoSelecionado = this.campoSelecionarGrafico.getSelectedIndex();
			indiceMaximo = this.campoSelecionarGrafico.getItemCount();
			if (indiceGraficoSelecionado == -1){
				indiceGraficoSelecionado = 0;
			}
			if (indiceGraficoSelecionado + 1 <= indiceMaximo - 1){
				this.campoSelecionarGrafico.setSelectedIndex(indiceGraficoSelecionado + 1);
			}
		}
	}
	
	/** Remove o grafico da janela
	 * 
	 */
	public void removeGrafico(){
		this.grafico.removeGrafico();
	}

	/** Implementa esse metodo porque a <code>InterfaceJanela</code> obriga, desta forma o metodo apenas retorna <code>true</code>.
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		return true; //nada a validar
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
