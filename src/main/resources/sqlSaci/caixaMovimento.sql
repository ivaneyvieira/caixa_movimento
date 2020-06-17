DROP TABLE IF EXISTS sqldados.TMP2_24746;
CREATE TEMPORARY TABLE sqldados.TMP2_24746 /*2*/
SELECT pxa.pdvno                                                 AS pdvno,
       pxa.storeno                                               AS storeno,
       pxa.eordno                                                AS eordno,
       pxa.nfno                                                  AS nfno,
       pxa.nfse                                                  AS nfse,
       ifnull(eord.pdvno, 0)                                     AS pdvnro,
       pxa.date                                                  AS date,
       pxa.paymno                                                AS paymno,
       pxa.xatype                                                AS xatype,
       pxa.amt - pxa.discount                                    AS amt,
       if(pxa.xatype != 999, pxa.xatype, pxaval.xatype)          AS xatipo,
       if(pxa.xatype != 999, pxa.amt - pxa.discount, pxaval.amt) AS amtot,
       0                                                         AS cardno,
       pxa.xano                                                  AS xano,
       pxa.empno                                                 AS empno,
       pxa.custno                                                AS custno,
       '0'                                                       AS autorz,
       pxa.time                                                  AS time
FROM sqlpdv.pxa
  LEFT JOIN sqlpdv.pxaval
	      ON (pxaval.storeno = pxa.storeno AND pxaval.pdvno = pxa.pdvno AND
		  pxaval.xano = pxa.xano)
  LEFT JOIN sqldados.eord
	      ON (eord.storeno = pxa.storeno AND eord.ordno = pxa.eordno)
WHERE (pxa.nfse = '' OR '' = '')
  AND (pxa.date BETWEEN 20200616 AND 20200616)
  AND (pxa.pdvno = 0 OR 0 = 0)
  AND (pxa.amt - pxa.discount = 0 OR 0 = 0)
  AND (pxa.storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pxa.custno = 0 OR 0 = 0)
  AND (pxa.empno = 0 OR 0 = 0)
  AND (pxa.eordno = 0 OR 0 = 0)
  AND (pxa.nfno = 0 OR 0 = 0)
  AND NOT (pxa.bits & POW(2, 0))
  AND NOT (pxa.bits & POW(2, 4))
  AND NOT (pxa.bits & POW(2, 7))
HAVING xatipo != 3
ORDER BY xatipo, cardno, storeno, date, pdvno, nfno, nfse;
DROP TABLE IF EXISTS sqldados.TMP3_24746;
CREATE TEMPORARY TABLE sqldados.TMP3_24746 /*3*/
SELECT pxa.pdvno                                                               AS pdvno,
       pxa.storeno                                                             AS storeno,
       pxa.eordno                                                              AS eordno,
       pxa.nfno                                                                AS nfno,
       pxa.nfse                                                                AS nfse,
       ifnull(eord.pdvno, 0)                                                   AS pdvnro,
       pxa.date                                                                AS date,
       pxa.paymno                                                              AS paymno,
       pxa.xatype                                                              AS xatype,
       pxa.amt - pxa.discount                                                  AS amt,
       if(pxa.xatype NOT IN (999, 3), pxa.xatype, 3)                           AS xatipo,
       sum(if(pxa.xatype NOT IN (999, 3), pxa.amt - pxa.discount, pxacrd.amt)) AS amtot,
       pxacrd.cardno                                                           AS cardno,
       pxa.xano                                                                AS xano,
       pxa.empno                                                               AS empno,
       pxa.custno                                                              AS custno,
       pxacrd.autorz                                                           AS autorz,
       pxa.time                                                                AS time
FROM sqlpdv.pxa
  LEFT JOIN sqldados.eord
	      ON (eord.storeno = pxa.storeno AND eord.ordno = pxa.eordno) STRAIGHT_JOIN sqlpdv.pxacrd
WHERE pxacrd.storeno = pxa.storeno
  AND pxacrd.pdvno = pxa.pdvno
  AND pxacrd.xano = pxa.xano
  AND (pxa.date BETWEEN 20200616 AND 20200616)
  AND (pxa.nfse = '' OR '' = '')
  AND (pxa.pdvno = 0 OR 0 = 0)
  AND (pxa.amt - pxa.discount = 0 OR 0 = 0)
  AND (pxa.storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pxa.custno = 0 OR 0 = 0)
  AND (pxa.empno = 0 OR 0 = 0)
  AND (pxa.eordno = 0 OR 0 = 0)
  AND (pxa.nfno = 0 OR 0 = 0)
  AND NOT (pxa.bits & POW(2, 0))
  AND NOT (pxa.bits & POW(2, 4))
  AND NOT (pxa.bits & POW(2, 7))
GROUP BY xatipo, cardno, storeno, date, nfno, nfse;

DROP TABLE IF EXISTS sqldados.TMP4_24746;
CREATE TEMPORARY TABLE sqldados.TMP4_24746 /*4*/
SELECT *
FROM sqldados.TMP2_24746
UNION
SELECT *
FROM sqldados.TMP3_24746
ORDER BY xatipo, cardno, storeno, date, pdvno, nfno, nfse;

DROP TABLE IF EXISTS sqldados.TMP6_24746;
CREATE TEMPORARY TABLE sqldados.TMP6_24746 /*6*/
SELECT 0               AS tipo,
       'Sang_Dinheiro' AS descricao,
       storeno,
       SUM(cash_other) AS valor
FROM sqldados.pdvcx
WHERE date BETWEEN 20200616 AND 20200616
  AND (storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pdvno = 0 OR 0 = 0)
GROUP BY storeno
UNION
SELECT 3               AS tipo,
       'Sang_Cartao'   AS descricao,
       storeno,
       SUM(card_other) AS valor
FROM sqldados.pdvcx
WHERE date BETWEEN 20200616 AND 20200616
  AND (storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pdvno = 0 OR 0 = 0)
GROUP BY storeno
UNION
SELECT 7                AS tipo,
       'Sang_Cheque'    AS descricao,
       storeno,
       SUM(cpdue_other) AS valor
FROM sqldados.pdvcx
WHERE date BETWEEN 20200616 AND 20200616
  AND (storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pdvno = 0 OR 0 = 0)
GROUP BY storeno
UNION
SELECT 8                AS tipo,
       'Sang_Duplicata' AS descricao,
       storeno,
       SUM(dup_other)   AS valor
FROM sqldados.pdvcx
WHERE date BETWEEN 20200616 AND 20200616
  AND (storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pdvno = 0 OR 0 = 0)
GROUP BY storeno
UNION
SELECT 10                 AS tipo,
       'Sang_Ticket'      AS descricao,
       storeno,
       SUM(tickets_other) AS valor
FROM sqldados.pdvcx
WHERE date BETWEEN 20200616 AND 20200616
  AND (storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pdvno = 0 OR 0 = 0)
GROUP BY storeno
UNION
SELECT 1                    AS tipo,
       'Sang_ContaCorrente' AS descricao,
       storeno,
       SUM(ctxa_other)      AS valor
FROM sqldados.pdvcx
WHERE date BETWEEN 20200616 AND 20200616
  AND (storeno IN (1, 2, 3, 4, 5, 6, 7, 10))
  AND (pdvno = 0 OR 0 = 0)
GROUP BY storeno;
DROP TABLE IF EXISTS sqldados.TMP7_24746;
CREATE TEMPORARY TABLE sqldados.TMP7_24746 /*7*/
SELECT @item := 0;

DROP TABLE IF EXISTS sqldados.TMP8_24746;
CREATE TEMPORARY TABLE sqldados.TMP8_24746 /*8*/
SELECT pxb.storeno                                                    AS lj,
       pxb.eordno                                                     AS pedido,
       pxb.pdvno                                                      AS pdv,
       cast(pxb.date AS DATE)                                         AS data,
       pxb.nfno                                                       AS nf,
       pxb.nfse                                                       AS sr,
       SEC_TO_TIME(pxb.time)                                          AS hora,
       pxb.cardno                                                     AS nro,
       pxb.xatype                                                     AS tp,
       ifnull(LPAD(card.sname, 8, ' '), LPAD(query1.string, 8, ' '))  AS tipos,
       pxb.autorz                                                     AS autoriz,
       pxb.amtot                                                      AS parcela,
       RPAD(MID(IFNULL(eordrk.remarks__480, ' '), 441, 480), 40, ' ') AS obs
FROM sqldados.TMP4_24746           pxb
  LEFT JOIN sqldados.ecfpre
	      ON (ecfpre.storeno = pxb.storeno AND ecfpre.ordno = pxb.eordno AND ecfpre.status = 3)
  LEFT JOIN sqldados.emp
	      ON (emp.no = pxb.empno)
  LEFT JOIN sqldados.custp
	      ON (custp.no = pxb.custno)
  LEFT JOIN sqldados.paym
	      ON (paym.no = pxb.paymno)
  LEFT JOIN sqldados.cartph
	      ON (cartph.xanoUso = pxb.xano)
  LEFT JOIN sqldados.card
	      ON (card.no = pxb.cardno)
  LEFT JOIN sqldados.query1
	      ON (query1.no_short = pxb.xatipo)
  LEFT JOIN sqldados.TMP6_24746 AS T6
	      ON (pxb.storeno = T6.storeno)
  LEFT JOIN sqldados.eordrk
	      ON (eordrk.storeno = pxb.storeno AND eordrk.ordno = pxb.eordno)
WHERE (pxb.xatipo IN (3))
  AND (cartph.cartpno = 0 OR 0 = 0)
  AND CASE 'N' WHEN 'S' THEN custp.name = '*' ELSE TRUE END
ORDER BY 8, pxb.storeno, pxb.pdvno, Tipo, pxb.date, pxb.nfno, pxb.nfse;

DROP TABLE IF EXISTS sqldados.TMP9_24746;
CREATE TEMPORARY TABLE sqldados.TMP9_24746
SELECT T8.*
FROM sqldados.TMP8_24746 AS T8
GROUP BY 11, nro, lj, Data, nf, sr;

SELECT LPAD(@item := @item + 1, 4, ' ') AS item, T9.*
FROM sqldados.TMP9_24746 AS T9
WHERE CASE '2'
	WHEN '0'
	  THEN TRUE
	WHEN '1'
	  THEN Nro IN (198, 199)
	WHEN '2'
	  THEN Tipos LIKE '%LINK%'
	WHEN '3'
	  THEN Tipos NOT LIKE '%LINK%' AND Nro NOT IN (198, 199)
      END
ORDER BY 8

