package com.arthurassuncao.sistel.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alee.extended.filechooser.SelectionMode;
import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.extended.filechooser.WebFileChooser;
import com.alee.extended.filefilter.DefaultFileFilter;
import com.alee.laf.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.arthurassuncao.sistel.recursos.Recursos;

/** Janela abstrata para todas as janelas do sistema
 * @author Arthur Assunção
 * 
 *
 * @see JFrame
 * @see InterfaceJanela
 */
public abstract class Janela extends JFrame implements InterfaceJanela{

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int largura 	= 800;
	private int altura 		= 600;
	private StringBuilder erros = new StringBuilder(0); //variavel para os erros, esses erros serão gerados no verificaCampos()
	/** <code>String</code> com o texto padrao de Selecione dos <code>JComboBox</code>*/
	public static final String COMBO_BOX_TEXTO_SELECIONE  = "--Selecione--";

	/** <code>String</code> com o endereco relativo do icone das janelas */
	protected static final String ICONE 	= "imagens/urna_48x48x32.png";
	private final Image IMAGEM_ICONE = new ImageIcon(Recursos.getResource(Janela.ICONE)).getImage();

	/** <code>Image</code> com o icone das janelas */
	protected Image icone = new ImageIcon(Recursos.getResource(Janela.ICONE)).getImage();
	/** <code>SystemTray</code> com o systemTray das janelas */
	protected SystemTray systemTray 	= SystemTray.getSystemTray();
	/** <code>JMenuItem</code> com o item restaurar do menuPopUp do minimizar das janelas */
	protected JMenuItem restaurar     		= new JMenuItem("Restaurar");
	/** <code>JMenuItem</code> com o item sair do menuPopUp do minimizar das janelas */
	protected JMenuItem sair      				= new JMenuItem("Sair");
	/** <code>JPopupMenu</code> com o menuPopUp do minimizar das janelas */
	protected JPopupMenu sysPopupMenu = new JPopupMenu();
	/** <code>TrayIcon</code> com o trayIcon do minimizar das janelas */
	protected TrayIcon trayIcon  				= new TrayIcon(icone, "Processo Eleitoral", null);
	/** {@code List<Frame>} com a lista de janelas abertas*/
	protected List<Frame> listaJanelasAbertas = new ArrayList<Frame>();

	/** Construtor com as principais caracteristicas das janelas do sistema, com titulo especifico, o tamanho(largura e altura) são os padrão da janela, 800 e 600, respectivamente
	 * @param titulo <code>String</code> com o titulo da janela
	 * @see Fonte
	 */
	public Janela(String titulo) {
		super();
		this.setTitle(titulo);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(this.largura, this.altura);
		//this.setResizable(false);
		this.setFont(Fonte.FONTE_NORMAL);

		this.setBackground(Color.WHITE);
		this.setIconImage(IMAGEM_ICONE);

		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.adicionarListeners();

		JFrame.setDefaultLookAndFeelDecorated(true);
		this.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				Janela.this.atualizaInterface(true);
			}
		});

		//this.setVisible(true);
	}
	/** Construtor com as principais caracteristicas das janelas do sistema, usando o titulo, largura e altura especificos
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param largura <code>int</code> com a largura da janela
	 * @param altura <code>int</code> com a altura da janela
	 * @see Fonte
	 */
	public Janela(String titulo, int largura, int altura) {
		super();
		this.setTitle(titulo);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.largura = largura;
		this.altura = altura;
		this.setSize(largura, altura);
		//this.setResizable(false);
		this.setFont(Fonte.FONTE_NORMAL);

		this.setBackground(Color.WHITE);
		this.setIconImage(IMAGEM_ICONE);

		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.adicionarListeners();
		//this.setVisible(true);
	}
	/** Construtor com as principais caracteristicas das janelas do sistema, usando o titulo, largura e altura especificos e um <code>JMenuBar</code>
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param largura <code>int</code> com a largura da janela
	 * @param altura <code>int</code> com a altura da janela
	 * @param menuBar <code>JMenuBar</code> com os menus da janela
	 * @see Fonte
	 */
	public Janela(String titulo, int largura, int altura, JMenuBar menuBar) {
		super();
		this.setTitle(titulo);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.largura = largura;
		this.altura = altura;
		this.setSize(largura, altura);
		this.setResizable(false);
		this.setJMenuBar(menuBar);

		this.setBackground(Color.WHITE);
		this.setIconImage(IMAGEM_ICONE); 

		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.adicionarListeners();
		//this.setVisible(true);
	}

	/** Retorna os erros nos campos da janela, esses erros dependendem das regras impostas para cada campo em cada janela.
	 * @return erros <code>String</code> com as mensagens de erro para os erros encontrados no preenchimento dos campos da janela
	 */
	public String getErros() {
		return this.erros.toString();
	}

	/** Apaga as mensagens de erro
	 * 
	 */
	public void removeErros(){
		this.erros.delete(0, this.erros.length()); //apaga o conteudo da variavel erros
		this.erros.setLength(0);
	}

	/** Adiciona uma mensagem de erro
	 * @param erro <code>String</code> com a mensagem de erro para o erro encontrado
	 */
	public void addError(String erro){
		this.erros.append(erro + "\n");
	}

	/** Atualiza a interface da janela
	 * @param rePack <code>boolean</code> com <code>true</code> se as janelas devem chamar o metodo JFrame#pack() e <code>false</code> senao.
	 */
	public void atualizaInterface(boolean rePack){  
		try {  
			SwingUtilities.updateComponentTreeUI(this);
			this.repaint();
			if(rePack){
				this.pack();
			}
		}
		catch(Exception e) {  
			e.printStackTrace();  
		}  
	}

	/** Janela para abrir arquivos
	 * @param componentePai <code>Component</code> sobre o qual esta janela será aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param diretorioCorrente <code>String</code> com o diretorio onde a janela de abrir inicializara, pode ser <code>null</code>
	 * @param opcaoTodosArquivos <code>boolean</code> informando se a opcao(filtro) Todos Arquivos será mostrada ou não
	 * @param nomeFiltro <code>String</code> com o nome do filtro de extensões
	 * @param extensao <code>String...</code> com as extensões usadas no filtro
	 * @return <code>String</code> com o endereço do arquivo selecionado, caso nenhum arquivo seja selecionado, é retornado <code>null</code>
	 * @see  JFileChooser
	 */
	public static String janelaAbrirArquivo(Component componentePai, String titulo, String diretorioCorrente, boolean opcaoTodosArquivos, String nomeFiltro, final String... extensao){
		String arquivoSelecionado = null;
		if(! UIManager.getLookAndFeel().getName().equals(WebLookAndFeel.class.getSimpleName())){
			JFileChooser janelaAbrir = null;
			janelaAbrir = new JFileChooser(titulo);

			janelaAbrir.setAcceptAllFileFilterUsed(false);
			janelaAbrir.setDialogType(JFileChooser.OPEN_DIALOG);
			janelaAbrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
			janelaAbrir.setDialogTitle(titulo);
			janelaAbrir.setFont(new Fonte().getFont());
			janelaAbrir.setFileFilter(new FileNameExtensionFilter(nomeFiltro, extensao));
			janelaAbrir.setAcceptAllFileFilterUsed(opcaoTodosArquivos);

			if(diretorioCorrente != null){
				if (diretorioCorrente.isEmpty()){
					janelaAbrir.setCurrentDirectory(new File("."));
				}
				else{
					janelaAbrir.setCurrentDirectory(new File(diretorioCorrente));
				}
			}

			janelaAbrir.showOpenDialog(componentePai);

			arquivoSelecionado = janelaAbrir.getSelectedFile() != null ? janelaAbrir.getSelectedFile().getPath() : null;
		}
		else{
			WebFileChooser janelaAbrir = null;
			janelaAbrir = new WebFileChooser(null, titulo);
			janelaAbrir.setSelectionMode(SelectionMode.SINGLE_SELECTION );

			DefaultFileFilter filtro = new DefaultFileFilter() {
				@Override
				public boolean accept(File arquivo) {
					String nomeArquivo = arquivo.getName();
					if(arquivo.isFile()){
						int posicaoPonto = nomeArquivo.lastIndexOf(".");
						if(posicaoPonto != -1){
							for(String ext : extensao){
								if(nomeArquivo.substring(posicaoPonto + 1, nomeArquivo.length()).equalsIgnoreCase(ext) ){
									return true;
								}
							}
						}
					}
					return false;
				}
			};
			janelaAbrir.setChooseFilter(filtro);
			//List<DefaultFileFilter> filtros = new ArrayList<DefaultFileFilter>();
			//filtros.add(filtro);
			//janelaAbrir.setAvailableFilters(filtros);
			janelaAbrir.setFont(new Fonte().getFont());
			janelaAbrir.setVisible(true);
			if (janelaAbrir.getResult() == StyleConstants.OK_OPTION ){
				arquivoSelecionado = janelaAbrir.getSelectedFile().getPath();
			}
		}
		return arquivoSelecionado;
	}

	/** Janela para selecionar um diretorio
	 * @param componentePai <code>Component</code> sobre o qual esta janela será aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param diretorioCorrente <code>String</code> com o diretorio onde a janela de abrir inicializara, pode ser <code>null</code>
	 * @return <code>String</code> com o endereço do diretorio selecionado, caso nenhum diretorio seja selecionado, é retornado <code>null</code>
	 * @see  JFileChooser 
	 */
	public static String janelaAbrirDiretorio(Component componentePai, String titulo, String diretorioCorrente){
		String diretorio = null;
		if(! UIManager.getLookAndFeel().getName().equals(WebLookAndFeel.class.getSimpleName())){
			JFileChooser janelaAbrir = null;
			janelaAbrir = new JFileChooser(titulo);

			janelaAbrir.setAcceptAllFileFilterUsed(false);
			janelaAbrir.setDialogType(JFileChooser.OPEN_DIALOG);
			janelaAbrir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			janelaAbrir.setDialogTitle(titulo);
			janelaAbrir.setFont(new Fonte().getFont());

			if(diretorioCorrente != null){
				if (diretorioCorrente.isEmpty()){
					janelaAbrir.setCurrentDirectory(new File("."));
				}
				else{
					janelaAbrir.setCurrentDirectory(new File(diretorioCorrente));
				}
			}

			janelaAbrir.showOpenDialog(componentePai);

			diretorio = janelaAbrir.getSelectedFile() != null ? janelaAbrir.getSelectedFile().getPath() : null;
		}
		else{
			WebDirectoryChooser janelaAbrir = new WebDirectoryChooser(null, titulo);
			janelaAbrir.setFont(new Fonte().getFont());
			janelaAbrir.setVisible(true);

			if (janelaAbrir.getResult() == StyleConstants.OK_OPTION ){
				diretorio = janelaAbrir.getSelectedFolder().getPath();
			}
		}
		return diretorio;
	}

	/** Janela para salvar um arquivo
	 * @param componentePai <code>Component</code> sobre o qual esta janela será aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param diretorioCorrente <code>String</code> com o diretorio onde a janela de abrir inicializara, pode ser <code>null</code>
	 * @param opcaoTodosArquivos <code>boolean</code> informando se a opcao(filtro) Todos Arquivos será mostrada ou não
	 * @param nomeFiltro <code>String</code> com o nome do filtro de extensões
	 * @param extensao <code>String...</code> com as extensões usadas no filtro
	 * @return <code>String</code> com o endereço onde o arquivo será salvo, caso nenhum arquivo seja selecionado, é retornado <code>null</code>
	 * @see  JFileChooser 
	 */
	public static String janelaSalvarArquivo(Component componentePai, String titulo, String diretorioCorrente, boolean opcaoTodosArquivos, String nomeFiltro, final String... extensao){
		String arquivoSelecionado = null;
		if(! UIManager.getLookAndFeel().getName().equals(WebLookAndFeel.class.getSimpleName())){
			JFileChooser janelaSalvar = new JFileChooser(titulo);

			janelaSalvar.setAcceptAllFileFilterUsed(false);
			janelaSalvar.setDialogType(JFileChooser.SAVE_DIALOG);
			janelaSalvar.setFileSelectionMode(JFileChooser.FILES_ONLY);
			janelaSalvar.setDialogTitle(titulo);
			janelaSalvar.setFont(new Fonte().getFont());
			janelaSalvar.setFileFilter(new FileNameExtensionFilter(nomeFiltro, extensao));
			janelaSalvar.setAcceptAllFileFilterUsed(opcaoTodosArquivos);

			if(diretorioCorrente != null){
				if (diretorioCorrente.isEmpty()){
					janelaSalvar.setCurrentDirectory(new File("."));
				}
				else{
					janelaSalvar.setCurrentDirectory(new File(diretorioCorrente));
				}
			}

			janelaSalvar.showSaveDialog(componentePai);

			arquivoSelecionado = janelaSalvar.getSelectedFile() != null ? janelaSalvar.getSelectedFile().getPath() : null;
		}
		else{
			WebFileChooser janelaSalvar = null;
			janelaSalvar = new WebFileChooser(null, titulo);
			janelaSalvar.setSelectionMode(SelectionMode.SINGLE_SELECTION );

			DefaultFileFilter filtro = new DefaultFileFilter() {
				@Override
				public boolean accept(File arquivo) {
					String nomeArquivo = arquivo.getName();
					if(arquivo.isFile()){
						int posicaoPonto = nomeArquivo.lastIndexOf(".");
						if(posicaoPonto != -1){
							for(String ext : extensao){
								if(nomeArquivo.substring(posicaoPonto + 1, nomeArquivo.length()).equalsIgnoreCase(ext) ){
									return true;
								}
							}
						}
					}
					else{
						return true;
					}
					return false;
				}
			};
			janelaSalvar.setChooseFilter(filtro);
			//List<DefaultFileFilter> filtros = new ArrayList<DefaultFileFilter>();
			//filtros.add(filtro);
			//janelaAbrir.setAvailableFilters(filtros);
			janelaSalvar.setFont(new Fonte().getFont());
			janelaSalvar.setVisible(true);
			if (janelaSalvar.getResult() == StyleConstants.OK_OPTION ){
				File arquivo = janelaSalvar.getSelectedFile();
				if(!arquivo.isDirectory()){
					arquivoSelecionado = arquivo.getPath();
				}
				else{
					if(extensao != null && extensao.length > 0){
						//arquivoSelecionado = arquivo.getPath() + "/arquivo." + extensao[0];
						arquivoSelecionado = arquivo.getPath() + "/arquivo";
					}
					else{
						arquivoSelecionado = arquivo.getPath() + "/arquivo";
					}
				}
			}
		}
		return arquivoSelecionado;
	}

	//SYSTRAY
	/** Adiciona o icone da bandeja (SystemTray) à janela. Ao minimizar uma janela todas as janelas são escondidas e é exibido um icone no SystemTray do
	 * Sistema de gerenciamento de janelas do sistema operacional. No icone há dois menus Restaurar e Sair, o primeiro restaura as janelas, já o segundo fecha o programa.
	 * @see SystemTray
	 * @see TrayIcon
	 */
	private final void addSystemTray() {
		//trayIcon.setToolTip(this.getTitle());
		if (SystemTray.isSupported()) {
			this.addEventoTrayIcon();
			this.addItensPrincipaisPopupMenu();
			this.addEventoItensPrincipais();

			this.addItensPopupMenu();
			this.addEventoItens();

			try {
				systemTray.add(trayIcon);
				//setVisible(false);
				this.dispose();
				//this.listaJanelasAbertas.removeAll(this.listaJanelasAbertas); //limpa a lista
				Frame[] janelas = JFrame.getFrames();
				for(Frame janela : janelas){
					if(janela != this){
						if(!janela.getTitle().equalsIgnoreCase("PopupMessageWindow") && !janela.getTitle().equalsIgnoreCase("")){
							if(janela.isVisible()){ //pega so as janelas visiveis
								this.listaJanelasAbertas.add(janela); //guarda as referencias
								janela.setVisible(false);
							}
						}
					}
				}
			}
			catch(AWTException e) {
				JanelaMensagem.mostraMensagemErro(this, "System Tray Não Suportado");
			}

		}
	}

	/** Adiciona o evento do menu popup ao trayicon 
	 * 
	 */
	private final void addEventoTrayIcon(){
		trayIcon.addMouseListener(new MouseAdapter(){
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseReleased(MouseEvent evento){
				if (evento.isPopupTrigger()){
					sysPopupMenu.setLocation(evento.getX(), evento.getY());
					sysPopupMenu.setInvoker(sysPopupMenu);
					sysPopupMenu.setVisible(true);
				}
			}
		});
	}

	//adiciona os itens do menuPopup
	/** Adiciona os itens principais do menuPopup
	 * 
	 */
	private final void addItensPrincipaisPopupMenu(){
		sysPopupMenu.add(restaurar);
		sysPopupMenu.add(sair);
	}

	//adiciona eventos aos itens do menuPopup
	/** Adiciona os eventos dos itens principais
	 * 
	 */
	private final void addEventoItensPrincipais(){
		sair.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				System.exit(0);
			}
		});
		restaurar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evento) {
				SystemTray.getSystemTray().remove(trayIcon);
				Janela.this.setVisible(true);
				Janela.this.setExtendedState(Frame.NORMAL);
				/*Frame[] janelas = JFrame.getFrames();
					for(Frame janela : janelas){
						if(!janela.getTitle().equalsIgnoreCase("PopupMessageWindow") && !janela.getTitle().equalsIgnoreCase("")){ //nao deixa a janela PopupMessageWindow aparecer
							janela.setVisible(true);
						}
						else{
							janela.dispose();
						}
					}*/
				for(Frame janela : Janela.this.listaJanelasAbertas){
					janela.setVisible(true);
				}
				Janela.this.listaJanelasAbertas.removeAll(Janela.this.listaJanelasAbertas); //limpa a lista
			}
		});
	}

	//adiciona outros itens ao popupMenu, as janelas filho podem sobreescreve-lo
	/** Permite adicao de outros itens ao popupMenu
	 * 
	 */
	protected abstract void addItensPopupMenu();

	//adiciona eventos aos outros itens ao popupMenu, as janelas filho podem sobreescreve-lo
	/** Permite adicao de eventos dos itens do popupMenu
	 * 
	 */
	protected abstract  void addEventoItens();

	/** Adiciona tratador de eventos aos componentes da janela e à janela
	 * @see WindowStateListener
	 */
	/** Adiciona os listeners a janela
	 * 
	 */
	private final void adicionarListeners(){
		addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent evento) {
				int estadoAntigo = evento.getOldState();
				int estado = evento.getNewState();

				if (estado == JFrame.ICONIFIED && estadoAntigo == JFrame.NORMAL){ //foi esta minimizada
					addSystemTray();
				}
			}
		});
	}

}
