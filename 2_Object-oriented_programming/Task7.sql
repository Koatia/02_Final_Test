-- - В ранее подключенном MySQL создать базу данных с названием "Human Friends"
DROP database IF EXISTS HumanFriends;
CREATE database HumanFriends;
USE HumanFriends;

-- Создать таблицы, соответствующие иерархии из вашей диаграммы классов
DROP TABLE IF EXISTS Pets;
CREATE TABLE Pets (
 id INT AUTO_INCREMENT PRIMARY KEY,
 Name VARCHAR(45) NOT NULL,
 Type VARCHAR(45) NOT NULL,
 BirthDate DATE,
 Commands VARCHAR(255)
);

DROP TABLE IF EXISTS PackAnimals;
CREATE TABLE PackAnimals (
 id INT AUTO_INCREMENT PRIMARY KEY,
 Name VARCHAR(45) NOT NULL,
 Type VARCHAR(45) NOT NULL,
 BirthDate DATE,
 Commands VARCHAR(255)
);





INSERT INTO itresume10019400.mobile_phones (product_name, manufacturer, product_count, price)
VALUES ('iPhone X', 'Apple', 156, 76000),
 ('iPhone 8', 'Apple', 180, 51000),
 ('Galaxy S9', 'Samsung', 21, 56000),
 ('Galaxy S8', 'Samsung', 124, 41000),
 ('P20 Pro', 'Huawei', 341, 36000);
 
 SELECT * FROM itresume10019400.mobile_phones;