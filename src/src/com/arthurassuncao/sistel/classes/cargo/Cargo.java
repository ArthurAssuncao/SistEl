package com.arthurassuncao.sistel.classes.cargo;

import java.sql.SQLException;
import java.util.List;

import com.arthurassuncao.sistel.persistencia.BDCargo;
import com.arthurassuncao.sistel.persistencia.BancoDeDados;

/** A classe <code>Cargo</code> manipula dados referente aos cargos
 * @author Arthur Assunção
 * 
 *
 * @see BDCargo
 * @see Comparable
 */
public class Cargo extends BDCargo implements Comparable<Cargo>{
	String nome;
	int numeroDigitos;
	
	/*private Cargo(){
	}*/
	
	/** Cria um cargo com nome e numero de digitos especificos
	 * @param cargo <code>String</code> com o nome do cargo
	 * @param numeroDigitos <code>int</code> com o numero de digitos do cargo
	 */
	public Cargo(String cargo, int numeroDigitos){
		this.nome = cargo;
		this.numeroDigitos = numeroDigitos;
	}
	
	/** Insere um cargo no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Cargo#inserir(Cargo)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	public int inserir(){
		return super.inserir(this);
	}
	
	/** Exclui um cargo do banco de dados
	 * @return <code>boolean</code> com <code>true</code> se o cargo foi excluido e <code>false</code> senão.
	 * 
	 */
	public boolean excluir(){
		boolean excluiu = false;
		try{
			excluiu = super.excluir(this.getNome());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return excluiu;
	}
	
	/** Verifica se existe um cargo com o mesmo nome deste
	 * @return <code>boolean</code> com <code>true</code> se o cargo existe e <code>false</code> senão.
	 * 
	 */
	public boolean exists(){
		boolean existe = false;
		try{
			existe = super.exists(this.getNome());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return existe;
	}
	
	/*public Cargo getCargo(){
		Cargo cargo = null;
		
		try{
			cargo = BDCargo.getCargo(this.nome);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return cargo;		
	}*/
	
	/** Retorna um objeto <code>Cargo</code> caso o nome do cargo passado como parametro seja encontrado, do contrario retorna <code>null</code>
	 * @param nomeCargo <code>String</code> com o nome do cargo que se pretende encontrar.
	 * @return <code>Cargo</code> com o cargo de nome <code>nomeCargo</code>, caso o cargo não seja encontrado retorna <code>null</code>
	 */
	public static Cargo getCargo(String nomeCargo){
		Cargo cargo = null;
		
		try{
			cargo = BDCargo.getCargo(nomeCargo);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return cargo;		
	}

	/** Retorna um <code>String</code> com o nome do cargo
	 * @return <code>String</code> com o nome do cargo
	 */
	public String getNome() {
		return nome;
	}

	/** Retorna um <code>int</code> com o numero de digitos do cargo
	 * @return <code>int</code> com o numero de digitos do cargo
	 */
	public int getNumeroDigitos() {
		return numeroDigitos;
	}
	
	/** Retorna uma lista com todos os cargos cadastrados no banco de dados
	 * @return {@code List<Cargo>} com todos os cargos cadastrados no banco de dados, se não há cargos cadastrados retorna uma lista vazia.
	 * Se algum erro ocorrer retorna <code>null</code>
	 */
	public static List<Cargo> getAll(){
		try{
			return BDCargo.getAll();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/** Compara dois objetos <code>Cargo</code>. O numero de digitos é usado na comparação.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @return <code>int</code> com 0 se o numero de digitos é igual, 
	 * um valor menor que 0 se o numero de digitos deste cargo for maior que o numero de digitos do cargo passado como parametro.
	 * um valor maior que 0 se o numero de digitos deste cargo for menor que o numero de digitos do cargo passado como parametro.
	 * 
	 */
	@Override
	public int compareTo(Cargo cargo) {
		Integer numeroDigitosDesseCargo = this.numeroDigitos;
		Integer numeroDigitosOutroCargo = cargo.getNumeroDigitos();
		return (numeroDigitosDesseCargo.compareTo(numeroDigitosOutroCargo)) * -1;
	}
	
}
