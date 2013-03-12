package com.arthurassuncao.sistel.eventos.partido;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.arthurassuncao.sistel.classes.partido.PartidoPolitico;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.gui.partido.JanelaExcluirPartido;

/** Classe para tratar os eventos da janela <code>JanelaExcluirPartido</code>
 * @author Arthur Assunção
 * 
 * @see MouseAdapter
 */
public class TratadorEventoExcluirPartido extends MouseAdapter{
	private JanelaExcluirPartido janela;
	
	/** Cria uma instancia do Tratador de eventos da janela <code>JanelaExcluirPartido</code> do pacote {@link com.arthurassuncao.sistel.gui.partido}
	 * @param janela <code>JanelaExcluirPartido</code> que sera manipulada
	 */
	public TratadorEventoExcluirPartido(JanelaExcluirPartido janela){
		this.janela = janela;
	}

	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 * @see JanelaMensagem
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		// TODO Auto-generated method stub
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if(evento.getSource() instanceof JButton){
				if(this.janela.verificaCampos()){ //campos estao validos
					boolean excluir;
					int numeroPartido = Integer.parseInt(this.janela.getValorCampoNumero());
					PartidoPolitico partido = new PartidoPolitico("", "", numeroPartido); //cria o partido para excluir
					if(partido.exists()){
						excluir = JanelaMensagem.mostraMensagemConfirmaWarning(this.janela, "A exclusão desse partido irá excluir todos os candidatos que pertencem a esse partido\n" +
								"Deseja excluir assim mesmo?");
						if (excluir){
							if (partido.excluir()){
								JanelaMensagem.mostraMensagem(this.janela, "Excluir Partido", "Partido excluido com sucesso");
								this.janela.limpaCampos();
								this.janela.dispose();
							}
							else{
								JanelaMensagem.mostraMensagemErro(this.janela, "Não foi possivel excluir o partido");
							}
						}
					}
					else{
						JanelaMensagem.mostraMensagem(this.janela, "Excluir Partido", "Partido não existe");
					}
				} //else verificaCampos()
				else{
					JanelaMensagem.mostraMensagemErro(this.janela, this.janela.getErros());
					this.janela.removeErros();
				}
			}
		}
	}
	
	
	
}
