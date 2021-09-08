package datamanager;

import java.util.ArrayList;

public abstract class FallbackedFileDataProvider {
    protected final ArrayList<String> filenames;

    protected FallbackedFileDataProvider(ArrayList<String> filenames) {
        this.filenames = filenames;
    }
}
