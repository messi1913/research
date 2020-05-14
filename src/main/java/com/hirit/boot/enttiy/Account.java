package com.hirit.boot.enttiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "ACCOUNT")
public class Account {

    //    까먹었을까봐
//    1. @Data -> 롬북 기본설정
//    2. @Entity -> JPA설정
//    3. 3개 어노테이션은 생성자 / 빌드 pattern

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(nullable = false, unique = true)
    private String userName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    // 이건 Json 보낼때 히든 처리
    private String password;
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;
    private int age;
}
