package sandbox.exceptions.errors;

public enum WriterErrorCode implements ErrorCode{
    FILE_WRITER_NOT_INITIALIZED("File writer not initialized.");

    private final String description;

    WriterErrorCode(final String description) {
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
