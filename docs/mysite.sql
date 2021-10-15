-- scheme user

desc user;

insert into user values(null, '둘리','dooly@gmail.com', '1234','male',now());
insert into user values(null, '양승우','ysw3114', '1234','male',now());
select * from user;

select no, name from user where email='kickscar@gmail.com' and password='1234';
select no, name, email, password, gender from user where no=16;


delete from user;

-- scheme board
desc board;

insert into board values(null, "뭘봐요", "눈깔아", 3, now(), (select ifnull(max(group_no), 0)+1 from board b) ,0,0, 16);

select * from board;
SELECT * FROM board ORDER BY group_no desc, order_no asc LIMIT 0, 4;

update board set order_no=order_no+1 where group_no = 1 and order_no in(select * from(select order_no from board where order_no >= 2) t);

delete from board;
