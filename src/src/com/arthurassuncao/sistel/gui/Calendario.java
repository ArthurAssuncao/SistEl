package com.arthurassuncao.sistel.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;

import org.jdesktop.swingx.JXDatePicker;

import com.alee.extended.date.WebDateField;
import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.Localidade;

/** Classe com Calendarios (Data pickers) para melhor exibicao e interacao do usuario com campos de data, alem de fornecer metodos que facilitam 
 * a manipulacao de datas
 * @author Arthur Assunção
 * 
 * 
 * @see JComponent
 */
public class Calendario extends JComponent{
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -4432396322152886159L;
	
	/** <code>int</code> representa o data picker do swingx, veja em {@link "http://swingx.java.net"} */
	public static final int CALENDARIO_JXDATEPICKER = 0;
	/** <code>int</code> representa o data picker de Juanheyns, veja em {@link "http://sourceforge.net/projects/jdatepicker/"} */
	public static final int CALENDARIO_JDATEPICKER = 1;
	/** <code>int</code> representa o data picker deo WebLookAndFeel, veja em {@link "http://weblookandfeel.com"} */
	public static final int CALENDARIO_WEBDATEFIELD = 2;
	
	private int tipoCalendario; 
	private Data data = new Data();
	private JComponent datePicker;
	private final int NUMERO_COLUNAS = 10;
	
	/** Cria um componente calendario do tipo especificado pelo parametro
	 * @param tipoCalendario <code>int</code> representando o tipo do calendario (data picker)
	 * @see Calendario#CALENDARIO_JXDATEPICKER
	 * @see Calendario#CALENDARIO_JDATEPICKER
	 */
	public Calendario(int tipoCalendario){
		super();
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.tipoCalendario = tipoCalendario;
		if (this.tipoCalendario == Calendario.CALENDARIO_JDATEPICKER){
			UtilCalendarModel modelo = new UtilCalendarModel(Calendar.getInstance(Localidade.LOCAL_PT_BR));
			JDatePanelImpl painel = new JDatePanelImpl(modelo);
			painel.setBackground(Color.WHITE);
			painel.setShowYearButtons(true);
			
			this.datePicker = new JDatePickerImpl(painel);
			((JDatePickerImpl)this.datePicker).setBackground(Color.WHITE);
			//((JDatePickerImpl)this.datePicker).getJFormattedTextField().setPreferredSize(TAMANHO);
			((JDatePickerImpl)this.datePicker).getJFormattedTextField().setColumns(NUMERO_COLUNAS);
			
			modelo.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent evento) {
					data.setData(((JDatePickerImpl)datePicker).getJFormattedTextField().getText());
				}
			});
			
		}
		else if(this.tipoCalendario == Calendario.CALENDARIO_JXDATEPICKER){
			this.datePicker = new JXDatePicker(Localidade.LOCAL_PT_BR);
			((JXDatePicker)this.datePicker).setFormats(new String [] { "dd/MM/yyyy" });
			((JXDatePicker)this.datePicker).getEditor().setEditable(false);
			((JXDatePicker)this.datePicker).getEditor().setColumns(NUMERO_COLUNAS);
			
			((JXDatePicker)this.datePicker).getEditor().addCaretListener(new CaretListener() {
				@Override
				public void caretUpdate(CaretEvent evento) {
					data.setData((((JXDatePicker)datePicker).getEditor().getText()));
				}
			});
			
		}
		else if(this.tipoCalendario == Calendario.CALENDARIO_WEBDATEFIELD){
			this.datePicker = new WebDateField();
			((WebDateField)this.datePicker).setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
			((WebDateField)this.datePicker).setLocale(Localidade.LOCAL_PT_BR);
			JTextField campoTexto = (JTextField)((WebDateField)this.datePicker).getComponent(0); //pega o campo de texto
			campoTexto.setEditable(false);
			campoTexto.setColumns(NUMERO_COLUNAS);
			campoTexto.addCaretListener(new CaretListener() {
				@Override
				public void caretUpdate(CaretEvent evento) {
					WebDateField dataPicker = ((WebDateField)datePicker);
					JTextField campoTexto = (JTextField)dataPicker.getComponent(0);
					data.setData(campoTexto.getText());
				}
			});
		}
		if (this.datePicker != null){
			this.add(datePicker);
		}
	}
	
	/** Retorna um <code>String</code> com a data selecionada, a data no formato dd/mm/aaaa
	 * @return <code>String</code> com a data no formato dd/mm/aaaa
	 */
	public String getData(){
		if (this.datePicker instanceof JDatePickerImpl){
			this.data.setDia(((JDatePickerImpl)this.datePicker).getModel().getDay());
			this.data.setMes(((JDatePickerImpl)this.datePicker).getModel().getMonth()+1);
			this.data.setAno(((JDatePickerImpl)this.datePicker).getModel().getYear());
		}
		else if(this.datePicker instanceof JXDatePicker){
			if (((JXDatePicker)this.datePicker).getEditor().getText() != null){
				data.setData(((JXDatePicker)this.datePicker).getEditor().getText());
			}
		}
		else if(this.datePicker instanceof WebDateField){
			if (((WebDateField)this.datePicker).getDate() != null){
				Date novaData = ((WebDateField)this.datePicker).getDate();
				if(novaData != null){
					data.setData(new Data(novaData).getDataDDMMAAAA() );
				}
				else{
					data.setData(new Data(new Date()).getDataDDMMAAAA() ); //data atual
				}
			}
		}
		return data.getDataDDMMAAAA();
	}
	
	/** Retorna um objeto Date com a data selecionada
	 * @return <code>Date</code> com a data que foi selecionada
	 * @see Date
	 */
	public Date getDate(){
		Date date = null;
		if (this.datePicker instanceof JDatePickerImpl){
			date = this.data.getDate();
		}
		else if(this.datePicker instanceof JXDatePicker){
			date = ((JXDatePicker)this.datePicker).getDate();
		}
		else if(this.datePicker instanceof WebDateField){
			date = ((WebDateField)this.datePicker).getDate();
		}
		return date;
	}
	
	/** Seta a data do calendario
	 * @param data <code>String</code> com a nova data, se a data for nula limpa o campo ou seta a data atual, dependendo do JDataPicker
	 */
	public void setData(String data){
		if (this.datePicker instanceof JDatePickerImpl){
			((JDatePickerImpl)this.datePicker).getJFormattedTextField().setText(data);
		}
		else if(this.datePicker instanceof JXDatePicker){
			((JXDatePicker)this.datePicker).getEditor().setText(data);
		}
		else if(this.datePicker instanceof WebDateField){
			if(data != null){ //nao aceita data nula
				((WebDateField)this.datePicker).setDate(new Data(data).getDate());
			}
			else{
				((WebDateField)this.datePicker).setDate(new Data(new Date()).getDate()); //data atual
			}
		}
	}
	
	/** Seta a data do calendario, caso o objeto <code>Date</code> seja <code>null</code> é setada a data atual
	 * @param date <code>Date</code> com a nova data
	 */
	public void setDate(Date date){
		this.data.setDate(date);
		if (date != null){
			this.setData(this.data.getDataDDMMAAAA());
		}
		else{
			this.setData("");
		}
	}
	
	/** Retorna o texto do componente de texto do calendario, o texto é retornado no formato dd/mm/aaaa
	 * @return <code>String</code> com o texto do campo de texto
	 */
	public String getText(){
		String texto = "";
		if (this.datePicker instanceof JDatePickerImpl){
			texto = ((JDatePickerImpl)this.datePicker).getJFormattedTextField().getText();
		}
		else if(this.datePicker instanceof JXDatePicker){
			texto = ((JXDatePicker)this.datePicker).getEditor().getText();
		}
		else if(this.datePicker instanceof WebDateField){
			texto = new Data(((WebDateField)this.datePicker).getDate()).getDataDDMMAAAA();
		}
		return texto;
	}
	
	
	/** Seta o toolTipText do calendario
	 * @see javax.swing.JComponent#setToolTipText(java.lang.String)
	 * @param mensagem <code>String</code> com a mensagem do toolTipText
	 */
	@Override
	public void setToolTipText(String mensagem){
		this.datePicker.setToolTipText(mensagem);
	}
	
}

