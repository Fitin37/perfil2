Base de datos utilizada

create table usuario(
nombre varchar2(50),
apellidos varchar2(50),
edad varchar2(8),
correo varchar2(50),
contraseña varchar2(10)
);

create table tbservicio(
uuid varchar2(50),
numeroTicket number,
tituloTicket varchar2(50),
descripccion varchar2(50),
responsable varchar2(50),
correo varchar2(50),
telefonoAutor number,
ubicacion varchar2(50),
estadaTicket varchar2(50)
);

alter table usuario add(
telefono number
);

select * from usuario;

select * from tbservicio;
