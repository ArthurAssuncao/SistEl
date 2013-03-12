package com.arthurassuncao.sistel.eventos.apuracao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.votacao.Votacao;
import com.arthurassuncao.sistel.gui.apuracao.JanelaConsultaVotacao;

/** Classe para tratar os eventos do mouse da janela <code>JanelaConsultaVotacao</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventosMouseConsultarVotacao extends MouseAdapter{

	private JanelaConsultaVotacao janela = null;
	
	/*public TratadorEventosMouseConsultarVotacao(){
	}*/
	
	/** Cria uma instancia do Tratador de eventos do mouse janela <code>JanelaConsultaVotacao</code>
	 * @param janela <code>JanelaConsultaVotacao</code> que sera manipulada
	 */
	public TratadorEventosMouseConsultarVotacao(JanelaConsultaVotacao janela){
		this.janela = janela;
	}
	
	/** Trata o evento do clique do mouse na <code>JanelaConsultaVotacao</code>
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if (evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == janela.getBotaoPesquisar()){
					if (!janela.getCampoData().getText().isEmpty()){
						Data dataVotacao = new Data(janela.getCampoData().getData());
						List<Votacao> votacoes = null;
						Votacao votacao = Votacao.pesquisa(dataVotacao);
						if(votacao != null){
							votacoes = new ArrayList<Votacao>();
							votacoes.add(votacao);
						}
						janela.addLinhasTabela(votacoes);
					}
					else if (!janela.getCampoCargo().getText().isEmpty()){
						String cargo = janela.getCampoCargo().getText();
						List<Votacao> votacoes = Votacao.pesquisa(cargo, true); //considera datas posteriores a data atual
						janela.addLinhasTabela(votacoes);
					}
				} //if botaoPesquisar
			} //fim instanceof JButton
		} //fim botao esquerdo mouse
	}
	
}
