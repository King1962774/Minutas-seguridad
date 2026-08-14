PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS conjunto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    nit TEXT,
    direccion TEXT,
    telefono TEXT,
    tipo_conjunto TEXT DEFAULT 'TORRES', -- TORRES, CASAS, MIXTO, EDIFICIO_UNICO, CAMPESTRE
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    nombre TEXT NOT NULL,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    rol TEXT NOT NULL, -- VIGILANTE, SUPERVISOR, ADMIN_CONJUNTO, SUPERADMIN
    activo INTEGER DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS unidad (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    torre TEXT NOT NULL,
    numero TEXT NOT NULL,
    tipo TEXT DEFAULT 'APARTAMENTO', -- APARTAMENTO, CASA, LOCAL
    coeficiente REAL DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS residente (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_unidad INTEGER REFERENCES unidad(id),
    nombre TEXT NOT NULL,
    documento TEXT UNIQUE NOT NULL,
    telefono TEXT,
    email TEXT,
    tipo TEXT DEFAULT 'PROPIETARIO', -- PROPIETARIO, ARRENDATARIO, RESIDENTE
    activo INTEGER DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS turno (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_usuario INTEGER NOT NULL REFERENCES usuario(id),
    puesto TEXT NOT NULL, -- PRINCIPAL, VEHICULAR, PEATONAL
    tipo TEXT NOT NULL, -- DIA, NOCHE
    hora_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hora_fin TIMESTAMP,
    estado TEXT DEFAULT 'ABIERTO' -- ABIERTO, CERRADO
);

CREATE TABLE IF NOT EXISTS informe_turno (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_turno INTEGER NOT NULL REFERENCES turno(id),
    resumen_visitantes INTEGER DEFAULT 0,
    resumen_vehiculos INTEGER DEFAULT 0,
    resumen_paquetes INTEGER DEFAULT 0,
    pendientes TEXT,
    firma_entrega TEXT,
    firma_recibo TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS novedad_turno (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_turno INTEGER NOT NULL REFERENCES turno(id),
    categoria TEXT NOT NULL, -- GENERAL, SEGURIDAD, MANTENIMIENTO, QUEJA
    descripcion TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS visitante (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    nombre TEXT NOT NULL,
    documento TEXT NOT NULL,
    telefono TEXT,
    observaciones TEXT,
    lista_negra INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS registro_visita (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_visitante INTEGER NOT NULL REFERENCES visitante(id),
    id_unidad INTEGER REFERENCES unidad(id),
    id_residente INTEGER REFERENCES residente(id),
    id_turno INTEGER REFERENCES turno(id),
    vehiculo_placa TEXT,
    observacion TEXT,
    hora_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hora_salida TIMESTAMP,
    estado TEXT DEFAULT 'DENTRO' -- DENTRO, SALIO, VENCIDO
);

CREATE TABLE IF NOT EXISTS vehiculo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_residente INTEGER REFERENCES residente(id),
    placa TEXT UNIQUE NOT NULL,
    marca TEXT,
    modelo TEXT,
    color TEXT,
    tipo TEXT DEFAULT 'AUTOMOVIL' -- AUTOMOVIL, MOTO, BICICLETA
);

CREATE TABLE IF NOT EXISTS registro_vehicular (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_vehiculo INTEGER REFERENCES vehiculo(id),
    placa TEXT NOT NULL,
    parqueadero TEXT,
    hora_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hora_salida TIMESTAMP,
    tipo TEXT DEFAULT 'RESIDENTE' -- RESIDENTE, VISITANTE
);

CREATE TABLE IF NOT EXISTS paquete (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_unidad INTEGER REFERENCES unidad(id),
    id_residente INTEGER REFERENCES residente(id),
    empresa_mensajeria TEXT,
    guia TEXT,
    descripcion TEXT,
    recibido_por INTEGER REFERENCES usuario(id),
    estado TEXT DEFAULT 'PENDIENTE', -- PENDIENTE, ENTREGADO
    hora_recepcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    hora_entrega TIMESTAMP,
    firma_entrega TEXT
);

CREATE TABLE IF NOT EXISTS mascota (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_unidad INTEGER REFERENCES unidad(id),
    nombre TEXT NOT NULL,
    especie TEXT DEFAULT 'PERRO', -- PERRO, GATO, OTRO
    raza TEXT,
    color TEXT,
    vacuna_vigente INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS punto_control (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    codigo_qr TEXT UNIQUE NOT NULL,
    nombre TEXT NOT NULL,
    ubicacion TEXT
);

CREATE TABLE IF NOT EXISTS registro_ronda (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_punto INTEGER NOT NULL REFERENCES punto_control(id),
    id_turno INTEGER NOT NULL REFERENCES turno(id),
    id_usuario INTEGER NOT NULL REFERENCES usuario(id),
    observacion TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS incidente (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_turno INTEGER REFERENCES turno(id),
    id_usuario INTEGER REFERENCES usuario(id),
    tipo TEXT NOT NULL, -- PANICO, ACCIDENTE, ROBO, OTRO
    descripcion TEXT NOT NULL,
    atendido INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reserva_zona_comun (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_residente INTEGER REFERENCES residente(id),
    zona TEXT NOT NULL, -- SALON_SOCIAL, BBQ, CANCHA, PISCINA
    fecha_reserva DATE NOT NULL,
    estado TEXT DEFAULT 'CONFIRMADA' -- CONFIRMADA, CANCELADA, COMPLETADA
);

CREATE TABLE IF NOT EXISTS pqrs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_residente INTEGER REFERENCES residente(id),
    tipo TEXT NOT NULL, -- PETICION, QUEJA, RECLAMO, SUGERENCIA
    asunto TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    estado TEXT DEFAULT 'ABIERTO', -- ABIERTO, EN_PROCESO, CERRADO
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS audit_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conjunto INTEGER NOT NULL REFERENCES conjunto(id),
    id_usuario INTEGER REFERENCES usuario(id),
    accion TEXT NOT NULL,
    entidad TEXT NOT NULL,
    detalles TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pendientes_sync (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    endpoint TEXT NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
