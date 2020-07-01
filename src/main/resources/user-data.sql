DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userName varchar(256) NOT NULL ,
    password varchar(256) NOT NULL
)

insert into users values (101, 'Clemens', 'test')
insert into users value (102, 'testnutzer', 'Start123!')