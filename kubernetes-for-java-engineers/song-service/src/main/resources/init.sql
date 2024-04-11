CREATE TABLE song (
    id SERIAL primary key ,
    name varchar(100),
    artist varchar(100),
    album varchar(100),
    length varchar(100),
    resource_id varchar(100) unique,
    year varchar(100)
);