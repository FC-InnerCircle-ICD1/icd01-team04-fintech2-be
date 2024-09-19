package innercircle.incermember.domain.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String memberId) {
        super("Member with ID " + memberId + " not found.");
    }
}