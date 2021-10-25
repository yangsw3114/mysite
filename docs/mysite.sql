-- scheme user

desc user;

insert into user values(null, '둘리','dooly@gmail.com', '1234','male',now());
insert into user values(null, '양승우','ysw3114', '1234','male',now());
select * from user;

select no, name from user where email='kickscar@gmail.com' and password='1234';
select no, name, email, password, gender from user where no=16;

 insert into user values(null,'관리자','admin@mysite.com','1234','male', now(), 'ADMIN');
 alter table user add column role enum('USER', 'ADMIN') not null default 'USER';
 select * from user;

delete from user;

-- scheme board
desc board;

insert into board values(null, "뭘봐요", "눈깔아", 3, now(), (select ifnull(max(group_no), 0)+1 from board b) ,0,0, 16);

select * from board;
select * from board order by group_no DESC, order_no ASC;
SELECT * FROM board ORDER BY group_no desc, order_no asc LIMIT 3, 4;

update board set order_no=order_no+1 where group_no = 1 and order_no in(select * from(select order_no from board where order_no >= 2) t);

delete from board where no=64;

alter table board auto_increment = 1;

-- schema site
desc site;

insert into site values(null, 'Mysite', '안녕하세요. YANG SEUNG WOO의 mysite에 오신 것을 환영합니다.', '/gallery/images/zoro.jpg', '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.');

select * from site;

-- schema gallery
desc gallery;

select * from gallery;