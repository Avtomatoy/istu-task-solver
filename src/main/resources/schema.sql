create table if not exists users(
    id uuid not null,
    login text unique not null,
    password text not null,
    roles text array not null,
    primary key (id)
)
