# project-spring-4-sejongdeveloper

# 이 앱을 언제 사용하나요?
상품의 재고를 관리할 때 사용합니다.

# 구체적인 사례는 무엇인가요?
부족한 재고가 있는 상품을 수주 받았을 때는 발주를 하여 재고를 채웁니다.<br>
충분한 재고가 있는 상품을 수주 받았을 때는 출고를 하여 재고를 비웁니다.

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
