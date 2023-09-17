package Capstone.Capstone.Repository;

import Capstone.Capstone.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    public void save(Member member){
        em.persist(member);
    }
    public Member findById(Long id){
        return em.find(Member.class, id);
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public List findByRealName(String realName){
        return em.createQuery("select m from Member m where m.realName = :realName", Member.class)
                .setParameter("realName", realName)
                .getResultList();
    }
    public Optional<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username =: username", Member.class)
                .setParameter("username", username)
                .getResultList()
                .stream().findFirst();
    }
}
