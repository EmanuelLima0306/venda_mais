-- MySQL dump 10.13  Distrib 5.7.32, for Linux (x86_64)
--
-- Host: localhost    Database: grestM
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
-- Table structure for table `barbeiroitem`
--
use grest;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
alter TABLE `barbeiroitem` (
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servico`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
alter TABLE `servico` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(255) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `servico_estado_FK` (`IdEstado`),
  CONSTRAINT `servico_estado_FK` FOREIGN KEY (`IdEstado`) REFERENCES `estado` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `servicoproduto`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
alter TABLE `servicoproduto` (
  `IdServico` int(10) unsigned NOT NULL,
  `IdProduto` int(11) NOT NULL,
  PRIMARY KEY (`IdServico`,`IdProduto`),
  KEY `servicoproduto_FK_1` (`IdProduto`),
  CONSTRAINT `servicoproduto_FK` FOREIGN KEY (`IdServico`) REFERENCES `servico` (`Id`),
  CONSTRAINT `servicoproduto_FK_1` FOREIGN KEY (`IdProduto`) REFERENCES `produto` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
alter TABLE `usuario` (
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
  CONSTRAINT `fk_usuario_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'grestM'
--

--
-- Dumping routines for database 'grestM'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-01 19:15:22
