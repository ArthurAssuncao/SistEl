package com.arthurassuncao.sistel.gui.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

import com.arthurassuncao.sistel.classes.Localidade;
import com.arthurassuncao.sistel.gui.Fonte;

/** Classe para criar os graficos
 * @author Arthur Assunção
 * 
 * 
 * @see JComponent
 * @see JFreeChart
 * @see ChartPanel
 */
public class Grafico extends JComponent{

	/**@serial
	 * 
	 */
	private static final long serialVersionUID = -7607700872589847535L;
	/** <code>JFreeChart</code> com o grafico*/
	protected JFreeChart grafico = null;
	private ChartPanel painelGrafico;
	private JMenu menuBarra;
	
	/** Cria um grafico vazio e painel com fundo cinza
	 * @see JFreeChart
	 * @see ChartPanel
	 * @see Color#LIGHT_GRAY
	 */
	public Grafico(){
		this.setLayout(new FlowLayout());
		this.painelGrafico = new ChartPanel(null);
		this.painelGrafico.setBackground(Color.LIGHT_GRAY);
		//this.painelGrafico.setChart(grafico);
		this.add(painelGrafico);
	}
	
	/** Cria um grafico com o grafico especificado e painel com fundo cinza
	 * @param grafico <code>JFreeChart</code> com o grafico
	 * @see JFreeChart
	 * @see ChartPanel
	 */
	public Grafico(JFreeChart grafico){
		this.setLayout(new FlowLayout());
		this.painelGrafico = new ChartPanel(null);
		this.painelGrafico.setChart(grafico);
		this.painelGrafico.setBackground(Color.LIGHT_GRAY);
		this.painelGrafico.setFillZoomRectangle(true);
		this.painelGrafico.setMouseWheelEnabled(true);
		this.painelGrafico.setDisplayToolTips(true);
		this.add(painelGrafico);
		this.applyChartTheme(grafico);
	}
	
	/** Adiciona itens ao menu popup dos graficos de barra
	 * @see JPopupMenu
	 */
	private void addItemBarraPopUpMenu(){
    	//Adiciona o item BARRA 3D ao grafico
    	JPopupMenu popupMenu = painelGrafico.getPopupMenu();
    	
    	if(popupMenu.getComponentIndex(menuBarra) != -1){
    		return; //ja tem adicionado
    	}
    	
    	menuBarra = new JMenu("Grafico de Barras");
    	JMenuItem itemBarra = new JMenuItem("Barras");
    	JMenuItem itemBarra3D = new JMenuItem("Barras 3D");
    	
    	menuBarra.add(itemBarra);
    	menuBarra.add(itemBarra3D);
    	
    	//evento
    	itemBarra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evento) {
				graficoBarra();
				modificaGrafico();
			}
		});
    	itemBarra3D.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evento) {
				graficoBarra3D();
				modificaGrafico();
			}
		});
    	
    	popupMenu.add(menuBarra);
	}
	
	/** Remove os itens do menu popup dos graficos de barra
	 * @see JPopupMenu
	 */
	public void removeItemBarraPopUpMenu(){
		JPopupMenu popupMenu = painelGrafico.getPopupMenu();
		int posicaoMenuBarra;
		posicaoMenuBarra = popupMenu.getComponentIndex(menuBarra);
		if(posicaoMenuBarra != -1){
			popupMenu.remove(posicaoMenuBarra);
		}
	}
	
	/** Cria um grafico de barra
	 * @see ChartFactory
	 */
	private void graficoBarra(){
		if(this.grafico.getPlot() instanceof CategoryPlot && ((CategoryPlot)this.grafico.getPlot()).getRenderer() instanceof BarRenderer3D ){
			String nomeGrafico = this.grafico.getTitle().getText();
			CategoryPlot plot = (CategoryPlot)this.grafico.getPlot();
			CategoryAxis3D domainAxis = (CategoryAxis3D)plot.getDomainAxis();
			NumberAxis3D valueAxis = (NumberAxis3D)plot.getRangeAxis();
			CategoryDataset dataset = plot.getDataset();
			
			this.grafico = ChartFactory.createBarChart(
					nomeGrafico,	 //Titulo do grafico
					domainAxis.getLabel(), //Coluna
					valueAxis.getLabel(), //Linha
					dataset, //dataset
					PlotOrientation.HORIZONTAL, //orientacao
					false, //Para mostrar ou não a legenda
					true, //Para mostrar ou não os tooltips
					false
				);
		}
	}
	
	/** Cria um grafico de barra 3D
	 * @see ChartFactory
	 */
	private void graficoBarra3D(){
		if(this.grafico.getPlot() instanceof CategoryPlot && ((CategoryPlot)this.grafico.getPlot()).getRenderer() instanceof BarRenderer ){
			String nomeGrafico = this.grafico.getTitle().getText();
			CategoryPlot plot = (CategoryPlot)this.grafico.getPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			NumberAxis valueAxis = (NumberAxis)plot.getRangeAxis();
			CategoryDataset dataset = plot.getDataset();
			
			this.grafico = ChartFactory.createBarChart3D(
					nomeGrafico,	 //Titulo do grafico
					domainAxis.getLabel(), //Coluna
					valueAxis.getLabel(), //Linha
					dataset, //dataset
					PlotOrientation.HORIZONTAL, //orientacao
					false, //Para mostrar ou não a legenda
					true, //Para mostrar ou não os tooltips
					false
				);
		}
	}
	
	/** Aplica o novo grafico ao painel
	 * @param grafico <code>JFreeChart</code> com o grafico que aparecera no painel
	 */
	private void applyChartTheme(JFreeChart grafico) {
        final StandardChartTheme chartTheme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
        
        Font extraLargeFont = Fonte.FONTE_TITULO;
        Font largeFont = new Fonte(Fonte.TAMANHO_TITULO - 3).getFont();
        Font regularFont = Fonte.FONTE_NORMAL;
        Font smallFont = new Fonte(Fonte.TAMANHO_TITULO - 3).getFont();
        
        chartTheme.setExtraLargeFont(extraLargeFont);
        chartTheme.setLargeFont(largeFont);
        chartTheme.setRegularFont(regularFont);
        chartTheme.setSmallFont(smallFont);

        chartTheme.apply(grafico);
    }
	
	/** Muda o grafico
	 * @param grafico <code>Grafico</code> com o grafico
	 */
	public void setGrafico(Grafico grafico){
		this.grafico = grafico.grafico;
		this.modificaGrafico();
	}
	
	/** Muda o grafico
	 * 
	 */
	private void modificaGrafico(){
		this.painelGrafico.setChart(this.grafico);
		this.applyChartTheme(this.grafico);
		
		if(this.grafico.getPlot() instanceof CategoryPlot && ((CategoryPlot)this.grafico.getPlot()).getRenderer() instanceof BarRenderer ){
			this.addItemBarraPopUpMenu();
		}
		else{
			this.removeItemBarraPopUpMenu();
		}

		//muda a posicao da legenda
		LegendTitle legenda = this.grafico.getLegend();
		if(legenda != null){
			legenda.setPosition(RectangleEdge.BOTTOM);
		}
		
		Plot plotGrafico = this.grafico.getPlot();

		//coloca o fundo branco
		//this.grafico.setBackgroundPaint(Color.WHITE);
		if(plotGrafico instanceof CategoryPlot){
			CategoryPlot plot = (CategoryPlot)plotGrafico;
	        plot.setBackgroundPaint(Color.WHITE); //fundo do grafico
	        plot.setDomainGridlinePaint(Color.WHITE);
	        plot.setRangeGridlinePaint(Color.DARK_GRAY); //linhas horizontais
	        
	        //plot.addRangeMarker(new ValueMarker(10.0));
	        
	        //coloca elipse em todas as serie e aumenta a espessura delas
	        if(plot.getRenderer() instanceof LineAndShapeRenderer){
	        	final int raioElipse = 4;
	        	final int larguraLinhas = 3;
	        	LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
		        renderer.setBaseStroke(new BasicStroke(larguraLinhas)); //aumenta a largura das series
				renderer.setBaseShape(new Ellipse2D.Double(-raioElipse, -raioElipse, 2 * raioElipse, 2 * raioElipse)); //deixa todas as series com bolinhas
				
				renderer.setBaseSeriesVisible(true); //deixa todas as series visiveis

				//mostra valor acima da bolinha
				renderer.setBaseItemLabelsVisible(true);
	            //renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}%", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(Localidade.LOCAL_PT_BR)));
				renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("0")));
				
				renderer.setAutoPopulateSeriesShape(false);
				renderer.setAutoPopulateSeriesStroke(false);
				
				//coloca toolTip nos itens da legenda
				renderer.setLegendItemToolTipGenerator(
					    new CategorySeriesLabelGenerator() {
							@Override
							public String generateLabel(CategoryDataset dataset, int series) {
								return dataset.getRowKey(series).toString();
							}
						});
				//coloca toolTip nos shapes(bolinhas das linhas dos graficos)
				renderer.setBaseToolTipGenerator(new CategoryToolTipGenerator() {
					@Override
					public String generateToolTip(CategoryDataset dataset, int row, int column) {
						return String.format("%.2f%%", dataset.getValue(row, column));
					}
				});
				CategoryAxis domainAxis = plot.getDomainAxis();
	
				//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0));
	
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	
				//plot.setBackgroundPaint(Color.lightGray);
	
				plot.setOutlinePaint(Color.BLACK);
	
				//plot.setRangeGridlinePaint(Color.white);
	
				NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
				//numberAxis.setVisible(false);
	
				numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				numberAxis.setAutoRangeIncludesZero(true); //inclui zero no grafico
				numberAxis.setUpperMargin(0.10); //margem superior
				
				//deixa o grafico com linhas horizontas de 10 em 10
				TickUnits standardUnits = new TickUnits();
			    standardUnits.add(new NumberTickUnit(10));
			    numberAxis.setStandardTickUnits(standardUnits); 
			}
	        else if(plot.getRenderer() instanceof BarRenderer){
	        	BarRenderer renderer = (BarRenderer)plot.getRenderer();
	        	renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}%", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(Localidade.LOCAL_PT_BR)));
	        	renderer.setBaseItemLabelsVisible(true);
	        	
	        	//muda cor barra
	        	Color corGraficoBarra = new Color(102 ,153, 102); //verde
	        	renderer.setBasePaint(corGraficoBarra);
	        	renderer.setSeriesPaint(0, corGraficoBarra); //so tem uma serie
	        	renderer.setAutoPopulateSeriesPaint(false);
	        	
	        	//muda largura barra
	        	BasicStroke largura = new BasicStroke(5);
	        	renderer.setBaseStroke(largura); //aumenta a largura das series
	        	renderer.setSeriesStroke(0, largura);
	        	renderer.setAutoPopulateSeriesStroke(false);
	        }
		}
		else if(plotGrafico instanceof XYPlot){
			XYPlot plot = (XYPlot)plotGrafico;
	        plot.setBackgroundPaint(Color.WHITE);
	        plot.setRangeGridlinePaint(Color.DARK_GRAY);
	        
	        //ValueAxis domainAxis = plot.getDomainAxis();
			
	        //coloca elipse em todas as serie e aumenta a espessura delas
	        if(plot.getRenderer() instanceof LineAndShapeRenderer){
	        	final int raioElipse = 4;
	        	XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
		        renderer.setBaseStroke(new BasicStroke(3));
				renderer.setBaseShape(new Ellipse2D.Double(-raioElipse, -raioElipse, 2 * raioElipse, 2 * raioElipse));
				
				renderer.setAutoPopulateSeriesShape(false);
				renderer.setAutoPopulateSeriesStroke(false);
	        }
	        else  if(plot.getRenderer() instanceof XYLineAndShapeRenderer){
	        	final int raioElipse = 4;
	        	XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
		        renderer.setBaseStroke(new BasicStroke(3));
				renderer.setBaseShape(new Ellipse2D.Double(-raioElipse, -raioElipse, 2 * raioElipse, 2 * raioElipse));
				
				renderer.setAutoPopulateSeriesShape(false);
				renderer.setAutoPopulateSeriesStroke(false);

				//renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator("{2}", new DecimalFormat("0.00"), NumberFormat.getPercentInstance(Localidade.LOCAL_PT_BR)));
				renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator("{2}", new DecimalFormat("0"), new DecimalFormat("0")));
	        	renderer.setBaseItemLabelsVisible(true);
	        	
	        }
		}
		else{
			//Plot plot = this.grafico.getPlot();
			plotGrafico.setBackgroundPaint(Color.WHITE);
		}
        
	}
	
	/** Remove o grafico do painel
	 * 
	 */
	public void removeGrafico(){
		this.painelGrafico.setChart(null);
		this.removeItemBarraPopUpMenu();
	}
	
	/** Classe para manipular os nomes e valores para exibicao nos graficos
	 * @author Arthur Assunção
	 * 
	 * @see Grafico
	 */
	public static class NomeValor{
		private String nome;
		private int valor;
		
		/** Cria uma instancia com nome e valor especifico
		 * @param nome <code>String</code> com o nome
		 * @param valor <code>int</code> com o valor
		 * 
		 */
		public NomeValor(String nome, int valor) {
			this.nome = nome;
			this.valor = valor;
		}

		/** Retorna um <code>String</code> com o nome
		 * @return um <code>String</code> com o nome
		 */
		public String getNome() {
			return nome;
		}

		/** Retorna um <code>int</code> com o valor
		 * @return um <code>int</code> com o valor
		 */
		public int getValor() {
			return valor;
		}
	}
	
	/** Enumeracao com os dados dos graficos disponiveis
	 * @author Arthur Assunção
	 * 
	 * @see Grafico
	 */
	public enum Graficos{
		/** Grafico de pizza, grafico nao seriado */
		PIZZA("Pizza", false),
		/** Grafico de pizza 3d, grafico nao seriado */
		PIZZA_3D("Pizza em 3D", false),
		/** Grafico de linha, grafico seriado */
		LINHA_SERIE("Linha Serie", true),
		/** Grafico de linha 3d, grafico seriado */
		LINHA_SERIE_3D("Linha Serie 3D", true),
		/** Grafico de linha sobre coordenadas XY, grafico seriado */
		LINHA_SERIE_XY("Linha Serie XY", true),
		/** Grafico de tempo, grafico seriado */
		TEMPO_SERIE("Tempo Serie", true),
		/** Grafico de barra, grafico nao seriado */
		BARRA("Barra", false),
		/** Grafico de barra 3d, grafico nao seriado */
		BARRA_3D("Barra em 3D", false),
		;
		
		private String nome;
		private boolean emSerie;
		
		private Graficos(String nome, boolean emSerie){
			this.nome = nome;
			this.emSerie = emSerie;
		}
		
		/** Retorna um <code>String</code> com o nome do grafico
		 * @return um <code>String</code> com o nome do grafico
		 */
		public String getNome(){
			return this.nome;
		}
		
		/** Retorna um <code>boolean</code> com <code>true</code> se o grafico é em serie e <code>false</code> senão.
		 * @return um <code>boolean</code> com <code>true</code> se o grafico é em serie e <code>false</code> senão.
		 */
		public boolean isEmSerie(){
			return this.emSerie;
		}
	}
	
}
