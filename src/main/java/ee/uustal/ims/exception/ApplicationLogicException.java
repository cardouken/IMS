package ee.uustal.ims.exception;

public class ApplicationLogicException extends RuntimeException {

    public enum ErrorCode {

        BALANCE_LESS_THAN_ZERO,
        BALANCE_CHANGE_EXCEEDS_MAX_LIMIT,
        PLAYER_BLACKLISTED;

        public String formatErrorCode() {
            return "error." + name().toLowerCase().replace("_", "-");
        }
    }

    public ApplicationLogicException(ErrorCode error) {
        super(error.formatErrorCode(), null, true, false);
    }
}
