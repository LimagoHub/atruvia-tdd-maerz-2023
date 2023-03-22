DROP TABLE IF EXISTS tblmonteure_zeitmodelle;
DROP TABLE IF EXISTS tblmitarbeiterrollen;
DROP TABLE IF EXISTS tblmitarbeiter;
DROP TABLE IF EXISTS tblzeitmodelle;


CREATE TABLE `tblzeitmodelle` (
  `id` varchar(36) NOT NULL,
  `bezeichnung` varchar(255) NOT NULL,
  `dienstag` decimal(6,2) DEFAULT 7.50,
  `donnerstag` decimal(6,2) DEFAULT 7.50,
  `freitag` decimal(6,2) DEFAULT 7.00,
  `last_update` datetime NOT NULL DEFAULT current_timestamp(),
  `mittwoch` decimal(6,2) DEFAULT 7.50,
  `montag` decimal(6,2) DEFAULT 7.50,
  `samstag` decimal(6,2) DEFAULT 0.00,
  `sonntag` decimal(6,2) DEFAULT 7.50,
   `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7fslsne172k5vb3as8r25phhg` (`bezeichnung`)
);

CREATE TABLE `tblmonteure_zeitmodelle` (
  `beginn_gueltigkeit` date NOT NULL,
  `mitarbeiter_username` varchar(50) NOT NULL,
  `zeitmodell_id` varchar(36) NOT NULL,
  PRIMARY KEY (`beginn_gueltigkeit`,`mitarbeiter_username`,`zeitmodell_id`)
);

CREATE TABLE `tblmitarbeiter` (
  `username` varchar(50) NOT NULL,
  `account_non_locked` tinyint(1) DEFAULT 1,
  `beginn_arbeitverhaeltnis` date NOT NULL DEFAULT '1900-01-01',
  `credentials_non_expired` tinyint(1) DEFAULT 1,
  `enabled` tinyint(1) DEFAULT 1,
  `ende_arbeitverhaeltnis` date NOT NULL DEFAULT '2200-01-01',
  `fullname` varchar(50) DEFAULT NULL,
  `hersteller` tinyint(1) DEFAULT 1,
  `last_update` datetime DEFAULT current_timestamp(),
  `password` varchar(255) DEFAULT NULL,
   `version` int(11) NOT NULL,
  PRIMARY KEY (`username`)
);


CREATE TABLE `tblmitarbeiterrollen` (
  `username` varchar(50) NOT NULL,
  `rolle` varchar(10) DEFAULT NULL
) ;
    
 
