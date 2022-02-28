# project-spring-4-sejongdeveloper

# 기능
1. 수주
2. 출고
3. 발주

# ERD
![erd2](https://user-images.githubusercontent.com/51711799/154278930-c0efaeaa-2669-4dac-a5ed-7b748cb1231f.png)

# 기본 sql
insert into item (item_id, save_date_time, name, quantity, use_yn)
values(1, now(), '상품1', 0, 1);

insert into item (item_id, save_date_time, name, quantity, use_yn)
values(2, now(), '상품2', 0, 1);

insert into item (item_id, save_date_time, name, quantity, use_yn)
values(3, now(), '상품3', 0, 1);

insert into users (user_id, login_id, password, save_date_time)
values (1, 'test', '1234', now())
