package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;
import Capstone.Capstone.Repository.CommunityRepository;
import Capstone.Capstone.Service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("CommunityService")
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public Page<Community> findByTitle(String title, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Community> page = communityRepository.findByTitle(title, pageRequest);
        return page;
    }

    @Override
    public Page<Community> findAllNotice(int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Community> page = communityRepository.findAll(pageRequest);
        return page;
    }

    @Override
    public List<CommunityDTO> findRecently5() {
        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<Community> page = communityRepository.findFirst5By(pageRequest);
        List<Community> content = page.getContent();
        List<CommunityDTO> communityDTOList = new ArrayList<>();
        for (Community community : content) {
            CommunityDTO communityDTO = CommunityDTO.builder()
                    .id(community.getId())
                    .ot_Username(community.getOt_Username())
                    .title(community.getTitle())
                    .content(community.getContent())
                    .createDate(community.getCreateDate())
                    .build();
            communityDTOList.add(communityDTO);
        }
        return communityDTOList;
    }

    @Override
    public CommunityDTO findById(Long id) {
        Community community = null;
        if(communityRepository.findById(id).isPresent()){
            community = communityRepository.findById(id).get();
        }
        CommunityDTO communityDto = CommunityDTO.builder()
                .id(community.getId())
                .ot_Username(community.getOt_Username())
                .title(community.getTitle())
                .content(community.getContent())
                .createDate(community.getCreateDate())
                .build();
        return communityDto;
    }
    public Community findById_v2(Long id) {
        Community community = null;
        if(communityRepository.findById(id).isPresent()){
            community = communityRepository.findById(id).get();
        }
        return community;
    }

    @Override
    public void saveNotice(Community community) throws NoResultException {
        communityRepository.save(community);
    }

    @Override
    public void deleteNotice(Long id, String title) throws NoResultException {
        communityRepository.deleteByIdAndTitle(id, title);
    }

}
