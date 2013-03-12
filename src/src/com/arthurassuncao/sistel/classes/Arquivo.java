package com.arthurassuncao.sistel.classes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/** Classe para manipular arquivos
 * @author Arthur Assunção
 * 
 * 
 * @see File
 * @see InputStream
 * @see BufferedInputStream
 */
public class Arquivo {
	/** Copia um arquivo para outro diretorio
	 * @param origem <code>File</code> com o arquivo de origem
	 * @param destino <code>File</code> com o arquivo de destino
	 * @return <code>boolean</code> com <code>true</code> se o arquivo foi copiado com sucesso e <code>false</code> senao
	 * @throws IOException pode ocorre algum erro ao ler ou escrever o arquivo
	 * @see File
	 */
	public static boolean copiaArquivo(File origem, File destino) throws IOException {  
		if (destino.exists()){
			destino.delete();
		}

		FileChannel origemChannel = null;
		FileChannel destinoChannel = null;

		try {
			origemChannel = new FileInputStream(origem).getChannel();
			destinoChannel = new FileOutputStream(destino).getChannel();
			origemChannel.transferTo(0, origemChannel.size(), destinoChannel);
		}
		finally {
			if (origemChannel != null && origemChannel.isOpen()){
				origemChannel.close();
			}
			else{
				return false;
			}
			if (destinoChannel != null && destinoChannel.isOpen()){
				destinoChannel.close();
			}
			else {
				return false;
			}
		}
		return true;
	}

	/** Copia um arquivo para outro diretorio
	 * @param origemStream <code>File</code> com o arquivo de origem
	 * @param destino <code>File</code> com o arquivo de destino
	 * @return <code>boolean</code> com <code>true</code> se o arquivo foi copiado com sucesso e <code>false</code> senao
	 * @throws IOException pode ocorre algum erro ao ler ou escrever o arquivo
	 * @throws ClassCastException caso o <code>InputStream</code> não seja um <code>FileOutputStream</code>
	 * @see File
	 * @see InputStream
	 * @see FileOutputStream
	 */
	public static boolean copiaArquivo(InputStream origemStream, File destino) throws IOException, ClassCastException {
		try {
			return Arquivo.copiaArquivo(origemStream, new FileOutputStream(destino));
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** Copia um arquivo para outro diretorio 
	 * @param origemStream <code>InputStream</code> com o stream de entrada
	 * @param saidaStream <code>OutputStream</code> com o stream de saida
	 * @return <code>boolean</code> com <code>true</code> se o arquivo foi copiado com sucesso e <code>false</code> senao
	 * @see InputStream
	 * @see OutputStream
	 */
	public static boolean copiaArquivo(final InputStream origemStream, final OutputStream saidaStream){
		try {
			final int tamanhoBuffer = 1024;
			final byte[] buffer = new byte[tamanhoBuffer];
			int tamanho = 0;
			while ((tamanho = origemStream.read(buffer)) > 0) {
				saidaStream.write(buffer, 0, tamanho);
			}
			origemStream.close();
			saidaStream.close();
			return true;
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** Copia arquivos de um diretorio para outro
	 * @param origem <code>File</code> com o diretorio ou arquivo de origem
	 * @param diretorioDestino <code>File</code> com o diretorio de destino
	 * @return <code>boolean</code> com <code>true</code> se copiou corretamente e <code>false</code> senao
	 * @see File
	 * @throws IOException caso nao consiga ler ou escrever
	 */
	public static boolean copiaArquivosRecusivamente(final File origem, final File diretorioDestino) throws IOException {
		if (!origem.isDirectory()) {
			boolean copiou = false;
			copiou = Arquivo.copiaArquivo(origem, new File(diretorioDestino, origem.getName()));
			return copiou;
		}
		else {
			final File novoDiretorioDestino = new File(diretorioDestino, origem.getName());
			if (!novoDiretorioDestino.exists() && !novoDiretorioDestino.mkdir()) {
				return false;
			}
			for (final File arquivoFilho : origem.listFiles()) {
				if (!Arquivo.copiaArquivosRecusivamente(arquivoFilho, novoDiretorioDestino)) {
					return false;
				}
			}
		}
		return true;
	}

	/** Copia arquivo do jar para um diretorio
	 * @param diretorioDestino <code>File</code> com o diretorio de destino
	 * @param jarConnection <code>JarURLConnection</code> com a conexao url do jar
	 * @return <code>boolean</code> com <code>true</code> se copiou corretamente e <code>false</code> senao
	 * @see File
	 * @see JarURLConnection
	 * @throws IOException caso nao consiga ler ou escrever
	 */
	public static boolean copiaJarRecursosRecursivamante(final File diretorioDestino, final JarURLConnection jarConnection) throws IOException {

		final JarFile jarFile = jarConnection.getJarFile();

		for (final Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements();) {
			final JarEntry entry = e.nextElement();
			if (entry.getName().startsWith(jarConnection.getEntryName())) {
				final String filename = Arquivo.removeStart(entry.getName(), jarConnection.getEntryName());

				final File arquivo = new File(diretorioDestino, filename);
				if (!entry.isDirectory()) {
					final InputStream entryInputStream = jarFile.getInputStream(entry);
					if(!Arquivo.copiaArquivo(entryInputStream, arquivo)){
						return false;
					}
					entryInputStream.close();
				}
				else {
					if (!Arquivo.garanteDiretorioExiste(arquivo)) {
						throw new IOException("Diretorio nao foi criado: " + arquivo.getAbsolutePath());
					}
				}
			}
		}
		return true;
	}

	/** Copia recursos para um diretorio
	 * @param origemUrl <code>URL</code> com a url de destino
	 * @param destino <code>File</code> com o diretorio ou arquivo de destino
	 * @return <code>boolean</code> com <code>true</code> se copiou corretamente e <code>false</code> senao
	 * @see URL
	 * @see File
	 */
	public static boolean copiaRecursosRecursivamente(final URL origemUrl, final File destino) {
		try {
			final URLConnection urlConnection = origemUrl.openConnection();
			if (urlConnection instanceof JarURLConnection) {
				return Arquivo.copiaJarRecursosRecursivamante(destino, (JarURLConnection) urlConnection);
			}
			else {
				return Arquivo.copiaArquivosRecusivamente(new File(origemUrl.getPath()), destino);
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** Remove um arquivo
	 * @param enderecoArquivo <code>String</code> com o endereco do arquivo
	 * @return <code>boolean</code> com <code>true</code> se removeu corretamente e <code>false</code> senao
	 */
	public static boolean removeArquivo(String enderecoArquivo){
		File arquivo = new File(enderecoArquivo);
		if(arquivo.exists() && arquivo.isFile()){
			return arquivo.delete();
		}
		return false;
	}

	/** Garante que um diretorio existe, verifica se o diretorio existe e senao existe cria
	 * @param diretorio <code>File</code> com o diretorio
	 * @return <code>boolean</code> com <code>true</code> se o diretorio existe ou foi criado e <code>false</code> senao
	 * @see File
	 */
	private static boolean garanteDiretorioExiste(final File diretorio) {
		return (diretorio.exists() && diretorio.isDirectory()) || diretorio.mkdir();
	}

	/** Remove uma parte de uma string
	 * @param string <code>String</code> com a string
	 * @param parteRemovida <code>String</code> com a parte que sera removida do inicio da string
	 * @return <code>String</code> com a nova string
	 */
	private static String removeStart(String string, String parteRemovida) {
		if (string.isEmpty() || parteRemovida.isEmpty()) {
			return string;
		}
		if (string.startsWith(parteRemovida)){
			return string.substring(parteRemovida.length());
		}
		return string;
	}

}
