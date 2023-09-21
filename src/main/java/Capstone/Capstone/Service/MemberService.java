package Capstone.Capstone.Service;

import Capstone.Capstone.Entity.Member;
import Capstone.Capstone.Exception.NotFoundIdException;
import Capstone.Capstone.Exception.PasswordException;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    public Long join(Member member);
    public Long login(Member member, String passwd) throws PasswordException, NotFoundIdException;
    public List<Member> findMembers(Member member);
    public Member findOneById(Long id);
    public List<Member> findOneByRealName(String realName);
    public Member findByUsername(String username);

}
