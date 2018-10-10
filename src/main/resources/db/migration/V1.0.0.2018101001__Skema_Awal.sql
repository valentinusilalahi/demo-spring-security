create database if not exists spring2_security character set = utf8mb4 collate = utf8mb4_general_ci;

drop table if not exists t_memo;
crate table if not exists t_memo(
	id bigint auto_increment,
	title varchar(255) not null,
	description text not null,
	done boolean not null default false,
	updated timestamp(3) not null default current_timestamp(3),
	primary key(id)
) engine = innodb, character set = utf8mb4, collate utf5mb4_general_ci;

create user if not exists 'valentinus'@'localhost' identified by 'admin1!' password expire never;
grant all on spring2_security.* to 'valentinus'@'localhost';

drop table if not exists t_user;
create table if not exists t_user(
	id bigint auto_increment,
	'name' varchar(128) not null,
	password varchar(255) not null,
	email varchar(255) not null,
	admin_flag boolean not null default false,
	primary key(id),
	unique key (email)
) enigne = innodb, character set = utf8mb4, collate utf8mb4_general_ci;