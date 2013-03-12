package com.arthurassuncao.sistel.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/** Classe para manipulação de imagens
 * @author Arthur Assunção
 * 
 *
 */
public abstract class Imagem {

	/** Copia uma imagem para o diretorio de destino informado com o novo nome especificado, se o novo nome for <code>null</code> o nome não é modificado.
	 * Se o endereco da imagem for invalido, <code>null</code> é retornado. Se o diretorio de destino nao existir, o diretorio é criado.
	 * @param enderecoImagem <code>String</code> com o endereco da imagem, isso inclui o nome e extensao da imagem
	 * @param novoNome <code>String</code> novo nome para a imagem que sera copiada
	 * @param diretorioDestino <code>String</code> com o diretorio para onde a imagem será copiada
	 * @return <code>String</code> com o novo endereco da imagem
	 * @throws IOException pode ocorrer algum erro de IO caso não aja permissão para criar o diretorio ou a nova imagem ou acessar a imagem
	 * @see File
	 * @see BufferedImage
	 * @see ImageIO
	 */
	public static String copiaImagemParaPastaImagensCandidatos(String enderecoImagem, String novoNome, String diretorioDestino) throws IOException{
		File diretorioImagens = new File(diretorioDestino);
		String nomeImagem = enderecoImagem.substring(enderecoImagem.lastIndexOf("/")+1);

		String extensao = nomeImagem.substring(nomeImagem.lastIndexOf(".")+1);
		String novoEnderecoImagem;
		if (!diretorioImagens.exists()){
			diretorioImagens.mkdir();
		}
		File imagemOrigem = new File(enderecoImagem);
		if(!imagemOrigem.exists()){
			return null;
		}
		BufferedImage bufferImagem = ImageIO.read(imagemOrigem);
		if(novoNome != null){
			novoEnderecoImagem = diretorioImagens.getPath() + "/" + novoNome + "." + extensao.toLowerCase();
		}
		else{
			novoEnderecoImagem = diretorioImagens.getPath() + "/" + nomeImagem + "." + extensao.toLowerCase();
		}
		//novoEnderecoImagem = diretorioImagens.getPath() + "/" + this.substituiEspacoNomeCandidato('_').toLowerCase() + "-" + this.numeroCandidato + "." + extensao.toLowerCase();
		File imagemDestino = new File(novoEnderecoImagem);
				
		if(ImageIO.write(bufferImagem, extensao.toUpperCase(), imagemDestino)){
			//imagem criada
		}
		else{
			//imagem nao criada
		}
		return novoEnderecoImagem;
	}
	
	/** Redimensiona uma imagem com a largura e altura especificados
	 * @param enderecoImagem <code>String</code> com o endereco da imagem, isso inclui o nome e extensao da imagem
	 * @param novaLargura <code>int</code> com a largura que a imagem deve ter após ser redimensionada
	 * @param novaAltura <code>int</code> com a altura que a imagem deve ter após ser redimensionada
	 * @return <code>boolean</code> com <code>true</code> se a imagem foi redimensionada e <code>false</code> senão. 
	 * @throws IOException pode ocorrer erro ao ler a imagem a ser redimensionada ou escrever a imagem redimensionada
	 * @see BufferedImage
	 * @see File
	 * @see Graphics2D
	 * @see ImageIO
	 */
	public static boolean redimensionaImagem(String enderecoImagem, int novaLargura, int novaAltura) throws IOException{
		boolean redimensionou = true;
		File imagemOrigem = new File(enderecoImagem);
		if(imagemOrigem.exists()){
			String nomeImagem = imagemOrigem.getPath().substring(imagemOrigem.getPath().lastIndexOf("/")+1); //pega o nome+extensao da imagem
			String extensao = nomeImagem.substring(nomeImagem.lastIndexOf(".")+1).toUpperCase();
			final String EXTENSAO_BACKUP = ".bkp";
			File ImagemBackup = new File(imagemOrigem.getPath() + EXTENSAO_BACKUP);
			
			BufferedImage bufferImagem = ImageIO.read(imagemOrigem); //le os bits da imagem
			
			imagemOrigem.renameTo(ImagemBackup); //renomeia o arquivo, mas nao muda o endereco que esta em imagemOrigem
	        
	        BufferedImage novaImagem = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = novaImagem.createGraphics();
	        g.drawImage(bufferImagem, 0, 0, novaLargura, novaAltura, null);
	        redimensionou = ImageIO.write(novaImagem, extensao, new File(enderecoImagem));  //salva a imagem redimensionada para o mesmo diretorio
	        
	        ImagemBackup.delete();
		}
		else{
			redimensionou = false;
		}
        return redimensionou;
	}
	
	/** Converte uma imagem para uma imagem em escala de cinza
	 * @param enderecoImagem <code>String</code> com o endereco da imagem, isso inclui o nome e extensao da imagem
	 * @return um <code>ImageIcon</code> com a imagem em escala de cinza
	 * @throws IOException pode ocorrer erro ao ler a imagem
	 * @see File
	 * @see BufferedImage
	 * @see Graphics2D
	 * @see ImageIcon
	 */
	public static ImageIcon imagemToEscalaCinza(String enderecoImagem) throws IOException{
		int largura;
		int altura;
		
		if(!new File(enderecoImagem).exists()){
			return null;
		}
		ImageIcon imagem = new ImageIcon(enderecoImagem);
		
		largura = imagem.getIconWidth();
		altura = imagem.getIconHeight();
		
		File imagemOrigem = new File(enderecoImagem);
		BufferedImage bufferImagem = ImageIO.read(imagemOrigem); //le os bits da imagem
        
        BufferedImage novaImagem = new BufferedImage(largura, altura, BufferedImage.TYPE_USHORT_GRAY);
        Graphics2D g = novaImagem.createGraphics();
        g.drawImage(bufferImagem, 0, 0, largura, altura, null);
        
        ImageIcon imagemEscalaCinza = new ImageIcon(novaImagem);
        
        return imagemEscalaCinza;
	}
	
	/** Converte uma imagem para uma imagem em escala de cinza
	 * @param enderecoImagem <code>URL</code> com o endereco da imagem, isso inclui o nome e extensao da imagem
	 * @param streamImagem <code>InputStream</code> com o stream da imagem
	 * @return um <code>ImageIcon</code> com a imagem em escala de cinza
	 * @throws IOException pode ocorrer erro ao ler a imagem
	 * @throws NullPointerException caso o endereco ou o stream da imagem seja <code>null</code>
	 * @see InputStream
	 * @see URL
	 * @see BufferedImage
	 * @see Graphics2D
	 * @see ImageIcon
	 */
	public static ImageIcon imagemToEscalaCinza(URL enderecoImagem, InputStream streamImagem) throws IOException, NullPointerException{
		int largura;
		int altura;
		
		ImageIcon imagem = new ImageIcon(enderecoImagem);
		
		largura = imagem.getIconWidth();
		altura = imagem.getIconHeight();
		
		BufferedImage bufferImagem = ImageIO.read(streamImagem); //le os bits da imagem
        
        BufferedImage novaImagem = new BufferedImage(largura, altura, BufferedImage.TYPE_USHORT_GRAY);
        Graphics2D g = novaImagem.createGraphics();
        g.drawImage(bufferImagem, 0, 0, largura, altura, null);
        
        ImageIcon imagemEscalaCinza = new ImageIcon(novaImagem);
        
        return imagemEscalaCinza;
	}
}
