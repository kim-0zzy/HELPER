package Capstone.Capstone.Service;


import Capstone.Capstone.Dto.MemberSpecHistoryDTO;
import Capstone.Capstone.Entity.MemberSpecHistory;

import java.util.List;

public interface HistoryService {
    public Long saveHistory(MemberSpecHistory memberSpecHistory);
    public List<MemberSpecHistoryDTO> findAllHistory(Long id);
    public MemberSpecHistoryDTO findFirstRecord(Long id);
    public MemberSpecHistory findFirstRecord_V2(Long id);
}
