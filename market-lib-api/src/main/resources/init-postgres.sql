/*
*/
drop table if exists employ;

create table if not exists employ 
(
 id varchar(14),
 name varchar(255),
 create_date timestamp,
 modify_date timestamp,
 create_id varchar(14),
 modify_id varchar(14),
 primary key (id)
);

insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P1hrggys4b3hh4', 'scott', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0qdyu0v35h5k6', 'tiger', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0s0xbssk4qvz9', 'ecycle', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0urv6uvvmd084', 'yslee', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0t8cf2ynjdw6q', 'sckim', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0rhofdnmqjs52', 'dhkim', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0qhkkc07160c2', 'jkpark', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0qeeyfij2z885', 'yhlee', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0qxyvj1lwgldj', 'jckim', NOW(), NOW(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('P0sl3e2kacak4i', 'mgkim', NOW(), NOW(), 1, 1);
