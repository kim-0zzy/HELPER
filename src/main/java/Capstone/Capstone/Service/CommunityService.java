package Capstone.Capstone.Service;


import Capstone.Capstone.Dto.CommunityDTO;
import Capstone.Capstone.Entity.Community;

<<<<<<< HEAD
import javax.persistence.NoResultException;
=======
>>>>>>> 4e46f58d12410f694c5f52c8ee67a3caeb5525ec
import java.util.List;

public interface CommunityService {
    public List<CommunityDTO> findByTitle(String title);
    public List<CommunityDTO> findAllNotice();
<<<<<<< HEAD
    public void saveNotice(Community community);
    public void deleteNotice(Community community) throws NoResultException;

=======
>>>>>>> 4e46f58d12410f694c5f52c8ee67a3caeb5525ec
}
