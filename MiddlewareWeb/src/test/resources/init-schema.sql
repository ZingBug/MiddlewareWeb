DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL DEFAULT '',
  `password` VARCHAR(128) NOT NULL DEFAULT '',
  `salt` VARCHAR(32) NOT NULL DEFAULT '',
  `head_url` VARCHAR(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name`(`name`)
)ENGINE=InnoDB DEFAULT CHARSET =utf8;

DROP TABLE IF EXISTS `dssample`;
CREATE TABLE `dssample`(
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `sampleId` VARCHAR(128) NOT NULL DEFAULT '',
  `patientId` VARCHAR(128) NOT NULL DEFAULT '',
  `item` VARCHAR(128) NOT NULL DEFAULT '',
  `type` VARCHAR(128) NOT NULL DEFAULT '',
  `sendTime` DATETIME NOT NULL DEFAULT '1970-1-1 00:00:00',
  `device` VARCHAR(128) NOT NULL DEFAULT '',
  `fullName` VARCHAR(256) NOT NULL DEFAULT '',
  `result` DOUBLE(15,3) NOT NULL DEFAULT 0,
  `unit` VARCHAR(128) NOT NULL DEFAULT '',
  `normalLow` DOUBLE(15,3) NOT NULL DEFAULT 0,
  `normalHigh` DOUBLE(15,3) NOT NULL DEFAULT 0,
  `time` DATETIME NOT NULL DEFAULT '1970-1-1 00:00:00',
  `indicate` VARCHAR(512) NOT NULL DEFAULT '',
  `firstName` VARCHAR(256) NOT NULL DEFAULT '',
  `sex` VARCHAR(16) NOT NULL DEFAULT '',
  `age` VARCHAR(16) NOT NULL DEFAULT '',
  `sampleKind` VARCHAR(256) NOT NULL DEFAULT '',
  `doctor` VARCHAR(256) NOT NULL DEFAULT '',
  `area` VARCHAR(64) NOT NULL DEFAULT '',
  `bed` VARCHAR(16) NOT NULL DEFAULT '',
  `department` VARCHAR(64) NOT NULL DEFAULT '',
  `isGet` TINYINT(1)  NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`,`sampleId`,`item`,`sendTime`,`device`)
)ENGINE=InnoDB DEFAULT CHARSET =utf8;

DROP TABLE if EXISTS `device`;
CREATE TABLE `device`(
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `time` DATETIME NOT NULL DEFAULT '1970-1-1 00:00:00',
  `device` VARCHAR(128) NOT NULL DEFAULT '',
  `sampleCount0` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount2` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount4` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount6` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount8` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount10` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount12` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount14` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount16` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount18` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount20` INT(11) unsigned NOT NULL DEFAULT 0,
  `sampleCount22` INT(11) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`,`time`,`device`)
)ENGINE =InnoDB DEFAULT CHARSET =utf8;

DROP TABLE IF EXISTS `downsample`;
CREATE TABLE `downsample`(
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `sampleId` VARCHAR(128) NOT NULL DEFAULT '',
  `patientId` VARCHAR(128) NOT NULL DEFAULT '',
  `firstName` VARCHAR(256) NOT NULL DEFAULT '',
  `sex` VARCHAR(16) NOT NULL DEFAULT '',
  `age` VARCHAR(16) NOT NULL DEFAULT '',
  `device` VARCHAR(128) NOT NULL DEFAULT '',
  `sendTime` DATETIME NOT NULL DEFAULT '1970-1-1 00:00:00',
  PRIMARY KEY (`id`,`sampleId`,`device`,`sendTime`)
)ENGINE = InnoDB DEFAULT CHARSET =utf8;

DROP TABLE IF EXISTS `downtask`;
CREATE TABLE `downtask`(
  `sampleId` VARCHAR(128) NOT NULL DEFAULT '',
  `item` VARCHAR(128) NOT NULL DEFAULT '',
  `kind` VARCHAR(128) NOT NULL DEFAULT '',
  `device` VARCHAR(128) NOT NULL DEFAULT '',
  `sendTime` DATETIME NOT NULL DEFAULT '1970-1-1 00:00:00',
  PRIMARY KEY (`sampleId`,`item`,`kind`,`device`,`sendTime`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;




