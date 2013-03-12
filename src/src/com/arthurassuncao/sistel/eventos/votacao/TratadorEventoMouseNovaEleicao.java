package com.arthurassuncao.sistel.eventos.votacao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.votacao.Votacao;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.gui.votacao.JanelaNovaEleicao;
import com.arthurassuncao.sistel.persistencia.BancoDeDados;

/** Classe para tratar os eventos do mouse da janela <code>JanelaNovaEleicao</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventoMouseNovaEleicao extends MouseAdapter {
	
	private JanelaNovaEleicao janela;
	
	/** Cria uma instancia do Tratador de eventos do mouse da janela <code>JanelaNovaEleicao</code>
	 * @param janela <code>JanelaNovaEleicao</code> que sera manipulada
	 */
	public TratadorEventoMouseNovaEleicao(JanelaNovaEleicao janela){
		this.janela = janela;
	}

	/** Trata eventos de clique do mouse nos componentes da janela, em caso de erro uma mensagem é exibida ao usuario informando o erro
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {
		super.mouseClicked(evento);
		if(evento.getButton() == MouseEvent.BUTTON1){ //botao esquerdo
			if(evento.getSource() instanceof JButton){
				if(evento.getSource() == janela.getBotaoAdiciona()){ //adiciona na coluna listaCargosUsados
					JList<String> listaCargos = janela.getListaCargos();
					JList<String> listaCargosUsados = janela.getListaCargosUsados();
					
					String cargoSelecionado = listaCargos.getSelectedValue(); //String
					
					DefaultListModel<String> modeloListaCargosUsados = (DefaultListModel<String>)listaCargosUsados.getModel();
					DefaultListModel<String> modeloListaCargos = (DefaultListModel<String>)listaCargos.getModel();
					
					if(!modeloListaCargosUsados.contains(cargoSelecionado) && cargoSelecionado != null){
						modeloListaCargosUsados.addElement(cargoSelecionado); //adiciona na lista de cargos usados
						modeloListaCargos.removeElement(cargoSelecionado); //remove da lista de cargos disponiveis
					}
					janela.ordenaLista(listaCargos);
					janela.ordenaLista(listaCargosUsados);
					
					if(modeloListaCargos.size() > 0){
						listaCargos.setSelectedIndex(0);
					}
				}
				else if(evento.getSource() == janela.getBotaoRemove()){ //remove da listaCargosUsados
					JList<String> listaCargos = janela.getListaCargos();
					JList<String> listaCargosUsados = janela.getListaCargosUsados();
					
					String cargoSelecionado = listaCargosUsados.getSelectedValue(); //String
					
					DefaultListModel<String> modeloListaCargosUsados = (DefaultListModel<String>)listaCargosUsados.getModel();
					DefaultListModel<String> modeloListaCargos = (DefaultListModel<String>)listaCargos.getModel();
					
					if(!modeloListaCargos.contains(cargoSelecionado) && cargoSelecionado != null){
						modeloListaCargos.addElement(cargoSelecionado); //remove da lista de cargos usados
						modeloListaCargosUsados.removeElement(cargoSelecionado); //adiciona na lista de cargos disponiveis
					}
					janela.ordenaLista(listaCargos);
					janela.ordenaLista(listaCargosUsados);
					
					if(modeloListaCargosUsados.size() > 0){
						listaCargosUsados.setSelectedIndex(0);
					}
					
				}
				else if(evento.getSource() == janela.getBotaoLimpar()){ //limpa a lista de cargos usados
					janela.limparCamposJanela();
					
					janela.ordenaLista(janela.getListaCargos());
				}
				else if(evento.getSource() == janela.getBotaoSalvar()){ //salva
					if(janela.verificaCampos()){ //campos estao validos
						JList<String> listaCargosUsados = janela.getListaCargosUsados();
						
						DefaultListModel<String> modeloListaCargosUsados = (DefaultListModel<String>)listaCargosUsados.getModel();
						
						String[] cargosUsados = new String[modeloListaCargosUsados.size()];

						//pega os cargos selecionados pelo usuario
						for(int i=0; i < cargosUsados.length; i++){
							cargosUsados[i] = modeloListaCargosUsados.getElementAt(i); //String
						}
						//adiciona os cargos usados a lista com os cargos
						List<String> cargos = new ArrayList<String>(Arrays.asList(cargosUsados));
						Data dataVotacao = janela.getDataVotacao();

						Votacao votacao = new Votacao(dataVotacao, cargos);
						int resultadoInsercao = votacao.insere();
						if (resultadoInsercao == BancoDeDados.RESULTADO_SUCESSO){ //se cadastrar ele mostra a mensagem
							JanelaMensagem.mostraMensagem(this.janela, "Cadastro", "Cadastro realizado com sucesso");
							janela.limparCamposJanela();
							janela.dispose();
						}
						else if(resultadoInsercao == BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO){
							JanelaMensagem.mostraMensagemErro(this.janela, "Votação ja cadastrada");
						}
						else{
							JanelaMensagem.mostraMensagemErro(this.janela, "Erro ao cadastrar");
						}

					}
					else{ //campos invalidos
						JanelaMensagem.mostraMensagemErro(janela, janela.getErros());
						janela.removeErros();
					}
				}
				
			} //botao
		}// botao esquerdo
	}
	
}
