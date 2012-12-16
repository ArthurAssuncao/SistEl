package too.trabalho.gui.graficos;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import too.trabalho.classes.Data;
import too.trabalho.gui.JanelaMensagem;

/**
 * @author Arthur Assunção
 * 
 *
 */
public class GraficoTempoSerie extends Grafico {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -3803066189157082414L;

	/** Cria um grafico de tempo em serie com os dados fornecidos
	 * @param titulo <code>String</code> com o titulo do grafico
	 * @param datas {@code List<Data>} com as datas das pesquisas
	 * @param nomes {@code List<String>} com os nomes dos candidatos
	 * @param listaValores {@code List<ArrayList<Integer>>} lista de lista, cada linha representa um candidato, as colunas da linha representam os numeros de votos em cada pesquisa
	 */
	public GraficoTempoSerie(String titulo, List<Data> datas, List<String> nomes, List<ArrayList<Integer>> listaValores){
		String nomeGrafico = titulo;

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		//cria uma lista com os valores de um porcento, isso é necessario pq os graficos sao exibidos em porcentagem
		List<Double> listaUmPorcento = new ArrayList<Double>(); //armazena o valor de um porcento de todas pesquisas
		listaUmPorcento = this.valorUmVotoEmPorCento(listaValores);
		for(int i=0; i < nomes.size() && i < listaValores.size(); i++){
			dataset.addSeries(criaSerie(nomes.get(i), datas, listaValores.get(i), listaUmPorcento));
		}
		
		//Cria um objeto JFreeChart passando os seguintes parametros
		this.grafico = ChartFactory.createTimeSeriesChart(nomeGrafico, "Periodo", "Numero Intenções de Votos", dataset, true, true, false);
		
		this.grafico.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot)this.grafico.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));	
	}
	/** Cria series para adicionar ao grafico
	 * @param nome <code>String</code> com o nome do candidato
	 * @param colunas {@code List<Data>} com as datas
	 * @param valores {@code List<Integer>} com os numeros de votos em cada data(pesquisa)
	 * @param listaValorUmVotoEmPorcento {@code List<Double>} com o valor de um voto em porcetagem em cada pesquisa
	 */
	private TimeSeries criaSerie(String nome, List<Data> datas, List<Integer> valores, List<Double> listaValorUmVotoEmPorcento){
		TimeSeries serie = new TimeSeries(nome);
		int i = 0;
		for(Data data : datas){
			try{
				if(i < valores.size()){
					if(valores.get(i).intValue() >= 0){
						serie.add(new Month(data.getMes(), data.getAno()), valores.get(i).intValue() * listaValorUmVotoEmPorcento.get(i).doubleValue());
					}
				}
				else{
					//caso o valor nao esteja nessa pesquisa
				}
			}
			catch(SeriesException e){
				e.printStackTrace();
				JanelaMensagem.mostraMensagemWarning(null, "A data " + data.getDataDDMMAAAA() + " não foi adicionada, pois há uma data com mesmo mes/ano ja utilizada no grafico\n");
			}
			i++;
		}
		return serie;
	}
	
	//valor em porcentos de um votos
	/** Retorna uma lista com o valor em porcentagem de uma unidade(voto) em cada pesquisa. O resultado é obtido somando todos os valores de uma pesquisa e depois dividindo 100 por cada soma.
	 * @param listaValores {@code List<ArrayList<Integer>>} com o numero de votos dos candidatos em cada pesquisa
	 * @return {@code List<Double>} com o valor em porcentagem de um voto em cada uma das pesquisas
	 */
	public List<Double> valorUmVotoEmPorCento(List<ArrayList<Integer>> listaValores){
		List<Double> listaUmPorcento = new ArrayList<Double>();
		double[] totalVotos = new double[listaValores.get(0).size()];
		for(int i = 0; i < totalVotos.length; i++){
			totalVotos[i] = 0;
		}
		for(int i=0; i < listaValores.get(0).size(); i++){
			for(int j = 0; j < listaValores.size(); j++){
				totalVotos[i] += listaValores.get(j).get(i);
			}
		}
		for(int i = 0; i < totalVotos.length; i++){
			listaUmPorcento.add(100 / totalVotos[i]);
		}
		return listaUmPorcento;
	}
}
