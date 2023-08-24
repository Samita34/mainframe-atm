
-- Crear la tabla de usuarios (modificada para incluir el nombre)
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    pin INT NOT NULL,
    saldo DOUBLE NOT NULL
);

-- Crear la tabla de historico
CREATE TABLE IF NOT EXISTS historico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    tipo_operacion VARCHAR(50) NOT NULL,
    cantidad DOUBLE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Insertar datos de ejemplo
-- Insertar datos de ejemplo en usuarios
INSERT INTO usuarios (nombre, pin, saldo,alias) VALUES 
('Juan Perez', 1234, 1000.0,"jperez"),
('Ana Ramirez', 5678, 2500.0,"aramirez"),
('Carlos Gomez', 9012, 500.0,"cgomez"),
('Marta Torres', 3456, 750.0,"mtorres"),
('Luisa Fernandez', 7890, 3000.0,"lfernandez");

-- Insertar datos de ejemplo en historico (asumiendo que los IDs de los usuarios coinciden con los valores insertados anteriormente)
-- Juan Perez hizo un depósito de 200.0
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (1, 'depósito', 1000.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (2, 'depósito', 2500.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (3, 'depósito', 500.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (4, 'depósito', 750.0);
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (5, 'depósito', 3000.0);

--Trigger registra depositos

DELIMITER //
CREATE TRIGGER depositos
AFTER UPDATE ON usuarios
FOR EACH ROW
BEGIN
    IF NEW.saldo > OLD.saldo THEN
        INSERT INTO historico (usuario_id, tipo_operacion, cantidad)
        VALUES (NEW.id, 'Deposito', NEW.saldo - OLD.saldo);
    END IF;
END;
//
DELIMITER ;


-- Trigger registra retiros:

DELIMITER //
CREATE TRIGGER retiros
AFTER UPDATE ON usuarios
FOR EACH ROW
BEGIN
    IF NEW.saldo < OLD.saldo THEN
        INSERT INTO historico (usuario_id, tipo_operacion, cantidad)
        VALUES (NEW.id, 'Retiro', OLD.saldo - NEW.saldo);
    END IF;
END;
//
DELIMITER ;


