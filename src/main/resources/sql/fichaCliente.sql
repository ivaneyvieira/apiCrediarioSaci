DROP TABLE IF EXISTS TCODIGOS;
CREATE TEMPORARY TABLE TCODIGOS (
  PRIMARY KEY (custno)
)
SELECT no AS custno
FROM sqldados.custp
WHERE no = :custno
  AND fjflag = 1;

/****************************************************************************/

DROP TABLE IF EXISTS TCADASTRO;
CREATE TEMPORARY TABLE TCADASTRO (
  PRIMARY KEY (custno)
)
SELECT no                                                                              AS custno,
       LPAD(no, 6, '0')                                                                AS codigo,
       name                                                                            AS nome,
       IF(birthday = 0, NULL, TIMESTAMPDIFF(YEAR, birthday, current_date))             AS idade,
       crlimit / 100                                                                   AS limiteCredito,
       IF(dtcrlimit = 0, NULL, cast(dtcrlimit AS DATE))                                AS dtLimiteCredito,
       IFNULL(sqldados.fn_atualizaLimiteCR(crlimit, dtcrlimit),
	      0)                                                                       AS limiteAtualizado,
       IF(admiss_chp11dt = 0, NULL,
	  TIMESTAMPDIFF(YEAR, admiss_chp11dt, current_date))                           AS tempoAdmissao,
       IF(sincedt = 0, NULL,
	  TIMESTAMPDIFF(YEAR, sincedt, current_date))                                  AS tempoRelacionamento,
       IF((owner & POW(2, 0)) <> 0, 'S', 'N')                                          AS moradiaPropria,
       C.city1                                                                         AS cidade,
       C.state1                                                                        AS estado,
       TRIM(MID(C.zip, 1, 10))                                                         AS cep
FROM sqldados.custp AS C
  INNER JOIN TCODIGOS
	       ON C.no = custno;

/****************************************************************************/

DROP TABLE IF EXISTS TRENDA_A;
CREATE TEMPORARY TABLE TRENDA_A (
  PRIMARY KEY (custno, seqno, tipo)
)
SELECT custno,
       seqno,
       declarada / 100                 AS vlDeclardo,
       comprovada / 100                AS vlComprovado,
       fontePagadora,
       docno,
       cast(date AS DATE)              AS data,
       IF(auxShort1 = 0, 'CLI', 'CON') AS tipo
FROM sqldados.ctrenda AS R
  INNER JOIN TCODIGOS AS N
	       USING (custno)
WHERE declarada > 0
   OR comprovada > 0;

DROP TABLE IF EXISTS TRENDA_CLI;
CREATE TEMPORARY TABLE TRENDA_CLI (
  PRIMARY KEY (custno, seqno)
)
SELECT *
FROM TRENDA_A AS R
WHERE tipo = 'CLI';

DROP TABLE IF EXISTS TRENDA_CON;
CREATE TEMPORARY TABLE TRENDA_CON (
  PRIMARY KEY (custno, seqno)
)
SELECT *
FROM TRENDA_A AS R
WHERE tipo = 'CON';

DROP TABLE IF EXISTS TRENDA;
CREATE TEMPORARY TABLE TRENDA (
  PRIMARY KEY (custno)
)
SELECT custno,
       SUM(vlDeclardo)   AS vlDeclardo,
       SUM(vlComprovado) AS vlComprovado,
       max(data)         AS ultimaAtualizacaoRenda
FROM TRENDA_CLI
GROUP BY custno;

/****************************************************************************/

DROP TABLE IF EXISTS TRESUMO_CREDIARIO;
CREATE TEMPORARY TABLE TRESUMO_CREDIARIO
SELECT custno,
       IF(P.status = 1, instamt, NULL) / 100                                               AS pestacaoQuitada,
       IF(P.status IN (0, 2, 3, 4), instamt - paidamt, NULL) / 100                         AS parcelaAberto,
       IF(P.status IN (0, 2, 3, 4) AND paidamt = 0, DATEDIFF(duedate, current_date),
	  NULL)                                                                            AS diasVencimento
FROM sqldados.inst         AS I
  INNER JOIN sqldados.itxa AS P
	       USING (storeno, contrno)
  INNER JOIN TCODIGOS
	       USING (custno);

DROP TABLE IF EXISTS TRESUMO_CARTAO;
CREATE TEMPORARY TABLE TRESUMO_CARTAO
SELECT custno,
       IF(R.status IN (1, 2), P.amt, NULL) / 100 AS pestacaoQuitada,
       IF(R.status IN (0), P.amt, NULL) / 100    AS parcelaAberto,
       cast(NULL AS UNSIGNED)                    AS diasVencimento
FROM sqldados.crdr           AS R
  INNER JOIN sqldados.crdrxa AS P
	       USING (storeno, date, cardno, resumo, parcela)
  INNER JOIN TCODIGOS
	       USING (custno);

DROP TABLE IF EXISTS TRESUMO_AGRUPADO;
CREATE TEMPORARY TABLE TRESUMO_AGRUPADO
SELECT custno,
       'CRE' AS tipo,
       pestacaoQuitada,
       parcelaAberto,
       diasVencimento
FROM TRESUMO_CREDIARIO
UNION
SELECT custno,
       'CAR' AS tipo,
       pestacaoQuitada,
       parcelaAberto,
       diasVencimento
FROM TRESUMO_CARTAO;

DROP TABLE IF EXISTS TRESUMO_PRESTACOES;
CREATE TEMPORARY TABLE TRESUMO_PRESTACOES (
  PRIMARY KEY (custno)
)
SELECT custno,
       ROUND(AVG(IF(pestacaoQuitada > 0, pestacaoQuitada, 0)), 2)           AS mediaPestacaoQuitada,
       IFNULL(ROUND(SUM(IF(parcelaAberto > 0, parcelaAberto, NULL)), 2), 0) AS totalParcelaAberto,
       SUM(IF(diasVencimento > 0, 1, 0))                                    AS qtdePrestacoesVencidas,
       SUM(IF(diasVencimento > 0, diasVencimento, 0))                       AS maiorAtraso
FROM TRESUMO_AGRUPADO
GROUP BY custno;

/****************************************************************************/

DROP TABLE IF EXISTS TRESUMO_CHEQUES;
CREATE TEMPORARY TABLE TRESUMO_CHEQUES (
  PRIMARY KEY (custno)
)
SELECT custno, SUM(IF(status NOT IN (1, 37), amt / 100, 0)) AS totalChequeAberto
FROM sqldados.cpdue
  INNER JOIN TCODIGOS
	       USING (custno)
GROUP BY custno;

/****************************************************************************/

DROP TABLE IF EXISTS TAVALISTAS;
CREATE TEMPORARY TABLE TAVALISTAS
SELECT avalno AS custno,
       storeno,
       contrno,
       CASE I.status
	 WHEN 0
	   THEN 'ABERTO'
	 WHEN 1
	   THEN 'LIQUIDADO'
	 WHEN 2
	   THEN 'CANCELADO'
       END    AS situacao
FROM sqldados.itaval       AS A
  INNER JOIN sqldados.inst AS I
	       USING (storeno, contrno)
  INNER JOIN TCODIGOS      AS C
	       ON A.avalno = C.custno;

DROP TABLE IF EXISTS TAVALISTA_ABERTO;
CREATE TEMPORARY TABLE TAVALISTA_ABERTO (
  PRIMARY KEY (custno)
)
SELECT custno
FROM TAVALISTAS
WHERE situacao = 'ABERTO'
GROUP BY custno;

/****************************************************************************/

DROP TABLE IF EXISTS TRENEGOCIADO;
CREATE TEMPORARY TABLE TRENEGOCIADO (
  PRIMARY KEY (custno)
)
SELECT custno
FROM sqldados.ithist       AS H
  INNER JOIN sqldados.itxa AS P
	       USING (storeno, contrno)
  INNER JOIN sqldados.inst AS C
	       USING (storeno, contrno)
  INNER JOIN TCODIGOS      AS N
	       USING (custno)
WHERE H.status = 40
GROUP BY custno;

/****************************************************************************/

SELECT cast(codigo AS CHAR)                                                       AS codigo,
       nome                                                                       AS nome,
       IFNULL(vlDeclardo, 0)                                                      AS vlDeclardo,
       IFNULL(vlComprovado, 0)                                                    AS vlComprovado,
       mediaPestacaoQuitada                                                       AS mediaPestacaoQuitada,
       idade                                                                      AS idade,
       IF(limiteCredito > 0, limiteAtualizado - IFNULL(totalParcelaAberto, 0),
	  0)                                                                      AS limiteDisponivel,
       ultimaAtualizacaoRenda                                                     AS ultimaAtualizacaoRenda,
       tempoAdmissao                                                              AS tempoAdmissao,
       tempoRelacionamento                                                        AS tempoRelacionamento,
       IFNULL(totalChequeAberto, 0)                                               AS totalChequeAberto,
       qtdePrestacoesVencidas                                                     AS qtdePrestacoesVencidas,
       maiorAtraso                                                                AS maiorAtraso,
       moradiaPropria                                                             AS moradiaPropria,
       IF(P.custno IS NULL, 'N', 'S')                                             AS possuiCompraCrediario,
       cidade                                                                     AS cidade,
       estado                                                                     AS estado,
       cep                                                                        AS cep,
       IF(A.custno IS NULL, 'N', 'S')                                             AS avalistaContratoAberto,
       IF(R.custno IS NULL, 'N', 'S')                                             AS renegociouContrato
FROM TCODIGOS
  LEFT JOIN TCADASTRO
	      USING (custno)
  LEFT JOIN TRENDA
	      USING (custno)
  LEFT JOIN TRESUMO_PRESTACOES AS P
	      USING (custno)
  LEFT JOIN TRESUMO_CHEQUES
	      USING (custno)
  LEFT JOIN TRENEGOCIADO       AS R
	      USING (custno)
  LEFT JOIN TAVALISTA_ABERTO   AS A
	      USING (custno)