package innercircle.incermember.application.port.in;

import innercircle.incermember.common.Role;

public interface AssignRoleUseCase {
    void assignRole(String memberId, Role role);
}
