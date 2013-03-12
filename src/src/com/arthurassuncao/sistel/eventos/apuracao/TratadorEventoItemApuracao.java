package com.arthurassuncao.sistel.eventos.apuracao;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.votacao.Votacao;
import com.arthurassuncao.sistel.classes.votacao.Votacao.NomeCandidatoVotos;
import com.arthurassuncao.sistel.gui.apuracao.JanelaApuracao;

/** Classe para tratar os eventos de item da janela <code>JanelaApuracao</code>
 * @author Arthur Assunção
 * 
 * 
 * @see ItemListener
 */
public class TratadorEventoItemApuracao implements ItemListener {

	private JanelaApuracao janela;
	
	/** Cria uma instancia do Tratador de eventos de item da janela <code>JanelaApuracao</code>
	 * @param janela <code>JanelaApuracao</code> que sera manipulada
	 */
	public TratadorEventoItemApuracao(JanelaApuracao janela){
		this.janela = janela;
	}
	
	/** Trata o evento de mudança de estado dos <code>JComboBox</code>, com isso a mudança dos graficos.
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent evento) {
		String cargoSelecionado = null;
		if (evento.getStateChange() == ItemEvent.SELECTED){
			if (evento.getSource() instanceof JComboBox){
				cargoSelecionado = (String)( ((JComboBox<String>)evento.getSource()).getSelectedItem() );
			}
			//Cargo
			if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarCargo()){ //mudou um cargo
				if (!cargoSelecionado.equals(JanelaApuracao.TEXTO_SELECIONE_CARGO)){
					this.mudaGrafico(cargoSelecionado);
				}
				else{
					janela.removeGrafico();
				}
			}
		}
	}
	
	/** Muda o grafico da janela <code>JanelaApuracao</code> setando os valores de acordo com o cargo.
	 * @param cargo <code>String</code> com o cargo selecionado
	 */
	private void mudaGrafico(String cargo){
		Data dataVotacao = janela.getDataVotacao();
		List<NomeCandidatoVotos> nomeCandidatosVotos;
		
		//pega os candidatos
		nomeCandidatosVotos = Votacao.getListaCandidatosVotos(dataVotacao, cargo);
		
		//adiciona os brancos e indecisos
		nomeCandidatosVotos.add(new NomeCandidatoVotos("Em Branco", Votacao.getNumeroVotosBrancos(cargo, dataVotacao)));
		nomeCandidatosVotos.add(new NomeCandidatoVotos("Nulos", Votacao.getNumeroVotosNulos(cargo, dataVotacao)));
		
		janela.setDadosGrafico(nomeCandidatosVotos);
	}
	
}