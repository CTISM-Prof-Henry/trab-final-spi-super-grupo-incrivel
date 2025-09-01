create table usuario
(
    id            serial       not null primary key,
    nome          varchar(50)  not null,
    tipo          varchar(6)   not null,
    identificador varchar(12)  not null,
    email         varchar(255) not null,
    senha         varchar(255) not null
);
create table sala
(
    id     serial     not null primary key,
    bloco  varchar(1) not null,
    n_sala varchar(3) not null,
    andar  int        not null
);
create table pedido_agenda
(
    id             serial      not null primary key,
    sala_id        int         not null,
    usuario_id     int         not null,

    hora_start_at time        not null,
    hora_end_at   time        not null,
    dia_start_at   date        not null,
    dia_end_at     date        not null,

    status         varchar(12) not null,

    foreign key (sala_id) references sala (id),
    foreign key (usuario_id) references usuario (id)
);
create table agenda
(
    id             serial      not null primary key,
    sala_id        int         not null,
    usuario_id     int         not null,

    hora_start_at time        not null,
    hora_end_at   time        not null,
    dia_start_at   date        not null,
    dia_end_at     date        not null,

    created_at     timestamp not null,
    criado_por     int         not null,
    cancelado_por  int,
    cancelado_em   timestamp,

    status         varchar(12) not null,

    foreign key (sala_id) references sala (id),
    foreign key (usuario_id) references usuario (id),

    foreign key (criado_por) references usuario (id),
    foreign key (cancelado_por) references usuario (id)
);