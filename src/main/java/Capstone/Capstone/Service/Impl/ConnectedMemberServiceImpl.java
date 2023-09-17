package Capstone.Capstone.Service.Impl;

import Capstone.Capstone.Entity.ConnectedMember;
import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Repository.ConnectedMemberRepository;
import Capstone.Capstone.Service.ConnectedMemberService;
import Capstone.Capstone.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ConnectedMemberService")
@Transactional
@RequiredArgsConstructor
public class ConnectedMemberServiceImpl implements ConnectedMemberService {

    private final ConnectedMemberRepository connectedMemberRepository;
    private final MemberService memberService;
    @Override
    public Long connectId(ConnectedMember connectedMember) {
        connectedMemberRepository.save(connectedMember);
        return connectedMember.getConnectedId();
    }

    @Override
    public void disconnectId(Long id) {
        connectedMemberRepository.delete(id);
    }

    @Override
    public Member findByConnectedMember() {
        ConnectedMember connectedMember = connectedMemberRepository.findByConnectedMember();
        Member member = memberService.findOneById(connectedMember.getConnectedId());
        return member;

    }
}
