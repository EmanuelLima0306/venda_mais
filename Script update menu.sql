-- grest.log definition

ALTER TABLE `log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Designacao` varchar(255) DEFAULT NULL,
  `Descricao` varchar(255) DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `Data` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO grest.menuitem (id, Designacao, Descricao, IdMenu, IdEstado) VALUES(90, 'Log de Acesso', NULL, 69, 1);
INSERT INTO grest.menuitemusuario ( IdMenuitem, IdUsuario, IdEstado) VALUES( 90, 1, 1);

