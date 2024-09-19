package innercircle.incermember.application.port.in;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import innercircle.incermember.application.port.in.dto.CreateMemberRequest;

public interface CreateMemberUseCase {
    MemberEntity createMember(CreateMemberRequest createMemberRequest);
}
