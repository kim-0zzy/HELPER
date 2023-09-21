package Capstone.Capstone.Service;


import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;

import java.util.List;

public interface CommunityService {
    public List<CommunityDTO> findByTitle(String title);
    public List<CommunityDTO> findAllNotice();
}
