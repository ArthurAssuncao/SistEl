/* Deleta Tabelas */
DROP TABLE partido_politico CASCADE;
DROP TABLE cargo CASCADE;
DROP TABLE candidato CASCADE;
DROP TABLE pesquisa CASCADE;
DROP TABLE candidato_pesquisa IF EXISTS;
DROP TABLE votacao CASCADE;
DROP TABLE cargo_votacao IF EXISTS;
DROP TABLE candidato_votacao IF EXISTS;
DROP TABLE configuracoes IF EXISTS;
/* */
/* Deleta Sequencias*/
DROP SEQUENCE seq_candidato IF EXISTS;
DROP SEQUENCE seq_partido_politico IF EXISTS;
DROP SEQUENCE seq_cargo IF EXISTS;
DROP SEQUENCE seq_pesquisa IF EXISTS;
DROP SEQUENCE seq_candidato_pesquisa IF EXISTS;
DROP SEQUENCE seq_configuracoes IF EXISTS;
DROP SEQUENCE seq_votacao IF EXISTS;
DROP SEQUENCE seq_cargo_votacao IF EXISTS;
DROP SEQUENCE seq_candidato_votacao IF EXISTS;
/* */
