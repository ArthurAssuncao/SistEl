package com.arthurassuncao.sistel.gui.temas;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.arthurassuncao.sistel.classes.Configuracoes;

/** Classe para manipular os temas(look and feel) do sistema
 * @author Arthur Assunção
 * 
 * 
 * @see UIManager
 */
public class Temas {
	/*
	 * 		Temas Disponiveis
	 * Metal
	 * Nimbus
	 * CDE/Motif
	 * 		No Windows
	 * Windows
	 * Windows Classic
	 * 		No Linux(GKT)
	 * GTK+
	 * 		No Mac
	 * Mac
	 */
	/** <code>String</code> com o nome do tema */
	public static final String METAL = "Metal" ;
	/** <code>String</code> com o nome do tema */
	public static final String NIMBUS = "Nimbus";
	/** <code>String</code> com o nome do tema */
	public static final String CDE_MOTIF = "CDE/Motif";
	/** <code>String</code> com o nome do tema */
	public static final String WINDOWS = "Windows";
	/** <code>String</code> com o nome do tema */
	public static final String WINDOWS_CLASSIC = "Windows Classic";
	/** <code>String</code> com o nome do tema */
	public static final String GTK = "GTK+";
	/** <code>String</code> com o nome do tema */
	public static final String Mac = "Mac+";
	
	private JMenu menu;
	private String temaAtual;
	
	/** Cria uma instancia de Temas
	 * 
	 */
	protected Temas(){}
	
	/** Cria uma instancia de Temas
	 * @param nomeMenu <code>String</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o nome do tema atual
	 */
	public Temas(String nomeMenu, String temaAtual){
		this.temaAtual = temaAtual;
		this.menu = this.createMenuTemas(nomeMenu, this.temaAtual);
		//Temas.mudaTema(tema);
	}
	
	/** Retorna o menu com os temas
	 * @return <code>String</code> com os temas
	 */
	public JMenu getMenu(){
		return this.menu;
	}

	/** Muda o tema para o padrão do sistema. Caso não consiga usar o tema padrão do sistema, usa o tema Nimbus<br>
	 * Ex: se o sistema é windows, usará o tema Windows, se o sistema é linux rodando GTK, o tema será GTK+
	 * 
	 */
	public static void mudaTema(){ //Padrao é o do sistema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			try{
				mudaTema(Temas.NIMBUS);
			}
			catch(Exception ee){
				//nao muda
			}
		}
		Temas.repinta(true, true);
	}

	/** Muda o tema para um dos temas passados como parametro no <code>var-args</code>. O tema é escolhido na ordem no <code>var-args</code>.
	 * 
	 * @param temas <code>String...</code> com a lista de temas.
	 */
	public static void mudaTema(String... temas){
		if(temas == null){
			return;
		}
		forExterno:
			for(String tema : temas){
				try {	
					for (LookAndFeelInfo t : UIManager.getInstalledLookAndFeels()) {
						//System.out.println(t.getName());
						if (tema.equalsIgnoreCase(t.getName())) {
							UIManager.setLookAndFeel(t.getClassName());
							Temas.repinta(true, true);
							break forExterno;
						} //fim if
					}// fim for
				}// fim else
				catch (Exception e) {
					//nao muda
				}
			}
	}

	/** Repinta as janelas
	 * @param rePack <code>boolean</code> com <code>true</code> se as janelas devem chamar o metodo JFrame#pack() e <code>false</code> senao.
	 * @param todasJanelasDecoradas <code>Boolean</code> com <code>true</code> se todas as janela do sistema sao decoradas e <code>false</code> se todas nao sao decoradas.
	 * Caso alguma seja diferente das outras do sistema, basta passar <code>null</code>.
	 */
	public static void repinta(boolean rePack, Boolean todasJanelasDecoradas){
		if(todasJanelasDecoradas != null){
			JFrame.setDefaultLookAndFeelDecorated(todasJanelasDecoradas); //tentativa de evitar o erro de desaparecer o windows decoration, sem sucesso
		}
		Frame[] frames = Frame.getFrames();
		for(Frame frame : frames){
			SwingUtilities.updateComponentTreeUI(frame);
			if(rePack){
				frame.pack();
				if(todasJanelasDecoradas != null){
					frame.setUndecorated(!todasJanelasDecoradas); //tentativa de evitar o erro de desaparecer o windows decoration, sem sucesso
				}
			}
			frame.repaint();
		}
	}

	/** Cria um menu do tipo <code>JMenu</code> com todos os temas instalados no sistema
	 * @param nomeMenu <code>String</code> com o nome do menu
	 * @param temaAtual <code>String</code> com o tema(LookAndFeel) atual
	 * @return <code>JMenu</code> com o menu com todos os temas disponiveis no sistema
	 */
	public JMenu createMenuTemas(String nomeMenu, String temaAtual){
		JMenu menu = null;
		menu = new JMenu(nomeMenu);
		ButtonGroup grupo = new ButtonGroup();
		//LookAndFeel temaAtual = UIManager.getLookAndFeel();
		
		/*for (LookAndFeelInfo tema : UIManager.getInstalledLookAndFeels()) {
			JMenuItem item = Temas.createRadioMenuItem(tema.getName());

			item.addActionListener(new ItemMenuThemeListener(tema.getName()));
			if(lookAndFeelAtual.getName().equals(tema.getName())){
				item.setSelected(true);
				System.out.println("Tema usado: " + tema.getName());
			}
			grupo.add(item);
			menu.add(item);
		}// fim for
		*/
		
		menu.add(new TemasPadrao(grupo, temaAtual).getMenu());
		menu.add(new TemasWebLookAndFeel(grupo, temaAtual).getMenu());
		menu.add(new TemasJGoodies(grupo, temaAtual).getMenu());
		menu.add(new TemasJTatto(grupo, temaAtual).getMenu());
		menu.add(new TemasSubstance(grupo, temaAtual).getMenu());
		//menu.add(new TemasSynthetica(grupo, temaAtual).getMenu());
		
		return menu;
	}

	/** Cria um item para o <code>JMenu</code>
	 * @param nome <code>String</code> com o nome do item
	 * @return <code>JMenuItem</code> com o item do menu
	 */
	@SuppressWarnings("unused")
	private static JMenuItem createMenuItem(String nome){
		JMenuItem item = null;

		item = new JMenuItem(nome);

		return item;
	}
	
	/** Cria um item para o <code>JMenu</code>
	 * @param nome <code>String</code> com o nome do item
	 * @return <code>JRadioButtonMenuItem</code> com o item do menu
	 */
	protected static JRadioButtonMenuItem createRadioMenuItem(String nome){
		JRadioButtonMenuItem item = null;

		item = new JRadioButtonMenuItem(nome);

		return item;
	}

	/**
	 * Adds the given defaults in UIManager.
	 * 
	 * Note: the values are added only if they do not exist in the existing look
	 * and feel defaults. This makes it possible for look and feel implementors to
	 * override SwingX defaults.
	 * 
	 * Note: the array is traversed in reverse order. If a key is found twice in
	 * the array, the key/value with the highest position in the array gets
	 * precedence over the other key in the array
	 * 
	 * @param keysAndValues <code>Object[]</code> keys and values
	 * http://www.javadocexamples.com/java_source/org/jdesktop/swingx/plaf/LookAndFeelAddons.java.html
	 */
	public static void loadDefaults(Object[] keysAndValues) {    
		// Go in reverse order so the most recent keys get added first...
		for (int i = keysAndValues.length - 2; i >= 0; i = i - 2) {
			if (UIManager.getLookAndFeelDefaults().get(keysAndValues[i]) == null) {
				UIManager.getLookAndFeelDefaults().put(keysAndValues[i], keysAndValues[i + 1]);
			}
		}
	}

	/** Remove as propriedades do UIManager
	 * @param keysAndValues <code>Object[]</code> com as chaves e valores
	 */
	public static void unloadDefaults(Object[] keysAndValues) {
		for (int i = 0, c = keysAndValues.length; i < c; i = i + 2) {
			UIManager.getLookAndFeelDefaults().put(keysAndValues[i], null);
		}
	}

	/** Retorna um vetor com as chaves e valores
	 * @param uiDefault <code>UIDefaults</code>
	 * @return <code>Object[]</code> com as chaves e valores
	 */
	public static Object[] getKeysAndValues(UIDefaults uiDefault){
		Enumeration<Object> keys = uiDefault.keys();
		Collection<Object> valuesCol = uiDefault.values();
		Object[] values = valuesCol.toArray();

		Object[] keysAndValues = new Object[values.length * 2];
		for(int i = 0, j = 0; i < keysAndValues.length; i += 2, j++){
			keysAndValues[i] = keys.nextElement();
			keysAndValues[i+1] = values[j];
		}
		return keysAndValues;		
	}

	/** Classe para tratar os itens do menu
	 * @author Arthur Assunção
	 * 
	 * @see ActionListener
	 */
	protected static class ItemMenuThemeListener implements ActionListener{
		/*private String classe;
		public ItemMenuThemeListener(String classe){
			this.classe = classe;
		}*/
		private String nomeTema;
		/**
		 * @param nomeTema <code>String</code> com o tema
		 */
		public ItemMenuThemeListener(String nomeTema){
			this.nomeTema = nomeTema;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent evento) {
			try {
				//UIManager.setLookAndFeel(classe);
				Temas.mudaTema(this.nomeTema);
				Configuracoes configs = Configuracoes.getInstance();
				configs.setTema(this.nomeTema);
				System.out.println("Mudou o tema para: " + this.nomeTema);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
