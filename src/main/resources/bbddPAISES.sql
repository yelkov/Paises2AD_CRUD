DROP DATABASE IF EXISTS paises;
CREATE DATABASE IF NOT EXISTS paises;
USE paises;

DROP TABLE IF EXISTS PAISES;
CREATE TABLE IF NOT EXISTS PAISES (
	NOMBRE CHAR(30) NOT NULL,
    NUM_HABITANTES INTEGER NOT NULL,
    CAPITAL CHAR(30) NOT NULL,
    MONEDA CHAR(25) NOT NULL,
    /*********************/
    PRIMARY KEY (NOMBRE)
) ENGINE INNODB; 

INSERT INTO PAISES (NOMBRE, NUM_HABITANTES, CAPITAL, MONEDA) VALUES
('Argentina', 45376763, 'Buenos Aires', 'Peso'),
('Brasil', 212559417, 'Brasilia', 'Real'),
('Canadá', 37742154, 'Ottawa', 'Dólar canadiense'),
('Francia', 65273511, 'París', 'Euro'),
('Japón', 125960000, 'Tokio', 'Yen'),
('México', 128932753, 'Ciudad de México', 'Peso'),
('Alemania', 83166711, 'Berlín', 'Euro'),
('Australia', 25687041, 'Canberra', 'Dólar australiano'),
('China', 1439323776, 'Pekín', 'Renminbi'),
('India', 1380004385, 'Nueva Delhi', 'Rupia');