package com.arthurassuncao.sistel.gui.graficos;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.arthurassuncao.sistel.classes.Data;

/** Classe para manipular graficos de linha em serie
 * @author Arthur Assunção
 * 
 * @see Grafico
 */
public class GraficoLinhaSerie extends Grafico{
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 3914066276102322118L;
	private boolean graficoEm3D;
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
	/** Cria um grafico de linha em serie com os dados fornecidos
	 * @param titulo <code>String</code> com o titulo do grafico
	 * @param datas {@code List<Data>} com as datas das pesquisas
	 * @param nomes {@code List<String>} com os nomes dos candidatos
	 * @param listaValores {@code List<ArrayList<Integer>>} lista de lista, cada linha representa um candidato, as colunas da linha representam os numeros de votos em cada pesquisa
	 * @param graficoEm3D <code>boolean</code> com <code>true</code> se o grafico é seriado(com varias pesquisas) ou não.
	 */
	public GraficoLinhaSerie(String titulo, List<Data> datas, List<String> nomes, List<ArrayList<Integer>> listaValores, boolean graficoEm3D){
		String nomeGrafico = titulo;
		this.graficoEm3D = graficoEm3D;
		
		//cria uma lista com os valores de um porcento, isso é necessario pq os graficos sao exibidos em porcentagem
		List<Double> listaUmPorcento = new ArrayList<Double>(); //armazena o valor de um porcento de todas pesquisas
		listaUmPorcento = this.valorUmVotoEmPorCento(listaValores);
		
		for(int i=0; i < nomes.size() && i < listaValores.size(); i++){
			this.addValor(nomes.get(i), datas, listaValores.get(i), listaUmPorcento);
		}
		
		//Cria um objeto JFreeChart passando os seguintes parametros
		if (!this.graficoEm3D){
			this.grafico = ChartFactory.createLineChart(nomeGrafico, "Periodo", "Numero Intenções de Votos", dataset, PlotOrientation.VERTICAL, true, true, false);
	        
			CategoryPlot plot = (CategoryPlot)this.grafico.getPlot();
			LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
			/*final int raioElipse = 4;
			
			renderer.setBaseStroke(new BasicStroke(3));
			renderer.setBaseShape(new Ellipse2D.Double(-raioElipse, -raioElipse, 2 * raioElipse, 2 * raioElipse));
			
			renderer.setAutoPopulateSeriesShape(false);
			renderer.setAutoPopulateSeriesStroke(false);*/
			
			plot.setRenderer(renderer);
		}
		else{
			this.grafico = ChartFactory.createLineChart3D(nomeGrafico, "Periodo", "Numero Intenções de Votos", dataset, PlotOrientation.VERTICAL, true, true, false);
		}
	}
	
	/** Adiciona series ao grafico
	 * @param nome <code>String</code> com o nome do candidato
	 * @param colunas {@code List<Data>} com as datas
	 * @param valores {@code List<Integer>} com os numeros de votos em cada data(pesquisa)
	 * @param listaValorUmVotoEmPorcento {@code List<Double>} com o valor de um voto em porcetagem em cada pesquisa
	 */
	private void addValor(String nome, List<Data> colunas, List<Integer> valores, List<Double> listaValorUmVotoEmPorcento){
		for(int i=0; i < valores.size() && i < colunas.size(); i++){
			if(valores.get(i).intValue() >= 0){
				//this.dataset.addValue(valores.get(i) * listaUmPorcento.get(i).doubleValue(), nome, colunas.get(i).getDataDDMMAAAA());
				this.dataset.addValue(valores.get(i) * listaValorUmVotoEmPorcento.get(i).doubleValue(), nome, colunas.get(i).getDataMesAAAA());
			}
		}
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
