package com.arthurassuncao.sistel.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.classes.partido.PartidoPolitico;
import com.arthurassuncao.sistel.gui.JanelaMensagem;

/** Classe para manipular dados dos Candidatos no banco de dados
 * 
 * @author Arthur Assunção
 * 
 * 
 * @see BDEleicoes
 *
 */
public abstract class BDCandidado extends BDEleicoes{

	/** Insere um candidato no banco de dados
	 * @param candidato <code>Candidato</code> que será inserido no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Candidato
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 */
	protected int inserir(Candidato candidato){
		String nome = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(candidato.getNome());
		int numeroCandidato = candidato.getNumeroCandidato();
		int numeroPartidoPolitico = candidato.getPartidoPolitico().getNumeroPartido();
		String cargo = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(candidato.getCargo());
		String foto = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(candidato.getEnderecoFoto());
		//(idcandidato, nome, numero, cargo, idPartidoPolitico, foto)
		String comandoInsercao = "INSERT INTO candidato VALUES";
		
		//verifica se o idPartido existe
		if(PartidoPolitico.partidoPoliticoExists(numeroPartidoPolitico)){
			//insere no banco
			int idPartidoPolitico = -1;
			try{
				// pega o id do canidato, caso nao exista recebera -1
				idPartidoPolitico = PartidoPolitico.getId(numeroPartidoPolitico);
			}
			catch(SQLException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
				return BancoDeDados.RESULTADO_ERRO_BANCO_DADOS;
			}
			if (idPartidoPolitico != -1){
				try{
					String comandoSQL = comandoInsercao + " (" +
														"NEXT VALUE FOR seq_candidato" + ", " +
														"\'" + nome + "\'" + ", " +
														numeroCandidato + ", " +
														"\'" + cargo + "\'" + ", " +
														idPartidoPolitico + ", " +
														"\'" + foto + "\'" + 
													")";
					this.executaUpdate(comandoSQL);
				}
				catch(SQLException e){
					e.printStackTrace();
					JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					return BancoDeDados.RESULTADO_ERRO_BANCO_DADOS;
				}
			}
			else{
				JanelaMensagem.mostraMensagemErro(null, "Partido inexistente, impossivel cadastrar o candidato");
				return BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO;
			}
		}
		return BancoDeDados.RESULTADO_SUCESSO;
	}
	
	/** Atualiza os dados do candidato no banco de dados
	 * @return <code>boolean</code> com <code>true</code> se os dados do candidato foram atualizados e <code>false</code> senão.
	 * @param candidato <code>Candidato</code> que será inserido no banco de dados
	 * @param id <code>int</code> com o id do candidato no banco de dados
	 * 
	 * @see Candidato
	 * @see BDCandidado#getId(int, String)
	 * 
	 */
	protected boolean atualizarDados(Candidato candidato, int id){
		String nome;
		int numeroCandidato;
		int numeroPartidoPolitico;
		String cargo;
		String foto;
		String comandoUpdate;
		String clausulaWhere;
		
		if (id <= 0){
			JanelaMensagem.mostraMensagemErro(null, "ID do candidato Invalido");
			return false;
		}
		
		nome 					 	= BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(candidato.getNome());
		numeroCandidato 		= candidato.getNumeroCandidato();
		numeroPartidoPolitico = candidato.getPartidoPolitico().getNumeroPartido();
		cargo 					 	= BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(candidato.getCargo());
		foto 					 	 	= BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(candidato.getEnderecoFoto());
		//(idcandidato, nome, numero, cargo, idPartidoPolitico, foto)
		comandoUpdate 	 	= "UPDATE candidato SET ";		
		clausulaWhere 		 	= " WHERE idCandidato=" + id;

		//verifica se o idPartido existe
		if(PartidoPolitico.partidoPoliticoExists(numeroPartidoPolitico)){
			//insere no banco
			int idPartidoPolitico = -1;
			try{
				// pega o id do canidato, caso nao exista recebera -1
				idPartidoPolitico = PartidoPolitico.getId(numeroPartidoPolitico);
			}
			catch(SQLException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
				return false;
			}
			if (idPartidoPolitico != -1){
				try{
					String comandoSQL = comandoUpdate +
													"nome=\'" + nome + "\'" + ", " +
													"numero=" + numeroCandidato + ", " +
													"cargo=\'" + cargo + "\'" + ", " +
													"idPartidoPolitico=" + idPartidoPolitico + ", " +
													"foto=\'" + foto + "\' " + 
													clausulaWhere;
					this.executaUpdate(comandoSQL);
				}
				catch(SQLException e){
					e.printStackTrace();
					JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					return false;
				}
			}
			else{
				JanelaMensagem.mostraMensagemErro(null, "Partido inexistente, impossivel atualizar o candidato");
			}
		}
		return true;
	}
	/** Exclui um candidato do banco de dados
	 * @param numeroCandidato <code>int</code> com o numero do candidato
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @return <code>boolean</code> com <code>true</code> se o candidato foi excluido e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean excluir(int numeroCandidato, String cargo) throws SQLException{
		
		this.abreConexao();
		
		BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(cargo);
		this.executaUpdate("DELETE FROM candidato WHERE numero=" + numeroCandidato + " AND cargo=\'" + cargo + "\'");
		
		this.fechaConexao();
		
		return true;
	}
	
	/** Verifica se existe um candidato com o numero e cargo especificados
	 * @param numeroCandidato <code>int</code> com o numero do candidato
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @return <code>boolean</code> com <code>true</code> se o candidato existe e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean exists(int numeroCandidato, String cargo) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		
		cargo = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(cargo);
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM candidato WHERE numero=" + numeroCandidato + " AND cargo=\'" + cargo + "\'");
		resultadoQuery.next();
		String contagemCandidatos = resultadoQuery.getString("contagem");
		if (contagemCandidatos != null){
			contagem = Integer.parseInt(contagemCandidatos);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	

	/** Verifica se existe um candidato com o numero e cargo iguais e id diferente do especificado
	 * @param numeroCandidato <code>int</code> com o numero do candidato
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param id <code>int</code> com o id do candidato no banco de dados
	 * 	@throws SQLException possível erro gerado por má configuração do banco de dados
	 * @return <code>boolean</code> com <code>true</code> se existe um outro candidato com o mesmo numero e cargo e <code>false</code> senão.
	 */
	protected boolean existsWithId(int numeroCandidato, String cargo, int id) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		
		cargo = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(cargo);
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM candidato WHERE numero=" + numeroCandidato + " AND cargo=\'" + cargo + "\' AND idCandidato <> " + id);
		resultadoQuery.next();
		String contagemCandidatos = resultadoQuery.getString("contagem");
		if (contagemCandidatos != null){
			contagem = Integer.parseInt(contagemCandidatos);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	/** Pesquisa por um candidato no banco de dados
	 * @param numeroCandidato <code>int</code> com o numero do candidato
	 * @param cargo <code>String</code> com o cargo do candidato, pode ser <code>null</code>, neste caso desconsidera o cargo na pesquisa.
	 * @return {@code List<Candidato>} com o candidato que tem o numero e cargo especificado
	 * @see List
	 * @see Candidato
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Candidato> pesquisar(int numeroCandidato, String cargo) throws SQLException{
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String nome;
		int numero;
		PartidoPolitico partidoPolitico = null;
		String enderecoFoto;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = null;
		if(cargo != null){
			resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM candidato WHERE numero=" + numeroCandidato + " AND cargo=\'" + cargo + "\'");
		}
		else{
			resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM candidato WHERE numero=" + numeroCandidato);
		}
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
    			nome 		= resultadoQuery.getString("nome");
    			enderecoFoto 	= resultadoQuery.getString("foto");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				int idPartido = Integer.parseInt(resultadoQuery.getString("idpartidopolitico"));
				partidoPolitico = PartidoPolitico.pesquisaById(idPartido);
				cargo 	= resultadoQuery.getString("cargo");
				candidatos.add(new Candidato(nome, numero, cargo, partidoPolitico, enderecoFoto));
    		}
		}
    	catch (SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return candidatos;
	}
	
	/** Pesquisa por candidatos no banco de dados
	 * @param nomeCandidato <code>String</code> com o nome do candidato ou parte do nome.
	 * @return {@code List<Candidato>} com os candidatos que tem no nome o nome especificado
	 * @see List
	 * @see Candidato
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Candidato> pesquisar(String nomeCandidato) throws SQLException{
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String nome;
		int numero;
		PartidoPolitico partidoPolitico = null;
		String enderecoFoto;
		String cargo;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		nomeCandidato = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(nomeCandidato);  //para pesquisar aspas simples no banco
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM candidato WHERE nome LIKE \'%" + nomeCandidato + "%\'");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
    			nome 		= resultadoQuery.getString("nome");
    			enderecoFoto 	= resultadoQuery.getString("foto");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				int idPartido = Integer.parseInt(resultadoQuery.getString("idpartidopolitico"));
				partidoPolitico = PartidoPolitico.pesquisaById(idPartido);
				cargo 	= resultadoQuery.getString("cargo");
				candidatos.add(new Candidato(nome, numero, cargo, partidoPolitico, enderecoFoto));
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return candidatos;
	}
	
	/** Pesquisa por candidatos no banco de dados
	 * @param nomeCandidato <code>String</code> com o nome do candidato
	 * @param cargoCandidato <code>String</code> com o cargo do candidato
	 * @return {@code List<Candidato>} com os candidatos que o nome e cargo especificado.
	 * @see List
	 * @see Candidato
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Candidato> pesquisar(String nomeCandidato, String cargoCandidato) throws SQLException{
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String nome;
		int numero;
		PartidoPolitico partidoPolitico = null;
		String enderecoFoto;
		String cargo;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		nomeCandidato = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(nomeCandidato);  //para pesquisar aspas simples no banco
		cargoCandidato = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(cargoCandidato);  //para pesquisar aspas simples no banco
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM candidato WHERE nome=\'" + nomeCandidato + "\' AND cargo=\'" + cargoCandidato + "\'");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
    			nome 		= resultadoQuery.getString("nome");
    			enderecoFoto 	= resultadoQuery.getString("foto");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				int idPartido = Integer.parseInt(resultadoQuery.getString("idpartidopolitico"));
				partidoPolitico = PartidoPolitico.pesquisaById(idPartido);
				cargo 	= resultadoQuery.getString("cargo");
				candidatos.add(new Candidato(nome, numero, cargo, partidoPolitico, enderecoFoto));
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return candidatos;
	}
	
	/** Pesquisa por candidatos no banco de dados
	 * @param cargoCandidato <code>String</code> com o cargo do candidato
	 * @return {@code List<Candidato>} com os candidatos que são do cargo especificado
	 * @see List
	 * @see Candidato
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Candidato> pesquisarByCargo(String cargoCandidato) throws SQLException{
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String nome;
		int numero;
		PartidoPolitico partidoPolitico = null;
		String enderecoFoto;
		String cargo;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		cargoCandidato = BancoDeDados.substituiAspasSimplesPorUmaValidaNoBD(cargoCandidato);  //para pesquisar aspas simples no banco
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM candidato WHERE cargo=\'" + cargoCandidato + "\'");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
    			nome 		= resultadoQuery.getString("nome");
    			enderecoFoto 	= resultadoQuery.getString("foto");
				numero = Integer.parseInt(resultadoQuery.getString("numero"));
				int idPartido = Integer.parseInt(resultadoQuery.getString("idpartidopolitico"));
				partidoPolitico = PartidoPolitico.pesquisaById(idPartido);
				cargo 	= resultadoQuery.getString("cargo");
				candidatos.add(new Candidato(nome, numero, cargo, partidoPolitico, enderecoFoto));
    		}
		}
    	catch (SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return candidatos;
	}
	
	//se nao achou retorna 0
	/** Retorna o id do candidato no banco de dados
	 * @param numeroCandidato <code>int</code> com o numero do candidato
	 * @param cargoCandidato <code>String</code> com o cargo do candidato
	 * @return <code>int</code> com o id do candidato no banco de dados, caso não encontre retorna <code>0</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected int getId(int numeroCandidato, String cargoCandidato) throws SQLException{
		int id = 0;
		
		this.abreConexao();
		ResultSet resultadoQuery = this.executaQuery("SELECT idCandidato FROM candidato WHERE numero=" + numeroCandidato + " AND cargo=\'" + cargoCandidato + "\'");
		
		resultadoQuery.next();
		id = resultadoQuery.getInt(1); //valor da coluna um, unica coluna
		
		this.fechaConexao();
		
		return id;
	}
	
	
}
