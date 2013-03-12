package com.arthurassuncao.sistel.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.classes.partido.PartidoPolitico;
import com.arthurassuncao.sistel.gui.JanelaMensagem;

/**
 * @author Arthur Assunção
 * 
 *
 */
public abstract class BDPartido extends BDEleicoes{
	
	/** Insere um partido no banco de dados
	 * @param partido <code>PartidoPolitico</code> com o partido que sera cadastrado no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	protected int inserir(PartidoPolitico partido){
		String sigla 	= substituiAspasSimplesPorUmaValidaNoBD(partido.getSigla());
		String nome 	= substituiAspasSimplesPorUmaValidaNoBD(partido.getNome());
		int numero 	= partido.getNumeroPartido();
		
		String comandoInsercao = "INSERT INTO partido_politico VALUES";
		
		try{
			if(!this.exists(numero, sigla)){
				try{
					nome = substituiAspasSimplesPorUmaValidaNoBD(nome); //para adicionar aspas simples no banco
					this.executaUpdate(comandoInsercao + " (" +
								"NEXT VALUE FOR seq_partido_politico" + ", " +
								"\'" + sigla.toUpperCase() + "\'" + ", " +
								"\'" + nome + "\'" + ", " +
								numero + 
							")");
				}
				catch(SQLException e){
					e.printStackTrace();
					JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					return RESULTADO_ERRO_BANCO_DADOS;
				}
			}
			else{
				//JanelaMensagem.mostraMensagemErro(null, "Cargo já existe");
				return RESULTADO_ERRO_REGISTRO_DUPLICADO; //ja existe
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	/** Exclui um partido do banco de dados
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return <code>boolean</code> com <code>true</code> se o cargo foi excluido e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean excluir(int numeroPartido) throws SQLException{
		this.abreConexao();
		
		this.executaUpdate("DELETE FROM partido_politico WHERE numero=" + numeroPartido);
		
		this.fechaConexao();
		
		return true;
	}
	
	/** Verifica se existe um partido com o numero ou a sigla informada no banco de dados
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @param sigla <code>String</code> com a sigla do partido
	 * @return <code>boolean</code> com <code>true</code> se o partido existe e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean exists(int numeroPartido, String sigla) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		
		String comandoSQL;
		if(sigla != null){
			comandoSQL = "SELECT COUNT(*) AS contagem FROM partido_politico WHERE numero=" + numeroPartido + " OR sigla=\'" + sigla + "\'";
		}
		else{
			comandoSQL = "SELECT COUNT(*) AS contagem FROM partido_politico WHERE numero=" + numeroPartido;
		}
		
		ResultSet resultadoQuery = this.executaQuery(comandoSQL);
		resultadoQuery.next();
		String contagemPartidos = resultadoQuery.getString("contagem");
		if (contagemPartidos != null){
			contagem = Integer.parseInt(contagemPartidos);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	/** Pesquisa por um partido no banco de dados atravez de um numero especifica
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return um {@code List<PartidoPolitico>} com o partido, se não houver um partido com o numero fornecido retorna uma lista vazia, 
	 * se retornar mais de um partido, significa que há inconsistencias no banco de dados
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<PartidoPolitico> pesquisaPartidoPolitico(int numeroPartido) throws SQLException{
		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		String sigla;
		String nome;
		int numero;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM partido_politico WHERE numero=" + numeroPartido);
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
				sigla 	= resultadoQuery.getString("sigla");
				nome 	= resultadoQuery.getString("nome");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				partidos.add(new PartidoPolitico(sigla, nome, numero));
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return partidos;
	}
	
	/** Pesquisa um partido pelo seu id no banco de dados
	 * @param idPartido <code>int</code> com o id do partido procurado
	 * @return um <code>PartidoPolitico</code> com os dados referentes ao id do partido fornecido, se o partido não for encontrado retorna <code>null</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static PartidoPolitico pesquisaById(int idPartido) throws SQLException{
		PartidoPolitico partido = null;
		String sigla;
		String nome;
		int numero;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM partido_politico WHERE idPartidoPolitico=" + idPartido);
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		//while(resultadoQuery.next()){
    		resultadoQuery.next();
			sigla 		= resultadoQuery.getString("sigla");
			nome 	= resultadoQuery.getString("nome");
			numero = Integer.parseInt(resultadoQuery.getString("numero"));
			partido = new PartidoPolitico(sigla, nome, numero);
    		//}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return partido;
	}
	
	/** Pesquisa por um partido no banco de dados atravez de uma sigla especifica
	 * @param siglaPartido <code>String</code> com o numero do partido
	 * @return um {@code List<PartidoPolitico>} com o partido, se não houver um partido com o numero fornecido retorna uma lista vazia, 
	 * se retornar mais de um partido, significa que há inconsistencias no banco de dados
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<PartidoPolitico> pesquisaPartidoPolitico(String siglaPartido) throws SQLException{
		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		String sigla;
		String nome;
		int numero;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM partido_politico WHERE sigla=\'" + siglaPartido.toUpperCase() + "\'");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
				sigla 		= resultadoQuery.getString("sigla");
				nome 	= resultadoQuery.getString("nome");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				partidos.add(new PartidoPolitico(sigla, nome, numero));
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return partidos;
	}
	
	/** Retorna o id do partido referente ao numero do partido fornecido
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return <code>int</code> com o id do partido politico no banco de dados.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static int getId(int numeroPartido) throws SQLException{
		int id = -1;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT idPartidoPolitico FROM partido_politico WHERE numero=\'" + numeroPartido + "\'");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	resultadoQuery.next();
    	String idPartido = resultadoQuery.getString("idPartidoPolitico");
    	if (idPartido != null){
    		id = Integer.parseInt(idPartido);
    	}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return id;
	}
	/** Retorna {@code List<PartidoPolitico>} com todos os partidos cadastrados no banco de dados, se não há partidos cadastrados retorna uma lista vazia
	 * @return {@code List<PartidoPolitico>} com todos os partidos cadastrados no banco de dados, se não há partidos cadastrados retorna uma lista vazia
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<PartidoPolitico> getAll() throws SQLException{
		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		String sigla;
		String nome;
		int numero;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM partido_politico");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
				sigla 		= resultadoQuery.getString("sigla");
				nome 	= resultadoQuery.getString("nome");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				partidos.add(new PartidoPolitico(sigla, nome, numero));
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return partidos;
	}
	
}
