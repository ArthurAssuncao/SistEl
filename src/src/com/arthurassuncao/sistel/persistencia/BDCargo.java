package com.arthurassuncao.sistel.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.classes.cargo.Cargo;
import com.arthurassuncao.sistel.gui.JanelaMensagem;

/** Classe para manipular dados dos Cargos no banco de dados
 * @author Arthur Assunção
 * 
 * 
 * @see BDEleicoes
 *
 */
public abstract class BDCargo extends BDEleicoes {
	
	/** Insere um cargo no banco de dados
	 * @param cargo <code>Cargo</code> que será inserido no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	protected int inserir(Cargo cargo){
		String nome = cargo.getNome();
		int numeroDigitos = cargo.getNumeroDigitos();
		
		String comandoInsercao = "INSERT INTO cargo VALUES";
		
		try{
			if(!this.exists(nome)){
				try{
					nome = substituiAspasSimplesPorUmaValidaNoBD(nome); //para adicionar aspas simples no banco
					this.executaUpdate(comandoInsercao + " (" +
								"NEXT VALUE FOR seq_cargo" + ", " +
								"\'" + nome + "\'" + ", " +
								numeroDigitos + 
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
	
	/** Exclui um cargo do banco de dados
	 * @param nomeCargo <code>String</code> com o nome do cargo
	 * @return <code>boolean</code> com <code>true</code> se o cargo foi excluido e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean excluir(String nomeCargo) throws SQLException{
		this.abreConexao();
		
		nomeCargo = substituiAspasSimplesPorUmaValidaNoBD(nomeCargo);
		this.executaUpdate("DELETE FROM cargo WHERE nome=\'" + nomeCargo + "\'");
		
		this.fechaConexao();
		
		return true;
	}
	
	/** Verifica se existe um cargo existe
	 * @param nomeCargo <code>String</code> com o nome do cargo
	 * @return <code>boolean</code> com <code>true</code> se o cargo existe e <code>false</code> senão.
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected boolean exists(String nomeCargo) throws SQLException{
		int contagem = 0;
		
		this.abreConexao();
		
		nomeCargo = substituiAspasSimplesPorUmaValidaNoBD(nomeCargo);
		ResultSet resultadoQuery = this.executaQuery("" +
				"SELECT COUNT(*) AS contagem FROM cargo WHERE nome=\'" + nomeCargo + "\'");
		resultadoQuery.next();
		String contagemCandidatos = resultadoQuery.getString("contagem");
		if (contagemCandidatos != null){
			contagem = Integer.parseInt(contagemCandidatos);
		}
		
		this.fechaConexao();
		
		return contagem > 0 ? true : false;
	}
	
	/** Retorna um {@code List<Cargo>} com todos os cargos cadastrados no banco de dados, se não há cargos cadastrados retorna uma lista vazia
	 * @return {@code List<Cargo>} com todos os cargos cadastrados no banco de dados, se não há cargos cadastrados retorna uma lista vazia
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static List<Cargo> getAll() throws SQLException{
		List<Cargo> cargos = new ArrayList<Cargo>();
		String nome;
		int numeroDigitos;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM cargo");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
				nome 	= resultadoQuery.getString("nome");
				numeroDigitos = Integer.parseInt(resultadoQuery.getString("numeroDigitos"));
				cargos.add(new Cargo(nome, numeroDigitos));
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return cargos;
	}
	
	/** Busca um cargo com o nome especifico, um objeto <code>Cargo</code> é retornado.
	 * @param nomeCargo <code>String</code> com o nome do cargo.
	 * @return <code>Cargo</code> com o cargo de nome <code>nomeCargo</code>. Se não encontrar o cargo, é retornado <code>null</code>
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected static Cargo getCargo(String nomeCargo) throws SQLException{
		Cargo cargo = null;
		String nome;
		int numeroDigitos;
		
		BANCO_DE_DADOS_ELEICOES.abreConexao();
		nomeCargo = substituiAspasSimplesPorUmaValidaNoBD(nomeCargo);
		ResultSet resultadoQuery = BANCO_DE_DADOS_ELEICOES.executaQuery("SELECT * FROM cargo WHERE nome=\'" + nomeCargo + "\'");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
				nome 	= resultadoQuery.getString("nome");
				numeroDigitos = Integer.parseInt(resultadoQuery.getString("numeroDigitos"));
				cargo = new Cargo(nome, numeroDigitos);
    		}
		}
    	catch (SQLException e) {
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
    	BANCO_DE_DADOS_ELEICOES.fechaConexao();
		
		return cargo;
	}
	
}
