package com.arthurassuncao.sistel.gui.temas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/** Classe para manipular os temas(look and feel) da biblioteca JGoodies
 * @author Arthur Assunção
 * 
 * 
 * @see Temas
 * @see UIManager
 */
public class TemasJGoodies extends Temas implements InterfaceTema{
	private static boolean temasInstalados;
	/** <code>String</code> com o nome do tema */
	public static final String WINDOWS_JGOODIES = "Windows JGoodies" ;
	/** <code>String</code> com o nome do tema */
	public static final String PLASTIC = "Plastic" ;
	/** <code>String</code> com o nome do tema */
	public static final String PLASTIC_3D = "Plastic 3D" ;
	/** <code>String</code> com o nome do tema */
	public static final String PLASTIC_XP = "Plastic XP" ;
	
	private List<LookAndFeelInfo> listaTemas;
	private JMenu menu;

	/** Instala os temas da biblioteca JGoodies
	 * 
	 */
	public static void instalaTemas(){
		if (!temasInstalados){
			UIManager.installLookAndFeel(WINDOWS_JGOODIES, "com.jgoodies.looks.windows.WindowsLookAndFeel");
	
			UIManager.installLookAndFeel(PLASTIC, "com.jgoodies.looks.plastic.PlasticLookAndFeel");
	
			UIManager.installLookAndFeel(PLASTIC_3D, "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
	
			UIManager.installLookAndFeel(PLASTIC_XP, "com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
			
			temasInstalados = true;
		}
	}
	
	/** Cria uma instancia da classe
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public TemasJGoodies(ButtonGroup grupoBotoes, String temaAtual){
		this.criaListaTemas();
		this.createMenuTemas(grupoBotoes, temaAtual);
	}
	
	/** Cria um menu do tipo <code>JMenu</code> com todos os temas do JGoodies
	 * @param grupoBotoes <code>ButtonGroup</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o tema atual
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	@Override
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual){
		this.menu = new JMenu("JGoodies");
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
		TemasJGoodies.instalaTemas();
		this.listaTemas = new ArrayList<UIManager.LookAndFeelInfo>();
		this.listaTemas.add(new LookAndFeelInfo(WINDOWS_JGOODIES, "com.jgoodies.looks.windows.WindowsLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(PLASTIC, "com.jgoodies.looks.plastic.PlasticLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(PLASTIC_3D, "com.jgoodies.looks.plastic.Plastic3DLookAndFeel"));
		this.listaTemas.add(new LookAndFeelInfo(PLASTIC_XP, "com.jgoodies.looks.plastic.PlasticXPLookAndFeel"));		
	}
	
	@Override
	public JMenu getMenu(){
		return this.menu;
	}

}
