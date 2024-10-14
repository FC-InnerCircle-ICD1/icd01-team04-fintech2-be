package innercircle.incermember.application.service;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import innercircle.incermember.application.port.in.AssignRoleUseCase;
import innercircle.incermember.application.port.out.SaveMemberPort;
import innercircle.incermember.application.port.out.SelectMemberPort;
import innercircle.incermember.common.Role;
import innercircle.incermember.domain.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignRoleService implements AssignRoleUseCase {

    private final SelectMemberPort selectMemberPort;
    private final SaveMemberPort saveMemberPort;

    @Override
    public void assignRole(String memberId, Role newRole) {
        MemberEntity member = selectMemberPort.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        if (member.getRole() == Role.PRE_CUSTOMER_ADMIN) {
            member.setRole(newRole);
            saveMemberPort.save(member);
        } else {
            throw new IllegalStateException("Cannot assign role to this member.");
        }
    }
}