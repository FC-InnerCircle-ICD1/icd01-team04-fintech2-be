package innercircle.incermember.adapter.in.web;

import innercircle.incermember.adapter.out.persistence.jpa.entity.MemberEntity;
import innercircle.incermember.application.port.in.GetMemberUseCase;
import innercircle.incermember.application.port.in.dto.CreateMemberRequest;
import innercircle.incermember.application.port.in.CreateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final CreateMemberUseCase createMemberUseCase;
    private final GetMemberUseCase getMemberUseCase;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody CreateMemberRequest request) {
        createMemberUseCase.createMember(request);
        return ResponseEntity.ok().build();
    }

    // 전체 회원 조회 API
    @GetMapping
    public ResponseEntity<List<MemberEntity>> getAllMembers() {
        List<MemberEntity> members = getMemberUseCase.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 특정 회원 조회 API
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberEntity> getMemberById(@PathVariable String memberId) {
        MemberEntity member = getMemberUseCase.getMemberById(memberId);
        return ResponseEntity.ok(member);
    }
}
