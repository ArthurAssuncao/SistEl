/**
 * 
 */
package too.trabalho.gui.partido;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;

import too.trabalho.eventos.FixedLengthWithNumerosPositivosDocument;
import too.trabalho.eventos.partido.TratadorEventoExcluirPartido;
import too.trabalho.gui.Janela;
import too.trabalho.gui.LabelRotulo;
import too.trabalho.gui.Painel;
import too.trabalho.persistencia.BDEleicoes;

/** Classe com a interface grafica para cadastro de partidos
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 * 
 */
public class JanelaExcluirPartido extends Janela {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -7520381561339759102L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA = 450;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	= 300;
	
	private Painel painelTotal;
	
	private LabelRotulo labelNumeroPartido;
	
	private JTextField campoNumeroPartido;
	
	private JButton botaoExcluir;
	
	//Grid
	private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
	//text documents
	private FixedLengthWithNumerosPositivosDocument documentCampoNumero   = new FixedLengthWithNumerosPositivosDocument(BDEleicoes.TAMANHO_PARTIDO_NUMERO);
	
	/** Cria uma instancia da janela de exclusão de partidos
   	 * 
   	 */
	public JanelaExcluirPartido(){
		super("Excluir Partido", LARGURA, ALTURA);
		
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
		painelTotal 			= new Painel(new GridBagLayout());
		
		//Grid
		gridBagConstraint.insets = new Insets(5, 10, 10, 10); //espacos pro GridBadLayout
		gridBagConstraint.fill 		= GridBagConstraints.BOTH;  //preenche toda coluna
		
		labelNumeroPartido 	= new LabelRotulo("Numero: ");
		
		botaoExcluir = new JButton("Exlcluir");
		botaoExcluir.addMouseListener(new TratadorEventoExcluirPartido(this));
		
		botaoExcluir.setFocusable(false);
		
		campoNumeroPartido = new JTextField(10);
		campoNumeroPartido.setToolTipText("Digite o numero do partido");
		campoNumeroPartido.setDocument(documentCampoNumero);
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		painelTotal.add(labelNumeroPartido, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelTotal.add(campoNumeroPartido, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelTotal.add(botaoExcluir, gridBagConstraint);
		
		this.add(painelTotal);
	}
	
	/** Retorna um <code>String</code> com o valor do campo Numero
	 * @return um <code>String</code> com o valor do campo Numero
	 */
	public String getValorCampoNumero() {
		return campoNumeroPartido.getText();
	}
	
	/** Limpa os campos da janela. Os campos de texto ficam vazios.
	 * 
	 */
	public void limpaCampos(){
		this.campoNumeroPartido.setText("");
	}

	/** Verifica se os campos são validos. <br>
	 * O campo numero não pode ficar em vazio, caso não esteja vazio, o numero informado deve ter 2 digitos.
	 * @see too.trabalho.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		if(campoNumeroPartido.getText().trim().isEmpty()){
			this.addError("Campo número está vazio");
		}
		else{
			try{
				Integer.parseInt(campoNumeroPartido.getText());
			}
			catch(NumberFormatException e){
				//e.printStackTrace();
				this.addError("Campo número deve conter apenas números");
			}
		}
		if(campoNumeroPartido.getText().trim().length() != 2){
			this.addError("Campo número deve ter 2 digitos");
		}
		if(!this.getErros().isEmpty()){
			return false;
		}
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
