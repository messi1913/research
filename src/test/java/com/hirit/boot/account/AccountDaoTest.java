//package com.hirit.boot.account;
//
//
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.hirit.boot.account.vo.AccountDTO;
//import com.hirit.boot.enttiy.Account;
//import com.hirit.boot.enttiy.QAccount;
//import com.hirit.boot.repository.AccountRepository;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class AccountDaoTest {
//    QAccount ACCOUNT = QAccount.account;
//
//    @Autowired
//    JPAQueryFactory query;
//    @Autowired
//    AccountRepository repository;
//
//
//    @Before
//    public void init() {
//        Account test1 = Account.builder()
//            .email("test@test.com")
//            .userName("test1")
//            .age(19)
//            .build();
//
//        repository.save(test1);
//
//    }
//
//    @Test
//    public void DTO_리턴하는_테스트() {
//        String email = "messi1913@gmail.com";
//        AccountDTO accountDTO = query.select(Projections.bean(AccountDTO.class,
//            ACCOUNT.email,
//            ACCOUNT.userName))
//            .from(ACCOUNT)
//            .where(ACCOUNT.email.eq(email))
//            .fetchOne();
//
//        assertThat(accountDTO.getEmail()).isEqualTo(email);
//    }
//
//    @Test
//    public void 생성자_DTO_리턴하는_테스트() {
//        String email = "messi1913@gmail.com";
//        AccountDTO accountDTO = query.select(Projections.constructor(AccountDTO.class,
//            ACCOUNT.email,
//            ACCOUNT.userName))
//            .from(ACCOUNT)
//            .where(ACCOUNT.email.eq(email))
//            .fetchOne();
//
//        assertThat(accountDTO.getEmail()).isNotEqualTo(email);
//    }
//
//    @Test
//    public void QueryDSL_일반쿼리() {
//        QAccount ACCOUNT = QAccount.account;
//        String email = "messi1913@gmail.com";
//        query.selectFrom(ACCOUNT).fetch().forEach(System.out::println);
//    }
//
//    @Test
//    public void 성인만_가져오기() {
//        List<Account> fetch =
//            query.selectFrom(ACCOUNT)
//            .where(ACCOUNT.age.gt(18))
//            .fetch();
//
//        assertThat(fetch.size()).isGreaterThan(0);
//    }
//
//}