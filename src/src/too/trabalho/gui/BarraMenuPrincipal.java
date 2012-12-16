package too.trabalho.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.hsqldb.lib.tar.TarMalformatException;

import too.trabalho.classes.Data;
import too.trabalho.classes.votacao.Votacao;
import too.trabalho.gui.apuracao.JanelaConsultaVotacao;
import too.trabalho.gui.candidato.JanelaCadastrarCandidato;
import too.trabalho.gui.candidato.JanelaPesquisarCandidato;
import too.trabalho.gui.cargo.JanelaCadastrarCargo;
import too.trabalho.gui.cargo.JanelaExcluirCargo;
import too.trabalho.gui.partido.JanelaCadastrarPartido;
import too.trabalho.gui.partido.JanelaExcluirPartido;
import too.trabalho.gui.pesquisa.JanelaCadastrarPesquisa;
import too.trabalho.gui.pesquisa.JanelaRelatorioPesquisa;
import too.trabalho.gui.temas.Temas;
import too.trabalho.gui.votacao.JanelaNovaEleicao;
import too.trabalho.gui.votacao.JanelaVotacao;
import too.trabalho.persistencia.BDEleicoes;

/** Barra de menu principal do sistema, usada na <code>JanelaPrincipal</code>.
 * Todos os modulos(candidato, cargo, partido, pesquisa, votacao, apuracao, configuracao e sobre) são acessados por esta barra, bem como seus sub-modulos.
 * @author Arthur Assunção
 * 
 * 
 * @see JMenuBar
 */
public class BarraMenuPrincipal extends JMenuBar{
	
	/** @serial
	 * 
	 */
	private static final long serialVersionUID = -4210512756440036945L;
	private static final String ITEM_ARQUIVO 	= "Arquivo";
	private static final String ITEM_CANDIDATO 	= "Candidato";
	private static final String ITEM_PESQUISA 	= "Pesquisa";
	private static final String ITEM_PARTIDO 	= "Partido";
	private static final String ITEM_CARGO 		= "Cargo";
	private static final String ITEM_VOTACAO 	= "Votação";
	private static final String ITEM_APURACAO 	= "Apuração";
	private static final String ITEM_TEMA 	= "Tema";
	private static final String ITEM_CONFIGURACOES = "Configurações";
	private static final String ITEM_SOBRE = "Sobre";
	
	//Janela onde esta essa barra
	Component janela;
	
	//cria os objetos
	private JMenu arquivo;
	private JMenu candidato;
	private JMenu partido;
	private JMenu cargo;
	private JMenu pesquisa;
	private JMenu votacao;
	private JMenu apuracao;
	private JMenu tema;
	private JMenu configuracoes;
	private JMenu sobre;
	
	private JMenuItem arquivoAbrirSQL;
	private JMenu arquivoBackupBancoDeDados; //item com submenus
	private JMenuItem arquivoBackupImportar;
	private JMenuItem arquivoBackupExportar;
	private JMenuItem arquivoPopularBancoDeDados;
	private JMenuItem arquivoLimparBancoDeDados;
	private JMenuItem arquivoSair;
	
	private JMenuItem cadastrarCandidato;
	private JMenuItem consultarCandidato;
	
	private JMenuItem cadastrarPesquisa;
	private JMenuItem relatorioPesquisa;
	
	private JMenuItem cadastrarPartido;
	private JMenuItem excluirPartido;
	
	private JMenuItem cadastrarCargo;
	private JMenuItem excluirCargo;
	
	private JMenuItem iniciarEleicao;
	private JMenuItem novaEleicao;
	
	private JMenuItem consultaApuracao;
	
	private JMenuItem preferenciasConfiguracoes;
	
	private JMenuItem sobreAutor;
	
	/** Cria uma instancia da barra de menu
	 * @param janela <code>Component</code> com a janela onde a barra foi adicionada.
	 */
	public BarraMenuPrincipal(Component janela){
		super();
		
		this.janela = janela;
		
		this.iniciaElementos(null);
		
		this.addElementos();

		this.addEventos();
		
		this.setBackground(Color.WHITE);
		this.setLocation(0,0);
		
		this.setSize(100, 50);
		
		this.setVisible(true);
		
	}//fim contrutor Menu
	
	/** Cria uma instancia da barra de menu
	 * @param janela <code>Component</code> com a janela onde a barra foi adicionada.
	 * @param temaAtual <code>String</code> com o tema atual
	 */
	public BarraMenuPrincipal(Component janela, String temaAtual){
		super();
		
		this.janela = janela;
		
		this.iniciaElementos(temaAtual);
		
		this.addElementos();

		this.addEventos();
		
		this.setBackground(Color.WHITE);
		this.setLocation(0,0);
		
		this.setSize(100, 50);
		
		this.setVisible(true);
		
	}//fim contrutor Menu
	
	/** Instancia os elementos da barra de menu, alem de setar algumas propriedades de alguns componentes
	 * @see JMenuBar
	 * @see JMenu
	 * @see JMenuItem
	 */
	private void iniciaElementos(String temaAtual){
		// JMenu
		arquivo 			= new JMenu(ITEM_ARQUIVO);
		candidato 		= new JMenu(ITEM_CANDIDATO);
		partido 			= new JMenu(ITEM_PARTIDO);
		cargo 			= new JMenu(ITEM_CARGO);		
		pesquisa 		= new JMenu(ITEM_PESQUISA);
		votacao			= new JMenu(ITEM_VOTACAO);
		apuracao		= new JMenu(ITEM_APURACAO);
		tema 			= new Temas(ITEM_TEMA, temaAtual).getMenu();
		configuracoes = new JMenu(ITEM_CONFIGURACOES);
		sobre 			= new JMenu(ITEM_SOBRE);
		
		//Adiciona mnemonicos
		arquivo.setMnemonic('a');
		candidato.setMnemonic('c');
		partido.setMnemonic('t');
		cargo.setMnemonic('g');		
		pesquisa.setMnemonic('p');
		votacao.setMnemonic('v');
		apuracao.setMnemonic('r');
		configuracoes.setMnemonic('f');
		sobre.setMnemonic('s');
		
		//JMenuItem submenus
		//Arquivo
		arquivoAbrirSQL = new JMenuItem("Abrir arquivo SQL");
		arquivoBackupBancoDeDados = new JMenu("Backup do Banco de Dados");
		arquivoPopularBancoDeDados = new JMenuItem("Popular Banco de Dados");
		arquivoLimparBancoDeDados = new JMenuItem("Limpar Banco de Dados");
		arquivoSair = new JMenuItem("Sair");
		
		//Arquivo > Backup
		arquivoBackupImportar = new JMenuItem("Importar");
		arquivoBackupExportar = new JMenuItem("Exportar");
		
		//Candidato
		cadastrarCandidato = new JMenuItem("Cadastrar");
		consultarCandidato = new JMenuItem("Consultar");
		
		//Partido
		cadastrarPartido = new JMenuItem("Cadastrar");
		excluirPartido = new JMenuItem("Excluir");
		
		//Cargo
		cadastrarCargo = new JMenuItem("Cadastrar");
		excluirCargo = new JMenuItem("Excluir");		
		
		//Pesquisa
		cadastrarPesquisa = new JMenuItem("Cadastrar");
		relatorioPesquisa = new JMenuItem("Relatório");
		
		//Votação
		iniciarEleicao = new JMenuItem("Iniciar Votação");
		novaEleicao = new JMenuItem("Nova Eleição");
		
		//Apuracao
		consultaApuracao = new JMenuItem("Consulta Votacao");
		
		//Configuracoes
		preferenciasConfiguracoes = new JMenuItem("Preferências");
		
		//Sobre
		sobreAutor = new JMenuItem("Autor");
		

	}
	
	/** Adiciona os menus e sub-menus à barra de menu
	 * 
	 */
	private void addElementos(){
		//JMenuItem submenus
		
		//Arquivo
		arquivo.add(arquivoAbrirSQL);
		arquivo.add(arquivoBackupBancoDeDados);
		arquivo.add(arquivoPopularBancoDeDados);
		arquivo.add(arquivoLimparBancoDeDados);
		arquivo.add(arquivoSair);
		//Arquvio > Backup
		arquivoBackupBancoDeDados.add(arquivoBackupImportar);
		arquivoBackupBancoDeDados.add(arquivoBackupExportar);
		
		//Candidato
		candidato.add(cadastrarCandidato);
		
		candidato.add(consultarCandidato);
		
		//Partido
		partido.add(cadastrarPartido);
		
		partido.add(excluirPartido);
		
		//Cargo
		cargo.add(cadastrarCargo);
		
		cargo.add(excluirCargo);
		
		//Pesquisa
		pesquisa.add(cadastrarPesquisa);
		
		pesquisa.add(relatorioPesquisa);
		
		//votação
		votacao.add(iniciarEleicao);
		votacao.add(novaEleicao);
		
		//Apuracao
		apuracao.add(consultaApuracao);
		
		//Configuracoes
		configuracoes.add(preferenciasConfiguracoes);
		
		//Sobre
		sobre.add(sobreAutor);
		
		//adiciona os menus ao panel
		this.add(arquivo);
		this.add(candidato);
		this.add(partido);
		this.add(cargo);		
		this.add(pesquisa);
		this.add(votacao);
		this.add(apuracao);
		this.add(tema);
		this.add(configuracoes);
		this.add(sobre);
		
	}
	
	/** Adiciona tratadores de eventos aos itens dos menus da barra de menu
	 *  @see ActionListener
	 *  @see ActionEvent
	 */
	private void addEventos(){
		//Cria os eventos, click
		//Arquivo
		arquivoAbrirSQL.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
            	String nomeArquivo;
            	boolean abre;
            	nomeArquivo = Janela.janelaAbrirArquivo(janela, "Abrir Arquivo SQL", null, false, "Arquivo SQL", "sql");
            	
            	if(nomeArquivo != null){
	                abre = JanelaMensagem.mostraMensagemConfirmaWarning(null, "Abrir SQL", "A mudanca no banco de dados pode gerar inconsistencias\n" +
	                		"Deseja continuar assim mesmo?");
	                if (abre){
	                	BDEleicoes BD = BDEleicoes.getInstance();
	                	if(BD.abreArquivoSQL(nomeArquivo)){
	                		JanelaMensagem.mostraMensagem(null, "Abrir Arquivo SQL", "Arquivo aberto com sucesso, todos os comandos do arquivo foram executados");
	                	}
	                	else{
	                		//JanelaMensagem.mostraMensagemErro(null, "Arquivo SQL não foi executado corretamente");
	                	}
	                }
            	}
            }
		});
		arquivoBackupExportar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
				BDEleicoes BD = BDEleicoes.getInstance();
				String enderecoNovoArquivo;
				enderecoNovoArquivo = Janela.janelaSalvarArquivo(janela, "Exportar Backup", ".", false, "Arquivo tar/gz", "tar", "gz");
				
				if (enderecoNovoArquivo != null){
					try {
						if (BD.criaBackupBancoDados(enderecoNovoArquivo)){
							JanelaMensagem.mostraMensagem(null, "Exportar Backup", "Backup criado com sucesso");
						}
						else{
							JanelaMensagem.mostraMensagemErro(null, "Backup não foi criado");
						}
					}
					catch (TarMalformatException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErro(null, "Erro ao criar arquivo no formato tar\n" +
								"Backup não foi criado");
					}
					catch (IOException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErro(null, "Erro ao criar arquivo para backup\n" +
								"Backup não foi criado");
					}
				}
            }
		});
		arquivoBackupImportar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
				//JanelaMensagem.mostraMensagemErro(null, "Nao implementado");
				BDEleicoes BD = BDEleicoes.getInstance();
				String arquivoBackup;
				arquivoBackup = Janela.janelaAbrirArquivo(janela, "Importar Backup", null, false, "Arquivo tar/gz", "tar", "gz");
				if(arquivoBackup != null){
					try {
						BD.restauraBackupBancoDeDados(arquivoBackup);
					}
					catch (TarMalformatException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErro(null, "Erro ao restaurar backup, arquivo " + arquivoBackup + " não foi reconhecido como um tar/tar.gz valido");
					}
					catch (IOException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErro(null, "Erro ao restaurar backup, erro ao abrir arquivo " + arquivoBackup);
					}
				}
            }
		});
		arquivoPopularBancoDeDados.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
            	boolean abre;
            	
                abre = JanelaMensagem.mostraMensagemConfirmaWarning(null, "Popular Banco de Dados", "A mudanca no banco de dados pode gerar inconsistencias\n" +
                		"Deseja continuar assim mesmo?");
                if (abre){
                	BDEleicoes BD = BDEleicoes.getInstance();
                	try {
						BD.insereDados();
					}
                	catch (IOException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					}
                	catch (SQLException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErroBD(null, e.getMessage());
					}
                }
            }
		});
		arquivoLimparBancoDeDados.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
				boolean excluiBD;
				excluiBD = JanelaMensagem.mostraMensagemConfirmaWarning(null, "Limpar banco de dados", "Esta operação irá excluir todos os registros do banco de dados exceto os registro de configurações\n" +
                		"Deseja continuar assim mesmo?");
				if(excluiBD){
					BDEleicoes BD = BDEleicoes.getInstance();
					try {
						BD.excluiRegistros();
						JanelaMensagem.mostraMensagem(null, "Limpar Banco de Dados", "A exclusão dos dados do banco de dados foi realizada com sucesso");
					}
					catch (IOException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErro(null, "Erro ao excluir banco de dados: " + e.getMessage());
					}
					catch (SQLException e) {
						e.printStackTrace();
						JanelaMensagem.mostraMensagemErroBD(null, "Erro ao excluir banco de dados" + e.getMessage());
					}
				} //if(excluiBD)
            }
		});
		arquivoSair.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
		});
		
		//Candidato
		cadastrarCandidato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaCadastrarCandidato();
			}
		});
		
		consultarCandidato.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaPesquisarCandidato();
			}
		});
		
		//Pesquisa
		cadastrarPesquisa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaCadastrarPesquisa();
			}
		});
		
		relatorioPesquisa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaRelatorioPesquisa();
			}
		});
		
		//Partido
		cadastrarPartido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaCadastrarPartido();
			}
		});
		
		excluirPartido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaExcluirPartido();
			}
		});
		
		//Cargo
		cadastrarCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaCadastrarCargo();
			}
		});
		
		excluirCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaExcluirCargo();
			}
		});
		
		//votação
		iniciarEleicao.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				//Votacao.getInstance();
				if(Votacao.isDiaDeVotacao()){
					//new JanelaVotacao(Votacao.getInstance().getBDCargos(new Data()));
					//Data dataVotacao = new Data("25/06/2012"); //vou colocar como data hoje, afinal a votacao é do proprio dia
					Data dataVotacao = new Data();
					new JanelaVotacao(new Votacao(dataVotacao, Votacao.getAllCargos(dataVotacao), 0));
				}
				else{
					JanelaMensagem.mostraMensagem(janela, "Votação", "Não há nenhuma votação hoje");
				}
			}
		});
		novaEleicao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaNovaEleicao();				
			}
		});
		
		//Apuracao
		consultaApuracao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaConsultaVotacao();				
			}
		});
		
		//Configuracoes
		preferenciasConfiguracoes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new JanelaConfiguracoes();
			}
		});
		
		//Sobre
		sobreAutor.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent ae) {
				JanelaMensagem.mostraMensagem(null, "Sobre", "Criado por: \nArthur Assuncao");
            }
		});
		
	}//fim addEventos()
}
