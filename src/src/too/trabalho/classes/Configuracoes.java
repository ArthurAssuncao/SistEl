package too.trabalho.classes;

import java.sql.SQLException;

import too.trabalho.persistencia.BDConfiguracoes;

/** A Classe <code>Configuracoes</code> define a estrutura das configurações do sistema
 * 
 * @author Arthur Assunção
 * 
 * 
 * @see BDConfiguracoes
 *
 */
public class Configuracoes extends BDConfiguracoes{
	private static Configuracoes CONFIGURACOES = new Configuracoes();
	
	/** Cria uma instancia da classe <code>Configuracoes</code>
	 * 
	 */
	private Configuracoes(){
		
	}
	
	public static Configuracoes getInstance(){
		return CONFIGURACOES;
	}
	
	/** Define a senha para acessar configurações que necessitam dela
	 * 
	 * @param senhaAntiga <code>String</code> com a senha atual
	 * @param senhaNova <code>String</code> com a nova senha
	 * @return <code>true</code> se a senha for atualizada e <code>false</code> se a senha não for atualizada
	 */
	public boolean setSenha(String senhaAntiga, String senhaNova){
		return super.atualizarSenha(senhaAntiga, senhaNova);
	}
	
	/** Obtem a senha do banco de dados
	 * @see too.trabalho.persistencia.BDConfiguracoes#getSenha()
	 * @return <code>String</code> com a senha
	 */
	@Override
	public String getSenha(){
		return super.getSenha();
	}
	
	@Override
	public void setTema(String tema){
		super.setTema(tema);
	}
	
	@Override
	public String getTema(){
		String tema = null;
		try {
			tema = super.getTema();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tema;
	}
	
}
