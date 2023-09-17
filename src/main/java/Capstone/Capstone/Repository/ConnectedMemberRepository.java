package Capstone.Capstone.Repository;

import Capstone.Capstone.Entity.ConnectedMember;
import Capstone.Capstone.Exception.NotFoundIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
@RequiredArgsConstructor
public class ConnectedMemberRepository {

    private final EntityManager em;

    public void save(ConnectedMember connectedMember){
        em.persist(connectedMember);
    }

    public ConnectedMember findByConnectedMember(){
        try{
            return em.createQuery("select cm from ConnectedMember cm", ConnectedMember.class)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public void delete(Long id){
        em.createQuery("delete from ConnectedMember" +
                        " where connectedId =: id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
