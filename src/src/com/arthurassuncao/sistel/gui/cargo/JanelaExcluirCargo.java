package com.arthurassuncao.sistel.gui.cargo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.arthurassuncao.sistel.eventos.cargo.TratadorEventoExcluirCargo;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.LabelRotulo;
import com.arthurassuncao.sistel.gui.Painel;

/** Classe com a interface grafica para exclusão de cargos
 * @author Arthur Assunção
 * 
 * 
 * @see Janela
 *
 */
public class JanelaExcluirCargo extends Janela {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 6926837926340881201L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA = 450;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	= 300;
	
	private Painel painelTotal;
	
	private LabelRotulo labelNomeCargo;
	
	private JTextField campoNomeCargo;
	
	private JButton botaoExcluir;
	
	//Grid
	private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
	/** Cria uma instancia da janela de exclusão de cargos
	 * 
	 */
	public JanelaExcluirCargo(){
		super("Excluir Candidato", LARGURA, ALTURA);
		
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
		
		labelNomeCargo 	= new LabelRotulo("Cargo");
		
		botaoExcluir = new JButton("Exlcluir");
		botaoExcluir.addMouseListener(new TratadorEventoExcluirCargo(this));
		
		botaoExcluir.setFocusable(false);
		
		campoNomeCargo = new JTextField(10);
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		gridBagConstraint.gridx = 0;
		gridBagConstraint.gridy = 0;
		painelTotal.add(labelNomeCargo, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelTotal.add(campoNomeCargo, gridBagConstraint);
		gridBagConstraint.gridx = 2;
		painelTotal.add(botaoExcluir, gridBagConstraint);
		
		this.add(painelTotal);
	}

	/** Verifica se os campos são validos.<br>
	 * Campo Cargo não pode ficar vazio.
	 * @see com.arthurassuncao.sistel.gui.InterfaceJanela#verificaCampos()
	 */
	@Override
	public boolean verificaCampos() {
		if(campoNomeCargo.getText().trim().isEmpty()){
			this.addError("Campo cargo está vazio");
		}
		if(!this.getErros().isEmpty()){
			return false;
		}
		return true;
	}
	
	/** Retorna um <code>String</code> com o valor do campo cargo
	 * @return um <code>String</code> com o valor do campo cargo
	 */
	public String getCargo(){
		return this.campoNomeCargo.getText();
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
