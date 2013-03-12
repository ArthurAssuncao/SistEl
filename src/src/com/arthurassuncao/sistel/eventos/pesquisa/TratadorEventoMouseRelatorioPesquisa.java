package com.arthurassuncao.sistel.eventos.pesquisa;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.arthurassuncao.sistel.gui.pesquisa.JanelaRelatorioPesquisa;

/** Classe para tratar os eventos do mouse da janela <code>JanelaRelatorioPesquisa</code>
 * @author Arthur Assunção
 * 
 *
 * @see MouseAdapter
 */
public class TratadorEventoMouseRelatorioPesquisa extends MouseAdapter {
	private JanelaRelatorioPesquisa janela;
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaRelatorioPesquisa</code>
	 * @param janela <code>JanelaRelatorioPesquisa</code> que sera manipulada
	 */
	public TratadorEventoMouseRelatorioPesquisa(JanelaRelatorioPesquisa janela){
		this.janela = janela;
	}

	/** Trata eventos de click do mouse nos componentes da janela. Ao clicar no botao proximo, o proximo grafico, caso exista, é exibido.
	 * Ao clicar no botao anterior, o grafico anterior, caso exista, é mostrado.
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if (evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == janela.getBotaoGraficoAnterior()){ //botao grafico anterior
					janela.setGraficoAnterior();
				}
				else if ((JButton)evento.getSource() == janela.getBotaoGraficoPosterior()){ //botao grafico posterior
					janela.setGraficoPosterior();
				}
			}
		}// fim botao esquerdo mouse
		
	}
	
	
	
}
