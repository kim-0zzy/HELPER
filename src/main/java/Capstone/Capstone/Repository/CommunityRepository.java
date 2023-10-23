package Capstone.Capstone.Repository;

import Capstone.Capstone.Entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

// Data-jpa의 페이지 start point는 0임.
// 페이지 offset, limit 이용하려면 PageRequest 구현해야함. 강의 5분부터
// 22분경 카운트 쿼리 분리하기
// 반환할 때 무조건 DTO로 변환 26분경
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query("select c from Community c where c.title like concat('%', :title, '%') ")
    Page<Community> findByTitle(String title, Pageable pageable);
    @Query("select c from Community c order by c.createDateWithTime DESC ")
    Page<Community> findAll(Pageable pageable);
    @Query("select c from Community c order by c.createDateWithTime DESC")
    Page<Community> findFirst5By(Pageable pageable);
//    Community findByIdAndTitle(Long id, String title);
    @Query("select c from Community c where c.id =:id")
    Optional<Community> findById(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("delete from Community c where c.id =:id and c.title =:title")
    void deleteByIdAndTitle(@Param("id") Long id, @Param("title") String title);

// Data-Jpa로 해결할 것임.
//    public long totalCount(String title){
//        return em.createQuery("select count(c) from Community c where c.title =: title", Long.class)
//                .setParameter("title", title)
//                .getSingleResult();
//    }
//    public List findByTitle(String title, int offset, int limit){
//        return em.createQuery("select c from Community c where c.title =: title order by c.notice_Num DESC")
//                .setParameter("title", title)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//    }
//    public List findAll(int offset, int limit){
//        return em.createQuery("select c from Community c order by c.notice_Num desc ")
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//    }
}
