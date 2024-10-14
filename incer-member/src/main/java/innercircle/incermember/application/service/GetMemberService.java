package innercircle.incermember.application.service;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import innercircle.incermember.application.port.in.GetMemberUseCase;
import innercircle.incermember.application.port.out.SelectMemberPort;
import innercircle.incermember.domain.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMemberService implements GetMemberUseCase {

    private final SelectMemberPort selectMemberPort;

    @Override
    public List<MemberEntity> getAllMembers() {
        return selectMemberPort.findAll();
    }

    @Override
    public MemberEntity getMemberById(String memberId) {
        return selectMemberPort.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
