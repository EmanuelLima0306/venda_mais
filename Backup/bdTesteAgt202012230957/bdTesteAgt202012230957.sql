CREATE DATABASE  IF NOT EXISTS `grest` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `grest`;
-- MySQL dump 10.13  Distrib 5.7.32, for Linux (x86_64)
--
-- Host: localhost    Database: grest
-- ------------------------------------------------------
-- Server version	5.7.32-0ubuntu0.18.04.1

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ano`
--

LOCK TABLES `ano` WRITE;
/*!40000 ALTER TABLE `ano` DISABLE KEYS */;
INSERT INTO `ano` VALUES (1,2019),(2,2018),(3,2020);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'DIVERSOS',1),(2,'aaaa',3),(3,'TESTE',1),(4,'TESTE SISTEMA',1);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'DIVERSO','diversos@gmail.com','111','Luanda',1,1,NULL,2,0,-220480,'999999999'),(2,'NCR-ANGOLA INFORMATICA, LDA','ncr@gmail.com','333345','Luanda',1,2,NULL,2,0,0,'5401007647'),(3,'ALIMENTA ANGOLA, LDA','zeta@gmail.com','930175149','AVENIDA VAN-DUNEM LOY ',1,1,NULL,2,0,-6400,'5417064980'),(5,'Agt Test','agttest@gmail.com','978451248','agt',1,1,NULL,4,0,0,'501997008');
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
  `Endereco` varchar(45) DEFAULT NULL,
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
INSERT INTO `empresa` VALUES (1,' A CASA DOS PERFUMES, LDA','5402132186','zetasoft100@gmail.com','930175149','Kilamba.K,Av.Pedro C.Vandum Loy','ww.zetasoft.co.ao','BFA: A006.0006.0000.9713.3592.3019.8\nATLANTICO:A006.0055.0000.2929.0654.1018.5',4,'Luanda','Condominio 1.png');
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
  CONSTRAINT `fk_encomenda_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encomenda_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  CONSTRAINT `fk_encomendaitem_1` FOREIGN KEY (`IdEncomenda`) REFERENCES `encomenda` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_encomendaitem_2` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradaproduto`
--

LOCK TABLES `entradaproduto` WRITE;
/*!40000 ALTER TABLE `entradaproduto` DISABLE KEYS */;
INSERT INTO `entradaproduto` VALUES (1,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(2,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(3,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(4,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(5,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(6,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(7,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(8,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(9,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(10,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(11,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(12,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(13,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(14,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(15,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(16,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(17,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(18,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(19,1,1,0,'2020-11-09',1,0,'2020-11-09',1,'0000D'),(20,1,1,0,'2020-12-17',1,0,'2020-12-17',1,'0000D'),(21,1,4,0,'2020-12-23',1,0,'2020-12-23',1,'0000D'),(22,1,4,0,'2020-12-23',1,0,'2020-12-23',1,'0000D'),(23,1,4,0,'2020-12-23',1,0,'2020-12-23',1,'0000D');
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradaprodutoitem`
--

LOCK TABLES `entradaprodutoitem` WRITE;
/*!40000 ALTER TABLE `entradaprodutoitem` DISABLE KEYS */;
INSERT INTO `entradaprodutoitem` VALUES (1,1,1,0,0,'1974',NULL,1,0,NULL),(2,2,2,0,0,'2974',NULL,1,0,NULL),(3,3,3,0,0,'3974',NULL,1,0,NULL),(4,4,4,0,0,'4974',NULL,1,0,NULL),(5,5,5,0,0,'5974',NULL,1,0,NULL),(6,6,6,0,0,'6974',NULL,1,0,NULL),(7,7,7,0,0,'7974',NULL,1,0,NULL),(8,8,8,0,0,'8974',NULL,1,0,NULL),(9,9,9,0,0,'9974',NULL,1,0,NULL),(10,10,10,0,0,'10974',NULL,1,0,NULL),(11,11,11,0,0,'11974',NULL,1,0,NULL),(12,12,12,0,0,'12974',NULL,1,0,NULL),(13,13,13,0,0,'13974',NULL,1,0,NULL),(14,14,14,0,0,'14974',NULL,1,0,NULL),(15,15,15,0,0,'15974',NULL,1,0,NULL),(16,16,16,0,0,'16974',NULL,1,0,NULL),(17,17,17,0,0,'17974',NULL,1,0,NULL),(18,18,18,0,0,'18974',NULL,1,0,NULL),(19,19,19,0,0,'19974',NULL,1,0,NULL),(20,20,21,0,0,'21974',NULL,1,0,NULL),(21,21,22,0,0,'22974',NULL,1,0,NULL),(22,22,23,0,0,'23974',NULL,1,0,NULL),(23,23,24,0,0,'24974',NULL,1,0,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Activado'),(2,'Desactivado'),(3,'Eliminado'),(4,'Concluir'),(5,'Devolucao'),(6,'Conzinha'),(7,'Ocupado'),(8,'Rectificacao'),(9,'A Liquidar'),(10,'Liquidado');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabricante`
--

LOCK TABLES `fabricante` WRITE;
/*!40000 ALTER TABLE `fabricante` DISABLE KEYS */;
INSERT INTO `fabricante` VALUES (1,'DIVERSOS',1),(2,'teste',3),(3,'aaaaa',3),(4,'RESTAURANTE',1),(5,'TESTE SISTEMA',1),(6,'HHH',3);
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
  `cambio` double NOT NULL DEFAULT '0',
  `valor_moeda_estrangeira` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `FK_factura_1` (`IdFormaPagamento`),
  KEY `FK_factura_2` (`IdUsuario`),
  KEY `fk_factura_3_idx` (`IdMoeda`),
  CONSTRAINT `FK_factura_1` FOREIGN KEY (`IdFormaPagamento`) REFERENCES `formapagamento` (`Id`),
  CONSTRAINT `FK_factura_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`),
  CONSTRAINT `fk_factura_3` FOREIGN KEY (`IdMoeda`) REFERENCES `moeda` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,2,4,3,1,'2020-12-23 09:17:15',0,14497.95,0,14497.95,0,NULL,'NCR-ANGOLA INFORMATICA, LDA','Venda','4',1,NULL,'dzcP','dfKRiCHIjEzPJxtyrmHzch5YOrzbcLPJZ71b+3UVK35iHybIVMQt5sAf840rx/Ob2sUceGJ0KfQ77ebsyPNFWZ7UwmU9/p6pbU+SoC/WI1UC8iVezdrTRzMIFx2/zNgfJ/8KNOxE0bgHs06NNupLcivoKDZtOnhbrj7yNTVR8so=',14379,118.95,'FR A2020/1',0,0),(2,2,4,3,3,'2020-12-23 09:29:06',0,8197.95,0,8197.95,0,NULL,'NCR-ANGOLA INFORMATICA, LDA','Venda','4',1,NULL,'AQ7V','A9NBPHBFswQkCaoyS/Gn7zI0EKmav6V6gxuN/vP1z/3zicBWiqRApsc1rGi5Jx2F1VoNrUZEqKfHXFgzLRNslCUgFkG7ArLYBXetR1l6P2qjNFmtKddkIgRt5MwvBYkSprkMy/jZ31mbUHAU2GQB4Jw66lcnKEsHaNnNYx1r94M=',7379,818.95,'FR A2020/2',0,0),(3,3,4,3,1,'2020-12-23 09:30:22',0,94006.35,0,94006.35,0,NULL,'ALIMENTA ANGOLA, LDA','Venda','4',1,NULL,'hjnc','huljlF695EjxKhuixOLWnglRTiu9YlcuD76fC7wLBqSZjrJuPHZOmpEGdjmYkTqZThL2EZNQj52E/V3JTPr252TJNxB1gnrNYsvnpiVPMaGSBbXWoxqQPz4cXWWUe45zzTnirzOd5+eabU+kXQPqMCGYf1Su+iLCOY1Mklqxkc0=',83889,10117.35,'FR A2020/3',0,0),(4,2,4,3,1,'2020-12-23 09:32:11',0,919687.59,0,919687.59,0,NULL,'NCR-ANGOLA INFORMATICA, LDA','Venda','4',1,NULL,'WFiI','WpJAtfaU64FNdXvjJGmNi8GCM84h+mIl1BMPlhL0Iv6cVd7ouqBxyo8Rf61kNDP8FyJFcKqkH0pz0sxlc1AuMs6QGoxBVBEWgokvVCI3V8HNbqzsBkpXa7pw+Ps8eE+Y5cOwzgbj14IEVV2DJNRoO6W/VWSyLvFzE5lVP7C3j84=',808005,111682.59,'FR A2020/4',0,0),(5,3,4,3,1,'2020-12-23 09:34:41',0,973624.71,0,973624.71,0,NULL,'ALIMENTA ANGOLA, LDA','Venda','4',1,NULL,'IMm4','IIC7ZLN6g6MJFVNZoejimJzf2S5UHV4UKDeUZ8Dm+X5l7+52XwRLjnA3i2l0ij9tJeXdHcoqJ47hG+TWwJU6DBupqoSsFIldbYHFL/KztOVotziK+g4f2pv9kPRcPgnu2ZaX4j+IJd5qvCF62aoqHD3NUGy5ZOv4Fq7WAOrCWMs=',856013,117611.71,'FR A2020/5',0,0),(6,2,4,3,3,'2020-12-23 09:35:15',0,1037.4,0,1037.4,0,NULL,'NCR-ANGOLA INFORMATICA, LDA','Venda','4',1,NULL,'HmCX','HMoJF0KRZJmXSokjVbztC/LU1jBZeXX+ScXXXbx33gamfUipatflN/zLTC0vkYhNP+SrZtbMC71Z0Y18JuZzPoFuurcmrj5qns9uavRTZg+sFfHHHZQn/tT/OAiVT+lNGukWj0ag0AnzbECxW+WNnJI07AWpIefbbhJa26ZSW1A=',910,127.4,'FR A2020/6',0,0),(7,2,4,4,1,'2020-12-23 09:39:35',0,0,0,4305,0,NULL,'NCR-ANGOLA INFORMATICA, LDA','Perfoma','4',1,NULL,'HXZh','Hs/R9l3xC8X1lYy1gRyfZcat5go1QzhzT63uDerwrbKNxT33WQG1U9H04GaFH2BAFPyQrJwqN3xt6K5HEj7gVKCA5dMUSkmhfxnDv5wi1P6EZifbOt+T4CYYv1MxkPzWzDmkZ+AI/pykA0FIMNsU475L0IAzIZkk0ZDlIoq7MUA=',4100,205,'PP A2020/1',0,0),(8,3,4,3,1,'2020-12-23 09:43:58',0,86955.56,0,86955.56,0,NULL,'ALIMENTA ANGOLA, LDA','Venda','4',1,NULL,'B0fN','BPAR4cRMud0mylCLNGCjfIodqvFlsyNuadJ73A6XW0TmO/ze9AIFi7AFXQJWYDFpRPB059Cad03GfmIXnwB3oYclJw/0E7tewvf9vw8mLPYqf+Vsrzi89Vq6NVazOXekclW4Z0NNYA27iGek5SF//LEhxoF7xJ64/Qi/I/YzJiI=',83889,9314.45,'FR A2020/7',0,0);
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturaitem`
--

LOCK TABLES `facturaitem` WRITE;
/*!40000 ALTER TABLE `facturaitem` DISABLE KEYS */;
INSERT INTO `facturaitem` VALUES (1,1,22,12000,1,12000,0,'22974',12000,0,1),(2,1,3,2000,1,2000,100,'3974',2100,0,3),(3,1,17,379,1,379,18.95,'17974',397.95,0,1),(4,2,21,5000,1,5000,700,'21974',5700,0,1),(5,2,3,2000,1,2000,100,'3974',2100,0,1),(6,2,17,379,1,379,18.95,'17974',397.95,0,1),(7,3,3,2000,1,2000,100,'3974',2100,0,1),(8,3,14,34406,1,34406,4816.84,'14974',39222.84,0,1),(9,3,2,1400,1,1400,70,'2974',1470,0,1),(10,3,9,10900,1,10900,1526,'9974',12426,0,1),(11,3,16,20504,1,20504,2870.56,'16974',23374.56,0,1),(12,3,17,379,1,379,18.95,'17974',397.95,0,1),(13,3,19,700,1,700,35,'19974',735,0,1),(14,3,18,1000,1,1000,50,'18974',1050,0,1),(15,3,1,10200,1,10200,510,'1974',10710,0,1),(16,3,5,2400,1,2400,120,'5974',2520,0,1),(17,4,10,1700,1,1700,85,'10974',1785,0,1),(18,4,3,2000,1,2000,100,'3974',2100,0,1),(19,4,14,34406,2,68812,9633.68,'14974',78445.68,0,1),(20,4,7,910,1,910,127.4,'7974',1037.4,0,1),(21,4,8,910,1,910,127.4,'8974',1037.4,0,1),(22,4,13,689990,1,689990,96598.6,'13974',786588.6,0,1),(23,4,9,10900,1,10900,1526,'9974',12426,0,1),(24,4,16,20504,1,20504,2870.56,'16974',23374.56,0,1),(25,4,17,379,1,379,18.95,'17974',397.95,0,1),(26,4,19,700,1,700,35,'19974',735,0,1),(27,4,18,1000,1,1000,50,'18974',1050,0,1),(28,4,1,10200,1,10200,510,'1974',10710,0,1),(29,5,24,5000,1,5000,250,'24974',5250,0,1),(30,5,10,1700,1,1700,85,'10974',1785,0,1),(31,5,3,2000,1,2000,100,'3974',2100,0,1),(32,5,14,34406,1,34406,4816.84,'14974',39222.84,0,1),(33,5,2,1400,1,1400,70,'2974',1470,0,1),(34,5,7,910,1,910,127.4,'7974',1037.4,0,1),(35,5,8,910,1,910,127.4,'8974',1037.4,0,1),(36,5,13,689990,1,689990,96598.6,'13974',786588.6,0,1),(37,5,9,10900,1,10900,1526,'9974',12426,0,1),(38,5,16,20504,1,20504,2870.56,'16974',23374.56,0,1),(39,5,17,379,1,379,18.95,'17974',397.95,0,1),(40,5,19,700,1,700,35,'19974',735,0,1),(41,5,18,1000,1,1000,50,'18974',1050,0,1),(42,5,1,10200,1,10200,510,'1974',10710,0,1),(43,5,12,7155,1,7155,1001.7,'12974',8156.7,0,1),(44,5,11,7050,1,7050,987,'11974',8037,0,1),(45,5,15,59409,1,59409,8317.26,'15974',67726.26,0,1),(46,5,5,2400,1,2400,120,'5974',2520,0,1),(47,6,7,910,1,910,127.4,'7974',1037.4,0,1),(48,7,3,2000,1,2000,100,'3974',2100,0,1),(49,7,2,1400,1,1400,70,'2974',1470,0,1),(50,7,19,700,1,700,35,'19974',735,0,1),(51,8,3,2000,1,2000,62,'3974',1302,760,1),(52,8,14,34406,1,34406,4816.84,'14974',39222.84,0,1),(53,8,2,1400,1,1400,70,'2974',1470,0,1),(54,8,9,10900,1,10900,763,'9974',6213,5450,1),(55,8,16,20504,1,20504,2870.56,'16974',23374.56,0,1),(56,8,17,379,1,379,17.05,'17974',358.16,37.9,1),(57,8,19,700,1,700,35,'19974',735,0,1),(58,8,18,1000,1,1000,50,'18974',1050,0,1),(59,8,1,10200,1,10200,510,'1974',10710,0,1),(60,8,5,2400,1,2400,120,'5974',2520,0,1);
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'DIVERSOS','','A',1,'2019-08-16',2,1,23423,'2222222222'),(2,'SHOPRITE - PALANCA','inf@shoprite.com','AFRICA DO SUL',1,'2019-11-07',2,1,33333,'2222');
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
  `Descricao` varchar(255) DEFAULT NULL,
  `IdUsuario` varchar(255) DEFAULT NULL,
  `Data` varchar(255) DEFAULT NULL,
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
  `id` int(10) NOT NULL AUTO_INCREMENT,
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
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `idmenu` int(10) DEFAULT NULL,
  `Designacao` varchar(45) DEFAULT NULL,
  `Descricao` text,
  PRIMARY KEY (`id`),
  KEY `fk_menuitem_1_idx` (`idmenu`),
  CONSTRAINT `fk_menuitem_1` FOREIGN KEY (`idmenu`) REFERENCES `menu` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitem`
--

LOCK TABLES `menuitem` WRITE;
/*!40000 ALTER TABLE `menuitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `menuitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menuitemusuario`
--

DROP TABLE IF EXISTS `menuitemusuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menuitemusuario` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `idmenuitem` int(10) DEFAULT NULL,
  `idusuario` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_menuitemusuario_1_idx` (`idmenuitem`),
  KEY `fk_menuitemusuario_2_idx` (`idusuario`),
  CONSTRAINT `fk_menuitemusuario_1` FOREIGN KEY (`idmenuitem`) REFERENCES `menuitem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_menuitemusuario_2` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuitemusuario`
--

LOCK TABLES `menuitemusuario` WRITE;
/*!40000 ALTER TABLE `menuitemusuario` DISABLE KEYS */;
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
INSERT INTO `moeda` VALUES (1,'AO',1),(2,'USD',1),(3,'EUR',1);
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
INSERT INTO `motivo` VALUES (2,'Regime transitório','M00',1),(3,'Transmissão de bens e serviço não sujeita','M02',1),(4,'IVA – Regime de não sujeição','M04',1),(6,'Isento nos termos da alínea b) do nº1 do artigo 12.º\ndo CIVA','M11',1),(7,'Isento nos termos da alínea c) do nº1 do artigo 12.º\ndo CIVA','M12',1),(8,'Isento nos termos da alínea d) do nº1 do artigo 12.º\ndo CIVA','M13',1),(9,'Isento nos termos da alínea e) do nº1 do artigo 12.º\ndo CIVA','M14',1),(10,'Isento nos termos da alínea f) do nº1 do artigo 12.º\ndo CIVA','M15',1),(11,'Isento nos termos da alínea g) do nº1 do artigo 12.º\ndo CIVA','M16',1),(12,'Isento nos termos da alínea h) do nº1 do artigo 12.º\ndo CIVA','M17',1),(13,'Isento nos termos da alínea i) do nº1 artigo 12.º do\nCIVA','M18',1),(14,'Isento nos termos da alínea j) do nº1 do artigo 12.º\ndo CIVA','M19',1),(15,'Isento nos termos da alínea k) do nº1 do artigo 12.º\ndo CIVA','M20',1),(16,'Isento nos termos da alínea l) do nº1 do artigo 12.º\ndo CIVA','M21',1),(17,'Isento nos termos da alínea m) do artigo 12.º do\nCIVA','M22',1),(18,'Isento nos termos da alínea n) do artigo 12.º do CIVA','M23',1),(19,'Isento nos termos da alínea 0) do artigo 12.º do CIVA','M24',1),(20,'Isento nos termos da alinea a) do nº1 do artigo 14.º','M80',1),(21,'Isento nos termos da alinea b) do nº1 do artigo 14.º','M81',1),(22,'Isento nos termos da alinea c) do nº1 do artigo 14.º','M82',1),(23,'Isento nos termos da alinea d) do nº1 do artigo 14.º','M83',1),(24,'Isento nos termos da alínea e) do nº1 do artigo 14.º','M84',1),(25,'  ','',1),(26,'Isento nos termos da alinea a) do nº2 do artigo 14.º','M85',1),(27,'Isento nos termos da alinea b) do nº2 do artigo 14.º','M86',1),(28,'Isento nos termos da alínea a) do artigo 15.º do CIVA','M30',1),(29,'Isento nos termos da alínea b) do artigo 15.º do CIVA','M31',1),(30,'Isento nos termos da alínea c) do artigo 15.º do CIVA','M32',1),(31,'Isento nos termos da alínea d) do artigo 15.º do CIVA','M33',1),(32,'Isento nos termos da alínea e) do artigo 15.º do CIVA','M34',1),(33,'Isento nos termos da alínea f) do artigo 15.º do CIVA','M35',1),(34,'Isento nos termos da alínea g) do artigo 15.º do CIVA','M36',1),(35,'Isento nos termos da alínea h) do artigo 15.º do CIVA','M37',1),(36,'Isento nos termos da alínea i) do artigo 15.º do CIVA','M38',1),(37,'Isento nos termos da alinea a) do nº1 do artigo 16.º','M90',1),(38,'Isento nos termos da alinea b) do nº1 do artigo 16.º','M91',1),(39,'Isento nos termos da alinea c) do nº1 do artigo 16.º','M92',1),(40,'Isento nos termos da alinea d) do nº1 do artigo 16.º','M93',1),(41,'Isento nos termos da alinea e) do nº1 do artigo 16.º','M94',1);
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimento`
--

LOCK TABLES `movimento` WRITE;
/*!40000 ALTER TABLE `movimento` DISABLE KEYS */;
INSERT INTO `movimento` VALUES (1,'Anulação REFERENTE À: ',1037.4,'C',6,4,2,'2020-12-23 09:37:27','2020-12-23','NC ZT2020/1',NULL,'YqsEf21UdBiJl8mmv/RPXgO7rlGdY8qusTgorHV1p04TaOG736P7BLncbgWMgyvN6Qv4luT1PbwLCeCkm8YpEgrDfR/GZW+wMfv/qNr3sg5qHeaACFmeBTt6qiMgymjaJ3i8yTZL2/bqoLC91F+AQH6veGKKCyihss0wopuL43U=','YiXq'),(2,'Anulação REFERENTE À: ',8197.95,'C',2,4,2,'2020-12-23 09:38:48','2020-12-23','NC ZT2020/2',NULL,'lT9ShxioEcSmnWx2Qwr7KBdmkQeYXm9UQlPNfgZmDiY8ybezpDZS5F8WTELYXlSLiEeCvDvvNJRsb/5YhBu+kRZMVdE/bHgY/2Nyjl419Bz8D8AbioXGI6buEpf8OmWLC2rImzDTV/qEib6vS0kJtdnPi31C0jeMNUP3l2pPfrg=','lSK9'),(3,'Rectificação REFERENTE À: ',2100,'C',1,4,2,'2020-12-23 09:52:21','2020-12-23','NC ZT2020/3',NULL,'MzwJoWo0sbnoWY1e7elO6FQy3inTj2fJjEJm/WiYqgmBYx4N21uM9rQsUkxENAoj4W6W+PPrVZMgR16rOg0SJW1JBudRhRMfxrpoMP0vEjtUnLLaXFc9/iZdUOHyQ843Z51EURMEBIbMGtR5vc3idP2HU/eQX1ORGCzdkQLql1k=','Mn6f');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentoitem`
--

LOCK TABLES `movimentoitem` WRITE;
/*!40000 ALTER TABLE `movimentoitem` DISABLE KEYS */;
INSERT INTO `movimentoitem` VALUES (1,0,1,7,910,1,910,127.4,'7974',1037.4,NULL),(2,0,2,21,5000,1,5000,700,'21974',5700,NULL),(3,0,2,3,2000,1,2000,100,'3974',2100,NULL),(4,0,2,17,379,1,379,18.95,'17974',397.95,NULL),(5,0,3,3,2000,1,2000,100,'3974',2100,NULL);
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
INSERT INTO `numeracao_documento` VALUES (1,'FACTURA CREDITO',1),(2,'PROFORMA',2),(3,'FACTURA RECIBO',8),(4,'RECIBO',1),(5,'NOTA CREDITO',4),(6,'NOTA DEBITO',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametro`
--

LOCK TABLES `parametro` WRITE;
/*!40000 ALTER TABLE `parametro` DISABLE KEYS */;
INSERT INTO `parametro` VALUES (1,'IMPRIMIR MOSTRAR ASSISTENTE',0),(2,'MOSTRAR ANTES DE IMPRIMIR ',1),(3,'MODULO SISTEMA ',1),(4,'PANEL CONFIG - PRODUTO',1),(5,'PANEL PRECO - PRODUTO',1),(6,'ARMAZEM PRINCIPAL LOJA',1),(7,'FORMAÇÃO',1);
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
  CONSTRAINT `fk_pedido_1` FOREIGN KEY (`IdMesa`) REFERENCES `mesa` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  PRIMARY KEY (`Id`),
  KEY `fk_pedidoItem_1_idx` (`IdPedido`),
  KEY `fk_pedidoItem_2_idx` (`IdProduto`),
  CONSTRAINT `fk_pedidoItem_1` FOREIGN KEY (`IdPedido`) REFERENCES `pedido` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedidoItem_2` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Designacao_UNIQUE` (`Designacao`),
  KEY `fk_IdEstado_produto` (`IdEstado`),
  KEY `fk_IdCategoria_produto` (`IdCategoria`),
  KEY `fk_IdUsuario_produto` (`IdUsuario`),
  CONSTRAINT `fk_IdCategoria_produto` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria` (`Id`),
  CONSTRAINT `fk_IdEstado_produto` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`),
  CONSTRAINT `fk_IdUsuario_produto` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Saco de arroz de 25kg','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,10200,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(2,'Cebola( 1kg )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,1400,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(3,'Batata ( 1kg )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,2000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(4,'Quijo Fresco ( 1kg )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,3600,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(5,'Um kilo de Pão ( 1kg )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,2400,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(6,'Leite ( 1 Litro )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,820,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(7,'Cerveja importada ( 330 ML )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,910,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(8,'Cerveja Nacional ( 0.5 Litros )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,910,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(9,'Garrafa de Vinho ( Qualidade Média )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,10900,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(10,'Alface ( 1 Unidade )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,1700,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(11,'Telemovel A8 MINI 2G','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,7050,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(12,'Telemovel A20 MINI 2G','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,7155,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(13,'Computador ALL-IN-ONE 21.5','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,689990,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(14,'CARREGADOR CARRO USB + AURICULAR BLUETOOTH','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,34406,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(15,'TRANSCENDE PENDRIVE 64GB','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,59409,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(16,'KINGSLONG MOCHILAS 15.6 CINZA ESCURO','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,20504,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(17,'ÓLEO DE SOJA KERO ( 1 LITRO )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,379,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(18,'PEITO ALTO COM OSSO ( BOVINO CONGELADO )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,1000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(19,'OVOS ALDEIA NOVA ( 12 UNIDADES )','','',1,1,0,0,1,'2020-11-09',1,0,0,0,0,1,700,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,''),(20,'massa','','',4,5,1,1,0,'2020-12-17',1,0,0,0,0,1,0,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',6,2,1,0,''),(21,'AGT Teste1','','',1,1,0,0,1,'2020-12-17',1,0,0,0,0,1,5000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,2,1,0,''),(22,'AGT TESTE','','',1,1,0,0,0,'2020-12-23',4,0,0,0,0,1,12000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',6,1,1,0,''),(23,'AGT','','',1,1,0,0,0,'2020-12-23',4,0,0,0,0,1,7000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',6,1,1,0,''),(24,'AGT TESTE 2','','',1,1,0,0,1,'2020-12-23',4,0,0,0,0,1,5000,'ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)',25,3,1,0,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serie`
--

LOCK TABLES `serie` WRITE;
/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
INSERT INTO `serie` VALUES (4,'A',1,1),(5,'F',3,2),(6,'D',2,2),(8,'F',1,2);
/*!40000 ALTER TABLE `serie` ENABLE KEYS */;
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
INSERT INTO `taxa` VALUES (1,'SEM IVA',0,1),(2,'COM IVA',14,1),(3,'COM BASE NO CODIGO -M10',5,1);
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposregime`
--

LOCK TABLES `tiposregime` WRITE;
/*!40000 ALTER TABLE `tiposregime` DISABLE KEYS */;
INSERT INTO `tiposregime` VALUES (4,'Regime Geral'),(5,'Regime Transitório'),(6,'Regime de Não Sujeição '),(7,'Lei n.º 7/19 de 24 de Abril)');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipousuario`
--

LOCK TABLES `tipousuario` WRITE;
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` VALUES (1,'ADMINISTRATOR'),(2,'OPERADOR');
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
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'ZETA','root','63a9f0ea7bb98050796b649e85481845',1,1,'1997-08-13',NULL,NULL,'CONTA - ACTIVADA'),(2,'Emanue Lima','emanuel','a80e1c212420901edde8bbeb64037593',2,1,'2020-12-16 23:5:20','emanuellima.lnb@gmail.com','943117628','CONTA - ACTIVADA'),(3,'Maruro','mauro','140c1f12feeb2c52dfbeb2da6066a73a',1,1,'2020-12-17 12:35:17','mauro@gmail.com','948471542','CONTA - ACTIVADA'),(4,'AGT','agt','99e7ec7ddbea1a2589c669301701f21f',1,1,'2020-12-23 9:8:47','agt@gmail.com','94561254','CONTA - ACTIVADA');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'grest'
--

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

-- Dump completed on 2020-12-23  9:58:36
