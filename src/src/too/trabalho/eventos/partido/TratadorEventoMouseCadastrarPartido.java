package too.trabalho.eventos.partido;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import too.trabalho.classes.partido.PartidoPolitico;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.gui.partido.JanelaCadastrarPartido;
import too.trabalho.persistencia.BancoDeDados;

/** Classe para tratar os eventos do mouse da janela <code>JanelaCadastrarPartido</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventoMouseCadastrarPartido extends MouseAdapter {
	private JanelaCadastrarPartido janela = null;
	
	/** Cria uma instancia do Tratador de eventos da janela <code>JanelaCadastrarPartido</code> do pacote {@link too.trabalho.gui.partido}
	 * @param janela <code>JanelaCadastrarPartido</code> que sera manipulada
	 */
	public TratadorEventoMouseCadastrarPartido(JanelaCadastrarPartido janela){
		this.janela = janela;
	}
	
	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if(this.janela != null && evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == this.janela.getBotaoLimparCampos()){
					//limpa campos
					this.janela.limpaCamposJanela();
				}
				else if ((JButton)evento.getSource() == this.janela.getBotaoSalvar()){
					if(this.janela.verificaCampos()){ //campos estao validos
						//salva cargo
						String nomePartido = this.janela.getValorCampoNome();
						String siglaPartido = this.janela.getValorCampoSigla();
						int numeroPartido = Integer.parseInt(this.janela.getValorCampoNumero());
						PartidoPolitico novoPartido = null;
						novoPartido = new PartidoPolitico(siglaPartido, nomePartido, numeroPartido);
						int resultadoInsercao = novoPartido.inserir(); //tenta cadastrar e retorna o resultado
						if (resultadoInsercao == BancoDeDados.RESULTADO_SUCESSO){ //se cadastrar ele mostra a mensagem
							JanelaMensagem.mostraMensagem(this.janela, "Cadastro", "Cadastro realizado com sucesso");
							this.janela.limpaCamposJanela();
							this.janela.dispose();
						}
						else if(resultadoInsercao == BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO){
							//JanelaMensagem.mostraMensagemErro(this.janela, "Partido ja cadastrado");
							if(PartidoPolitico.partidoPoliticoExists(numeroPartido)){
								JanelaMensagem.mostraMensagemErro(this.janela, "Já existe um partido com o número " + numeroPartido);
							}
							else{
								JanelaMensagem.mostraMensagemErro(this.janela, "Já existe um partido com a sigla " + siglaPartido);
							}
						}
						else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Erro ao cadastrar");
						}
						
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
