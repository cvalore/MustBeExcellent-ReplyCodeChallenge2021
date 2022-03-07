package sandbox;

import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.StringUtilsErrorCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class StringUtils {

    private static final List<String> validExtensions = Arrays.asList("csv", "txt", "in");

    public static String defaultString(String str, String defaultString) throws RCCException {
        if(defaultString == null) {
            throw new RCCException(StringUtilsErrorCode.DEFAULT_STRING_NULL);
        }
        return (str != null ? str : defaultString);
    }

    public static String[] extractExtension(String filename) throws RCCException {
        String[] splitted = filename.split("\\.");
        if(splitted.length < 2) {
            throw new RCCException(StringUtilsErrorCode.FILE_EXTENSION_NOT_PROVIDED);
        }
        if(splitted.length > 2) {
            throw new RCCException(StringUtilsErrorCode.TOO_MAY_FILE_EXTENSION);
        }

        boolean validExtension = isValidExtension(splitted[1]);
        if(splitted[1].isEmpty() || !validExtension) {
            throw new RCCException(StringUtilsErrorCode.FILE_EXTENSION_NOT_VALID);
        }

        return splitted;
    }

    public static boolean isValidExtension(String ext) {
        AtomicBoolean validExtension = new AtomicBoolean(false);
        validExtensions.forEach(ve -> {
            if(ve.equals(ext)) {
                validExtension.set(true);
            }
        });
        return validExtension.get();
    }

}
