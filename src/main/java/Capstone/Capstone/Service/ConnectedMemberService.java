package Capstone.Capstone.Service;


import Capstone.Capstone.Entity.Calendar;
import Capstone.Capstone.Entity.ConnectedMember;
import Capstone.Capstone.Entity.Member;

public interface ConnectedMemberService {
    public Long connectId(ConnectedMember connectedMember);
    public void disconnectId(Long id);
    public Member findByConnectedMember();
}
