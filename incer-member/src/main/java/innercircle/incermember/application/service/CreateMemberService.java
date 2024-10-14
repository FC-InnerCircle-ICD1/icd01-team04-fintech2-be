package innercircle.incermember.application.service;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import innercircle.incermember.adapter.out.persistence.jpa.mapper.MemberMapper;
import innercircle.incermember.application.port.in.dto.CreateMemberRequest;
import innercircle.incermember.application.port.in.CreateMemberUseCase;
import innercircle.incermember.application.port.out.SaveMemberPort;
import innercircle.incermember.application.port.out.SelectMemberPort;
import innercircle.incermember.domain.Member;
import innercircle.incermember.common.Role;
import innercircle.incermember.domain.exception.MemberIdAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMemberService implements CreateMemberUseCase {

    private final SelectMemberPort selectMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public MemberEntity createMember(CreateMemberRequest request) {
        String memberId = request.getMemberId();
        if (selectMemberPort.findById(memberId).isPresent()) {
            throw new MemberIdAlreadyExistsException(memberId);
        }

        //TODO 이미지 업로드 구현

        Member member = new Member(memberId, request.getPassword(), request.getVerificationImage(), Role.PRE_CUSTOMER_ADMIN, false);

        MemberEntity memberEntity = MemberMapper.toEntity(member);

        return saveMemberPort.save(memberEntity);
    }
}
