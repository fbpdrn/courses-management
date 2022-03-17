-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema course_management
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema course_management
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `course_management` DEFAULT CHARACTER SET utf8 ;
USE `course_management` ;

-- -----------------------------------------------------
-- Table `course_management`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`course` (
  `idcourse` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NOT NULL,
  `ssd` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NULL,
  `credits` DOUBLE NULL DEFAULT 0,
  `students` DOUBLE NULL DEFAULT 0,
  `hours` DOUBLE NULL DEFAULT 0,
  `param` VARCHAR(255) NULL,
  `period` DOUBLE NOT NULL,
  `year` DOUBLE NOT NULL,
  `yearOff` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idcourse`),
  UNIQUE INDEX `uq_courses` (`code` ASC, `ssd` ASC, `yearOff` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`department` (
  `iddepartment` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL,
  `city` VARCHAR(90) NULL,
  `streetname` VARCHAR(255) NULL,
  `streetnumber` VARCHAR(45) NULL,
  `zipcode` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`iddepartment`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`staff` (
  `idstaff` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `firstname` VARCHAR(255) NULL,
  `middlename` VARCHAR(255) NULL,
  `surname` VARCHAR(255) NULL,
  `sex` VARCHAR(45) NULL,
  `phonenumber` VARCHAR(45) NULL,
  `roomnumber` VARCHAR(45) NULL,
  `streetname` VARCHAR(255) NULL,
  `streetnumber` VARCHAR(45) NULL,
  `city` VARCHAR(255) NULL,
  `zipcode` VARCHAR(45) NULL,
  `contract` VARCHAR(45) NULL,
  `departmentid` INT NOT NULL,
  PRIMARY KEY (`idstaff`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_staff_department1_idx` (`departmentid` ASC) VISIBLE,
  CONSTRAINT `fk_staff_department1`
    FOREIGN KEY (`departmentid`)
    REFERENCES `course_management`.`department` (`iddepartment`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`year`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`year` (
  `idyear` INT NOT NULL AUTO_INCREMENT,
  `yearstart` VARCHAR(255) NULL,
  `yearend` VARCHAR(255) NULL,
  PRIMARY KEY (`idyear`),
  UNIQUE INDEX `year_uq` (`yearstart` ASC, `yearend` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`referent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`referent` (
  `staff_idstaff` INT NOT NULL,
  `course_idcourse` INT NOT NULL,
  `hours` DOUBLE NOT NULL DEFAULT 0,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`staff_idstaff`, `course_idcourse`, `type`),
  INDEX `fk_staff_has_course_course1_idx` (`course_idcourse` ASC) VISIBLE,
  INDEX `fk_staff_has_course_staff1_idx` (`staff_idstaff` ASC) VISIBLE,
  UNIQUE INDEX `fk_referent_uq` (`course_idcourse` ASC, `staff_idstaff` ASC, `type` ASC) VISIBLE,
  CONSTRAINT `fk_staff_has_course_staff1`
    FOREIGN KEY (`staff_idstaff`)
    REFERENCES `course_management`.`staff` (`idstaff`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_staff_has_course_course1`
    FOREIGN KEY (`course_idcourse`)
    REFERENCES `course_management`.`course` (`idcourse`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`degree`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`degree` (
  `iddegree` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `code` VARCHAR(255) NOT NULL,
  `accyear` DOUBLE NOT NULL,
  `totalcredits` DOUBLE NOT NULL,
  `isPublished` INT NOT NULL DEFAULT 1,
  `yearid` INT NOT NULL,
  PRIMARY KEY (`iddegree`, `yearid`),
  INDEX `fk_degree_year1_idx` (`yearid` ASC) VISIBLE,
  UNIQUE INDEX `uq_degree` (`code` ASC, `yearid` ASC, `name` ASC) VISIBLE,
  CONSTRAINT `fk_degree_year1`
    FOREIGN KEY (`yearid`)
    REFERENCES `course_management`.`year` (`idyear`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`degree_has_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`degree_has_course` (
  `degree_iddegree` INT NOT NULL,
  `course_idcourse` INT NOT NULL,
  PRIMARY KEY (`degree_iddegree`, `course_idcourse`),
  INDEX `fk_degree_has_course_course1_idx` (`course_idcourse` ASC) VISIBLE,
  INDEX `fk_degree_has_course_degree1_idx` (`degree_iddegree` ASC) VISIBLE,
  UNIQUE INDEX `fk_degree_has_course_uq` (`degree_iddegree` ASC, `course_idcourse` ASC) VISIBLE,
  CONSTRAINT `fk_degree_has_course_degree1`
    FOREIGN KEY (`degree_iddegree`)
    REFERENCES `course_management`.`degree` (`iddegree`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_degree_has_course_course1`
    FOREIGN KEY (`course_idcourse`)
    REFERENCES `course_management`.`course` (`idcourse`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `hashedPassword` VARCHAR(255) NOT NULL,
  `role` VARCHAR(45) NOT NULL DEFAULT 'user',
  `profilePictureUrl` VARCHAR(255) NULL,
  `name` VARCHAR(255) NULL DEFAULT 'Name',
  `surname` VARCHAR(255) NULL DEFAULT 'Surname',
  PRIMARY KEY (`iduser`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE USER 'cm_user' IDENTIFIED BY 'cm_pass';

GRANT ALL ON `course_management`.* TO 'cm_user';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- begin attached script 'script'
SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

INSERT INTO `year` (`idyear`, `yearstart`, `yearend`) VALUES
(1,	'2021-10-04',	'2022-06-06'),
(2,	'2022-10-04',	'2023-06-06');

INSERT INTO `course_management`.`user` (`iduser`, `username`, `hashedPassword`, `role`, `profilePictureUrl`, `name`, `surname`) VALUES (DEFAULT, 'admin', '$2a$12$RcvwttwSag7Uw8Uq41O1t.YoZe32P9tMGjGD.i3e3LglwGkzqFwJ2', 'admin', 'images/logo.png', 'Name', 'Surname');

INSERT INTO `department` (`iddepartment`, `name`, `description`, `city`, `streetname`, `streetnumber`, `zipcode`, `phone`) VALUES
(1,	'DISA',	'Dipartimento di Ingegneria e Scienze Applicate',	'Dalmine',	'Via Ingegneri',	'35',	NULL,	'+39 029123567'),
(2,	'DIGIP',	'Dipartimento di Ingegneria Gestionale, dell\'Informazione e della Produzione',	'Dalmine',	'Via Ingegneri',	'35',	NULL,	'+39 029123467'),
(3,	'DIPSA',	'Dipartimento di Scienze Aziendali',	'Milano',	'Via Economica',	'92',	NULL,	'+39 002123789'),
(4,	'DSE',	'Dipartimento di Scienze Economiche',	'Brescia',	'Piazza Azienda',	'22',	NULL,	'+39 003123987'),
(5,	'DSUS',	'Dipartimento di Scienze Umane e Sociali',	'Bergamo',	'Via I Maggio',	'5',	NULL,	'+39 035467893'),
(6,	'DIGIU',	'Dipartimento di Giurisprudenza',	'Roma',	'Via Legge',	'79',	NULL,	'+39 098123768'),
(7,	'DLFC',	'Dipartimento di Lettere, Filosofia, Comunicazione',	'Bergamo',	'Via Ugo Foscolo',	'34',	NULL,	'+39 035647761'),
(8,	'DLLCS',	'Dipartimento di Lingue, Letterature e Culture Straniere',	'Bergamo',	'Via Roma',	'8',	NULL,	'+39 035612234');

INSERT INTO `staff` (`idstaff`, `email`, `firstname`, `middlename`, `surname`, `sex`, `phonenumber`, `roomnumber`, `streetname`, `streetnumber`, `city`, `zipcode`, `contract`, `departmentid`) VALUES
(1,	'e.trevisani@disa.com',	'Espedito',	'',	'Trevisani',	'M',	'+39 0340 8127847',	'R4',	'Strada Provinciale',	'65',	'Bergamo',	'24129',	'internal',	1),
(2,	'e.pisano@disa.com',	'Elia',	'',	'Pisano',	'M',	'+39 0392 2607086',	'B5',	'Via San Pietro Ad Aram',	'50',	'Velate',	'20040',	'other',	1),
(3,	'a.trevisan@dsus.com',	'Arturo',	'',	'Trevisan',	'M',	'+39 036 546798',	'34',	'Via Dei Fiorentini',	'5',	'Bergamo',	'24124',	'internal',	5),
(4,	'i.bergamaschi@disa.com',	'Isa',	'',	'Bergamaschi',	'F',	'+39 0341 5645387',	'25',	'Via Torricelli',	'23',	'Isera',	'38060',	'external',	1),
(5,	'f.trentini@disa.com',	'Filomena',	'',	'Trentini',	'F',	'+39 0328 0539890',	'L42',	'Via Scala',	'72',	'Cetona',	'53040',	'external',	1),
(6,	'f.boni@dsus.com',	'Fabrizia',	'Maria',	'Boni',	'F',	'+39 0382 51761',	'5',	'Via Giacomo Leopardi',	'23',	'Bergamo',	'24124',	'other',	5),
(7,	'i.duca@disa.com',	'Imelda',	'Loris',	'Duca',	'F',	'+39 0397 5352325',	'G24',	'Via Nazario Sauro',	'88',	'Cantalupo',	'20020',	'internal',	1),
(8,	'f.udinesi@dsus.com',	'Fabio',	'',	'Udinesi',	'M',	'+39 0370 98765',	'B3',	'Piazzetta Scalette Rubiani',	'10',	'Dalmine',	'24589',	'internal',	5),
(9,	'l.monaldo@digip.com',	'Liviano',	'',	'Monaldo',	'M',	'+39 0319 7921424',	'P921',	'Via Capo le Case',	'146',	'Wangen',	'39050',	'internal',	2),
(10,	'd.genovesi@dsus.com',	'Dora',	'',	'Genovesi',	'F',	'+39 0342 88657',	'E21',	'Via Enrico Fermi',	'78',	'Bergamo',	'24124',	'external',	5),
(11,	'o.greco@digip.com',	'Oliviero',	'',	'Greco',	'M',	'+39 0395 8502993',	'W123',	'Via Francesco Del Giudice',	'81',	'Complobbi',	'50061',	'external',	2),
(12,	'a.marino@digip.com',	'Alida',	'',	'Marino',	'F',	'+39 0328 9352388',	'Nessuna',	'Via Francesco Girardi',	'18',	'Carrara Santo Stefano',	'35020',	'other',	2),
(13,	'c.loggia@dsus.com',	'Claudio',	'',	'Loggia',	'M',	'+39 0398 72460',	'L45',	'Via delle Coste',	'23',	'Milano',	'23567',	'external',	5),
(14,	'g.loggia@digip.com',	'Gerolamo',	'',	'Loggia',	'M',	'+39 0334 5133603',	'CV1',	'Via Zannoni',	'23',	'Vervo',	'38010',	'external',	2),
(15,	'e.padovesi@dlfc.com',	'Ezio',	'',	'Padovesi',	'M',	'+39 0325 64235',	'T6',	'Via del Pontiere',	'139',	'Parma',	'25699',	'internal',	7),
(16,	'g.manna@digip.com',	'Gianna',	'',	'Manna',	'F',	'+39 0330 8841410',	'P12',	'Via Corso Casale',	'11',	'Tuscania',	'01017',	'external',	2),
(17,	'l.moretti@dlfc.com',	'Landolfo',	'',	'Moretti',	'M',	'+39 0324 47681',	'F7',	'Via Firenze',	'44',	'Imperia',	'43556',	'external',	7),
(18,	'i.toscano@dipsa.com',	'Italia',	'',	'Toscano',	'F',	'+39 0378 2381137',	'Nessuna',	'Via Domenica Morelli',	'97',	'Pescara',	'65124',	'external',	3),
(19,	'l.sal@dlfc.com',	'Libera',	'',	'Sal',	'F',	'+39 0356 65893',	'35',	'Piazza Cardinale Sforza',	'45',	'Milano',	'21356',	'other',	7),
(20,	'c.lombardi@dse.com',	'Candido',	'',	'Lombardia',	'M',	'+39 0320 6035474',	'T12',	'Via Gaetano Donizetti',	'97',	'Cerro Veronese',	'37020',	'other',	4),
(21,	'n.fiorentino@dlfc.com',	'Nadia',	'',	'Fiorentino',	'F',	'+39 0324 47990',	'67',	'Via Giacinto Gigante',	'34',	'Mantova',	'34561',	'internal',	7),
(22,	'i.angelo@dlfc.com',	'Isaia',	'',	'Angelo',	'M',	'+39 0341 84497892',	'E4',	'Via degli Aldobrandeschi',	'97',	'Brescia',	'34521',	'external',	7),
(23,	'i.pinto@dllcs.com',	'Irma',	'',	'Pinto',	'F',	'+3903990236866',	'R5',	'Via Catullo',	'131',	'Milano',	'23412',	'external',	8);

INSERT INTO `course` (`idcourse`, `code`, `ssd`, `name`, `credits`, `students`, `hours`, `param`, `period`, `year`, `yearOff`) VALUES
(1,	'21055',	'MAT/05',	'Analisi Matematica I',	9,	100,	225,	'',	1,	1,	'21-22'),
(2,	'21011',	'FIS/01',	'Fisica Generale',	12,	125,	300,	'',	1,	1,	'21-22'),
(3,	'21010',	'CHIM/07',	'Chimica',	6,	125,	150,	'',	1,	1,	'21-22'),
(4,	'25310',	'M-PED/02',	'Storia Sociale Dell\'Educazione',	12,	60,	300,	'',	1,	1,	'21-22'),
(5,	'21012',	'ING-INF/05',	'Informatica (Modulo di Programmazione)',	6,	100,	150,	'',	1,	1,	'21-22'),
(6,	'25318',	'SPS/08',	'Sociologia Della Cultura',	6,	120,	150,	'',	3,	1,	'21-22'),
(7,	'25299',	'M-PSI/04',	'Psicologia Del Ciclo Di Vita',	12,	145,	300,	'',	2,	1,	'21-22'),
(8,	'21054',	'MAT/03',	'Geometria e Algebra Lineare',	6,	111,	150,	'p1',	2,	1,	'21-22'),
(9,	'25285',	'IUS/10',	'Diritto Amministrativo',	8,	120,	200,	'',	4,	1,	'21-22'),
(10,	'21013',	'ING-INF/05',	'Informatica modulo di Calcolatori elettronici',	6,	111,	125,	'p1',	2,	1,	'21-22'),
(11,	'25315',	'SPS/03',	'Storia Delle Istituzioni Politiche',	6,	80,	150,	'',	1,	1,	'21-22'),
(12,	'218517',	'ING-INF/05',	'Programmazione a oggetti',	6,	108,	150,	'p1',	2,	1,	'21-22'),
(13,	'25069',	'M-STO/06',	'Storia Delle Religioni',	6,	70,	150,	'',	2,	1,	'21-22'),
(14,	'218516',	'ING-IND/35',	'Economia ed organizzazione aziendale',	9,	112,	225,	'p20',	2,	1,	'21-22'),
(15,	'21015',	'MAT/05',	'Analisi matematica II',	6,	90,	150,	'',	1,	2,	'21-22'),
(16,	'21017',	'ING-IND/31',	'Elettrotecnica',	6,	90,	150,	'',	1,	2,	'21-22'),
(17,	'21060',	'SECS-S/02',	'Statistica',	9,	70,	225,	'',	1,	2,	'21-22'),
(18,	'25292',	'INF/01',	'Fondamenti Di Informatica',	6,	200,	150,	'',	1,	1,	'21-22'),
(19,	'21018',	'ING-INF/04',	'Fondamenti di automatica',	9,	90,	225,	'',	1,	2,	'21-22'),
(20,	'CCLENGB1',	'LINGUA',	'ADD Lingua inglese B1',	6,	200,	150,	'',	1,	1,	'21-22'),
(21,	'21061',	'ING-INF/05',	'Sistemi operativi',	6,	89,	150,	'p21-22',	2,	2,	'21-22'),
(22,	'25239',	'M-PED/01',	'Filosofia Dell\'Educazione',	12,	180,	300,	'',	1,	2,	'21-22'),
(23,	'21064',	'ING-INF/05',	'Basi di Dati',	6,	70,	150,	'',	2,	2,	'21-22'),
(24,	'21020',	'ING-INF/01',	'Fondamenti di elettronica',	9,	150,	225,	'',	2,	2,	'21-22'),
(25,	'25234',	'M-PED/04',	'Metodi E Tecniche Della Ricerca Educativa ',	12,	200,	300,	'',	1,	2,	'21-22'),
(26,	'25258',	'SPS/07',	'Teoria Delle Scienze Sociali',	12,	180,	300,	'',	3,	2,	'21-22'),
(27,	'21024',	'ING-INF/03',	'Fondamenti di reti e telecomunicazione',	9,	68,	225,	'',	2,	2,	'21-22'),
(28,	'25284',	'M-DEA/01',	'Istituzioni Di Antropologia Culturale',	6,	80,	150,	'',	1,	2,	'21-22'),
(29,	'21034',	'ING-INF/05',	'Ingegneria del software',	9,	98,	225,	'p2021',	1,	3,	'21-22'),
(30,	'21063',	'ING-INF/05',	'Embedded and real time systems',	6,	60,	125,	'',	1,	3,	'21-22'),
(31,	'21063A',	'ING-INF/05',	'Programmazione Web',	6,	70,	125,	'p1',	2,	3,	'21-22'),
(32,	'25218',	'M-PED/03',	'Pedagogia Speciale',	12,	200,	300,	'',	2,	2,	'21-22'),
(33,	'25152',	'TIRO/85',	'Tirocinio',	13,	200,	325,	'',	2,	2,	'21-22'),
(34,	'21053',	'ING-INF/03',	'CI Databases 2 + Multimedia Internet',	12,	120,	300,	'p0',	2,	3,	'21-22'),
(35,	'25237',	'M-PED/01',	'Pedagogia Sociale',	12,	180,	300,	'',	1,	3,	'21-22'),
(36,	'21037',	'ING-IND/35',	'Sistemi di controllo di gestione',	6,	100,	125,	'p03',	1,	3,	'21-22'),
(37,	'21028',	'MAT/02',	'Algebra e logica',	6,	120,	125,	'',	2,	3,	'21-22'),
(38,	'25233',	'L-FIL-LET/10',	'Letteratura Italiana',	6,	180,	150,	'',	2,	3,	'21-22'),
(39,	'21033',	'ING-IND/17',	'Gestione della produzione industriale',	12,	250,	300,	'',	2,	3,	'21-22'),
(40,	'25173',	'INF/01',	'Metodi Per Il Trattamento Dei Dati Sociali',	6,	150,	150,	'',	3,	3,	'21-22'),
(41,	'L3L8',	'TESI/INF',	'Tesi ',	3,	300,	75,	'',	2,	3,	'21-22'),
(42,	'25244',	'M-PSI/05',	'Teoria E Pratica Dei Gruppi',	6,	180,	150,	'',	2,	3,	'21-22'),
(43,	'25245',	'M-STO/05',	'Storia Della Scienza E Servizi Sociali',	6,	140,	150,	'',	3,	3,	'21-22'),
(44,	'25308',	'M-PED/02',	'Storia Della Pedagogia Per L\'Infanzia',	12,	150,	300,	'',	1,	3,	'21-22'),
(45,	'25200',	'TESI/UM',	'Prova Finale',	3,	200,	75,	'',	4,	3,	'21-22');

INSERT INTO `referent` (`staff_idstaff`, `course_idcourse`, `hours`, `type`) VALUES
(1,	6,	1,	'lesson'),
(1,	41,	75,	'lesson'),
(2,	1,	45,	'tutoring'),
(2,	2,	150,	'lesson'),
(2,	10,	25,	'tutoring'),
(2,	12,	50,	'lesson'),
(2,	19,	200,	'other'),
(3,	9,	125,	'lesson'),
(3,	11,	35,	'tutoring'),
(3,	13,	70,	'lesson'),
(3,	18,	151,	'tutoring'),
(3,	20,	50,	'lesson'),
(3,	22,	180,	'lesson'),
(3,	26,	55,	'tutoring'),
(3,	32,	270,	'lesson'),
(3,	33,	35,	'lesson'),
(3,	35,	90,	'tutoring'),
(3,	40,	10,	'training'),
(3,	42,	55,	'tutoring'),
(3,	43,	55,	'tutoring'),
(3,	44,	80,	'tutoring'),
(4,	27,	25,	'tutoring'),
(5,	16,	50,	'tutoring'),
(5,	34,	200,	'tutoring'),
(6,	7,	100,	'tutoring'),
(6,	9,	75,	'other'),
(6,	13,	80,	'tutoring'),
(6,	20,	100,	'tutoring'),
(6,	25,	150,	'lesson'),
(6,	26,	70,	'other'),
(6,	33,	290,	'training'),
(6,	38,	55,	'tutoring'),
(6,	39,	275,	'lesson'),
(6,	42,	30,	'training'),
(6,	45,	75,	'other'),
(7,	3,	30,	'training'),
(7,	15,	150,	'lesson'),
(8,	4,	250,	'lesson'),
(8,	7,	20,	'training'),
(8,	28,	90,	'lesson'),
(8,	37,	125,	'lesson'),
(8,	42,	65,	'lesson'),
(8,	43,	95,	'lesson'),
(9,	1,	180,	'lesson'),
(9,	8,	50,	'tutoring'),
(9,	17,	200,	'other'),
(9,	23,	50,	'lesson'),
(10,	6,	149,	'tutoring'),
(10,	7,	180,	'lesson'),
(10,	14,	100,	'lesson'),
(10,	25,	150,	'tutoring'),
(10,	32,	30,	'tutoring'),
(10,	35,	210,	'lesson'),
(10,	40,	140,	'lesson'),
(10,	44,	220,	'lesson'),
(11,	2,	25,	'tutoring'),
(11,	36,	125,	'lesson'),
(12,	2,	25,	'tutoring'),
(12,	12,	25,	'lesson'),
(12,	29,	225,	'lesson'),
(13,	4,	50,	'tutoring'),
(13,	8,	100,	'lesson'),
(13,	11,	115,	'lesson'),
(13,	12,	25,	'lesson'),
(13,	14,	125,	'lesson'),
(13,	26,	175,	'lesson'),
(13,	28,	60,	'tutoring'),
(14,	3,	120,	'lesson'),
(14,	16,	100,	'lesson'),
(14,	21,	50,	'tutoring'),
(14,	24,	225,	'lesson'),
(15,	2,	100,	'training'),
(15,	10,	100,	'lesson'),
(15,	21,	100,	'lesson'),
(15,	34,	100,	'tutoring'),
(16,	17,	25,	'training'),
(16,	30,	125,	'lesson'),
(17,	22,	120,	'tutoring'),
(17,	23,	50,	'training'),
(17,	23,	50,	'tutoring'),
(17,	39,	25,	'tutoring'),
(18,	12,	50,	'training'),
(18,	19,	25,	'lesson'),
(18,	31,	125,	'lesson'),
(19,	5,	75,	'other'),
(20,	5,	75,	'lesson'),
(20,	27,	200,	'lesson'),
(23,	38,	95,	'lesson');

INSERT INTO `degree` (`iddegree`, `name`, `code`, `accyear`, `totalcredits`, `isPublished`, `yearid`) VALUES
(1,	'Laurea In Ingegneria Informatica',	'L/8',	3,	180,	1,	1),
(2,	'Laurea in Scienze Dell\'Educazione',	'LT-SE',	3,	180,	1,	1);

INSERT INTO `degree_has_course` (`degree_iddegree`, `course_idcourse`) VALUES
(1,	1),
(1,	2),
(1,	3),
(2,	4),
(1,	5),
(2,	6),
(2,	7),
(1,	8),
(1,	10),
(2,	11),
(1,	12),
(2,	13),
(1,	14),
(1,	15),
(1,	16),
(1,	17),
(2,	18),
(1,	19),
(2,	20),
(1,	21),
(2,	22),
(1,	23),
(1,	24),
(2,	25),
(2,	26),
(1,	27),
(2,	28),
(1,	29),
(1,	30),
(1,	31),
(2,	32),
(2,	33),
(1,	34),
(2,	35),
(1,	36),
(1,	37),
(2,	38),
(1,	39),
(2,	40),
(1,	41),
(2,	42),
(2,	43),
(2,	44),
(2,	45);

-- end attached script 'script'
