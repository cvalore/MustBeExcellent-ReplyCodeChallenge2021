package sandbox.exceptions.errors;

public enum ReaderErrorCode implements ErrorCode {
    RESOURCE_NOT_FOUND("Resource to be read not found."),
    FILE_NOT_FOUND("File to be read not found"),
    GENERIC_EXCEPTION("Generic exception."),
    ERROR_WHILE_READING("Error while reading file.");

    private final String description;

    ReaderErrorCode(final String description) {
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