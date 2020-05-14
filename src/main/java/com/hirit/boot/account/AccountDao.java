package com.hirit.boot.account;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hirit.boot.account.vo.AccountDTO;
import com.hirit.boot.enttiy.QAccount;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountDao {

    private final JPAQueryFactory query;

    private final QAccount ACCOUNT = QAccount.account;

    public void getAccountBasic(String email, String password) {
        Tuple tuple = query.select(ACCOUNT.email, ACCOUNT.password)
            .from(ACCOUNT)
            .where(ACCOUNT.email.eq(email)
                .and(ACCOUNT.password.eq(password)))
            .fetchOne();

        String s = tuple.get(ACCOUNT.email);
        Long aLong = tuple.get(ACCOUNT.id);


    }

    public void getAccount(String email, String password) {
        AccountDTO accountDTO = query.from(ACCOUNT)
            .select(Projections.constructor(AccountDTO.class,
                ACCOUNT.userName,
                ACCOUNT.email))
            .where(ACCOUNT.email.eq(email)
                .and(ACCOUNT.password.eq(password)))
            .fetchOne();
    }



}
