package innercircle.incermember.application.port.in.dto;

import lombok.Data;

@Data
public class CreateMemberRequest {
    private String memberId;
    private String password;
    private String verificationImage;
}