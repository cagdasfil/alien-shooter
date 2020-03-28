insert into role(role) values ('USER');
insert into role(role) values ('ADMIN');
insert into user(username,password,name,surname,email) values ('user','user','user','user','user@gmail.com');
insert into user(username,password,name,surname,email) values ('admin','admin','admin','admin','admin@gmail.com');
insert into user_role(user_id,role_id) values (1,1);
insert into user_role() values (2,2);