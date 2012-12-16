package too.trabalho.gui.pesquisa;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import too.trabalho.eventos.NumerosPositivosDocument;

/** Editor para os campos numericos da tabela de <code>JanelaCadastrarPesquisa</code>
 * @author Arthur Assunção
 * 
 * @see AbstractCellEditor
 * @see TableCellEditor
 */
public class EditorTabelaColunaNumerica extends AbstractCellEditor implements TableCellEditor {
    /** @serial
	 * 
	 */
	private static final long serialVersionUID = -2943468626259030152L;
	//O componente que será o editor
    JComponent componente = new JTextField();
    
    /** Cria uma instancia do editor
	 * 
	 */
    public EditorTabelaColunaNumerica(){
    	JTextField editor = ((JTextField)componente);
    	editor.setDocument(new NumerosPositivosDocument());
    	
    	componente.addMouseListener(new MouseAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent evento) {
				// TODO Auto-generated method stub
				super.mousePressed(evento);
				JTextField editor = ((JTextField)evento.getSource());
				
				if(editor.getText().equalsIgnoreCase("0")){
					editor.setText("");
				}
			}
		});
    }

    //Esse metodo é chamado quando o valor de uma celula é editado pelo usuario
    /** Metodo chamada quando o usuario edita uma celula
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    @Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
    	// value é o valor contido na celula localizada em (rowIndex, vColIndex)

        if (isSelected) {
        	//celula (e talvez outras celulas) estao selecionadas
        }

        // configura o componente com o valor especificado por value
        ((JTextField)componente).setText((String)value);

        //retorna o componente configurado
        return componente;
    }

    //Esse metodo é chamado quando a edição da celula é completada
    // Deve retornar o novo valor para a celula
    /** Metodo é chamado apos editar a celula, ele retorna o novo valor da celula
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    @Override
	public Object getCellEditorValue() {
    	if(((JTextField)componente).getText().equalsIgnoreCase("")){
    		((JTextField)componente).setText("0");
    	}
        return ((JTextField)componente).getText();
    }
}
