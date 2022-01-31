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
 (1,'DIVERSO','SDFSD','111','SDF',1,1,NULL,2,0,0,'999999999'),
 (2,'NCR - MAIANGA','ncr@gmail.com','333345','Luanda',1,2,NULL,2,0,0,'5000345'),
 (3,'ZETA SOFT','zeta@gmail.com','930175149','AVENIDA VAN-DUNEM LOY ',1,1,NULL,2,0,0,'3434545');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entradaproduto`
--

/*!40000 ALTER TABLE `entradaproduto` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entradaprodutoitem`
--

/*!40000 ALTER TABLE `entradaprodutoitem` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `factura`
--

/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `facturaitem`
--

/*!40000 ALTER TABLE `facturaitem` DISABLE KEYS */;
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
 (4,'CRÉDITO',0,0,1),
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `liquidardivida`
--

/*!40000 ALTER TABLE `liquidardivida` DISABLE KEYS */;
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
 (2,'Regime Transitórios','M00',1),
 (3,'Transmissão de Bens e serviços não sujeita','M02',1),
 (4,'IVA-Regime de não sujeita','M04',1),
 (5,'Isento Artigo 12.º a) do CIVA','M10',1),
 (6,'Isento Artigo 12.º b) do CIVA','M11',1),
 (7,'Isento Artigo 12.º c) do CIVA','M12',1),
 (8,'Isento Artigo 12.º d) do CIVA','M13',1),
 (9,'Isento Artigo 12.º e) do CIVA','M14',1),
 (10,'Isento Artigo 12.º f) do CIVA','M15',1),
 (11,'Isento Artigo 12.º g) do CIVA','M16',1),
 (12,'Isento Artigo 12.º h) do CIVA','M17',1),
 (13,'Isento Artigo 12.º i) do CIVA','M18',1),
 (14,'Isento Artigo 12.º j) do CIVA','M19',1),
 (15,'Isento Artigo 12.º k) do CIVA','M20',1),
 (16,'Isento Artigo 15.º 1 a) do CIVA','M30',1),
 (17,'Isento Artigo 15.º 1 b) do CIVA','M31',1),
 (18,'Isento Artigo 15.º 1 c) do CIVA','M32',1),
 (19,'Isento Artigo 15.º 1 d) do CIVA','M33',1),
 (20,'Isento Artigo 15.º 1 e) do CIVA','M34',1),
 (21,'Isento Artigo 15.º 1 f) do CIVA','M35',1),
 (22,'Isento Artigo 15.º 1 g) do CIVA','M36',1),
 (23,'Isento Artigo 15.º 1 h) do CIVA','M37',1),
 (24,'Isento Artigo 12.º  i) do CIVA) do CIVA','M38',1),
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movimento`
--

/*!40000 ALTER TABLE `movimento` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movimentoitem`
--

/*!40000 ALTER TABLE `movimentoitem` DISABLE KEYS */;
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
 (1,'FACTURA CREDITO',1),
 (2,'PROFORMA',1),
 (3,'FACTURA RECIBO',1),
 (4,'RECIBO',1),
 (5,'NOTA CREDITO',1),
 (6,'NOTA DEBITO',1);
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
 (34,'LICENÇA DIFINITIVA DO SOFT VENDA+','','',1,1,0,0,0,'2020-07-23',1,0,0,0,0,1,180000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,1,0,''),
 (35,'TESTE DE SISTEMA','','',4,5,0,0,0,'2020-08-09',1,0,0,0,0,1,10000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,1,18,''),
 (36,'dgfhjuio','','',1,1,1,1,0,'2020-09-19',1,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',4,1,0,0,''),
 (37,'Tenes Zara','','',1,1,0,1,1,'2020-09-21',1,18,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,4,'');
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
 (5,'Regime Transitório'),
 (6,'Regime de Não Sujeição '),
 (7,'Lei n.º 7/19 de 24 de Abril)');
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
