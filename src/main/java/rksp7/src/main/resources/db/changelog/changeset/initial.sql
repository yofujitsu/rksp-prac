create table if not exists hero
(
    id            serial primary key,
    name          varchar(255) not null,
    attribute     varchar(255) not null,
    spells_count  int          not null,
    is_in_cm_mode boolean      not null
    );