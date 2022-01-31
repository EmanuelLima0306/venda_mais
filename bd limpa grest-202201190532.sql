-- MySQL dump 10.13  Distrib 5.7.34, for Linux (x86_64)
--
-- Host: localhost    Database: grest
-- ------------------------------------------------------
-- Server version	5.7.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ano`
--

DROP TABLE IF EXISTS `ano`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ano` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Ano` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ano`
--

LOCK TABLES `ano` WRITE;
/*!40000 ALTER TABLE `ano` DISABLE KEYS */;
INSERT INTO `ano` VALUES (1,2019),(2,2018),(3,2020),(4,2022);
/*!40000 ALTER TABLE `ano` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `armazem`
--

DROP TABLE IF EXISTS `armazem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `armazem`
--

LOCK TABLES `armazem` WRITE;
/*!40000 ALTER TABLE `armazem` DISABLE KEYS */;
INSERT INTO `armazem` VALUES (1,'LOJA','LOJA',1,'2019-08-15'),(2,'A - ENTRADA','VIANA',1,'2019-10-31 10:39:20');
/*!40000 ALTER TABLE `armazem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `barbeiroitem`
--

DROP TABLE IF EXISTS `barbeiroitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barbeiroitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdFacturaItem` int(11) DEFAULT NULL,
  `IdBarbeiro` int(11) DEFAULT NULL,
  `Taxa` double DEFAULT NULL,
  `QuantidadeItem` double DEFAULT NULL,
  `ValorRemuneracao` double DEFAULT NULL,
  `IdEstado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id`),
  KEY `barbeiroItem_FK` (`IdFacturaItem`),
  KEY `barbeiroItem_FK_1` (`IdBarbeiro`),
  KEY `barbeiroItem_FK_2` (`IdEstado`),
  CONSTRAINT `barbeiroItem_FK` FOREIGN KEY (`IdFacturaItem`) REFERENCES `facturaitem` (`Id`),
  CONSTRAINT `barbeiroItem_FK_1` FOREIGN KEY (`IdBarbeiro`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `barbeiroItem_FK_2` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barbeiroitem`
--

LOCK TABLES `barbeiroitem` WRITE;
/*!40000 ALTER TABLE `barbeiroitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `barbeiroitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caixa`
--

DROP TABLE IF EXISTS `caixa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caixa` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) NOT NULL,
  `dataAbertura` datetime NOT NULL,
  `dataFecho` datetime DEFAULT NULL,
  `valorInicial` double NOT NULL DEFAULT '0',
  `estado` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `caixa_FK` (`idUsuario`),
  CONSTRAINT `caixa_FK` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caixa`
--

LOCK TABLES `caixa` WRITE;
/*!40000 ALTER TABLE `caixa` DISABLE KEYS */;
/*!40000 ALTER TABLE `caixa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cambio`
--

DROP TABLE IF EXISTS `cambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cambio` (
  `Codigo` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IdMoeda` int(11) DEFAULT NULL,
  `Valor` double unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`Codigo`),
  KEY `FK_cambio_1` (`IdMoeda`),
  CONSTRAINT `FK_cambio_1` FOREIGN KEY (`IdMoeda`) REFERENCES `moeda` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cambio`
--

LOCK TABLES `cambio` WRITE;
/*!40000 ALTER TABLE `cambio` DISABLE KEYS */;
INSERT INTO `cambio` VALUES (1,1,1),(2,2,600),(3,3,1);
/*!40000 ALTER TABLE `cambio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado` (`IdEstado`),
  CONSTRAINT `fk_IdEstado` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'DIVERSOS',1);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certificacao`
--

DROP TABLE IF EXISTS `certificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `certificacao` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `NomeEmpresa` varchar(255) DEFAULT NULL,
  `NifProdutorSistema` varchar(255) DEFAULT NULL,
  `Endereco` varchar(255) DEFAULT NULL,
  `PontoReferenca` varchar(255) DEFAULT NULL,
  `EMail` varchar(255) DEFAULT NULL,
  `NifRepresentanteLegal` varchar(255) DEFAULT NULL,
  `NomePrograma` varchar(255) DEFAULT NULL,
  `VersaoPrograma` varchar(255) DEFAULT NULL,
  `NumeroValidacao` varchar(45) DEFAULT NULL,
  `DataValidacao` date DEFAULT NULL,
  `DataExpirar` varchar(45) DEFAULT NULL,
  `telefone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificacao`
--

LOCK TABLES `certificacao` WRITE;
/*!40000 ALTER TABLE `certificacao` DISABLE KEYS */;
INSERT INTO `certificacao` VALUES (1,'ZETASOFT TECNOLOGIA-PRESTAÇÃO DE SERVIÇOS E COMERCIO,LDA','5000437395','RUA PEDRO DE CASTRO VAN-DUNEM LOY N 09','AVENIDA PEDRO DE CASTRO VANDUNE LOY-KILAMBA KIAXI','zetasoft@gmail.com','5000437395','Venda+','2.6','282/AGT/2020','2020-12-23','2022-12-23','994482920');
/*!40000 ALTER TABLE `certificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `Nif` varchar(45) NOT NULL DEFAULT '999999999',
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_cliente` (`IdEstado`),
  KEY `fk_ITipoCliente_cliente` (`IdTipoCliente`),
  KEY `fk_IdUsuario_cliente` (`IdUsuario`),
  CONSTRAINT `fk_ITipoCliente_cliente` FOREIGN KEY (`IdTipoCliente`) REFERENCES `tipocliente` (`Id`),
  CONSTRAINT `fk_IdEstado_cliente` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdUsuario_cliente` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'DIVERSO','diversos@gmail.com','111','Luanda',1,1,NULL,1,0,-220480,'999999999');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Nif` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  `Endereco` varchar(255) DEFAULT NULL,
  `WebSite` varchar(45) DEFAULT NULL,
  `InfoConta` varchar(255) DEFAULT NULL,
  `IdTipoRegime` int(11) DEFAULT NULL,
  `Loja` varchar(45) DEFAULT NULL,
  `Logotipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'SACEL PHARMA, LDA','5484032024','','926300294','RUA 31  - BAIRRO CAZENGA, Nº 1, Município: CAZENGA, Província: LUANDA','','',5,'Luanda','hhdjhaj.png');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encomenda`
--

DROP TABLE IF EXISTS `encomenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  CONSTRAINT `fk_encomenda_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`Id`),
  CONSTRAINT `fk_encomenda_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encomenda`
--

LOCK TABLES `encomenda` WRITE;
/*!40000 ALTER TABLE `encomenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `encomenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encomendaitem`
--

DROP TABLE IF EXISTS `encomendaitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  CONSTRAINT `fk_encomendaitem_1` FOREIGN KEY (`IdEncomenda`) REFERENCES `encomenda` (`Id`),
  CONSTRAINT `fk_encomendaitem_2` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encomendaitem`
--

LOCK TABLES `encomendaitem` WRITE;
/*!40000 ALTER TABLE `encomendaitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `encomendaitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradaproduto`
--

DROP TABLE IF EXISTS `entradaproduto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradaproduto`
--

LOCK TABLES `entradaproduto` WRITE;
/*!40000 ALTER TABLE `entradaproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `entradaproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradaprodutoitem`
--

DROP TABLE IF EXISTS `entradaprodutoitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entradaprodutoitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdEntrada` int(11) NOT NULL,
  `IdProduto` int(11) NOT NULL,
  `Qtd` double DEFAULT NULL,
  `QtdTotal` double DEFAULT NULL,
  `PrecoVenda` double NOT NULL,
  `CodBara` varchar(45) NOT NULL,
  `DataExpiracao` date DEFAULT NULL,
  `IdArmazem` int(11) NOT NULL,
  `PrecoCompra` double DEFAULT NULL,
  `QtdControler` int(10) unsigned DEFAULT NULL,
  `IdEstado` int(11) NOT NULL DEFAULT '1',
  `precoUnitarioCompra` double DEFAULT NULL,
  `lucro` double DEFAULT NULL,
  `margemLucro` double DEFAULT NULL,
  `Lote` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_entradaprodutoitem_1` (`IdProduto`),
  KEY `FK_entradaprodutoitem_2` (`IdArmazem`),
  KEY `entradaprodutoitem_FK` (`IdEstado`),
  CONSTRAINT `FK_entradaprodutoitem_1` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`),
  CONSTRAINT `FK_entradaprodutoitem_2` FOREIGN KEY (`IdArmazem`) REFERENCES `armazem` (`Id`),
  CONSTRAINT `entradaprodutoitem_FK` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradaprodutoitem`
--

LOCK TABLES `entradaprodutoitem` WRITE;
/*!40000 ALTER TABLE `entradaprodutoitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `entradaprodutoitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Activado'),(2,'Desactivado'),(3,'Eliminado'),(4,'Concluir'),(5,'Devolucao'),(6,'Conzinha'),(7,'Ocupado'),(8,'Rectificacao'),(9,'A Liquidar'),(10,'Liquidado'),(11,'Expirado'),(12,'Esgotado'),(13,'Pendente'),(14,'Novo');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `existencia`
--

DROP TABLE IF EXISTS `existencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `existencia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idArmazem` int(11) NOT NULL,
  `idProduto` int(11) DEFAULT NULL,
  `lote` varchar(100) DEFAULT NULL,
  `quantidade` int(11) NOT NULL DEFAULT '0',
  `data` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `existencia_FK` (`idArmazem`),
  KEY `existencia_FK_1` (`idProduto`),
  CONSTRAINT `existencia_FK` FOREIGN KEY (`idArmazem`) REFERENCES `armazem` (`Id`),
  CONSTRAINT `existencia_FK_1` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `existencia`
--

LOCK TABLES `existencia` WRITE;
/*!40000 ALTER TABLE `existencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `existencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fabricante`
--

DROP TABLE IF EXISTS `fabricante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fabricante` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_fabricante` (`IdEstado`),
  CONSTRAINT `fk_IdEstado_fabricante` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabricante`
--

LOCK TABLES `fabricante` WRITE;
/*!40000 ALTER TABLE `fabricante` DISABLE KEYS */;
INSERT INTO `fabricante` VALUES (1,'DIVERSOS',1),(2,'RESTAURANTE',1);
/*!40000 ALTER TABLE `fabricante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `Obs` text,
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
  `cambio` double NOT NULL DEFAULT '0',
  `valor_moeda_estrangeira` double NOT NULL DEFAULT '0',
  `criada_modulo_formacao` tinyint(1) NOT NULL DEFAULT '0',
  `idCaixa` int(10) unsigned DEFAULT NULL,
  `localDescarga` varchar(200) DEFAULT NULL,
  `localCarga` varchar(200) DEFAULT NULL,
  `dataTransporte` varchar(100) DEFAULT NULL,
  `TotalRetencao` double DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `FK_factura_1` (`IdFormaPagamento`),
  KEY `FK_factura_2` (`IdUsuario`),
  KEY `fk_factura_3_idx` (`IdMoeda`),
  KEY `factura_FK` (`idCaixa`),
  CONSTRAINT `FK_factura_1` FOREIGN KEY (`IdFormaPagamento`) REFERENCES `formapagamento` (`Id`),
  CONSTRAINT `FK_factura_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `factura_FK` FOREIGN KEY (`idCaixa`) REFERENCES `caixa` (`id`),
  CONSTRAINT `fk_factura_3` FOREIGN KEY (`IdMoeda`) REFERENCES `moeda` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturaitem`
--

DROP TABLE IF EXISTS `facturaitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `Retencao` double DEFAULT '0',
  `lucro` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturaitem`
--

LOCK TABLES `facturaitem` WRITE;
/*!40000 ALTER TABLE `facturaitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `facturaitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formapagamento`
--

DROP TABLE IF EXISTS `formapagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formapagamento` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  `Cash` tinyint(4) DEFAULT NULL,
  `Multicaixa` tinyint(4) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formapagamento`
--

LOCK TABLES `formapagamento` WRITE;
/*!40000 ALTER TABLE `formapagamento` DISABLE KEYS */;
INSERT INTO `formapagamento` VALUES (1,'NUMERARIO',1,0,1),(3,'MULTICAIXA',0,1,1),(4,'CRÉDITO',0,0,1),(5,'PAGAMENTO DUPLO',1,1,1);
/*!40000 ALTER TABLE `formapagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Localizacao` varchar(45) DEFAULT NULL,
  `IdTipoFornecedor` int(11) DEFAULT NULL,
  `Data` date DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `Contacto` varchar(45) DEFAULT NULL,
  `NumContribuente` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_IdEstado_fornecedor` (`IdEstado`),
  KEY `fk_IdTipoFornecedor_fornecedor` (`IdTipoFornecedor`),
  KEY `fk_IdUsuario_fornecedor` (`IdUsuario`),
  CONSTRAINT `fk_IdEstado_fornecedor` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdTipoFornecedor_fornecedor` FOREIGN KEY (`IdTipoFornecedor`) REFERENCES `tipofornecedor` (`Id`),
  CONSTRAINT `fk_IdUsuario_fornecedor` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'DIVERSOS','','A',1,'2019-08-16',1,1,'2222222222',NULL);
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iva`
--

DROP TABLE IF EXISTS `iva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iva` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Valor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iva`
--

LOCK TABLES `iva` WRITE;
/*!40000 ALTER TABLE `iva` DISABLE KEYS */;
INSERT INTO `iva` VALUES (1,'5');
/*!40000 ALTER TABLE `iva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liquidardivida`
--

DROP TABLE IF EXISTS `liquidardivida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `RetensaoNaFonte` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liquidardivida`
--

LOCK TABLES `liquidardivida` WRITE;
/*!40000 ALTER TABLE `liquidardivida` DISABLE KEYS */;
/*!40000 ALTER TABLE `liquidardivida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) DEFAULT NULL,
  `Descricao` text,
  `IdUsuario` int(10) unsigned DEFAULT NULL,
  `Data` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  `Descricao` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'Ficheiro',NULL),(2,'Operação',NULL),(3,'Tabela',NULL),(4,'Relatório',NULL);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menuitem`
--

DROP TABLE IF EXISTS `menuitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menuitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT NULL,
  `Descricao` text,
  `IdMenu` int(11) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_menuitem_1_idx` (`IdMenu`),
  KEY `fk_menuitem_2_idx` (`IdEstado`),
  CONSTRAINT `fk_menuitem_1` FOREIGN KEY (`IdMenu`) REFERENCES `menuitem` (`id`),
  CONSTRAINT `fk_menuitem_2` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitem`
--

LOCK TABLES `menuitem` WRITE;
/*!40000 ALTER TABLE `menuitem` DISABLE KEYS */;
INSERT INTO `menuitem` VALUES (1,'Ficheiro','',1,1),(2,'Produto',NULL,1,1),(4,'Fornecedor',NULL,1,1),(5,'Cliente',NULL,1,1),(6,'Entrada de Produto',NULL,1,1),(7,'Operação',NULL,7,1),(8,'Transferência de Produto',NULL,7,1),(9,'Pagamento de Divida',NULL,7,1),(10,'Fornecedor',NULL,9,1),(11,'Eliminar Factura','',7,1),(12,'Actualização de Stock',NULL,7,1),(13,'Alterar data de Expiração',NULL,7,1),(14,'Alterar preço de venda',NULL,7,1),(15,'Alterar Código de Barra',NULL,7,1),(16,'Devolução',NULL,7,1),(17,'Documentos Rectificativo',NULL,7,1),(18,'Nota de Crédito',NULL,17,1),(19,'Nota debito',NULL,17,1),(20,'Anulação Documento',NULL,17,1),(21,'Liquidar Divida',NULL,17,1),(22,'Relatório',NULL,22,1),(23,'Cliente',NULL,22,1),(24,'Factura',NULL,23,1),(25,'Pagas',NULL,24,1),(26,'Dividas',NULL,24,1),(27,'Devolvidas','',24,1),(28,'Pagas Por Cliente',NULL,24,1),(29,'Encomenda','',23,1),(30,'Todos',NULL,23,1),(31,'Fornecedor',NULL,22,1),(32,'Encomenda',NULL,31,1),(33,'Todos',NULL,31,1),(34,'Entrada',NULL,31,1),(35,'Entrada Por Fonecedor',NULL,31,1),(36,'Categoria',NULL,22,1),(37,'Armazém','',22,1),(38,'Fabricante',NULL,22,1),(39,'Lista de Movimento',NULL,22,1),(40,'Nota de Entrega de Encomenda',NULL,22,1),(41,'Documentos Retificativos',NULL,22,1),(42,'Tabela',NULL,42,1),(43,'Categoria',NULL,42,1),(44,'Cambio',NULL,42,1),(45,'Armazém',NULL,42,1),(46,'Forma de Pagamento',NULL,42,1),(47,'Forma de Impressão',NULL,42,1),(48,'IPC',NULL,42,1),(49,'Fabricante',NULL,42,1),(50,'IVA',NULL,42,1),(51,'Motivo de Isenção',NULL,50,1),(52,'Taxa',NULL,50,1),(53,'Saft',NULL,50,1),(54,'Numeração Documento',NULL,50,1),(55,'Mesa','',42,1),(56,'Gráfico',NULL,56,1),(57,'Stock',NULL,56,1),(58,'Código de Barra',NULL,57,1),(59,'Produto',NULL,57,1),(60,'Categoria',NULL,57,1),(61,'Fornecedor',NULL,57,1),(62,'Fabricante',NULL,57,1),(63,'Produto detalhado',NULL,57,1),(64,'Venda',NULL,56,1),(65,'Produto',NULL,64,1),(66,'Cliente',NULL,64,1),(67,'Balanço Anual',NULL,64,1),(68,'Balanço Mensal',NULL,64,1),(69,'Sistema',NULL,69,1),(70,'Usuário',NULL,69,1),(71,'Permissão',NULL,69,1),(72,'Empresa',NULL,69,1),(73,'Lista de Usuario',NULL,69,1),(74,'Backup do Sistema',NULL,69,1),(75,'Ajuda',NULL,75,1),(76,'Manual do SIstema',NULL,75,1),(77,'Empresa',NULL,75,1),(78,'Produto',NULL,22,1),(79,'Stock',NULL,78,1),(80,'Por Armazem',NULL,78,1),(81,'Stock em Baixa',NULL,78,1),(82,'Etiqueta',NULL,78,1),(83,'Expirado',NULL,78,1),(84,'Caixa',NULL,22,1),(85,'Entrada e Stock',NULL,22,1),(86,'Agupar Entradas nas Vendas',NULL,42,1),(87,'Preste a Expirar',NULL,78,1),(88,'Preste a Terminar',NULL,78,1),(90,'Log de Acesso',NULL,69,1),(91,'Fecho de Caixas',NULL,7,1),(92,'Adicionar QTD Pela Tela de Produto',NULL,42,1),(93,'Permitir Fecho de Caixa ao Operador',NULL,42,1);
/*!40000 ALTER TABLE `menuitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menuitemusuario`
--

DROP TABLE IF EXISTS `menuitemusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menuitemusuario` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdMenuitem` int(11) DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_menuitemusuario_1_idx` (`IdMenuitem`),
  KEY `fk_menuitemusuario_2_idx` (`IdUsuario`),
  KEY `fk_menuitemusuario_3_idx` (`IdEstado`),
  CONSTRAINT `fk_menuitemusuario_1` FOREIGN KEY (`IdMenuitem`) REFERENCES `menuitem` (`id`),
  CONSTRAINT `fk_menuitemusuario_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `fk_menuitemusuario_3` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2782 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitemusuario`
--

LOCK TABLES `menuitemusuario` WRITE;
/*!40000 ALTER TABLE `menuitemusuario` DISABLE KEYS */;
INSERT INTO `menuitemusuario` VALUES (1,1,1,1),(2,2,1,1),(3,4,1,1),(4,5,1,1),(5,6,1,1),(6,7,1,1),(7,8,1,1),(8,9,1,1),(9,10,1,1),(10,11,1,1),(11,12,1,1),(12,13,1,1),(13,14,1,1),(14,15,1,1),(15,16,1,1),(16,17,1,1),(17,18,1,1),(18,19,1,1),(19,20,1,1),(20,21,1,1),(21,22,1,1),(22,23,1,1),(23,24,1,1),(24,25,1,1),(25,26,1,1),(26,27,1,1),(27,28,1,1),(28,29,1,1),(29,30,1,1),(30,31,1,1),(31,32,1,1),(32,33,1,1),(33,34,1,1),(34,35,1,1),(35,36,1,1),(36,37,1,1),(37,38,1,1),(38,39,1,1),(39,40,1,1),(40,41,1,1),(41,42,1,1),(42,43,1,1),(43,44,1,1),(44,45,1,1),(45,46,1,1),(46,47,1,1),(47,48,1,1),(48,49,1,1),(49,50,1,1),(50,51,1,1),(51,52,1,1),(52,53,1,1),(53,54,1,1),(54,55,1,1),(55,56,1,1),(56,57,1,1),(57,58,1,1),(58,59,1,1),(59,60,1,1),(60,61,1,1),(61,62,1,1),(62,63,1,1),(63,64,1,1),(64,65,1,1),(65,66,1,1),(66,67,1,1),(67,68,1,1),(68,69,1,1),(69,70,1,1),(70,71,1,1),(71,72,1,1),(72,73,1,1),(73,74,1,1),(74,75,1,1),(75,76,1,1),(76,77,1,1),(77,78,1,1),(78,79,1,1),(79,80,1,1),(80,81,1,1),(81,82,1,1),(82,83,1,1),(329,84,1,1),(330,85,1,1),(2699,86,1,1),(2700,87,1,1),(2701,88,1,1),(2727,86,1,1),(2728,87,1,1),(2729,88,1,1),(2754,90,1,1),(2761,91,1,1),(2768,92,1,1),(2775,93,1,1);
/*!40000 ALTER TABLE `menuitemusuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesa` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa`
--

LOCK TABLES `mesa` WRITE;
/*!40000 ALTER TABLE `mesa` DISABLE KEYS */;
INSERT INTO `mesa` VALUES (1,'Mesa 1',1),(2,'Mesa 2',1),(3,'Mesa 3',1),(4,'Mesa 4',1),(5,'Mesa 5',1),(6,'Mesa 6',1),(7,'Mesa 7',1),(8,'Mesa 8',1),(9,'Mesa 9',1),(10,'Mesa 10',1),(11,'Mesa 11',1),(12,'Mesa 12',2),(13,'Mesa 13',2),(14,'Mesa 14',2),(15,'Mesa 15',2),(16,'Mesa 16',2),(17,'Mesa 17',2),(18,'Mesa 18',2),(19,'Mesa 19',2),(20,'Mesa 20',2),(21,'Mesa 21',2),(22,'Mesa 22',2),(23,'Mesa 23',2),(24,'Mesa 24',2),(25,'Mesa 25',2),(26,'Mesa 26',2),(27,'Mesa 30',2),(28,'Mesa 31',2),(29,'Mesa 32',2),(30,'Mesa 33',2),(31,'Mesa 34',2),(32,'Mesa 35',2),(33,'Mesa 36',2),(34,'Mesa 37',2),(35,'Mesa 38',2),(36,'Mesa 39',2),(37,'Mesa 40',2),(38,'Mesa 41',2),(39,'Mesa 42',2),(40,'Mesa 43',2),(41,'Mesa 44',2);
/*!40000 ALTER TABLE `mesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moeda`
--

DROP TABLE IF EXISTS `moeda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moeda` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT '',
  `Cambio` float DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moeda`
--

LOCK TABLES `moeda` WRITE;
/*!40000 ALTER TABLE `moeda` DISABLE KEYS */;
INSERT INTO `moeda` VALUES (1,'AOA',1),(2,'USD',1),(3,'EUR',1);
/*!40000 ALTER TABLE `moeda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motivo`
--

DROP TABLE IF EXISTS `motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `motivo` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(1000) DEFAULT NULL,
  `Codigo` varchar(45) DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motivo`
--

LOCK TABLES `motivo` WRITE;
/*!40000 ALTER TABLE `motivo` DISABLE KEYS */;
INSERT INTO `motivo` VALUES (2,'IVA – Regime Simplificado','M00',1),(3,'Transmissão de bens e serviço não sujeita','M02',1),(4,'IVA – Regime de Exclusão','M04',1),(6,'Isento nos termos da alínea b) do nº1 do artigo 12.º\ndo CIVA','M11',1),(7,'Isento nos termos da alínea c) do nº1 do artigo 12.º\ndo CIVA','M12',1),(8,'Isento nos termos da alínea d) do nº1 do artigo 12.º\ndo CIVA','M13',1),(9,'Isento nos termos da alínea e) do nº1 do artigo 12.º\ndo CIVA','M14',1),(10,'Isento nos termos da alínea f) do nº1 do artigo 12.º\ndo CIVA','M15',1),(11,'Isento nos termos da alínea g) do nº1 do artigo 12.º\ndo CIVA','M16',1),(12,'Isento nos termos da alínea h) do nº1 do artigo 12.º\ndo CIVA','M17',1),(13,'Isento nos termos da alínea i) do nº1 artigo 12.º do\nCIVA','M18',1),(14,'Isento nos termos da alínea j) do nº1 do artigo 12.º\ndo CIVA','M19',1),(15,'Isento nos termos da alínea k) do nº1 do artigo 12.º\ndo CIVA','M20',1),(16,'Isento nos termos da alínea l) do nº1 do artigo 12.º\ndo CIVA','M21',1),(17,'Isento nos termos da alínea m) do artigo 12.º do\nCIVA','M22',1),(18,'Isento nos termos da alínea n) do artigo 12.º do CIVA','M23',1),(19,'Isento nos termos da alínea 0) do artigo 12.º do CIVA','M24',1),(20,'Isento nos termos da alinea a) do nº1 do artigo 14.º','M80',1),(21,'Isento nos termos da alinea b) do nº1 do artigo 14.º','M81',1),(22,'Isento nos termos da alinea c) do nº1 do artigo 14.º','M82',1),(23,'Isento nos termos da alinea d) do nº1 do artigo 14.º','M83',1),(24,'Isento nos termos da alínea e) do nº1 do artigo 14.º','M84',1),(25,'  ','',1),(26,'Isento nos termos da alinea a) do nº2 do artigo 14.º','M85',1),(27,'Isento nos termos da alinea b) do nº2 do artigo 14.º','M86',1),(28,'Isento nos termos da alínea a) do artigo 15.º do CIVA','M30',1),(29,'Isento nos termos da alínea b) do artigo 15.º do CIVA','M31',1),(30,'Isento nos termos da alínea c) do artigo 15.º do CIVA','M32',1),(31,'Isento nos termos da alínea d) do artigo 15.º do CIVA','M33',1),(32,'Isento nos termos da alínea e) do artigo 15.º do CIVA','M34',1),(33,'Isento nos termos da alínea f) do artigo 15.º do CIVA','M35',1),(34,'Isento nos termos da alínea g) do artigo 15.º do CIVA','M36',1),(35,'Isento nos termos da alínea h) do artigo 15.º do CIVA','M37',1),(36,'Isento nos termos da alínea i) do artigo 15.º do CIVA','M38',1),(37,'Isento nos termos da alinea a) do nº1 do artigo 16.º','M90',1),(38,'Isento nos termos da alinea b) do nº1 do artigo 16.º','M91',1),(39,'Isento nos termos da alinea c) do nº1 do artigo 16.º','M92',1),(40,'Isento nos termos da alinea d) do nº1 do artigo 16.º','M93',1),(41,'Isento nos termos da alinea e) do nº1 do artigo 16.º','M94',1);
/*!40000 ALTER TABLE `motivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimento`
--

DROP TABLE IF EXISTS `movimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `criada_modulo_formacao` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimento`
--

LOCK TABLES `movimento` WRITE;
/*!40000 ALTER TABLE `movimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentoitem`
--

DROP TABLE IF EXISTS `movimentoitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentoitem`
--

LOCK TABLES `movimentoitem` WRITE;
/*!40000 ALTER TABLE `movimentoitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimentoitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `numeracao_documento`
--

DROP TABLE IF EXISTS `numeracao_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `numeracao_documento` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  `Next` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `numeracao_documento`
--

LOCK TABLES `numeracao_documento` WRITE;
/*!40000 ALTER TABLE `numeracao_documento` DISABLE KEYS */;
INSERT INTO `numeracao_documento` VALUES (1,'FACTURA CREDITO',1),(2,'PROFORMA',1),(3,'FACTURA RECIBO',1),(4,'RECIBO',1),(5,'NOTA CREDITO',1),(6,'NOTA DEBITO',1);
/*!40000 ALTER TABLE `numeracao_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametro`
--

DROP TABLE IF EXISTS `parametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parametro` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(255) NOT NULL,
  `Valor` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametro`
--

LOCK TABLES `parametro` WRITE;
/*!40000 ALTER TABLE `parametro` DISABLE KEYS */;
INSERT INTO `parametro` VALUES (1,'IMPRIMIR DIREITO',1),(2,'MOSTRAR ANTES DE IMPRIMIR ',1),(3,'MODULO SISTEMA ',1),(4,'PANEL CONFIG - PRODUTO',1),(5,'PANEL PRECO - PRODUTO',1),(6,'ARMAZEM PRINCIPAL LOJA',1),(7,'FORMAÇÃO',2),(8,'ADD  QTD OU  PRECO - A PARTIR DA TELA DE PRODUTO',2),(9,'BACKUP-AUTOMATICO',1),(10,'PERMITIR ABERTURA E FECHO DO CAIXA',1),(11,'NOME IMPRESSORA CUZINHA',1),(12,'TELA TOUCH',1),(13,'DEFINIR Nº DE FOLHA(Nº DE VIA DE FACTURA) À SER IMPRESSA',2),(14,'AGRUPAR ENTRADAS NAS VENDAS',1),(15,'TIPO FACTURA PRINCIPAL (1,2,3)',1);
/*!40000 ALTER TABLE `parametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdMesa` int(11) DEFAULT NULL,
  `Data` datetime DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  `Nome` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_pedido_1_idx` (`IdMesa`),
  CONSTRAINT `fk_pedido_1` FOREIGN KEY (`IdMesa`) REFERENCES `mesa` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidoitem`
--

DROP TABLE IF EXISTS `pedidoitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `Subtotal` double DEFAULT '0',
  `Retencao` double DEFAULT '0',
  `Total` double DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `fk_pedidoItem_1_idx` (`IdPedido`),
  KEY `fk_pedidoItem_2_idx` (`IdProduto`),
  CONSTRAINT `fk_pedidoItem_1` FOREIGN KEY (`IdPedido`) REFERENCES `pedido` (`Id`),
  CONSTRAINT `fk_pedidoItem_2` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidoitem`
--

LOCK TABLES `pedidoitem` WRITE;
/*!40000 ALTER TABLE `pedidoitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidoitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permanente`
--

DROP TABLE IF EXISTS `permanente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permanente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idCaixa` int(10) unsigned DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `idCategoria` int(11) DEFAULT NULL,
  `idEstado` int(11) DEFAULT NULL,
  `totalMulticaixa` double DEFAULT '0',
  `totalNumerario` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `permanente_FK` (`idCaixa`),
  KEY `permanente_FK_1` (`idUsuario`),
  KEY `permanente_FK_2` (`idCategoria`),
  KEY `permanente_FK_3` (`idEstado`),
  CONSTRAINT `permanente_FK` FOREIGN KEY (`idCaixa`) REFERENCES `caixa` (`id`),
  CONSTRAINT `permanente_FK_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `permanente_FK_2` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`Id`),
  CONSTRAINT `permanente_FK_3` FOREIGN KEY (`idEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permanente`
--

LOCK TABLES `permanente` WRITE;
/*!40000 ALTER TABLE `permanente` DISABLE KEYS */;
/*!40000 ALTER TABLE `permanente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permanenteitem`
--

DROP TABLE IF EXISTS `permanenteitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permanenteitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProduto` int(11) DEFAULT NULL,
  `totalSaida` double DEFAULT '0',
  `totalPerca` double DEFAULT '0',
  `preco` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `idPermanente` int(11) DEFAULT NULL,
  `idEstado` int(11) DEFAULT NULL,
  `stock` double DEFAULT '0',
  `qtdVendida` double DEFAULT '0',
  `taxaIva` double DEFAULT '0',
  `totalVendido` double DEFAULT '0',
  `qtdEntrada` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `permanenteItem_FK` (`idProduto`),
  KEY `permanenteItem_FK_1` (`idPermanente`),
  KEY `permanenteItem_FK_2` (`idEstado`),
  CONSTRAINT `permanenteItem_FK` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`Id`),
  CONSTRAINT `permanenteItem_FK_1` FOREIGN KEY (`idPermanente`) REFERENCES `permanente` (`id`),
  CONSTRAINT `permanenteItem_FK_2` FOREIGN KEY (`idEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permanenteitem`
--

LOCK TABLES `permanenteitem` WRITE;
/*!40000 ALTER TABLE `permanenteitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `permanenteitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissao`
--

DROP TABLE IF EXISTS `permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissao` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) NOT NULL,
  `Descricao` text,
  `Menu item` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissao`
--

LOCK TABLES `permissao` WRITE;
/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` VALUES (1,'Produto',NULL,'Produto'),(2,'Fornecedor',NULL,'Fornecedor'),(3,'Entrada de Stock',NULL,'entradaStock'),(4,'Cliente',NULL,'cliente'),(5,'Categoria',NULL,''),(6,'Armazem',NULL,''),(7,'Forma de Pagamento',NULL,''),(8,'Forma de Impressao',NULL,''),(9,'IPC',NULL,''),(10,'Fabricante',NULL,''),(11,'Transferencia de Produto',NULL,''),(12,'Pagagemento de divida do fornecedor',NULL,''),(13,'Pagagemento de divida do cliente',NULL,''),(14,'Eliminar Factura',NULL,''),(15,'Encomenda',NULL,''),(16,'Alterar data de Expiracao',NULL,''),(17,'Alterar preco de venda',NULL,''),(18,'Alterar codigo de barra',NULL,''),(19,'Actualizar stock',NULL,''),(20,'Listagem de todos clientes',NULL,'');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissao_usuario`
--

DROP TABLE IF EXISTS `permissao_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissao_usuario` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IdPermissao` int(10) unsigned NOT NULL,
  `IdUsuario` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissao_usuario`
--

LOCK TABLES `permissao_usuario` WRITE;
/*!40000 ALTER TABLE `permissao_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissao_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `IsCozinha` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `produto_UN` (`Designacao`),
  KEY `fk_IdEstado_produto` (`IdEstado`),
  KEY `fk_IdCategoria_produto` (`IdCategoria`),
  KEY `fk_IdUsuario_produto` (`IdUsuario`),
  CONSTRAINT `fk_IdCategoria_produto` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria` (`Id`),
  CONSTRAINT `fk_IdEstado_produto` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdUsuario_produto` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serie`
--

DROP TABLE IF EXISTS `serie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serie` (
  `Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL DEFAULT '',
  `Ano` int(11) NOT NULL DEFAULT '0',
  `Status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Codigo`),
  KEY `FK_serie_1` (`Ano`),
  CONSTRAINT `FK_serie_1` FOREIGN KEY (`Ano`) REFERENCES `ano` (`Codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serie`
--

LOCK TABLES `serie` WRITE;
/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
INSERT INTO `serie` VALUES (4,'A',1,2),(5,'F',3,2),(6,'D',2,2),(8,'F',1,2),(9,'K',4,1);
/*!40000 ALTER TABLE `serie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servico`
--

DROP TABLE IF EXISTS `servico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servico` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(255) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `servico_estado_FK` (`IdEstado`),
  CONSTRAINT `servico_estado_FK` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servico`
--

LOCK TABLES `servico` WRITE;
/*!40000 ALTER TABLE `servico` DISABLE KEYS */;
/*!40000 ALTER TABLE `servico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicoproduto`
--

DROP TABLE IF EXISTS `servicoproduto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicoproduto` (
  `IdServico` int(10) unsigned NOT NULL,
  `IdProduto` int(11) NOT NULL,
  PRIMARY KEY (`IdServico`,`IdProduto`),
  KEY `servicoproduto_FK_1` (`IdProduto`),
  CONSTRAINT `servicoproduto_FK` FOREIGN KEY (`IdServico`) REFERENCES `servico` (`Id`),
  CONSTRAINT `servicoproduto_FK_1` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicoproduto`
--

LOCK TABLES `servicoproduto` WRITE;
/*!40000 ALTER TABLE `servicoproduto` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicoproduto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taxa`
--

DROP TABLE IF EXISTS `taxa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taxa` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(1000) DEFAULT NULL,
  `Taxa` double DEFAULT NULL,
  `IdEstado` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taxa`
--

LOCK TABLES `taxa` WRITE;
/*!40000 ALTER TABLE `taxa` DISABLE KEYS */;
INSERT INTO `taxa` VALUES (1,'SEM IVA',0,1),(2,'COM IVA',7,1),(3,'COM BASE NO CODIGO -M10',5,1);
/*!40000 ALTER TABLE `taxa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipocliente`
--

DROP TABLE IF EXISTS `tipocliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipocliente` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipocliente`
--

LOCK TABLES `tipocliente` WRITE;
/*!40000 ALTER TABLE `tipocliente` DISABLE KEYS */;
INSERT INTO `tipocliente` VALUES (1,'Normal'),(2,'Especial');
/*!40000 ALTER TABLE `tipocliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipofornecedor`
--

DROP TABLE IF EXISTS `tipofornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipofornecedor` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipofornecedor`
--

LOCK TABLES `tipofornecedor` WRITE;
/*!40000 ALTER TABLE `tipofornecedor` DISABLE KEYS */;
INSERT INTO `tipofornecedor` VALUES (1,'Empresa'),(2,'Individual');
/*!40000 ALTER TABLE `tipofornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposregime`
--

DROP TABLE IF EXISTS `tiposregime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposregime` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(100) NOT NULL,
  `cobraImposto` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposregime`
--

LOCK TABLES `tiposregime` WRITE;
/*!40000 ALTER TABLE `tiposregime` DISABLE KEYS */;
INSERT INTO `tiposregime` VALUES (4,'Regime Geral',1),(5,'Regime Simplificado',0),(6,'Regime de Exclusão',0),(7,'Lei n.º 7/19 de 24 de Abril)',0);
/*!40000 ALTER TABLE `tiposregime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipousuario` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipousuario`
--

LOCK TABLES `tipousuario` WRITE;
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` VALUES (1,'ADMINISTRATOR'),(2,'OPERADOR'),(3,'BARBEIRO'),(4,'DIVERSOS');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `IdUsuario` int(11) DEFAULT NULL,
  `Taxa` double DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `fk_usuario_1_idx` (`IdUsuario`),
  CONSTRAINT `fk_usuario_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'ZETA','root','63a9f0ea7bb98050796b649e85481845',1,1,'1997-08-13',NULL,NULL,'CONTA - ACTIVADA',1,0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'grest'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-19  5:32:12
