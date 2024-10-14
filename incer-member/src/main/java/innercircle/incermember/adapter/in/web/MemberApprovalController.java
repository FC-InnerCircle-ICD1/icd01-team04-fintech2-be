package innercircle.incermember.adapter.in.web;

import innercircle.incermember.application.port.in.AssignRoleUseCase;
import innercircle.incermember.common.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApprovalController {

    private final AssignRoleUseCase assignRoleUseCase;

    @PostMapping("/{memberId}/approve")
    public ResponseEntity<Void> approveMember(@PathVariable String memberId) {
        assignRoleUseCase.assignRole(memberId, Role.CUSTOMER_ADMIN);
        // TODO 상점 생성 로직 추가
        return ResponseEntity.ok().build();
    }
}