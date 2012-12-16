package too.trabalho.gui.graficos;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/** Classe para manipular graficos de barra 2D e 3D
 * @author Arthur Assunção
 * 
 * @see Grafico
 */
public class GraficoBarra extends Grafico {
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = 3712365710619810169L;
	private boolean graficoEm3D;
	//private Color corGrafico = new Color(102 ,153, 102); //verde
	
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	
	/** Cria um grafico de barra com os dados fornecidos
	 * @param titulo <code>String</code> com o titulo do grafico 
	 * @param listaNomesValores {@code List<NomeValor>} lista com os nomes e valores para o grafico
	 * @param graficoEm3D <code>boolean</code> com <code>true</code> se o grafico é em 3D ou não.
	 */
	public GraficoBarra(String titulo, List<NomeValor> listaNomesValores, boolean graficoEm3D){
		
		String coluna = "coluna";
		String linha = "linha";
		int numeroEleitores = 0;
		
		this.graficoEm3D = graficoEm3D;
		
		numeroEleitores = this.getNumeroEleitores(listaNomesValores);
		double valorUmPorCentoVotos = this.valorUmVotoEmPorCento(listaNomesValores);
		
		for(NomeValor nomesValores : listaNomesValores){
			dataset.setValue(nomesValores.getValor() * valorUmPorCentoVotos, "Votacao", nomesValores.getNome());
		}
		
		if (!this.graficoEm3D){
			this.grafico = ChartFactory.createBarChart(
				titulo,	 //Titulo do grafico
				coluna, //Coluna
				linha, //Linha
				this.dataset, //dataset
				PlotOrientation.HORIZONTAL, //orientacao
				false, //Para mostrar ou não a legenda
				true, //Para mostrar ou não os tooltips
				false
			);
		}
		else{ //em 3D
			this.grafico = ChartFactory.createBarChart3D(
					titulo,	 //Titulo do grafico
					coluna, //Coluna
					linha, //Linha
					this.dataset, //dataset
					PlotOrientation.HORIZONTAL, //orientacao
					false, //Para mostrar ou não a legenda
					true, //Para mostrar ou não os tooltips
					false
				);
		}
		
		/*CategoryPlot plot = this.grafico.getCategoryPlot();
		
		BarRenderer renderer = (BarRenderer)plot.getRenderer();
    	renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}%", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(Localidade.LOCAL_PT_BR)));
    	renderer.setBaseItemLabelsVisible(true);
    	
    	
    	//muda cor barra
    	renderer.setBasePaint(this.corGrafico);
    	renderer.setSeriesPaint(0, this.corGrafico); //so tem uma serie
    	renderer.setAutoPopulateSeriesPaint(false);
    	
    	//muda largura barra
    	BasicStroke largura = new BasicStroke(5);
    	renderer.setBaseStroke(largura); //aumenta a largura das series
    	renderer.setSeriesStroke(0, largura);
    	renderer.setAutoPopulateSeriesStroke(false);*/
		
	}
	
	//valor em porcentos de um votos
	/** Retorna o valor em porcentagem de uma unidade(voto). O resultado é obtido somando todos os valores e depois dividindo 100 por essa soma.
	 * @param listaNomesValores {@code List<NomeValor>} com os nomes e valores que serão adicionados ao grafico
	 * @return <code>double</code> com o valor em porcentagem de um voto
	 */
	public double valorUmVotoEmPorCento(List<NomeValor> listaNomesValores){
		double totalVotos = 0.0;
		for(NomeValor nomesValores : listaNomesValores){
			totalVotos += nomesValores.getValor();
		}
		return 100.0 / totalVotos; //pega o valor de um voto em porcentos
	}
	
	//pega o numero total de eleitores
	/** Retorna o numero total de eleitores, sao conside
	 * @param listaNomesValores {@code List<NomeValor>} com os nomes e valores que serão adicionados ao grafico
	 * @return <code>int</code> com o numero total de eleitores
	 */
	public int getNumeroEleitores(List<NomeValor> listaNomesValores){
		int totalEleitores = 0;
		for(NomeValor nomesValores : listaNomesValores){
			totalEleitores += nomesValores.getValor();
		}
		return totalEleitores; //pega o valor de um voto em porcentos
	}

	/**
	 * @return um <code>boolean</code> com o graficoEm3D
	 */
	public boolean isGraficoEm3D() {
		return graficoEm3D;
	}
	
}
