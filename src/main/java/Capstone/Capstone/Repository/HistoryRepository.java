package Capstone.Capstone.Repository;

import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.MemberSpecHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {
    private final EntityManager em;

    public void save(MemberSpecHistory memberSpecHistory){
        em.persist(memberSpecHistory);
    }
    public List findByOwnerID(Long id){
        return em.createQuery("select msh from MemberSpecHistory msh"+
                        " join fetch msh.memberSpec ms"+
                        " where ms.id =: id", MemberSpecHistory.class)
                .setParameter("id", id)
                .getResultList();
    }

    public MemberSpecHistory findFirst(Long id){
        try{
            return em.createQuery("select msh from MemberSpecHistory msh"+
                            " join fetch msh.memberSpec ms"+
                            " where ms.id =: id " +
                            "order by msh.make_date_withTime ASC"
                            , MemberSpecHistory.class)
                    .setParameter("id", id)
                    .getResultList().get(0);
        }catch (NoResultException e){
            return null;
        }
    }
}
