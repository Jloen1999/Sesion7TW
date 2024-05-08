-- Insertar 5 registros en la tabla Usuarios
INSERT INTO Usuarios (nombre, apellidos, email, username, password)
VALUES
    ('Jose Luis', 'Obiang', 'joseluis@gmail.com', 'admin', 'admin'),
    ('Juan', 'Perez', 'juan@example.com', 'juanp', '123456'),
    ('Maria', 'Garcia', 'maria@example.com', 'mariag', 'abcdef'),
    ('Pedro', 'Lopez', 'pedro@example.com', 'pedrol', 'qwerty'),
    ('Laura', 'Martinez', 'laura@example.com', 'lauram', 'asdfgh'),
    ('Luis', 'Gomez', 'luis@example.com', 'luisg', 'zxcvbn');

-- Insertar registros en la tabla Cartas
INSERT INTO Cartas (id_usuario, titulo_carta)
VALUES
    (1, 'Carta del administrador'),
    (2, 'Carta de Juan'),
    (3, 'Carta de Maria'),
    (4, 'Carta de Pedro'),
    (5, 'Carta de Laura'),
    (6, 'Carta de Luis');

-- Insertar registros en la tabla Regalos
INSERT INTO Regalos (id_carta, nombre_regalo, cantidad)
VALUES
    (1, 'Libro Cien dias de soledad', 2),
    (1, 'Tarjeta de regalo para una cena en un restaurante elegante', 1),
    (2, 'Flores de temporada', 3),
    (3, 'Caja de chocolates belgas', 1),
    (4, 'Colgante de plata con forma de corazon', 1),
    (5, 'album de fotos personalizado', 1),
    (5, 'Juego de copas de cristal para vino', 1),
    (5, 'Caja de bombones surtidos', 1),
    (5, 'Vale de spa para un dia de relajacion', 1),
    (5, 'Suscripcion anual a una revista de arte', 1),
    (6, 'Caja de bombones surtidos', 1),
    (6, 'Vale de spa para un dia de relajacion', 1),
    (6, 'Suscripcion anual a una revista de arte', 1);
