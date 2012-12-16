/**
 * 
 */
package too.trabalho.gui.apuracao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import too.trabalho.classes.Data;
import too.trabalho.classes.votacao.Votacao;
import too.trabalho.gui.candidato.JanelaPesquisarCandidato;

/** A classe <code>MenuPopUp</code> cria um menu pop para a tabela da janela de consulta de votacao
 * @author Arthur Assunção
 * 
 * 
 * @see JPopupMenu
 */
public class MenuPopUp extends JPopupMenu {
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -3337209329465813934L;
	private JMenuItem itemExibir;
	
	/** Cria uma instancia do <code>MenuPopUp</code> 
     * @param tabela <code>JTable</code> com a tabela que terá o menu popup
     */
    public MenuPopUp(JTable tabela){
    	itemExibir = new JMenuItem("Exibir Grafico");

    	itemExibir.addActionListener(new TratadorEventoItensMenuPopUp(tabela));
    	
        this.add(itemExibir);
    }
    
    /** Cria uma instancia do <code>MenuPopUp</code> 
     * @param tabela <code>JTable</code> com a tabela que terá o menu popup
     * @param janela <code>JanelaConsultaVotacao</code> com a janela que será manipulada por ações do menu popup
     */
    public MenuPopUp(JTable tabela, JanelaConsultaVotacao janela){
    	itemExibir = new JMenuItem("Exibir Grafico");
    	
    	itemExibir.addActionListener(new TratadorEventoItensMenuPopUp(tabela));
    	
        this.add(itemExibir);        
    }
    
    /** Tratador de eventos dos itens da classe <code>MenuPopUp</code>
     * @author Arthur Assunção
     * 
     * 
     * @see ActionListener
     * @see JanelaPesquisarCandidato
     * @see JTable
     *
     */
    private class TratadorEventoItensMenuPopUp implements ActionListener{

    	private JTable tabela;
    	//private JanelaConsultaVotacao janela;
    	
    	/** Cria uma instancia do tratador de eventos da tabela
    	 * @param tabela <code>JTable</code> com a tabela que terá os eventos tratados
    	 */
    	public TratadorEventoItensMenuPopUp(JTable tabela){
    		this.tabela = tabela;
    	}
    	/* Cria uma instancia do tratador de eventos da tabela
    	 * @param tabela <code>JTable</code> com a tabela que terá os eventos tratados
    	 * @param janela <code>JanelaConsultaVotacao</code> com a janela que tem a tabela
    	 */
    	/*public TratadorEventoItensMenuPopUp(JTable tabela, JanelaConsultaVotacao janela){
    		this.tabela = tabela;
    		this.janela = janela;
    	}*/
		/** Trata o evento de clique nos itens do menu popup. Ao clicar em Exibir Grafico, o grafico é exibido com os dados da votacao selecionada.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * @see Votacao
		 * @see JanelaApuracao
		 */
		@Override
		public void actionPerformed(ActionEvent evento) {
			int linha = this.tabela.getSelectedRow();
			Votacao votacao = null;
			if (linha != -1){ //usuario selecionou alguma linha
				Data dataVotacao;
				//String cargoCandidato;
				dataVotacao = new Data((String)this.tabela.getValueAt(linha, this.tabela.getColumn("Data").getModelIndex())); //coluna 0 == nome
				//cargoCandidato = (String)this.tabela.getValueAt(linha, this.tabela.getColumn("Cargos").getModelIndex()); //coluna 1 == cargo
				votacao = new Votacao(dataVotacao, Votacao.getAllCargos(dataVotacao), 0);
			}
			//Trata os eventos
			if(evento.getSource() == itemExibir){ //trata item exibir
				//new JanelaExibirDadosCandidato(candidato.get(0));
				if(votacao != null){
					new JanelaApuracao(votacao);
				}
			}
		}
    }
    
}
