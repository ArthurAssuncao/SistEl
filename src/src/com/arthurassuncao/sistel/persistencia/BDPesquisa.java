package com.arthurassuncao.sistel.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa.IdCandidatoVotos;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa.NomeCandidatoVotos;
import com.arthurassuncao.sistel.gui.JanelaMensagem;

/** Classe para manipular dados das pesquisas no banco de dados
 * @author Arthur Assunção
 * 
 * 
 * @see BDEleicoes
 */
public abstract class BDPesquisa extends BDEleicoes {
	
	/** Insere uma pesquisa no banco de dados
	 * @param pesquisa <code>Pesquisa</code> que será inserida no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	protected int insere(Pesquisa pesquisa){
		String cargo = pesquisa.getCargo();
		Data dataInicio = pesquisa.getDataInicio();
		Data dataFim = pesquisa.getDataFim();
		int numeroVotosNulos = pesquisa.getNumeroVotosNulosBrancos();
		int numeroVotosIndecisos = pesquisa.getNumeroVotosIndecisos();
		int numeroPessoasEntrevistadas = pesquisa.getNumeroPessoasEntrevistadas();
		int numeroMunicipiosPesquisados = pesquisa.getNumeroMunicipiosPesquisados();
		
		String comandoInsercao = "INSERT INTO pesquisa VALUES";
		
		try{
			if(!this.exists(cargo, dataInicio, dataFim)){
				try{
					cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo); //para adicionar aspas simples no banco
					this.executaUpdate(comandoInsercao + " (" +
								"NEXT VALUE FOR seq_pesquisa" + ", " +
								"\'" + cargo + "\'" + "," + 
								"\'" + dataInicio.getDataAAAAMMDD("-") + "\'" + "," +
								"\'" + dataFim.getDataAAAAMMDD("-") + "\'" + "," +
								numeroVotosNulos + "," +
								numeroVotosIndecisos + "," +
								numeroPessoasEntrevistadas + "," +
								numeroMunicipiosPesquisados + 
							")");
					
					//insere os candidatos
					int idPesquisa = -1; //nulo
					try{
						idPesquisa = BDPesquisa.getId(cargo, dataInicio, dataFim);
						if (idPesquisa != -1){
							List<IdCandidatoVotos> candidatos = pesquisa.getIdCandidatoVotos();
							if(candidatos != null && candidatos.size() > 0){
								this.insereCandidatoPesquisa(idPesquisa, candidatos);
							}
						}
						else{
							return RESULTADO_ERRO_BANCO_DADOS;
						}
					}
					catch(SQLException e){
						e.printStackTrace();
						//apaga a pesquisa que foi criada
						this.excluir(idPesquisa);
						JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
						return RESULTADO_ERRO_BANCO_DADOS;
					}
					
				}
				catch(SQLException e){
					e.printStackTrace();
					JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					return RESULTADO_ERRO_BANCO_DADOS;
				}
			}
			else{
				//JanelaMensagem.mostraMensagemErro(null, "Pesquisa já existe");
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
	
	/** Verifica se existe uma pesquisa com o cargo, data de inicio e data de fim especificos no banco de dados.
	 * @param cargo <code>String</code> com o cargo da pesquisa
	 * @param dataInicio <code>Data</code> com a data de inicio da pesquisa
	 * @param dataFim <code>Data</code> com a data de fim da pesquisa
	 * @return <code>boolean</code> com <code>true</code> se o candidato existe e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see Data
	 */
	protected boolean exists(String cargo, Data dataInicio, Data dataFim) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM pesquisa WHERE cargo=\'" + cargo + "\' AND data_Inicio=\'" + dataInicio.getDataAAAAMMDD("-") + "\' AND data_Fim=\'" + dataFim.getDataAAAAMMDD("-") + "\'" );
		resultadoQuery.next();
		String contagemPartidos = resultadoQuery.getString("contagem");
		if (contagemPartidos != null){
			contagem = Integer.parseInt(contagemPartidos);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	/** Retorna o id da pesquisa referente ao cargo, data inicio e data fim especificados.
	 * @param cargo <code>String</code> com o cargo da pesquisa
	 * @param dataInicio <code>Data</code> com a data de inicio da pesquisa
	 * @param dataFim <code>Data</code> com a data de fim da pesquisa
	 * @return <code>int</code> com o id da pesquisa, se não encontrar retorna -1
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see Data
	 */
	protected static int getId(String cargo, Data dataInicio, Data dataFim) throws SQLException{
		int id = -1;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		//Para salvar o tipo Date no HSQL, basta usar a data no formato aaaa-mm-dd, exemplo: '2012-12-25'
		cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT idPesquisa FROM pesquisa WHERE cargo=\'" + cargo + "\' AND data_Inicio=\'" + dataInicio.getDataAAAAMMDD("-") + "\' AND data_Fim=\'" + dataFim.getDataAAAAMMDD("-") + "\'" );
		if (resultadoQuery.next()){
			id = resultadoQuery.getInt(1); //valor da coluna um, unica coluna
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return id;
	}
	
	/** Insere um candidato e o numero de votos em uma pesquisa no banco de dados
	 * @param idPesquisa <code>int</code> com o id da pesquisa que o candidato participou
	 * @param candidatos {@code List<IdCandidatoVotos>} com o id e o numero de votos dos candidatos na pesquisa
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	private int insereCandidatoPesquisa(int idPesquisa, List<IdCandidatoVotos> candidatos) throws SQLException{
		
		String comandoInsercao = "INSERT INTO candidato_pesquisa VALUES";
		
		try{
			if (candidatos != null){
				for(IdCandidatoVotos candidato : candidatos){
					this.executaUpdate(comandoInsercao + " (" +
								"NEXT VALUE FOR seq_candidato_pesquisa" + ", " +
								candidato.getIdCandidato() + "," +
								candidato.getNumeroVotos() + "," +
								idPesquisa + 
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
	
	/** Exclui uma pesquisa do banco de dados
	 * @param idPesquisa <code>int</code> com o id da pesquisa que será excluida
	 * @return <code>boolean</code> com <code>true</code> se o candidato foi excluido e <code>false</code> senão.
	 */
	protected boolean excluir(int idPesquisa){
		boolean excluiu = false;
		try{
			this.abreConexao();
			
			this.executaUpdate("DELETE FROM candidato_pesquisa WHERE idPesquisa=" + idPesquisa);
			
			this.fechaConexao();
		}
		catch(SQLException e){
			e.printStackTrace();
			excluiu = false;
		}
		return excluiu;
	}
	
	/** Retorna todos os cargos usados nas pesquisas
	 * @return {@code List<String>} com os nomes de todos os cargos que participam de alguma pesquisa
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<String> getAllCargos() throws SQLException{ //retorna os cargos que tem pesquisas cadastradas
		List<String> cargos = new ArrayList<String>();
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		String comando;
		comando = "SELECT DISTINCT cargo FROM pesquisa";
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			cargos.add(resultadoQuery.getString("cargo"));
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return cargos;
	}
	
	/** Retorna as data de inicio e fim das pesquisas do cargo especificado
	 * @param cargo <code>String</code> com o nome do cargo
	 * @return {@code List<Data[]>} lista com as datas de inicio e fim das pesquisas do cargo especificado
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 * @see Data
	 * @see List
	 */
	protected static List<Data[]> getDatas(String cargo) throws SQLException{ //retorna os cargos que tem pesquisas cadastradas
		List<Data[]> datas = new ArrayList<Data[]>();
		Data[] data = new Data[2];
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		String comando;
		comando = "SELECT data_inicio, data_fim FROM pesquisa WHERE cargo=\'" + cargo + "\'";
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			data[0] = new Data(Data.AAAAMMDDtoDDMMAAAA(resultadoQuery.getString("data_inicio")));
			data[1] = new Data(Data.AAAAMMDDtoDDMMAAAA(resultadoQuery.getString("data_fim")));
			datas.add(new Data[]{data[0], data[1]});
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return datas;
	}
	
	/** Retorna o numero de votos nulos e brancos da pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero de votos nulos e brancos da pesquisa
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static int getNumeroVotosNulosBrancos(int idPesquisa) throws SQLException{ //retorna os cargos que tem pesquisas cadastradas

		int numeroVotos = 0;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		String comando;
		comando = "SELECT votos_nulos_brancos FROM pesquisa WHERE idPesquisa=" + idPesquisa;
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			numeroVotos = Integer.parseInt(resultadoQuery.getString("votos_nulos_brancos"));
		}

		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return numeroVotos;
	}
	
	/** Retorna o numero de indecisos, nao souberam ou quiseram respoder da pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero de eleitores indecisos ou que não quiseram ou souberam responder da pesquisa
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static int getNumeroVotosIndecisos(int idPesquisa) throws SQLException{ //retorna os cargos que tem pesquisas cadastradas

		int numeroVotos = 0;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		String comando;
		comando = "SELECT votos_Indecisos FROM pesquisa WHERE idPesquisa=" + idPesquisa;
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			numeroVotos = Integer.parseInt(resultadoQuery.getString("votos_Indecisos"));
		}

		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return numeroVotos;
	}
	
	/** Retorna o numero de pessoas entrevistas na pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero total de pessoas entrevistas na pesquisa
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static int getNumeroPessoasEntrevistadas(int idPesquisa) throws SQLException{

		int numeroVotos = 0;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		String comando;
		comando = "SELECT numero_Pessoas_Entrevistadas FROM pesquisa WHERE idPesquisa=" + idPesquisa;
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			numeroVotos = Integer.parseInt(resultadoQuery.getString("numero_Pessoas_Entrevistadas"));
		}

		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return numeroVotos;
	}
	
	/** Retorna o numero de municipios na pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero total de municipios na pesquisa
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static int getNumeroMunicipiosPesquisados(int idPesquisa) throws SQLException{

		int numeroVotos = 0;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		
		String comando;
		comando = "SELECT numero_municipios_pesquisados FROM pesquisa WHERE idPesquisa=" + idPesquisa;
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			numeroVotos = Integer.parseInt(resultadoQuery.getString("numero_municipios_pesquisados"));
		}

		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return numeroVotos;
	}
	
	/* SQL Para pegar o nome do candidato e numero de votos em uma determinada pesquisa, no caso, a de id 1
	 * 
	 * SELECT candidato.nome, candidato_pesquisa.numero_votos, pesquisa.idPesquisa FROM pesquisa INNER JOIN (candidato_pesquisa INNER JOIN candidato ON candidato.idCandidato=candidato_pesquisa.idCandidato) ON pesquisa.idPesquisa=candidato_pesquisa.idPesquisa WHERE idPesquisa=1;
	 */
	/** Retorna uma lista com o id da pesquisa, os nomes dos candidatos que participaram da pesquisa referente ao id passado como parametro, bem como o numero de votos de cada candidato.
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return {@code List<NomeCandidatoVotos>} com os ids das pesquisa, nomes e numero de votos de todos os candidatos da pesquisa
	 * @see List
	 * @see NomeCandidatoVotos
	 */
	protected static List<NomeCandidatoVotos> getNomeCandidatosVotos(int idPesquisa){
		List<NomeCandidatoVotos> dadosCandidatos = new ArrayList<NomeCandidatoVotos>();
		String nomeCandidato;
		int numeroVotos;
		try{
			BANCO_DE_DADOS_ELEICOES.abreConexao();
			String comando;
			//pega as colunas Nome da tabela candidato e numero de votos da tabela candidato_pesquisa onde o id da pesquisa é idPesquisa
			comando = "SELECT candidato.nome, candidato_pesquisa.numero_votos FROM pesquisa INNER JOIN (candidato_pesquisa INNER JOIN candidato ON candidato.idCandidato=candidato_pesquisa.idCandidato) ON pesquisa.idPesquisa=candidato_pesquisa.idPesquisa WHERE idPesquisa=" + idPesquisa + " ORDER BY data_fim";
			
			ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
			try {
				while(resultadoQuery.next()){
					nomeCandidato = resultadoQuery.getString("nome");
					try{
						numeroVotos = Integer.parseInt(resultadoQuery.getString("numero_votos"));
						dadosCandidatos.add(new NomeCandidatoVotos(idPesquisa, nomeCandidato, numeroVotos));
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
		return dadosCandidatos;
	}
	
	//seleciona os nomes dos candidatos, id das pesquisas e numero de votos e armazena numa lista em ordem alfabetica em relacao ao nome e numerica ao id da pesquisa
	/** Retorna uma lista com o id das pesquisas, os nomes dos candidatos que participaram da pesquisa, bem como o numero de votos de cada candidato.
	 * @param cargo <code>String</code> com o cargo das pesquisas
	 * @return {@code List<NomeCandidatoVotos>} com os ids das pesquisa, nomes e numero de votos de todos os candidatos da pesquisa
	 * @see List
	 * @see NomeCandidatoVotos
	 */
	protected static List<NomeCandidatoVotos> getNomeCandidatosVotos(String cargo){
		List<NomeCandidatoVotos> dadosCandidatos = new ArrayList<NomeCandidatoVotos>();
		String nomeCandidato;
		int numeroVotos;
		int idPesquisa;
		try{
			BANCO_DE_DADOS_ELEICOES.abreConexao();
			String comando;
			//pega as colunas Nome da tabela candidato e numero de votos da tabela candidato_pesquisa onde o id da pesquisa é idPesquisa
			//comando = "SELECT pesquisa.idPesquisa, candidato.nome, candidato_pesquisa.numero_votos FROM pesquisa INNER JOIN (candidato_pesquisa INNER JOIN candidato ON candidato.idCandidato=candidato_pesquisa.idCandidato) ON pesquisa.idPesquisa=candidato_pesquisa.idPesquisa WHERE cargo=\'" + cargo + "\' ORDER BY candidato.nome, pesquisa.idPesquisa";
			comando = "SELECT pesquisa.idPesquisa, candidato.nome, candidato_pesquisa.numero_votos FROM pesquisa INNER JOIN (candidato_pesquisa INNER JOIN candidato ON candidato.idCandidato=candidato_pesquisa.idCandidato) ON pesquisa.idPesquisa=candidato_pesquisa.idPesquisa WHERE cargo=\'" + cargo + "\' ORDER BY candidato.nome, pesquisa.data_fim";
			
			ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
			try {
				while(resultadoQuery.next()){
					nomeCandidato = resultadoQuery.getString("nome");
					try{
						idPesquisa = Integer.parseInt(resultadoQuery.getString("idPesquisa"));
						numeroVotos = Integer.parseInt(resultadoQuery.getString("numero_votos"));
						dadosCandidatos.add(new NomeCandidatoVotos(idPesquisa, nomeCandidato, numeroVotos));
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
		return dadosCandidatos;
	}
	
	/** Retorna uma lista com o id e data de fim das pesquisas com o cargo especificado.
	 * @param cargo <code>String</code> com o cargo
	 * @return {@code List<String[]>} com a lista de ids e datas de fim das pesquisas com o cargo especificado.
	 * @see List
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<String[]> getIdDataFim(String cargo) throws SQLException{
		//SELECT idPesquisa, data_fim FROM pesquisa WHERE cargo='Presidente'
		List<String[]> idDataFim = new ArrayList<String[]>();
		String idPesquisa;
		String dataFim;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		String comando;
		cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		//pega as colunas Nome da tabela candidato e numero de votos da tabela candidato_pesquisa onde o id da pesquisa é idPesquisa
		comando = "SELECT idPesquisa, data_fim FROM pesquisa WHERE cargo=\'" + cargo + "\' ORDER BY data_fim";
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			idPesquisa = resultadoQuery.getString("idPesquisa");
			dataFim = resultadoQuery.getString("data_fim");
			idDataFim.add(new String[] { idPesquisa, dataFim} );
		}
			
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		return idDataFim;
	}
	
	//maximo de candidatos nas pesquisas de um determinado cargo
	/** Retorna o numero maximo de candidatos entre as pesquisas de um determinado cargo. 
	 * Por exemplo, se há duas pesquisas, a primeira com 5 candidatos e a 2 com 7 candidatos, esta função irá retornar 7.  
	 * @param cargo <code>String</code> com o cargo
	 * @return <code>int</code> com o numero maximo de candidatos entre as pesquisas
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static int getMaximoCandidatosCargo(String cargo) throws SQLException{
		//select COUNT(candidato_pesquisa.idPesquisa) as maximo_candidatos from pesquisa inner join candidato_pesquisa on pesquisa.idPesquisa=candidato_pesquisa.idPesquisa where cargo='Presidente' group by idpesquisa order by maximo_candidatos desc limit 1; 
		int maximoCandidatos = 0;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		String comando;
		cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		//pega as colunas Nome da tabela candidato e numero de votos da tabela candidato_pesquisa onde o id da pesquisa é idPesquisa
		comando = "SELECT COUNT(candidato_pesquisa.idPesquisa) AS maximo_candidatos FROM pesquisa INNER JOIN candidato_pesquisa ON pesquisa.idPesquisa=candidato_pesquisa.idPesquisa WHERE cargo=\'" + cargo + "\' GROUP BY idpesquisa ORDER BY maximo_candidatos DESC LIMIT 1";
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			maximoCandidatos = Integer.parseInt(resultadoQuery.getString("maximo_candidatos"));
		}
			
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return maximoCandidatos;
	}
	
	/** Retorna uma lista com os ids das pesquisa do cargo especificado no banco de dados.
	 * @param cargo <code>String</code> com o cargo
	 * @return {@code List<Integer>} com os ids das pesquisas do cargo especificado
	 * @see List
	 * @see Integer
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Integer> getIdPesquisaByCargo(String cargo) throws SQLException{
		//SELECT idPesquisa FROM pesquisa WHERE cargo='presidente' ORDER BY idpesquisa;
		
		List<Integer> pesquisas = new ArrayList<Integer>();
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		String comando;
		cargo = substituiAspasSimplesPorUmaValidaNoBD(cargo);
		//pega as colunas Nome da tabela candidato e numero de votos da tabela candidato_pesquisa onde o id da pesquisa é idPesquisa
		//comando = "SELECT idPesquisa FROM pesquisa WHERE cargo=\'" + cargo + "\' ORDER BY idpesquisa";
		comando = "SELECT idPesquisa FROM pesquisa WHERE cargo=\'" + cargo + "\' ORDER BY data_fim";
		
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		while(resultadoQuery.next()){
			pesquisas.add(Integer.parseInt(resultadoQuery.getString("idPesquisa")));
		}
		
		BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return pesquisas;
	}
	
}
