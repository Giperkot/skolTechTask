
create schema sensor;

create table sensor.object (
    id bigserial NOT NULL primary key,
    create_time timestamp not null default now()
);

create table sensor.sensor (
    id bigserial NOT NULL primary key,
    create_time timestamp not null default now()
);

create table sensor.measure_result (
    id bigserial NOT NULL primary key,
    create_time timestamp not null default now(),
    object_id bigint not null
            references sensor.object(id),
    sensor_id bigint not null
        references sensor.sensor(id),
    value double precision not null,
    measure_time timestamp not null
);

create index measure_result_measure_time_index
    on sensor.measure_result (measure_time);

/*
drop table object;
drop table sensor;
drop table sensor.measure_result;
*/
