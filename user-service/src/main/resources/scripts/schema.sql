drop table if exists users;

create table users
(
    id          bigint  not null auto_increment,
    email       varchar(100),
    username    varchar(50),
    password    varchar(500),
    profile_url varchar(500),
    created_at  timestamp default now(),
    updated_at  timestamp default now(),

    primary key (id)
);
