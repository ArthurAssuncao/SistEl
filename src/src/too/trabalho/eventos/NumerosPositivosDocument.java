package too.trabalho.eventos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//Classe adaptada de http://www.guj.com.br/articles/29 - Controlando um JTextField

/** Classe para evitar que se digite caracteres não numericos nos campos de texto das janelas.
 * @author Arthur Assunção
 * 
 * 
 * @see PlainDocument
 *
 */
public class NumerosPositivosDocument extends PlainDocument {
	/**@serial
	 * 
	 */
	private static final long serialVersionUID = 2146891437940722764L;
	
    /** Cria um documento que so aceita caracteres numericos
     * 
     */
    public NumerosPositivosDocument(){
        super();
    }
    
    /** Insere um conteudo no documento. Se o conteudo não for numerico o texto não é adicionado
     * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
     */
    @Override
    public void insertString(int offset, String string, AttributeSet attr) throws BadLocationException {  
	    if (string == null){
	    	return;
	    }
	    if (string.matches("[0-9]+")){ // aceitaso numeros
	        super.insertString(offset, string, attr);
	        return;
	    }
    }
    
}
