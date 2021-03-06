-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.58


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema grest
--

CREATE DATABASE IF NOT EXISTS grest;
USE grest;

--
-- Definition of table `ano`
--

DROP TABLE IF EXISTS `ano`;
CREATE TABLE `ano` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Ano` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ano`
--

/*!40000 ALTER TABLE `ano` DISABLE KEYS */;
INSERT INTO `ano` (`Codigo`,`Ano`) VALUES 
 (1,2019),
 (2,2018),
 (3,2020);
/*!40000 ALTER TABLE `ano` ENABLE KEYS */;


--
-- Definition of table `armazem`
--

DROP TABLE IF EXISTS `armazem`;
CREATE TABLE `armazem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  `Localizacao` varchar(45) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `Data` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_armazem` (`IdEstado`),
  CONSTRAINT `fk_IdEstado_armazem` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `armazem`
--

/*!40000 ALTER TABLE `armazem` DISABLE KEYS */;
INSERT INTO `armazem` (`Id`,`Designacao`,`Localizacao`,`IdEstado`,`Data`) VALUES 
 (1,'LOJA','LOJA',1,'2019-08-15'),
 (2,'A - ENTRADA','VIANA',1,'2019-10-31 10:39:20');
/*!40000 ALTER TABLE `armazem` ENABLE KEYS */;


--
-- Definition of table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado` (`IdEstado`),
  CONSTRAINT `fk_IdEstado` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categoria`
--

/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`Id`,`Designacao`,`IdEstado`) VALUES 
 (1,'DIVERSOS',1),
 (2,'aaaa',3),
 (3,'TESTE',1),
 (4,'TESTE SISTEMA',1);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;


--
-- Definition of table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  `Morada` varchar(45) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `IdTipoCliente` int(11) DEFAULT NULL,
  `Data` date DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `LimiteCredito` double NOT NULL DEFAULT '0',
  `ValorCarteira` double NOT NULL DEFAULT '0',
  `Nif` varchar(45) DEFAULT '999999999',
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_cliente` (`IdEstado`),
  KEY `fk_ITipoCliente_cliente` (`IdTipoCliente`),
  KEY `fk_IdUsuario_cliente` (`IdUsuario`),
  CONSTRAINT `fk_IdEstado_cliente` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdUsuario_cliente` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `fk_ITipoCliente_cliente` FOREIGN KEY (`IdTipoCliente`) REFERENCES `tipocliente` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cliente`
--

/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`Id`,`Nome`,`Email`,`Contacto`,`Morada`,`IdEstado`,`IdTipoCliente`,`Data`,`IdUsuario`,`LimiteCredito`,`ValorCarteira`,`Nif`) VALUES 
 (1,'DIVERSO','SDFSD','111','SDF',1,1,NULL,2,0,-220480,'999999999'),
 (2,'NCR - MAIANGA','ncr@gmail.com','333345','Luanda',1,2,NULL,2,0,0,'5000345'),
 (3,'ZETA SOFT','zeta@gmail.com','930175149','AVENIDA VAN-DUNEM LOY ',1,1,NULL,2,0,-11400,'3434545');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;


--
-- Definition of table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
CREATE TABLE `empresa` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Nif` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  `Endereco` varchar(45) DEFAULT NULL,
  `WebSite` varchar(45) DEFAULT NULL,
  `InfoConta` varchar(255) DEFAULT NULL,
  `IdTipoRegime` int(11) DEFAULT NULL,
  `Loja` varchar(45) DEFAULT NULL,
  `Logotipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empresa`
--

/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` (`Id`,`Nome`,`Nif`,`Email`,`Contacto`,`Endereco`,`WebSite`,`InfoConta`,`IdTipoRegime`,`Loja`,`Logotipo`) VALUES 
 (1,'ZETASOFT','4344334','zetasoft@gmail.com','930175149','Kilamba.K,Av.Pedro C.Vandum Loy','ww.zetasoft.co.ao','BFA: A006.0006.0000.9713.3592.3019.8\nATLANTICO:A006.0055.0000.2929.0654.1018.5',4,'Luanda','Condominio.png');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;


--
-- Definition of table `encomenda`
--

DROP TABLE IF EXISTS `encomenda`;
CREATE TABLE `encomenda` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  `LocalEntrega` varchar(45) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `IdCliente` int(11) DEFAULT NULL,
  `Data` datetime DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_encomenda_1_idx` (`IdCliente`),
  KEY `fk_encomenda_2_idx` (`IdUsuario`),
  CONSTRAINT `fk_encomenda_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encomenda_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `encomenda`
--

/*!40000 ALTER TABLE `encomenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `encomenda` ENABLE KEYS */;


--
-- Definition of table `encomendaitem`
--

DROP TABLE IF EXISTS `encomendaitem`;
CREATE TABLE `encomendaitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdEncomenda` int(11) NOT NULL,
  `IdProduto` int(11) NOT NULL,
  `Preco` double NOT NULL,
  `Qtd` int(11) NOT NULL,
  `SubTotal` double NOT NULL,
  `Iva` double NOT NULL,
  `Lote` varchar(45) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Desconto` double NOT NULL,
  `IdEstado` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id`),
  KEY `fk_encomendaitem_1_idx` (`IdEncomenda`),
  KEY `fk_encomendaitem_2_idx` (`IdProduto`),
  CONSTRAINT `fk_encomendaitem_1` FOREIGN KEY (`IdEncomenda`) REFERENCES `encomenda` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encomendaitem_2` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `encomendaitem`
--

/*!40000 ALTER TABLE `encomendaitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `encomendaitem` ENABLE KEYS */;


--
-- Definition of table `entradaproduto`
--

DROP TABLE IF EXISTS `entradaproduto`;
CREATE TABLE `entradaproduto` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdFornecedor` int(11) NOT NULL,
  `IdUsuario` int(11) NOT NULL,
  `ValorTotal` double NOT NULL,
  `DataFactura` date NOT NULL,
  `IdFormaPagamento` int(11) NOT NULL,
  `TemDivida` tinyint(4) NOT NULL,
  `Data` date NOT NULL,
  `IdEstado` int(11) NOT NULL,
  `NumFactura` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_entradaproduto_1` (`IdFormaPagamento`),
  KEY `FK_entradaproduto_2` (`IdEstado`),
  KEY `FK_entradaproduto_3` (`IdUsuario`),
  KEY `FK_entradaproduto_4` (`IdFornecedor`),
  CONSTRAINT `FK_entradaproduto_1` FOREIGN KEY (`IdFormaPagamento`) REFERENCES `formapagamento` (`Id`),
  CONSTRAINT `FK_entradaproduto_2` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `FK_entradaproduto_3` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `FK_entradaproduto_4` FOREIGN KEY (`IdFornecedor`) REFERENCES `fornecedor` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entradaproduto`
--

/*!40000 ALTER TABLE `entradaproduto` DISABLE KEYS */;
INSERT INTO `entradaproduto` (`Id`,`IdFornecedor`,`IdUsuario`,`ValorTotal`,`DataFactura`,`IdFormaPagamento`,`TemDivida`,`Data`,`IdEstado`,`NumFactura`) VALUES 
 (1,1,2,0,'2020-01-31',4,1,'2020-01-10',1,'43534'),
 (2,1,2,0,'2020-01-31',4,1,'2020-01-13',1,'54645'),
 (3,1,1,0,'2020-05-31',4,1,'2020-05-21',1,'4675'),
 (4,1,1,0,'2020-07-14',1,0,'2020-07-14',1,'0000D'),
 (5,1,1,0,'2020-07-23',1,0,'2020-07-23',1,'0000D'),
 (6,1,1,0,'2020-08-09',1,0,'2020-08-09',1,'0000D'),
 (7,1,1,0,'2020-09-01',4,1,'2020-09-21',1,'77778');
/*!40000 ALTER TABLE `entradaproduto` ENABLE KEYS */;


--
-- Definition of table `entradaprodutoitem`
--

DROP TABLE IF EXISTS `entradaprodutoitem`;
CREATE TABLE `entradaprodutoitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdEntrada` int(11) NOT NULL,
  `IdProduto` int(11) NOT NULL,
  `Qtd` double DEFAULT NULL,
  `PrecoVenda` double NOT NULL,
  `CodBara` varchar(45) NOT NULL,
  `DataExpiracao` date DEFAULT NULL,
  `IdArmazem` int(11) NOT NULL,
  `PrecoCompra` double DEFAULT NULL,
  `QtdControler` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_entradaprodutoitem_1` (`IdProduto`),
  KEY `FK_entradaprodutoitem_2` (`IdArmazem`),
  CONSTRAINT `FK_entradaprodutoitem_1` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`),
  CONSTRAINT `FK_entradaprodutoitem_2` FOREIGN KEY (`IdArmazem`) REFERENCES `armazem` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entradaprodutoitem`
--

/*!40000 ALTER TABLE `entradaprodutoitem` DISABLE KEYS */;
INSERT INTO `entradaprodutoitem` (`Id`,`IdEntrada`,`IdProduto`,`Qtd`,`PrecoVenda`,`CodBara`,`DataExpiracao`,`IdArmazem`,`PrecoCompra`,`QtdControler`) VALUES 
 (1,1,28,0,5000,'1715033453','2020-01-31',2,50000,50),
 (2,1,28,0,5000,'1715033453','2020-01-31',1,50000,50),
 (3,2,29,61,45445,'45654645','2020-01-31',1,687,67),
 (4,2,30,3,4545,'7663','2020-01-31',1,5656,6),
 (5,3,31,43,10000,'5463463','2020-05-31',2,12,56),
 (6,3,32,58,12000,'877','2020-05-31',2,0,67),
 (7,3,32,52,12000,'877','2020-05-31',1,0,21),
 (8,3,31,0,10000,'5463463','2020-05-31',1,12,13),
 (9,4,33,0,0,'33974',NULL,1,0,NULL),
 (10,5,34,0,0,'34974',NULL,1,0,NULL),
 (11,6,35,0,0,'35974',NULL,1,0,NULL),
 (12,7,37,58,20000,'445454545','2022-09-30',2,5000,58);
/*!40000 ALTER TABLE `entradaprodutoitem` ENABLE KEYS */;


--
-- Definition of table `estado`
--

DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `estado`
--

/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` (`Id`,`Designacao`) VALUES 
 (1,'Activado'),
 (2,'Desactivado'),
 (3,'Eliminado'),
 (4,'Concluir'),
 (5,'Devolucao'),
 (6,'Conzinha'),
 (7,'Ocupado'),
 (8,'Rectificacao'),
 (9,'A Liquidar'),
 (10,'Liquidado');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;


--
-- Definition of table `fabricante`
--

DROP TABLE IF EXISTS `fabricante`;
CREATE TABLE `fabricante` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_fabricante` (`IdEstado`),
  CONSTRAINT `fk_IdEstado_fabricante` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fabricante`
--

/*!40000 ALTER TABLE `fabricante` DISABLE KEYS */;
INSERT INTO `fabricante` (`Id`,`Nome`,`IdEstado`) VALUES 
 (1,'DIVERSOS',1),
 (2,'teste',3),
 (3,'aaaaa',3),
 (4,'RESTAURANTE',1),
 (5,'TESTE SISTEMA',1),
 (6,'HHH',3);
/*!40000 ALTER TABLE `fabricante` ENABLE KEYS */;


--
-- Definition of table `factura`
--

DROP TABLE IF EXISTS `factura`;
CREATE TABLE `factura` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdCliente` int(11) NOT NULL DEFAULT '1',
  `IdUsuario` int(11) NOT NULL,
  `IdFormaPagamento` int(11) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  `Data` datetime NOT NULL,
  `ValorEntregue` double NOT NULL,
  `ValorMulticaixa` double NOT NULL,
  `Troco` double NOT NULL,
  `TotalApagar` double NOT NULL,
  `TotalDesconto` double NOT NULL,
  `Obs` varchar(45) DEFAULT NULL,
  `NomeCliente` varchar(100) NOT NULL DEFAULT '  ',
  `TipoFactura` varchar(100) NOT NULL DEFAULT 'Venda',
  `IdSerie` varchar(45) DEFAULT NULL,
  `IdMoeda` int(11) DEFAULT NULL,
  `IdAno` varchar(45) DEFAULT NULL,
  `FacturaRefence` varchar(45) DEFAULT NULL,
  `Hash` text,
  `SubTotal` double DEFAULT NULL,
  `TotalIVA` double DEFAULT NULL,
  `NextFactura` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_factura_1` (`IdFormaPagamento`),
  KEY `FK_factura_2` (`IdUsuario`),
  KEY `fk_factura_3_idx` (`IdMoeda`),
  CONSTRAINT `FK_factura_1` FOREIGN KEY (`IdFormaPagamento`) REFERENCES `formapagamento` (`Id`),
  CONSTRAINT `FK_factura_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `fk_factura_3` FOREIGN KEY (`IdMoeda`) REFERENCES `moeda` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `factura`
--

/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` (`Id`,`IdCliente`,`IdUsuario`,`IdFormaPagamento`,`IdEstado`,`Data`,`ValorEntregue`,`ValorMulticaixa`,`Troco`,`TotalApagar`,`TotalDesconto`,`Obs`,`NomeCliente`,`TipoFactura`,`IdSerie`,`IdMoeda`,`IdAno`,`FacturaRefence`,`Hash`,`SubTotal`,`TotalIVA`,`NextFactura`) VALUES 
 (1,1,1,3,3,'2020-08-27 09:06:47',0,180000,0,180000,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'YOTm','Yz0+OQCNCLOW4dtxfpusT9ex71g4PcmIEr+mJEGTNPkhSE9+GJCeSl3IkPC07xrDU9hWcK1JeURBzD9cIUbikZWd02Dp74JvbdXhQhSp/F3dyrA3mZ7cka8DT4Q75ylTjdZmAf+9oeffbqiz1k1StTRfg3NYDRimnWCvRysnmbA=',180000,0,'FR Z002020/1'),
 (2,1,1,3,3,'2020-09-06 03:06:20',0,20520,0,20520,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'D2Mr','DboAkx4isM2NdkWek9ouMiB7xZfAXhrkrj6y7EmXVFQTxH4UCS5L2gCVJ9FWg2PdMINh2OR5+W3T0jgdud5VL1csdeUJ33Gb19ZpGhIk+Bg9caZdkKt9/BgZbY9GjDfQkRGKuUkzDYPuPAJtwl6WdU09Ba8i/oudUx0AvvDgqEg=',20000,2520,'FR Z002020/2'),
 (3,3,1,1,3,'2020-09-08 01:13:34',50000,0,5200,44800,0,NULL,'ZETA SOFT','Venda',NULL,1,NULL,'mesd','m2AQt2xnLieKXr6qQjIlsw80XMcKtudqkDQuz4fDD5jURGnMfLPkB0MQFbho9VotsWcCBHDLJpp9+++4qt7qe0Q5eXqPL8YCSWETx2Y5blKcIMrh4ZtGVC05JzZ6VWSyydp3gbNVVvDFobDzKss+duQD2/TTZQ76kVvIMu6OpuQ=',42000,2800,'FR Z002020/3'),
 (4,1,1,1,3,'2020-09-08 01:56:00',33000,0,200,32800,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'fxwl','f2i7x+sYYhxZrVTbFM2vwqrF9Wwljbl2AW/Evh/5xYIkBjImcIXPbgg0WyDyVTC4A4Ul0t20rKCam9XtT/4UY0FP7aacZ1OiyNzj0L6Qvvp2ZIsQ4BIKDlkh3q9SWieRaoYxQAuVu24oeLB/wivNU3LozCHTvkPfYqoGzzb4NkQ=',30000,2800,'FR Z002020/4'),
 (5,3,1,1,1,'2020-09-08 02:09:07',80000,0,4192.7,75807.3,0,NULL,'ZETA SOFT','Venda',NULL,1,NULL,'fbDY','feBfMhygN6b+X4XLT8taD2vMCHZRKnY83Z56tB09ZTibB2OR1CMgBvEPHkxtQEKfd995yY6wiUbG+9j9b4Si51mGApQ9/sanLZ1YelK5HTuFN43Cvn4ymy4Yz2c61r6F4ewEDZdVskfTF7SymvDTtaK9Bi9PjqlobRionJUe63k=',69445,6362.3,'FR Z002020/5'),
 (6,1,1,1,3,'2020-09-09 01:00:54',30000,0,0,18240,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'J+fR','JIowumLntW++jLgpfas+fTazw/+2btR/e3mF1OpkOZXY/+w98WtAYPqU505mDowChmNW4uLUIo9QQSq9PrCbQxGhnIGmj+I74Vcv3yvOEP/JIAhQARMTB49mfL6Nxrr0jaKnQq7OI2Hq7x/xZoPd5feRf9gtKtkO5deD1x1h18c=',20000,2240,'FR Z002020/6'),
 (7,1,1,1,3,'2020-09-09 01:03:41',200000,0,37760,162240,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'K0Ex','KBmHp9RhRb0O0zFDp2sWEVPiAkno2Jx2b6BAgx2KmZPSi22+MGuhG3uGmC0uDrKQ0a8CL6sHSucPayBkCKaLjhlJeCfiElHHAv9talRtlkrMRczXd/+NgZlrHL097CLmcMrAI7Hm+v+ZJeTSUS/1Z7DDsi8NG4szbIQiawPKDm0=',200000,2240,'FR Z002020/7'),
 (8,1,1,1,1,'2020-09-09 01:04:47',200000,0,47000,153000,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'Y8J4','YAajUzuuVQ8h/xB0NGUKJpNpVq2Jyw4OiNzKlgSAbqA7knwzQwodpcvdOwlDwJFlqgFou8H5NJ+97q03wmbaKJanlSCyQjuiiGUbN5OXgSsAlwsRQp2uQlnPxX6/nUmjHi8vk94W/9tuCRKLQHcuP6Of/bDVs9PTn5w4P0HLayE=',190000,0,'FR Z002020/8'),
 (9,1,1,1,1,'2020-09-09 01:06:53',198240,0,0,198240,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'hTrY','hrCVdIEveMTVW9OPWmKPrGhakatteIYfSf4pyucu0luDL54l0wK4JyQLv69aMU8lbWW+5GfOO7KYYV4v85PByKwD5fgUMLPU9IxkerrCqCcIEHI33oUJc8sTjiqjMQOdVnfRtin7IgHufw7a5NfhkBkFO08KieRxEU9LJb8nRNQ=',200000,2240,'FR Z002020/9'),
 (10,1,1,1,3,'2020-09-09 01:08:54',18240,0,0,18240,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'Hpwb','HdYiq6iq/lpMC5o9p0dzwM8oz5X2deb5U+5M2FQEQGG3rfvtFC6BrhicwgV11swr9kQZzCvRJG/oBnV0GM9uHCk9kbfWr2V5urpF0Nes7a7nCmZ8XRpHk+l6ZWSoTJP4rNcYf0tlp1oN8EIAk46Ww+oe7ZsUSeD49mxCt/2eoiI=',20000,2240,'FR Z002020/10'),
 (11,1,1,1,3,'2020-09-09 01:05:32',22800,0,0,22800,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'gTxI','gAzpcROi/mTWTvrHwoNNxJgY4HwcUgIZKmoPC8aLEcvfQGLUEqNhVV7RNor16a/liWFhpYBBJt6EusMDP5oRtbgQaTHVY+VNWG05uZ9Es6UqRxHwqbb/BOdXR9JWoSDygtYBqVeORutVR7r9FD0eZkqfYdpm3uur0Jbk8ePLW9k=',20000,2800,'FR Z002020/11'),
 (12,1,1,1,3,'2020-09-09 01:13:28',11400,0,0,11400,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'FI7x','FBgUIqA8fMI+yMXEGNzr7mf9s3doQXx2TLzEOICRDpe1CEm5fq4aaESKY4xdcFxMokIdvI/pC8VlK3Yc9sk71LiW05OeHYO6Y58OsDwySM5zbo6E8DHZ5cYWuypCNHTBUopODg40Z8yID9mQ5B38jzUvQrKU9BiGtKoyVsJ8fik=',20000,1400,'FR Z002020/12'),
 (13,3,1,1,3,'2020-09-09 01:15:15',11400,0,0,11400,0,NULL,'ZETA SOFT','Venda',NULL,1,NULL,'bYZq','bwy3Tt6u1iYod/C98OvwZvZmvXacH7qTKKPyfSv75HW1P+/NqxpZ860Uzdp59CmvSVzT2fIupr7fJOarxZ4THwiP9JGbFSGf911XuBkSU2sFizBbqdqy/0WFrxbOi1U5Zlzpr/WA9oKLBBrHQR2zeLNSMQewGbiDjJYT5ueGFlo=',20000,1400,'FR Z002020/13'),
 (14,3,1,1,8,'2020-09-09 01:17:40',11400,0,0,11400,0,NULL,'ZETA SOFT','Venda',NULL,1,NULL,'T78u','TfSmhtO/G573R9xjAfGM8dInDOxCutuR/ilx35mftBSWpu+RgiJCcux40HlpSdfPhDhKcVQVkdPtjEBHjTqxiCyagp3+sT9m3pAlufxCSlpxBFcb566AhVJXmw8A/nE5undyz3y9EuSEUOvr5wW91UIyzHVtRMmDQh9YgBYxlP0=',20000,1400,'FR Z002020/14'),
 (15,1,1,4,1,'2020-09-21 01:32:17',0,0,0,0.024,0,NULL,'DIVERSO','Venda Credito',NULL,1,NULL,'bXoo','bdDnSGyRgdXhP/asRBF3okURnR+lmDokgluGcbey9Guo3J5EQqC9k8uWp1orD7nu4Oxq1AaNnuW5SXpc3DrSDhiRGcty5na/ipTd+OSAcUWJIgg65qvXmXTg5tXad8d94MhXQyxQHduu0NJ62uQkksIoRrbZKbLjsfz4Wxxe9kQ=',0.024,0,'FT Z002020/15'),
 (16,1,1,1,1,'2020-09-21 01:06:45',30000,0,4320,25680,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'MQRD','MXUJI5R0gaQO4KX23cfrR4qJH5PM/PDtdY3Gi0mCjteD5iuqJLAo6itGq/A0VQBh1uRIIBstzSapZsUljRpYffxLlam9wVhKrJBMxfplShHsJxbNRwo4DyhnSr1cbm05DVC5sMdIHWkMPl2XrgObNEmBfRJS3yHzgeWQ6I6k+1o=',24000,1680,'FR Z002020/16'),
 (17,1,1,1,3,'2020-09-21 01:08:19',30000,0,4320,25680,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'OpMj','Ogs8mQFPd5pUCpFR40onMDK8VLULyRjrMkC/gghA5U+IroOJHlE5CZrepVQzYbyThRjRUGhfsLcIXIyg/HH2iLE5oYIwUcYHmAspP20acaA3gAoSWzHRP5txMD7BPptB2Ux0I0BY2QTtsBQRSgclKFDNxpG9XDXBvyYaPIuldbQ=',24000,1680,'FR Z002020/17'),
 (18,1,1,1,1,'2020-09-21 01:18:51',70000,0,8320,61680,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'kdhJ','kuSTJTDiqndkL/lD7U2ph3hzfBydhjJy7hBy12D+vYMBPvuxhOk1Ml8TApU22SVCp4NYfqDauimpxmWU/ZrtOsffleTxL2fX+apikdVPRM8LrMWNFrdb1uipjoeyLd4u9UDViseNZyfx996LY/DCKzctGoiTLxI/XobD98MvxOI=',60000,1680,'FR Z002020/18'),
 (19,1,1,1,3,'2020-09-21 01:23:24',90000,0,4320,85680,0,NULL,'DIVERSO','Venda',NULL,1,NULL,'luLG','lmg4ahf9SMuq1EZg86F7L2Qka1ECmvGaIIMPLn0Rke1KqEjFYi7Slb/wjMBa+MoSxRFOU9qOeNr/gknTenrR0VbHztnL7sbY1o1U2F9NMAwzcPIBy1iGSV9yMdX7oxwpLk7qKuMKa4PGnQX6CzQ6cIjLiT6NCoUv1Keya4l2tkU=',84000,1680,'FR Z002020/19'),
 (20,1,1,1,1,'2020-09-21 01:24:52',0,0,0,37680,0,NULL,'DIVERSO','Perfoma',NULL,1,NULL,'GgXw','GINgl+7bIngfPkYEO+uEXyF1XSEOU9w7oGgNmH8MhVgKAf/vCU6KpIVHTaYXF0SZPXx83eOYw/vQS3thXbAPqlvwoEHdFMcBq8qsgDM6w7CTYvkk1Z15ruSB93fufbL1ysS4l/1sOUMegfhsUeeWrCfKrxom9e9QYThFFR2KkIw=',36000,1680,'FT Z002020/20');
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;


--
-- Definition of table `facturaitem`
--

DROP TABLE IF EXISTS `facturaitem`;
CREATE TABLE `facturaitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdFactura` int(11) NOT NULL,
  `IdProduto` int(11) NOT NULL,
  `Preco` double NOT NULL,
  `Qtd` int(11) NOT NULL,
  `SubTotal` double NOT NULL,
  `Iva` double NOT NULL,
  `Lote` varchar(45) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Desconto` double NOT NULL,
  `IdEstado` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `facturaitem`
--

/*!40000 ALTER TABLE `facturaitem` DISABLE KEYS */;
INSERT INTO `facturaitem` (`Id`,`IdFactura`,`IdProduto`,`Preco`,`Qtd`,`SubTotal`,`Iva`,`Lote`,`Total`,`Desconto`,`IdEstado`) VALUES 
 (1,1,34,180000,1,180000,0,'34974',180000,0,1),
 (2,2,31,20000,1,20000,2520,'5463463',20520,2000,1),
 (3,3,31,20000,1,20000,2800,'5463463',22800,0,3),
 (4,3,32,12000,1,12000,0,'877',12000,0,3),
 (5,3,35,10000,1,10000,0,'35974',10000,0,3),
 (6,4,31,20000,1,20000,2800,'5463463',22800,0,3),
 (7,4,35,10000,1,10000,0,'35974',10000,0,3),
 (8,5,29,45445,1,45445,6362.3,'45654645',51807.3,0,3),
 (9,5,32,12000,2,24000,0,'877',24000,0,1),
 (10,6,31,20000,1,20000,2240,'5463463',18240,4000,3),
 (11,7,31,20000,1,20000,2240,'5463463',18240,4000,3),
 (12,7,34,180000,1,180000,0,'34974',144000,36000,1),
 (13,8,34,180000,1,180000,0,'34974',144000,36000,1),
 (14,8,35,10000,1,10000,0,'35974',9000,1000,1),
 (15,9,31,20000,1,20000,2240,'5463463',18240,4000,1),
 (16,9,34,180000,1,180000,0,'34974',180000,0,1),
 (17,10,31,20000,1,20000,2240,'5463463',18240,4000,3),
 (18,11,31,20000,1,20000,2800,'5463463',22800,0,3),
 (19,12,31,20000,1,20000,1400,'5463463',11400,10000,3),
 (20,13,31,20000,1,20000,1400,'5463463',11400,10000,3),
 (21,14,31,20000,1,20000,1400,'5463463',11400,10000,1),
 (22,15,32,12,2,24,0,'877',24,0,1),
 (23,16,33,12000,1,12000,1680,'33974',13680,0,1),
 (24,16,32,12000,1,12000,0,'877',12000,0,1),
 (25,17,32,12000,1,12000,0,'877',12000,0,1),
 (26,17,33,12000,1,12000,1680,'33974',13680,0,1),
 (27,18,33,12000,1,12000,1680,'33974',13680,0,1),
 (28,18,32,12000,4,48000,0,'877',48000,0,1),
 (29,19,32,12000,6,72000,0,'877',72000,0,1),
 (30,19,33,12000,1,12000,1680,'33974',13680,0,1),
 (31,20,33,12000,1,12000,1680,'33974',13680,0,1),
 (32,20,32,12000,2,24000,0,'877',24000,0,1);
/*!40000 ALTER TABLE `facturaitem` ENABLE KEYS */;


--
-- Definition of table `formapagamento`
--

DROP TABLE IF EXISTS `formapagamento`;
CREATE TABLE `formapagamento` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  `Cash` tinyint(4) DEFAULT NULL,
  `Multicaixa` tinyint(4) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `formapagamento`
--

/*!40000 ALTER TABLE `formapagamento` DISABLE KEYS */;
INSERT INTO `formapagamento` (`Id`,`Designacao`,`Cash`,`Multicaixa`,`IdEstado`) VALUES 
 (1,'NUMERARIO',1,0,1),
 (3,'MULTICAIXA',0,1,1),
 (4,'CR??DITO',0,0,1),
 (5,'PAGAMENTO DUPLO',1,1,1);
/*!40000 ALTER TABLE `formapagamento` ENABLE KEYS */;


--
-- Definition of table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
CREATE TABLE `fornecedor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Localizacao` varchar(45) DEFAULT NULL,
  `IdTipoFornecedor` int(11) DEFAULT NULL,
  `Data` date DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `NumContribuente` int(11) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_fornecedor` (`IdEstado`),
  KEY `fk_IdTipoFornecedor_fornecedor` (`IdTipoFornecedor`),
  KEY `fk_IdUsuario_fornecedor` (`IdUsuario`),
  CONSTRAINT `fk_IdEstado_fornecedor` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdTipoFornecedor_fornecedor` FOREIGN KEY (`IdTipoFornecedor`) REFERENCES `tipofornecedor` (`Id`),
  CONSTRAINT `fk_IdUsuario_fornecedor` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fornecedor`
--

/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` (`Id`,`Nome`,`Email`,`Localizacao`,`IdTipoFornecedor`,`Data`,`IdUsuario`,`IdEstado`,`NumContribuente`,`Contacto`) VALUES 
 (1,'DIVERSOS','','A',1,'2019-08-16',2,1,23423,'2222222222'),
 (2,'SHOPRITE - PALANCA','inf@shoprite.com','AFRICA DO SUL',1,'2019-11-07',2,1,33333,'2222');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;


--
-- Definition of table `iva`
--

DROP TABLE IF EXISTS `iva`;
CREATE TABLE `iva` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Valor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `iva`
--

/*!40000 ALTER TABLE `iva` DISABLE KEYS */;
INSERT INTO `iva` (`Id`,`Valor`) VALUES 
 (1,'5');
/*!40000 ALTER TABLE `iva` ENABLE KEYS */;


--
-- Definition of table `liquidardivida`
--

DROP TABLE IF EXISTS `liquidardivida`;
CREATE TABLE `liquidardivida` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IdFactura` int(10) unsigned NOT NULL,
  `ValorEntregue` double NOT NULL,
  `Obs` text NOT NULL,
  `DataPagamento` datetime NOT NULL,
  `DataEmissao` datetime NOT NULL,
  `IdUsuario` int(10) unsigned NOT NULL,
  `IdEstado` int(10) unsigned NOT NULL,
  `IdCliente` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `liquidardivida`
--

/*!40000 ALTER TABLE `liquidardivida` DISABLE KEYS */;
INSERT INTO `liquidardivida` (`Id`,`IdFactura`,`ValorEntregue`,`Obs`,`DataPagamento`,`DataEmissao`,`IdUsuario`,`IdEstado`,`IdCliente`) VALUES 
 (1,10,10000,'primeira presta????o 10','2020-09-15 00:00:00','2020-09-15 11:15:22',1,10,1),
 (2,10,8240,'segunda presta????o 10','2020-09-15 00:00:00','2020-09-15 11:15:50',1,10,1);
/*!40000 ALTER TABLE `liquidardivida` ENABLE KEYS */;


--
-- Definition of table `log`
--

DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) DEFAULT NULL,
  `Descricao` varchar(255) DEFAULT NULL,
  `IdUsuario` varchar(255) DEFAULT NULL,
  `Data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `log`
--

/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;


--
-- Definition of table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
CREATE TABLE `mesa` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mesa`
--

/*!40000 ALTER TABLE `mesa` DISABLE KEYS */;
INSERT INTO `mesa` (`Id`,`Designacao`,`IdEstado`) VALUES 
 (1,'Mesa 1',1),
 (2,'Mesa 2',1),
 (3,'Mesa 3',1),
 (4,'Mesa 4',1),
 (5,'Mesa 5',1),
 (6,'Mesa 6',1),
 (7,'Mesa 7',1),
 (8,'Mesa 8',1),
 (9,'Mesa 9',1),
 (10,'Mesa 10',1),
 (11,'Mesa 11',1),
 (12,'Mesa 12',2),
 (13,'Mesa 13',2),
 (14,'Mesa 14',2),
 (15,'Mesa 15',2),
 (16,'Mesa 16',2),
 (17,'Mesa 17',2),
 (18,'Mesa 18',2),
 (19,'Mesa 19',2),
 (20,'Mesa 20',2),
 (21,'Mesa 21',2),
 (22,'Mesa 22',2),
 (23,'Mesa 23',2),
 (24,'Mesa 24',2),
 (25,'Mesa 25',2),
 (26,'Mesa 26',2),
 (27,'Mesa 30',2),
 (28,'Mesa 31',2),
 (29,'Mesa 32',2),
 (30,'Mesa 33',2),
 (31,'Mesa 34',2),
 (32,'Mesa 35',2),
 (33,'Mesa 36',2),
 (34,'Mesa 37',2),
 (35,'Mesa 38',2),
 (36,'Mesa 39',2),
 (37,'Mesa 40',2),
 (38,'Mesa 41',2),
 (39,'Mesa 42',2),
 (40,'Mesa 43',2),
 (41,'Mesa 44',2);
/*!40000 ALTER TABLE `mesa` ENABLE KEYS */;


--
-- Definition of table `moeda`
--

DROP TABLE IF EXISTS `moeda`;
CREATE TABLE `moeda` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT '',
  `Cambio` float DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `moeda`
--

/*!40000 ALTER TABLE `moeda` DISABLE KEYS */;
INSERT INTO `moeda` (`Id`,`Designacao`,`Cambio`) VALUES 
 (1,'AO',1),
 (2,'USD',1),
 (3,'EUR',1);
/*!40000 ALTER TABLE `moeda` ENABLE KEYS */;


--
-- Definition of table `motivo`
--

DROP TABLE IF EXISTS `motivo`;
CREATE TABLE `motivo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(1000) DEFAULT NULL,
  `Codigo` varchar(45) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `motivo`
--

/*!40000 ALTER TABLE `motivo` DISABLE KEYS */;
INSERT INTO `motivo` (`Id`,`Descricao`,`Codigo`,`IdEstado`) VALUES 
 (2,'Regime Transit??rios','M00',1),
 (3,'Transmiss??o de Bens e servi??os n??o sujeita','M02',1),
 (4,'IVA-Regime de n??o sujeita','M04',1),
 (5,'Isento Artigo 12.?? a) do CIVA','M10',1),
 (6,'Isento Artigo 12.?? b) do CIVA','M11',1),
 (7,'Isento Artigo 12.?? c) do CIVA','M12',1),
 (8,'Isento Artigo 12.?? d) do CIVA','M13',1),
 (9,'Isento Artigo 12.?? e) do CIVA','M14',1),
 (10,'Isento Artigo 12.?? f) do CIVA','M15',1),
 (11,'Isento Artigo 12.?? g) do CIVA','M16',1),
 (12,'Isento Artigo 12.?? h) do CIVA','M17',1),
 (13,'Isento Artigo 12.?? i) do CIVA','M18',1),
 (14,'Isento Artigo 12.?? j) do CIVA','M19',1),
 (15,'Isento Artigo 12.?? k) do CIVA','M20',1),
 (16,'Isento Artigo 15.?? 1 a) do CIVA','M30',1),
 (17,'Isento Artigo 15.?? 1 b) do CIVA','M31',1),
 (18,'Isento Artigo 15.?? 1 c) do CIVA','M32',1),
 (19,'Isento Artigo 15.?? 1 d) do CIVA','M33',1),
 (20,'Isento Artigo 15.?? 1 e) do CIVA','M34',1),
 (21,'Isento Artigo 15.?? 1 f) do CIVA','M35',1),
 (22,'Isento Artigo 15.?? 1 g) do CIVA','M36',1),
 (23,'Isento Artigo 15.?? 1 h) do CIVA','M37',1),
 (24,'Isento Artigo 12.??  i) do CIVA) do CIVA','M38',1),
 (25,'  ','',1);
/*!40000 ALTER TABLE `motivo` ENABLE KEYS */;


--
-- Definition of table `movimento`
--

DROP TABLE IF EXISTS `movimento`;
CREATE TABLE `movimento` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Obs` varchar(255) DEFAULT NULL,
  `Valor` double DEFAULT NULL,
  `TipoMovimento` varchar(45) DEFAULT NULL,
  `IdFactura` int(11) DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `IdCliente` int(11) DEFAULT NULL,
  `Data` datetime DEFAULT NULL,
  `DataOperacao` date DEFAULT NULL,
  `NextFactura` varchar(255) DEFAULT NULL,
  `IdEstado` int(10) unsigned DEFAULT NULL,
  `Hash` text,
  `Reference` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movimento`
--

/*!40000 ALTER TABLE `movimento` DISABLE KEYS */;
INSERT INTO `movimento` (`Id`,`Obs`,`Valor`,`TipoMovimento`,`IdFactura`,`IdUsuario`,`IdCliente`,`Data`,`DataOperacao`,`NextFactura`,`IdEstado`,`Hash`,`Reference`) VALUES 
 (6,NULL,0,'C',1,1,1,'2020-09-08 09:58:44','2020-09-08','NC Z012020/6',1,NULL,NULL),
 (7,NULL,0,'C',2,1,1,'2020-09-08 10:03:00','2020-09-08','NC Z012020/7',1,NULL,NULL),
 (8,'Rectifica????o',22800,'C',3,1,3,'2020-09-08 10:05:04','2020-09-08','NC Z012020/8',1,'FL2COFCxLbl6WryyEgkujlo1UTmHXNgvB/Zt2yLkSTod8zXtY144/zrV0C1MJ3DUdEPn0CyKA/lzooNXohWWd4TBLleVT8o5+Z0jgUjHWkNNYXhUO1uAtxQS9RWtPKrdVc3hvNFP/llJ/JLU2V2h90sWOjYfzysSIg2RLMuQCp4=','Fljg'),
 (9,'Rectifica????o',12000,'C',3,1,1,'2020-09-08 00:48:55','2020-09-08','NC Z012020/9',1,'XXgF9R7HyjoRMFAbC44vLGsYa8Re2gj4pNO49ZgFfKOzrSLll+hb0iwzbCz1f4vZfNRWSSlOhMu1r4j7vxK9dhT4sfZR7/PzZhY8yvyCG8kfTN9CzaeflUv9OJ/zvvC+hFZ1WTA69xYQSoX0gKwppCmq2c1XJS+QM0YNJgQ8zjk=','XoLj'),
 (10,'Rectifica????o',12000,'C',3,1,1,'2020-09-08 01:03:34','2020-09-08','NC Z012020/10',1,'cu5VgBJph7eo2zcYkSFqXxlKu95NYsL2d94tc/2dLNTlhHqs5tEqN7dtdvKpAYuEqIc4gvDU7Xfz9aO6zCNqvKxaX9bS/x6Rk92g0pHSShatAM/B8aba3Z1rnE1LtFn97csFo2aWTjaxxK1dPWWD/4L0fINc8r625d56dWBWDe8=','ceXL'),
 (11,'Rectifica????o',12000,'C',3,1,1,'2020-09-08 01:11:56','2020-09-08','NC Z012020/11',1,'K3ypath70Tri8LngLd/gMF1e3qg40OqbaBj1DPVWzn59E44bNxAZoSxgWHGZY+QOVDH2RoTx1WiKHtKMtRJ40Rq/mg831P4sjtp6gg/OxR9ibvpv2zCsHVxk2qTLFj8j5YAwXpVbWX4jjfC2EPhG2yEk02++6az+5zTLluBbp3k=','KrMq'),
 (12,'Rectifica????o',12000,'C',3,1,1,'2020-09-08 01:18:40','2020-09-08','NC Z012020/12',1,'d40uhSWBHz27ttMCSsyLJ+Q7fZjCB/FFAXVLjoRDpcyNrpOIuT98er4DTER5bZX2j9INZnxL7S7rfbcze+Ww8nmMkOixqsaRoNPTmjSTfsdnFsTcyx78qOPxqhQKTUzj27cCb0NS8C08F2KwICFsbigB+zisuPUu+dOIJmR5Joc=','d2JF'),
 (13,'Rectifica????o',12000,'C',3,1,1,'2020-09-08 01:22:06','2020-09-08','NC Z012020/13',1,'JVAQs3Q92PYIA835AuShkkTwTV7coR0LN+y8DPcz9qHfU4TY9UVrlC22/Vh1Ht0k93y8W4/VuHhOtM/jTPKRkiL/al/kJRwK6TAdGh32CJSAgtcxaDU4By4rOwWPNHSE8E6hFPuhVFhgYHM3GqP9JH73UnmH+xREK8nSeBTomXY=','JYk0'),
 (14,'Rectifica????o',12000,'C',3,1,1,'2020-09-08 01:28:04','2020-09-08','NC Z012020/14',1,'gZbbuB8zpQs/ICxWhdQHWeXFpWtsue9xW6ZBhSVwGFQDbD9Dfax59HyKakcJG/xyyyiiTJx4TKtXkhTgj8t9BwByLhxrsjwAWMoePCiWfH+wH3RBrM1YB8FHiBDjXJj+2nYUBbaY0QT72XPvtazd3HeMrNgVj82cq03+t79Ss3c=','gsW9'),
 (15,'Rectifica????o',10000,'C',3,1,1,'2020-09-08 01:48:31','2020-09-08','NC Z012020/15',1,'henHmPeztBzHONbY/Q66/kQ8M/HfZwh75Jl4FUSyVE1Ybhz1XqI5ZDoKDVQPB+/KSxrPR3zItEjAnIe94/lPaZxR39fX3+Kkb0hk16qP8mFE1CDJb1FKUG7p+UUpm1ZN/fkNV+1TbYSMM9b2vbR51YLOv5Hrh3Hi3HJ/7SNSOCU=','hz/h'),
 (16,'Rectifica????o',22800,'C',4,1,1,'2020-09-08 01:56:48','2020-09-08','NC Z012020/16',1,'jOG5Jt/ZjhFDxee7doLpKeONCCQfdwLLNV2n1iirkrl/0Tn4MObJz39chS0AiPGjsvLs1V/u4pv3ueNTrKQBW6m0ABdBjRKFVewiH+tHsdrxCnT10WZIaQjwEttGGxSe3Ku5cZquH0m0/C4WvMpDLL/vfaWM1nuEQO23cvuBG5g=','jFKL'),
 (17,'Rectifica????o',10000,'C',4,1,1,'2020-09-08 01:58:26','2020-09-08','NC Z012020/17',1,'C/uKp/LbXEo21zg8w8SWMKGJMMS5didzxxxNbI/Y5NSUwrCfQZZ7RoZuVzzlAgIlOhJ09LD7fQIFq2Kcg51Cza7+PpvPfePl5pK7CJUgeO24D0xU5yWNgwj+5jOmvOS8YocUKvVQWz66l7oPe8qZtvFmJ0U2nYnclyyV8nugRU0=','CoMd'),
 (18,'Rectifica????o',51807.3,'C',5,1,1,'2020-09-08 02:09:35','2020-09-08','NC Z012020/18',1,'gN2wzDsqZrIJNX+SDn54aY7uGS6R49E24RqhYxCTI84zceGy2+XQGQBPz9fe/8EdWJO6zPj8ud9yvTtFRd/r1QDWoXNtF6aLCR1QD4l2p/I8o/4hfTfs6Nd1oXVBAj75nXRP+8deBl2/Un8F78cMTtPI0zNn6VKXJEo6mmIF2mg=','gIaE'),
 (19,'Rectifica????o',18240,'C',10,1,1,'2020-09-09 03:06:11','2020-09-09','NC Z012020/19',1,'Q+dQg5MUFrIesFignDT3STBzNMIbh1EX/NeCngq43LHK/VsE39M1y6jxlTSFfZJXTPDvp/8FWZ1o+fdHmK059hQ84SyeMp560elBN6UWZz179rrnt7vsKgYIu71LDiMt7UGRAjDp+E8NpgMhw8+qlqaC/xRPpTtsvP8xu5MM+IE=','QISE'),
 (20,'Rectifica????o',22800,'C',11,1,1,'2020-09-09 03:36:11','2020-09-09','NC Z012020/20',1,'NTaUoZGKkuBDO+AlHNpzeEEIVAKO9AA9twp7ZdVlfWtOA7mR8N/FWMyU5TxGLU6LP/nghVn5T/s9mV9soygR++0bugD3W6O7FsCwEwcsuaL0R1KrXGjGacGFeZY+6fysMx/xoMQt5AS6u7tJw7ATHHMIb1hQHIKm9rT+lbDNNhk=','NBeA'),
 (21,'Rectifica????o',11400,'C',12,1,1,'2020-09-09 03:47:29','2020-09-09','NC Z012020/21',1,'QSCV4VzcPqf0nsmDmUnLJuvegnI6GgJ1XuE8HhNerwiK0Yy7Qpw0ptBW3ZTlrySPM8tE/88lK27wKqY3xrAlD5TYoMnjBs8/l99kjLYO+DRaWNPAsjvp/RPmB+Co2DjS9tZEPVjsQW6x6Fhd8cAyX+crJ+8Ks0AON5cWO5sXlZA=','QfJJ'),
 (22,'Rectifica????o',18240,'C',6,1,1,'2020-09-09 03:54:07','2020-09-09','NC Z012020/22',1,'Z4/83cLtfJkcUcaACPW4fUl9LRLLzOUYJipAagfwSVZEn6T/pkClnAmUmp0YxcdqTXfwQ9pEL0XLYEwIZ8chBoIcFJmbebi00s2TVPQfCT8L892RsfezllAjiggBldrs9DLC+LHP6fL4LoLZevv9Y/isfd2JUP+vKH6YMvwDxPI=','ZkfU'),
 (23,'Rectifica????o',11400,'C',13,1,1,'2020-09-09 04:17:06','2020-09-09','NC Z012020/23',1,'Y6AXGSpR/WR+qtwCz0EtytFIpqdaLtZrqth7/eAumxrJs368dL3+sKBHt9vSskwATFfhJF3/jfNtzHIRaTA9x6UJ5VUT4M2h/z4z3xYXJTHOHUlu04nb1v4W1Y/rQ7r6KDliHH80Ks0rST8o8MB04EH59IPnz8/zKlxIBjW2uR8=','YRyZ'),
 (24,'Rectifica????o',18240,'C',7,1,1,'2020-09-09 04:27:01','2020-09-09','NC Z012020/24',1,'JYqIYXIRBs3WEf19JOqjnikbQ7AvqlfmgSWl32vkLA8czU2ZF+vMYgR3MZjXKqxaEDmxZ6mlAWTj599+rKKuYmH3e+7Nh7VHdaOfDClVCzj/Lh24akcIrP/KADkyVyshERPZLIAxJ05VzEwwvXxBDh0hy0PM34hIIhxzx4H2ud0=','J3nf'),
 (26,'valor de teste 14',11400,'D',14,1,3,'2020-09-10 11:23:11','2020-09-10','ND Z022020 / 26',NULL,NULL,NULL),
 (27,'teste da factura 7',162240,'D',7,1,1,'2020-09-10 02:13:38','2020-09-10','ND Z022020 / 27',NULL,NULL,NULL),
 (28,'Anula????o',0,'C',7,1,1,'2020-09-11 08:13:52','2020-09-11','NC Z012020/28',NULL,NULL,NULL),
 (29,'Anula????o',25680,'C',17,1,1,'2020-09-21 01:12:14','2020-09-21','NC Z012020/29',NULL,NULL,NULL),
 (30,'Anula????o',85680,'C',19,1,1,'2020-09-21 01:26:59','2020-09-21','NC Z012020/30',NULL,NULL,NULL);
/*!40000 ALTER TABLE `movimento` ENABLE KEYS */;


--
-- Definition of table `movimentoitem`
--

DROP TABLE IF EXISTS `movimentoitem`;
CREATE TABLE `movimentoitem` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Desconto` double DEFAULT NULL,
  `IdMovimento` int(10) unsigned NOT NULL,
  `IdProduto` int(10) unsigned NOT NULL,
  `Preco` double DEFAULT NULL,
  `Qtd` double DEFAULT NULL,
  `SubTotal` double DEFAULT NULL,
  `Iva` double DEFAULT NULL,
  `Lote` varchar(45) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `CodigoBarra` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movimentoitem`
--

/*!40000 ALTER TABLE `movimentoitem` DISABLE KEYS */;
INSERT INTO `movimentoitem` (`Id`,`Desconto`,`IdMovimento`,`IdProduto`,`Preco`,`Qtd`,`SubTotal`,`Iva`,`Lote`,`Total`,`CodigoBarra`) VALUES 
 (7,0,6,34,180000,1,180000,0,'34974',180000,NULL),
 (8,2000,7,31,20000,1,20000,2520,'5463463',20520,NULL),
 (9,0,8,31,20000,1,20000,2800,'5463463',22800,NULL),
 (10,0,9,32,12000,1,12000,0,'877',12000,NULL),
 (11,0,10,32,12000,1,12000,0,'877',12000,NULL),
 (12,0,11,32,12000,1,12000,0,'877',12000,NULL),
 (13,0,12,32,12000,1,12000,0,'877',12000,NULL),
 (14,0,13,32,12000,1,12000,0,'877',12000,NULL),
 (15,0,14,32,12000,1,12000,0,'877',12000,NULL),
 (16,0,15,35,10000,1,10000,0,'35974',10000,NULL),
 (17,0,16,31,20000,1,20000,2800,'5463463',22800,NULL),
 (18,0,17,35,10000,1,10000,0,'35974',10000,NULL),
 (19,0,18,29,45445,1,45445,6362.3,'45654645',51807.3,NULL),
 (20,4000,19,31,20000,1,20000,2240,'5463463',18240,NULL),
 (21,0,20,31,20000,1,20000,2800,'5463463',22800,NULL),
 (22,10000,21,31,20000,1,20000,1400,'5463463',11400,NULL),
 (23,4000,22,31,20000,1,20000,2240,'5463463',18240,NULL),
 (24,10000,23,31,20000,1,20000,1400,'5463463',11400,NULL),
 (25,4000,24,31,20000,1,20000,2240,'5463463',18240,NULL),
 (26,10000,26,31,20000,1,20000,1400,'5463463',11400,NULL),
 (27,4000,27,31,20000,1,20000,2240,'5463463',18240,NULL),
 (28,36000,27,34,180000,1,180000,0,'34974',144000,NULL),
 (29,4000,28,31,20000,1,20000,2240,'5463463',18240,NULL),
 (30,36000,28,34,180000,1,180000,0,'34974',144000,NULL),
 (31,0,29,32,12000,1,12000,0,'877',12000,NULL),
 (32,0,29,33,12000,1,12000,1680,'33974',13680,NULL),
 (33,0,30,32,12000,6,72000,0,'877',72000,NULL),
 (34,0,30,33,12000,1,12000,1680,'33974',13680,NULL);
/*!40000 ALTER TABLE `movimentoitem` ENABLE KEYS */;


--
-- Definition of table `numeracao_documento`
--

DROP TABLE IF EXISTS `numeracao_documento`;
CREATE TABLE `numeracao_documento` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  `Next` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `numeracao_documento`
--

/*!40000 ALTER TABLE `numeracao_documento` DISABLE KEYS */;
INSERT INTO `numeracao_documento` (`Id`,`Designacao`,`Next`) VALUES 
 (1,'FACTURA CREDITO',41),
 (2,'PROFORMA',26),
 (3,'FACTURA RECIBO',139),
 (4,'RECIBO',1),
 (5,'NOTA CREDITO',61),
 (6,'NOTA DEBITO',10);
/*!40000 ALTER TABLE `numeracao_documento` ENABLE KEYS */;


--
-- Definition of table `parametro`
--

DROP TABLE IF EXISTS `parametro`;
CREATE TABLE `parametro` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(255) NOT NULL,
  `Valor` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `parametro`
--

/*!40000 ALTER TABLE `parametro` DISABLE KEYS */;
INSERT INTO `parametro` (`Id`,`Descricao`,`Valor`) VALUES 
 (1,'IMPRIMIR MOSTRAR ASSISTENTE',0),
 (2,'MOSTRAR ANTES DE IMPRIMIR ',1),
 (3,'MODULO SISTEMA ',1),
 (4,'PANEL CONFIG - PRODUTO',1),
 (5,'PANEL PRECO - PRODUTO',1),
 (6,'ARMAZEM PRINCIPAL LOJA',1);
/*!40000 ALTER TABLE `parametro` ENABLE KEYS */;


--
-- Definition of table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE `pedido` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdMesa` int(11) DEFAULT NULL,
  `Data` datetime DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `Nome` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_pedido_1_idx` (`IdMesa`),
  CONSTRAINT `fk_pedido_1` FOREIGN KEY (`IdMesa`) REFERENCES `mesa` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pedido`
--

/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` (`Id`,`IdMesa`,`Data`,`IdEstado`,`Nome`) VALUES 
 (1,11,'2020-01-13 01:42:45',4,'DIVERSO'),
 (2,10,'2020-01-13 01:43:13',1,'DIVERSO');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;


--
-- Definition of table `pedidoitem`
--

DROP TABLE IF EXISTS `pedidoitem`;
CREATE TABLE `pedidoitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdProduto` int(11) DEFAULT NULL,
  `Preco` double DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `IVA` double DEFAULT NULL,
  `DESCONTO` double DEFAULT NULL,
  `Qtd` int(11) DEFAULT NULL,
  `CodBarra` varchar(45) DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `IdPedido` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_pedidoItem_1_idx` (`IdPedido`),
  KEY `fk_pedidoItem_2_idx` (`IdProduto`),
  CONSTRAINT `fk_pedidoItem_1` FOREIGN KEY (`IdPedido`) REFERENCES `pedido` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedidoItem_2` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pedidoitem`
--

/*!40000 ALTER TABLE `pedidoitem` DISABLE KEYS */;
INSERT INTO `pedidoitem` (`Id`,`IdProduto`,`Preco`,`IdEstado`,`IVA`,`DESCONTO`,`Qtd`,`CodBarra`,`IdUsuario`,`IdPedido`) VALUES 
 (1,28,5000,1,700,0,1,'1715033453',2,1),
 (2,28,5000,1,700,0,1,'1715033453',2,2);
/*!40000 ALTER TABLE `pedidoitem` ENABLE KEYS */;


--
-- Definition of table `permissao`
--

DROP TABLE IF EXISTS `permissao`;
CREATE TABLE `permissao` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) NOT NULL,
  `Descricao` text,
  `Menu item` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permissao`
--

/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` (`Id`,`Designacao`,`Descricao`,`Menu item`) VALUES 
 (1,'Produto',NULL,'Produto'),
 (2,'Fornecedor',NULL,'Fornecedor'),
 (3,'Entrada de Stock',NULL,'entradaStock'),
 (4,'Cliente',NULL,'cliente'),
 (5,'Categoria',NULL,''),
 (6,'Armazem',NULL,''),
 (7,'Forma de Pagamento',NULL,''),
 (8,'Forma de Impressao',NULL,''),
 (9,'IPC',NULL,''),
 (10,'Fabricante',NULL,''),
 (11,'Transferencia de Produto',NULL,''),
 (12,'Pagagemento de divida do fornecedor',NULL,''),
 (13,'Pagagemento de divida do cliente',NULL,''),
 (14,'Eliminar Factura',NULL,''),
 (15,'Encomenda',NULL,''),
 (16,'Alterar data de Expiracao',NULL,''),
 (17,'Alterar preco de venda',NULL,''),
 (18,'Alterar codigo de barra',NULL,''),
 (19,'Actualizar stock',NULL,''),
 (20,'Listagem de todos clientes',NULL,'');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;


--
-- Definition of table `permissao_usuario`
--

DROP TABLE IF EXISTS `permissao_usuario`;
CREATE TABLE `permissao_usuario` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IdPermissao` int(10) unsigned NOT NULL,
  `IdUsuario` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permissao_usuario`
--

/*!40000 ALTER TABLE `permissao_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissao_usuario` ENABLE KEYS */;


--
-- Definition of table `produto`
--

DROP TABLE IF EXISTS `produto`;
CREATE TABLE `produto` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(400) NOT NULL,
  `Descricao` varchar(255) DEFAULT NULL,
  `Referencia` varchar(45) DEFAULT NULL,
  `IdCategoria` int(11) NOT NULL,
  `IdFabricante` int(11) NOT NULL,
  `Expira` tinyint(4) NOT NULL,
  `Stocavel` tinyint(4) NOT NULL,
  `iva` tinyint(4) NOT NULL,
  `Data` date NOT NULL,
  `IdUsuario` int(11) NOT NULL,
  `StockMinimo` int(11) NOT NULL,
  `AlertaExpiracao` int(11) DEFAULT NULL,
  `AlertaQuantidade` int(11) DEFAULT NULL,
  `PrasoDevolucao` int(11) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `ValorVenda` double NOT NULL DEFAULT '0',
  `Organizacao` varchar(200) NOT NULL,
  `IdMotivo` int(11) DEFAULT NULL,
  `IdTaxa` int(11) DEFAULT NULL,
  `IsMenuDia` tinyint(4) DEFAULT '1',
  `Garantia` int(11) DEFAULT '1',
  `Imagem` varchar(150) DEFAULT '',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Designacao_UNIQUE` (`Designacao`),
  KEY `fk_IdEstado_produto` (`IdEstado`),
  KEY `fk_IdCategoria_produto` (`IdCategoria`),
  KEY `fk_IdUsuario_produto` (`IdUsuario`),
  CONSTRAINT `fk_IdCategoria_produto` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria` (`Id`),
  CONSTRAINT `fk_IdEstado_produto` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdUsuario_produto` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produto`
--

/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` (`Id`,`Designacao`,`Descricao`,`Referencia`,`IdCategoria`,`IdFabricante`,`Expira`,`Stocavel`,`iva`,`Data`,`IdUsuario`,`StockMinimo`,`AlertaExpiracao`,`AlertaQuantidade`,`PrasoDevolucao`,`IdEstado`,`ValorVenda`,`Organizacao`,`IdMotivo`,`IdTaxa`,`IsMenuDia`,`Garantia`,`Imagem`) VALUES 
 (28,'MASSA PRIMAVERA','','MP-45674',1,1,1,1,0,'2020-01-10',2,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,1,6,''),
 (29,'aaaaa','','',1,1,1,1,1,'2020-01-13',2,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,1,''),
 (30,'bbbbb','','',1,1,1,1,1,'2020-01-13',2,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,1,''),
 (31,'ARROZ','','',1,1,0,0,1,'2020-05-21',1,0,0,0,0,1,20000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),
 (32,'MASSA DONA XEPA','','',1,1,1,1,0,'2020-05-21',1,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',5,1,1,0,''),
 (33,'TOYOTA HACY','','',1,1,0,0,1,'2020-07-14',1,0,0,0,0,1,12000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),
 (34,'LICEN??A DIFINITIVA DO SOFT VENDA+','','',1,1,0,0,0,'2020-07-23',1,0,0,0,0,1,180000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,1,0,''),
 (35,'TESTE DE SISTEMA','','',4,5,0,0,0,'2020-08-09',1,0,0,0,0,1,10000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,1,18,''),
 (36,'dgfhjuio','','',1,1,1,1,0,'2020-09-19',1,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,0,0,''),
 (37,'Tenes Zara','','',1,1,1,1,1,'2020-09-21',1,18,0,5,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,4,'');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;


--
-- Definition of table `serie`
--

DROP TABLE IF EXISTS `serie`;
CREATE TABLE `serie` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL DEFAULT '',
  `Ano` int(11) NOT NULL DEFAULT '0',
  `Status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Codigo`),
  KEY `FK_serie_1` (`Ano`),
  CONSTRAINT `FK_serie_1` FOREIGN KEY (`Ano`) REFERENCES `ano` (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `serie`
--

/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
INSERT INTO `serie` (`Codigo`,`Designacao`,`Ano`,`Status`) VALUES 
 (4,'A',1,1),
 (5,'F',3,2),
 (6,'D',2,1),
 (8,'F',1,1);
/*!40000 ALTER TABLE `serie` ENABLE KEYS */;


--
-- Definition of table `taxa`
--

DROP TABLE IF EXISTS `taxa`;
CREATE TABLE `taxa` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(1000) DEFAULT NULL,
  `Taxa` double DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `taxa`
--

/*!40000 ALTER TABLE `taxa` DISABLE KEYS */;
INSERT INTO `taxa` (`Id`,`Descricao`,`Taxa`,`IdEstado`) VALUES 
 (1,'SEM IVA',0,1),
 (2,'COM IVA',14,1);
/*!40000 ALTER TABLE `taxa` ENABLE KEYS */;


--
-- Definition of table `tipocliente`
--

DROP TABLE IF EXISTS `tipocliente`;
CREATE TABLE `tipocliente` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipocliente`
--

/*!40000 ALTER TABLE `tipocliente` DISABLE KEYS */;
INSERT INTO `tipocliente` (`Id`,`Designacao`) VALUES 
 (1,'Normal'),
 (2,'Especial');
/*!40000 ALTER TABLE `tipocliente` ENABLE KEYS */;


--
-- Definition of table `tipofornecedor`
--

DROP TABLE IF EXISTS `tipofornecedor`;
CREATE TABLE `tipofornecedor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipofornecedor`
--

/*!40000 ALTER TABLE `tipofornecedor` DISABLE KEYS */;
INSERT INTO `tipofornecedor` (`Id`,`Designacao`) VALUES 
 (1,'Empresa'),
 (2,'Individual');
/*!40000 ALTER TABLE `tipofornecedor` ENABLE KEYS */;


--
-- Definition of table `tiposregime`
--

DROP TABLE IF EXISTS `tiposregime`;
CREATE TABLE `tiposregime` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tiposregime`
--

/*!40000 ALTER TABLE `tiposregime` DISABLE KEYS */;
INSERT INTO `tiposregime` (`Id`,`Designacao`) VALUES 
 (4,'Regime Geral'),
 (5,'Regime Transit??rio'),
 (6,'Regime de N??o Sujei????o '),
 (7,'Lei n.?? 7/19 de 24 de Abril)');
/*!40000 ALTER TABLE `tiposregime` ENABLE KEYS */;


--
-- Definition of table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
CREATE TABLE `tipousuario` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipousuario`
--

/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` (`Id`,`Designacao`) VALUES 
 (1,'ADMINISTRATOR'),
 (2,'OPERADOR');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;


--
-- Definition of table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(255) NOT NULL,
  `Usuario` varchar(45) NOT NULL,
  `Senha` varchar(45) NOT NULL,
  `IdTipoUsuario` int(11) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `Data` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  `StatusAcesso` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`Id`,`Nome`,`Usuario`,`Senha`,`IdTipoUsuario`,`IdEstado`,`Data`,`Email`,`Contacto`,`StatusAcesso`) VALUES 
 (1,'ZETA','root','63a9f0ea7bb98050796b649e85481845',1,1,'1997-08-13',NULL,NULL,'CONTA - ACTIVADA');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
