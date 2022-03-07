package sandbox.exceptions.errors;

public enum ProcessorErrorCode implements ErrorCode {
    RUN_WITHOUT_PROCESSING("run invoked on processor without previous processing\".");

    private final String description;

    ProcessorErrorCode(final String description) {
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