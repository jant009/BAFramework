package datamanager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public abstract class FallbackedPropertiesDataProvider<A extends IDataType> extends FallbackedFileDataProvider implements IDataPovider<A> {

    protected ArrayList<Properties> datas = null;

    public FallbackedPropertiesDataProvider(ArrayList<String> filenames) {
        super(filenames);
        this.datas = new ArrayList<Properties>();
        for (String filename: this.filenames) {
            datas.add(propertiesFromFile(filename));
        }
    }

    private static Properties propertiesFromFile(String filename) {
        Properties data = new Properties();
        try {
            FileInputStream fis = new FileInputStream(filename);
            data.load(fis);
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public abstract A fetch(String dataReference) throws DataSourceException;

    protected String getValue(String dataReference, String attributeName) throws DataSourceException {
        for (Properties data : this.datas)
            if (dataContainsValue(data, dataReference, attributeName))
                return data.getProperty(dataReference + "." + attributeName);
        throw new DataSourceException(dataReference + "." + attributeName + " does not exists in the listed data sources");
    }

    protected String getOptionalValue(String dataReference, String attributeName, String defaultValue) throws DataSourceException {
        for (Properties data : this.datas)
            if (dataContainsValue(data, dataReference, attributeName))
                return data.getProperty(dataReference + "." + attributeName);
        return defaultValue;
    }

    protected Boolean containsValue(String dataReference, String attributeName) throws DataSourceException {
        for (Properties data : this.datas)
            if (dataContainsValue(data, dataReference, attributeName))
                return true;
        return false;
    }

    protected static Boolean dataContainsValue(Properties data, String dataReference, String attributeName) throws DataSourceException {
        if (data == null)
            throw new DataSourceException("Properties data is not set");
        return data.containsKey(dataReference + "." + attributeName);
    }
}
