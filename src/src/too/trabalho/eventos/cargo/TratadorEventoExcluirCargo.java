/**
 * 
 */
package too.trabalho.eventos.cargo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import too.trabalho.classes.cargo.Cargo;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.gui.cargo.JanelaExcluirCargo;

/** Classe para tratar os eventos da janela <code>JanelaExcluirCargo</code>
 * @author Arthur Assunção
 * 
 * @see MouseAdapter
 */
public class TratadorEventoExcluirCargo extends MouseAdapter{
	private JanelaExcluirCargo janela;
	
	/** Cria uma instancia do Tratador de eventos da janela <code>JanelaExcluirCargo</code> do pacote {@link too.trabalho.gui.cargo}
	 * @param janela <code>JanelaExcluirCargo</code> que sera manipulada
	 */
	public TratadorEventoExcluirCargo(JanelaExcluirCargo janela){
		this.janela = janela;
	}

	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 * @see JanelaMensagem
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if(evento.getSource() instanceof JButton){
				if(this.janela.verificaCampos()){ //campos estao validos
					boolean excluir;
					Cargo cargo = new Cargo(this.janela.getCargo(), 0); //cria o cargo para excluir
					if(cargo.exists()){
						excluir = JanelaMensagem.mostraMensagemConfirmaWarning(this.janela, "A exclusão desse cargo irá excluir todos os candidatos que são desse cargo\n" +
								"Deseja excluir assim mesmo?");
						if (excluir){
							if (cargo.excluir()){
								JanelaMensagem.mostraMensagem(this.janela, "Excluir Cargo", "Cargo excluido com sucesso");
								this.janela.dispose();
							}
							else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Não foi possivel excluir o cargo");
							}
						}
					}
					else{
						JanelaMensagem.mostraMensagem(this.janela, "Excluir Cargo", "Cargo não existe");
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
