/* INSERE PARTIDOS */
/* idPartidoPolitico, sigla, nome, numero */
INSERT INTO partido_politico VALUES (NEXT VALUE FOR seq_partido_politico, 'PT', 'Partido dos Trabalhadores', 13);
INSERT INTO partido_politico VALUES (NEXT VALUE FOR seq_partido_politico, 'PSDB', 'Partido da Social Democracia Brasileiro', 45);
INSERT INTO partido_politico VALUES (NEXT VALUE FOR seq_partido_politico, 'SBC', 'SBC', 11);
/* */
/* INSERE CARGOS */
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Presidente', 2);
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Governador', 2);
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Deputado Federal', 4);
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Deputado Estadual', 5);
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Senador', 3);
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Prefeito', 2);
INSERT INTO cargo VALUES (NEXT VALUE FOR seq_cargo, 'Vereador', 5);
/* */
/* INSERE CANDIDATOS */
/* idCandidato, nome, numero, idPartidoPolitico, foto */
INSERT INTO candidato VALUES (NEXT VALUE FOR seq_candidato, 'Luiz Inacio Lula da Silva', 13, 'Presidente', 1, '');
INSERT INTO candidato VALUES (NEXT VALUE FOR seq_candidato, 'Aecio Neves', 45, 'Presidente', 2, '');
INSERT INTO candidato VALUES (NEXT VALUE FOR seq_candidato, 'Antônio Anastasia', 45, 'Governador', 2, 'imagens_candidatos/homemBrinquedo.jpg');
INSERT INTO candidato VALUES (NEXT VALUE FOR seq_candidato, 'Desenvolvedor', 11, 'Presidente', 3, 'imagens/semFoto.jpg');
/* */
/* INSERE PESQUISA */
INSERT INTO PESQUISA VALUES(NEXT VALUE FOR seq_pesquisa, 'Presidente', '2012-05-10', '2012-05-11', 5, 0, 8, 1);
INSERT INTO PESQUISA VALUES(NEXT VALUE FOR seq_pesquisa, 'Presidente', '2012-06-11', '2012-06-12', 10, 20, 330, 1);
INSERT INTO PESQUISA VALUES(NEXT VALUE FOR seq_pesquisa, 'Presidente', '2012-04-19', '2012-04-25', 5, 10, 85, 1);
INSERT INTO PESQUISA VALUES(NEXT VALUE FOR seq_pesquisa, 'Presidente', '2012-03-12', '2012-03-22', 0, 0, 80, 1);
INSERT INTO PESQUISA VALUES(NEXT VALUE FOR seq_pesquisa, 'Presidente', '2012-02-07', '2012-02-21', 10, 0, 190, 1);
INSERT INTO PESQUISA VALUES(NEXT VALUE FOR seq_pesquisa, 'Presidente', '2012-06-17', '2012-06-19', 10, 0, 85, 1);
/* */
/* INSERE CANDIDATOS_PESQUISA */
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 1, 70, 1);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 2, 50, 1);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 1, 100, 2);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 2, 200, 2);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 1, 30, 3);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 2, 40, 3);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 1, 10, 4);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 2, 20, 4);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 4, 50, 4);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 4, 70, 5);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 1, 50, 5);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 2, 60, 5);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 4, 30, 6);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 1, 20, 6);
INSERT INTO CANDIDATO_PESQUISA VALUES(NEXT VALUE FOR seq_candidato_pesquisa, 2, 25, 6);
/* */
/* INSERE VOTACAO */
INSERT INTO VOTACAO VALUES(NEXT VALUE FOR seq_votacao, '2012-06-25');
/* */
/* INSERE CARGO_VOTACAO */
INSERT INTO CARGO_VOTACAO VALUES(NEXT VALUE FOR seq_cargo_votacao, '2012-06-25', 'Governador', 0, 0);
INSERT INTO CARGO_VOTACAO VALUES(NEXT VALUE FOR seq_cargo_votacao, '2012-06-25', 'Presidente', 0, 0);
INSERT INTO CARGO_VOTACAO VALUES(NEXT VALUE FOR seq_cargo_votacao, '2012-06-25', 'Senador', 0, 0);
/* */
/* INSERE CONFIGURACOES */
/* INSERT INTO configuracoes VALUES (NEXT VALUE FOR seq_configuracoes, 'senha', '123456');*/
