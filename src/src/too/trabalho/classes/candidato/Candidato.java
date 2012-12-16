package too.trabalho.classes.candidato;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import too.trabalho.classes.partido.PartidoPolitico;
import too.trabalho.gui.Imagem;
import too.trabalho.gui.JanelaMensagem;
import too.trabalho.persistencia.BDCandidado;
import too.trabalho.persistencia.BancoDeDados;

/** A classe <code>Candidato</code> manipula dados referente aos candidatos
 * @author Arthur Assunção
 * 
 * 
 * @see BDCandidado
 *
 */
public class Candidato extends BDCandidado {
	
	/** <code>String</code> com o endereco relativo do diretorio de imagens dos candidatos */
	private final String DIRETORIO_IMAGENS = "imagens_candidatos";
	/** <code>String</code> com o endereco relativo da foto padrão dos candidatos */
	private static final String ENDERECO_FOTO_PADRAO = "imagens/semFoto.png";
	/** <code>String</code> com o endereco relativo da foto padrão dos candidatos */
	public static final String FOTO_PADRAO 	= ENDERECO_FOTO_PADRAO; //imagem para candidato sem foto
	
	/** <code>int</code> para representar a largura da foto do candidato */
	public static final int LARGURA_FOTO 		= 130;
	/** <code>int</code> para representar a altura da foto do candidato */
	public static final int ALTURA_FOTO  		= 150;
	
	private String nome;
	private int numeroCandidato;
	private PartidoPolitico partidoPolitico;
	private String enderecoFoto = ENDERECO_FOTO_PADRAO;
	private String cargo;
	
	/*private Candidato(){
	}*/
	
	/** Cria um <code>Candidato</code> com nome, numero, cargo, partido e endereco da foto especificos
	 * @param nome <code>String</code> com o nome do candidato
	 * @param numero <code>int</code> com o numero do candidato
	 * @param cargo <code>String</code> com o cargo do candidato
	 * @param partido <code>PartidoPolitico</code> do partido do candidato
	 * @param enderecoFoto <code>String</code> com o endereço da foto do candidato
	 */
	public Candidato(String nome, int numero, String cargo, PartidoPolitico partido, String enderecoFoto){
		this.nome = nome;
		this.numeroCandidato = numero;
		this.cargo = cargo;
		this.partidoPolitico = partido;
		setEnderecoFoto(enderecoFoto);
	}
		
	/** Retorna o endereço da foto do candidato, caso a imagem não exista retorna a foto padrão dos candidatos
	 * @return <code>String</code> com o endereco da foto do candidato
	 */
	public String getEnderecoFoto() {
		String endereco;
		//String enderecoFotoPadraoInvalido = "/C:/Programacao/Teoria%20de%20Orientacao%20a%20Objetos/Trabalhos/processo-eleitoral/bin/too/trabalho/gui/imagens/semFoto.jpg";
		if (this.enderecoFoto != null && !this.enderecoFoto.isEmpty()/* && !this.enderecoFoto.equalsIgnoreCase(enderecoFotoPadraoInvalido)*/){
			this.enderecoFoto = this.enderecoFoto.replace("\\", "/");
			endereco = this.enderecoFoto;
		}
		else{
			String enderecoPadrao;
			enderecoPadrao = ENDERECO_FOTO_PADRAO.replace("\\", "/");
			endereco = enderecoPadrao;
		}
		File imagem = new File(endereco);
		if (imagem.exists()){
			return endereco;
		}
		else{
			return ENDERECO_FOTO_PADRAO.replace("\\", "/");
		}
	}
	/** Muda o endereco da foto do candidato, caso o endereço passado seja <code>null</code> ou vazio, a foto não é modificada.
	 * @param foto <code>String</code> com o endereco da foto do candidato
	 */
	public void setEnderecoFoto(String foto) {
		if (foto != null && !foto.isEmpty()){
			this.enderecoFoto = foto;
		}
	}
	
	/** Retorna um objeto da classe <code>PartidoPolitico</code> com o partido do candidato
	 * @return o <code>PartidoPolitico</code> do candidato
	 */
	public PartidoPolitico getPartidoPolitico() {
		return partidoPolitico;
	}
	
	/** Retorna um <code>String</code> com o cargo do candidato 
	 * @return <code>String</code> com o cargo do candidato
	 */
	public String getCargo() {
		return cargo;
	}
	
	/** Retorna um <code>String</code> com o nome do candidato
	 * @return <code>String</code> com o nome do candidato
	 */
	public String getNome() {
		return nome;
	}
	
	
	/** Retornar um <code>int</code> com o numero do candidato
	 * @return <code>int</code> com o numero do candidato
	 */
	public int getNumeroCandidato() {
		return numeroCandidato;
	}
	
	/** Muda o numero do candidato
	 * @param numeroCandidato <code>int</code> com o novo numero para o candidato
	 */
	public void setNumeroCandidato(int numeroCandidato) {
		this.numeroCandidato = numeroCandidato;
	}
	
	/** Compara dois <code>Candidato</code>. Retorna <code>true</code> se o candidato tem o mesmo numero e cargo e <code>false</code> senão.
	 * @return <code>boolean</code> com <code>true</code> se o candidato tem o mesmo numero e cargo e <code>false</code> senão.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof Candidato){
			Candidato segundoCandidato = (Candidato)anObject;
			
			//Compara o numero do candidato com mais um digito no final, o numero referente ao cargo, exemplo: 131230 = numero 13123 + Presidente
			if (( this.getNumeroCandidato()  ==  segundoCandidato.getNumeroCandidato()) && (this.getCargo().equals(segundoCandidato.getCargo())) ){
				return true;
			}
		}
		return false;
	}
	

	/** Retorna o <code>hash</code> deste <code>Candidato</code>. O hashcode é calculado da seguinte forma:<br>
	 * <code>31 * (31 + numeroCandidato) + cargo.hashCode()</code></br>
	 * @return <code>int</code> com o hashcode do candidato.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int primo = 31;
		int resultado = 1;
		int numeroCandidato =  this.numeroCandidato;
		int hashCargoCandidato = ((this.cargo == null) ? 0 : this.cargo.hashCode());
		
		resultado = primo * resultado + numeroCandidato;
		resultado = primo * resultado + hashCargoCandidato;
		
		return resultado;
	}

	/** Insere um candidato no banco de dados
	 * @return <code>int</code> com o resultado da inserção. Este valor é uma constante definida na classe <code>BancoDeDados</code>
	 * 
	 * @see Candidato#inserir(Candidato)
	 * @see Imagem#copiaImagemParaPastaImagensCandidatos(String, String, String)
	 * @see Imagem#redimensionaImagem(String, int, int)
	 * @see BancoDeDados#RESULTADO_SUCESSO
	 * @see BancoDeDados#RESULTADO_ERRO_REGISTRO_DUPLICADO
	 * @see BancoDeDados#RESULTADO_ERRO_BANCO_DADOS
	 * @see BancoDeDados#RESULTADO_ERRO_DESCONHECIDO
	 * 
	 */
	public int inserir(){
		int resultadoInsercao;
		if (!this.exists()){
			String novoEnderecoFoto = null;
			try{
				//copia imagem para diretorio de imagens do programa
				if (!this.getEnderecoFoto().equals(ENDERECO_FOTO_PADRAO)){
					//novoEnderecoFoto = this.copiaImagemParaPastaImagensCandidatos(this.getEnderecoFoto());
					String novoNome = this.substituiEspacoNomeCandidato('_').toLowerCase() + "-" + this.numeroCandidato;
					novoEnderecoFoto = Imagem.copiaImagemParaPastaImagensCandidatos(this.getEnderecoFoto(), novoNome, DIRETORIO_IMAGENS);
					this.setEnderecoFoto(novoEnderecoFoto);
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			//insere o candidato no banco de dados
			resultadoInsercao = super.inserir(this);
			if (resultadoInsercao != BancoDeDados.RESULTADO_SUCESSO){ //se nao cadastrou
				//Apaga a imagem criada
				File fotoCandidato = new File(novoEnderecoFoto);
				fotoCandidato.delete();
			}
			else{
				//redimensiona imagem
				try{
					if (!this.getEnderecoFoto().equals(ENDERECO_FOTO_PADRAO)){
						//redimensionarImagem(novoEnderecoFoto);
						Imagem.redimensionaImagem(novoEnderecoFoto, Candidato.LARGURA_FOTO, Candidato.ALTURA_FOTO);
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			return resultadoInsercao;
		}
		else{
			//JanelaMensagem.mostraMensagemErro(null, "Numero de Candidato e Cargo já cadastrados");
			return BancoDeDados.RESULTADO_ERRO_REGISTRO_DUPLICADO;
		}
		
	}

	/** Atualiza os dados do candidato no banco de dados
	 * @return <code>boolean</code> com <code>true</code> se os dados do candidato foram atualizados e <code>false</code> senão.
	 * @param id <code>int</code> com o id do candidato no banco de dados
	 * 
	 * @see Candidato#atualizarDados(Candidato, int)
	 * @see Imagem#copiaImagemParaPastaImagensCandidatos(String, String, String)
	 * @see Imagem#redimensionaImagem(String, int, int)
	 * @see Candidato#getId()
	 * 
	 */
	public boolean atualizarDados(int id){
		boolean atualizou = false;
		//verifica se os dados do candidato nao ferem as restricoes do banco
		if (!this.existsWithId(id)){
			atualizou = super.atualizarDados(this, id);
			if (atualizou){
				String novoEnderecoFoto = null;
				try{
					//copia imagem para diretorio de imagens do programa
					if (!this.getEnderecoFoto().equals(ENDERECO_FOTO_PADRAO)){
						//novoEnderecoFoto = this.copiaImagemParaPastaImagensCandidatos(this.getEnderecoFoto());
						String novoNome = this.substituiEspacoNomeCandidato('_').toLowerCase() + "-" + this.numeroCandidato;
						novoEnderecoFoto = Imagem.copiaImagemParaPastaImagensCandidatos(this.getEnderecoFoto(), novoNome, DIRETORIO_IMAGENS);
						this.setEnderecoFoto(novoEnderecoFoto);
						//redimensiona imagem
						try{
							if (!this.getEnderecoFoto().equals(ENDERECO_FOTO_PADRAO)){
								//redimensionarImagem(novoEnderecoFoto);
								Imagem.redimensionaImagem(novoEnderecoFoto, Candidato.LARGURA_FOTO, Candidato.ALTURA_FOTO);
							}
						}
						catch(IOException e){
							e.printStackTrace();
						}
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}	
		return atualizou;
	}
	/** Verifica se existe um candidato com o mesmo numero e cargo deste <code>Candidato</code>
	 * @return <code>boolean</code> com <code>true</code> se o candidato existe e <code>false</code> senão.
	 * 
	 */
	public boolean exists(){
		boolean candidatoExiste = false;
		try{
			candidatoExiste = super.exists(this.getNumeroCandidato(), this.getCargo());
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return candidatoExiste;
	}
	/** Verifica se existe um candidato com o mesmo numero e cargo e id diferente deste <code>Candidato</code>
	 * @return <code>boolean</code> com <code>true</code> se existe um outro candidato com o mesmo numero e cargo e <code>false</code> senão.
	 * @param id <code>int</code> com o id do candidato no banco de dados
	 */
	public boolean existsWithId(int id){
		boolean candidatoExiste = false;
		try{
			candidatoExiste = super.existsWithId(this.getNumeroCandidato(), this.getCargo(), id);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return candidatoExiste;
	}
	/** Exclui um candidato do banco de dados
	 * @return <code>boolean</code> com <code>true</code> se o candidato foi excluido e <code>false</code> senão.
	 * 
	 */
	public boolean excluir(){
		boolean excluiu;
		try{
			String enderecoImagem = this.enderecoFoto;
			excluiu = super.excluir(this.getNumeroCandidato(), this.getCargo());
			if(enderecoImagem != ENDERECO_FOTO_PADRAO){
				File fotoCandidato = new File(enderecoImagem);
				if(fotoCandidato.exists()){
					if(fotoCandidato.delete()){
						//excluiu com sucesso
					}
					else{
						JanelaMensagem.mostraMensagemErro(null, "Erro ao excluir arquivo: " + enderecoImagem);
					}
				}
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return excluiu;
	}
	/** Pesquisa por um candidato no banco de dados
	 * @return <code>{@code List<Candidato>}</code> com o candidato que tem o numero e cargo especificado
	 * @see BDCandidado#pesquisar(int, String)
	 * 	@see List
	 */
	public static List<Candidato> pesquisar(int numeroCandidato, String cargo){
		try{
			return BDCandidado.pesquisar(numeroCandidato, cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/** Pesquisa por candidatos no banco de dados
	 * @return <code>{@code List<Candidato>}</code> com os candidatos que tem no nome o nome especificado
	 * @see BDCandidado#pesquisar(String)
	 * @see List
	 */
	public static List<Candidato> pesquisar(String nomeCandidato){
		try{
			return BDCandidado.pesquisar(nomeCandidato);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	/** Pesquisa por candidatos no banco de dados
	 * @return <code>{@code List<Candidato>}</code> com os candidatos que o nome e cargo especificado.
	 * @see BDCandidado#pesquisar(String, String)
	 * @see List
	 */
	public static List<Candidato> pesquisar(String nomeCandidato, String cargo){
		try{
			return BDCandidado.pesquisar(nomeCandidato, cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	/** Pesquisa por candidatos no banco de dados
	 * @return <code>{@code List<Candidato>}</code> com os candidatos que são do cargo especificado
	 * @see BDCandidado#pesquisarByCargo(String)
	 * @see List
	 */
	public static List<Candidato> pesquisarByCargo(String cargo){
		try{
			return BDCandidado.pesquisarByCargo(cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/** Retorna o id do candidato no banco de dados
	 * @return <code>int</code> com o id do candidato no banco de dados, caso não encontre retorna <code>0</code>
	 * @see Candidato#getId(int, String)
	 */
	public int getId(){
		int id = 0;
		try{
			id = super.getId(this.numeroCandidato, this.cargo);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}
	
	/** Retorna o nome candidato substituindo os espaços pelo caracter especificado
	 * @return <code>String</code> com o nome do candidato com o caracter especificado no lugar dos espaços
	 */
	private String substituiEspacoNomeCandidato(char caracter){
		return this.nome.replace(' ', caracter);
	}
	
}

