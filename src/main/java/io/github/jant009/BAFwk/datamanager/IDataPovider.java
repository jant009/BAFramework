package io.github.jant009.BAFwk.datamanager;

/**
 * Created by jeremy.charpentier on 06/06/2017.
 */
public interface IDataPovider<A extends IDataType> {
    A fetch(String dataReference) throws DataSourceException;
}

