package sandbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.StringUtilsErrorCode;
import sandbox.exceptions.errors.WriterErrorCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractWriter {

    private final Logger LOGGER = LogManager.getLogger(AbstractWriter.class);

    private final static String BASE_PATH = "src/main/resources/output/";

    private BufferedWriter writer = null;

    /**
     * Method to initialize a file writer
     * @param filename the name of the file (if extension not provided [.txt] will be used)
     */
    public void initWriter(String filename) throws RCCException {
        try {
            String[] filenameAndExtension = StringUtils.extractExtension(filename);
            initWriter(filenameAndExtension[0], filenameAndExtension[1]);
        } catch (RCCException e) {
            if(StringUtilsErrorCode.FILE_EXTENSION_NOT_PROVIDED.code().equals(e.getErrorCode().code())) {
                LOGGER.warn("Write file called without extension. Default set to [.txt]");
                initWriter(filename, "txt");
            } else {
                throw e;
            }
        }
    }

    /**
     * Method to initialize a file writer
     * @param filename the name of the file to read WITHOUT extension
     * @param extension the extension of the file to read (without ".")
     */
    public void initWriter(String filename, String extension) {
        String completeFilename = BASE_PATH + filename + "." + extension;
        try {
            File file = new File(completeFilename);
            boolean appendMode = file.exists() && !file.isDirectory();
            FileWriter fileWriter = new FileWriter(file, appendMode);
            writer = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            LOGGER.error("Error while initializing writer to filename {}", completeFilename);
        }
    }

    public void writeLines(String[] lines) throws RCCException {
        List<String> linesList = Arrays.asList(lines);
        writeLines(linesList);
    }

    public void writeLines(List<String> lines) throws RCCException {
        for (String line : lines) {
            writeLine(line);
        }
    }

    public void writeLine(String line) throws RCCException {
        if(this.writer == null) {
            LOGGER.error("File to write to not initialized");
            throw new RCCException(WriterErrorCode.FILE_WRITER_NOT_INITIALIZED);
        }

        try {
            this.writer.write(line);
        } catch (IOException e) {
            LOGGER.error("Error while writing line {} to file", line, e);
        }
    }

    public void closeWriter() {
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing writer", e);
            }
        }
    }

    public abstract String processWriting(Object[] objects);
}
