package Capstone.Capstone.Repository;

import Capstone.Capstone.Entity.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CalendarRepository {
    private final EntityManager em;

    public void save(Calendar calendar){
        em.persist(calendar);
    }

    public List findAll(){
        return em.createQuery("select c from Calendar c")
                .getResultList();
    }

    public List findByOwnerId(Long id){
        return em.createQuery("select c from Calendar c"+
                        " join fetch c.memberSpec ms"+
                        " where ms.id =: id", Calendar.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List findByOwnerIdWithYear(Long id, int year){
        return em.createQuery("select c from Calendar c" +
                        " join fetch c.memberSpec ms"+
                        " where ms.id =: id" +
                        " and c.act_Year =: year", Calendar.class)
                .setParameter("id", id)
                .setParameter("year", year)
                .getResultList();
    }

    public List findByOwnerIdWithYM(Long id, int year, int month){
        return em.createQuery("select c from Calendar c" +
                        " join fetch c.memberSpec ms"+
                        " where ms.id =: id" +
                        " and  c.act_Year =: year and c.act_Month =: month", Calendar.class)
                .setParameter("id", id)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }

    public Calendar findByOwnerIdWithYMD(Long id, int year, int month, int day){
        try{
            return em.createQuery("select c from Calendar c" +
                            " join fetch c.memberSpec ms"+
                            " where ms.id =: id" +
                            " and c.act_Year =: year and c.act_Month =: month and c.act_Day =: day", Calendar.class)
                    .setParameter("id", id)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .setParameter("day", day)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }

    }

    public int delete(Long id, int year, int month, int day){
        return em.createQuery("delete from Calendar c" +
                                " where  c.memberSpec.id =: id" +
                                " and c.act_Year =: year " +
                                "and c.act_Month =: month " +
                                "and c.act_Day =: day")
                .setParameter("id",id)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("day", day)
                .executeUpdate();
    }

}
