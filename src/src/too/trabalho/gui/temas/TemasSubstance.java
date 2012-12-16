package too.trabalho.gui.temas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel;

/** Classe para manipular os temas(look and feel) da biblioteca Substance
 * @author Arthur Assunção
 * 
 * 
 * @see Temas
 * @see UIManager
 */
public class TemasSubstance extends Temas implements InterfaceTema{
	private static boolean temasInstalados;

	private List<LookAndFeelInfo> listaTemas;
	private JMenu menu;

	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE = SubstanceLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_AUTUMN = SubstanceAutumnLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_BUSINESS_BLACK_STEEL = SubstanceBusinessBlackSteelLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_BUSINESS = SubstanceBusinessLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_BUSINESS_BLUE_STEEL = SubstanceBusinessBlueSteelLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_CHALLENGER_DEEP = SubstanceChallengerDeepLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_CREME_COFFEE = SubstanceCremeCoffeeLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_CREME = SubstanceCremeLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_DUST_COFFEE = SubstanceDustCoffeeLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_DUST = SubstanceDustLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_GEMINI = SubstanceGeminiLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_GRAPHITE_AQUA = SubstanceGraphiteAquaLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_GRAPHITE_GLASS = SubstanceGraphiteGlassLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_GRAPHITE = SubstanceGraphiteLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_EMERALD_DUSK = SubstanceEmeraldDuskLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_MAGELLAN = SubstanceMagellanLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_MARINER = SubstanceMarinerLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_MIST_AQUA = SubstanceMistAquaLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_MIST_SILVER = SubstanceMistSilverLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_MODERATE = SubstanceModerateLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_NEBULA_BRICK_WALL = SubstanceNebulaBrickWallLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_NEBULA = SubstanceNebulaLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_OFFICE_BLACK_2007 = SubstanceOfficeBlack2007LookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_OFFICE_BLUE_2007 = SubstanceOfficeBlue2007LookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_OFFICE_SILVER_2007 = SubstanceOfficeSilver2007LookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_RAVEN = SubstanceRavenLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_SAHARA = SubstanceSaharaLookAndFeel.class.getSimpleName();
	/** <code>String</code> com o nome do tema */
	public static final String SUBSTANCE_TWILIGHT = SubstanceTwilightLookAndFeel.class.getSimpleName();

	/** Instala os temas da biblioteca Substance
	 * 
	 */
	public static void instalaTemas(){
		if (!temasInstalados){
			UIManager.installLookAndFeel(SUBSTANCE, SubstanceLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_AUTUMN, SubstanceAutumnLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_BUSINESS_BLACK_STEEL, SubstanceBusinessLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_BUSINESS, SubstanceBusinessLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_BUSINESS_BLUE_STEEL, SubstanceBusinessBlueSteelLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_CHALLENGER_DEEP, SubstanceChallengerDeepLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_CREME_COFFEE, SubstanceCremeCoffeeLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_CREME, SubstanceCremeLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_DUST_COFFEE, SubstanceDustCoffeeLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_DUST, SubstanceDustLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_GEMINI, SubstanceGeminiLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_GRAPHITE_AQUA, SubstanceGraphiteAquaLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_GRAPHITE_GLASS, SubstanceGraphiteGlassLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_GRAPHITE, SubstanceGraphiteLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_EMERALD_DUSK, SubstanceEmeraldDuskLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_MAGELLAN, SubstanceMagellanLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_MARINER, SubstanceMarinerLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_MIST_AQUA, SubstanceMistAquaLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_MIST_SILVER, SubstanceMistSilverLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_MODERATE, SubstanceModerateLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_NEBULA_BRICK_WALL, SubstanceNebulaBrickWallLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_NEBULA, SubstanceNebulaLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_OFFICE_BLACK_2007, SubstanceOfficeBlack2007LookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_OFFICE_BLUE_2007, SubstanceOfficeBlue2007LookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_OFFICE_SILVER_2007, SubstanceOfficeSilver2007LookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_RAVEN, SubstanceRavenLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_SAHARA, SubstanceSaharaLookAndFeel.class.getCanonicalName());
			UIManager.installLookAndFeel(SUBSTANCE_TWILIGHT, SubstanceTwilightLookAndFeel.class.getCanonicalName());
		}
	}

	/** Cria um menu do tipo <code>JMenu</code> com todos os temas do substance
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	public static JMenu createMenuTemas(){
		return null;
	}

	/** Cria uma instancia da classe
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public TemasSubstance(ButtonGroup grupoBotoes, String temaAtual){
		this.criaListaTemas();
		this.createMenuTemas(grupoBotoes, temaAtual);
	}

	/** Cria um menu do tipo <code>JMenu</code> com todos os temas do Substance
	 * @param grupoBotoes <code>ButtonGroup</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o tema atual
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	@Override
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual){
		this.menu = new JMenu("Substance");
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
		TemasSubstance.instalaTemas();
		this.listaTemas = new ArrayList<UIManager.LookAndFeelInfo>();
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE, SubstanceLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_AUTUMN, SubstanceAutumnLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_BUSINESS_BLACK_STEEL, SubstanceBusinessLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_BUSINESS, SubstanceBusinessLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_BUSINESS_BLUE_STEEL, SubstanceBusinessBlueSteelLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_CHALLENGER_DEEP, SubstanceChallengerDeepLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_CREME_COFFEE, SubstanceCremeCoffeeLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_CREME, SubstanceCremeLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_DUST_COFFEE, SubstanceDustCoffeeLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_DUST, SubstanceDustLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_GEMINI, SubstanceGeminiLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_GRAPHITE_AQUA, SubstanceGraphiteAquaLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_GRAPHITE_GLASS, SubstanceGraphiteGlassLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_GRAPHITE, SubstanceGraphiteLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_EMERALD_DUSK, SubstanceEmeraldDuskLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_MAGELLAN, SubstanceMagellanLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_MARINER, SubstanceMarinerLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_MIST_AQUA, SubstanceMistAquaLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_MIST_SILVER, SubstanceMistSilverLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_MODERATE, SubstanceModerateLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_NEBULA_BRICK_WALL, SubstanceNebulaBrickWallLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_NEBULA, SubstanceNebulaLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_OFFICE_BLACK_2007, SubstanceOfficeBlack2007LookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_OFFICE_BLUE_2007, SubstanceOfficeBlue2007LookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_OFFICE_SILVER_2007, SubstanceOfficeSilver2007LookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_RAVEN, SubstanceRavenLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_SAHARA, SubstanceSaharaLookAndFeel.class.getCanonicalName()));
		this.listaTemas.add(new LookAndFeelInfo(SUBSTANCE_TWILIGHT, SubstanceTwilightLookAndFeel.class.getCanonicalName()));		
	}

	@Override
	public JMenu getMenu(){
		return this.menu;
	}

}
