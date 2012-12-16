package too.trabalho.gui;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import too.trabalho.gui.temas.Temas;
import too.trabalho.recursos.Recursos;

/** Classe que define a interface grafica para o usuario com os modulos do sistema
 * @author Arthur Assunção
 * 
 * @see Janela
 *
 */
public class JanelaPrincipal extends Janela{

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -980970410472120049L;
	/** <code>int</code> com a largura da janela sem considerar a largura após a chamada ao metodo {@link Janela#pack()} */
	public static final int LARGURA = 600;
	/** <code>int</code> com a altura da janela sem considerar a altura após a chamada ao metodo {@link Janela#pack()} */
	public static final int ALTURA 	= 350;
	private final String IMAGEM_FUNDO = "imagens/eleicoes_2012.jpg";

	//Barra de Menu
	private BarraMenuPrincipal barraMenuPrincipal;

	//paineis
	private Painel painelTudo;
	//private Painel painelImagens;
	/*private Painel painelImagemCadastrar;
	private Painel painelImagemPesquisa;
	private Painel painelImagemApuracao;
	private Painel painelImagemVotacao;*/

	private ImageIcon imagemPrincipal;
	//private LabelRotulo labelTitulo;
	private LabelRotulo labelImagem;

	/*private LabelRotulo labelImagemCadastrar;
	private LabelRotulo labelImagemPesquisa;
	private LabelRotulo labelImagemApuracao;
	private LabelRotulo labelImagemVotacao;*/

	/** Cria uma instancia da janela principal do sistema, com o titulo "Processo Eleitoral", largura e altura definidas por constantes e uma barra de menu
	 * @param tema <code>String</code> com o tema que sera colocado no programa
	 * @see JMenuBar
	 */	
	public JanelaPrincipal(String tema) {
		super("Processo Eleitoral", LARGURA, ALTURA, new JMenuBar());
		barraMenuPrincipal =  new BarraMenuPrincipal(this, tema);
		this.setJMenuBar(barraMenuPrincipal);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.iniciaElementos();

		this.addElementos();

		this.addWindowListener(new WindowAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent evento) {
				super.windowClosing(evento);
				JanelaPrincipal.this.encerraDependencias();
			}
		});
		Temas.mudaTema(tema);
		this.setVisible(true);
	}

	/** Instancia os elementos, alem de setar algumas propriedades de alguns componentes
	 * 
	 */
	private void iniciaElementos(){
		//inicializa os paineis
		painelTudo 		= new Painel(new GridLayout());
		//painelImagens 	= new Painel(new GridBagLayout());

		/*painelImagemCadastrar = new Painel();
		painelImagemPesquisa = new Painel();
		painelImagemApuracao = new Painel();
		painelImagemVotacao = new Painel();

		painelImagemCadastrar.setBackground(Color.GRAY);
		painelImagemPesquisa.setBackground(Color.GRAY);
		painelImagemApuracao.setBackground(Color.GRAY);
		painelImagemVotacao.setBackground(Color.GRAY);*/

		//inicializa as imagens e labels
		imagemPrincipal = new ImageIcon(Recursos.getResource(IMAGEM_FUNDO));
		imagemPrincipal.setImage(imagemPrincipal.getImage().getScaledInstance(LARGURA-20, ALTURA-55-20, 100));
		labelImagem 	= new LabelRotulo(imagemPrincipal);

		/*labelImagemCadastrar = new LabelRotulo(new ImageIcon(imagemPrincipal.getImage().getScaledInstance(LARGURA / 4, ALTURA, 100)));
		labelImagemPesquisa = new LabelRotulo(new ImageIcon(imagemPrincipal.getImage().getScaledInstance(LARGURA / 4, ALTURA, 100)));
		labelImagemApuracao = new LabelRotulo(new ImageIcon(imagemPrincipal.getImage().getScaledInstance(LARGURA / 4, ALTURA, 100)));
		labelImagemVotacao = new LabelRotulo(new ImageIcon(imagemPrincipal.getImage().getScaledInstance(LARGURA / 4, ALTURA, 100)));

		labelImagemCadastrar.addMouseListener(new TratadorEventosMouseJanelaPrincipal());
		labelImagemPesquisa.addMouseListener(new TratadorEventosMouseJanelaPrincipal());
		labelImagemApuracao.addMouseListener(new TratadorEventosMouseJanelaPrincipal());
		labelImagemVotacao.addMouseListener(new TratadorEventosMouseJanelaPrincipal());

		//Adiciona ToolTipText
		labelImagemCadastrar.setToolTipText("Clique para cadastrar candidatos");
		labelImagemPesquisa.setToolTipText("Clique para ver as pesquisas");
		labelImagemApuracao.setToolTipText("Clique para ver a apuracao dos votos");
		labelImagemVotacao.setToolTipText("Clique para iniciar a votacao");*/

	}

	/** Adiciona os componentes aos paineis e à janela
	 * 
	 */
	private void addElementos(){
		GridBagConstraints gridBagConstraint = new GridBagConstraints();
		gridBagConstraint.insets = new Insets(10, 10, 10, 10); //espacos pro GridBadLayout 
		gridBagConstraint.fill = GridBagConstraints.NONE;  //preenche toda coluna

		painelTudo.add(labelImagem, gridBagConstraint);

		this.add(painelTudo);
	}

	/** Encerra todos os servicos do sistema
	 * 
	 */
	private void encerraDependencias(){
		System.out.println("Programa Processo Eleitoral finalizado");
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
