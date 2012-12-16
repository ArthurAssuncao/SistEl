package too.trabalho.classes;

import java.util.Calendar;
import java.util.Date;

/** A classe <code>Data</code> manipula datas.
 * 
 * @author Arthur Assunção
 * 
 *
 * @see Comparable
 *
 */

public final class Data implements Comparable<Data>{
	
	/** <code>int</code> para representar o campo Hora */
	public static final int HORA 							= Calendar.HOUR;
	/** <code>int</code> para representar o campo Dia da semana */
	public static final int DIA_DA_SEMANA 			= Calendar.DAY_OF_WEEK;
	/** <code>int</code> para representar o campo Dia */
	public static final int DIA 							= Calendar.DAY_OF_MONTH;
	/** <code>int</code> para representar o campo Mes */
	public static final int MES 							= Calendar.MONTH;
	/** <code>int</code> para representar o campo Ano */
	public static final int ANO 							= Calendar.YEAR;
	private Calendar data;
	private int anoMinimo = Calendar.getInstance(Localidade.LOCAL_PT_BR).getMinimum(ANO);
	
	
	/** Cria uma <code>Data</code> com os dados atuais e com a localidade Português do Brasil
	 * 
	 * @see Calendar
	 * @see Localidade
	 * @see Date
	 */
	public Data(){
		this.data = Calendar.getInstance(Localidade.LOCAL_PT_BR);
		this.data.setTime(new Date()); //agora
	}
	
	
	/** Cria uma <code>Data</code> com os dados definidos por <code>date</code> e com a localidade Português do Brasil
	 * 
	 * @param date <code>Date</code> com a data
	 * 
	 * @see Calendar
	 * @see Localidade
	 * @see Date
	 */
	public Data(Date date){
		this.data = Calendar.getInstance(Localidade.LOCAL_PT_BR);
		this.data.setTime(date); //agora
	}
	
	/** Cria uma <code>Data</code> com os dados definidos por <code>data</code> e com a localidade Português do Brasil
	 * 
	 * @param data <code>String</code> com a data no formato dd/mm/aaaa
	 * 
	 * @see Calendar
	 * @see Localidade
	 */
	public Data(String data){ //dd/mm/aaaa
		int[] vetorData = divideData(data);
		this.data = Calendar.getInstance(Localidade.LOCAL_PT_BR);
		if(vetorData != null){
			this.data.set(vetorData[2], vetorData[1] - 1, vetorData[0]);
		}
		else{
			this.data.setTime(new Date()); //agora
		}
	}
	
	/** Cria uma <code>Data</code> com os dados definidos por <code>dia</code>, <code>mes</code>, <code>ano</code> e com a localidade Português do Brasil
	 * 
	 * @param dia <code>int</code> com o dia 
	 * @param mes <code>int</code> com o mes
	 * @param ano <code>int</code> com o ano
	 * 
	 * @see Calendar
	 * @see Localidade
	 */
	public Data(int dia, int mes, int ano){ //dd/mm/aaaa
		this.data = Calendar.getInstance(Localidade.LOCAL_PT_BR);
		this.data.set(ano, mes - 1, dia);
	}
	
	/** Obtem a data no formato <code>dd/mm/aaaa</code>
	 * 
	 * @return <code>String</code> com a data
	 */
	public String getDataDDMMAAAA(){
		return (this.data.get(DIA) < 10 ? "0" : "")  + this.data.get(DIA) + "/" + (this.data.get(MES)+1 < 10 ? "0" : "") + (this.data.get(MES) + 1) + "/" + this.data.get(ANO);
	}
	
	/** Obtem a data no formato <code>mes/aaaa</code>, mes sendo os 3 primeiros caracteres do nome do mes
	 * 
	 * @return <code>String</code> com a data
	 */
	public String getDataMesAAAA(){
		String mesTresPrimeirasLetras = "";
		int mes = this.data.get(MES)+1;
		switch(mes){
			case 1: mesTresPrimeirasLetras = "jan"; break;
			case 2: mesTresPrimeirasLetras = "fev"; break;
			case 3: mesTresPrimeirasLetras = "mar"; break;
			case 4: mesTresPrimeirasLetras = "abr"; break;
			case 5: mesTresPrimeirasLetras = "mai"; break;
			case 6: mesTresPrimeirasLetras = "jun"; break;
			case 7: mesTresPrimeirasLetras = "jul"; break;
			case 8: mesTresPrimeirasLetras = "ago"; break;
			case 9: mesTresPrimeirasLetras = "set"; break;
			case 10: mesTresPrimeirasLetras = "out"; break;
			case 11: mesTresPrimeirasLetras = "nov"; break;
			case 12: mesTresPrimeirasLetras = "dez"; break;
		}
		return mesTresPrimeirasLetras + "/" + this.data.get(ANO);
	}
	
	/** Obtem a data no formato <code>aaaa/mm/dd</code>
	 * @param separador <code>String</code> que separa os campos, ano, dia e mes
	 * 
	 * @return <code>String</code> com a data no formato <code>aaaa/mm/dd</code>
	 */
	public String getDataAAAAMMDD(String separador){
		return this.data.get(ANO) + separador + (this.data.get(MES)+1 < 10 ? "0" : "") + (this.data.get(MES) + 1) + separador + (this.data.get(DIA) < 10 ? "0" : "")  + this.data.get(DIA);
	}
	
	/** Define a data
	 * @param date <code>Date</code> com a data
	 * @see Date
	 */
	public void setDate(Date date){
		if (date != null){
			this.data.setTime(date);
		}
	}
	
	/** Define a data
	 * @param data <code>String</code> com a data no formato dd/mm/aaaa
	 */
	public void setData(String data){ //dd/mm/aaaa
		int[] vetorData = divideData(data);
		if(vetorData != null){
			this.data.set(vetorData[2], vetorData[1] - 1, vetorData[0]);
		}
		else{
			this.data.setTime(new Date()); //agora
		}
	}
	
	/** Define o dia
	 * @param dia <code>int</code> com o dia
	 */
	public void setDia(int dia){
		if (dia >= data.getActualMinimum(DIA) && dia <= data.getActualMaximum(DIA)){
			this.data.set(DIA, dia);
		}
	}
	
	
	/** Obtem o dia
	 * @return <code>int</code> com o dia
	 */
	public int getDia(){
		return Integer.parseInt(this.getDataDDMMAAAA().substring(0, 2));
	}
	
	/** Define o mes
	 * @param mes <code>int</code> com o mes
	 */
	public void setMes(int mes){ //mes comeca em 1 e vai ate 12
		if (mes > data.getActualMinimum(MES) && mes <= data.getActualMaximum(MES) + 1){
			this.data.set(MES, mes - 1);
		}
	}
	
	/** Obtem o mes
	 * @return <code>int</code> com o mes
	 */
	public int getMes(){
		return Integer.parseInt(this.getDataDDMMAAAA().substring(3, 5));
	}
	
	/** Define o ano
	 * @param ano <code>int</code> com o ano
	 */
	public void setAno(int ano){
		if (ano >= this.anoMinimo && ano <= data.getActualMaximum(ANO)){
			this.data.set(ANO, ano);
		}
	}
	
	/** Obtem o ano
	 * @return <code>int</code> com o ano
	 */
	public int getAno(){
		return Integer.parseInt(this.getDataDDMMAAAA().substring(6, 10));
	}
	
	/** Define o ano minimo
	 * @param ano <code>int</code> com o ano minimo
	 */
	public void setAnoMinimo(int ano){
		if (ano >= data.getMinimum(ANO) && ano <= data.getMaximum(ANO)){
			this.anoMinimo = ano;
		}
	}
	
	/** Obtem a data
	 * @return <code>Date</code> com a data
	 * @see Date
	 */
	public Date getDate(){
		return this.data.getTime();
	}
	
	/** Define o valor para um campo especifico
	 * @param campo <code>int</code> representando o campo
	 * @param valor <code>int</code> com o valor para campo
	 */
	public void add(int campo, int valor){
		this.data.add(campo, valor);
	}

	/** Divide a data, no formato dd/mm/aaaa, em 3 partes, dia, mes e ano
	 * 
	 * @param data <code>String</code> com a data a ser dividida
	 * @return <code>int[]</code> com o dia, mes e ano da <code>data</code>
	 */
	public static int[] divideData(String data){
		int[] dataDividida = null;
		if(data.indexOf("/") == 2 && data.lastIndexOf("/") == 5){
			dataDividida = new int[3];
			dataDividida[0] = Integer.parseInt(data.substring(0, 2));
			dataDividida[1] = Integer.parseInt(data.substring(3, 5));
			dataDividida[2] = Integer.parseInt(data.substring(6, 10));
		}
		return dataDividida;
	}
	
	/** Converte a data no formato aaaa/mm/dd para o formato dd/mm/aaaa
	 * 
	 * @param data <code>String</code> com a data no formato aaaa/mm/dd
	 * @return <code>String</code> com a data no formato dd/mm/aaaa
	 */
	public static String AAAAMMDDtoDDMMAAAA(String data){
		String[] dataDividida = new String[3];
		if( (data.charAt(4) == '/' || data.charAt(4) == '-' ) && (data.charAt(7) == '/' || data.charAt(7) == '-' )){
			dataDividida[0] = data.substring(0, 4); //ANO
			dataDividida[1] = data.substring(5, 7); //MES
			dataDividida[2] = data.substring(8, 10); //DIA
		}
		return dataDividida[2] + "/" + dataDividida[1] + "/" + dataDividida[0]; 
	}

	/** Compara duas datas
	 * @param data <code>Data</code> com a data a ser comparada com esta(<code>this</code>)
	 * @return <code>int</code> com 0 se as datas forem iguais, -1 se a primeira data(<code>this</code>) for maior que a data passada como parametro 
	 * ou 1 se a data(<code>this</code>) for maior que a data passada como parametro.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Data data) {
		// TODO Auto-generated method stub
		String estaDataDDMMAAAA = this.getDataDDMMAAAA();
		String outraDataDDMMAAAA = data.getDataDDMMAAAA();
		Integer estaData 	= Integer.parseInt(estaDataDDMMAAAA.substring(6, 10) + estaDataDDMMAAAA.substring(3, 5) + estaDataDDMMAAAA.substring(0, 2));
		Integer outraData 	= Integer.parseInt(outraDataDDMMAAAA.substring(6, 10) + outraDataDDMMAAAA.substring(3, 5) + outraDataDDMMAAAA.substring(0, 2));
		return estaData.compareTo(outraData);
	}


	/** @return Retorna um objeto <code>String</code> representando a data no formato dd/mm/aaaa.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDataDDMMAAAA();
	}
	
	
}
