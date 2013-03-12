package com.arthurassuncao.sistel.classes.votacao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.candidato.Candidato;
import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.persistencia.BDVotacao;
import com.arthurassuncao.sistel.persistencia.BancoDeDados;

/** Classe para manipular votacoes
 * @author Arthur Assunção
 * 
 * 
 * @see BDVotacao
 */
public class Votacao extends BDVotacao{
	private Data data;
	private List<String> cargos;

	//public static Votacao VOTACAO = null;
	
	/*private Votacao(){
	}*/
	
	/** Cria uma votacao com data especifica e uma lista de cargos especifica
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @param listaCargos {@code List<String>} com a lista de cargos que a votacao terá
	 * 
	 * @see Data
	 * @see List
	 */
	public Votacao(Data dataVotacao, List<String> listaCargos){
		this.data = dataVotacao;
		this.cargos = listaCargos;
	}
	
	/** Cria uma votacao com data especifica e uma lista de cargos especifica
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @param listaCargos {@code List<Cargo>} com a lista de cargos que a votacao terá
	 * @param flag <code>int</code> para permitir a sobrecarga, passar lista de <code>Cargos</code> para o contrutor
	 * 
	 * @see Data
	 * @see List
	 * @see Cargo
	 */
	public Votacao(Data dataVotacao, List<Cargo> listaCargos, int flag){ //flag, so pra deixar eu fazer a sobrecarga
		this.data = dataVotacao;
		this.cargos = new ArrayList<String>();
		if(listaCargos != null){
			for(Cargo cargo : listaCargos){
				this.cargos.add(cargo.getNome());
			}
		}
	}
	
	/*public static Votacao getInstance(){
		if(VOTACAO == null){
			VOTACAO = new Votacao();
		}
		return VOTACAO;
	}*/
	
	/** Insere uma votação no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao#insere(Votacao)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	public int insere(){
		int resultadoInsere;
		resultadoInsere = super.insere(this);
		return resultadoInsere;
	}
	
	/** Insere um candidato na votacao, se o candidato ainda nao foi votado, o candidato é inserido na votacao com numero de votos igual a 1.
	 * Se o candidato ja foi votado(está cadastrado na tabela), o numero de votos é incrementando em 1.
	 * @param candidato <code>Candidato</code> com o candidato que foi votado
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao#insere(Votacao)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	public int insereCandidato(Candidato candidato){
		int resultadoInsere = RESULTADO_ERRO_BANCO_DADOS;
		boolean existeCandidato = false;
		
		try{
			existeCandidato = existsCandidato(this.data, candidato.getId());
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		if(!existeCandidato){ //se nao existe, cadastra
			resultadoInsere = super.insereCandidato(this, candidato);
		}
		else{ //se existe so atualiza
			resultadoInsere = super.atualizaCandidato(this, candidato, 1); //soma 1 ao numero de votos do candidato nesta eleicao
		}
		return resultadoInsere;
	}
	
	/** Incrementa em 1 o numero de votos nulos em um cargo na votacao
	 * @param cargo <code>String</code> com o cargo
	 * @return <code>int</code> com o resultado da atualizacao. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao#atualizaNulos(Votacao, Cargo, int)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 */
	public int atualizaNulos(Cargo cargo){
		int resultadoInsere = RESULTADO_ERRO_BANCO_DADOS;
		
		resultadoInsere = super.atualizaNulos(this, cargo, 1); //soma 1 ao numero de votos nulos nesta eleicao
		
		return resultadoInsere;
	}
	
	/** Incrementa em 1 o numero de votos em branco em um cargo na votacao
	 * @param cargo <code>String</code> com o cargo
	 * @return <code>int</code> com o resultado da atualizacao. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Votacao#atualizaBrancos(Votacao, Cargo, int)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 */
	public int atualizaBrancos(Cargo cargo){
		int resultadoInsere = RESULTADO_ERRO_BANCO_DADOS;
		
		resultadoInsere = super.atualizaBrancos(this, cargo, 1); //soma 1 ao numero de votos nulos nesta eleicao
		
		return resultadoInsere;
	}
	
	/** Retorna o numero de votos nulos em um cargo da votacao
	 * @param cargo <code>String</code> com o cargo
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>int</code> com o numero de votos nulos
	 * @see Data
	 */
	public static int getNumeroVotosNulos(String cargo, Data dataVotacao){
		int numeroVotosNulos = 0;
		
		try{
			numeroVotosNulos = BDVotacao.getNumeroVotosNulos(dataVotacao, cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		return numeroVotosNulos;
	}
	
	/** Retorna o numero de votos em branco em um cargo da votacao
	 * @param cargo <code>String</code> com o cargo
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>int</code> com o numero de votos em branco
	 * @see Data
	 */
	public static int getNumeroVotosBrancos(String cargo, Data dataVotacao){
		int numeroVotosNulos = 0;
		
		try{
			numeroVotosNulos = BDVotacao.getNumeroVotosBrancos(dataVotacao, cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		return numeroVotosNulos;
	}
	
	/** Retorna a votacao referente a data especificada
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return <code>Votacao</code> com a votacao, se não encontrar uma votacao com a data especificada, retorna <code>null</code>
	 * @see Data
	 */
	public static Votacao pesquisa(Data dataVotacao){
		Votacao votacao = null;
		try{
			votacao = BDVotacao.pesquisa(dataVotacao);
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		return votacao;
	}
	
	/** Retorna uma lista com as votacoes que tenham o cargo especifico
	 * @param cargo <code>String</code> com o cargo
	 * @return {@code List<Votacao>} com a lista de votacoes, se nenhuma votacao for encontrada retorna uma lista vazia.
	 */
	public static List<Votacao> pesquisa(String cargo, boolean datasPosteriores){
		List<Votacao> votacoes = null;
		
		try{
			votacoes = BDVotacao.pesquisa(cargo, datasPosteriores);
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		return votacoes;
	}
	
	/** Verifica se existe uma votacao com a mesma data desta no banco de dados.
	 * @return <code>boolean</code> com <code>true</code> se a votacao existe e <code>false</code> senão.
	 */
	public boolean exists(){
		boolean existe = false;
		try{
			existe = super.exists(this.data);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return existe;
	}
	
	/** Verifica se existe uma votacao para o dia atual
	 * @return <code>boolean</code> com <code>true</code> se existe e <code>false</code> senão.
	 */
	public static boolean isDiaDeVotacao(){
		boolean diaDeVotacao = false;
		try{
			diaDeVotacao = BDVotacao.isDiaDeVotacao();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return diaDeVotacao;
	}
	
	/** Retorna uma lista com os nomes dos candidatos e seus respectivos numero de votos na votacao sem considerar o cargo do candidato. Se o candidato não obteve votos, 
	 * portanto não foi inserido na tabela, é considerado que obteve 0 votos.
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @return {@code List<NomeCandidatoVotos>} com a lista com os nomes dos candidatos e seus respectivos numeros de votos.
	 * @see NomeCandidatoVotos
	 * @see Data
	 */
	public List<NomeCandidatoVotos> getCandidatosVotos(Data dataVotacao){
		List<NomeCandidatoVotos> candidatosVotos = null;
		
		candidatosVotos = BDVotacao.getListaCandidatosVotos(dataVotacao, null);

		return candidatosVotos;
	}
	
	/** Retorna uma lista com os nomes dos candidatos de um cargo e seus respectivos numero de votos na votacao. Se o candidato não obteve votos, 
	 * portanto não foi inserido na tabela, é considerado que obteve 0 votos.
	 * @param dataVotacao <code>Data</code> com a data da votacao
	 * @param cargo <code>String</code> com o cargo dos candidatos
	 * @return {@code List<NomeCandidatoVotos>} com a lista com os nomes dos candidatos e seus respectivos numeros de votos.
	 * @see NomeCandidatoVotos
	 * @see Data
	 */
	public static List<NomeCandidatoVotos> getListaCandidatosVotos(Data dataVotacao, String cargo){
		List<NomeCandidatoVotos> candidatosVotos = null;
		
		candidatosVotos = BDVotacao.getListaCandidatosVotos(dataVotacao, cargo);

		return candidatosVotos;
	}
	
	/** Retorna todos os cargos da votacao. É feito um acesso ao banco de dados, portanto são retornados os cargos cadastrados no banco.
	 * @return {@code List<Cargo>} com a lista de cargos usados na votacao
	 */
	public List<Cargo> getAllCargos(){
		List<Cargo> cargos = new ArrayList<Cargo>();
		try{
			cargos = BDVotacao.getAllCargos(this.getData());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return cargos;
	}
	
	/** Retorna todos os cargos da votacao da data especificada
	 * @return {@code List<Cargo>} com a lista de cargos usados na votacao
	 */
	public static List<Cargo> getAllCargos(Data dataVotacao){
		List<Cargo> cargos = new ArrayList<Cargo>();
		try{
			/*Votacao votacao = new Votacao();
			votacao.data = dataVotacao;*/
			cargos = BDVotacao.getAllCargos(dataVotacao);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return cargos;
	}
	
	/** Retorna um <code>Data</code> com a data da votacao
	 * @return <code>Data</code> com a data da votacao
	 */
	public Data getData() {
		return data;
	}

	/*
	 * @param data the data to set
	 */
	/*public void setData(Data data) {
		this.data = data;
	}*/
	
	/** Retorna todos os nomes dos cargos da votacao. Nenhum acesso ao banco de dados é feito.
	 * @return {@code List<String>} com a lista de cargos usados na votacao
	 */
	public List<String> getCargos() {
		return cargos;
	}

	/*public static class IdCandidatoVotos{
		int idCandidato;
		int numeroVotos;
		
		public IdCandidatoVotos(int idCandidato,int numeroVotos){
			this.idCandidato = idCandidato;
			this.numeroVotos = numeroVotos;
		}


		public int getIdCandidato() {
			return idCandidato;
		}


		public int getNumeroVotos() {
			return numeroVotos;
		}
	}*/
	
	/** Classe armazenar o nome do candidato e o seu numero de votos na votacao.
	 * @author Arthur Assunção
	 * 
	 *
	 * @see Votacao
	 */
	public static class NomeCandidatoVotos{
		String nomeCandidato;
		int numeroVotos;
		
		/** Cria uma instancia da classe com nome do candidato e numero de votos especifico.
		 * @param nomeCandidato <code>String</code> com o nome do candidato
		 * @param numeroVotos <code>int</code> com o numero de votos do candidato na pesquisa com o id já especificado
		 */
		public NomeCandidatoVotos(String nomeCandidato, int numeroVotos){
			this.nomeCandidato = nomeCandidato;
			this.numeroVotos = numeroVotos;
		}

		/** Retorna um <code>String</code> com o nome do candidato
		 * @return um <code>String</code> com o nome do candidato
		 */
		public String getNomeCandidato(){
			return this.nomeCandidato;
		}

		/** Retorna um <code>int</code> com o numero de votos do candidato
		 * @return um <code>int</code> com o numero de votos do candidato
		 */
		public int getNumeroVotos() {
			return this.numeroVotos;
		}
	}

}
