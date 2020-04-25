insert into role(role) values ('ADMIN');
insert into role(role) values ('USER');
insert into user(username,password,name,surname,email,active) values ('admin','$2a$05$Xm9ODm79wA9RQEBT5Vo5S.Q1u/8QMYi.I8.xvs59uQJ6WhJ5zrbSO','admin','admin','admin@gmail.com',1);
insert into user(username,password,name,surname,email,active) values ('user','$2a$05$gTFIylLkjPwzKNIO6i2RCuUNFfc4Wcpr.CxZ6ZInibAIW8gi6bqCS','user','user','user@gmail.com',1);
insert into user_role(user_id,role_id) values (1,1);
insert into user_role(user_id,role_id) values (2,2);
insert into score(created_at, score, user_id) values ('2020-04-22', 560, 2);
insert into score(created_at, score, user_id) values ('2020-03-28', 1590, 2);
insert into score(created_at, score, user_id) values ('2020-01-22', 830, 2);