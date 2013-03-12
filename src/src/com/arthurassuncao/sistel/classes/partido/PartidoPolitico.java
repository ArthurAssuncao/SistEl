package com.arthurassuncao.sistel.classes.partido;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arthurassuncao.sistel.gui.JanelaMensagem;
import com.arthurassuncao.sistel.persistencia.BDPartido;
import com.arthurassuncao.sistel.persistencia.BancoDeDados;

/** A classe <code>PartidoPolitico</code> manipula dados referente aos partidos
 * @author Arthur Assunção
 * 
 * @see BDPartido
 */
public class PartidoPolitico extends BDPartido{
	private String sigla;
	private String nome;
	private int numeroPartido;
	
	/** Cria um partido com sigla, nome e numero especificos
	 * @param sigla <code>String</code> com a sigla do partido
	 * @param nome  <code>String</code> com o nome do partido
	 * @param numero <code>int</code> com o numero do partido
	 */
	public PartidoPolitico(String sigla, String nome, int numero){
		this.sigla = sigla;
		this.nome = nome;
		this.numeroPartido = numero;
	}
	
	/** Retorna um <code>int</code> com o numero do partido
	 * @return <code>int</code> com o numero do partido
	 */
	public int getNumeroPartido() {
		return numeroPartido;
	}

	/** Retorna um <code>String</code> com a sigla do partido
	 * @return <code>String</code> com a sigla do partido
	 */
	public String getSigla() {
		return sigla;
	}
	/** Retorna um <code>String</code> com o nome do partido
	 * @return <code>String</code> com o nome do partido
	 */
	public String getNome() {
		return nome;
	}
	
	//Parte de Banco de Dados
	/** Insere um partido no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see PartidoPolitico#inserir(PartidoPolitico)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	public int inserir(){
		return super.inserir(this);
	}
	
	/** Exclui um partido do banco de dados
	 * @return <code>boolean</code> com <code>true</code> se o partido foi excluido e <code>false</code> senão.
	 * 
	 */
	public boolean excluir(){
		boolean excluiu = false;
		try{
			excluiu = super.excluir(this.getNumeroPartido());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return excluiu;
	}
	
	/** Verifica se existe um partido com o mesmo numero ou sigla deste existe no banco de dados
	 * @return <code>boolean</code> com <code>true</code> se o partido existe e <code>false</code> senão.
	 * 
	 */
	public boolean exists(){
		boolean existe = false;
		try{
			existe = super.exists(this.getNumeroPartido(), this.getSigla());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return existe;
	}
	
	/** Retorna <code>int</code> com o id do partido pesquisado, caso nao exista retorna -1
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return <code>int</code> com o id do partido pesquisado, caso nao exista retorna -1
	 */
	public static int getIdPartido(int numeroPartido){
		int idPartido = -1;
		try{
			idPartido = BDPartido.getId(numeroPartido);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return idPartido;
	}
	
	/** Verifica se existe um partido com o numero fornecido existe no banco de dados
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return <code>boolean</code> com <code>true</code> se o partido existe e <code>false</code> senão.
	 * 
	 */
	public static boolean partidoPoliticoExists(int numeroPartido){
		boolean partidoExiste = false;
		try{
			partidoExiste = BDPartido.pesquisaPartidoPolitico(numeroPartido).size() == 0 ? false : true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return partidoExiste;
	}
	
	/** Pesquisa por um partido no banco de dados atravez de um numero de partido especifico, se houver inconsistencias gera mensagem de erro para o usuario
	 * @param numeroPartido <code>int</code> com o numero do partido
	 * @return <code>PartidoPolitico</code> com o partido pesquisado, se não encontrar retorna <code>null</code> 
	 * @see JanelaMensagem
	 */
	public static PartidoPolitico pesquisa(int numeroPartido){
		PartidoPolitico partido = null;
		try{
			List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
			partidos = BDPartido.pesquisaPartidoPolitico(numeroPartido);
			switch(partidos.size()){
			case 0: //nao achou partido
				break;
			case 1: //achou partido
				partido = partidos.get(0);
				break;
			default: //achou mais de um
				//tecnicamente impossivel chegar aqui, inconsistencia no banco de dados
				JanelaMensagem.mostraMensagemErro(null, "Há inconsistencias no banco de dados\nExiste dados de partidos duplicados");
				break;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return partido;
	}
	/** Pesquisa por um partido no banco de dados atravez de uma sigla especifica, se houver inconsistencias gera mensagem de erro para o usuario
	 * @param sigla <code>String</code> com a sigla do partido
	 * @return <code>PartidoPolitico</code> com o partido pesquisado, se não encontrar retorna <code>null</code> 
	 * @see JanelaMensagem
	 */
	public static PartidoPolitico pesquisa(String sigla){
		PartidoPolitico partido = null;
		try{
			List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
			partidos = BDPartido.pesquisaPartidoPolitico(sigla);
			
			switch(partidos.size()){
				case 0: //nao achou partido
					break; 
				case 1: //achou partido
					partido = partidos.get(0);
					break;
				default: //achou mais de um
					//tecnicamente impossivel chegar aqui
					JanelaMensagem.mostraMensagemErro(null, "Há inconsistencias no banco de dados\nExiste dados de partidos duplicados");
					break;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return partido;
	}
	
	/** Pesquisa um partido pelo seu id no banco de dados
	 * @param idPartido <code>int</code> com o id do partido procurado
	 * @return um <code>PartidoPolitico</code> com os dados referentes ao id do partido fornecido, se o partido não for encontrado retorna <code>null</code>
	 */
	public static PartidoPolitico pesquisaById(int idPartido){
		PartidoPolitico partido = null;
		try{
			partido = BDPartido.pesquisaById(idPartido);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return partido;
	}
	
	/** Retorna todos os partido cadastrados no banco de dados
	 * @return um {@code List<PartidoPolitico>} com todos os partidos do banco de dados, se não houver partidos cadastrados retorna uma lista vazia.
	 */
	public static List<PartidoPolitico> getAll(){
		try{
			return BDPartido.getAll();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
