package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Dto.MemberSpecDTO;
import Capstone.Capstone.Dto.MemberSpecHistoryDTO;
import Capstone.Capstone.Entity.MemberSpecHistory;
import Capstone.Capstone.Repository.HistoryRepository;
import Capstone.Capstone.Service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("HistoryService")
@RequiredArgsConstructor
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public Long saveHistory(MemberSpecHistory memberSpecHistory) {
        historyRepository.save(memberSpecHistory);
        return memberSpecHistory.getId();
    }

    @Override
    public List<MemberSpecHistoryDTO> findAllHistory(Long id) {
        List<MemberSpecHistory> all = historyRepository.findByOwnerID(id);
        List<MemberSpecHistoryDTO> memberSpecHistoryDTOList = new ArrayList<>();

        for (MemberSpecHistory memberSpecHistory : all) {
            MemberSpecHistoryDTO memberSpecHistoryDTO = MemberSpecHistoryDTO.builder()
                    .make_Year(memberSpecHistory.getMake_Year())
                    .make_Month(memberSpecHistory.getMake_Month())
                    .make_Day(memberSpecHistory.getMake_Day())
                    .his_weight(memberSpecHistory.getHis_weight())
                    .his_career(memberSpecHistory.getHis_career())
                    .build();
        }
        return memberSpecHistoryDTOList;
    }
}
