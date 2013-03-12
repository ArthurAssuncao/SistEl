package com.arthurassuncao.sistel.eventos.pesquisa;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.gui.pesquisa.JanelaCadastrarPesquisa;
import com.arthurassuncao.sistel.persistencia.BancoDeDados;

/** Classe para tratar os eventos do mouse da janela <code>JanelaCadastrarPesquisa</code>
 * @author Arthur Assunção
 * 
 *
 * @see MouseAdapter
 */
public class TratadorEventoMouseCadastrarPesquisa extends MouseAdapter {
	private JanelaCadastrarPesquisa janela;
	
	/*public TratadorEventoMouseCadastrarPesquisa(){
		
	}*/

	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaCadastrarPesquisa</code>
	 * @param janela <code>JanelaCadastrarPesquisa</code> que sera manipulada
	 */
	public TratadorEventoMouseCadastrarPesquisa(JanelaCadastrarPesquisa janela){
		this.janela = janela;
	}

	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if(evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == this.janela.getBotaoLimparCampos()){
					//limpa campos
					this.janela.limpaCamposJanela();
				}
				else if ((JButton)evento.getSource() == this.janela.getBotaoSalvar()){
					if(this.janela.verificaCampos()){ //campos estao validos
						//JanelaMensagem.mostraMensagem(this.janela, "Cadastrar Pesquisa", "Campos são validos\nAinda nao salva");
						String cargo;
						Data dataInicio;
						Data dataFim;
						int numeroVotosNulos;
						int numeroVotosIndecisos;
						int numeroPessoasEntrevistadas;
						int numeroMunicipiosPesquisados;
						
						cargo = (String)janela.getCampoSelecionarCargo().getSelectedItem();
						dataInicio = janela.getDataCampoDataInicio();
						dataFim = janela.getDataCampoDataFim();
						numeroVotosNulos = janela.getVotosNulosBrancos();
						numeroVotosIndecisos = janela.getVotosIndecisos();
						numeroPessoasEntrevistadas = janela.getNumeroPessoasEntrevistadas();
						numeroMunicipiosPesquisados = janela.getNumeroMunicipiosEntrevistados();
						
						//cria um objeto Pesquisa com os dados do formulario
						Pesquisa novaPesquisa = new Pesquisa(cargo, dataInicio, dataFim, numeroVotosNulos, numeroVotosIndecisos, numeroPessoasEntrevistadas, numeroMunicipiosPesquisados, janela.getCandidatosVotos());
						if(novaPesquisa.getIdCandidatoVotos() != null){
							if (!novaPesquisa.exists()){
								int resultadoInsercao;
								resultadoInsercao = novaPesquisa.insere();
								if (resultadoInsercao == BancoDeDados.RESULTADO_SUCESSO){ //se cadastrar ele mostra a mensagem
									JanelaMensagem.mostraMensagem(this.janela, "Cadastro", "Cadastro realizado com sucesso");
									this.janela.limpaCamposJanela();
									this.janela.dispose();
								}
								else if(resultadoInsercao == BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO){
									JanelaMensagem.mostraMensagemErro(this.janela, "Pesquisa ja cadastrada");
								}
								else{
									JanelaMensagem.mostraMensagemErro(this.janela, "Erro ao cadastrar");
								}
							}
							else{
								JanelaMensagem.mostraMensagemErro(this.janela, "Pesquisa ja cadastrada");
							}
						}
						else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Não há candidatos para cadastrar nessa pesquisa");
						}
					}
					else{
						JanelaMensagem.mostraMensagemErro(this.janela, this.janela.getErros());
						this.janela.removeErros();
					}
				}
			}
			else if(evento.getSource() instanceof JTextField){
				JTextField campoTexto = ((JTextField)evento.getSource());
				if(campoTexto.getText().equalsIgnoreCase("0")){
					campoTexto.setText("");
				}
			}
		}// fim botao esquerdo do mouse
	}
	
	/** Classe para tratar os eventos de mouse out(focus lost) nos componentes da <code>JanelaCadastrarPesquisa</code>
	 * @author Arthur Assunção
	 * 
	 * @see FocusAdapter
	 */
	public static class TratadorEventosMouseOutCadastrarPesquisa extends FocusAdapter{
		
		/** Cria uma instancia do tratador de eventos da <code>JanelaCadastrarPesquisa</code>
		 *  
		 */
		public TratadorEventosMouseOutCadastrarPesquisa(){
		}

		/** Trata o evento de perda de foco nos campos de texto da janela, ao perder o foco e o conteudo do campo for vazio, 0 é setado como valor do campo de texto.
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		@Override
		public void focusLost(FocusEvent evento) {
			super.focusLost(evento);
			if(evento.getSource() instanceof JTextField){
				JTextField campoTexto = ((JTextField)evento.getSource());
				if(campoTexto.getText().equalsIgnoreCase("")){
					campoTexto.setText("0");
				}
			}
		}
	}//fim classe interna
}
