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
  `code` VARCHAR(45) NOT NULL,
  `ssd` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `credits` DOUBLE NULL DEFAULT 0,
  `students` DOUBLE NULL DEFAULT 0,
  `hours` DOUBLE NULL DEFAULT 0,
  `param` VARCHAR(45) NULL,
  `period` DOUBLE NOT NULL,
  `year` DOUBLE NOT NULL,
  `yearOff` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idcourse`),
  UNIQUE INDEX `uq_courses` (`code` ASC, `ssd` ASC, `yearOff` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `course_management`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_management`.`department` (
  `iddepartment` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `city` VARCHAR(45) NULL,
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
  `firstname` VARCHAR(45) NULL,
  `middlename` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `sex` VARCHAR(45) NULL,
  `phonenumber` VARCHAR(45) NULL,
  `roomnumber` VARCHAR(45) NULL,
  `streetname` VARCHAR(255) NULL,
  `streetnumber` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
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
  `yearstart` VARCHAR(45) NULL,
  `yearend` VARCHAR(45) NULL,
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
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
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
  `name` VARCHAR(45) NULL DEFAULT 'Name',
  `surname` VARCHAR(45) NULL DEFAULT 'Surname',
  PRIMARY KEY (`iduser`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE USER 'cm_user'@'%' IDENTIFIED BY 'cm_pass';

GRANT ALL ON `course_management`.* TO 'cm_user';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- begin attached script 'script'
INSERT INTO `course_management`.`user` (`iduser`, `username`, `hashedPassword`, `role`, `profilePictureUrl`, `name`, `surname`) VALUES (DEFAULT, 'admin', '$2a$12$RcvwttwSag7Uw8Uq41O1t.YoZe32P9tMGjGD.i3e3LglwGkzqFwJ2', 'admin', 'images/logo.png', 'Name', 'Surname');

-- end attached script 'script'
