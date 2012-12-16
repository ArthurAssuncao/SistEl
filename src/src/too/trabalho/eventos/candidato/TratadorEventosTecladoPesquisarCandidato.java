package too.trabalho.eventos.candidato;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTextField;
import too.trabalho.classes.candidato.Candidato;
import too.trabalho.gui.candidato.JanelaPesquisarCandidato;

/** Classe para tratar os eventos do teclado da janela <code>JanelaPesquisarCandidato</code>
 * @author Arthur Assunção
 * 
 *
 * @see KeyAdapter
 */
public class TratadorEventosTecladoPesquisarCandidato extends KeyAdapter {

	private JanelaPesquisarCandidato janela = null;
	
	/*public TratadorEventosTecladoPesquisarCandidato(){
	}*/
	/** Cria uma instancia do Tratador de eventos do teclado da janela <code>JanelaPesquisarCandidato</code> do pacote {@link too.trabalho.gui.candidato}
	 * @param janela <code>JanelaPesquisarCandidato</code> que sera manipulada
	 */
	public TratadorEventosTecladoPesquisarCandidato(JanelaPesquisarCandidato janela){
		this.janela = janela;
	}
	
	/** Trata o evento de soltar o botão apos pressionar a tecla do teclado nos componentes da janela
	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent evento){
		// TODO Auto-generated method stub
		super.keyReleased(evento);
		if (evento.getSource() instanceof JTextField){
			if ((JTextField)evento.getSource() == janela.getCampoNome()){
				if (!janela.getCampoNome().getText().isEmpty()){
					String nome = janela.getCampoNome().getText();
					List<Candidato> candidatos = Candidato.pesquisar(nome);
					janela.addLinhasTabela(candidatos);
				}
				else{
					janela.limpaTabela();
				}
			}
			else if ((JTextField)evento.getSource() == janela.getCampoNumero()){
				if (!janela.getCampoNumero().getText().isEmpty()){
					String numero = janela.getCampoNumero().getText();
					List<Candidato> candidatos = Candidato.pesquisar(Integer.parseInt(numero), null);
					janela.addLinhasTabela(candidatos);
				}
				else{
					janela.limpaTabela();
				}
			}
		}
	}
	
}
