package too.trabalho.eventos.candidato;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import too.trabalho.gui.Janela;
import too.trabalho.gui.candidato.JanelaCadastrarCandidato;

/** Classe para tratar os eventos do teclado da janela <code>JanelaCadastrarCandidato</code>
 * @author Arthur Assunção
 * 
 *
 * @see KeyAdapter
 */
public class TratadorEventosTecladoCadastrarCandidato extends KeyAdapter {
	
	private JanelaCadastrarCandidato janela;
	
	/** Cria uma instancia do Tratador de eventos do teclado da janela <code>JanelaCadastrarCandidato</code> do pacote {@link too.trabalho.gui.candidato}
	 * @param janela <code>JanelaCadastrarCandidato</code> que sera manipulada
	 */
	public TratadorEventosTecladoCadastrarCandidato(JanelaCadastrarCandidato janela){
		this.janela = janela;
	}

	/** Trata o evento de soltar o botão apos pressionar a tecla do teclado nos componentes da janela
	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent evento) {
		// TODO Auto-generated method stub
		super.keyReleased(evento);
		if (evento.getSource() instanceof JTextField){
			JTextField campoTexto = (JTextField)evento.getSource();
			if (campoTexto == janela.getCampoNumero()){
				if (campoTexto.getText().length() >= 2){
					JComboBox<String> caixaSelecionavel = janela.getCampoSelecionarPartido();
					String sigla;
					try{
						int numeroPartido = Integer.parseInt(campoTexto.getText().substring(0, 2));
						sigla = janela.getSiglaPartidoByNumero( numeroPartido );
						caixaSelecionavel.setSelectedItem(sigla);
					}
					catch(NumberFormatException e){
						//e.printStackTrace();
						caixaSelecionavel.setSelectedItem(Janela.COMBO_BOX_TEXTO_SELECIONE);
					}
				}// ja digitou a legenda do partido
			}// é o campoNumero
		}// if é um JTextField
	}
	
	
}
