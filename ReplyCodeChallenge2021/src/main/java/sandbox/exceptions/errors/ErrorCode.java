package sandbox.exceptions.errors;

public interface ErrorCode {

    default String code() {
        if (this instanceof Enum) {
            return ((Enum) this).name();
        } else {
            return "UNDEFINED";
        }
    }

    String description();
}
