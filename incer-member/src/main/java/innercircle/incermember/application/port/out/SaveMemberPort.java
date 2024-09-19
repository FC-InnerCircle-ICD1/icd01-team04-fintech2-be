package innercircle.incermember.application.port.out;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;

public interface SaveMemberPort {
    MemberEntity save(MemberEntity member);
}
