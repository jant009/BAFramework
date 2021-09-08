package datamanager;

/**
 * Created by jeremy.charpentier on 06/06/2017.
 */
public abstract class ExcelDataProvider<A extends IDataType> extends FileDataProvider implements IDataPovider<A> {

    public ExcelDataProvider(String filename) {
        super(filename);
    }
}
