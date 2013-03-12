package com.arthurassuncao.sistel.gui.graficos;

import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.arthurassuncao.sistel.classes.Data;

/**
 * @author Arthur Assunção
 * 
 *
 */
public class GraficoXYLinhaSerie extends Grafico{
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 398884967154556977L;

	/** Cria um grafico de linha XY em serie com os dados fornecidos
	 * @param titulo <code>String</code> com o titulo do grafico
	 * @param datas {@code List<Data>} com as datas das pesquisas
	 * @param nomes {@code List<String>} com os nomes dos candidatos
	 * @param listaValores {@code List<ArrayList<Integer>>} lista de lista, cada linha representa um candidato, as colunas da linha representam os numeros de votos em cada pesquisa
	 */
	public GraficoXYLinhaSerie(String titulo, List<Data> datas, List<String> nomes, List<ArrayList<Integer>> listaValores){
		String nomeGrafico = titulo;

		XYSeriesCollection dataset = new XYSeriesCollection();
		//cria uma lista com os valores de um porcento, isso é necessario pq os graficos sao exibidos em porcentagem
		List<Double> listaUmPorcento = new ArrayList<Double>(); //armazena o valor de um porcento de todas pesquisas
		listaUmPorcento = this.valorUmVotoEmPorCento(listaValores);
		for(int i=0; i < nomes.size() && i < listaValores.size(); i++){
			dataset.addSeries(criaSerie(nomes.get(i), datas.size() ,listaValores.get(i), listaUmPorcento));
		}
		
		//Cria um objeto JFreeChart passando os seguintes parametros
		this.grafico = ChartFactory.createXYLineChart(nomeGrafico, "Periodo", "Numero Intenções de Votos", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		XYPlot plot = (XYPlot)this.grafico.getPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    //renderer.setSeriesLinesVisible(0, true); //serie em linha
        //renderer.setSeriesShapesVisible(0, false);
        //renderer.setSeriesPaint(0, Color.RED);
        //renderer.setBaseShapesVisible(false);
        //renderer.setBaseItemLabelsVisible(false);
        //renderer.setBaseLinesVisible(false);
	    
	    final int raioElipse = 4;
		
		renderer.setBaseStroke(new BasicStroke(3));
		renderer.setBaseShape(new Ellipse2D.Double(-raioElipse, -raioElipse, 2 * raioElipse, 2 * raioElipse));
		
		renderer.setAutoPopulateSeriesShape(false);
		renderer.setAutoPopulateSeriesStroke(false);
        
        plot.setRenderer(renderer);
		
	}
	
	/** Cria uma serie para adicionar ao grafico
	 * @param nome <code>String</code> com o nome do candidato
	 * @param colunas {@code List<Data>} com as datas
	 * @param valores {@code List<Integer>} com os numeros de votos em cada data(pesquisa)
	 * @param listaValorUmVotoEmPorcento {@code List<Double>} com o valor de um voto em porcetagem em cada pesquisa
	 */
	private XYSeries criaSerie(String nome, int numeroPontos, List<Integer> valores, List<Double> listaValorUmVotoEmPorcento){
		final XYSeries serie = new XYSeries(nome);
		for(int i=0; i < numeroPontos; i++){
			if (i < valores.size() && valores.get(i).intValue() >= 0){
				serie.add(i+1, valores.get(i).intValue() * listaValorUmVotoEmPorcento.get(i).doubleValue());
			}
			else{

			}
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
