-- scheme user

desc user;

insert into user values(null, '둘리','dooly@gmail.com', '1234','male',now());

select * from user;

select no, name from user where email='kickscar@gmail.com' and password='1234';
