package io.github.jant009.BAFwk.datamanager;

import io.github.jant009.BAFwk.setup.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by jeremy.charpentier on 06/06/2017.
 */
public abstract class PropertiesDataProvider<A extends IDataType> extends FileDataProvider implements IDataPovider<A> {

    protected Properties data = null;

    public PropertiesDataProvider(String filename) {
        super(filename);

        if (! filename.equals(Settings.NDEF))
            data = new Properties();
            try {
                FileInputStream fis = new FileInputStream(filename);
                data.load(fis);
                fis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public abstract A fetch(String dataReference) throws DataSourceException;

    protected String getValue(String dataReference, String attributeName) throws DataSourceException {
        if (this.data == null)
            throw new DataSourceException("Properties data is not set");
        return this.data.getProperty(dataReference + "." + attributeName);
    }

    protected String getOptionalValue(String dataReference, String attributeName, String defaultValue) throws DataSourceException {
        if (this.containsValue(dataReference, attributeName))
            return this.getValue(dataReference, attributeName);
        else
            return defaultValue;
    }

    protected Boolean containsValue(String dataReference, String attributeName) throws DataSourceException {
        if (this.data == null)
            throw new DataSourceException("Properties data is not set");
        return this.data.containsKey(dataReference + "." + attributeName);
    }
}
