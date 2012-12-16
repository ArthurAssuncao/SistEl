package too.trabalho.gui.temas;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;

/**
 * @author Arthur Assunção
 * 
 *
 */
public interface InterfaceTema {
	/** Cria um objeto <code>JMenu</code> com os temas
	 * @param grupoBotoes <code>ButtonGroup</code> com o grupo de botoes em que os temas serao adicionados
	 * @param temaAtual <code>String</code> com o nome do tema atual. Caso o tema seja encontrado, sera marcado como selecionado
	 * @return <code>JMenu</code> com o menu com os temas
	 */
	public JMenu createMenuTemas(ButtonGroup grupoBotoes, String temaAtual);
	
	/** Cria a lista de temas
	 * 
	 */
	public void criaListaTemas();
	
	
	/** Retorna o menu
	 * @return <code>JMenu</code> com o menu com os temas
	 */
	public JMenu getMenu();
}
