INSERT INTO conjunto (id, nombre, nit, direccion, telefono, tipo_conjunto) 
VALUES (1, 'Conjunto Residencial Altos de San Juan', '900123456-1', 'Calle 100 # 45-20', '3101234567', 'TORRES');

INSERT INTO usuario (id, id_conjunto, nombre, username, password, rol, activo) VALUES
(1, 1, 'Carlos Perez (Admin)', 'admin', 'admin123', 'ADMIN_CONJUNTO', 1),
(2, 1, 'Ana Gomez (Supervisor)', 'supervisor', 'super123', 'SUPERVISOR', 1),
(3, 1, 'Juan Vigilante', 'vigilante', 'vigi123', 'VIGILANTE', 1);

INSERT INTO unidad (id, id_conjunto, torre, numero, tipo, coeficiente) VALUES
(1, 1, 'Torre A', '101', 'APARTAMENTO', 1.5),
(2, 1, 'Torre A', '102', 'APARTAMENTO', 1.5),
(3, 1, 'Torre B', '201', 'APARTAMENTO', 2.0),
(4, 1, 'Torre B', '202', 'APARTAMENTO', 2.0),
(5, 1, 'Casa', '01', 'CASA', 3.5);

INSERT INTO residente (id, id_conjunto, id_unidad, nombre, documento, telefono, email, tipo) VALUES
(1, 1, 1, 'Maria Rodriguez', '1032456789', '3209876543', 'maria@email.com', 'PROPIETARIO'),
(2, 1, 2, 'Pedro Infante', '79123456', '3112223344', 'pedro@email.com', 'ARRENDATARIO'),
(3, 1, 3, 'Lucia Mendez', '52987654', '3157778899', 'lucia@email.com', 'PROPIETARIO');

INSERT INTO vehiculo (id, id_conjunto, id_residente, placa, marca, color, tipo) VALUES
(1, 1, 1, 'ABC123', 'Chevrolet', 'Blanco', 'AUTOMOVIL'),
(2, 1, 2, 'XYZ789', 'Renault', 'Gris', 'AUTOMOVIL'),
(3, 1, 3, 'MNO456', 'Yamaha', 'Negro', 'MOTO');

INSERT INTO visitante (id, id_conjunto, nombre, documento, telefono, observaciones, lista_negra) VALUES
(1, 1, 'Carlos Visitante', '99887766', '3001112233', 'Ninguna', 0),
(2, 1, 'Hacker Maligno', '66666666', '3000000000', 'Prohibido el ingreso por robo', 1);

INSERT INTO punto_control (id, id_conjunto, codigo_qr, nombre, ubicacion) VALUES
(1, 1, 'QR-TORRE-A-LOBBY', 'Lobby Torre A', 'Entrada Torre A'),
(2, 1, 'QR-TORRE-B-LOBBY', 'Lobby Torre B', 'Entrada Torre B'),
(3, 1, 'QR-PARQUEADERO-S1', 'Parqueadero S1', 'Sótano 1');
