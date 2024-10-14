package innercircle.incermember.adapter.out.persistence.jpa.entity;

import innercircle.incermember.common.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
public class MemberEntity {

    @Id
    private String memberId;

    private String password;

    private String verificationImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean approved;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public MemberEntity(String memberId, String password, String verificationImage, Role role, boolean approved) {
        this.memberId = memberId;
        this.password = password;
        this.verificationImage = verificationImage;
        this.role = role;
        this.approved = approved;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
