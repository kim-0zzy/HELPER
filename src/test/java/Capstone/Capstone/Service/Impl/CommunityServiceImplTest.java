package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;
import Capstone.Capstone.Repository.CommunityRepository;
import Capstone.Capstone.Service.CommunityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommunityServiceImplTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityRepository communityRepository;
    @Test
    void findByTitle() {
//        private String ot_Username;
//        private String ot_Password;
//        private String title;
//        private String content;

//        communityRepository.save(new Community("aaa","123","111","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("bbb","123","222","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("ccc","123","333","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("ddd","123","444","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("eee","123","555","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("fff","123","666","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("ggg","123","111","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("hhh","123","222","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("iii","123","333","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("jjj","123","444","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("kkk","123","555","lalala",LocalDateTime.now()));
//        communityRepository.save(new Community("lll","123","666","lalala",LocalDateTime.now()));
//
//        List<CommunityDTO> communityDTOList = communityService.findByTitle("111");
//        org.assertj.core.api.Assertions.assertThat(communityDTOList.size()).isEqualTo(2);
//        List<CommunityDTO> communityDTOList2 = communityService.findAllNotice();
//        org.assertj.core.api.Assertions.assertThat(communityDTOList2.size()).isEqualTo(12);


    }

    @Test
    void findAllNotice() {
    }
}