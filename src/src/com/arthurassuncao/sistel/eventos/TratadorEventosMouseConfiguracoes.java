package com.arthurassuncao.sistel.eventos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.arthurassuncao.sistel.classes.Configuracoes;
import com.arthurassuncao.sistel.gui.JanelaConfiguracoes;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.persistencia.BDEleicoes;

/** Classe para tratar os eventos do mouse da janela <code>JanelaConfiguracoes</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 * 
 */
public class TratadorEventosMouseConfiguracoes extends MouseAdapter{
	
	private JanelaConfiguracoes janela;
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaConfiguracoes</code>
	 * @param janela <code>JanelaConfiguracoes</code> que sera manipulada
	 */
	public TratadorEventosMouseConfiguracoes(JanelaConfiguracoes janela){
		this.janela = janela;
	}

	/** Trata eventos de click do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		
		if(evento.getSource() instanceof JButton){
			String senha;
			senha = Configuracoes.getInstance().getSenha();
			if (new String(this.janela.getCampoSenha().getPassword()).equals(senha)){ //senha correta, mudar isso depois
				BDEleicoes.mostraJanelaBancoDeDados();
				this.janela.dispose();
			}
			else{
				JanelaMensagem.mostraMensagemErro(this.janela, "Senha Incorreta");
			}
		}
	}
	
}
