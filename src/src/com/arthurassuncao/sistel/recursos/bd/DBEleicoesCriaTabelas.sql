/* Cria Tabelas */
/* Partido Politico */
CREATE TABLE IF NOT EXISTS partido_politico(
	idPartidoPolitico INTEGER NOT NULL UNIQUE,
	sigla VARCHAR(6),
	nome VARCHAR_IGNORECASE(100),
	numero NUMERIC(2,0),
	CONSTRAINT pk_partido_politico PRIMARY KEY (numero)
);
/* Cargo */
CREATE TABLE IF NOT EXISTS cargo(
	idCargo INTEGER NOT NULL UNIQUE,
	nome VARCHAR_IGNORECASE(20),
	numeroDigitos NUMERIC(1,0),
	CONSTRAINT pk_cargo PRIMARY KEY (nome),
	CHECK ( numeroDigitos >= 0)
);
/* Candidato */
CREATE TABLE IF NOT EXISTS candidato(
	idCandidato INTEGER NOT NULL UNIQUE,
	nome VARCHAR_IGNORECASE(100),
	numero NUMERIC(5,0),
	cargo VARCHAR_IGNORECASE(20),
	idPartidoPolitico INTEGER,
	foto VARCHAR(255) DEFAULT '',
	CONSTRAINT fk_candidato_cargo FOREIGN KEY (cargo) REFERENCES cargo(nome) ON DELETE CASCADE,
	CONSTRAINT fk_candidato_partido_politico FOREIGN KEY (idPartidoPolitico) REFERENCES partido_politico(idPartidoPolitico) ON DELETE CASCADE,
	CONSTRAINT pk_candidato PRIMARY KEY (numero, cargo)
);
/* Pesquisa */
CREATE TABLE IF NOT EXISTS pesquisa(
	idPesquisa INTEGER NOT NULL UNIQUE,
	cargo VARCHAR_IGNORECASE(20),
	data_Inicio DATE,
	data_Fim DATE,
	votos_Nulos_Brancos INTEGER DEFAULT 0,
	votos_Indecisos INTEGER DEFAULT 0,
	numero_Pessoas_Entrevistadas INTEGER DEFAULT 0,
	numero_Municipios_Pesquisados INTEGER DEFAULT 0,
	CONSTRAINT fk_pesquisa_cargo FOREIGN KEY (cargo) REFERENCES cargo(nome) ON DELETE CASCADE,
	CONSTRAINT pk_pesquisa PRIMARY KEY (cargo, data_Inicio, data_Fim),
	CHECK ( (votos_Nulos_Brancos >= 0) AND 
			(votos_Indecisos >= 0) AND
			(numero_Pessoas_Entrevistadas >= 0) AND
			(numero_Municipios_Pesquisados >= 0)
	)
);
/* Candidatos_Pesquisa */
CREATE TABLE IF NOT EXISTS candidato_pesquisa(
	idCandidatoPesquisa INTEGER NOT NULL,
	idCandidato INTEGER,
	numero_Votos INTEGER DEFAULT 0,
	idPesquisa INTEGER,
	CONSTRAINT fk_candidato_pesquisa_idcandidato FOREIGN KEY (idCandidato) REFERENCES candidato(idCandidato) ON DELETE CASCADE,
	CONSTRAINT fk_candidato_pesquisa_pesquisa FOREIGN KEY (idPesquisa) REFERENCES pesquisa(idPesquisa) ON DELETE CASCADE,
	CONSTRAINT pk_candidato_pesquisa PRIMARY KEY (idCandidato, idPesquisa)
);
/* votação */
CREATE TABLE IF NOT EXISTS votacao(
	idVotacao INTEGER NOT NULL,
	data DATE NOT NULL,
	CONSTRAINT pk_votacao PRIMARY KEY (data)
);
/* Cargos concorrentes a eleição */
CREATE TABLE IF NOT EXISTS cargo_votacao(
	idCargoVotacao INTEGER NOT NULL,
	data_Votacao DATE NOT NULL,
	cargo VARCHAR_IGNORECASE(20),
	votos_brancos INTEGER DEFAULT 0,
	votos_nulos INTEGER DEFAULT 0,
	CONSTRAINT fk_cargo_votacao_data FOREIGN KEY (data_Votacao) REFERENCES votacao(data) ON DELETE CASCADE,
	CONSTRAINT fk_cargo_votacao_cargo FOREIGN KEY (cargo) REFERENCES cargo(nome) ON DELETE CASCADE,
	CONSTRAINT pk_cargo_votacao PRIMARY KEY (data_Votacao, cargo),
	CHECK ( (votos_brancos >= 0) AND 
		    (votos_nulos >= 0)
	)
);
/* Candidatos_Votacao */
CREATE TABLE IF NOT EXISTS candidato_votacao(
	idCandidatoVotacao INTEGER NOT NULL,
	data_Votacao DATE NOT NULL,
	idCandidato INTEGER,
	numero_Votos INTEGER DEFAULT 0,
	CONSTRAINT fk_candidato_votacao_candidato FOREIGN KEY (idCandidato) REFERENCES candidato(idCandidato) ON DELETE CASCADE,
	CONSTRAINT fk_candidato_votacao_data FOREIGN KEY (data_Votacao) REFERENCES votacao(data) ON DELETE CASCADE,
	CONSTRAINT pk_candidato_votacao PRIMARY KEY (idCandidato, data_Votacao)
);
/* Configuracoes */
CREATE TABLE IF NOT EXISTS configuracoes(
	idConfiguracao INTEGER NOT NULL,
	nome VARCHAR(50),
	valor VARCHAR(50),
	CONSTRAINT pk_configuracoes PRIMARY KEY (nome)
);
/* */
/* Cria Sequencias */
CREATE SEQUENCE seq_candidato START WITH 1;
CREATE SEQUENCE seq_partido_politico START WITH 1;
CREATE SEQUENCE seq_cargo START WITH 1;
CREATE SEQUENCE seq_pesquisa START WITH 1;
CREATE SEQUENCE seq_candidato_pesquisa START WITH 1;
CREATE SEQUENCE seq_votacao START WITH 1;
CREATE SEQUENCE seq_cargo_votacao START WITH 1;
CREATE SEQUENCE seq_candidato_votacao START WITH 1;
CREATE SEQUENCE seq_configuracoes START WITH 1;
/* usar nextval('seq_candidato') para usar o proximo id valido no postgres*/
/* NEXT VALUE FOR seq_candidato no hsql*/
/* */
/* INSERE AS CONFIGURACOES */
INSERT INTO configuracoes VALUES (NEXT VALUE FOR seq_configuracoes, 'senha', '123456');