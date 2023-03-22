INSERT INTO tblzeitmodelle (id, bezeichnung, version) values ('5c88d4c5-4c8a-45f6-85f0-170ff4112427', 'Standard', 0); 
INSERT INTO tblzeitmodelle (id, bezeichnung, version) values ('5c88d4c5-4c8a-45f6-85f0-170ff4112428', 'Dummy1', 0); 

INSERT INTO `tblmitarbeiter` (username, fullname,hersteller,password, version ) values ('limago', 'Hersteller', TRUE,'$2a$10$VyNSiMVB5s8M2MEOm1Mh/el8NW3mPM3UX81trnjgTVGdDrqbpq8Qq',0);
INSERT INTO `tblmitarbeiter` (username, fullname,hersteller,password, version ) values ('peterhinz', 'Peter Hinz', FALSE,'$2a$10$VyNSiMVB5s8M2MEOm1Mh/el8NW3mPM3UX81trnjgTVGdDrqbpq8Qq', 0);
INSERT INTO `tblmitarbeiter` (username, fullname,hersteller,password, version ) values ('paulkunz', 'Paul Kunz', FALSE,'$2a$10$VyNSiMVB5s8M2MEOm1Mh/el8NW3mPM3UX81trnjgTVGdDrqbpq8Qq', 0);
INSERT INTO `tblmitarbeiter` (username, fullname,hersteller,password , version) values ('maryschmidt', 'Mary Schmidt', FALSE,'$2a$10$VyNSiMVB5s8M2MEOm1Mh/el8NW3mPM3UX81trnjgTVGdDrqbpq8Qq',0);



INSERT INTO `tblmonteure_zeitmodelle` (`beginn_gueltigkeit`,`mitarbeiter_username`,`zeitmodell_id`) VALUES ('2019-01-01','limago','5c88d4c5-4c8a-45f6-85f0-170ff4112427');
INSERT INTO `tblmonteure_zeitmodelle` (`beginn_gueltigkeit`,`mitarbeiter_username`,`zeitmodell_id`) VALUES ('2019-01-01','peterhinz','5c88d4c5-4c8a-45f6-85f0-170ff4112427');

INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('limago', 'ADMIN');
INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('limago', 'MANAGER');
INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('limago', 'MEISTER');
INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('limago', 'MONTEUR');
INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('maryschmidt', 'MANAGER');
INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('paulkunz', 'MEISTER');
INSERT INTO `tblmitarbeiterrollen` (username, rolle) values ('peterhinz', 'MONTEUR');



