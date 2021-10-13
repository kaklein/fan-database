/*
Use this file to initialize a database called databasedb
which will work with the Fans.java file in the fan-database project.

To initialize the database:
    1. Download this file
    2. Open the MySQL command line on your terminal
        - MySQL must be downloaded; check by typing 'mysql --version'
        - type 'mysql -u root -p'
        - type your root password and press enter
    3. Type 'source <path/to/file/fan-database-init.sql>;' and press enter    
*/

-- Create database
CREATE DATABASE IF NOT EXISTS databasedb;

-- Drop user if exists
DROP USER IF EXISTS 'student1'@'localhost';

-- Create user and grant privileges
CREATE USER 'student1'@'localhost' IDENTIFIED WITH mysql_native_password BY 'pass';
GRANT ALL PRIVILEGES ON databasedb.* TO 'student1'@'localhost';

-- Use database
USE databasedb;

-- Create fans table
DROP TABLE IF EXISTS fans;
CREATE TABLE fans (
    ID INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(25),
    lastname VARCHAR(25),
    favoriteteam VARCHAR(25),
    PRIMARY KEY (ID)
);

-- Insert sample data into table
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Nathan', 'Hill', 'Vikings');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Christian', 'Murray', 'Packers');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Lillian', 'Ogden', 'Patriots');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Trevor', 'Butler', 'Rams');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Tracey', 'Hunter', 'Rams');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Gavin', 'Lewis', 'Seahawks');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Bernadette', 'Peterson', 'Vikings');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Stewart', 'Knox', 'Bears');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Sam', 'Walters', 'Eagles');
INSERT INTO fans (firstname, lastname, favoriteteam)
    VALUES ('Andrea', 'Mackay', 'Broncos');