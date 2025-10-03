create table bloco
(
    id              serial not null primary key,
    nome            varchar(10),
    qt_salas int
);

alter table sala
    drop column bloco;

alter table sala
    add column bloco_id int;

alter table sala
    add constraint fk_bloco_sala
        foreign key (bloco_id) references bloco(id);

alter table sala
    add column codigo varchar(50);

alter table sala
    drop column n_sala;

alter table sala
    add column nome varchar(100);

alter table usuario
    add column telefone varchar(15);