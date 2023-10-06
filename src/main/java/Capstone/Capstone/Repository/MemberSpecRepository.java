package Capstone.Capstone.Repository;

import Capstone.Capstone.Entity.MemberSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberSpecRepository {
    private final EntityManager em;

    public void save(MemberSpec memberSpec) {
        em.persist(memberSpec);
    }

    public MemberSpec findById(Long id) {
        return em.find(MemberSpec.class, id);
    }

    public List findAll() {
        return em.createQuery("select ms from MemberSpec ms", MemberSpec.class)
                .getResultList();
    }


    public MemberSpec findByMemberId(Long id) { // join 한거임
        try{
            return em.createQuery("select ms from MemberSpec ms" +
                            " join fetch ms.member m"+
                            " where m.id =: id", MemberSpec.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}