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
                    .make_date(memberSpecHistory.getMake_date())
                    .make_date_withTime(memberSpecHistory.getMake_date_withTime())
                    .his_weight(memberSpecHistory.getHis_weight())
                    .his_career(memberSpecHistory.getHis_career() / 100)
                    .build();
            memberSpecHistoryDTOList.add(memberSpecHistoryDTO);
        }
        return memberSpecHistoryDTOList;
    }

    @Override
    public MemberSpecHistoryDTO findFirstRecord(Long id) {
        MemberSpecHistory firstRecord = historyRepository.findFirst(id);
        if (firstRecord == null){
            return null;
        }else{
            return MemberSpecHistoryDTO.builder()
                    .make_date(firstRecord.getMake_date())
                    .make_date_withTime(firstRecord.getMake_date_withTime())
                    .his_career(firstRecord.getHis_career() / 100)
                    .his_weight(firstRecord.getHis_weight())
                    .build();
        }
    }

    @Override
    public MemberSpecHistory findFirstRecord_V2(Long id) {
        MemberSpecHistory firstRecord = historyRepository.findFirst(id);
        return firstRecord;
    }
}
