DROP DATABASE IF EXISTS medicalcenter;
CREATE DATABASE IF NOT EXISTS medicalcenter;
SHOW DATABASES ;
USE medicalcenter;
#-------------------


DROP TABLE IF EXISTS patient;
CREATE TABLE IF NOT EXISTS patient(
    pId VARCHAR(6) NOT NULL,
    name VARCHAR(50) NOT NULL DEFAULT 'Unknown',
    address VARCHAR(50),
    contactNumber VARCHAR(20),
    bloodPressure double(10,2),
    bodyTemperature double(10,2),
    CONSTRAINT PRIMARY KEY (pId)
);
SHOW TABLES ;
DESCRIBE patient;

DROP TABLE IF EXISTS doctor;
CREATE TABLE IF NOT EXISTS doctor(
    doctorId VARCHAR(6)NOT NULL,
    name VARCHAR(100) NOT NULL DEFAULT 'Unknown',
    address VARCHAR(100),
    contactNumber VARCHAR(20),
    type VARCHAR(100),
    CONSTRAINT PRIMARY KEY (doctorId)
);
SHOW TABLES ;
DESCRIBE doctor;


DROP TABLE IF EXISTS nurse;
CREATE TABLE IF NOT EXISTS nurse(
    nurseId VARCHAR(6)NOT NULL,
    name VARCHAR(100) NOT NULL DEFAULT 'Unknown',
    address VARCHAR(100),
    contactNumber VARCHAR(20),
    CONSTRAINT PRIMARY KEY (nurseId)
);
SHOW TABLES ;
DESCRIBE nurse;


DROP TABLE IF EXISTS channel;
CREATE TABLE IF NOT EXISTS channel(
    cId VARCHAR(6)NOT NULL,
    pId VARCHAR(6),
    doctorId VARCHAR(6),
    speciality VARCHAR(100),
    nurseId VARCHAR(6),
    time TIME,
    fee DOUBLE(6,2),
    CONSTRAINT PRIMARY KEY (cId),
    FOREIGN KEY (pId) REFERENCES patient(PID) ON DELETE CASCADE ON UPDATE CASCADE ,
    FOREIGN KEY (doctorId) REFERENCES doctor(doctorId)ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (nurseId) REFERENCES nurse(nurseId)ON DELETE CASCADE ON UPDATE CASCADE

);
SHOW TABLES ;
DESCRIBE channel;

DROP TABLE IF EXISTS loginDetails;
CREATE TABLE IF NOT EXISTS loginDetails(
    userId VARCHAR(6) NOT NULL,
    userName VARCHAR(50),
    password VARCHAR(50),
    userRole VARCHAR(50),
    CONSTRAINT PRIMARY KEY (userId)
);
SHOW TABLES ;
DESCRIBE loginDetails;

INSERT INTO loginDetails (userId, userName, password, userRole)
VALUES
('U001','admin','12345','Managment'),
('U002','D001','12345','Doctor'),
('U003','D002','12345','Doctor'),
('U004','R001','12345','Reception'),
('U005','P001','12345','Pharmacist');

DROP TABLE IF EXISTS supplier;
CREATE TABLE IF NOT EXISTS supplier(
    supId VARCHAR(6) NOT NULL ,
    name VARCHAR(50),
    address VARCHAR(50),
    contactNo VARCHAR(20),
    CONSTRAINT PRIMARY KEY (supId)
);
SHOW TABLES ;
DESCRIBE supplier;

INSERT INTO supplier (supId, name, address, contactNo)
VALUES
('S001','Amal Perera','Colombo','0718823450'),
('S002','Kamal Silva','Kaluthara','0728833849'),
('S003','Sumudu Sadaruwan','Panadura','0768833893')
;

DROP TABLE IF EXISTS medicine;
CREATE TABLE IF NOT EXISTS medicine(
    mId VARCHAR(6)NOT NULL ,
    description VARCHAR(50),
    type VARCHAR(50),
    size INT(20),
    price DOUBLE(10,2),
    qty INT (10),
    CONSTRAINT PRIMARY KEY (mId)
);
SHOW TABLES ;
DESCRIBE medicine;

DROP TABLE IF EXISTS suppliedDetails;
CREATE TABLE IF NOT EXISTS suppliedDetails(
    medicineId VARCHAR(6)NOT NULL ,
    supId VARCHAR(6)NOT NULL ,
    qty INT(6),
    CONSTRAINT PRIMARY KEY (medicineId,supId),
    FOREIGN KEY (medicineId) REFERENCES medicine(mId)  ON DELETE CASCADE ON UPDATE CASCADE ,
    FOREIGN KEY (supId) REFERENCES supplier(supId)  ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE suppliedDetails;

DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders(
    orderId VARCHAR(6)NOT NULL ,
    pId VARCHAR(6),
    patientName VARCHAR(50),
    subTotal DOUBLE(10,2),
    time TIME,
    date DATE,
    CONSTRAINT PRIMARY KEY (orderId),
    FOREIGN KEY (pId) REFERENCES patient(pId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE orders;

DROP TABLE IF EXISTS orderDetails;
CREATE TABLE IF NOT EXISTS orderDetails(
    mId VARCHAR(6)NOT NULL ,
    description VARCHAR(50),
    orderId VARCHAR(6)NOT NULL ,
    qty INT(5),
    totalPrice DOUBLE(10,2),
    CONSTRAINT PRIMARY KEY (mId,orderId),
    FOREIGN KEY (mId)REFERENCES medicine(mId)ON DELETE CASCADE ON UPDATE CASCADE ,
    FOREIGN KEY (orderId)REFERENCES orders(orderId)ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE orderDetails;


DROP TABLE IF EXISTS channelFee;
CREATE TABLE IF NOT EXISTS channelFee(
    channelId VARCHAR(6)NOT NULL ,
    patientName VARCHAR(100)NOT NULL ,
    doctorName VARCHAR(100)NOT NULL ,
    date DATE,
    time TIME,
    fee DOUBLE(10,2),
    CONSTRAINT PRIMARY KEY (channelId)

);
SHOW TABLES ;
DESCRIBE channelFee;


