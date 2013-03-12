package com.arthurassuncao.sistel.eventos.candidato;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.gui.candidato.JanelaPesquisarCandidato;

/** Classe para tratar os eventos do mouse da janela <code>JanelaPesquisarCandidato</code>
 * @author Arthur Assunção
 * 
 *
 * @see MouseAdapter
 */
public class TratadorEventosMousePesquisarCandidato extends MouseAdapter{

	private JanelaPesquisarCandidato janela = null;
	
	/*public TratadorEventosMousePesquisarCandidato(){
	}*/
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaPesquisarCandidato</code> do pacote {@link com.arthurassuncao.sistel.gui.candidato}
	 * @param janela <code>JanelaPesquisarCandidato</code> que sera manipulada
	 */
	public TratadorEventosMousePesquisarCandidato(JanelaPesquisarCandidato janela){
		this.janela = janela;
	}
	
	/** Trata eventos de click do mouse nos componentes da janela
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if (evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == janela.getBotaoPesquisar()){
					if (!janela.getCampoNome().getText().isEmpty()){
						String nome = janela.getCampoNome().getText();
						List<Candidato> candidatos = Candidato.pesquisar(nome);
						janela.addLinhasTabela(candidatos);
					}
					else if (!janela.getCampoNumero().getText().isEmpty()){
						String numero = janela.getCampoNumero().getText();
						List<Candidato> candidatos = Candidato.pesquisar(Integer.parseInt(numero), null);
						janela.addLinhasTabela(candidatos);
					}
				} //if botaoPesquisar
			} //fim instanceof JButton
		} //fim botao esquerdo mouse
	}
	
}
