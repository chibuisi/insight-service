
-- insert into role(id,role_name) values (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_CLIENT'),(4, 'ROLE_DEVELOPER');
-- insert into user_account(id,email,username,firstname,lastname,password) values (1,'ckamiaka2019@gmail.com', 'ckamiaka',
--                                                       'Chibuisi','Amiaka','$2a$12$ESK4BePk5B4cSek39pI3y.tGLeab1E0yqwFh30cY.r4l9BvcATH2e');
-- insert into user_account_roles(user_id, role_id) values (1,1);

INSERT into role(id, role_name)
select 1,'ROLE_ADMIN'
where not exists(select id, role_name from role where id = 1 and role_name = 'ROLE_ADMIN');

INSERT into role(id, role_name)
select 2,'ROLE_USER'
where not exists(select id, role_name from role where id = 2 and role_name = 'ROLE_USER');

INSERT into role(id, role_name)
select 3,'ROLE_CLIENT'
where not exists(select id, role_name from role where id = 3 and role_name = 'ROLE_CLIENT');

INSERT into role(id, role_name)
select 4,'ROLE_DEVELOPER'
where not exists(select id, role_name from role where id = 4 and role_name = 'ROLE_DEVELOPER');

INSERT into role(id, role_name)
select 5,'ROLE_CAN_ADD_TOPIC'
where not exists(select id, role_name from role where id = 5 and role_name = 'ROLE_CAN_ADD_TOPIC');

INSERT into role(id, role_name)
select 6,'ROLE_CAN_UPDATE_TOPIC'
where not exists(select id, role_name from role where id = 6 and role_name = 'ROLE_CAN_UPDATE_TOPIC');

INSERT into role(id, role_name)
select 7,'ROLE_CAN_REMOVE_TOPIC'
where not exists(select id, role_name from role where id = 7 and role_name = 'ROLE_CAN_REMOVE_TOPIC');

INSERT INTO user_account (id,email,username,firstname,lastname,password)
SELECT 1,'ckamiaka2019@gmail.com', 'ckamiaka',
       'Chibuisi','Amiaka','$2a$12$ESK4BePk5B4cSek39pI3y.tGLeab1E0yqwFh30cY.r4l9BvcATH2e'
WHERE NOT EXISTS (SELECT id FROM user_account WHERE id = 1);

insert into user_account_roles(user_id, role_id)
select 1,1
where not exists(select user_id, role_id from user_account_roles where user_id = 1 and role_id = 1);

insert into user_account_roles(user_id, role_id)
select 1,5
where not exists(select user_id, role_id from user_account_roles where user_id = 1 and role_id = 5);

insert into user_account_roles(user_id, role_id)
select 1,6
where not exists(select user_id, role_id from user_account_roles where user_id = 1 and role_id = 6);

insert into user_account_roles(user_id, role_id)
select 1,7
where not exists(select user_id, role_id from user_account_roles where user_id = 1 and role_id = 7);
