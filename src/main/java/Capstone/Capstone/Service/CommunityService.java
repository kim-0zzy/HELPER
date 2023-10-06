package Capstone.Capstone.Service;


import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;

import javax.persistence.NoResultException;

import java.util.List;

public interface CommunityService {
    public List<CommunityDTO> findByTitle(String title);
    public List<CommunityDTO> findAllNotice();
    public void saveNotice(Community community);
    public void deleteNotice(Community community) throws NoResultException;

}
