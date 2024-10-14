package innercircle.incermember.domain;

import innercircle.incermember.common.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String memberId;
    private String password;
    private String verificationImage;
    private Role role;
    private boolean approved;
}
