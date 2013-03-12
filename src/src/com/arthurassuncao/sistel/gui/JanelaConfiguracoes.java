package com.arthurassuncao.sistel.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import com.arthurassuncao.sistel.eventos.TratadorEventosMouseConfiguracoes;

/** Janela com as configuracoes do sistema
 * @author Arthur Assunção
 * 
 *
 */
public class JanelaConfiguracoes extends Janela {
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 317331445150849351L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA = 450;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	= 300;
	
	//paineis
	private Painel painelTotal;
	private Painel painelDados;
	
	//labels
	//private LabelRotulo labelTitulo;
	private LabelRotulo labelSenha;
	
	//campos e botoes
	private JButton botaoMostrarBancoDeDados;
	private JPasswordField campoSenha;
	
	//Grid
	private GridBagConstraints gridBagConstraint = new GridBagConstraints();
	
	/** Cria uma instancia da janela de configuracoes do sistema
	 * 
	 */
	public JanelaConfiguracoes(){
		super("Configuracoes", LARGURA, ALTURA);
		
		this.iniciaElementos();
		
		this.addElementos();
		
		this.pack();
		
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	/** Instancia os elementos, alem de setar algumas propriedades de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		//cria os paineis
		painelTotal = new Painel();
		painelDados = new Painel(new GridBagLayout());
		
		//Grid
		gridBagConstraint.insets = new Insets(5, 10, 10, 10); //espacos pro GridBadLayout 
		gridBagConstraint.fill = GridBagConstraints.BOTH;  //preenche toda coluna
		
		//Instancia os labels
		//labelTitulo = new LabelRotulo("Configurações");
		//labelTitulo.setFont(Fonte.FONTE_TITULO);
		labelSenha = new LabelRotulo("Senha: ");
		
		//Instancia os campos
		botaoMostrarBancoDeDados = new JButton("Abrir BD");
		botaoMostrarBancoDeDados.addMouseListener(new TratadorEventosMouseConfiguracoes(this));
		botaoMostrarBancoDeDados.setFocusable(false);
		campoSenha = new JPasswordField(10);
	}
	
	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		//adiciona campos ao painelDados
		int linha = 0;
		
		gridBagConstraint.gridy = linha++;
		gridBagConstraint.gridx = 0;
		painelDados.add(labelSenha, gridBagConstraint);
		gridBagConstraint.gridx = 1;
		painelDados.add(campoSenha, gridBagConstraint);
		gridBagConstraint.gridx = 3;
		painelDados.add(botaoMostrarBancoDeDados, gridBagConstraint);
		
		//adiciona componentes ao painelTotal
		//painelTotal.add(labelTitulo, BorderLayout.NORTH);
		painelTotal.add(painelDados);
		
		//adiciona o painelTotal a janela
		this.add(painelTotal);
	}

	/** Retorna um <code>JPasswordField</code> onde a senha é digitada 
	 * @return campoSenha um campo <code>JPasswordField</code> onde a senha é digitada 
	 */
	public JPasswordField getCampoSenha() {
		return campoSenha;
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
