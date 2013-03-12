package com.arthurassuncao.sistel.gui.temas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.alee.laf.WebLookAndFeel;

/** Classe para manipular os temas(look and feel) da biblioteca WebLookAndFeel
 * @author Arthur Assunção
 * 
 * 
 * @see Temas
 * @see UIManager
 */
public class TemasWebLookAndFeel extends Temas implements InterfaceTema{
	private static boolean temasInstalados;
	/** <code>String</code> com o nome do tema */
	public static final String WEB_LOOK_AND_FEEL = WebLookAndFeel.class.getSimpleName(); //WebLookAndFeel.class.getSimpleName() == "WebLookAndFeel"

	private List<LookAndFeelInfo> listaTemas;
	private JMenu menu;
	
	/** Cria uma instancia da classe
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public TemasWebLookAndFeel(ButtonGroup grupoBotoes, String temaAtual){
		this.criaListaTemas();
		this.createMenuTemas(grupoBotoes, temaAtual);
	}
	
	/** Instala os temas da biblioteca WebLookAndFeel
	 * 
	 */
	public static void instalaTemas(){
		if (!temasInstalados){
			//WebLookAndFeel.install();
			UIManager.installLookAndFeel(WEB_LOOK_AND_FEEL, WebLookAndFeel.class.getCanonicalName()); //WebLookAndFeel.class.getCanonicalName() == "com.alee.laf.WebLookAndFeel"
			temasInstalados = true;
		}
	}
	
	/** Cria um menu do tipo <code>JMenu</code> com todos os temas do WebLookAndFeel
	 * @param grupoBotoes <code>ButtonGroup</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o tema atual
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	@Override
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual){
		this.menu = new JMenu("WebLookAndFeel");
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
		TemasWebLookAndFeel.instalaTemas();
		this.listaTemas = new ArrayList<UIManager.LookAndFeelInfo>();
		this.listaTemas.add(new LookAndFeelInfo(WEB_LOOK_AND_FEEL, WebLookAndFeel.class.getCanonicalName()));
	}
	
	@Override
	public JMenu getMenu(){
		return this.menu;
	}

}
