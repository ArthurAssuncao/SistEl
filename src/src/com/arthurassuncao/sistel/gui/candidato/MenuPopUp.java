package com.arthurassuncao.sistel.gui.candidato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.arthurassuncao.sistel.classes.candidato.Candidato;

/** A classe <code>MenuPopUp</code> cria um menu pop para a tabela da janela de pesquisa de candidatos
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
	private JMenuItem itemAlterar;
	private JMenuItem itemExcluir;
	
    /** Cria uma instancia do <code>MenuPopUp</code> 
     * @param tabela <code>JTable</code> com a tabela que terá o menu popup
     */
    public MenuPopUp(JTable tabela){
    	itemExibir = new JMenuItem("Exibir Candidato");
    	itemAlterar = new JMenuItem("Alterar Candidato");
    	itemExcluir = new JMenuItem("Excluir Candidato");
    	
    	itemExibir.addActionListener(new TratadorEventoItensMenuPopUp(tabela));
    	itemAlterar.addActionListener(new TratadorEventoItensMenuPopUp(tabela));
    	itemExcluir.addActionListener(new TratadorEventoItensMenuPopUp(tabela));
    	
        this.add(itemExibir);
        this.add(itemAlterar);
        this.add(itemExcluir);
    }
    
    /** Cria uma instancia do <code>MenuPopUp</code> 
     * @param tabela <code>JTable</code> com a tabela que terá o menu popup
     * @param janelaPesquisar <code>JanelaPesquisarCandidato</code> com a janela que será manipulada por ações do menu popup
     */
    public MenuPopUp(JTable tabela, JanelaPesquisarCandidato janelaPesquisar){
    	
    	itemExibir = new JMenuItem("Exibir Candidato");
    	itemAlterar = new JMenuItem("Alterar Candidato");
    	itemExcluir = new JMenuItem("Excluir Candidato");
    	
    	itemExibir.addActionListener(new TratadorEventoItensMenuPopUp(tabela));
    	itemAlterar.addActionListener(new TratadorEventoItensMenuPopUp(tabela, janelaPesquisar));
    	itemExcluir.addActionListener(new TratadorEventoItensMenuPopUp(tabela, janelaPesquisar));
    	
        this.add(itemExibir);
        this.add(itemAlterar);
        this.add(itemExcluir);
        
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
    	private JanelaPesquisarCandidato janelaPesquisar;
    	
    	/** Cria uma instancia do tratador de eventos da tabela
    	 * @param tabela <code>JTable</code> com a tabela que terá os eventos tratados
    	 */
    	public TratadorEventoItensMenuPopUp(JTable tabela){
    		this.tabela = tabela;
    	}
    	/** Cria uma instancia do tratador de eventos da tabela
    	 * @param tabela <code>JTable</code> com a tabela que terá os eventos tratados
    	 * @param janelaPesquisar <code>JanelaPesquisarCandidato</code> com a janela que tem a tabela
    	 */
    	public TratadorEventoItensMenuPopUp(JTable tabela, JanelaPesquisarCandidato janelaPesquisar){
    		this.tabela = tabela;
    		this.janelaPesquisar = janelaPesquisar;
    	}
		/** Trata o evento de clique nos itens do menu popup. Ao clicar em Exibir, é exibida uma janela com os dados do candidato.
		 * Ao clicar em Alterar, abre uma janela para modificar os dados do candidato. Ao clicar em excluir, abre uma janela para excluir o candidato.
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * @see Candidato#pesquisar(String, String)
		 * @see JanelaExibirDadosCandidato
		 * @see JanelaCadastrarCandidato
		 * @see JanelaExcluirCandidato
		 */
		@Override
		public void actionPerformed(ActionEvent evento) {
			List<Candidato> candidato = null;
			int linha = this.tabela.getSelectedRow();
			if (linha != -1){ //usuario selecionou alguma linha
				String nomeCandidato;
				String cargoCandidato;
				nomeCandidato = (String)this.tabela.getValueAt(linha, this.tabela.getColumn("Nome").getModelIndex()); //coluna 0 == nome
				cargoCandidato = (String)this.tabela.getValueAt(linha, this.tabela.getColumn("Cargo").getModelIndex()); //coluna 2 == cargo
				candidato = Candidato.pesquisar(nomeCandidato, cargoCandidato);
			}
			//Trata os eventos
			if(evento.getSource() == itemExibir){ //trata item exibir
				if (candidato != null && candidato.size() == 1){ //achou
					new JanelaExibirDadosCandidato(candidato.get(0));
				}
			}
			else if(evento.getSource() == itemAlterar){ //trata item alterar
				if (candidato != null && candidato.size() == 1){ //achou
					new JanelaCadastrarCandidato(candidato.get(0), this.janelaPesquisar);
				}
			}
			else if(evento.getSource() == itemExcluir){ //trata item excluir
				if (candidato != null && candidato.size() == 1){ //achou
					new JanelaExcluirCandidato(candidato.get(0), this.janelaPesquisar);
				}
			}
		}
    }
    
}
