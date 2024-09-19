package innercircle.incermember.application.port.in;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;

import java.util.List;

public interface GetMemberUseCase {
    List<MemberEntity> getAllMembers();
    MemberEntity getMemberById(String memberId);
}
