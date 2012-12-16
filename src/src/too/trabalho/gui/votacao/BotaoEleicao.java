package too.trabalho.gui.votacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import too.trabalho.classes.votacao.Votacao;

/** Classe com os atributos dos botoes da urna eletronica para a votacao.
 * @author Arthur Assunção
 * 
 * 
 * @see Votacao
 * @see JButton
 *
 */
//classe baseada em : http://www.jroller.com/DhilshukReddy/entry/customizing_jbuttons
public class BotaoEleicao extends JButton {
	/**@serial
	 * 
	 */
	private static final long serialVersionUID = 8325047424722406014L;
	
	private Cursor cursorMao = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	//Confirma = 156, 216, 180
	private Color corInicial = new Color(156, 216, 180); //cor do gradiente, cor inicial, cima
	private Color corFinal = new Color(174, 223, 194); //cor do gradiente, cor final, baixo
	private Color corRollOver = new Color(174, 223, 194); //cor ao passar o mouse por cima
	private Color corPressionado = new Color(120, 202, 152); //cor ao pressionar
	private Color corTexto = new Color(40, 40, 40); //quase preto
	private int outerRoundRectSize = 5; //arrendondamento da parte de baixo(cantos sudeste e sudoeste)
	private int innerRoundRectSize = 5; //arrendondamento da parte de cima(cantos nordeste e noroeste)
	private GradientPaint gradientPaint;
	
	/**<code>int</code> para representar o botao Confirmar */
	public static final int BOTAO_CONFIRMAR = 0;
	/**<code>int</code> para representar o botao Corrige */
	public static final int BOTAO_CORRIGE = 1;
	/**<code>int</code> para representar o botao Branco */
	public static final int BOTAO_BRANCO = 2;
	/**<code>int</code> para representar os botoes numericos */
	public static final int BOTAO_NUMERO = 3;
	
	/** Cria uma instancia do botao com texto e tipo especificado
	 * 
	 * @param textoBotao <code>String</code> com o texto do botao
	 * @param tipoBotao <code>int</code> com o tipo do botao
	 * 
	 * @see BotaoEleicao#BOTAO_CONFIRMAR
	 * @see BotaoEleicao#BOTAO_CORRIGE
	 * @see BotaoEleicao#BOTAO_BRANCO
	 * @see BotaoEleicao#BOTAO_NUMERO
	 */ 
	public BotaoEleicao(String textoBotao, int tipoBotao) { 
		super();
		String texto = textoBotao;
		int largura = 50;
		int altura = (largura / 2) + (largura / 5);
		int tamanhoFonte = 15;
		double proporcao = 1.5;
		int posicaoVertical = JButton.CENTER;
		int posicaoHorizontal = JButton.CENTER;
		
		setContentAreaFilled(false);
		setBorderPainted(false);
		setCursor(cursorMao);
		
		setFocusable(false);
		
		switch(tipoBotao){
			case BOTAO_CONFIRMAR:
				texto = "CONFIRMAR";
				
				/*corInicial = new Color(156, 216, 180);
				corFinal = new Color(174, 223, 194);
				corRollOver = new Color(174, 223, 194);
				corPressionado = new Color(120, 202, 152);*/
				
				altura = 33;
				altura = (int)(altura * proporcao);
				largura = (int)(largura * proporcao);
				tamanhoFonte = 7;
				
				posicaoVertical = JButton.NORTH;
				
				break;
			case BOTAO_CORRIGE:
				texto = "CORRIGE";
				
				corInicial = new Color(251, 161, 65); //cor
				corFinal = new Color(252, 173, 90); //cor menos luz
				corRollOver = new Color(252, 173, 90); //cor menos luz
				corPressionado = new Color(252, 137, 15); //cor mais escuro 2x
				
				
				altura = 25;
				altura = (int)(altura * proporcao);
				largura = (int)(largura * proporcao);
				tamanhoFonte = 8;
				
				posicaoVertical = JButton.NORTH;
				
				break;
			case BOTAO_BRANCO:
				texto = "BRANCO";
				
				corInicial = new Color(248, 248, 248); //cor
				corFinal = new Color(255, 255, 255); //cor menos luz
				corRollOver = new Color(255, 255, 255); //cor menos luz
				corPressionado = new Color(237, 237, 237); //cor mais escuro 2x
				
				altura = 25;
				altura = (int)(altura * proporcao);
				largura = (int)(largura * proporcao);
				tamanhoFonte = 10;
				
				posicaoVertical = JButton.NORTH;
				
				break;
			case BOTAO_NUMERO:
				texto = textoBotao;
				
				corInicial = new Color(101, 99, 110); //cor
				corFinal = new Color(113, 111, 123); //cor menos luz
				corRollOver = new Color(113, 111, 123); //cor menos luz
				corPressionado = new Color(89, 87, 97); //cor mais escuro
				
				corTexto = Color.WHITE;
				
				//largura = 30;
				//altura = 25;
				largura = (int)(30 * proporcao);
				altura = (int)(23 * proporcao);
				tamanhoFonte = 20;
				
				posicaoHorizontal = SwingConstants.LEFT;

				break;
		}
		
		setText(texto);
		setPreferredSize(new Dimension(largura , altura));
		
		setVerticalAlignment(posicaoVertical);
		setHorizontalAlignment(posicaoHorizontal);
		
		setFont(new Font("Arial", Font.BOLD, tamanhoFonte ));
		
		setForeground(corTexto);

	} 

	 
	/** Metodo para desenhar o botao
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create(); 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		int h = getHeight(); 
		int w = getWidth(); 
		ButtonModel model = getModel(); 
		if (!model.isEnabled()) { 
			setForeground(Color.GRAY); 
			gradientPaint = new GradientPaint(0, 0, new Color(192,192,192), 0, h, new Color(192,192,192), true); 
		}
		else{ 
			setForeground(corTexto); 
			if (model.isRollover()) { 
				gradientPaint = new GradientPaint(0, 0, corRollOver, 0, h, corRollOver, true); 
			}
			else { 
				gradientPaint = new GradientPaint(0, 0, corInicial, 0, h, corFinal, true); 
			} 
		} 
		g2d.setPaint(gradientPaint); 
		GradientPaint p1; 
		GradientPaint p2; 
		if (model.isPressed()) { 
			gradientPaint = new GradientPaint(0, 0, corPressionado, 0, h, corPressionado, true);
			g2d.setPaint(gradientPaint); 
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100)); 
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 100)); 
		}
		else { 
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0)); 
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50)); 
			gradientPaint = new GradientPaint(0, 0, corInicial, 0, h, corFinal, true); 
		} 
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, outerRoundRectSize, outerRoundRectSize); 
		Shape clip = g2d.getClip(); 
		g2d.clip(r2d); 
		g2d.fillRect(0, 0, w, h); 
		g2d.setClip(clip); 
		g2d.setPaint(p1); 
		g2d.drawRoundRect(0, 0, w - 1, h - 1, outerRoundRectSize, outerRoundRectSize); 
		g2d.setPaint(p2); 
		g2d.drawRoundRect(1, 1, w - 3, h - 3, innerRoundRectSize, innerRoundRectSize); 
		g2d.dispose(); 

		super.paintComponent(g); 
	}
} 