package innercircle.incermember.application.port.out;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface SelectMemberPort {
    Optional<MemberEntity> findById(String memberId);

    List<MemberEntity> findAll();
}
