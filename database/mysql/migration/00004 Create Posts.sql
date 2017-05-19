CREATE TABLE `blag`.`blogpost` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created` DATETIME NULL,
  `updated` DATETIME NULL,
  `content` TEXT NULL,
  `title` TEXT NULL,
  `deleted` TINYINT(1) NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
