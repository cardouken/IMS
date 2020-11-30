package ee.uustal.ims.exception;

public class ApplicationLogicException extends RuntimeException {

    public enum ErrorCode {

        BALANCE_LESS_THAN_ZERO,
        BALANCE_GREATER_THAN_MAX_LIMIT,
        PLAYER_BLACKLISTED;

        public String toLocalizationKey() {
            return "error." + name().toLowerCase().replace("_", "-");
        }
    }

    public ApplicationLogicException(ErrorCode error) {
        super(error.toLocalizationKey(), null, true, false);
    }
}
