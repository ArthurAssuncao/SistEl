package too.trabalho.persistencia;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.hsqldb.lib.tar.TarMalformatException;
import org.hsqldb.util.DatabaseManagerSwing;

import too.trabalho.classes.Arquivo;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.recursos.Recursos;

/** Classe para manipular o banco de dados do sistema
 * @author Arthur Assunção
 * 
 * @see BancoDeDados
 */
public class BDEleicoes extends BancoDeDados implements Runnable{
	private static final String DIRETORIO_BANCO_DE_DADOS = "banco/";
	/** Caminho do banco de dados*/
	protected static final String ENDERECO_BANCO_DE_DADOS = DIRETORIO_BANCO_DE_DADOS + "DBEleicao"; //caminho do banco de dados
	private static final String ENDERECO_ARQUIVO_REMOVE_ESTRUTURA_SQL 	= "bd/DBEleicoesDeletaTabelas.sql"; //arquivo para apagar as tabelas
	private static final String ENDERECO_ARQUIVO_ESTRUTURA_SQL 	= "bd/DBEleicoesCriaTabelas.sql"; //arquivo para criar as tabelas e sequencias
	private static final String ENDERECO_ARQUIVO_EXCLUI_DADOS_SQL = "bd/DBEleicoesDeletaRegistrosTabelas.sql";
	private static final String ENDERECO_ARQUIVO_DADOS_SQL 	= "bd/DBEleicoesInsereDados.sql"; //arquivo para inserir os dados
	private static final String[] CONFIGS_BANCO_DADOS = {"--url", "jdbc:hsqldb:file:" + ENDERECO_BANCO_DE_DADOS, 
		"--user", "sa",
		"--password", "",
	"--noexit"};
	/*private static final String chaveDaCriptografica = "tp";
	private static final String[] CONFIGS_BANCO_DADOS = {"--url", "jdbc:hsqldb:file:" + ENDERECO_BANCO_DE_DADOS + ";crypt_key=" + chaveDaCriptografica + ";crypt_type=DESede", 
		 "--user", "sa",
		 "--password", "",
		 "--noexit"};*/

	/** <code>BDEleicoes</code> com a instancia do banco de dados */
	protected static BDEleicoes BANCO_DE_DADOS_ELEICOES = new BDEleicoes();

	//Tamanho dos dados(varchar e numeric) no banco de dados
	//Partido
	/** <code>int</code> com o numero de caracteres maximo da sigla do partido */
	public static final int TAMANHO_PARTIDO_SIGLA 		= 6;
	/** <code>int</code> com o numero de caracteres maximo do nome do partido */
	public static final int TAMANHO_PARTIDO_NOME 		= 100;
	/** <code>int</code> com o numero de caracteres maximo do numero do partido */
	public static final int TAMANHO_PARTIDO_NUMERO 	= 2;

	//Candidato
	/** <code>int</code> com o numero de caracteres maximo do nome do candidato */
	public static final int TAMANHO_CANDIDATO_NOME 		= 100;
	/** <code>int</code> com o numero de digitos maximo do numero do candidato */
	public static final int TAMANHO_CANDIDATO_NUMERO 	= 5;
	/** <code>int</code> com o numero de caracteres maximo do cargo do candidato */
	public static final int TAMANHO_CANDIDATO_CARGO 	= 20;
	/** <code>int</code> com o numero de caracteres maximo da foto do candidato */
	public static final int TAMANHO_CANDIDATO_FOTO 		= 255;

	//Partido

	//Cargo
	/** <code>int</code> com o numero de caracteres maximo do nome do cargo */
	public static final int TAMANHO_CARGO_NOME 			 = 20;
	/** <code>int</code> com o numero de caracteres maximo do numero de digitos do cargo */
	public static final int TAMANHO_CARGO_NUMERO_DIGITOS = 1;

	//Configuracoes


	/** Cria uma instancia do banco de dados do sistema
	 * 
	 */
	protected BDEleicoes(){
		super(ENDERECO_BANCO_DE_DADOS);
	}

	/** Retorna a instancia do banco de dados do sistema
	 * @return <code>BDEleicoes</code> com a instancia da classe
	 */
	public static BDEleicoes getInstance(){
		return BANCO_DE_DADOS_ELEICOES;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		try {
			this.iniciaBanco();
		}
		catch (SQLException e) {
			JanelaMensagem.mostraMensagemErroBD(null, "Falha ao iniciar o banco de dados: \n" + e.getMessage() + "\n\nPrograma sera finalizado");
			System.exit(0);
		}
	}

	/** Inicia o banco de dados
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	protected void iniciaBanco() throws SQLException{
		if(!iniciado){
			this.setArquivoBD(ENDERECO_BANCO_DE_DADOS);
			boolean diretorioExistia = true;
			File diretorio = new File(DIRETORIO_BANCO_DE_DADOS);
			if(!diretorio.exists()){
				diretorio.mkdirs();
				diretorioExistia = false;
			}
			this.abreConexao();
			this.fechaConexao();
			if(!diretorioExistia){
				try{
					//this.removeTabelas();
					System.out.println("Tabelas do banco de dados criadas");
					this.criaTabelas();
					//this.insereDados();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			BANCO_DE_DADOS_ELEICOES.iniciado = true;
			System.out.println("Banco de Dados Iniciado");
		}
	}

	/** Reinicia o banco de dados, apaga todas as tabelas e as recria
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public void reiniciaBanco() throws SQLException{
		this.setArquivoBD(ENDERECO_BANCO_DE_DADOS);
		this.abreConexao();
		this.fechaConexao();
		try{
			this.removeTabelas();
			this.criaTabelas();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	/** Abre e executa os comando de um arquivo SQL
	 * @param arquivo <code>String</code> com o endereco do arquivo sql
	 * @return <code>boolean</code> com <code>true</code> se o arquivo foi aberto e executado e <code>false</code> senão.
	 * @see too.trabalho.persistencia.BancoDeDados#abreArquivoSQL(java.lang.String)
	 */
	@Override
	public boolean abreArquivoSQL(String arquivo){
		boolean abriu = true;
		try{
			abriu = super.abreArquivoSQL(arquivo);
		}
		catch(IOException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			abriu = false;
		}
		catch(SQLException e){
			e.printStackTrace();
			JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
			abriu = false;
		}
		return abriu;
	}

	/** Cria um arquivo no formato tar.gz com endereço especifico contendo um backup do banco de dados.
	 * @param enderecoArquivoBackup <code>String</code> com o endereco do arquivo que será salvo.
	 * @return <code>boolean</code> com <code>true</code> se o backup foi criado e <code>false</code> senão.
	 * @throws TarMalformatException possivel erro ao gerar o arquivo no formato tar
	 * @throws IOException possivel erro ao criar o arquivo
	 */
	@Override
	public boolean criaBackupBancoDados(String enderecoArquivoBackup) throws TarMalformatException, IOException{
		boolean criouBackup = true;
		if (enderecoArquivoBackup != null){
			super.criaBackupBancoDados(enderecoArquivoBackup);
		}
		else{
			criouBackup = false;
		}
		return criouBackup;
	}

	/** Restaura o banco de dados a partir de um backup
	 * @param arquivoBackup <code>String</code> com o endereco do arquivo de backup
	 * @throws TarMalformatException possivel erro ao ler o arquivo no formato tar
	 * @throws IOException possivel erro ao abrir o arquivo ou restaurar os arquivos do backup
	 */
	@Override
	public void restauraBackupBancoDeDados(String arquivoBackup) throws TarMalformatException, IOException{
		if (arquivoBackup != null){
			super.restauraBackupBancoDeDados(arquivoBackup); //arquivo com extensao tar, tar.gz
			/*boolean reiniciar = false;
			reiniciar = JanelaMensagem.mostraMensagemConfirma(null, "Restaurar Backup", "Backup Restaurado\n" +
					"Para completar a restauração do banco de dados, o banco de dados deve ser reiniciado.\n" +
					"Deseja reiniciar agora?");
			if (reiniciar){
				BANCO_DE_DADOS_ELEICOES = null;
				BANCO_DE_DADOS_ELEICOES = new BDEleicoes();
			}*/
			//else{
			JanelaMensagem.mostraMensagem(null, "Restaurar Backup", "O novo banco de dados estará disponível na proxima iniciação");
			//}
		}
		else{
			//nao restaurou
		}
	}

	/** Remove as tabelas do banco de dados do sistema
	 * @throws IOException possivel erro ao abrir o arquivo sql
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	private void removeTabelas() throws IOException, SQLException{
		//remove todas as tabelas e sequencias do banco de dados
		String arquivoTemporario = "tempRemoveEstrutura.sql";
		Arquivo.copiaArquivo(Recursos.getResourceAsStream(ENDERECO_ARQUIVO_REMOVE_ESTRUTURA_SQL), new File(arquivoTemporario));
		abreArquivoSQL(arquivoTemporario);
		Arquivo.removeArquivo(arquivoTemporario);
	}
	/** Exclui os registros das tabelas do banco de dados do sistema
	 * @throws IOException possivel erro ao abrir o arquivo sql
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public void excluiRegistros() throws IOException, SQLException{
		//remove todas as tabelas e sequencias do banco de dados
		String arquivoTemporario = "tempExcluiDados.sql";
		Arquivo.copiaArquivo(Recursos.getResourceAsStream(ENDERECO_ARQUIVO_EXCLUI_DADOS_SQL), new File(arquivoTemporario));
		abreArquivoSQL(arquivoTemporario);
		Arquivo.removeArquivo(arquivoTemporario);
	}
	/** Cria as tabelas do banco de dados do sistema
	 * @throws IOException possivel erro ao abrir o arquivo sql
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	private void criaTabelas() throws IOException, SQLException{
		//Cria a estrutura do banco de dados: tabelas, sequencias
		String arquivoTemporario = "tempEstruturaTabelas.sql";
		Arquivo.copiaArquivo(Recursos.getResourceAsStream(ENDERECO_ARQUIVO_ESTRUTURA_SQL), new File(arquivoTemporario));
		abreArquivoSQL(arquivoTemporario);
		Arquivo.removeArquivo(arquivoTemporario);
	}
	/** Insere dados predefinidos nas tabelas do banco de dados do sistema
	 * @throws IOException possivel erro ao abrir o arquivo sql
	 * @throws SQLException possível erro gerado por má configuração do banco de dados
	 */
	public void insereDados() throws IOException, SQLException{
		//Insere os dados no banco de dados
		String arquivoTemporario = "tempInsereDados.sql";
		Arquivo.copiaArquivo(Recursos.getResourceAsStream(ENDERECO_ARQUIVO_DADOS_SQL), new File(arquivoTemporario));
		abreArquivoSQL(arquivoTemporario);
		Arquivo.removeArquivo(arquivoTemporario);
	}

	/** Abre a interface do banco de dados com as configuracoes do sistema
	 * 
	 */
	public static void mostraJanelaBancoDeDados(){
		//DatabaseManagerSwing.main(new String[] {"--help", "--noexit"}); //exibe o help
		DatabaseManagerSwing.main(CONFIGS_BANCO_DADOS);
	}


}
