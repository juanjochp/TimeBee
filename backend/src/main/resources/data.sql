-- CONTRASEÑA ENCRIPTADA PARA '1234' CON BCrypt
-- $2a$10$Dow1P.N/PaE3uQIQZxW6VewjAjj6U9GKY9vCD7QTCQUn2cdhBSs/C

-- Insert 1: Empresa tipo SL
INSERT INTO EMPRESA (
    NOMBRE, FORMA_JURIDICA, CIF, DIRECCION, TELEFONO, EMAIL, PASSWORD, ROL, ACTIVO
) VALUES (
    'TechSolutions SL', 'SOCIEDAD_LIMITADA', 'B12345678', 'Calle Innovación 123, Madrid', 912345678,
    'consultora@timebee.com',
    '$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu',
    'EMPRESA', TRUE
);

-- Insert 2: Empresa tipo Autónomo
INSERT INTO EMPRESA (
    NOMBRE, FORMA_JURIDICA, CIF, DIRECCION, TELEFONO, EMAIL, PASSWORD, ROL, ACTIVO
) VALUES (
    'Consultoría Pérez', 'AUTONOMO', 'X87654321', 'Avenida Central 45, Barcelona', 934567890,
    'consultora2@timebee.com',
    '$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu',
    'EMPRESA', TRUE
);

INSERT INTO trabajador (
  activo, fecha_antiguedad, fecha_nacimiento, telefono, empresa_id,
  dni, naf, iban, apellidos, categoria, email, nombre,
  password, genero, rol
) VALUES
(b'1','2024-01-10','1985-06-15',612345001,1,'12345601A','NAF123456001','ES7620770024003102575701','García López','Desarrollador','juan.lopez@empresa.com','Juan',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2024-02-12','1990-07-20',612345002,1,'12345602B','NAF123456002','ES7620770024003102575702','Pérez Sánchez','Analista','ana.perez@empresa.com','Ana',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2020-03-15','1988-03-30',612345003,1,'12345603C','NAF123456003','ES7620770024003102575703','Ruiz Martínez','Diseñador','carlos.ruiz@empresa.com','Carlos',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2020-04-18','1987-09-12',612345004,1,'12345604D','NAF123456004','ES7620770024003102575704','Gómez Torres','Tester','laura.gomez@empresa.com','Laura',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2020-05-10','1992-11-01',612345005,1,'12345605E','NAF123456005','ES7620770024003102575705','Sánchez Molina','Backend','david.sanchez@empresa.com','David',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2020-06-22','1989-05-17',612345006,1,'12345606F','NAF123456006','ES7620770024003102575706','Fernández Herrera','Frontend','marta.fernandez@empresa.com','Marta',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2020-07-25','1986-01-30',612345007,1,'12345607G','NAF123456007','ES7620770024003102575707','López Ramos','UX/UI','jorge.lopez@empresa.com','Jorge',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2020-08-01','1991-03-22',612345008,1,'12345608H','NAF123456008','ES7620770024003102575708','Navarro Castro','QA Engineer','elena.navarro@empresa.com','Elena',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2020-09-03','1993-04-10',612345009,1,'12345609I','NAF123456009','ES7620770024003102575709','Jiménez Díaz','Desarrollador','lucas.jimenez@empresa.com','Lucas',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2020-10-11','1984-08-05',612345010,1,'12345610J','NAF123456010','ES7620770024003102575710','Ortega Gil','Administrador','patricia.ortega@empresa.com','Patricia',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),

(b'1','2020-11-12','1980-02-14',612345011,1,'12345611K','NAF123456011','ES7620770024003102575711','Romero Serrano','Soporte','andres.romero@empresa.com','Andrés',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2020-12-01','1994-07-28',612345012,1,'12345612L','NAF123456012','ES7620770024003102575712','Cano Vargas','Contabilidad','eva.cano@empresa.com','Eva',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2021-01-15','1995-12-09',612345013,1,'12345613M','NAF123456013','ES7620770024003102575713','Iglesias Peña','Scrum Master','pablo.iglesias@empresa.com','Pablo',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2021-02-27','1982-03-15',612345014,1,'12345614N','NAF123456014','ES7620770024003102575714','Delgado Reyes','Diseñador','laia.delgado@empresa.com','Laia',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2021-03-13','1981-11-11',612345015,1,'12345615O','NAF123456015','ES7620770024003102575715','Méndez Cabrera','Ingeniero DevOps','raul.mendez@empresa.com','Raúl',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2021-04-21','1983-06-25',612345016,1,'12345616P','NAF123456016','ES7620770024003102575716','Rey Zamora','Product Owner','ines.rey@empresa.com','Inés',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2021-05-07','1996-09-07',612345017,1,'12345617Q','NAF123456017','ES7620770024003102575717','Silva Nieto','QA Tester','gonzalo.silva@empresa.com','Gonzalo',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2021-06-19','1997-10-03',612345018,1,'12345618R','NAF123456018','ES7620770024003102575718','Peña Bravo','Administración','clara.pena@empresa.com','Clara',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR'),
(b'1','2021-07-10','1985-01-01',612345019,1,'12345619S','NAF123456019','ES7620770024003102575719','Cruz Soto','Analista','javier.cruz@empresa.com','Javier',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','MASCULINO','TRABAJADOR'),
(b'1','2021-08-30','1992-08-22',612345020,1,'12345620T','NAF123456020','ES7620770024003102575720','León Acosta','RRHH','sofia.leon@empresa.com','Sofía',
'$2a$10$SLnhOQtbS0EfK1sWHlDCAeonwz3dgBsMINmUvDT4VWsCN8wPbhKYu','FEMENINO','TRABAJADOR');

INSERT INTO nomina (
    periodo,
    empresa_id,
    fecha_subida,
    tamano,
    trabajador_id,
    content_type,
    nombre_archivo,
    archivo_pdf
) VALUES
  ('2025-01-01', 1, '2025-01-10 09:00:00', 102400, 2, 'application/pdf', 'nomina_enero_2025.pdf', x'00'),
  ('2025-02-01', 1, '2025-02-10 09:00:00', 104857, 2, 'application/pdf', 'nomina_febrero_2025.pdf', x'00'),
  ('2025-03-01', 1, '2025-03-10 09:00:00',  99876, 2, 'application/pdf', 'nomina_marzo_2025.pdf', x'00'),
  ('2025-04-01', 1, '2025-04-10 09:00:00', 110000, 2, 'application/pdf', 'nomina_abril_2025.pdf', x'00'),
  ('2025-05-01', 1, '2025-05-10 09:00:00', 105432, 2, 'application/pdf', 'nomina_mayo_2025.pdf', x'00');


-- FICHAJES

INSERT INTO fichaje (empresa_id, trabajador_id, fecha_inicio, fecha_fin) VALUES
(1, 1, '2024-01-16 08:30:00', '2024-01-16 13:15:00'),
(1, 1, '2024-01-16 14:15:00', '2024-01-16 17:45:00'),
(1, 1, '2024-01-17 09:00:00', '2024-01-17 14:00:00'),
(1, 2, '2024-02-01 08:15:00', '2024-02-01 12:30:00'),
(1, 2, '2024-02-01 14:00:00', '2024-02-01 18:20:00'),
(1, 2, '2024-02-02 09:30:00', '2024-02-02 19:45:00');

-- Inserta 20 fichajes para el trabajador_id = 2 y empresa_id = 1
INSERT INTO fichaje (empresa_id, fecha_inicio, fecha_fin, trabajador_id)
VALUES
  (1, '2025-04-01 09:00:00', '2025-04-01 17:00:00', 2),
  (1, '2025-04-02 09:15:00', '2025-04-02 17:30:00', 2),
  (1, '2025-04-03 08:45:00', '2025-04-03 16:45:00', 2),
  (1, '2025-04-04 09:00:00', '2025-04-04 17:00:00', 2),
  (1, '2025-04-05 09:30:00', '2025-04-05 18:00:00', 2),
  (1, '2025-04-06 08:50:00', '2025-04-06 17:10:00', 2),
  (1, '2025-04-07 09:05:00', '2025-04-07 17:20:00', 2),
  (1, '2025-04-08 09:00:00', '2025-04-08 17:00:00', 2),
  (1, '2025-04-09 09:10:00', '2025-04-09 17:15:00', 2),
  (1, '2025-04-10 08:55:00', '2025-04-10 17:05:00', 2),
  (1, '2025-04-11 09:00:00', '2025-04-11 17:00:00', 2),
  (1, '2025-04-12 09:20:00', '2025-04-12 17:25:00', 2),
  (1, '2025-04-13 08:40:00', '2025-04-13 16:50:00', 2),
  (1, '2025-04-14 09:00:00', '2025-04-14 17:00:00', 2),
  (1, '2025-04-15 09:15:00', '2025-04-15 17:30:00', 2),
  (1, '2025-04-16 08:45:00', '2025-04-16 16:45:00', 2),
  (1, '2025-04-17 09:00:00', '2025-04-17 17:00:00', 2),
  (1, '2025-04-18 09:30:00', '2025-04-18 18:00:00', 2),
  (1, '2025-04-19 08:50:00', '2025-04-19 17:10:00', 2),
  (1, '2025-04-20 09:05:00', '2025-04-20 17:20:00', 2);

  INSERT INTO permiso (fecha, hora, trabajador_id, estado, tipo_permiso) VALUES
  ('2024-01-10', 2.5, 1, 'SOLICITADO', 'ASISTENCIA_MEDICA'),
  ('2024-01-15', 1.0, 2, 'SOLICITADO', 'MUDANZA'),
  ('2024-01-20', 3.0, 1, 'SOLICITADO', 'FALLECIMIENTO_FAMILIAR'),
  ('2024-01-25', 2.0, 2, 'SOLICITADO', 'VACACIONES'),
  ('2024-02-02', 1.5, 1, 'SOLICITADO', 'EXAMEN_PRENATAL'),
  ('2024-02-05', 2.0, 2, 'SOLICITADO', 'ASISTENCIA_EXAMEN'),
  ('2024-02-10', 3.5, 1, 'RECHAZADO', 'HOSPITALIZACION_FAMILIAR'),
  ('2024-02-12', 1.0, 2, 'APROBADO', 'INDISPOSICION_MEDICA'),
  ('2024-02-20', 4.0, 1, 'SOLICITADO', 'ASISTENCIA_MEDICA'),
  ('2024-02-25', 0.5, 2, 'RECHAZADO', 'ENFERMEDAD'),
  ('2024-03-01', 2.0, 1, 'APROBADO', 'PERMISO_PATERNIDAD'),
  ('2024-03-05', 1.5, 2, 'SOLICITADO', 'BAJA_ENFERMEDAD'),
  ('2024-03-10', 3.0, 1, 'SOLICITADO', 'PERMISO_MATERNIDAD'),
  ('2024-03-15', 2.0, 2, 'APROBADO', 'INTERVENCION_QUIRURGICA'),
  ('2024-03-20', 1.0, 1, 'RECHAZADO', 'NACIMIENTO_HIJO'),
  ('2024-03-25', 0.5, 2, 'APROBADO', 'MATRIMONIO'),
  ('2024-04-01', 2.0, 1, 'SOLICITADO', 'BAJA_ACCIDENTE'),
  ('2024-04-05', 3.5, 2, 'RECHAZADO', 'LACTANCIA'),
  ('2024-04-10', 1.0, 1, 'APROBADO', 'EXAMEN_PRENATAL');



