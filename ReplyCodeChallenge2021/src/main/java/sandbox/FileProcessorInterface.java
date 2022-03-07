package sandbox;

import java.io.InputStream;


public interface FileProcessorInterface {
    void process(InputStream inputStream);

    void process(String line);
}
