package too.trabalho.gui.cargo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;

import too.trabalho.eventos.FixedLengthDocument;
import too.trabalho.eventos.FixedLengthWithNumerosPositivosDocument;
import too.trabalho.eventos.cargo.TratadorEventoMouseCadastrarCargo;
import too.trabalho.gui.Janela;
import too.trabalho.gui.LabelRotulo;
import too.trabalho.gui.Painel;
import too.trabalho.persistencia.BDEleicoes;

/** Classe com a interface grafica para cadastro de cargos
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 *
 */
public class JanelaCadastrarCargo extends Janela {
	
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
	private LabelRotulo labelNomeCargo;
	private LabelRotulo labelNumeroDigitos;
	
	//Campos
	private JTextField campoNomeCargo;
	private JTextField campoNumeroDigitos;
	
	//text documents
	private FixedLengthDocument documentCampoNome 	= new FixedLengthDocument(BDEleicoes.TAMANHO_CARGO_NOME);
	private FixedLengthWithNumerosPositivosDocument documentCampoNumeroDigitos  = new FixedLengthWithNumerosPositivosDocument(BDEleicoes.TAMANHO_CARGO_NUMERO_DIGITOS);
	
	//Botoes
	private JButton botaoLimparCampos;
	private JButton botaoSalvar;
	
	//Grid
    private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
    /** Cria uma instancia da janela de cadastro de cargos
	 * 
	 */
	public JanelaCadastrarCargo(){
		super("Cadastrar Cargo", LARGURA, ALTURA);
		
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
		painelTotal 	= new Painel(new GridBagLayout());
		painelNorte 	= new Painel();
		painelSul   	= new Painel();
		painelDados 	= new Painel(new GridBagLayout());
		
		//Grid
		gridBagConstraint.insets = new Insets(5, 10, 10, 10); //espacos pro GridBadLayout 
		gridBagConstraint.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		//inicia labels 
		labelNomeCargo 			= new LabelRotulo("Cargo: ");
		labelNumeroDigitos  = new LabelRotulo("Numero Digitos: ");
		
		//inicia campos
		campoNomeCargo = new JTextField(numeroColunasCamposTexto);
		campoNomeCargo.setDocument(documentCampoNome);
		campoNomeCargo.setToolTipText("Digite o cargo");
		campoNumeroDigitos = new JTextField();
		campoNumeroDigitos.setToolTipText("Digite o numero de digitos do candidato deste cargo");
		campoNumeroDigitos.setDocument(documentCampoNumeroDigitos);
		
		//Inicializa os Botoes
		botaoLimparCampos = new JButton("Limpar");
		botaoLimparCampos.addMouseListener(new TratadorEventoMouseCadastrarCargo(this));
		botaoSalvar 	  = new JButton("Salvar");
		botaoSalvar.addMouseListener(new TratadorEventoMouseCadastrarCargo(this));
		
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
		painelDados.add(labelNomeCargo, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNomeCargo, gridBagConstraint);
		
		//adiciona ao painelSul
		painelSul.add(botaoLimparCampos);
		painelSul.add(new Painel());
		painelSul.add(botaoSalvar);
		
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 1;
		painelDados.add(labelNumeroDigitos, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoNumeroDigitos, gridBagConstraint);
		
		
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
		this.pack();
	}
	/** Limpa os campos da janela. Os campos de texto ficam vazios.
	 * 
	 */
	public void limpaCamposJanela(){
		this.campoNomeCargo.setText("");
		this.campoNumeroDigitos.setText("");
	}

	/** Verifica se os campos são validos.<br>
	 * Os campos cargo e numero de digitos não podem ficar vazios e o numero de digitos deve ser menor que o tamanho definido no banco de dados, veja  
	 * {@link BDEleicoes#TAMANHO_CANDIDATO_NUMERO}
	 * @see too.trabalho.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		if(campoNomeCargo.getText().trim().isEmpty()){
			this.addError("Campo cargo está vazio");
		}
		if(campoNumeroDigitos.getText().trim().isEmpty()){
			this.addError("Campo número digitos está vazio");
		}
		else{
			try{
				int numDigitos = Integer.parseInt(campoNumeroDigitos.getText());
				if (numDigitos <= 1){
					this.addError("Número de digitos deve ser maior que 1");
				}
				if (numDigitos > BDEleicoes.TAMANHO_CANDIDATO_NUMERO){
					this.addError("Número de digitos deve ser menor ou igual a " + BDEleicoes.TAMANHO_CANDIDATO_NUMERO);
				}
			}
			catch(NumberFormatException e){
				//e.printStackTrace();
				this.addError("Campo número deve conter apenas números");
			}
		}
		if(!this.getErros().isEmpty()){
			return false;
		}
		return true;
	}

	/** Retorna um <code>String</code> com o valor do campo cargo
	 * @return um <code>String</code> com o valor do campo cargo
	 */
	public String getValorCampoNomeCargo() {
		return campoNomeCargo.getText();
	}

	/** Retorna um <code>String</code> com o valor do campo numero de digitos
	 * @return um <code>String</code> com o valor do campo numero de digitos
	 */
	public String getValorCampoNumeroDigitos() {
		return campoNumeroDigitos.getText();
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
