package com.arthurassuncao.sistel.eventos.pesquisa;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.gui.Janela;
import com.arthurassuncao.sistel.gui.pesquisa.JanelaCadastrarPesquisa;

/** Classe para tratar os eventos de item da janela <code>JanelaCadastrarPesquisa</code>
 * @author Arthur Assunção
 * 
 * 
 * @see ItemListener
 *
 */
public class TratadorEventoItemCadastrarPesquisa implements ItemListener {

	JanelaCadastrarPesquisa janela;
	
	/** Cria uma instancia do Tratador de eventos de item da janela JanelaCadastrarPesquisa
	 * @param janela <code>JanelaCadastrarPesquisa</code> que sera manipulada
	 */
	public TratadorEventoItemCadastrarPesquisa(JanelaCadastrarPesquisa janela){
		this.janela = janela;
	}
	
	/** Trata o evento de mudança de estado do <code>JComboBox</code> cargo e adiciona os candidatos à tabela de acordo com o cargo.
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent evento) {
		// TODO Auto-generated method stub
		String cargoSelecionado = null;
		if (evento.getStateChange() == ItemEvent.SELECTED){
			if (evento.getSource() instanceof JComboBox){
				//Cargo ou Sigla, depende do JComboBox
				cargoSelecionado = (String)( ((JComboBox<String>)evento.getSource()).getSelectedItem() );
			}
			if (cargoSelecionado != null && !cargoSelecionado.equals(Janela.COMBO_BOX_TEXTO_SELECIONE)){
				if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarCargo()){
					List<Candidato> candidatos = Candidato.pesquisarByCargo(cargoSelecionado);
					janela.limpaTabela();
					janela.addLinhasTabela(candidatos);
						return;
				}
			}
		}
	}
}
