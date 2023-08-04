DROP TABLE IF EXISTS `tb_person`;

CREATE TABLE `tb_person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `sobrenome` varchar(80) NOT NULL,
  `idade` INT NOT NULL,
  `pais` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
)