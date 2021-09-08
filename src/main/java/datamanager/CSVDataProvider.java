package datamanager;

/**
 * Created by jeremy.charpentier on 16/10/2020.
 */
public abstract class CSVDataProvider<A extends IDataType> extends FileDataProvider implements IDataPovider<A> {

    public CSVDataProvider(String filename) {
        super(filename);
    }
}
