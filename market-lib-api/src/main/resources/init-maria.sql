drop table if exists employ;

create table if not exists employ 
(
 id varchar(14),
 name varchar(255),
 create_date timestamp(6),
 modify_date timestamp(6),
 create_id varchar(14),
 modify_id varchar(14),
 primary key (id)
);

insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M1hrggys4b3hh4', 'scott', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0qdyu0v35h5k6', 'tiger', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0s0xbssk4qvz9', 'ecycle', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0urv6uvvmd084', 'yslee', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0t8cf2ynjdw6q', 'sckim', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0rhofdnmqjs52', 'dhkim', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0qhkkc07160c2', 'jkpark', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0qeeyfij2z885', 'yhlee', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0qxyvj1lwgldj', 'jckim', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('M0sl3e2kacak4i', 'mgkim', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 1, 1);
