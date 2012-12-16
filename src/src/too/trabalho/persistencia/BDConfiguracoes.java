package too.trabalho.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import too.trabalho.gui.JanelaMensagem;

/** Classe para manipular as configuracoes do sistema no banco de dados
 * @author Arthur Assunção
 * 
 * 
 * @see BDEleicoes
 */
public abstract class BDConfiguracoes extends BDEleicoes {
	
	private final String SENHA = "senha";
	
	/** Atualiza a senha de administrador do sistema.
	 * @param senhaAtual <code>String</code> com a senha atual
	 * @param novaSenha <code>String</code> com a nova senha
	 * @return <code>boolean</code> com <code>true</code> se a senha foi alterada e <code>false</code> senão
	 */
	protected boolean atualizarSenha(String senhaAtual, String novaSenha){
		String comandoUpdate;
		String clausulaWhere;
		String senha = null;
		
		comandoUpdate 	 	= "UPDATE configuracoes SET ";		
		clausulaWhere 		 	= " WHERE nome=" + SENHA;
		
		List<ConfiguracoesNomeValor> configuracoes = null;
		try{
			configuracoes = getAll();
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			return false;
		}
		for(ConfiguracoesNomeValor configs : configuracoes){
			if (configs.getNome().equals(SENHA)){
				if(configs.getValor().equals(senhaAtual)){
					senha = novaSenha;
					break;
				}
			}
		}
		
		if(senha != null){
			try{
				String comandoSQL = comandoUpdate +
												"valor=\'" + senha + "\'" +
												clausulaWhere;
				this.executaUpdate(comandoSQL);
			}
			catch(SQLException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
				return false;
			}		
			return true;
		}
		return false;
	}
	
	/** Retorna a senha atual do sistema
	 * @return <code>String</code> com a senha do sistema
	 */
	protected String getSenha(){
		String senha = null;
		List<ConfiguracoesNomeValor> configuracoes = null;
		try{
			configuracoes = getAll();
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		if(configuracoes != null){
			for(ConfiguracoesNomeValor configs : configuracoes){
				if (configs.getNome().equals(SENHA)){
					senha = configs.getValor();
				}
			}
		}
		return senha;
	}
	
	/** Retorna todas as configuracoes do sistema
	 * @return {@code List<ConfiguracoesNomeValor>} com o nome e valor das configuracoes do sistema
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	private List<ConfiguracoesNomeValor> getAll() throws SQLException{
		List<ConfiguracoesNomeValor> configuracoes = new ArrayList<ConfiguracoesNomeValor>();
		String nome;
		String valor;
		
		this.abreConexao();
		ResultSet resultadoQuery = this.executaQuery("SELECT * FROM configuracoes");
		
		//resultSet posiciona o cursor antes da primeira linha, entao o next() abaixo ja o coloca na primeira linha, caso haja
    	try {
    		while(resultadoQuery.next()){
				nome 		= resultadoQuery.getString("nome");
				valor 	= resultadoQuery.getString("valor");
				configuracoes.add(new ConfiguracoesNomeValor(nome, valor));
    		}
		}
    	catch (SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
		}
		
		this.fechaConexao();
		
		return configuracoes;
	}
	
	/** Seta o tema(Look and Feel) do programa
	 * @param tema <code>String</code> com o nome do tema
	 */
	protected void setTema(String tema){
		tema = substituiAspasSimplesPorUmaValidaNoBD(tema);
		String comandoSetaTema = null;
		String temaAtual = null;
		try {
			temaAtual = this.getTema();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(temaAtual == null){ //insere
			comandoSetaTema = "INSERT INTO configuracoes VALUES (NEXT VALUE FOR seq_configuracoes, 'tema', \'" + tema + "\');";
		}
		else{ //atualiza
			comandoSetaTema = "UPDATE configuracoes SET valor=\'" + tema + "\' WHERE nome='tema'";
		}
		try {
			BANCO_DE_DADOS_ELEICOES.executaUpdate(comandoSetaTema);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** Retorna o tema(Look and Feel) atual do programa
	 * @return <code>String</code> com o nome do tema atual
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected String getTema() throws SQLException{
		String comando = "SELECT valor FROM configuracoes WHERE nome='tema'";
		String tema = null;
		ResultSet resultado = BANCO_DE_DADOS_ELEICOES.executaQuery(comando);
		if (resultado.next()){
			tema = resultado.getString("valor");
		}
		return tema;
	}
	
	/** Classe para armazenar o nome e valor de uma configuracas do sistema
	 * @author Arthur Assunção
	 * 
	 * @see BDConfiguracoes
	 */
	private class ConfiguracoesNomeValor{
		private String nome;
		private String valor;
		
		public ConfiguracoesNomeValor(String nome, String valor){
			this.nome = nome;
			this.valor = valor;
		}

		/**
		 * @return <code>String</code> com o nome
		 */
		public String getNome() {
			return nome;
		}

		/**
		 * @return <code>String</code> com o valor
		 */
		public String getValor() {
			return valor;
		}
		
	}
	
}
