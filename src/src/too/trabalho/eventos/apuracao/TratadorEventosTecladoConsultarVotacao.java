package too.trabalho.eventos.apuracao;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JTextField;

import too.trabalho.classes.votacao.Votacao;
import too.trabalho.gui.apuracao.JanelaConsultaVotacao;

/** Classe para tratar os eventos de item da janela <code>JanelaConsultaVotacao</code>
 * @author Arthur Assunção
 * 
 * 
 * @see MouseAdapter
 */
public class TratadorEventosTecladoConsultarVotacao extends KeyAdapter {

	private JanelaConsultaVotacao janela = null;
	
	/*public TratadorEventosTecladoConsultarVotacao(){
	}*/
	
	/** Cria uma instancia do Tratador de eventos do mouse janela <code>JanelaConsultaVotacao</code>
	 * @param janela <code>JanelaConsultaVotacao</code> que sera manipulada
	 */
	public TratadorEventosTecladoConsultarVotacao(JanelaConsultaVotacao janela){
		this.janela = janela;
	}
	
	/** Trata o evento do clique do mouse na <code>JanelaConsultaVotacao</code>
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void keyReleased(KeyEvent evento){
		super.keyReleased(evento);
		if (evento.getSource() instanceof JTextField){
			if ((JTextField)evento.getSource() == janela.getCampoCargo()){
				if (!janela.getCampoCargo().getText().isEmpty()){
					String cargo = janela.getCampoCargo().getText();
					List<Votacao> votacoes = Votacao.pesquisa(cargo, true); //considera datas posteriores a data atual
					janela.addLinhasTabela(votacoes);
				}
				else{
					janela.limpaTabela();
				}
			}
		}
		/*else if(evento.getSource() instanceof Calendario){
			if ((Calendario)evento.getSource() == janela.getCampoData()){
				if (!janela.getCampoData().getText().isEmpty()){
					Data dataVotacao = new Data(janela.getCampoData().getData());
					List<Votacao> votacoes = new ArrayList<Votacao>();
					votacoes.add(Votacao.pesquisa(dataVotacao));
					janela.addLinhasTabela(votacoes);
				}
				else{
					janela.limpaTabela();
				}
			}
		}*/
	}
	
}
