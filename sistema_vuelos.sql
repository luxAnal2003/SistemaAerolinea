CREATE DATABASE IF NOT EXISTS sistema_vuelos;
USE sistema_vuelos;

CREATE TABLE clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cedula VARCHAR(10) UNIQUE NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(15) NOT NULL,
    celular VARCHAR(10) NOT NULL,
    fecha_ultima_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE aeronaves (
    id_aeronave INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100),
    capacidad INT,
    estado ENUM('Activo','Mantenimiento','Inactivo') DEFAULT 'Activo'
) ENGINE=InnoDB;

CREATE TABLE tripulacion (
    id_tripulante INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(10),
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    rol ENUM('Piloto','Copiloto','Asistente'),
    licencia VARCHAR(50) UNIQUE
) ENGINE=InnoDB;

CREATE TABLE vuelos (
    id_vuelo INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10),
    aerolinea VARCHAR(50),
    origen VARCHAR(50),
    destino VARCHAR(50),
    fecha_salida DATE,
    hora_salida TIME,
    hora_llegada TIME,
    cupos INT,
    estado VARCHAR(20),
    precio_base DOUBLE,
    id_aeronave INT,
    FOREIGN KEY (id_aeronave) REFERENCES aeronaves(id_aeronave)
) ENGINE=InnoDB;

CREATE TABLE vuelo_tripulacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_vuelo INT,
    id_tripulante INT,
    FOREIGN KEY (id_vuelo) REFERENCES vuelos(id_vuelo),
    FOREIGN KEY (id_tripulante) REFERENCES tripulacion(id_tripulante)
) ENGINE=InnoDB;

INSERT INTO clientes (cedula, nombres, apellidos, email, password, celular)
VALUES
('1456738393', 'Daniel', 'Ríos', 'daniel@test.com', 'Pass1234', '0991234567'),
('0987654321', 'María', 'Gómez', 'maria@test.com', 'Pass5678', '0987654321');

INSERT INTO aeronaves (modelo, capacidad, estado)
VALUES
('Boeing 737', 180, 'Activo'),
('Airbus A320', 150, 'Activo'),
('Boeing 747', 400, 'Mantenimiento');

INSERT INTO tripulacion (cedula, nombre, apellido, rol, licencia)
VALUES
('0911111111', 'Carlos', 'Mendoza', 'Piloto', 'LIC-PIL-001'),
('0922222222', 'Luis', 'Torres', 'Copiloto', 'LIC-COP-002'),
('0933333333', 'Ana', 'Pérez', 'Asistente', 'LIC-ASI-003'),
('0944444444', 'José', 'Ramírez', 'Asistente', 'LIC-ASI-004'),
('1234567867', 'Eddy', 'Pérez', 'Asistente', 'LIC-ASI-005'),
('3456342343', 'Marcos', 'Ramírez', 'Asistente', 'LIC-ASI-006'),
('3476894523', 'Steff', 'Pérez', 'Asistente', 'LIC-ASI-007'),
('1235278457', 'July', 'Ramírez', 'Asistente', 'LIC-ASI-008');

INSERT INTO vuelos (
    codigo, aerolinea, origen, destino,
    fecha_salida, hora_salida, hora_llegada,
    cupos, estado, precio_base, id_aeronave
)
VALUES
('AV101', 'Avianca', 'Guayaquil', 'Quito',
 '2026-05-20', '08:30:00', '09:30:00',
 120, 'Activo', 120.50, 1),

('LA202', 'LATAM', 'Quito', 'Cuenca',
 '2026-05-21', '10:00:00', '11:00:00',
 100, 'Activo', 90.00, 2),

('IB303', 'Iberia', 'Guayaquil', 'Madrid',
 '2026-06-01', '18:00:00', '10:00:00',
 300, 'Activo', 850.00, 1);

INSERT INTO vuelo_tripulacion (id_vuelo, id_tripulante)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 4),
(3, 1);

CREATE TABLE reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_vuelos INT NOT NULL,
    cantidad_pasajeros INT NOT NULL,
    precio_total DECIMAL(10,2),
    fecha_reserva DATE,
    estado VARCHAR(20),
    asiento VARCHAR(10),

    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_vuelos) REFERENCES vuelos(id_vuelo)
);

CREATE TABLE pasajeros_extra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_vuelo INT NOT NULL,

    nombre VARCHAR(100),
    identificacion VARCHAR(15),
    fecha_nacimiento DATE,
    asiento VARCHAR(10),

    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_vuelo) REFERENCES vuelos(id_vuelo)
);

INSERT INTO reservas (
    id_cliente, id_vuelos, cantidad_pasajeros,
    precio_total, fecha_reserva, estado, asiento
)
VALUES
(1, 1, 2, 241.00, '2026-05-10', 'Confirmada','3A'),
(2, 2, 1, 90.00, '2026-05-11', 'Pendiente', '3A');

INSERT INTO pasajeros_extra (
    id_cliente, id_vuelo, nombre,
    identificacion, fecha_nacimiento, asiento
)
VALUES
(1, 1, 'Daniel Ríos', '1234567890', '1998-05-10', '1A'),
(1, 1, 'Laura Vega', '1112223334', '2000-08-15', '1B'),
(2, 2, 'María Gómez', '0987654321', '1995-03-20', '2A');


select * from reservas;
