package com.arthurassuncao.sistel.gui.candidato;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.gui.JanelaMensagem;

/** A classe <code>JanelaExcluirCandidato</code> cria uma GUI para excluir candidatos
 * @author Arthur Assun��o
 * 
 * 
 * @see JanelaMensagem
 *
 */
public class JanelaExcluirCandidato {
	
	private JanelaPesquisarCandidato janelaPesquisar;
	
	/**Cria uma instancia da janela de exclus�o de candidato
	 * @param candidato <code>Candidato</code> que ser� excluido
	 * @param janelaPesquisar <code>JanelaPesquisarCandidato</code> janela de pesquisa para que o metodo atualize a tabela da janela ap�s a exclus�o do candidato
	 */
	public JanelaExcluirCandidato(Candidato candidato, JanelaPesquisarCandidato janelaPesquisar){
		this.janelaPesquisar = janelaPesquisar;
		boolean excluir = JanelaMensagem.mostraMensagemConfirma(this.janelaPesquisar, "Excluir candidato", "Deseja remover o candidato " + candidato.getNome() + " ?");
		if (excluir){
			if(candidato.excluir()){ //se excluiu
				JanelaMensagem.mostraMensagem(this.janelaPesquisar, "Excluir Candidato", "Exclus�o realizada com sucesso");
				janelaPesquisar.atualizaTabela();
			}
		}
		else{
			//nao excluir
		}
	} //fim construtor
}
