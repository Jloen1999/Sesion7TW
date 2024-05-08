create database cartabd;
create user 'tw'@localhost
    identified by 'tw2324';
GRANT ALL PRIVILEGES on cartabd.*
    TO 'tw'@localhost;
flush privileges;

use cartabd;

CREATE TABLE IF NOT EXISTS Usuarios
(
    id        INT(11) AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(30) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    email     varchar(50) NOT NULL,
    username  varchar(10) DEFAULT NULL,
    password  varchar(10) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS Cartas
(
    id           INT(11) AUTO_INCREMENT PRIMARY KEY,
    id_usuario   INT(11),
    titulo_carta VARCHAR(100),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

CREATE TABLE IF NOT EXISTS Regalos
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    cantidad      INT DEFAULT 1,
    id_carta      INT,
    FOREIGN KEY (id_carta) REFERENCES Cartas (id),
    nombre_regalo VARCHAR(100)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;