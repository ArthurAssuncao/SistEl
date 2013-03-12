package com.arthurassuncao.sistel.eventos.apuracao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.arthurassuncao.sistel.gui.apuracao.JanelaApuracao;

/** Classe para tratar os eventos do mouse da janela <code>JanelaApuracao</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventoMouseApuracao extends MouseAdapter {
	private JanelaApuracao janela;
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaApuracao</code>
	 * @param janela <code>JanelaApuracao</code> que sera manipulada
	 */
	public TratadorEventoMouseApuracao(JanelaApuracao janela){
		this.janela = janela;
	}

	/** Trata o evento do clique do mouse na <code>JanelaApuracao</code>
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if (evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == janela.getBotaoCargoAnterior()){ //botao grafico anterior
					janela.setCargoAnterior();
				}
				else if ((JButton)evento.getSource() == janela.getBotaoCargoPosterior()){ //botao grafico posterior
					janela.setCargoPosterior();
				}
			}
		}// fim botao esquerdo mouse
	}
}
