package com.arthurassuncao.sistel.eventos.votacao;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.arthurassuncao.sistel.gui.votacao.JanelaVotacao;

/** Classe para tratar os eventos do teclado da janela <code>JanelaVotacao</code>
 * @author Arthur Assunção
 * 
 *
 * @see KeyAdapter
 */
public class TratadorEventoTecladoVotacao extends KeyAdapter {
	private JanelaVotacao janela;
	
	/** Cria uma instancia do Tratador de eventos do teclado da janela <code>JanelaVotacao</code>
	 * @param janela <code>JanelaVotacao</code> que sera manipulada
	 */
	public TratadorEventoTecladoVotacao(JanelaVotacao janela){
		this.janela = janela;
	}

	/** Trata eventos de clique do teclado nos componentes da janela
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent evento){
		super.keyReleased(evento);
		
		janela.mudaPartido();
		
	}
	
	
}
