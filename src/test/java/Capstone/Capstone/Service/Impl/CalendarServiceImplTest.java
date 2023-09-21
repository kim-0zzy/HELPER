package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.E_type.Gender;
import Capstone.Capstone.Entity.E_type.Goals;
import Capstone.Capstone.Entity.E_type.Level;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Exception.NotFoundIdException;
import Capstone.Capstone.Repository.CalendarRepository;
import Capstone.Capstone.Repository.MemberRepository;
import Capstone.Capstone.Repository.MemberSpecRepository;
import Capstone.Capstone.Service.CalendarService;
import Capstone.Capstone.Service.MemberService;
import Capstone.Capstone.Service.MemberSpecService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class CalendarServiceImplTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberSpecRepository memberSpecRepository;
    @Autowired
    private MemberSpecService memberSpecService;

//
//    @Test
//    void join() throws NotFoundIdException {
//        String name = "kim";
//        String id = "0zzy";
//        String passwd = "123asd";
//        Member member = new Member(name, id, passwd);
//
//        int height = 170;
//        int weight = 70;
//        int waist = 80;
//        int hip = 90;
//        int career = 30;
//        int age = 25;
//        int times = 3;
//        Gender gender = Gender.MALE;
//        Goals goals = Goals.BULKUP;
//        Level level = Level.BEGINNER;
//        MemberSpec memberSpec = new MemberSpec(member, height, weight, waist, hip, career, age, times, gender, goals, level);
//
//
//        int year = LocalDate.now().getYear();
//        int month = LocalDate.now().getMonth().getValue();
//        int day = LocalDate.now().getDayOfMonth();
//        Calendar calendar = new Calendar(year,month,day);
//        calendarService.createCalendar(memberSpec);
//
//        Long saveId = calendarService.saveProgress(calendar);
//        Long mSaveId = memberSpecService.saveMemberSpec(memberSpec);
//
//        assertThat(saveId).isEqualTo(calendar.getId());
//
//        System.out.println("saveId = " + saveId);
//        System.out.println("find = " + calendarService.findAll());
//        System.out.println("findM = " + calendarService.findAllRecord(mSaveId));
//    }

    @Test
    void findAllRecord() {
    }

    @Test
    void findAnnualRecord() {
    }

    @Test
    void findMonthlyRecord() {
    }

    @Test
    void findDateRecord() {
    }
}
