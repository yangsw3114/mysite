-- scheme user

desc user;

insert into user values(null, '둘리','dooly@gmail.com', '1234','male',now());
insert into user values(null, '양승우','ysw3114', '1234','male',now());
select * from user;

select no, name from user where email='kickscar@gmail.com' and password='1234';
select no, name, email, password, gender from user where no=16;


delete from user;