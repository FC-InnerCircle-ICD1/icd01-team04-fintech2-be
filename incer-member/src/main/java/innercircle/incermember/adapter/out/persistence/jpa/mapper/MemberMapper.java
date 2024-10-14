package innercircle.incermember.adapter.out.persistence.jpa.mapper;

import innercircle.incermember.domain.Member;
import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;

public class MemberMapper {

    public static MemberEntity toEntity(Member member) {
        return new MemberEntity(
            member.getMemberId(),
            member.getPassword(),
            member.getVerificationImage(),
            member.getRole(),
            member.isApproved()
        );
    }

    public static Member toDomain(MemberEntity entity) {
        return new Member(
            entity.getMemberId(),
            entity.getPassword(),
            entity.getVerificationImage(),
            entity.getRole(),
            entity.isApproved()
        );
    }
}
