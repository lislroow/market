/*
*/
drop table employ;

create table employ 
(
 id varchar(14),
 name varchar(255),
 create_date timestamp,
 modify_date timestamp,
 create_id varchar(14),
 modify_id varchar(14),
 primary key (id)
);

insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O1hrggys4b3hh4', 'scott', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0qdyu0v35h5k6', 'tiger', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0s0xbssk4qvz9', 'ecycle', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0urv6uvvmd084', 'yslee', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0t8cf2ynjdw6q', 'sckim', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0rhofdnmqjs52', 'dhkim', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0qhkkc07160c2', 'jkpark', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0qeeyfij2z885', 'yhlee', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0qxyvj1lwgldj', 'jckim', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('O0sl3e2kacak4i', 'mgkim', SYSTIMESTAMP, SYSTIMESTAMP, 1, 1);
