package too.trabalho.eventos.candidato;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import too.trabalho.gui.Janela;
import too.trabalho.gui.candidato.JanelaCadastrarCandidato;
import too.trabalho.persistencia.BDEleicoes;

/** Classe para tratar os eventos de item da janela <code>JanelaCadastrarCandidato</code>
 * @author Arthur Assunção
 * 
 * 
 * @see ItemListener
 *
 */
public class TratadorEventoItemCandidato implements ItemListener {

	JanelaCadastrarCandidato janela;
	int numeroDigitos;
	int numeroPartido;
	
	/** Cria uma instancia do Tratador de eventos de item da janela JanelaCadastrarCandidato
	 * @param janela <code>JanelaCadastrarCandidato</code> que sera manipulada
	 */
	public TratadorEventoItemCandidato(JanelaCadastrarCandidato janela){
		this.janela = janela;
	}
	
	/** Trata o evento de mudança de estado dos <code>JComboBox</code>
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent evento) {
		// TODO Auto-generated method stub
		String opcaoSelecionada = null;
		if (evento.getStateChange() == ItemEvent.SELECTED){
			if (evento.getSource() instanceof JComboBox){
				//Cargo ou Sigla, depende do JComboBox
				opcaoSelecionada = (String)( ((JComboBox<String>)evento.getSource()).getSelectedItem() );
			}
			if (opcaoSelecionada != null && !opcaoSelecionada.equals(Janela.COMBO_BOX_TEXTO_SELECIONE)){
				if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarCargo()){
					this.numeroDigitos = janela.getNumeroDigitosByCargo(opcaoSelecionada);
						janela.setMaxlengthCampoNumero(this.numeroDigitos);
						janela.removeExcessoCaracteresCampoNumero();
						return;
				}
				else if((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarPartido()){
						this.numeroPartido = janela.getNumeroPartidoBySigla(opcaoSelecionada);
						if (this.numeroPartido != 0){
							if (janela.getCampoNumero() != null){
								janela.getCampoNumero().setText(String.valueOf(this.numeroPartido));
							}
						}
				}
				if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarCargo()){
					janela.setMaxlengthCampoNumero(BDEleicoes.TAMANHO_CANDIDATO_NUMERO);
				}
			}
		} // fim if state
	}

}
