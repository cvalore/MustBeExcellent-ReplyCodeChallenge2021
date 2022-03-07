package sandbox;

import sandbox.exceptions.RCCException;

import java.io.InputStream;


public interface FileProcessorInterface {
    void process(InputStream inputStream);

    void process(String line);

    void run() throws RCCException;

    void setProcessed();
}
