package too.trabalho.gui.apuracao;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

import too.trabalho.classes.Data;
import too.trabalho.classes.votacao.Votacao;
import too.trabalho.classes.votacao.Votacao.NomeCandidatoVotos;
import too.trabalho.eventos.apuracao.TratadorEventoItemApuracao;
import too.trabalho.eventos.apuracao.TratadorEventoMouseApuracao;
import too.trabalho.gui.Janela;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.gui.Painel;
import too.trabalho.gui.graficos.Grafico;
import too.trabalho.gui.graficos.Grafico.NomeValor;
import too.trabalho.gui.graficos.GraficoBarra;

/** A classe <code>JanelaApuracao</code> cria uma GUI para exibir os dados da apuracao
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 */
public class JanelaApuracao extends Janela {
	
	/**@serial
	 * 
	 */
	private static final long serialVersionUID = 8822733035812778200L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  	= 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 	= 400;
	/** <code>String</code> com o texto de "selecione" do <code>JComboBox</code> cargo*/
	public static final String TEXTO_SELECIONE_CARGO = "--Cargo--";
	
	private Votacao votacao;
	
	private Grafico grafico;
	
	//Paineis
	private Painel painelTotal;
	private Painel painelNorte;
	private Painel painelSul;
	private Painel painelGrafico;
	
	//Campos
	JComboBox<String> campoSelecionarCargo;
	
	//Botoes
	JButton botaoCargoAnterior;
	JButton botaoCargoPosterior;
	
	//Grid
    private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
    /** Cria uma instancia da janela de apuracao
     * @param votacao <code>Votacao</code> com a votacao que será apurada
	 * 
	 */
	public JanelaApuracao(Votacao votacao){
		super("Apuracao", LARGURA, ALTURA);
		
		this.votacao = votacao;
		
		if(this.votacao.getData().compareTo(new Data()) > 0){
			JanelaMensagem.mostraMensagem(this, "Apuracao", "Votacao ainda não foi realizada");
			this.dispose();
		}
		else{
			this.iniciaElementos();
			
			this.addElementos();
			
			this.pack();
			
			this.setLocationRelativeTo(null);
			
			this.setVisible(true);
		}
		
		
	}
	
	/** Instancia os elementos e adiciona dados ao <code>JComboBox</code>, alem de setar algumas propriedades de alguns componentes e adicionar eventos aos elementos
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
		
		//inicia os botoes
		botaoCargoAnterior = new JButton("<");
		botaoCargoPosterior = new JButton(">");
		
		botaoCargoAnterior.setFocusable(false);
		botaoCargoPosterior.setFocusable(false);
		
		//inicia o grafico
		this.grafico = new Grafico();
		
		//inicia o Grid
		gridBagConstraint.insets = new Insets(3, 0, 3, 0); //espacos pro GridBadLayout
		gridBagConstraint.fill = GridBagConstraints.NONE;  //preenche toda coluna
		
		//Adiciona dados nos campos selecionar
		campoSelecionarCargo.addItem(TEXTO_SELECIONE_CARGO);
		
		//adiciona dados no campoSelecionarCargo
		List<String> cargos = votacao.getCargos();
		for(String cargo : cargos){
			campoSelecionarCargo.addItem(cargo);
		}
		
		//Adiciona ToolTipsText
		campoSelecionarCargo.setToolTipText("Selecione um cargo");
		botaoCargoAnterior.setToolTipText("Anterior");
		botaoCargoPosterior.setToolTipText("Proximo");
		
		//Adiciona os tratadores de eventos
		campoSelecionarCargo.addItemListener(new TratadorEventoItemApuracao(this));
		
		botaoCargoAnterior.addMouseListener(new TratadorEventoMouseApuracao(this));
		botaoCargoPosterior.addMouseListener(new TratadorEventoMouseApuracao(this));
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		int linha = 0;
		
		//Adiciona ao painelNorte
		linha = 0;
		
		//Adiciona ao painelSul
		linha = 0;
		int coluna = 0;
		gridBagConstraint.gridx = coluna++;
		gridBagConstraint.gridy = linha;
		painelSul.add(botaoCargoAnterior, gridBagConstraint);
		gridBagConstraint.gridx = coluna++;
		gridBagConstraint.gridy = linha;
		painelSul.add(campoSelecionarCargo, gridBagConstraint);
		gridBagConstraint.gridx = coluna++;
		gridBagConstraint.gridy = linha;
		painelSul.add(botaoCargoPosterior, gridBagConstraint);
		
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
	
	/** Muda para o cargo anterior no <code>JComboBox</code> caso exista.
	 * 
	 */
	public void setCargoAnterior(){
		int indiceCargoSelecionado;
		int indiceMinimo = 0;
		indiceCargoSelecionado = this.campoSelecionarCargo.getSelectedIndex();
		if (indiceCargoSelecionado == -1){
			indiceCargoSelecionado = 0;
		}
		if (indiceCargoSelecionado - 1 >= indiceMinimo){
			this.campoSelecionarCargo.setSelectedIndex(indiceCargoSelecionado - 1);
		}
	}
	
	/** Muda para o cargo posterior no <code>JComboBox</code> caso exista.
	 * 
	 */
	public void setCargoPosterior(){
		int indiceCargoSelecionado;
		int indiceMaximo;
		indiceCargoSelecionado = this.campoSelecionarCargo.getSelectedIndex();
		indiceMaximo = this.campoSelecionarCargo.getItemCount();
		if (indiceCargoSelecionado == -1){
			indiceCargoSelecionado = 0;
		}
		if (indiceCargoSelecionado + 1 <= indiceMaximo - 1){
			this.campoSelecionarCargo.setSelectedIndex(indiceCargoSelecionado + 1);
		}
	}
	
	/** Muda os dados do grafico da janela
	 * @param listaNomesValores {@code List<NomeCandidatoVotos>} com os nomes dos candidatos e seus respectivos numeros de votos
	 */
	public void setDadosGrafico(List<NomeCandidatoVotos> listaNomesValores){
		//String grafico = Graficos.BARRA_3D.getNome(); 
		List<NomeValor> nomesValores = new ArrayList<NomeValor>();
		for(NomeCandidatoVotos nomesCandidatosVotos : listaNomesValores){
			nomesValores.add(new NomeValor(nomesCandidatosVotos.getNomeCandidato(), nomesCandidatosVotos.getNumeroVotos()));
		}
		String titulo;
		titulo = this.getTituloGrafico();
		if(nomesValores.size() > 0){
			this.grafico.setGrafico(new GraficoBarra(titulo, nomesValores, true));
		}
		else{
			JanelaMensagem.mostraMensagem(this, "Erro", "Está votacao não teve eleitores");
			this.dispose();
		}
		/*if (grafico.equalsIgnoreCase(Graficos.BARRA.getNome())){
			this.grafico.setGrafico(new GraficoBarra(titulo, nomesValores, false));
		}
		else if (grafico.equalsIgnoreCase(Graficos.BARRA_3D.getNome())){
			this.grafico.setGrafico(new GraficoBarra(titulo, nomesValores, true));
		}*/
	}
	
	/** Remove o grafico da janela
	 * 
	 */
	public void removeGrafico(){
		this.grafico.removeGrafico();
	}
	
	/** Retorna um <code>String</code> com o titulo do grafico
	 * @return um <code>String</code> com o titulo do grafico, o titulo é gerado da seguinte forma:<br>
	 * {@code cargo selecionado + "(" + data da votacao + ")"}
	 */
	private String getTituloGrafico(){
		return (String)this.campoSelecionarCargo.getSelectedItem() + " (Votação do dia " + votacao.getData().getDataDDMMAAAA() + ")";
	}
	
	/** Retorna um <code>JButton</code> com o botaoCargoAnterior
	 * @return um <code>JButton</code> com o botaoCargoAnterior
	 */
	public JButton getBotaoCargoAnterior() {
		return botaoCargoAnterior;
	}

	/** Retorna um <code>JButton</code> com o botaoCargoPosterior
	 * @return um <code>JButton</code> com o botaoCargoPosterior
	 */
	public JButton getBotaoCargoPosterior() {
		return botaoCargoPosterior;
	}
	
	/** Retorna um <code>Data</code> com a data da votacao
	 * @return <code>Data</code> com a data da votacao
	 */
	public Data getDataVotacao(){
		return this.votacao.getData();
	}
	

	/** Retorna um {@code JComboBox<String>} com o campoSelecionarCargo
	 * @return um {@code JComboBox<String>} com o campoSelecionarCargo
	 */
	public JComboBox<String> getCampoSelecionarCargo() {
		return campoSelecionarCargo;
	}

	/** Implementa esse metodo porque a <code>InterfaceJanela</code> obriga, desta forma o metodo apenas retorna <code>true</code>.
	 * @see too.trabalho.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		return true;
	}

	/* (non-Javadoc)
	 * @see too.trabalho.gui.Janela#addItensPopupMenu()
	 */
	@Override
	protected void addItensPopupMenu() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see too.trabalho.gui.Janela#addEventoItens()
	 */
	@Override
	protected void addEventoItens() {
		// TODO Auto-generated method stub
		
	}

}
