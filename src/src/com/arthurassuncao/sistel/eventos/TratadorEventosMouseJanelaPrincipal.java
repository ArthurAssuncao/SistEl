package com.arthurassuncao.sistel.eventos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import com.arthurassuncao.sistel.gui.LabelRotulo;

/** Classe para tratar os eventos do mouse das janelas <code>JanelaCadastrarCandidato</code> e <code>JanelaPesquisarCandidato</code>
 * @author Arthur Assunção
 * 
 *
 * @see MouseAdapter
 */
public class TratadorEventosMouseJanelaPrincipal extends MouseAdapter {
	
	/*private JanelaPrincipal janela;
	public TratadorEventosMouseJanelaPrincipal(JanelaPrincipal janela){
		this.janela = janela;
	}*/
	
	/** Trata os eventos ao entrar com o mouse em um determinado componente da janela
	 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent evento){
		super.mouseEntered(evento);
	    if (evento.getSource() instanceof LabelRotulo) {
	    	LabelRotulo labelImagem = (LabelRotulo)evento.getSource();
	    	ImageIcon imagemPrincipal = new ImageIcon("imagens/planalto.jpg");
	    	imagemPrincipal.setImage(imagemPrincipal.getImage().getScaledInstance(100, 300, 100));
	    	labelImagem.setIcon(imagemPrincipal);
	    }
	}

	/**  Trata os eventos ao sair com o mouse da area de um determinado componente da janela
	 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent evento) {
		super.mouseExited(evento);
		if (evento.getSource() instanceof LabelRotulo) {
	    	LabelRotulo labelImagem = (LabelRotulo)evento.getSource();
	    	ImageIcon imagemPrincipal = new ImageIcon(getClass().getResource("/too/trabalho/gui/imagens/eleicoes_2012.jpg"));
			imagemPrincipal.setImage(imagemPrincipal.getImage().getScaledInstance(100, 300, 100));
	    	labelImagem.setIcon(imagemPrincipal);
	    }
	}
}
