package com.arthurassuncao.sistel.app;

import com.arthurassuncao.sistel.classes.Configuracoes;
import com.arthurassuncao.sistel.gui.JanelaPrincipal;
import com.arthurassuncao.sistel.gui.temas.Temas;
import com.arthurassuncao.sistel.persistencia.BDEleicoes;

/** 
 * A Classe <code>ProgramaEleicao</code> inicia o sistema para o processo eleitoral
 *
 * @author Arthur Assunção
 * 
 * 
 * @see JanelaPrincipal
 * @see BDEleicoes
*/
public abstract class ProgramaEleicao {
	
	/** Cria uma instancia do programa
	 * @param args <code>String[]</code> com os argumentos de linha de comando, esses argumentos nao sao usados
	 * 
	 */
	public static void main(String[] args) {
		//inicia as threads
		BDEleicoes BancoDados = BDEleicoes.getInstance();
		Thread threadBancoDeDados = new Thread(BancoDados);
		threadBancoDeDados.start();

		Temas.mudaTema(); //muda para o tema padrao do sistema

		try {
			threadBancoDeDados.join();//faz o programa so iniciar apos o banco de dados iniciar
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Temas.mudaTema(Configuracoes.getInstance().getTema());
		new JanelaPrincipal(Configuracoes.getInstance().getTema());
	}
}
