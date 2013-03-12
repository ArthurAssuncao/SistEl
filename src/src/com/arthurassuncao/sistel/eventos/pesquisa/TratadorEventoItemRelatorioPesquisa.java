package com.arthurassuncao.sistel.eventos.pesquisa;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import com.arthurassuncao.sistel.classes.Data;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa;
import com.arthurassuncao.sistel.classes.pesquisa.Pesquisa.NomeCandidatoVotos;
import com.arthurassuncao.sistel.gui.graficos.Grafico.Graficos;
import com.arthurassuncao.sistel.gui.pesquisa.JanelaRelatorioPesquisa;

/** Classe para tratar os eventos de item da janela <code>JanelaRelatorioPesquisa</code>
 * @author Arthur Assunção
 * 
 * 
 * @see ItemListener
 *
 */
public class TratadorEventoItemRelatorioPesquisa implements ItemListener {

	private JanelaRelatorioPesquisa janela;
	
	/** Cria uma instancia do Tratador de eventos de item da janela <code>JanelaRelatorioPesquisa</code>
	 * @param janela <code>JanelaRelatorioPesquisa</code> que sera manipulada
	 */
	public TratadorEventoItemRelatorioPesquisa(JanelaRelatorioPesquisa janela){
		this.janela = janela;
	}
	
	/** Trata o evento de mudança de estado dos <code>JComboBox</code>, com isso a mudança dos graficos.
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent evento){
		String valorSelecionado = null;
		if (evento.getStateChange() == ItemEvent.SELECTED){
			if (evento.getSource() instanceof JComboBox){
				valorSelecionado = (String)( ((JComboBox<String>)evento.getSource()).getSelectedItem() );
				janela.changeEnabledCampoSelecionarGrafico();
			}
			//Cargo
			if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarCargo()){ //mudou um cargo
				if (!valorSelecionado.equals(JanelaRelatorioPesquisa.TEXTO_SELECIONE_CARGO)){
					List<Data[]> datas = Pesquisa.getDatasByCargo(valorSelecionado);
					janela.setValoresCampoSelecionarDataPesquisa(datas);
					if (janela.getCampoSelecionarDataPesquisa().getItemCount() > 0){
						if (janela.getCampoSelecionarDataPesquisa().getSelectedIndex() > 0){ //tem alguma data de pesquisa selecionada
							janela.setCampoSelecionarGrafico(false); //permite usar graficos que usam apenas uma pesquisa
						}
						else{
							janela.setCampoSelecionarGrafico(true); //permite usar graficos que usam varias pesquisas
						}
					}
				}
				else{
					if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarCargo()){
						janela.setValoresCampoSelecionarDataPesquisa(null);
					}
				}
			}
			//Datas Pesquisa
			else if((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarDataPesquisa()){
				if (!valorSelecionado.equals(JanelaRelatorioPesquisa.TEXTO_SELECIONE_DATA_PESQUISA)){
					janela.setCampoSelecionarGrafico(false); //permite usar graficos que usam apenas uma serie
					if (janela.getCampoSelecionarGrafico().getSelectedIndex() > 0){ //tem grafico selecionado
						this.mudaGrafico((String)janela.getCampoSelecionarGrafico().getSelectedItem(), janela.getValorCampoSelecionarCargo());
					}
				}
				else{ //nenhuma pesquisa foi selecionada
					janela.setCampoSelecionarGrafico(true); //permite usar graficos que usam varias pesquisas
				}
			}
			//Grafico
			else if ((JComboBox<String>)evento.getSource() == janela.getCampoSelecionarGrafico()){
				if (!valorSelecionado.equals(JanelaRelatorioPesquisa.TEXTO_SELECIONE_GRAFICO)){
					this.mudaGrafico(valorSelecionado, janela.getValorCampoSelecionarCargo());
				}
				else{
					janela.removeGrafico();
				}
			} //fim grafico
		}// fim ItemEvent.SELECTED
	}// fim metodo

	/** Muda o grafico da janela <code>JanelaRelatorioPesquisa</code> setando os valores de acordo com as opcoes selecionadas a partir dos
	 * <code>JComboBox</code> e os dados do banco de dados.
	 * @param graficoSelecionado
	 * @param cargo
	 */
	private void mudaGrafico(String graficoSelecionado, String cargo){
		/*Pesquisa pesquisa = Pesquisa.getInstance();
		pesquisa.setCargo(cargo);*/
		boolean graficoEmSerie = false;
		if(cargo == null){
			return;
		}
		for(Graficos grafico : Graficos.values()){
			if (grafico.getNome().equalsIgnoreCase(graficoSelecionado)){
				graficoEmSerie = grafico.isEmSerie();
				break;
			}
		}
		if (!graficoEmSerie){
			Data[] datas;
			List<NomeCandidatoVotos> nomeCandidatosVotos;
			datas = janela.getDatasCampoSelecionarDataPesquisa(); //pega datas selecionadas
			/*pesquisa.setDataInicio(datas[0]);
			pesquisa.setDataFim(datas[1]);*/
		
			nomeCandidatosVotos = Pesquisa.getNomeCandidatosVotos(cargo, datas[0], datas[1]);
			int idPesquisa = Pesquisa.getId(cargo, datas[0], datas[1]);
			//adiciona os brancos e indecisos
			nomeCandidatosVotos.add(new NomeCandidatoVotos(idPesquisa, "Brancos ou Nulos", Pesquisa.getNumeroVotosNulosBrancos(idPesquisa)));
			nomeCandidatosVotos.add(new NomeCandidatoVotos(idPesquisa, "Indecisos", Pesquisa.getNumeroVotosIndecisos(idPesquisa)));
			
			int entrevistados = Pesquisa.getNumeroPessoasEntrevistadas(idPesquisa);
			int municipios = Pesquisa.getNumeroMunicipiosPesquisados(idPesquisa);
			
			janela.setGrafico(graficoSelecionado, nomeCandidatosVotos, entrevistados, municipios);
		}
		else{ //grafico em serie, ou seja, usa todas as pesquisas de determinado cargo
			//JanelaMensagem.mostraMensagemErro(null, "Esta parte esta sendo refeita, portanto nao funciona, favor esperar...");
			List<String[]> idDatasFim = null; 
			List<Data> datasFim = new ArrayList<Data>(); //considera a data de fim como a data da pesquisa
			List<String> nomes = new ArrayList<String>();
			List<ArrayList<Integer>> valores = new ArrayList<ArrayList<Integer>>(); 
			final int VALOR_NULO = -1;
			
			idDatasFim = Pesquisa.getIdDataFim(cargo);
			if (idDatasFim != null && idDatasFim.size() > 0){
				for(int i = 0; i < idDatasFim.size(); i++){
					datasFim.add(new Data(Data.AAAAMMDDtoDDMMAAAA(idDatasFim.get(i)[1])));
				}
				//faz pesquisa pelo id, pegando os nomes e valores(votos)
				//seta o cargo da pesquisa
				//pesquisa.setCargo(janela.getValorCampoSelecionarCargo());
				cargo = janela.getValorCampoSelecionarCargo();
				
				List<NomeCandidatoVotos> nomeCandidatosVotos = new ArrayList<Pesquisa.NomeCandidatoVotos>();
			
				int numeroMaximoCandidatos = Pesquisa.getMaximoCandidatosCargo(cargo); //pega o maximo de candidatos entre as pesquisas
				List<Integer> idsPesquisas = Pesquisa.getIdPesquisaByCargo(cargo); //pega os ids das pesquisas
				
				//pega o nome de todos candidatos do cargo selecionado
				nomeCandidatosVotos = Pesquisa.getNomeCandidatosVotosByCargo(cargo);
			
				NomeCandidatoVotos brancosNulos;
				NomeCandidatoVotos indecisos;
				
				int k = 0;
				int j = 0;
				int posicaoIdPesquisa;
				for(int i = 0; i < numeroMaximoCandidatos; i++){
					//armazena os nomes dos candidatos
					if(!nomes.contains(nomeCandidatosVotos.get(k).getNomeCandidato())){
						nomes.add(nomeCandidatosVotos.get(k).getNomeCandidato());
					}
					//aumenta o numero de candidatos
					valores.add(new ArrayList<Integer>());
					
					j = 0;
					while(j < idsPesquisas.size()){
						//if(j < valores.get(i).size()){
						if(k < nomeCandidatosVotos.size()){
							posicaoIdPesquisa = -1;
							for(int n = 0; n < idsPesquisas.size(); n ++){
								if(idsPesquisas.get(n).intValue() == nomeCandidatosVotos.get(k).getIdPesquisa()){
									posicaoIdPesquisa = n;
									break;
								}
							}

							if(posicaoIdPesquisa > j){ //nao participou de pesquisas anteriores
								while(j < posicaoIdPesquisa){
									valores.get(i).add(VALOR_NULO);
									j++;
								}
								j++;
								valores.get(i).add(nomeCandidatosVotos.get(k).getNumeroVotos());
								k++;
								continue;
							}
							else if(posicaoIdPesquisa == j){ //participou das pesquisas anteriores
								valores.get(i).add(nomeCandidatosVotos.get(k).getNumeroVotos());
								k++;
							}
						}
						else{
							valores.get(i).add(VALOR_NULO);
						}
						j++;
					}
				}				
				//armazena nos nomes os brancos/nulos e indecisos
				brancosNulos = new NomeCandidatoVotos(Integer.parseInt(idDatasFim.get(0)[0]), "Brancos ou Nulos", Pesquisa.getNumeroVotosNulosBrancos(Integer.parseInt(idDatasFim.get(0)[0]) ));
				indecisos = new NomeCandidatoVotos(Integer.parseInt(idDatasFim.get(0)[0]), "Indecisos", Pesquisa.getNumeroVotosIndecisos(Integer.parseInt(idDatasFim.get(0)[0]) ));
				nomes.add(brancosNulos.getNomeCandidato());
				nomes.add(indecisos.getNomeCandidato());
				
				//adiciona os brancos e nulos
				valores.add(new ArrayList<Integer>());
				valores.get(valores.size()-1).add(brancosNulos.getNumeroVotos());
				for(int i = 1; i < idDatasFim.size(); i++){
					brancosNulos = new NomeCandidatoVotos(Integer.parseInt(idDatasFim.get(i)[0]), "Brancos ou Nulos", Pesquisa.getNumeroVotosNulosBrancos(Integer.parseInt(idDatasFim.get(i)[0]) ));
					valores.get(valores.size()-1).add(brancosNulos.getNumeroVotos());
				}
				
				//adiciona os indecisos
				valores.add(new ArrayList<Integer>());
				valores.get(valores.size()-1).add(indecisos.getNumeroVotos());
				for(int i = 1; i < idDatasFim.size(); i++){
					indecisos = new NomeCandidatoVotos(Integer.parseInt(idDatasFim.get(i)[0]), "Indecisos", Pesquisa.getNumeroVotosIndecisos(Integer.parseInt(idDatasFim.get(i)[0]) ));
					valores.get(valores.size()-1).add(indecisos.getNumeroVotos());
				}
				
				
				for(int i=0; i < valores.size(); i++){
					if(i < nomes.size())
						System.out.println(nomes.get(i));
					//System.out.println();
					for(int m =0; m < valores.get(i).size(); m++){
						System.out.print(valores.get(i).get(m).intValue() + " ");
					}
					System.out.println();
				}
				
				janela.setGrafico(graficoSelecionado, nomes, valores, datasFim);
			}// fim if (idDatasFim != null)
		}
	}
	
}
