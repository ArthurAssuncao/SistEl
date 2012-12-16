package too.trabalho.recursos;

import java.io.InputStream;
import java.net.URL;

/** Classe para carregar recursos como imagens, icones, fontes etc
 * @author Arthur Assuncao
 * 
 *
 */
public abstract class Recursos {
	
	private static final String ENDERECO = "/too/trabalho/recursos/";
	
	/** Encontra o recurso no diretorio de recursos do jar
	 * @param enderecoArquivo <code>String</code> caminho do recurso
	 * @return <code>URL</code> com o endereco do recurso
	 */
	public static URL getResource(String enderecoArquivo){
		return Recursos.class.getResource(ENDERECO + enderecoArquivo);
	}
	/** Encontra o recurso no diretorio de recursos do jar
	 * @param enderecoArquivo <code>String</code> caminho do recurso
	 * @return <code>InputStream</code> com o stream do recurso
	 */
	public static InputStream getResourceAsStream(String enderecoArquivo){
		return Recursos.class.getResourceAsStream(ENDERECO + enderecoArquivo);
	}
	/*public static String getResourceAsString(String enderecoArquivo){
		return Recursos.class.getResource(ENDERECO + enderecoArquivo).getFile();
	}*/
	/*public static File getResourceAsFile(String enderecoArquivo){
		return new File(Recursos.class.getResource(ENDERECO + enderecoArquivo).getFile());
	}*/
}
