package too.trabalho.gui.candidato;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;

import too.trabalho.classes.candidato.Candidato;
import too.trabalho.gui.Fonte;
import too.trabalho.gui.Janela;
import too.trabalho.gui.LabelRotulo;
import too.trabalho.gui.Painel;
import too.trabalho.recursos.Recursos;

/** A classe <code>JanelaExibirDadosCandidato</code> cria uma GUI para exibir os dados de um candidato 
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 *
 */
public class JanelaExibirDadosCandidato extends Janela {

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -1435202103739270064L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA  = 500;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	 = 300;
	@SuppressWarnings("unused")
	private String enderecoFotoCandidato = Candidato.FOTO_PADRAO;
	
	//paineis
	private Painel painelTotal;
	private Painel painelNorte;
	private Painel painelSul;
	private Painel painelDados;
	private Painel painelImagem;
	private Painel painelCentro;
	
	//Labels
	private LabelRotulo labelTitulo;
	private LabelRotulo labelNome;
	private LabelRotulo labelCargo;
	private LabelRotulo labelPartido;
	private LabelRotulo labelNumero;
	private LabelRotulo labelFotoCandidato;
	
	//Labels com dados do candidato
	private LabelRotulo campoNome;
	private LabelRotulo campoCargo;
	private LabelRotulo campoPartido;
	private LabelRotulo campoNumero;
	
	//Imagens
	//private ImageIcon fotoCandidato;
	
	//Grid
    private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
	
	/** Cria a janela com os dados do candidato passado como parametro
	 * @param candidato <code>Candidato</code> com os dados do candidato
	 */
	public JanelaExibirDadosCandidato(Candidato candidato) {
		super("Dados Candidato", LARGURA, ALTURA);
		iniciaElementos();
		
		addDadosCandidato(candidato);
		
		addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos e adiciona dados aos <code>JComboBox</code>, alem de setar algumas propriedades de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		painelTotal 		= new Painel(new GridBagLayout());
		painelNorte 		= new Painel();
		painelSul 			= new Painel(new GridBagLayout());
		painelDados 		= new Painel(new GridBagLayout());
		painelImagem	= new Painel();
		painelCentro 	 	= new Painel(new GridBagLayout());
		
		//Grid
		gridBagConstraint.insets = new Insets(3, 0, 3, 0); //espacos pro GridBadLayout
		gridBagConstraint.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		//inicializa os labels
		Font fonteDados = new Fonte(13.0F).getFont();
		labelTitulo 		= new LabelRotulo();
		labelTitulo.setFont(Fonte.FONTE_TITULO);
		labelNome 			= new LabelRotulo(String.format("%-20s%-15s", "","Nome: "));
		labelNome.setFont(fonteDados);
		labelCargo 			= new LabelRotulo(String.format("%-20s%-15s", "","Cargo: "));
		labelCargo.setFont(fonteDados);
		labelPartido 		= new LabelRotulo(String.format("%-20s%-15s", "", "Partido: "));
		labelPartido.setFont(fonteDados);
		labelNumero 		= new LabelRotulo(String.format("%-20s%-15s", "", "Numero: "));
		labelNumero.setFont(fonteDados);
		labelFotoCandidato 	= new LabelRotulo();
		
		//inicializa os labels com dados do candidato
		campoNome 		= new LabelRotulo();
		campoCargo 		= new LabelRotulo();
		campoPartido 	= new LabelRotulo();
		campoNumero 	= new LabelRotulo();
		
		campoNome.setFont(fonteDados);
		campoCargo.setFont(fonteDados);
		campoPartido.setFont(fonteDados);
		campoNumero.setFont(fonteDados);
		
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
		painelDados.add(campoCargo, gridBagConstraint);
		
		//Partido
		gridBagConstraint.gridx = 1;
		gridBagConstraint.gridy = linha++;
		painelDados.add(labelPartido, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelDados.add(campoPartido, gridBagConstraint);
		
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
	
	/**Adiciona os dados do candidato nos campos da janela
	 * @param candidato <code>Candidato</code> com os dados do candidato que será exibido
	 */
	private void addDadosCandidato(Candidato candidato){
		this.campoNome.setText(candidato.getNome());
		this.campoNumero.setText(String.valueOf(candidato.getNumeroCandidato()));
		this.campoCargo.setText(candidato.getCargo());
		this.campoPartido.setText(candidato.getPartidoPolitico().getSigla());
		this.setFotoCandidato(candidato.getEnderecoFoto());
		this.labelTitulo.setText(candidato.getNome());
	}
	
	/** Seta a imagem do candidato
	 * @param enderecoFotoCandidato <code>String</code> com o endereço da foto do candidato
	 */
	private void setEnderecoFotoCandidato(String enderecoFotoCandidato) {
		this.enderecoFotoCandidato = enderecoFotoCandidato;
	}
	
	
	/** Coloca a imagem passada como parametro no janela
	 * @param foto <code>String</code> com o caminho da imagem
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
