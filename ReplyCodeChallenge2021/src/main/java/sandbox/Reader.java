package sandbox;

import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.ReaderErrorCode;
import sandbox.exceptions.errors.StringUtilsErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Reader {

    private final Logger LOGGER = LogManager.getLogger(Reader.class);

    private final FileProcessorInterface processor;

    public Reader(FileProcessorInterface processor) {
        this.processor = processor;
    }

    /**
     * Method to multiple files from an array
     * @param filenames the array of the filenames to be read WITH extension
     */
    public void readFiles(String[] filenames) throws RCCException {
        List<String> filenamesList = Arrays.asList(filenames);
        readFiles(filenamesList);
    }

    /**
     * Method to multiple files from a List
     * @param filenames the array of the filenames to be read WITH extension
     */
    public void readFiles(List<String> filenames) throws RCCException {
        for (String filename : filenames) {
            String[] filenameAndExtension = StringUtils.extractExtension(filename);
            readFile(filenameAndExtension[0], filenameAndExtension[1]);
        }
    }

    /**
     * Method to read a file
     * @param filename the name of the file (if extension not provided [.txt] will be used)
     */
    public void readFile(String filename) throws RCCException {
        try {
            String[] filenameAndExtension = StringUtils.extractExtension(filename);
            readFile(filenameAndExtension[0], filenameAndExtension[1]);
        } catch (RCCException e) {
            if(StringUtilsErrorCode.FILE_EXTENSION_NOT_PROVIDED.code().equals(e.getErrorCode().code())) {
                LOGGER.warn("Read file called without extension. Default set to [.txt]");
                readFile(filename, "txt");
            } else {
                throw e;
            }
        }
    }

    /**
     * Method to read a file
     * @param filename the name of the file to read WITHOUT extension
     * @param extension the extension of the file to read (without ".")
     */
    public void readFile(String filename, String extension) throws RCCException {
        String completeFilename = filename + "." + extension;
        LOGGER.debug("Reading file [{}]", completeFilename);

        ClassLoader classLoader = Reader.class.getClassLoader();

        InputStream inputStream = null;
        try {
            URL resource = classLoader.getResource(completeFilename);
            if(resource == null) {
                throw new RCCException(ReaderErrorCode.RESOURCE_NOT_FOUND);
            }
            File file = new File(resource.toURI());
            inputStream = new FileInputStream(file);

            LOGGER.debug("Start processing file [{}]", completeFilename);

            //option 1
            //this.processor.process(inputStream);

            //option 2
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    this.processor.process(line);
                }
            } catch (IOException e) {
                LOGGER.error("Error while reading file", e);
                throw new RCCException(ReaderErrorCode.ERROR_WHILE_READING);
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("File not found", e);
            throw new RCCException(ReaderErrorCode.FILE_NOT_FOUND);
        } catch (URISyntaxException u) {
            LOGGER.error("Uri syntax error", u);
            throw new RCCException(ReaderErrorCode.GENERIC_EXCEPTION);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Cannot close input stream", e);
                }
            }
        }

    }
}
