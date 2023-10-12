package Capstone.Capstone.Service;


import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;
import org.springframework.data.domain.Page;

import javax.persistence.NoResultException;

import java.util.List;

public interface CommunityService {
    public Page<Community> findByTitle(String title, int pageNum);
    public Page<Community> findAllNotice(int pageNum);
    public List<CommunityDTO> findRecently5();
    public CommunityDTO findByIdWithTitle(Long Id, String title);
    public void saveNotice(Community community);
    public void deleteNotice(Long Id, String title) throws NoResultException;

}
