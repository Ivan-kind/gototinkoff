package my.gototinkoff.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IOUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtil.class);

    private IOUtil() {}

    public static String getScriptByResource(URL resource) {
        LOGGER.trace("getScriptByResource - start resource : {}", resource);
        try {
            StringBuilder result = new StringBuilder();
            String line;
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(resource.openStream()));
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
            LOGGER.trace("getScriptByResource - end result : {}", result);
            return result.toString();
        }
        catch (Exception e) {
            LOGGER.error("getScriptByResource - ERROR!", e);
        }
        return null;
    }
}
