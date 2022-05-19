create table forma_pagamento(
	id bigint not null auto_increment,
    descricao varchar(60) not null,
    primary key (id)
)engine=InnoDB default charset=utf8;

create table grupo(
	id bigint not null auto_increment,
    nome varchar(80) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo_permissao(
	grupo_id bigint not null,
	permissao_id bigint not null
)engine=InnoDB default charset=utf8;

create table permissao(
	id bigint not null auto_increment,
	nome varchar(80) not null,
	descricao varchar(60) not null,
	primary key (id)
)engine=InnoDB default charset=utf8;

create table produto(
	id bigint not null auto_increment,
	nome varchar(80) not null,
    ativo bit not null,
    descricao varchar(60) not null,
    preco decimal(19,2) not null,
    restaurante_id bigint not null,
    primary key (id)
)engine=InnoDB default charset=utf8;

create table restaurante(
	id bigint not null auto_increment,
    nome varchar(80) not null,
    data_atualizacao datetime not null,
    data_cadastro datetime not null,
    endereco_bairro varchar(60),
    endereco_cep varchar(60),
    endereco_complemento varchar(60),
    endereco_logradouro varchar(60),
    endereco_numero varchar(60),
    taxa_frete decimal (19,2) not null,
    codinha_id bigint not null,
    endereco_cidade_id bigint not null,
    
    primary key (id)
)engine=InnoDB default charset=utf8;

create table restaurante_forma_pagamento(
	restaurante_id bigint not null,
	forma_pagamento_id bigint not null

)engine=InnoDB default charset=utf8;

create table usuario(
	id bigint not null auto_increment,
	nome varchar(80) not null,
	data_cadastro datetime not null,
	email varchar(255) not null,
	senha varchar(80) not null,

primary key (id)
)engine=InnoDB default charset=utf8;