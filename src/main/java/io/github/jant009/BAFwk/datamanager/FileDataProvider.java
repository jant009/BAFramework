package io.github.jant009.BAFwk.datamanager;

/**
 * Created by jeremy.charpentier on 06/06/2017.
 */
public abstract class FileDataProvider {

    protected final String filename;

    protected FileDataProvider(String filename) {
        this.filename = filename;
    }
}
