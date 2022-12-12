-- ======================================================================
-- 							Script de Création
-- ======================================================================

CREATE TABLE CHAMBRE
(
	idChambre 	INTEGER 	NOT NULL,
	nomChambre 	VARCHAR(225) 	NOT NULL,
	typeLit 	VARCHAR(225)	NOT NULL,
	prix 		INTEGER 	NOT NULL,
	
	CONSTRAINT pkChambre PRIMARY KEY (idChambre),
	CONSTRAINT prixService CHECK (prix >= 0)
);

CREATE TABLE SERVICE
(
	idService 	INTEGER 	NOT NULL,
	nomService 	VARCHAR(225)	NOT NULL,
	prix 		INTEGER		NOT NULL,
	
	CONSTRAINT pkServices PRIMARY KEY (idService),
	CONSTRAINT prixService CHECK (prix >= 0)
);

CREATE TABLE CLIENT
(
	idClient 		INTEGER 	NOT NULL,
	nomClient 		VARCHAR(225) 	NOT NULL,
	prenomClient 		VARCHAR(225)	NOT NULL,
	age 			INTEGER		NOT NULL,

	CONSTRAINT pkClient PRIMARY KEY (idClient)
);

CREATE TABLE RESERVER
(
	idChambre 	INTEGER 	NOT NULL,
	idClient 	INTEGER 	NOT NULL,
	dateDebut 	DATE 		NOT NULL,
	dateFin 	DATE 		NOT NULL,
	
	CONSTRAINT pkReserver PRIMARY KEY (idClient,idChambre,dateDebut,dateFin),
	CONSTRAINT fkReserver01 FOREIGN KEY (idChambre) REFERENCES chambre (idChambre),
	CONSTRAINT fkReserver02 FOREIGN KEY (idClient) REFERENCES client (idClient)
);

CREATE TABLE POSSEDE
(
	idChambre INTEGER		NOT NULL,
	idService INTEGER 		NOT NULL,
	
	CONSTRAINT pkAvoirr PRIMARY KEY (idService,idChambre),
	CONSTRAINT fkAvoir01 FOREIGN KEY (idChambre) REFERENCES chambre (idChambre),
	CONSTRAINT fkAvoir02 FOREIGN KEY (idService) REFERENCES Service (idService)
);
