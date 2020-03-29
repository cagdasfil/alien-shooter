insert into role(role) values ('ADMIN');
insert into role(role) values ('USER');
insert into user(username,password,name,surname,email,active) values ('admin','admin','admin','admin','admin@gmail.com',1);
insert into user(username,password,name,surname,email,active) values ('user','user','user','user','user@gmail.com',1);
insert into user_role(user_id,role_id) values (1,1);
insert into user_role(user_id,role_id) values (2,2);