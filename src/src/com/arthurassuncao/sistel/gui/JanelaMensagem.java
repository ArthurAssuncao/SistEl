package com.arthurassuncao.sistel.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

/** Classe com metodos estaticos para exibi��o de mensagens para o usu�rio
 * @author Arthur Assun��o
 * 
 * 
 * @see JOptionPane
 *
 */
public abstract class JanelaMensagem extends JOptionPane {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -5822788201526100076L;

	/** Janela para mostrar uma mensagem para o usuario
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param mensagem <code>String</code> com a mensagem para o usuario
	 */
	public static void mostraMensagem(Component componentePai, String titulo, String mensagem){
		JOptionPane.showMessageDialog(componentePai, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/** Janela para mostrar uma mensagem de erro para o usuario
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param mensagem <code>String</code> com a mensagem de erro para o usuario
	 */
	public static void mostraMensagemErro(Component componentePai, String mensagem){
		JOptionPane.showMessageDialog(componentePai, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	/** Janela para mostrar uma mensagem de erro de banco de dados para o usuario
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param mensagem <code>String</code> com a mensagem de erro de banco de dados para o usuario
	 */
	public static void mostraMensagemErroBD(Component componentePai, String mensagem){
		JOptionPane.showMessageDialog(componentePai, mensagem, "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
	}
	
	/** Janela para mostrar uma mensagem de advertencia para o usuario
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param mensagem <code>String</code> com a mensagem de advertencia para o usuario
	 */
	public static void mostraMensagemWarning(Component componentePai, String mensagem){
		JOptionPane.showMessageDialog(componentePai, mensagem, "Aten��o", JOptionPane.WARNING_MESSAGE);
	}
	
	/** Janela de confirma��o com uma mensagem de advertencia para o usuario. Usada para quando se quer perguntar ao usuario
	 * sobre a a��o a se tomar
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param mensagem <code>String</code> com a mensagem de advertencia para o usuario
	 * @return <code>boolean</code> com a op��o escolhida pelo usuario, caso o usuario escolha sim, entao retornar� <code>true</code>, do contrario retornar� <code>false</code>
	 */
	public static boolean mostraMensagemConfirma(Component componentePai, String titulo, String mensagem){
		int opcao;
		opcao = JOptionPane.showConfirmDialog(componentePai, mensagem, titulo, JOptionPane.YES_OPTION);
		return opcao == 0 ? true : false;
	}
	
	/** Janela de confirma��o com uma mensagem de advertencia para o usuario. Usada para quando se quer perguntar ao usuario
	 * sobre a a��o a se tomar e est� a��o deve ser tomada com cuidado.
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param mensagem <code>String</code> com a mensagem de advertencia para o usuario
	 * @return <code>boolean</code> com a op��o escolhida pelo usuario, caso o usuario escolha sim, entao retornar� <code>true</code>, do contrario retornar� <code>false</code>
	 */
	public static boolean mostraMensagemConfirmaWarning(Component componentePai, String mensagem){
		int opcao;
		opcao = JOptionPane.showConfirmDialog(componentePai, mensagem, "Aten��o", JOptionPane.YES_OPTION);
		return opcao == 0 ? true : false;
	}
	
	/** Janela de confirma��o com uma mensagem de advertencia para o usuario. Usada para quando se quer perguntar ao usuario
	 * sobre a a��o a se tomar e est� a��o deve ser tomada com cuidado.
	 * @param componentePai <code>Component</code> sobre o qual esta janela ser� aberta, a qual esta sera "filha"
	 * @param titulo <code>String</code> com o titulo da janela
	 * @param mensagem <code>String</code> com a mensagem de advertencia para o usuario
	 * @return <code>boolean</code> com a op��o escolhida pelo usuario, caso o usuario escolha sim, entao retornar� <code>true</code>, do contrario retornar� <code>false</code>
	 */
	public static boolean mostraMensagemConfirmaWarning(Component componentePai, String titulo, String mensagem){
		int opcao;
		opcao = JOptionPane.showConfirmDialog(componentePai, mensagem, titulo, JOptionPane.YES_OPTION);
		return opcao == 0 ? true : false;
	}
	
}
