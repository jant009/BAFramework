package setup;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by jeremy.charpentier on 01/09/2017.
 */
public class Settings {
    private static final String GLOBALE_FILENAME = "global.properties";
    public static final String NDEF ="NDEF";

    private static Properties settings = null;

    public static String getProperty(String key) {
        if (settings == null) {
            settings = new Properties();
            try {
                FileInputStream fis = new FileInputStream(Settings.GLOBALE_FILENAME);
                settings.load(fis);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return Settings.NDEF;
            }
        }

        return settings.getProperty(key);
    }
}
