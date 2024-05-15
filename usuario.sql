create database bdusuarios;

create table sexo (
id int auto_increment not null primary key,
sexo varchar(50)
);

insert into sexo (sexo) values ("Masculino");
insert into sexo (sexo) values ("Femenino");
insert into sexo (sexo) values ("Otro");

select * from sexo;

create table usuarios(
id int auto_increment not null primary key,
nombres varchar(100),
apellidos varchar(100),
fksexo int,
edad int,
docnombre varchar(100),
docapellidos varchar(100),
especialidad varchar(100),
fcita date,
foto longblob,
FOREIGN KEY (fksexo) references sexo(id) ON DELETE CASCADE
);

insert into usuarios(nombres,apellidos,fksexo,edad,docnombre,docapellidos,especialidad,fcita,foto) values ("isaac","gomez",1,21,"jose","flores","dermatologo","02/06/2024","foto");

select * from usuarios;

SHOW VARIABLES LIKE 'max_connections';

SHOW STATUS LIKE 'threads_connected';



select usuarios.id,usuarios.nombres,usuarios.apellidos,sexo.sexo,usuarios.edad,usuarios.docnombres,usuarios.docapellidos,usuarios.especialidad,usuarios.fcita,usuarios.foto 
from usuarios INNER JOIN  sexo ON usuarios.fksexo = sexo.id;

UPDATE usuarios SET usuarios.nombres=?,usuarios.apellidos=?,usuarios.sexo=?,usuarios.edad=?,usuarios.docnombre=?,usuarios.docapellidos=?,usuarios.especialidad=?,usuarios.fcita=?,usuarios.foto=?
Where usuarios.id=?;

DELETE FROM usuarios WHERE usuarios.id=2;