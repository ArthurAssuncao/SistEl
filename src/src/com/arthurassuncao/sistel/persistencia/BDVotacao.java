package com.arthurassuncao.sistel.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.classes.votacao.Votacao;
import com.arthurassuncao.sistel.classes.votacao.Votacao.NomeCandidatoVotos;
import com.arthurassuncao.sistel.gui.JanelaMensagem;

/**
 * @author Arthur Assunção
 * 
 *
 */
public abstract class BDVotacao extends BDEleicoes {
	
	/** Insere uma votação no banco de dados
	 * @param votacao <code>Votacao</code> com a votacao que será cadastrada
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	protected int insere(Votacao votacao){
		Data dataVotacao;
		dataVotacao = votacao.getData();
		
		String comandoInsercao = "INSERT INTO votacao VALUES";
		try{
			if(!this.exists(votacao.getData())){
				this.executaUpdate(comandoInsercao + " (" +
						"NEXT VALUE FOR seq_votacao" + ", " +
						"\'" + dataVotacao.getDataAAAAMMDD("-") + "\'" +
					")");
				
				//insere os cargos utilizados na votacao
				try{
					this.insereCargoVotacao(dataVotacao, votacao.getCargos());
				}
				catch(SQLException e){
					e.printStackTrace();
					this.exclui(dataVotacao); //como nao inseriu todos cargos, exclui
					JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
				}
			}//fim if exists
			else{
				return RESULTADO_ERRO_REGISTRO_DUPLICADO; //votacao ja existe
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	//insere na tabela candidato_cargo o candidato

	/** Insere um candidato na votacao, se o candidato ainda nao foi votado, o candidato é inserido na votacao com numero de votos igual a 1.
	 * 
	 * @param votacao <code>Votacao</code> com a votacao onde o candidato será cadastrado
	 * @param candidato <code>Candidato</code> com o candidato que foi votado
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao
	 * @see Candidato
	 * @see Votacao#insere(Votacao)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	protected int insereCandidato(Votacao votacao, Candidato candidato){
		String comandoInsercao = "INSERT INTO candidato_votacao (idCandidatoVotacao, data_votacao, idCandidato, numero_Votos) VALUES";
		Data dataVotacao = votacao.getData();
		int idCandidato = -1;
		int numeroVotos = 1; //se esta cadastrando é pq o candidato recebeu 1 voto
		
		try{
			if (candidato != null){
				idCandidato = candidato.getId();
				if(idCandidato != -1){
					if(!existsCandidato(dataVotacao, idCandidato)){
						this.executaUpdate(comandoInsercao + " (" +
									"NEXT VALUE FOR seq_candidato_votacao" + ", " +
									"\'" + dataVotacao.getDataAAAAMMDD("-") + "\', " +
									idCandidato + "," + 
									numeroVotos +
								")");
					}
					else{
						return RESULTADO_ERRO_REGISTRO_DUPLICADO;
					}
				}
				else{
					//nao vai chegar aki
					//JanelaMensagem.mostraMensagemErro(null, "Candidato inexistente");
					return RESULTADO_ERRO_DESCONHECIDO;
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	//atualiza o numero de votos do candidato
	//numeroVotos, provavelmente, sera sempre 1, afinal o programa atualiza depois q um eleitor votou
	/** Atualiza o numero de votos do candidato na votacao, se o candidato ainda nao foi votado, o candidato é inserido na votacao com numero de votos igual a 1.
	 * Se o candidato ja foi votado, o numero de votos é incrementado, geralmente, em 1.
	 * @param votacao <code>Votacao</code> com a votacao onde o candidato será cadastrado
	 * @param candidato <code>Candidato</code> com o candidato que foi votado
	 * @param numeroVotos  <code>int</code> com o numero de votos que serão incrementados, usualmente, será 1.
	 * @return <code>int</code> com o resultado da atualizacao. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao
	 * @see Candidato
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	protected int atualizaCandidato(Votacao votacao, Candidato candidato, int numeroVotos){
		
		//UPDATE candidato_votacao SET numero_Votos=(SELECT numero_Votos FROM candidato_votacao WHERE idCandidato=5 AND data_votacao='2012-06-25')+1 WHERE idCandidato=5 AND data_votacao='2012-06-25';
		
		Data dataVotacao = votacao.getData();
		int idCandidato = -1;
		String comandoUpdate;
		String clausulaWhere;
		int numeroVotosAtual = 0;
		
		if (candidato != null){
			idCandidato = candidato.getId();
			if(idCandidato == -1){
				//nao pode chegar aki, so se houver erro no banco de dados ou outro desconhecido
				return RESULTADO_ERRO_DESCONHECIDO;
			}
		}
		try{
			numeroVotosAtual = this.getNumeroVotos(dataVotacao, idCandidato);
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		comandoUpdate 	= "UPDATE candidato_votacao SET ";
		clausulaWhere  	= " WHERE idCandidato=" + idCandidato + " AND data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\'";
		
		try{
			if(this.existsCandidato(dataVotacao, idCandidato)){ //se o candidato existe, entao atualiza
				String comandoSQL =  comandoUpdate + 
												 "numero_Votos=" + (numeroVotosAtual + numeroVotos) + 
												 clausulaWhere;
				this.executaUpdate(comandoSQL);
			}
			else{//se nao existe insere na votacao
				this.insereCandidato(votacao, candidato);
			}
		} 
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	/** Insere cargos na votacao da data especificada
	 * @param dataVotacao <code>Data</code> data da votacao
	 * @param cargos {@code List<String> cargos} com os cargos da votacao
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * 
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 */
	protected int insereCargoVotacao(Data dataVotacao, List<String> cargos) throws SQLException{
		
		String comandoInsercao = "INSERT INTO cargo_votacao(idCargoVotacao, data_votacao, cargo) VALUES";
		try{
			if (cargos != null){
				for(String cargo : cargos){
					cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
					this.executaUpdate(comandoInsercao + " (" +
								"NEXT VALUE FOR seq_cargo_votacao" + ", " +
								"\'" + dataVotacao.getDataAAAAMMDD("-") + "\', " +
								"\'" + cargo + "\'" +
							")");
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	/** Incrementa em 1 o numero de votos nulos em um cargo na votacao
	 * @param votacao <code>Votacao</code> com a votacao
	 * @param cargo <code>String</code> com o cargo
	 * @param numeroVotos <code>int</code> com o numero de votos que serão incrementados, usualmente, será 1.
	 * @return <code>int</code> com o resultado da atualizacao. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao
	 * @see Cargo
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 */
	protected int atualizaNulos(Votacao votacao, Cargo cargo , int numeroVotos){
		Data dataVotacao = votacao.getData();
		String comandoUpdate;
		String clausulaWhere;
		int numeroVotosNulosAtual = 0;
		
		try{
			numeroVotosNulosAtual = BDVotacao.getNumeroVotosNulos(dataVotacao, cargo.getNome());
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		comandoUpdate = "UPDATE cargo_votacao SET ";
		clausulaWhere = " WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND cargo=\'" + cargo.getNome() + "\'";
		
		try{
			String comandoSQL =  comandoUpdate + 
					 "votos_nulos=" + (numeroVotosNulosAtual + numeroVotos) + 
					 clausulaWhere;
			this.executaUpdate(comandoSQL);
		} 
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	/** Incrementa em 1 o numero de votos em branco em um cargo na votacao
	 * @param votacao <code>Votacao</code> com a votacao
	 * @param cargo <code>String</code> com o cargo
	 * @param numeroVotos <code>int</code> com o numero de votos que serão incrementados, usualmente, será 1.
	 * @return <code>int</code> com o resultado da atualizacao. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao
	 * @see Cargo
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 */
	protected int atualizaBrancos(Votacao votacao, Cargo cargo , int numeroVotos){
		Data dataVotacao = votacao.getData();
		String comandoUpdate;
		String clausulaWhere;
		int numeroVotosBrancosAtual = 0;
		
		try{
			numeroVotosBrancosAtual = BDVotacao.getNumeroVotosBrancos(dataVotacao, cargo.getNome()); 
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		comandoUpdate = "UPDATE cargo_votacao SET ";
		clausulaWhere = " WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND cargo=\'" + cargo.getNome() + "\'";
		
		try{
			String comandoSQL =  comandoUpdate + 
					 "votos_brancos=" + (numeroVotosBrancosAtual + numeroVotos) + 
					 clausulaWhere;
			this.executaUpdate(comandoSQL);
		} 
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return RESULTADO_ERRO_BANCO_DADOS;
		}
		return RESULTADO_SUCESSO;
	}
	
	/** Retorna o numero de votos de um candidato numa votacao
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @param idCandidato <code>int</code> com o id do candidato
	 * @return <code>int</code> com o numero de votos
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see Data
	 */
	protected int getNumeroVotos(Data dataVotacao, int idCandidato) throws SQLException{
		int numeroVotos = 0;
		
		this.abreConexao();
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT numero_votos FROM candidato_votacao WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND idCandidato=" + idCandidato );
		resultadoQuery.next();
		String contagemNumeroVotos= resultadoQuery.getString("numero_votos");
		if (contagemNumeroVotos != null){
			numeroVotos = Integer.parseInt(contagemNumeroVotos);
		}
		
		this.fechaConexao();
		
		return numeroVotos;
	}
	
	/** Retorna o numero de votos nulos em um cargo da votacao
	 * @param cargo <code>String</code> com o cargo
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>int</code> com o numero de votos nulos
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see Data
	 */
	protected static int getNumeroVotosNulos(Data dataVotacao, String cargo) throws SQLException{
		int numeroVotos = 0;
		String nomeCargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("" +
				"SELECT votos_nulos FROM cargo_votacao WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND cargo=\'" + nomeCargo + "\'");
		resultadoQuery.next();
		String contagemNumeroVotosNulos = resultadoQuery.getString("votos_nulos");
		if (contagemNumeroVotosNulos != null){
			numeroVotos = Integer.parseInt(contagemNumeroVotosNulos);
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return numeroVotos;
	}
	
	/** Retorna o numero de votos em branco em um cargo da votacao
	 * @param cargo <code>String</code> com o cargo
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>int</code> com o numero de votos em branco
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see Data
	 */
	protected static int getNumeroVotosBrancos(Data dataVotacao, String cargo) throws SQLException{
		int numeroVotos = 0;
		if(dataVotacao == null || cargo == null){
			return numeroVotos;
		}
		
		String nomeCargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("" +
				"SELECT votos_brancos FROM cargo_votacao WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND cargo=\'" + nomeCargo + "\'");
		resultadoQuery.next();
		String contagemNumeroVotosBrancos = resultadoQuery.getString("votos_brancos");
		if (contagemNumeroVotosBrancos != null){
			numeroVotos = Integer.parseInt(contagemNumeroVotosBrancos);
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return numeroVotos;
	}
	
	/** Verifica se existe uma votacao na data especificada
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>boolean</code> com <code>true</code> se existe uma votacao na data especificada e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean exists(Data dataVotacao) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM votacao WHERE data=\'" + dataVotacao.getDataAAAAMMDD("-") + "\'" );
		resultadoQuery.next();
		String contagemVotacoes = resultadoQuery.getString("contagem");
		if (contagemVotacoes != null){
			contagem = Integer.parseInt(contagemVotacoes);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	//verifica se o candidato ja esta cadastrado numa votacao
	/** Verifica se o candidato existe na votacao da data especificada
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @param idCandidato <code>int</code> com o id do candidato
	 * @return <code>boolean</code> com <code>true</code> se existe uma votacao na data especificada e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean existsCandidato(Data dataVotacao, int idCandidato) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM candidato_votacao WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND idCandidato=" + idCandidato );
		resultadoQuery.next();
		String contagemCandidatoVotacao = resultadoQuery.getString("contagem");
		if (contagemCandidatoVotacao != null){
			contagem = Integer.parseInt(contagemCandidatoVotacao);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	/** Verifica se existe uma votacao para o dia atual
	 * @return <code>boolean</code> com <code>true</code> se existe e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static boolean isDiaDeVotacao() throws SQLException{
		int contagem = 0;
		Data dataAtual = new Data();
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM votacao WHERE data=\'" + dataAtual.getDataAAAAMMDD("-") + "\'" );
		resultadoQuery.next();
		String contagemVotacoes = resultadoQuery.getString("contagem");
		if (contagemVotacoes != null){
			contagem = Integer.parseInt(contagemVotacoes);
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	/** Retorna o id da votacao, caso a votacao não exista retorna -1
	 * @param dataVotacao <code>Data</code> data da votacao
	 * @return <code>int</code> com o id da votacao, caso a votacao não exista retorna -1
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected int getId(Data dataVotacao) throws SQLException{
		int id = -1;
		
		this.abreConexao();
		//Para salvar o tipo Date no HSQL, basta usar a data no formato aaaa-mm-dd, exemplo: '2012-12-25'
		ResultSet resultadoQuery = this.executaQuery("SELECT idVotacao FROM votacao WHERE data=\'" + dataVotacao.getDataAAAAMMDD("-") + "\'" );
		if (resultadoQuery.next()){
			id = resultadoQuery.getInt(1); //valor da coluna um, unica coluna
		}
		
		this.fechaConexao();
		
		return id;
	}
	
	/** Exclui uma votacao com a data especificada
	 * @param dataVotacao <code>Data</code> data da votacao
	 * @return <code>boolean</code> com <code>true</code> se a votacao foi excluida e <code>false</code> senão
	 */
	protected boolean exclui(Data dataVotacao){
		boolean excluiu = false;
		try{
			this.abreConexao();
			
			this.executaUpdate("DELETE FROM votacao WHERE data=\'" + dataVotacao.getDataAAAAMMDD("-") + "\'");
			
			this.fechaConexao();
		}
		catch(SQLException e){
			e.printStackTrace();
			excluiu = false;
		}
		return excluiu;
	}
	
	//com a atual estrutura do banco de dados, so retorna uma votacao, afinal data é chave primaria
	/** Retorna a votacao referente a data especificada
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>Votacao</code> com a votacao, se não encontrar uma votacao com a data especificada, retorna <code>null</code>
	 * @see Data
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static Votacao pesquisa(Data dataVotacao) throws SQLException{
		Votacao votacao = null;
		List<Cargo> listaCargos = null;
		
		listaCargos = BDVotacao.getAllCargos(dataVotacao);
		
		if(listaCargos != null && listaCargos.size() > 0){
			votacao = new Votacao(dataVotacao, listaCargos, 0);
		}
		
		return votacao;
	}
	
	/** Retorna uma lista com as votacoes que tenham o cargo especifico
	 * @param cargo <code>String</code> com o cargo
	 * @param datasPosteriores <code>boolean</code> com <code>true</code> se a pesquisa não ira desconsiderar datas posteriores a data atual e <code>false</code> senão.
	 * @return {@code List<Votacao>} com a lista de votacoes, se nenhuma votacao for encontrada retorna uma lista vazia.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Votacao> pesquisa(String cargo, boolean datasPosteriores) throws SQLException{
	 	//SELECT DISTINCT data_votacao FROM cargo_votacao WHERE cargo='Presidente'
		List<Votacao> votacoes = new ArrayList<Votacao>();
		String comandoSQL = null;
		List<String> listaData = new ArrayList<String>();
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		if(datasPosteriores){
			comandoSQL = "SELECT DISTINCT data_votacao FROM cargo_votacao WHERE cargo LIKE\'%" + cargo + "%\' ORDER BY data_votacao DESC";
		}
		else{
			comandoSQL = "SELECT DISTINCT data_votacao FROM cargo_votacao WHERE cargo LIKE\'%" + cargo + "%\' AND data_votacao <= (SELECT current_date  FROM cargo_votacao LIMIT 1) ORDER BY data_votacao DESC";
		}

		//pega todas as datas com determinado cargo
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comandoSQL);
		while (resultadoQuery.next()){
			listaData.add(resultadoQuery.getString("data_votacao"));
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		for(int i =0; i < listaData.size(); i++){
			Data data = new Data(Data.AAAAMMDDtoDDMMAAAA(listaData.get(i)));
			Votacao votacao = new Votacao(data, BDVotacao.getAllCargos(data), 0);
			votacoes.add(votacao);
		}
		
		return votacoes;
	}
	
	/** Retorna uma lista com os nomes dos candidatos e seus respectivos numero de votos na votacao. Se o candidato não obteve votos, 
	 * portanto não foi inserido na tabela, é considerado que obteve 0 votos. 
	 * Se o cargo for <code>null</code>, o cargo não é considerado e são retornados todos os candidatos, independende do cargo.
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @param cargo <code>String</code> com o cargo, caso seja <code>null</code>, o cargo é desconsiderado.
	 * @return {@code List<NomeCandidatoVotos>} com a lista com os nomes dos candidatos e seus respectivos numeros de votos.
	 * @see NomeCandidatoVotos
	 * @see Data
	 */
	protected static List<NomeCandidatoVotos> getListaCandidatosVotos(Data dataVotacao, String cargo){
		//SELECT nome AS nome_candidato, numero_votos FROM candidato_votacao INNER JOIN candidato ON candidato_votacao.idcandidato=candidato.idcandidato WHERE data_votacao='2012-06-25';
		List<NomeCandidatoVotos> candidatosVotos = new ArrayList<NomeCandidatoVotos>(); //lista com os nomes dos candidatos e o numero de votos
		String nomeCandidato;
		int numeroVotos = 0;
		
		try{
			BANCO_DE_DADOS_ELEICOES.abreConexao();
			String comando;
			//pega as colunas Nome da tabela candidato e numero de votos da tabela candidato_pesquisa onde o id da pesquisa é idPesquisa
			if(cargo == null){
				//comando = "SELECT nome AS nome_candidato, numero_votos FROM candidato_votacao INNER JOIN candidato ON candidato_votacao.idcandidato=candidato.idcandidato WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' ORDER BY nome_candidato";
				comando = "SELECT nome AS nome_candidato, numero_votos FROM candidato_votacao RIGHT JOIN candidato ON candidato_votacao.idcandidato=candidato.idcandidato WHERE (data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' OR data_votacao IS NULL) ORDER BY nome_candidato";
			}
			else{
				cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
				//comando = "SELECT nome AS nome_candidato, numero_votos FROM candidato_votacao INNER JOIN candidato ON candidato_votacao.idcandidato=candidato.idcandidato WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' AND candidato.cargo=\'" + cargo + "\' ORDER BY nome_candidato";
				comando = "SELECT nome AS nome_candidato, numero_votos FROM candidato_votacao RIGHT JOIN candidato ON candidato_votacao.idcandidato=candidato.idcandidato WHERE (data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' OR data_votacao IS NULL) AND candidato.cargo=\'" + cargo + "\' ORDER BY nome_candidato";
			}
				
			ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
			try {
				String numeroDeVotos = "";
				while(resultadoQuery.next()){
					nomeCandidato = resultadoQuery.getString("nome_candidato");
					try{
						numeroDeVotos = resultadoQuery.getString("numero_votos");
						if(numeroDeVotos != null){
							numeroVotos = Integer.parseInt(numeroDeVotos);
						}
						else{
							numeroVotos = 0;
						}
						candidatosVotos.add(new NomeCandidatoVotos(nomeCandidato, numeroVotos));
					}
					catch(SQLException e){
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
						//impossivel converter para numero
						return null;
						//numeroVotos = 0;
					}
				}
			}
	    	catch (SQLException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
				return null;
			}
			BANCO_DE_DADOS_ELEICOES.fechaConexao();
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return null;
		}
		
		return candidatosVotos;
	}
	
	/** Retorna todos os cargos da votacao com a data especificada.
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return {@code List<Cargo>} com os cargos da votacao
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Cargo> getAllCargos(Data dataVotacao) throws SQLException{
		List<Cargo> cargos = new ArrayList<Cargo>();
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		//ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT cargo FROM cargo_votacao WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\'" );
		//retorna os cargos de uma determinada votacao pela data e ordenado crescentemente de acordo com o numero de digitos do cargo
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT cargo FROM cargo_votacao INNER JOIN cargo ON cargo_votacao.cargo=cargo.nome WHERE data_votacao=\'" + dataVotacao.getDataAAAAMMDD("-") + "\' ORDER BY numeroDigitos ASC" );
		while (resultadoQuery.next()){
			cargos.add(Cargo.getCargo(resultadoQuery.getString("cargo")));
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return cargos;
	}
	
}
