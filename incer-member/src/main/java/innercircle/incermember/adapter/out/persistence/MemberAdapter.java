package innercircle.incermember.adapter.out.persistence;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import innercircle.incermember.adapter.out.persistence.jpa.repository.MemberRepository;
import innercircle.incermember.application.port.out.SaveMemberPort;
import innercircle.incermember.application.port.out.SelectMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberAdapter implements SaveMemberPort, SelectMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity save(MemberEntity member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<MemberEntity> findById(String memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<MemberEntity> findAll() {
        return memberRepository.findAll();
    }
}