package com.codesoom.sejongdeveloper.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")   //사용자
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;    //사용자 일련번호

    private String loginId; //로그인 아이디

    private String password;    //비밀번호

    @Builder
    public User(Long id, String loginId, String password) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
    }
}
