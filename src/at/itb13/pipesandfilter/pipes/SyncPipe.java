package at.itb13.pipesandfilter.pipes;

/**
 * Created by Mathias on 18/11/2015.
 */
public class SyncPipe<T> extends BufferedSyncPipe <T>{
    public SyncPipe(int maxBufferSize) {
        super(maxBufferSize);
    }

}
