package too.trabalho.classes.pesquisa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import too.trabalho.classes.Data;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.persistencia.BDPesquisa;
import too.trabalho.persistencia.BancoDeDados;

/** Classe para manipular pesquisas eleitorais
 * @author Arthur Assunção
 * 
 * 
 * @see BDPesquisa
 */
public class Pesquisa extends BDPesquisa {
	private String cargo;
	private Data dataInicio;
	private Data dataFim;
	private int numeroVotosNulosBrancos;
	private int numeroVotosIndecisos;
	private int numeroPessoasEntrevistadas;
	private int numeroMunicipiosPesquisados;
	private List<IdCandidatoVotos> idCandidatoVotos;

	//private static Pesquisa PESQUISA = null;
	
	/* Cria uma instancia da pesquisa
	 * 
	 */
	/*private Pesquisa(){
	}*/
	
	/** Cria uma pesquisa com cargo, data de inicio, data de fim, numero de votos nulos, numero de indecisos, numero de pessoas entrevistadas, 
	 * numero de municipios pesquisados e id candidatos/numero de votos especificos.
	 * @param cargo <code>String</code> com o cargo da pesquisa
	 * @param dataInicio <code>Data</code> com a data de inicio da pesquisa
	 * @param dataFim <code>Data</code> com a data de fim da pesquisa
	 * @param votosNulos <code>int</code> com o numero de votos nulos na pesquisa
	 * @param votosIndecisos <code>int</code> com o numero de pessoas indecisas na pesquisa
	 * @param pessoasEntrevistadas <code>int</code> com o numero de pessoas entrevistadas
	 * @param municipiosPesquiados <code>int</code> com o numero de municipios pesquisados
	 * @param candidatos {@code List<IdCandidatoVotos>} com os ids dos candidatos e seus respectivos numero de votos na pesquisa.
	 * 
	 * @see Data
	 * @see List
	 * @see IdCandidatoVotos
	 */
	public Pesquisa(String cargo, Data dataInicio, Data dataFim, int votosNulos, int votosIndecisos, int pessoasEntrevistadas, int municipiosPesquiados, List<IdCandidatoVotos> candidatos){
		this.cargo = cargo;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.numeroVotosNulosBrancos = votosNulos;
		this.numeroVotosIndecisos = votosIndecisos;
		this.numeroPessoasEntrevistadas = pessoasEntrevistadas;
		this.numeroMunicipiosPesquisados = municipiosPesquiados;
		this.idCandidatoVotos = candidatos;
	}
	
	/* Adiciona um <code>IdCandidatoVotos</code>(id do candidato e numero de votos) à lista de ids/candidatos.
	 * @param candidatoVotos um <code>IdCandidatoVotos</code> com o id do candidato e o numero de votos na pesquisa
	 * @see IdCandidatoVotos
	 */
	/*public void addCandidatoVotos(IdCandidatoVotos candidatoVotos){
		idCandidatoVotos.add(candidatoVotos);
	}*/
	
	/** Retorna uma lista de ids dos candidatos com seus respectivos numero de votos.
	 * @return um {@code List<IdCandidatoVotos>} com a lista de ids dos candidatos com seus respectivos numero de votos.
	 */
	public List<IdCandidatoVotos> getIdCandidatoVotos() {
		return idCandidatoVotos;
	}
	
	/** Insere uma pesquisa no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Pesquisa#insere(Pesquisa)
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
	
	/** Verifica se existe uma pesquisa com o mesmo cargo, data de inicio e data de fim desta no banco de dados.
	 * @return <code>boolean</code> com <code>true</code> se a pesquisa existe e <code>false</code> senão.
	 */
	public boolean exists(){
		boolean existe = false;
		try{
			existe = super.exists(this.cargo, this.dataInicio, this.dataFim);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return existe;
	}

	/** Retorna o id da pesquisa referente ao cargo, data inicio e data fim desta pesquisa.
	 * @return <code>int</code> com o id da pesquisa, se não encontrar retorna -1
	 */
	public int getId(){
		int idPesquisa = -1;
		try {
			idPesquisa = super.getId(this.cargo, this.dataInicio, this.dataFim);
		} catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		return idPesquisa;
	}
	
	/** Retorna o id da pesquisa referente ao cargo, data inicio e data fim especificados.
	 * @param cargo <code>String</code> com o cargo da pesquisa
	 * @param dataInicio <code>Data</code> com a data de inicio da pesquisa
	 * @param dataFim <code>Data</code> com a data de fim da pesquisa
	 * @return <code>int</code> com o id da pesquisa, se não encontrar retorna -1
	 * @see Data
	 */
	public static int getId(String cargo, Data dataInicio, Data dataFim){
		int idPesquisa = -1;
		try {
			idPesquisa = BDPesquisa.getId(cargo, dataInicio, dataFim);
		} catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		return idPesquisa;
	}
	
	/** Retorna todos os cargos usados nas pesquisas
	 * @return {@code List<String>} com os nomes de todos os cargos que participam de alguma pesquisa
	 */
	public static List<String> getAllCargos(){
		List<String> cargos;
		try{
			cargos = BDPesquisa.getAllCargos();
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return cargos;
	}

	/*public List<Data[]> getDatas(){
		List<Data[]> datas;
		try{
			datas = BDPesquisa.getDatas(this.cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return datas;
	}*/
	
	/** Retorna as data de inicio e fim das pesquisas do cargo especificado
	 * @param cargo <code>String</code> com o nome do cargo
	 * @return {@code List<Data[]>} lista com as datas de inicio e fim das pesquisas do cargo especificado
	 * @see Data
	 * @see List
	 */
	public static List<Data[]> getDatasByCargo(String cargo){
		List<Data[]> datas;
		try{
			datas = Pesquisa.getDatas(cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return datas;
	}
	
	/** Retorna os nomes dos candidatos que participaram da pesquisa, bem como o numero de votos de cada candidato.
	 * @param cargo <code>String</code> com o cargo da pesquisa
	 * @param dataInicio <code>Data</code> com a data de inicio da pesquisa
	 * @param dataFim  <code>Data</code> com a data de fim da pesquisa
	 * @return {@code List<NomeCandidatoVotos>} com os nomes e numero de votos de todos os candidatos desta pesquisa
	 * @see List
	 * @see Data
	 * @see NomeCandidatoVotos
	 */
	public static List<NomeCandidatoVotos> getNomeCandidatosVotos(String cargo, Data dataInicio, Data dataFim){
		List<NomeCandidatoVotos> nomesCandidatos;
		int idPesquisa;
		try{
			idPesquisa = BDPesquisa.getId(cargo, dataInicio, dataFim);
			nomesCandidatos = BDPesquisa.getNomeCandidatosVotos(idPesquisa);
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return nomesCandidatos;
	}
	
	/*public static List<NomeCandidatoVotos> getNomeCandidatosVotos(int idPesquisa){
		List<NomeCandidatoVotos> nomesCandidatos;
		
		nomesCandidatos = BDPesquisa.getNomeCandidatosVotos(idPesquisa);
		
		return nomesCandidatos;
	}*/

	/** Retorna uma lista com todos os ids das pesquisas, nomes dos candidatos e numero de votos em cada pesquisa que seja do cargo especificado.
	 * @param cargo <code>String</code> com o cargo
	 * @return {@code List<NomeCandidatoVotos>} com a lista de <code>NomeCandidatoVotos</code>, classe que contem id da pesquisa, nome do candidato e numero de votos.
	 * @see List
	 * @see NomeCandidatoVotos
	 */
	public static List<NomeCandidatoVotos> getNomeCandidatosVotosByCargo(String cargo){
		List<NomeCandidatoVotos> nomesCandidatos;

		nomesCandidatos = BDPesquisa.getNomeCandidatosVotos(cargo);

		return nomesCandidatos;
	}
	
	/** Retorna o numero de votos nulos e brancos da pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero de votos nulos e brancos da pesquisa
	 */
	public static int getNumeroVotosNulosBrancos(int idPesquisa){
		int numeroVotos = 0;
		try{
			numeroVotos = BDPesquisa.getNumeroVotosNulosBrancos(idPesquisa);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		return numeroVotos;
	}
	
	/** Retorna o numero de indecisos, nao souberam ou quiseram respoder da pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero de eleitores indecisos ou que não quiseram ou souberam responder da pesquisa
	 */
	public static int getNumeroVotosIndecisos(int idPesquisa){
		int numeroVotos = 0;
		try{
			numeroVotos = BDPesquisa.getNumeroVotosIndecisos(idPesquisa);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		return numeroVotos;
	}
	
	/** Retorna o numero de pessoas entrevistas na pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero total de pessoas entrevistas na pesquisa
	 */
	public static int getNumeroPessoasEntrevistadas(int idPesquisa){
		int numeroVotos = 0;
		try{
			numeroVotos = BDPesquisa.getNumeroPessoasEntrevistadas(idPesquisa);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		return numeroVotos;
	}
	
	/** Retorna o numero de municipios na pesquisa referente ao id passado como parametro
	 * @param idPesquisa <code>int</code> com o id da pesquisa
	 * @return <code>int</code> com o numero total de municipios na pesquisa
	 */
	public static int getNumeroMunicipiosPesquisados(int idPesquisa){
		int numeroVotos = 0;
		try{
			numeroVotos = BDPesquisa.getNumeroMunicipiosPesquisados(idPesquisa);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		return numeroVotos;
	}
	
	/** Retorna o numero maximo de candidatos entre as pesquisas de um determinado cargo. 
	 * Por exemplo, se há duas pesquisas, a primeira com 5 candidatos e a 2 com 7 candidatos, esta função irá retornar 7.  
	 * @param cargo <code>String</code> com o cargo
	 * @return <code>int</code> com o numero maximo de candidatos entre as pesquisas
	 */
	public static int getMaximoCandidatosCargo(String cargo){
		int maximoCandidatos = 0;
		try{
			maximoCandidatos = BDPesquisa.getMaximoCandidatosCargo(cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
		return maximoCandidatos;
	}
	

	/** Retorna uma lista com os ids das pesquisa do cargo especificado no banco de dados.
	 * @param cargo <code>String</code> com o cargo
	 * @return {@code List<Integer>} com os ids das pesquisas do cargo especificado
	 * @see List
	 * @see Integer
	 */
	public static List<Integer> getIdPesquisaByCargo(String cargo){
		List<Integer> pesquisas = new ArrayList<Integer>();
		try{
			pesquisas = BDPesquisa.getIdPesquisaByCargo(cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return pesquisas;
	}
	
	/** Retorna uma lista com o id e data de fim das pesquisas com o cargo especificado.
	 * @param cargo <code>String</code> com o cargo
	 * @return {@code List<String[]>} com a lista de ids e datas de fim das pesquisas com o cargo especificado.
	 * @see List
	 */
	public static List<String[]> getIdDataFim(String cargo){
		List<String[]> listaIdDataFim = new ArrayList<String[]>();
		try{
			listaIdDataFim = BDPesquisa.getIdDataFim(cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return listaIdDataFim;
	}
	
	/* Retorna uma pesquisa com os dados vazios e nulos. Esta pesquisa é compartilhada por todos que instanciam atrvez deste metodo.
	 * Portanto, caso em outra parte do codigo se setou dados nesta pesquisa, ela retornara com os dados setados.
	 * @return <code>Pesquisa</code> com o objeto estatico
	 */
	/*public static Pesquisa getInstance(){
		if(PESQUISA == null){
			PESQUISA = new Pesquisa();
		}
		return PESQUISA;
	}*/
	
	/** Retorna um <code>String</code> com o cargo da pesquisa
	 * @return um <code>String</code> com o cargo da pesquisa
	 */
	public String getCargo() {
		return cargo;
	}
	
	/** Retorna um <code>Data</code> com a data de inicio da pesquisa
	 * @return um <code>Data</code> com a data de inicio da pesquisa
	 */
	public Data getDataInicio() {
		return dataInicio;
	}
	/** Retorna um <code>Data</code> com a data de fim da pesquisa
	 * @return um <code>Data</code> com a data de fim da pesquisa
	 */
	public Data getDataFim() {
		return dataFim;
	}
	/** Retorna um <code>int</code> com o numero de votos nulos e brancos da pesquisa
	 * @return um <code>int</code> com o numero de votos nulos e brancos da pesquisa
	 */
	public int getNumeroVotosNulosBrancos() {
		return numeroVotosNulosBrancos;
	}
	/** Retorna um <code>int</code> com o numero de indecisos, não souberam ou quiseram opinar na pesquisa.
	 * @return um <code>int</code> com o numero de indecisos, não souberam ou quiseram opinar na pesquisa.
	 */
	public int getNumeroVotosIndecisos() {
		return numeroVotosIndecisos;
	}
	/** Retorna um <code>int</code> com o numero de pesssoas entrevistadas
	 * @return um <code>int</code> com o numero de pesssoas entrevistadas
	 */
	public int getNumeroPessoasEntrevistadas() {
		return numeroPessoasEntrevistadas;
	}
	/** Retorna um <code>int</code> com o numero de municipios pesquisados
	 * @return um <code>int</code> com o numero de municipios pesquisados
	 */
	public int getNumeroMunicipiosPesquisados() {
		return numeroMunicipiosPesquisados;
	}
	
	/** Classe para relacionar candidatos, atraves do id no banco de dados, e o numero de votos na pesquisa.
	 * @author Arthur Assunção
	 * 
	 * 
	 */
	public static class IdCandidatoVotos{
		private int idCandidato;
		private int numeroVotos;
		
		/** Cria uma instancia da classe com id do candidato e numero de votos especifico
		 * @param idCandidato <code>int</code> com o id do candidato no banco de dados
		 * @param numeroVotos <code>int</code> com o numero de votos do candidato
		 */
		public IdCandidatoVotos(int idCandidato,int numeroVotos){
			this.idCandidato = idCandidato;
			this.numeroVotos = numeroVotos;
		}

		/** Retorna um <code>int</code> com o id do candidato
		 * @return um <code>int</code> com o id do candidato
		 */
		public int getIdCandidato() {
			return idCandidato;
		}

		/** Retorna um <code>int</code> com o numero de votos do candidato
		 * @return um <code>int</code> com o numero de votos do candidato
		 */
		public int getNumeroVotos() {
			return numeroVotos;
		}
	}
	
	/** Classe para relacionar uma pesquisa, atraves do id, o nome do candidato e o seu numero de votos na pesquisa.
	 * @author Arthur Assunção
	 * 
	 *
	 * @see Pesquisa
	 */
	public static class NomeCandidatoVotos{
		int idPesquisa;
		String nomeCandidato;
		int numeroVotos;
		
		/** Cria uma instancia da classe com id da pesquisa, nome do candidato e numero de votos especifico.
		 * @param idPesquisa <code>int</code> com o id da pesquisa
		 * @param nomeCandidato <code>String</code> com o nome do candidato
		 * @param numeroVotos <code>int</code> com o numero de votos do candidato na pesquisa com o id já especificado
		 */
		public NomeCandidatoVotos(int idPesquisa, String nomeCandidato, int numeroVotos){
			this.idPesquisa = idPesquisa;
			this.nomeCandidato = nomeCandidato;
			this.numeroVotos = numeroVotos;
		}
		
		/** Retorna um <code>int</code> com o id da pesquisa
		 * @return um <code>int</code> com o id da pesquisa
		 */
		public int getIdPesquisa() {
			return idPesquisa;
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
