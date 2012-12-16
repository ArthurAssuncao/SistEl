/* Deleta Registos das Tabelas */
TRUNCATE TABLE partido_politico;
TRUNCATE TABLE cargo;
TRUNCATE TABLE candidato;
TRUNCATE TABLE candidato_pesquisa;
TRUNCATE TABLE pesquisa;
TRUNCATE TABLE cargo_votacao;
TRUNCATE TABLE candidato_votacao;
TRUNCATE TABLE votacao;
/* */
/* Reinicia Sequencias*/
ALTER SEQUENCE seq_candidato RESTART WITH 1;
ALTER SEQUENCE seq_partido_politico RESTART WITH 1;
ALTER SEQUENCE seq_cargo RESTART WITH 1;
ALTER SEQUENCE seq_pesquisa RESTART WITH 1;
ALTER SEQUENCE seq_candidato_pesquisa RESTART WITH 1;
ALTER SEQUENCE seq_votacao RESTART WITH 1;
ALTER SEQUENCE seq_cargo_votacao RESTART WITH 1;
ALTER SEQUENCE seq_candidato_votacao RESTART WITH 1;
/* */
