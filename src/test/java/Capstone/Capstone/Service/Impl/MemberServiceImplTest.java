package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Exception.NotFoundIdException;
import Capstone.Capstone.Exception.PasswordException;
import Capstone.Capstone.Repository.MemberRepository;
import Capstone.Capstone.Service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceImplTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void join() {
        String name = "kim";
        String id = "0zzy";
        String passwd = "123asd";
        Member member = new Member(name, id, passwd);

        Long saveId = memberService.join(member);

        assertThat(saveId).isEqualTo(member.getId());
    }

    @Test
    void loginValidation() throws PasswordException, NotFoundIdException {
        String name = "kim";
        String id = "0zzy";
        String passwd = "123asd";
        Member member = new Member(name, id, passwd);

        Long saveId = memberService.join(member);
        String passwd2 = "asd123";
        memberService.login(member, passwd2);

    }

    @Test
    void userid_중복_예외_() {
        String name_1 = "kim";
        String id_1 = "0zzy";
        String passwd_1 = "123asd";
        Member member_1 = new Member(name_1, id_1, passwd_1);

        Long saveId = memberService.join(member_1);

        String name = "Lee";
        String id = "0zzy";
        String passwd = "123asd";
        Member member = new Member(name, id, passwd);

        Long saveId2 = memberService.join(member);
    }

}