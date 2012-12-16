package too.trabalho.eventos.votacao;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import too.trabalho.classes.candidato.Candidato;
import too.trabalho.classes.cargo.Cargo;
import too.trabalho.classes.votacao.Votacao;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.gui.LabelRotulo;
import too.trabalho.gui.votacao.JanelaVotacao;
import too.trabalho.persistencia.BancoDeDados;
import too.trabalho.recursos.Recursos;

/** Classe para tratar os eventos do mouse da janela <code>JanelaVotacao</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventoMouseVotacao extends MouseAdapter{

	private JanelaVotacao janela;

	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaVotacao</code>
	 * @param janela <code>JanelaVotacao</code> que sera manipulada
	 */
	public TratadorEventoMouseVotacao(JanelaVotacao janela){
		this.janela = janela;
	}

	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento){
		super.mouseClicked(evento);
		if(evento.getSource() instanceof JButton){ //Botao
			JButton botao = (JButton)evento.getSource();
			if(MouseEvent.BUTTON1 == evento.getButton()){ //se clicou com o botao esquerdo do mouse
				if(this.janela.isBotaoTecladoNumerico(botao) && !janela.isVotoBranco()){ //botao do teclado numerico
					if(!this.janela.addNumeroCampoNumero(Integer.parseInt(botao.getText()))){
						return; //nao faz nada
					}
					boolean partidoExiste = this.janela.mudaPartido();

					LabelRotulo campoNumero = this.janela.getCampoNumero();
					//digitou o maximo de digitos para o candidato
					if(campoNumero.getText().trim().length() == this.janela.getNumeroDigitosCargo()){
						int numeroCandidato = Integer.parseInt(campoNumero.getText().trim());
						String cargo = this.janela.getLabelCargo().getText();
						List<Candidato> candidato = Candidato.pesquisar(numeroCandidato, cargo);
						if(candidato.size() > 0){ //achou
							this.janela.setNumeroCandidatoValido(true, partidoExiste);
							this.janela.getLabelNomeCandidato().setText(candidato.get(0).getNome());
							this.janela.setFotoCandidato(candidato.get(0).getEnderecoFoto());
						}
						else{
							this.janela.setNumeroCandidatoValido(false, partidoExiste);
						}
					}
				}
				else if(evento.getSource() == this.janela.getBotaoBranco()  && !janela.isVotoBranco()){ //botao Branco
					//JanelaMensagem.mostraMensagemErro(this.janela, "Ainda nao foi implementado");
					LabelRotulo campoNumero = this.janela.getCampoNumero();
					if(campoNumero.getText().trim().isEmpty()){
						janela.setLabelVotoBrancoVisivel(true);
						janela.setVotoNulo(true);
					}
				}
				else if(evento.getSource() == this.janela.getBotaoCorrigir()){ //botao Corrigir
					this.janela.limpaCampos();
				}
				else if(evento.getSource() == this.janela.getBotaoConfirmar()){ //botao Confirmar
					//JanelaMensagem.mostraMensagemErro(janela, "Ainda nao foi implementado");
					LabelRotulo campoNumero = this.janela.getCampoNumero();
					Cargo cargo = null;
					Votacao votacao = null;
					int resultadoInsercao = BancoDeDados.RESULTADO_ERRO_DESCONHECIDO;
					if(janela.getCargoAtual() == null){
						return;
					}
					boolean votou = false;
					if(janela.isVotoBranco()){ //voto em branco
						//JanelaMensagem.mostraMensagem(this.janela, "Confimar", "Agora so faltar atualizar na tabela cargo_votacao o numero de Votos BRANCOS");
						votacao = janela.getVotacao();
						cargo = janela.getCargoAtual();
						resultadoInsercao = votacao.atualizaBrancos(cargo);
						votou = true;
					}
					else if(campoNumero.getText().trim().length() == this.janela.getNumeroDigitosCargo()){
						int numeroCandidato = 0;
						numeroCandidato = Integer.parseInt(campoNumero.getText().trim());
						cargo = janela.getCargoAtual();
						List<Candidato> candidato = null;
						if(cargo != null){
							candidato = Candidato.pesquisar(numeroCandidato, cargo.getNome());
						}
						if(candidato != null && candidato.size() > 0){ //achou
							//salva na tabela candidato_votacao
							/*JanelaMensagem.mostraMensagem(this.janela, "Confimar", "Agora so faltar salvar na tabela candidato_votacao o candidato: " +
									candidato.get(0).getNome().toUpperCase());*/
							votacao = janela.getVotacao();
							resultadoInsercao = votacao.insereCandidato(candidato.get(0));
						}
						else{ //voto nulo
							//candidato inexistente
							votacao = janela.getVotacao();
							cargo = janela.getCargoAtual();
							resultadoInsercao = votacao.atualizaNulos(cargo);
						}
						votou = true;
					}
					if(votou){
						if (resultadoInsercao == BancoDeDados.RESULTADO_SUCESSO){
							//nada a fazer
							AudioClip somCliqueConfirma = null;
							somCliqueConfirma = Applet.newAudioClip(Recursos.getResource("sons/somUrnaConfirma.wav"));
							somCliqueConfirma.play();
						}
						else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Erro ao computar voto");
						}
						janela.proximoCargo();
					}
				}
			}//botao esquerdo
		}
	}

}
