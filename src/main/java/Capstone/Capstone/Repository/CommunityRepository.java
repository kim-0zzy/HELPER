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

}
