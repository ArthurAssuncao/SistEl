/**
 * 
 */
package com.arthurassuncao.sistel.eventos.cargo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.gui.cargo.JanelaCadastrarCargo;
import com.arthurassuncao.sistel.persistencia.BancoDeDados;

/** Classe para tratar os eventos do mouse da janela <code>JanelaCadastrarCargo</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventoMouseCadastrarCargo extends MouseAdapter {
	private JanelaCadastrarCargo janela = null;
	
	/** Cria uma instancia do Tratador de eventos da janela <code>JanelaCadastrarCargo</code> do pacote {@link com.arthurassuncao.sistel.gui.cargo}
	 * @param janela <code>JanelaCadastrarCargo</code> que sera manipulada
	 */
	public TratadorEventoMouseCadastrarCargo(JanelaCadastrarCargo janela){
		this.janela = janela;
	}
	
	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if(janela != null && evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == janela.getBotaoLimparCampos()){
					//limpa campos
					janela.limpaCamposJanela();
				}
				else if ((JButton)evento.getSource() == janela.getBotaoSalvar()){
					if(janela.verificaCampos()){ //campos estao validos
						//salva cargo
						String nomeCargo = janela.getValorCampoNomeCargo();
						int numeroDigitos = Integer.parseInt(janela.getValorCampoNumeroDigitos());
						Cargo novoCargo = null;
						novoCargo = new Cargo(nomeCargo, numeroDigitos);
						int resultadoInsercao = novoCargo.inserir(); //tenta cadastrar e retorna o resultado
						if (resultadoInsercao == BancoDeDados.RESULTADO_SUCESSO){ //se cadastrar ele mostra a mensagem
							JanelaMensagem.mostraMensagem(this.janela, "Cadastro", "Cadastro realizado com sucesso");
							this.janela.dispose();
						}
						else if(resultadoInsercao == BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO){
							JanelaMensagem.mostraMensagemErro(this.janela, "Cargo ja cadastrado");
						}
						else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Erro ao cadastrar");
						}
						janela.limpaCamposJanela();
					}
					else{
						JanelaMensagem.mostraMensagemErro(this.janela, this.janela.getErros());
						this.janela.removeErros();
					}
				}
			}
		}
	} //fim metodo mouseClicked
}
