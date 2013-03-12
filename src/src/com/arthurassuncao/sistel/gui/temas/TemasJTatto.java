package com.arthurassuncao.sistel.gui.temas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/** Classe para manipular os temas(look and feel) da biblioteca JTatto
 * @author Arthur Assunção
 * 
 * 
 * @see Temas
 * @see UIManager
 */
public class TemasJTatto extends Temas implements InterfaceTema{
	//private static final String nomeProgramaEmpresa = "Eleicao";
	//private static final String corTema = "Default";
	private static boolean temasInstalados;
	/** <code>String</code> com o nome do tema */
	public static final String ACRYL = "Acryl" ;
	/** <code>String</code> com o nome do tema */
	public static final String AERO = "Aero" ;
	/** <code>String</code> com o nome do tema */
	public static final String ALUMINIUM = "Aluminium" ;
	/** <code>String</code> com o nome do tema */
	public static final String BERNSTEIN = "Bernstein" ;
	/** <code>String</code> com o nome do tema */
	public static final String FAST = "Fast" ;
	/** <code>String</code> com o nome do tema */
	public static final String GRAPHITE = "Graphite" ;
	/** <code>String</code> com o nome do tema */
	public static final String HIFI = "HiFi" ;
	/** <code>String</code> com o nome do tema */
	public static final String LUNA = "Luna" ;
	/** <code>String</code> com o nome do tema */
	public static final String MCWIN = "McWin" ;
	/** <code>String</code> com o nome do tema */
	public static final String MINT = "Mint" ;
	/** <code>String</code> com o nome do tema */
	public static final String NOIRE = "Noire" ;
	/** <code>String</code> com o nome do tema */
	public static final String SMART = "Smart" ;
	/** <code>String</code> com o nome do tema */
	public static final String TEXTURE = "Texture" ;
	
	private List<LookAndFeelInfo> listaTemas;
	private JMenu menu;

	//UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
	/** Instala os temas da biblioteca JTatto
	 * 
	 */
	public static void instalaTemas(){
		if (!temasInstalados){
			//Properties propriedades = new Properties();
			//propriedades.put("logoString", nomeProgramaEmpresa);

			//LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
			//UIDefaults uiDefault = UIManager.getLookAndFeelDefaults();

			//AcrylLookAndFeel.setTheme("Default", "", "nomeProgramaEmpresa");
			//AcrylLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(ACRYL, "com.jtattoo.plaf.acryl.AcrylLookAndFeel");

			//AeroLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(AERO, "com.jtattoo.plaf.aero.AeroLookAndFeel");

			//AluminiumLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(ALUMINIUM, "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");

			//BernsteinLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(BERNSTEIN, "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");

			//FastLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(FAST, "com.jtattoo.plaf.fast.FastLookAndFeel");

			//GraphiteLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(GRAPHITE, "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");

			//HiFiLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(HIFI, "com.jtattoo.plaf.hifi.HiFiLookAndFeel");

			//LunaLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(LUNA, "com.jtattoo.plaf.luna.LunaLookAndFeel");

			//McWinLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(MCWIN, "com.jtattoo.plaf.mcwin.McWinLookAndFeel");

			//MintLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(MINT, "com.jtattoo.plaf.mint.MintLookAndFeel");

			//NoireLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(NOIRE, "com.jtattoo.plaf.noire.NoireLookAndFeel");

			//SmartLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(SMART, "com.jtattoo.plaf.smart.SmartLookAndFeel");

			//TextureLookAndFeel.setTheme(corTema, "", nomeProgramaEmpresa);
			UIManager.installLookAndFeel(TEXTURE, "com.jtattoo.plaf.texture.TextureLookAndFeel");

			//UIManager.setLookAndFeel(lookAndFeel);
			//Temas.unloadDefaults(Temas.getKeysAndValues(UIManager.getLookAndFeelDefaults()));
			//Temas.loadDefaults(Temas.getKeysAndValues(uiDefault));
			//Temas.repinta();
		}

	}
	
	/** Cria uma instancia da classe
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public TemasJTatto(ButtonGroup grupoBotoes, String temaAtual){
		this.criaListaTemas();
		this.createMenuTemas(grupoBotoes, temaAtual);
	}
	
	/** Cria um menu do tipo <code>JMenu</code> com todos os temas do JTatto
	 * @param grupoBotoes <code>ButtonGroup</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o tema atual
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	@Override
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual){
		this.menu = new JMenu("JTatto");
		for (LookAndFeelInfo tema : this.listaTemas){
			JMenuItem item = Temas.createRadioMenuItem(tema.getName());
			item.addActionListener(new ItemMenuThemeListener(tema.getName()));
			if (tema.getName().equals(temaAtual)){
				item.setSelected(true);
				System.out.println("Tema usado: " + tema.getName());
			}
			grupoBotoes.add(item);
			this.menu.add(item);
		}
		return this.menu;
	}
	
	@Override
	public void criaListaTemas(){
		TemasJTatto.instalaTemas();
		this.listaTemas = new ArrayList<UIManager.LookAndFeelInfo>();
		this.listaTemas.add(new LookAndFeelInfo(ACRYL, "com.jtattoo.plaf.acryl.AcrylLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(AERO, "com.jtattoo.plaf.aero.AeroLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(ALUMINIUM, "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(BERNSTEIN, "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(FAST, "com.jtattoo.plaf.fast.FastLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(GRAPHITE, "com.jtattoo.plaf.graphite.GraphiteLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(HIFI, "com.jtattoo.plaf.hifi.HiFiLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(LUNA, "com.jtattoo.plaf.luna.LunaLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(MCWIN, "com.jtattoo.plaf.mcwin.McWinLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(MINT, "com.jtattoo.plaf.mint.MintLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(NOIRE, "com.jtattoo.plaf.noire.NoireLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(SMART, "com.jtattoo.plaf.smart.SmartLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(TEXTURE, "com.jtattoo.plaf.texture.TextureLookAndFeel"));		
	}
	
	@Override
	public JMenu getMenu(){
		return this.menu;
	}
	

}
