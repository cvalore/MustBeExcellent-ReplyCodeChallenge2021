package sandbox.exceptions.errors;

public enum StringUtilsErrorCode implements ErrorCode {
    DEFAULT_STRING_NULL("Default string cannot be null."),
    FILE_EXTENSION_NOT_PROVIDED("File extension not provided."),
    TOO_MAY_FILE_EXTENSION("Too many file extensions provided."),
    FILE_EXTENSION_NOT_VALID("File extension is not valid.");

    private final String description;

    StringUtilsErrorCode(final String description) {
        this.description = description;
    }

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String description() {
        return this.description;
    }
}
