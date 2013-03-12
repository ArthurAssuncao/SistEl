package com.arthurassuncao.sistel.eventos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//Classe adaptada de http://www.guj.com.br/articles/29 - Controlando um JTextField

/** Classe para limitar o numero de digitos nos campos de texto das janelas.
 * @author Arthur Assunção
 * 
 * 
 * @see PlainDocument
 */
public class FixedLengthDocument extends PlainDocument {
	/**@serial
	 * 
	 */
	private static final long serialVersionUID = 2146891437940722764L;
	private int maxlength;
	
    /** Cria uma instancia do documento com um limite caracteres 
     * @param maxlength <code>int</code> com o maximo de caracteres do campo de texto
     */
    public FixedLengthDocument(int maxlength){
        super();
        this.maxlength = maxlength;
    }

    /** Insere um conteudo no documento, esse conteudo deve ser inferior ao <code>maxlength</code>, caso seja maior so será adicionado ate o limite de caracteres
     * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
     */
    @Override
    public void insertString(int offset, String string, AttributeSet attr) throws BadLocationException {  
	    if (string == null){
	    	return;
	    }

	    if (this.maxlength <= 0){ // aceitara qualquer numero de caracteres
	        super.insertString(offset, string, attr);
	        return;
	    }
	    
	    int ilen = (getLength() + string.length());
	    if (ilen <= this.maxlength){    // se o comprimento final for menor...
	        super.insertString(offset, string, attr);   // ...aceita string
	    }
	    else{
	        if (getLength() == this.maxlength){ //chegou ao tamanho maximo, nao pode inserir
	        	return; // nada a fazer
	        }
	        if (this.maxlength - getLength() >= 0){ //vai inserir o que der para inserir
	        	String newString = string.substring(0, (this.maxlength - getLength()));
	        	super.insertString(offset, newString, attr);
	        }
	        else{ //passou por algum motivo, entao remove o excesso
	        	super.remove(this.maxlength, (getLength() - this.maxlength));
	        }
	    }
    }
    
    /** Seta o maximo de caracteres
     * @param maxlength <code>int</code> com o maximo de caracteres do documento
     */
    public void setMaxLength(int maxlength){
    	this.maxlength = maxlength;
    }
    
    /** Retorna o maximo de caracteres
     * @return um <code>int</code> com o maximo de caracteres do documento
     */
    public int getMaxLength(){
    	return this.maxlength;
    }
    
    //caso tenha mais caracteres que o maximo, retira o excesso 
    /** Caso o documento tenha mais caracters que o limite, retirasse os caracteres que ultrapassaram esse limite
     * 
     */
    public void removeOverage(){
    	if (this.maxlength - getLength() < 0){
    		try{
    			super.remove(this.maxlength, (getLength() - this.maxlength));
    		}
    		catch(BadLocationException e){ //nunca chegara aqui, entao nao ha problemas em "pegar"
    			e.printStackTrace();
    		}
    	}
    }
    
}
