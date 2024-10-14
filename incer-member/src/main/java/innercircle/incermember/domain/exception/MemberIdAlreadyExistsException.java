package innercircle.incermember.domain.exception;

public class MemberIdAlreadyExistsException extends RuntimeException {
    public MemberIdAlreadyExistsException(String memberId) {
        super("MemberId '" + memberId + "' is already taken.");
    }
}