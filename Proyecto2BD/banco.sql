#Archivo batch (banco.sql)

#Creo la base de datos
CREATE DATABASE banco;

#Selecciono la base de datos sobre la cual voy a hacer modificaciones
USE banco;

#------------------------------------------------------------------------
#Creacion tablas para las entidades

CREATE TABLE Ciudad(
	cod_postal INT(4) UNSIGNED NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	
	CONSTRAINT pk_Ciudad
	PRIMARY KEY(cod_postal)
) ENGINE=InnoDB;


CREATE TABLE Sucursal(
	nro_suc INT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	horario VARCHAR(45) NOT NULL,
	cod_postal INT(4) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Sucursal
	PRIMARY KEY (nro_suc),
	
	CONSTRAINT FK_Sucursal_Ciudad
	FOREIGN KEY (cod_postal) REFERENCES Ciudad(cod_postal)
) ENGINE=InnoDB;


CREATE TABLE Empleado(
	legajo INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	tipo_doc VARCHAR(20) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	cargo VARCHAR(45) NOT NULL,
	password VARCHAR(32) NOT NULL,
	nro_doc INT(8) UNSIGNED NOT NULL,
	nro_suc INT(3) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Empleado
	PRIMARY KEY (legajo),
	
	CONSTRAINT FK_Empleado_Sucursal
	FOREIGN KEY (nro_suc) REFERENCES Sucursal(nro_suc)
) ENGINE=InnoDB;


CREATE TABLE Cliente(
	nro_cliente INT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	tipo_doc VARCHAR(20) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	nro_doc INT(8) UNSIGNED NOT NULL,
	fecha_nac DATE NOT NULL, 
	
	CONSTRAINT pk_Cliente
	PRIMARY KEY (nro_cliente)
) ENGINE=InnoDB;


CREATE TABLE Plazo_Fijo(
	nro_plazo INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
	capital DECIMAL(16,2) UNSIGNED NOT NULL,
	tasa_interes DECIMAL(4,2) UNSIGNED NOT NULL,
	interes DECIMAL(16,2) UNSIGNED NOT NULL,
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE NOT NULL,
	nro_suc INT(4) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Plazo_Fijo
	PRIMARY KEY (nro_plazo),
	
	CONSTRAINT FK_Plazo_Fijo_Sucursal
	FOREIGN KEY (nro_suc) REFERENCES Sucursal(nro_suc)	
) ENGINE=InnoDB;


CREATE TABLE Tasa_Plazo_Fijo(
	periodo INT(3) UNSIGNED NOT NULL,
	monto_inf DECIMAL(16, 2)UNSIGNED NOT NULL,
	monto_sup DECIMAL(16, 2) UNSIGNED NOT NULL,
	tasa DECIMAL(4, 2) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Tasa_Plazo_Fijo
	PRIMARY KEY (periodo, monto_inf, monto_sup)
) ENGINE= InnoDB;


CREATE TABLE Prestamo(
	interes DECIMAL(9, 2) UNSIGNED NOT NULL,
	tasa_interes DECIMAL(4, 2) UNSIGNED NOT NULL,
	valor_cuota DECIMAL(9, 2) UNSIGNED NOT NULL,
	nro_prestamo INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	cant_meses INT(2) UNSIGNED NOT NULL,
	monto DECIMAL(10, 2) UNSIGNED NOT NULL,
	legajo INT(4) UNSIGNED NOT NULL, 
	nro_cliente INT(5) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Prestamo
	PRIMARY KEY (nro_prestamo),
	
	CONSTRAINT FK_Prestamo_Cliente
	FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente),	
	
	CONSTRAINT FK_Prestamo_Empleado
	FOREIGN KEY (legajo) REFERENCES Empleado(legajo)	
	
)ENGINE= InnoDB;


CREATE TABLE Pago(
	nro_prestamo INT(8) UNSIGNED NOT NULL,
	nro_pago INT(2) UNSIGNED NOT NULL,
	fecha_venc DATE NOT NULL,
	fecha_pago DATE,

	CONSTRAINT pk_Pago
	PRIMARY KEY (nro_pago, nro_prestamo),
	
	CONSTRAINT FK_Pago_Prestamo
	FOREIGN KEY (nro_prestamo) REFERENCES Prestamo(nro_prestamo)	
	
)ENGINE= InnoDB;


CREATE TABLE Tasa_Prestamo(
	periodo INT(3) UNSIGNED NOT NULL,
	monto_inf DECIMAL(10, 2) UNSIGNED NOT NULL,
	monto_sup DECIMAL(10, 2) UNSIGNED NOT NULL,
	tasa DECIMAL(4, 2) UNSIGNED NOT NULL,

	CONSTRAINT pk_Tasa_Prestamo
	PRIMARY KEY (periodo, monto_sup, monto_inf)
	
)ENGINE= InnoDB;


CREATE TABLE Caja_Ahorro(
	nro_ca INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
	CBU BIGINT(18) UNSIGNED NOT NULL,
	saldo DECIMAL(16, 2) UNSIGNED NOT NULL,

	CONSTRAINT pk_Caja_Ahorro
	PRIMARY KEY (nro_ca)
)ENGINE= InnoDB;


CREATE TABLE Caja(
	cod_caja INT(5) UNSIGNED NOT NULL AUTO_INCREMENT,
	
	CONSTRAINT pk_Caja
	PRIMARY KEY (cod_caja)
) ENGINE= InnoDB;


CREATE TABLE Ventanilla(
	cod_caja INT(5) UNSIGNED NOT NULL,
	nro_suc INT(3) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Ventanilla
	PRIMARY KEY (cod_caja),
	
	CONSTRAINT FK_Ventanilla_Caja
	FOREIGN KEY (cod_caja) REFERENCES Caja(cod_caja),
	
	CONSTRAINT FK_Ventanilla_Sucursal
	FOREIGN KEY (nro_suc) REFERENCES Sucursal(nro_suc)
) ENGINE= InnoDB;


CREATE TABLE ATM(
	cod_caja INT(5) UNSIGNED NOT NULL,
	cod_postal INT(4) UNSIGNED NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	
	CONSTRAINT pk_ATM
	PRIMARY KEY (cod_caja),
	
	CONSTRAINT FK_ATM_Caja
	FOREIGN KEY (cod_caja) REFERENCES Caja(cod_caja),
	
	CONSTRAINT FK_ATM_Ciudad
	FOREIGN KEY (cod_postal) REFERENCES Ciudad(cod_postal)
) ENGINE= InnoDB;


CREATE TABLE Transaccion(
	nro_trans INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
	monto DECIMAL(16, 2) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Transaccion
	PRIMARY KEY (nro_trans)
) ENGINE= InnoDB;


CREATE TABLE Cliente_CA(
	nro_cliente INT(8) UNSIGNED NOT NULL,
	nro_ca INT(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Cliente_CA
	PRIMARY KEY (nro_cliente, nro_ca),
	
	CONSTRAINT FK_Cliente_CA_Cliente
	FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente),
	
	CONSTRAINT FK_Cliente_CA_Caja_Ahorro
	FOREIGN KEY (nro_ca) REFERENCES Caja_Ahorro(nro_ca)
) ENGINE= InnoDB;


CREATE TABLE Tarjeta(
	nro_tarjeta BIGINT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
	PIN VARCHAR(32) NOT NULL,
	CVT VARCHAR(32) NOT NULL,
	fecha_venc DATE NOT NULL,
	nro_cliente INT(8) UNSIGNED NOT NULL,
	nro_ca INT(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Tarjeta
	PRIMARY KEY (nro_tarjeta),
	
	CONSTRAINT FK_Tarjeta_Cliente
	FOREIGN KEY (nro_cliente, nro_ca) REFERENCES Cliente_ca(nro_cliente, nro_ca)

) ENGINE= InnoDB;


CREATE TABLE Debito(
	nro_trans INT(10) UNSIGNED NOT NULL,
	descripcion TEXT,
	nro_cliente INT(8) UNSIGNED NOT NULL,
	nro_ca INT(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_ATM
	PRIMARY KEY (nro_trans),
	
	CONSTRAINT FK_Debito_Cliente_CA
	FOREIGN KEY (nro_cliente, nro_ca) REFERENCES Cliente_CA(nro_cliente, nro_ca),
	
	CONSTRAINT FK_Debito_Transaccion
	FOREIGN KEY (nro_trans) REFERENCES Transaccion(nro_trans)
) ENGINE= InnoDB;


CREATE TABLE Transaccion_por_caja(
	nro_trans INT(10) UNSIGNED NOT NULL,
	cod_caja INT(5) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Transaccion_por_caja
	PRIMARY KEY (nro_trans),
	
	CONSTRAINT FK_Transaccion_por_caja_caja
	FOREIGN KEY (cod_caja) REFERENCES caja(cod_caja),
	
	CONSTRAINT FK_Transaccion_por_caja_Transaccion
	FOREIGN KEY (nro_trans) REFERENCES transaccion(nro_trans)
) ENGINE= InnoDB;


CREATE TABLE Deposito(
	nro_trans INT(10) UNSIGNED NOT NULL,
	nro_ca INT(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Deposito
	PRIMARY KEY (nro_trans),
	
	CONSTRAINT FK_Deposito_Caja_Ahorro
	FOREIGN KEY (nro_ca) REFERENCES Caja_Ahorro(nro_ca),
	
	CONSTRAINT FK_Deposito_Transaccion
	FOREIGN KEY (nro_trans) REFERENCES Transaccion_por_caja(nro_trans)
) ENGINE= InnoDB;


CREATE TABLE Extraccion(
	nro_trans INT(10) UNSIGNED NOT NULL,
	nro_ca INT(8) UNSIGNED NOT NULL,
	nro_cliente INT(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Extraccion
	PRIMARY KEY (nro_trans),
	
	CONSTRAINT FK_Extraccion_Cliente_CA
	FOREIGN KEY (nro_ca, nro_cliente) REFERENCES Cliente_CA(nro_ca, nro_cliente),
	
	CONSTRAINT FK_Extraccion_Transaccion_por_caja
	FOREIGN KEY (nro_trans) REFERENCES Transaccion_por_caja(nro_trans)
	
) ENGINE= InnoDB;


CREATE TABLE Transferencia(
	nro_trans INT(10) UNSIGNED NOT NULL,
	nro_cliente INT(8) UNSIGNED NOT NULL,
	origen INT(8) UNSIGNED NOT NULL,
	destino INT(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Transferencia
	PRIMARY KEY (nro_trans),
	
	CONSTRAINT FK_Transferencia_Transaccion_por_caja
	FOREIGN KEY (nro_trans) REFERENCES Transaccion_por_caja(nro_trans),
	
	CONSTRAINT FK_Transferencia_Cliente_CA
	FOREIGN KEY (nro_cliente, origen) REFERENCES Cliente_CA(nro_cliente, nro_ca),
	
	CONSTRAINT FK_Transferencia_Caja_Ahorro
	FOREIGN KEY (destino) REFERENCES Caja_Ahorro(nro_ca)
) ENGINE= InnoDB;



CREATE TABLE Plazo_Cliente(
	nro_plazo INT(8) UNSIGNED NOT NULL,
	nro_cliente INT(5) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_Plazo_Cliente
	PRIMARY KEY (nro_plazo, nro_cliente),
	
	CONSTRAINT FK_Plazo_Cliente_Plazo_Fijo
	FOREIGN KEY (nro_plazo) REFERENCES Plazo_Fijo(nro_plazo),
	
	CONSTRAINT FK_Plazo_Cliente_Cliente
	FOREIGN KEY (nro_cliente) REFERENCES Cliente(nro_cliente)
) ENGINE= InnoDB;



#------------------------------------------------------------------------
#Creacion de vistas
CREATE VIEW vista_debito as
SELECT 
	ca.nro_ca, 
	ca.saldo,
	t.nro_trans, 
	t.fecha, 
	t.hora, 
	t.monto,
	'Debito' as tipo,
	d.descripcion,
	NULL as cadestino, 
	NULL as cod_caja,
	c.nro_cliente,
	c.tipo_doc,
	c.nro_doc,
	c.nombre,
	c.apellido
FROM ((((Transaccion as t JOIN Debito as d ON t.nro_trans=d.nro_trans)
			  JOIN Cliente_CA as clca ON clca.nro_cliente=d.nro_cliente) 
			  JOIN Caja_Ahorro as ca ON ca.nro_ca=clca.nro_ca)
			  JOIN Cliente as c ON c.nro_cliente=clca.nro_cliente)
WHERE t.nro_trans=d.nro_trans;


CREATE VIEW vista_extraccion as
SELECT 
	ca.nro_ca, 
	ca.saldo,
	t.nro_trans, 
	t.fecha, 
	t.hora, 
	t.monto,
	'Extraccion' as tipo,
	NULL as descripcion,
	NULL as cadestino,  
	caja.cod_caja,
	c.nro_cliente,
	c.tipo_doc,
	c.nro_doc,
	c.nombre,
	c.apellido
FROM ((((((Transaccion as t JOIN Extraccion as e ON t.nro_trans=e.nro_trans)
			  JOIN Cliente_CA as clca ON clca.nro_cliente=e.nro_cliente) 
			  JOIN Caja_Ahorro as ca ON ca.nro_ca=clca.nro_ca )
			  JOIN Transaccion_por_caja as tpc )
			  JOIN Caja as caja ON tpc.cod_caja=caja.cod_caja)
			  JOIN Cliente as c ON c.nro_cliente=clca.nro_cliente)
WHERE t.nro_trans=e.nro_trans;


CREATE VIEW vista_deposito as
SELECT 
	ca.nro_ca, 
	ca.saldo,
	t.nro_trans, 
	t.fecha, 
	t.hora, 
	t.monto,
	'Deposito' as tipo,
	NULL as descripcion,
	NULL as cadestino,
	caja.cod_caja,	
	NULL as nro_cliente,
	NULL as tipo_doc,
	NULL as nro_doc,
	NULL as nombre,
	NULL as apellido
FROM ((((Transaccion as t JOIN Deposito as dep ON t.nro_trans=dep.nro_trans)
			  JOIN Caja_Ahorro as ca ON ca.nro_ca=dep.nro_ca )
			  JOIN Transaccion_por_caja as tpc )
			  JOIN Caja as caja ON tpc.cod_caja=caja.cod_caja)
WHERE t.nro_trans=dep.nro_trans;


CREATE VIEW vista_transferencia as
SELECT 
	tr.origen as nro_ca, 
	ca.saldo,
	t.nro_trans, 
	t.fecha, 
	t.hora, 
	t.monto,
	'Transferencia' as tipo,
	NULL as descripcion,
	tr.destino as cadestino,
	caja.cod_caja,
	c.nro_cliente,
	c.tipo_doc,
	c.nro_doc,
	c.nombre,
	c.apellido	
FROM ((((((Transaccion as t JOIN Transferencia as tr ON t.nro_trans=tr.nro_trans)
			  JOIN Cliente_CA as clca) 
			  JOIN Caja_Ahorro as ca)
			  JOIN Cliente as c ON c.nro_cliente=clca.nro_cliente)
			  JOIN Transaccion_por_caja as tpc )
			  JOIN Caja as caja ON tpc.cod_caja=caja.cod_caja)
WHERE t.nro_trans=tr.nro_trans;


CREATE VIEW	trans_cajas_ahorro as			
SELECT * FROM vista_debito
	UNION SELECT * FROM vista_extraccion
	UNION SELECT * FROM vista_deposito
	UNION SELECT * FROM vista_transferencia;
 
#------------------------------------------------------------------------
#Creacion de usuarios y otorgamiento de privilegios 

#Creacion de usuario admin

	GRANT ALL PRIVILEGES ON banco.* TO admin@localhost
	IDENTIFIED BY 'admin' WITH GRANT OPTION;


#Creacion de usuario empleado;

	CREATE USER empleado@'%' IDENTIFIED BY 'empleado';
	
	GRANT SELECT ON banco.Empleado TO empleado@'%';
	GRANT SELECT ON banco.Sucursal TO empleado@'%';
	GRANT SELECT ON banco.Tasa_Plazo_Fijo TO empleado@'%';
	GRANT SELECT ON banco.Tasa_Prestamo TO empleado@'%';

	GRANT SELECT ON banco.Prestamo TO empleado@'%';
	GRANT SELECT ON banco.Plazo_Fijo TO empleado@'%';
	GRANT SELECT ON banco.Plazo_Cliente TO empleado@'%';
	GRANT SELECT ON banco.Caja_Ahorro TO empleado@'%';
	GRANT SELECT ON banco.Tarjeta TO empleado@'%';
	GRANT INSERT ON banco.Prestamo TO empleado@'%';
	GRANT INSERT ON banco.Plazo_Fijo TO empleado@'%';
	GRANT INSERT ON banco.Plazo_Cliente TO empleado@'%';
	GRANT INSERT ON banco.Caja_Ahorro TO empleado@'%';
	GRANT INSERT ON banco.Tarjeta TO empleado@'%';
	
	GRANT SELECT ON banco.Cliente_CA TO empleado@'%';
	GRANT SELECT ON banco.Cliente TO empleado@'%';
	GRANT SELECT ON banco.Pago TO empleado@'%';
	GRANT INSERT ON banco.Cliente_CA TO empleado@'%';
	GRANT INSERT ON banco.Cliente TO empleado@'%';
	GRANT INSERT ON banco.Pago TO empleado@'%';
	GRANT UPDATE ON banco.Cliente_CA TO empleado@'%';
	GRANT UPDATE ON banco.Cliente TO empleado@'%';
	GRANT UPDATE ON banco.Pago TO empleado@'%';

#Creacion de usuario ATM
	CREATE USER atm@'%' IDENTIFIED BY 'atm';
	GRANT SELECT ON banco.trans_cajas_ahorro TO atm@'%';
	GRANT SELECT ON banco.Tarjeta TO atm@'%';
	GRANT UPDATE ON banco.Tarjeta TO atm@'%';
