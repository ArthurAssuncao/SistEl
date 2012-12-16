package too.trabalho.eventos.candidato;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import too.trabalho.classes.candidato.Candidato;
import too.trabalho.classes.partido.PartidoPolitico;
import too.trabalho.gui.Janela;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.gui.candidato.JanelaCadastrarCandidato;
import too.trabalho.gui.candidato.JanelaPesquisarCandidato;
import too.trabalho.persistencia.BancoDeDados;

/** Classe para tratar os eventos do mouse das janelas <code>JanelaCadastrarCandidato</code> e <code>JanelaPesquisarCandidato</code>
 * @author Arthur Assunção
 * 
 *
 * @see MouseAdapter
 */
public class TratadorEventosMouseCadastrarCandidato extends MouseAdapter {
	
	private JanelaCadastrarCandidato janela = null;
	private JanelaPesquisarCandidato janelaPesquisar = null;
	private int idCandidato;
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaCadastrarCandidato</code> do pacote {@link too.trabalho.gui.candidato}
	 * @param janela <code>JanelaCadastrarCandidato</code> que sera manipulada
	 */
	public TratadorEventosMouseCadastrarCandidato(JanelaCadastrarCandidato janela){
		this.janela = janela;
	}
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaCadastrarCandidato</code> do pacote {@link too.trabalho.gui.candidato}
	 * @param janela <code>JanelaCadastrarCandidato</code> que sera manipulada
	 * @param idCandidato <code>int</code> com o id de um candidato
	 */
	public TratadorEventosMouseCadastrarCandidato(JanelaCadastrarCandidato janela, int idCandidato){
		this.janela = janela;
		this.idCandidato = idCandidato;
	}
	
	/** Cria uma instancia do Tratador de eventos do mouse das janelas <code>JanelaCadastrarCandidato</code> e <code>JanelaPesquisarCandidato</code> do pacote {@link too.trabalho.gui.candidato}
	 * @param janela <code>JanelaCadastrarCandidato</code> que sera manipulada
	 * @param janelaPesquisar <code>JanelaPesquisarCandidato</code> que sera manipulada
	 * @param idCandidato <code>int</code> com o id de um candidato
	 */
	public TratadorEventosMouseCadastrarCandidato(JanelaCadastrarCandidato janela, JanelaPesquisarCandidato janelaPesquisar, int idCandidato){
		this.janela = janela;
		this.janelaPesquisar = janelaPesquisar;
		this.idCandidato = idCandidato;
	}
	
	/** Trata eventos de click do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 * @see JanelaMensagem
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if (evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo do mouse
			if (evento.getSource() instanceof JLabel){
				String arquivoImagem = Janela.janelaAbrirArquivo(this.janela, "Abrir Imagem", null, true,"Imagens", "jpg", "png", "gif");
				if (arquivoImagem != null){
					this.janela.setFotoCandidato(arquivoImagem);
				}
			}// fim if instanceof
			else if(this.janela != null && evento.getSource() instanceof JButton){
				if ((JButton)evento.getSource() == janela.getBotaoLimparCampos()){
					//limpa campos
					this.janela.limpaCamposJanela();
				}
				else if ((JButton)evento.getSource() == janela.getBotaoSalvar()){
					if(this.janela.verificaCampos()){ //campos estao validos
						//salva candidato
						String nome 	= this.janela.getCampoNome().getText();
						int numero 	= Integer.parseInt(this.janela.getCampoNumero().getText());
						String cargo 	= this.janela.getCampoSelecionarCargo().getSelectedItem().toString();
						String siglaPartidoPolitico = this.janela.getCampoSelecionarPartido().getSelectedItem().toString();
						PartidoPolitico partido;
						String enderecoFoto = this.janela.getEnderecoFotoCandidato();
					
						partido = PartidoPolitico.pesquisa(siglaPartidoPolitico);
						if (partido != null){
							//System.out.println(nome + " " + numero + " " + cargo + " " + siglaPartidoPolitico + " " + enderecoImagem);
							Candidato novoCandidato = null;
							novoCandidato = new Candidato(nome, numero, cargo, partido, enderecoFoto);
							//clicou no botao Salvar em cadastrar candidato
							if (this.janela.getBotaoSalvar().getText().equals(JanelaCadastrarCandidato.TEXTO_BOTAO_SALVAR)){
								int resultadoInsercao = novoCandidato.inserir();
								if (resultadoInsercao == BancoDeDados.RESULTADO_SUCESSO){ //se cadastrar ele mostra a mensagem
									JanelaMensagem.mostraMensagem(this.janela, "Cadastro", "Cadastro realizado com sucesso");
									this.janela.limpaCamposJanela();
									this.janela.dispose(); //fecha janela
								}
								else if(resultadoInsercao == BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO){
									//JanelaMensagem.mostraMensagemErro(this.janela, "Candidato ja cadastrado");
									JanelaMensagem.mostraMensagemErro(this.janela, "Já existe um candidato do partido " +
											siglaPartidoPolitico + " no cargo " + cargo + " com o número " + numero
											);
								}
								else{
									JanelaMensagem.mostraMensagemErro(this.janela, "Erro ao cadastrar");
								}
							}
							//clicou no botao Atualizar em atualizar candidato
							else if (this.janela.getBotaoSalvar().getText().equals(JanelaCadastrarCandidato.TEXTO_BOTAO_ATUALIZAR)){
								if (novoCandidato.atualizarDados(this.idCandidato)){ //se atualizar ele mostra a mensagem
									janelaPesquisar.atualizaTabela();
									JanelaMensagem.mostraMensagem(this.janela, "Cadastro", "Dados do candidato atualizados com sucesso");
									this.janela.limpaCamposJanela();
									this.janela.dispose(); //fecha janela
								}
								else{
									JanelaMensagem.mostraMensagemErro(null, "Cargo e numero já existem no banco de dados");
								}
							}
						}
						else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Partido Inválido");
						}
				}
				else{
					JanelaMensagem.mostraMensagemErro(this.janela, this.janela.getErros());
					this.janela.removeErros();
				}
					
				}// fim botao salvar
			} //fim tratamento botoes
		}// fim if getButton
	}// fim metodo
	
}
