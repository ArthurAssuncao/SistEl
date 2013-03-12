package com.arthurassuncao.sistel.gui.temas;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/*
 *  Before use see License
 *  
 */
/** Classe para manipular os temas(look and feel) da biblioteca Synthetica
 * @author Arthur Assunção
 * 
 * 
 * @see Temas
 * @see UIManager
 */
public class TemasSynthetica extends Temas implements InterfaceTema{
	private List<LookAndFeelInfo> listaTemas;
	private JMenu menu;
	/*
	private static boolean temasInstalados;
	
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_PADRAO = SyntheticaStandardLookAndFeel.class.getSimpleName(); //SyntheticaStandardLookAndFeel.class.getSimpleName() == "SyntheticaStandartLookAndFeel"
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_ALU_OXIDE = SyntheticaAluOxideLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_BLACK_EYE = SyntheticaBlackEyeLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_BLACK_MOON = SyntheticaBlackMoonLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_BLACK_STAR = SyntheticaBlackStarLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_BLUE_ICE = SyntheticaBlueIceLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_BLUE_MOON = SyntheticaBlueMoonLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_BLUE_STEEL = SyntheticaBlueSteelLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_CLASSY = SyntheticaClassyLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_GREEN_DREAM = SyntheticaGreenDreamLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_MAUVE_METALLIC = SyntheticaMauveMetallicLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_ORANGE_METALLIC = SyntheticaOrangeMetallicLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_SILVER_MOON = SyntheticaSilverMoonLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_SIMPLE_2D = SyntheticaSimple2DLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_SKY_METALLIC = SyntheticaSkyMetallicLookAndFeel.class.getSimpleName();
	// <code>String</code> com o nome do tema
	public static final String SYNTHETICA_WHITE_VISION = SyntheticaWhiteVisionLookAndFeel.class.getSimpleName();
	*/

	/** Instala os temas da biblioteca Synthetica
	 * 
	 */
	public static void instalaTemas(){
		/*try{ //tira a parte de nao licenciado
		  UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
		  SyntheticaLookAndFeel.setExtendedFileChooserEnabled(false);
		}
		catch (Exception e){
		  e.printStackTrace();
		}*/

		//SyntheticaLookAndFeel.setWindowsDecorated(false);
		//UIManager.put("Synthetica.window.decoration", Boolean.FALSE);
		/*
		if (!temasInstalados){
			UIManager.installLookAndFeel(SYNTHETICA_PADRAO, SyntheticaStandardLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_ALU_OXIDE, SyntheticaAluOxideLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_BLACK_EYE, SyntheticaBlackEyeLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_BLACK_MOON, SyntheticaBlackMoonLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_BLACK_STAR, SyntheticaBlackStarLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_BLUE_ICE, SyntheticaBlueIceLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_BLUE_MOON, SyntheticaBlueMoonLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_BLUE_STEEL, SyntheticaBlueSteelLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_CLASSY, SyntheticaClassyLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_GREEN_DREAM, SyntheticaGreenDreamLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_MAUVE_METALLIC, SyntheticaMauveMetallicLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_ORANGE_METALLIC, SyntheticaOrangeMetallicLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_SILVER_MOON, SyntheticaSilverMoonLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_SIMPLE_2D, SyntheticaSimple2DLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_SKY_METALLIC, SyntheticaSkyMetallicLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SYNTHETICA_WHITE_VISION, SyntheticaWhiteVisionLookAndFeel.class.getCanonicalName());
		}
		*/
	}

	/** Cria uma instancia da classe
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public TemasSynthetica(ButtonGroup grupoBotoes, String temaAtual){
		this.criaListaTemas();
		this.createMenuTemas(grupoBotoes, temaAtual);
	}

	/** Cria um menu do tipo <code>JMenu</code> com todos os temas do Synthetica
	 * @param grupoBotoes <code>ButtonGroup</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o tema atual
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	@Override
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual){
		this.menu = new JMenu("Synthetica");
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
		/*
		TemasSynthetica.instalaTemas();
		this.listaTemas = new ArrayList<UIManager.LookAndFeelInfo>();
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_PADRAO, SyntheticaStandardLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_ALU_OXIDE, SyntheticaAluOxideLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_BLACK_EYE, SyntheticaBlackEyeLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_BLACK_MOON, SyntheticaBlackMoonLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_BLACK_STAR, SyntheticaBlackStarLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_BLUE_ICE, SyntheticaBlueIceLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_BLUE_MOON, SyntheticaBlueMoonLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_BLUE_STEEL, SyntheticaBlueSteelLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_CLASSY, SyntheticaClassyLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_GREEN_DREAM, SyntheticaGreenDreamLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_MAUVE_METALLIC, SyntheticaMauveMetallicLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_ORANGE_METALLIC, SyntheticaOrangeMetallicLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_SILVER_MOON, SyntheticaSilverMoonLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_SIMPLE_2D, SyntheticaSimple2DLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_SKY_METALLIC, SyntheticaSkyMetallicLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SYNTHETICA_WHITE_VISION, SyntheticaWhiteVisionLookAndFeel.class.getCanonicalName()));
		*/
	}

	@Override
	public JMenu getMenu(){
		return this.menu;
	}

}
