package Capstone.Capstone.Service;

import Capstone.Capstone.Dto.CalendarDTO;
import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.MemberSpec;
import Capstone.Capstone.Exception.NotFoundIdException;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public interface CalendarService {
    public Long saveProgress(Calendar calendar);
    public List findAll() throws NoResultException;
    public List<CalendarDTO> findAllRecord(Long id) throws NoResultException;
    public List<Calendar> findAnnualRecord(Long id, int year) throws NoResultException;
    public List<Calendar> findMonthlyRecord(Long id, int year, int month) throws NoResultException;
    public Calendar findDateRecord(Long id, int year, int month, int day) throws NoResultException;
    public Calendar createCalendar(MemberSpec memberSpec);
    public void deleteCalendarData(Long id, int year, int month, int day) throws NoResultException;

}
