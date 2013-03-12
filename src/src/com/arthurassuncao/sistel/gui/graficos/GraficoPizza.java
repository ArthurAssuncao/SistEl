package com.arthurassuncao.sistel.gui.graficos;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;


/** Classe para manipular graficos de pizza 2D e 3D
 * @author Arthur Assun��o
 * 
 * @see Grafico
 */
public class GraficoPizza extends Grafico{

	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 947212839881193817L;
	private boolean graficoEm3D;

	/** Cria um grafico de pizza com os dados fornecidos
	 * @param titulo <code>String</code> com o titulo do grafico 
	 * @param listaNomesValores {@code List<NomeValor>} lista com os nomes e valores para o grafico
	 * @param legendasAdicionais <code>String...</code> com as legendas adicionais 
	 * @param graficoEm3D <code>boolean</code> com <code>true</code> se o grafico � em 3D ou n�o.
	 */
	public GraficoPizza(String titulo, List<NomeValor> listaNomesValores, boolean graficoEm3D, final String... legendasAdicionais){
		//Cria um dataSet para inserir os dados que ser�o passados para a cria��o do grafico tipo Pie
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		this.graficoEm3D = graficoEm3D;

		//Adiciona os dados ao dataSet deve somar um total de 100%
		double valorUmPorCentoVotos = this.valorUmVotoEmPorCento(listaNomesValores);
		String valor;
		for(NomeValor nomesValores : listaNomesValores){
			valor = String.format("%.2f%%", nomesValores.getValor() * valorUmPorCentoVotos);
			pieDataset.setValue(nomesValores.getNome() + "(" + valor  + ")", nomesValores.getValor());
		}

		//Cria um objeto JFreeChart passando os seguintes parametros
		if (!this.graficoEm3D){
			this.grafico = ChartFactory.createPieChart(
					titulo,	 //Titulo do grafico
					pieDataset, //DataSet
					true, //Para mostrar ou n�o a legenda
					true, //Para mostrar ou n�o os tooltips
					false
					);
		}
		else{ //em 3D
			this.grafico = ChartFactory.createPieChart3D(
					titulo,	 //Titulo do grafico
					pieDataset, //DataSet
					true, //Para mostrar ou n�o a legenda
					true, //Para mostrar ou n�o os tooltips
					false
					);
		}

		if (this.grafico != null){
			LegendTitle legenda = new LegendTitle(new LegendItemSource() {

				@Override
				public LegendItemCollection getLegendItems() {
					LegendItemCollection legenda = new LegendItemCollection();
					for (String texto: legendasAdicionais){
						legenda.add(new LegendItem(texto));
					}
					return legenda;
				}
			});
			float larguraBordaLegenda = 0.6F;
			legenda.setBorder(larguraBordaLegenda, larguraBordaLegenda, larguraBordaLegenda, larguraBordaLegenda);
			legenda.setPosition(RectangleEdge.BOTTOM);
			legenda.setHorizontalAlignment(HorizontalAlignment.LEFT);
			this.grafico.addLegend(legenda);
		}

	}

	//valor em porcentos de um votos
	/** Retorna o valor em porcentagem de uma unidade(voto). O resultado � obtido somando todos os valores e depois dividindo 100 por essa soma.
	 * @param listaNomesValores {@code List<NomeValor>} com os nomes e valores que ser�o adicionados ao grafico
	 * @return <code>double</code> com o valor em porcentagem de um voto
	 */
	public double valorUmVotoEmPorCento(List<NomeValor> listaNomesValores){
		double totalVotos = 0.0;
		for(NomeValor nomesValores : listaNomesValores){
			totalVotos += nomesValores.getValor();
		}
		return 100.0 / totalVotos; //pega o valor de um voto em porcentos
	}
}
