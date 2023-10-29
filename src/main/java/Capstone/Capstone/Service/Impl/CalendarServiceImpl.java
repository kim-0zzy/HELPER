package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Dto.CalendarDTO;
import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Exception.NotFoundIdException;
import Capstone.Capstone.Repository.CalendarRepository;
import Capstone.Capstone.Service.CalendarService;
import Capstone.Capstone.Service.MemberSpecService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("CalendarService")
@Transactional
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberSpecService memberSpecService;
    @Override
    public Long saveProgress(Calendar calendar) {
        calendarRepository.save(calendar);
        return calendar.getId();
    }

    @Override
    public List findAll() throws NoResultException{
        return calendarRepository.findAll();
    }

    @Override
    public List<CalendarDTO> findAllRecord(Long id) throws NoResultException{
        List<Calendar> all = calendarRepository.findByOwnerId(id);
        List<CalendarDTO> calendarDTOList = new ArrayList<>();

        for (Calendar calendar : all) {
            CalendarDTO calendarDTO = CalendarDTO.builder()
                    .act_Day(calendar.getAct_Day())
                    .act_Month(calendar.getAct_Month())
                    .act_Year(calendar.getAct_Year())
                    .progress(calendar.getProgress())
                    .build();
            calendarDTOList.add(calendarDTO);
        }
        return calendarDTOList;
    }

    @Override
    public List findAnnualRecord(Long id, int year) throws NoResultException{
        return calendarRepository.findByOwnerIdWithYear(id, year);
    }

    @Override
    public List findMonthlyRecord(Long id, int year, int month) throws NoResultException{
        List<Calendar> all = calendarRepository.findByOwnerIdWithYM(id,LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        List<CalendarDTO> calendarDTOList = new ArrayList<>();

        for (Calendar calendar : all) {
            CalendarDTO calendarDTO = CalendarDTO.builder()
                    .act_Day(calendar.getAct_Day())
                    .act_Month(calendar.getAct_Month())
                    .act_Year(calendar.getAct_Year())
                    .progress(calendar.getProgress())
                    .build();
            calendarDTOList.add(calendarDTO);
        }
        return calendarDTOList;
    }

    @Override
    public Calendar findDateRecord(Long id, int year, int month, int day) throws NoResultException{
        return calendarRepository.findByOwnerIdWithYMD(id, year, month, day);
    }

    @Override
    public Calendar createCalendar_today(MemberSpec memberSpec){
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        int day = LocalDate.now().getDayOfMonth();

        memberSpec.addCareer();
        Calendar calendar = new Calendar(year, month, day);
        calendar.setMemberSpec(memberSpec);
        return calendar;
    }

    @Override
    public Calendar createCalendar(MemberSpec memberSpec, int day) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        memberSpec.addCareer();
        Calendar calendar = new Calendar(year, month, day);
        calendar.setMemberSpec(memberSpec);
        return calendar;
    }

    @Override
    public void deleteCalendarData(Long id, int year, int month, int day) throws NoResultException{
        MemberSpec memberSpec = memberSpecService.findMemberSpec(id);
        memberSpec.decreaseCareer();
        calendarRepository.delete(id, year, month, day);
    }
}
